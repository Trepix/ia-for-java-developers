package io.trepix.ia.fuzzylogic;

import io.trepix.ia.fuzzylogic.geometry.Line;
import io.trepix.ia.fuzzylogic.geometry.Point;
import io.trepix.ia.fuzzylogic.geometry.Trend;
import io.trepix.ia.fuzzylogic.geometry.shapes.Shape;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static io.trepix.ia.fuzzylogic.geometry.Point.leftmostPoint;
import static io.trepix.ia.fuzzylogic.geometry.Point.rightestPoint;
import static io.trepix.ia.fuzzylogic.geometry.shapes.Shape.FigureFactory.create;
import static java.util.stream.Collectors.toCollection;

public class FuzzySet {

    private final LinkedList<Point> points;

    public FuzzySet(LinkedList<Point> points) {
        this.points = points;
        Collections.sort(this.points);
    }

    private static FuzzySet merge(FuzzySet firstSet, FuzzySet secondSet, BiFunction<Point, Point, Point> optimum) {
        LinkedList<Point> mergedPoints = new LinkedList<>();

        Iterator<Point> firstIterator = firstSet.points.iterator();
        Iterator<Point> secondIterator = secondSet.points.iterator();
        Point firstPoint = firstIterator.next();
        Point secondPoint = secondIterator.next();
        Point lastFirstPoint = firstPoint;

        Trend lastTrend;
        Trend currentTrend = Trend.given(firstPoint, secondPoint);

        while (firstPoint != null && secondPoint != null) {
            lastTrend = currentTrend;
            currentTrend = Trend.given(firstPoint, secondPoint);

            if (currentTrend.isInvertedRespectTo(lastTrend)) {
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
            } else if (firstPoint.isSameValue(secondPoint)) {
                mergedPoints.add(optimum.apply(firstPoint, secondPoint));

                lastFirstPoint = firstPoint;
                firstPoint = iterate(firstIterator);
                secondPoint = iterate(secondIterator);
            } else if (firstPoint.isPreviousTo(secondPoint)) {
                Point pointAtSecondSet = secondSet.getPointAtValue(firstPoint.value());
                mergedPoints.add(optimum.apply(firstPoint, pointAtSecondSet));
                lastFirstPoint = firstPoint;
                firstPoint = iterate(firstIterator);
            } else {
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
        else return null;
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

//    public FuzzySet intersect(FuzzySet set) {
//        return merge(this, set, Point::min);
//    }

    public Point getPointAtValue(double value) {
        return new Point(value, membershipDegree(value));
    }

    private boolean notBelongToFuzzySet(double value) {
        if (points.size() < 2) return true;
        return value < minimum() || value > maximum();
    }

    public FuzzySet union(FuzzySet set) {
        return merge(this, set, Point::max);
    }

    public double barycenter() {
        if (points.size() <= 2) return 0;

        double weightedArea = 0;
        double totalArea = 0;

        Point lastPoint = null;
        for (Point point : points) {
            if (lastPoint == null) {
                lastPoint = point;
                continue;
            }

            Shape shape = create(lastPoint, point);
            totalArea += shape.area();
            weightedArea += shape.weightedArea();

            lastPoint = point;
        }
        return weightedArea / totalArea;
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
