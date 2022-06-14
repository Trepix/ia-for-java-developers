package io.trepix.ia.fuzzylogic.geometry.shapes;

import io.trepix.ia.fuzzylogic.geometry.Point;

import static java.lang.Math.abs;

public class RightTriangle extends Shape {

    private final Point leftPoint;
    private final Point rightPoint;

    public RightTriangle(Point leftPoint, Point rightPoint) {
        this.leftPoint = leftPoint;
        this.rightPoint = rightPoint;
    }

    @Override
    public double area() {
        double width = rightPoint.distanceBetweenValues(leftPoint);
        double height = abs(rightPoint.distanceBetweenMembershipDegree(leftPoint));
        return width * height / 2.0;
    }

    @Override
    public double weightedArea() {
        double side = sideRatio();
        double valueBarycenter = leftPoint.value() + side * rightPoint.distanceBetweenValues(leftPoint);
        return area() * valueBarycenter;
    }

    private double sideRatio() {
        if (isLookingToLeft()) return 2.0 / 3.0;
        else return 1.0 / 3.0;
    }

    private boolean isLookingToLeft() {
        return rightPoint.isHigherThan(leftPoint);
    }


}
