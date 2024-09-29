import java.util.Random;

public class Main {
    
    interface FormulaStrategy {
        double calculate(double x);
    }
    
    static class FormulaEight implements FormulaStrategy {
        @Override
        public double calculate(double x) {
            return Math.pow(Math.E, Math.pow(2 * Math.cbrt(x), Math.log10(Math.abs(x))));
        }
    }
    
    static class FormulaSpecial implements FormulaStrategy {
        @Override
        public double calculate(double x) {
            return Math.asin(Math.pow(Math.E, Math.cbrt(-Math.pow(Math.E, x))));
        }
    }

    static class FormulaDefault implements FormulaStrategy {
        @Override
        public double calculate(double x) {
            return Math.tan(Math.cbrt(Math.pow(Math.E, Math.pow(2 / (x + 1), 2))));
        }
    }
    
    static class FormulaFactory {
        public static FormulaStrategy getStrategy(int z) {
            if (z == 8) {
                return new FormulaEight();
            } else if (z == 4 || z == 6 || z == 12 || z == 14 || z == 22) {
                return new FormulaSpecial();
            } else {
                return new FormulaDefault();
            }
        }
    }
    
    static class ArrayManager {
        
        public static int[] createZArray() {
            int[] z = new int[11];
            for (int i = 0, value = 4; i < z.length; i++, value += 2) {
                z[i] = value;
            }
            return z;
        }

        public static float[] createXArray() {
            Random random = SingletonRandom.getInstance();
            float[] x = new float[12];
            for (int i = 0; i < x.length; i++) {
                x[i] = -8.0f + random.nextFloat() * (13.0f - (-8.0f));
            }
            return x;
        }

        public static double[][] create2DArray(int[] z, float[] x) {
            double[][] result = new double[11][12];
            for (int i = 0; i < z.length; i++) {
                FormulaStrategy strategy = FormulaFactory.getStrategy(z[i]);
                for (int j = 0; j < x.length; j++) {
                    result[i][j] = strategy.calculate(x[j]);
                }
            }
            return result;
        }

        public static void print2DArray(double[][] array) {
            for (double[] row : array) {
                for (double value : row) {
                    System.out.printf("%.2f ", value);
                }
                System.out.println();
            }
        }
    }

    static class SingletonRandom {
        private static Random instance;

        private SingletonRandom() {}

        public static Random getInstance() {
            if (instance == null) {
                instance = new Random();
            }   
            return instance;
        }
    }

    public static void main(String[] args) {
        int[] z = ArrayManager.createZArray();
        
        float[] x = ArrayManager.createXArray();
        
        double[][] z_2D = ArrayManager.create2DArray(z, x);
    
        ArrayManager.print2DArray(z_2D);
    }
}
