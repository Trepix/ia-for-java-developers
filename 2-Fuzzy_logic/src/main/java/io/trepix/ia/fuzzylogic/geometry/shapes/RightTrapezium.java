package io.trepix.ia.fuzzylogic.geometry.shapes;

import io.trepix.ia.fuzzylogic.geometry.Point;

import java.util.List;

public class RightTrapezium extends Shape {

    private final List<Shape> shapes;

    public RightTrapezium(Point leftPoint, Point rightPoint) {
        Shape lowerRectangle = createRectangle(leftPoint, rightPoint);
        Shape triangle = new RightTriangle(leftPoint, rightPoint);
        shapes = List.of(lowerRectangle, triangle);
    }

    private static Shape createRectangle(Point leftPoint, Point rightPoint) {
        Point lowerPoint = Point.min(leftPoint, rightPoint);
        if (lowerPoint.equals(leftPoint)) {
            return new Rectangle(leftPoint, new Point(rightPoint.value(), lowerPoint.membershipDegree()));
        }
        return new Rectangle(new Point(leftPoint.value(), lowerPoint.membershipDegree()), rightPoint);
    }

    @Override
    public double area() {
        return shapes.stream().map(Shape::area).mapToDouble(Double::valueOf).sum();
    }

    @Override
    public double weightedArea() {
        return shapes.stream().map(Shape::weightedArea).mapToDouble(Double::valueOf).sum();
    }
}
