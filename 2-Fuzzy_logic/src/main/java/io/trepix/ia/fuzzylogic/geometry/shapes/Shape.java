package io.trepix.ia.fuzzylogic.geometry.shapes;

import io.trepix.ia.fuzzylogic.geometry.Line;
import io.trepix.ia.fuzzylogic.geometry.Point;

public abstract class Shape {

    public abstract double area();

    public abstract double weightedArea();

    public static class FigureFactory {

        public static Shape create(Point first, Point second) {
            Line line = first.lineBetween(second);
            if (line.hasZeroSlope()) return new Rectangle(first, second);
            return new RightTrapezium(first, second);
        }

    }

}
