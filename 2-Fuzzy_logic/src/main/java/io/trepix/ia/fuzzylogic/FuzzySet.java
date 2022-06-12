package io.trepix.ia.fuzzylogic;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static io.trepix.ia.fuzzylogic.Point.leftmostPoint;
import static io.trepix.ia.fuzzylogic.Point.rightestPoint;
import static java.util.stream.Collectors.toCollection;

public class FuzzySet {

    private final LinkedList<Point> points;

    public FuzzySet(LinkedList<Point> points) {
        this.points = points;
        Collections.sort(this.points);
    }

    public FuzzySet applyDegree(double degree) {
        return new FuzzySet(
                points.stream()
                        .map(point -> point.applyDegree(degree))
                        .collect(toCollection(LinkedList::new))
        );
    }

    public double membershipDegree(double value) {

        if (notBelongToFuzzySet(value)) return 0;

        Point firstPoint = points.get(0);
        Point secondPoint = points.get(1);
        Iterator<Point> iterator = points.iterator();
        while (value >= secondPoint.value()) {
            firstPoint = secondPoint;
            secondPoint = iterator.next();
        }

        if (firstPoint.isValue(value)) {
            return firstPoint.membershipDegree();
        } else {
            return firstPoint.interpolateAt(secondPoint, value).membershipDegree();
        }
    }

    public Point getPointAtValue(double value) {
        return new Point(value, membershipDegree(value));
    }


    private boolean notBelongToFuzzySet(double value) {
        if (points.size() < 2) return true;
        return value < minimum() || value > maximum();
    }

//    public FuzzySet intersect(FuzzySet set) {
//        return merge(this, set, Point::min);
//    }

    public FuzzySet union(FuzzySet set) {
        return merge(this, set, Point::max);
    }


    private static FuzzySet merge(FuzzySet firstSet, FuzzySet secondSet, BiFunction<Point, Point, Point> optimum) {
        LinkedList<Point> mergedPoints = new LinkedList<>();

        Iterator<Point> firstIterator = firstSet.points.iterator();
        Iterator<Point> secondIterator = secondSet.points.iterator();
        Point firstPoint = firstIterator.next();
        Point secondPoint = secondIterator.next();
        Point lastFirstPoint = firstPoint;

        Line lineBefore;
        Line currentLine = firstPoint.lineBetween(secondPoint);

        while (firstPoint != null && secondPoint != null) {
            lineBefore = currentLine;
            currentLine = firstPoint.lineBetween(secondPoint);

            if (currentLine.isIntersectingWith(lineBefore)) {
                double leftmostValue = (firstPoint.isSameValue(secondPoint) ? lastFirstPoint : leftmostPoint(firstPoint, secondPoint)).value();
                double rightestValue = rightestPoint(firstPoint, secondPoint).value();

                Line firstSetLine = new Line(firstSet.getPointAtValue(leftmostValue), firstSet.getPointAtValue(rightestValue));
                Line secondSetLine = new Line(secondSet.getPointAtValue(leftmostValue), secondSet.getPointAtValue(rightestValue));

                double intersectionDistance = 0;
                if (firstSetLine.isNotEqual(secondSetLine)) {
                    intersectionDistance = firstSetLine.intersectionDistance(secondSetLine);
                }

                mergedPoints.add(firstSet.getPointAtValue(leftmostValue + intersectionDistance));

                if (firstPoint.isPreviousTo(secondPoint)) {
                    lastFirstPoint = firstPoint;
                    firstPoint = iterate(firstIterator);
                } else if (secondPoint.isPreviousTo(firstPoint)) {
                    secondPoint = iterate(secondIterator);
                }
            }
            else if (firstPoint.isSameValue(secondPoint)) {
                mergedPoints.add(optimum.apply(firstPoint, secondPoint));

                lastFirstPoint = firstPoint;
                firstPoint = iterate(firstIterator);
                secondPoint = iterate(secondIterator);
            }
            else if (firstPoint.isPreviousTo(secondPoint)) {
                Point pointAtSecondSet = secondSet.getPointAtValue(firstPoint.value());
                mergedPoints.add(optimum.apply(firstPoint, pointAtSecondSet));
                lastFirstPoint = firstPoint;
                firstPoint = iterate(firstIterator);
            }
            else {
                Point pointAtFirstSet = firstSet.getPointAtValue(secondPoint.value());
                mergedPoints.add(optimum.apply(pointAtFirstSet, secondPoint));
                secondPoint = iterate(secondIterator);
            }
        }

        Consumer<Point> addPoints = point -> mergedPoints.add(optimum.apply(point, null));
        firstIterator.forEachRemaining(addPoints);
        secondIterator.forEachRemaining(addPoints);

        return new FuzzySet(mergedPoints);
    }

    private static Point iterate(Iterator<Point> firstIterator) {
        if (firstIterator.hasNext()) return firstIterator.next();
        else return  null;
    }

    public double Baricentro() {
        // Si hay menos de dos puntos, no hay baricentro
        if (points.size() <= 2) {
            return 0;
        } else {
            // Iinicialización de las áreas
            double areaPonderada = 0;
            double areaTotal = 0;
            double areaLocal;
            // Recorrido de la lista conservando 2 puntos
            Point antiguoPt = null;
            for (Point pt : points) {
                if (antiguoPt != null) {
                    // Cálculo del baricentro local
                    if (antiguoPt.membershipDegree() == pt.membershipDegree()) {
                        // Es un rectángulo, baricentro al centro
                        areaLocal = pt.membershipDegree() * (pt.value() - antiguoPt.value());
                        areaTotal += areaLocal;
                        areaPonderada += areaLocal * ((pt.value() - antiguoPt.value()) / 2.0 + antiguoPt.value());
                    } else {
                        // Es un trapecio, se puede descomponer
                        // como un rectángulo con un triangulo rectángulo debajo
                        // Se separan los dos formas
                        // Momento 1 : rectángulo
                        areaLocal = Math.min(pt.membershipDegree(), antiguoPt.membershipDegree()) * (pt.value() - antiguoPt.value());
                        areaTotal += areaLocal;
                        areaPonderada += areaLocal * ((pt.value() - antiguoPt.value()) / 2.0 + antiguoPt.value());
                        // Momento 2 : triangulo rectángulo
                        areaLocal = (pt.value() - antiguoPt.value()) * Math.abs(pt.membershipDegree() - antiguoPt.membershipDegree()) / 2.0;
                        areaTotal += areaLocal;
                        if (pt.membershipDegree() > antiguoPt.membershipDegree()) {
                            // Baricentro de 1/3 lado pt
                            areaPonderada += areaLocal * (2.0 / 3.0 * (pt.value() - antiguoPt.value()) + antiguoPt.value());
                        } else {
                            // Baricentro de 1/3 lado antiguoPt
                            areaPonderada += areaLocal * (1.0 / 3.0 * (pt.value() - antiguoPt.value()) + antiguoPt.value());
                        }
                    }
                }
                antiguoPt = pt;
            }
            // Se devuelve la coordada del baricentro
            return areaPonderada / areaTotal;
        }
    }

    public double minimum() {
        return points.getFirst().value();
    }

    public double maximum() {
        return points.getLast().value();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("[" + minimum() + "-" + maximum() + "]:");
        for (Point pt : points) {
            sj.add(pt.toString());
        }
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuzzySet fuzzySet = (FuzzySet) o;
        return points.equals(fuzzySet.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}
