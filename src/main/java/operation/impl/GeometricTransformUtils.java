package operation.impl;

import model.GrayImage;

public final class GeometricTransformUtils {

    private GeometricTransformUtils() {
    }

    public static GrayImage applyAffine(GrayImage image, double[][] matrix) {
        double[][] corners = {
                {0, 0},
                {image.getWidth() - 1, 0},
                {0, image.getHeight() - 1},
                {image.getWidth() - 1, image.getHeight() - 1}
        };

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (double[] c : corners) {
            double x = c[0];
            double y = c[1];

            double tx = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2];
            double ty = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2];

            minX = Math.min(minX, tx);
            minY = Math.min(minY, ty);
            maxX = Math.max(maxX, tx);
            maxY = Math.max(maxY, ty);
        }

        int outWidth = (int) Math.ceil(maxX - minX + 1);
        int outHeight = (int) Math.ceil(maxY - minY + 1);

        GrayImage output = new GrayImage(outWidth, outHeight, image.getMaxGray());

        double[][] inverse = invertAffine(matrix);

        for (int yOut = 0; yOut < outHeight; yOut++) {
            for (int xOut = 0; xOut < outWidth; xOut++) {
                double worldX = xOut + minX;
                double worldY = yOut + minY;

                double srcX = inverse[0][0] * worldX + inverse[0][1] * worldY + inverse[0][2];
                double srcY = inverse[1][0] * worldX + inverse[1][1] * worldY + inverse[1][2];

                int nearestX = (int) Math.round(srcX);
                int nearestY = (int) Math.round(srcY);

                if (nearestX >= 0 && nearestX < image.getWidth()
                        && nearestY >= 0 && nearestY < image.getHeight()) {
                    output.setPixel(xOut, yOut, image.getPixel(nearestX, nearestY));
                } else {
                    output.setPixel(xOut, yOut, 0);
                }
            }
        }

        return output;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        double[][] r = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                r[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    r[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return r;
    }

    public static double[][] translation(double tx, double ty) {
        return new double[][]{
                {1, 0, tx},
                {0, 1, ty},
                {0, 0, 1}
        };
    }

    public static double[][] scale(double sx, double sy) {
        return new double[][]{
                {sx, 0, 0},
                {0, sy, 0},
                {0, 0, 1}
        };
    }

    public static double[][] rotationDegrees(double angleDegrees) {
        double rad = Math.toRadians(angleDegrees);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new double[][]{
                {cos, -sin, 0},
                {sin,  cos, 0},
                {0,    0,   1}
        };
    }

    public static double[][] shear(double shx, double shy) {
        return new double[][]{
                {1, shx, 0},
                {shy, 1, 0},
                {0, 0, 1}
        };
    }

    public static double[][] aroundCenter(GrayImage image, double[][] transform) {
        double cx = (image.getWidth() - 1) / 2.0;
        double cy = (image.getHeight() - 1) / 2.0;

        return multiply(
                translation(cx, cy),
                multiply(transform, translation(-cx, -cy))
        );
    }

    private static double[][] invertAffine(double[][] m) {
        double a = m[0][0];
        double b = m[0][1];
        double c = m[0][2];
        double d = m[1][0];
        double e = m[1][1];
        double f = m[1][2];

        double det = a * e - b * d;
        if (Math.abs(det) < 1e-10) {
            throw new IllegalArgumentException("Transformação não invertível.");
        }

        double invDet = 1.0 / det;

        return new double[][]{
                { e * invDet, -b * invDet, (b * f - e * c) * invDet},
                {-d * invDet,  a * invDet, (d * c - a * f) * invDet},
                {0, 0, 1}
        };
    }
}