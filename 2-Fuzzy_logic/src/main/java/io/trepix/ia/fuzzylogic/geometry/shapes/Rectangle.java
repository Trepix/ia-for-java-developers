package io.trepix.ia.fuzzylogic.geometry.shapes;

import io.trepix.ia.fuzzylogic.geometry.Point;

public class Rectangle extends Shape {

    private final Point leftUpperPoint;
    private final Point rightUpperPoint;

    public Rectangle(Point leftUpperPoint, Point rightUpperPoint) {
        this.leftUpperPoint = leftUpperPoint;
        this.rightUpperPoint = rightUpperPoint;
    }


    @Override
    public double weightedArea() {
        double valueBarycenter = leftUpperPoint.value() + rightUpperPoint.distanceBetweenValues(leftUpperPoint) / 2.0;
        return area() * valueBarycenter;
    }

    @Override
    public double area() {
        double width = rightUpperPoint.distanceBetweenValues(leftUpperPoint);
        double height = leftUpperPoint.membershipDegree();
        return width * height;
    }
}
