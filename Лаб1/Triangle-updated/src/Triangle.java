public final class Triangle {
    private static final double EPSILON = 1e-10;

    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        validateSides(sideA, sideB, sideC);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public double getSideA() {
        return sideA;
    }

    public double getSideB() {
        return sideB;
    }

    public double getSideC() {
        return sideC;
    }

    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    public double getArea() {
        double semiPerimeter = getPerimeter() / 2.0;

        return Math.sqrt(
                semiPerimeter
                        * (semiPerimeter - sideA)
                        * (semiPerimeter - sideB)
                        * (semiPerimeter - sideC)
        );
    }

    public boolean isEquilateral() {
        return areEqual(sideA, sideB) && areEqual(sideB, sideC);
    }

    private static boolean areEqual(double first, double second) {
        return Math.abs(first - second) < EPSILON;
    }

    private static void validateSides(
            double sideA, double sideB, double sideC) {

        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new IllegalArgumentException(
                    "Triangle sides must be greater than zero.");
        }

        if (sideA + sideB <= sideC
                || sideA + sideC <= sideB
                || sideB + sideC <= sideA) {
            throw new IllegalArgumentException(
                    "The given sides do not form a valid triangle.");
        }
    }

    @Override
    public String toString() {
        return "Triangle{"
                + "sideA=" + sideA
                + ", sideB=" + sideB
                + ", sideC=" + sideC
                + '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Triangle)) {
            return false;
        }

        Triangle triangle = (Triangle) other;

        return Double.compare(sideA, triangle.sideA) == 0
                && Double.compare(sideB, triangle.sideB) == 0
                && Double.compare(sideC, triangle.sideC) == 0;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(sideA);
        result = 31 * result + Double.hashCode(sideB);
        result = 31 * result + Double.hashCode(sideC);
        return result;
    }
}