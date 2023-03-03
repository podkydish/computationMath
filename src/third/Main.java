package third;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;

public class Main {
    final static double EPS = Math.pow(10, -15);
    final static int L = 2;
    static final String[] normList = new String[]{"||X||∞", "||X||₁", "||X||₂ₗ"};
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {

        System.out.println(ANSI_RED + "№1. Метод Гаусса" + ANSI_RESET);

        List<double[][]> firstNumMatrix = new LinkedList<>();

        firstNumMatrix.add(
                new double[][]{{2, 2, -1, 1, 4}, {4, 3, -1, 2, 6}, {8, 5, -3, 4, 12}, {3, 3, -2, 2, 6}}
        );
        firstNumMatrix.add(
                new double[][]{{1, 7, -9, -8, -7}, {-3, -18, 23, 28, 5}, {0, -3, 6, -1, 8}, {-1, -1, 1, 18, -29}}
        );
        firstNumMatrix.add(
                new double[][]{{3, -3, 7, -4, 0}, {-6, 9, -21, 9, 9}, {9, -12, 30, -22, -2}, {6, 0, 6, -31, 37}}
        );
        firstNumMatrix.add(
                new double[][]{{9, -5, -6, 3, -8}, {1, -7, 1, 0, 38}, {3, -4, 9, 0, 47}, {6, -1, 9, 8, -8}}
        );
        firstNumMatrix.add(
                new double[][]{{-6, -5, -3, -8, 101}, {5, -1, -5, -4, 51}, {-6, 0, 5, 5, -53}, {-7, -2, 8, 5, -63}}
        );

        for (double[][] eq : firstNumMatrix) {
            gaussMethod(eq);
        }

        System.out.println(ANSI_RED + "\n№2. Метод простой итерации" + ANSI_RESET);

        List<double[][]> secondNumMatrix = new LinkedList<>();
        secondNumMatrix.add(
                new double[][]{{12, -3, -1, 3, -31}, {5, 20, 9, 1, 90}, {6, -3, -21, -7, 119}, {8, -7, 3, -27, 71}}
        );
        secondNumMatrix.add(
                new double[][]{{28, 9, -3, -7, -159}, {-5, 21, -5, -3, 63}, {-8, 1, -16, 5, -45}, {0, -2, 5, 8, 24}}
        );
        secondNumMatrix.add(
                new double[][]{{21, 1, -8, 4, -119}, {-9, -23, -2, 4, 79}, {7, -1, -17, 6, -24}, {8, 8, -4, -26, -52}}
        );
        secondNumMatrix.add(
                new double[][]{{14, -4, -2, 3, 38}, {-3, 23, -6, -9, -195}, {-7, -8, 21, -5, -27}, {-2, -2, 8, 18, 142}}
        );

        for (double[][] eq : secondNumMatrix) {
            for (int norm = 1; norm <= 3; norm++) {
                System.out.println(ANSI_YELLOW +"Количество итераций для нормы " + normList[norm - 1] + ": " + simpleIteration(eq, norm) + ANSI_RESET);
            }
        }

        System.out.println(ANSI_RED + "\n\n\n№2. Метод Зейделя" + ANSI_RESET);
        for (double[][] matrix : secondNumMatrix) {
            for (int norm = 1; norm <= 3; norm++) {
                System.out.println(ANSI_YELLOW +"\nКоличество итераций для нормы " + normList[norm - 1] + ": " + zeidelMethod(matrix, norm) + ANSI_RESET);
            }
        }
    }

    public static void gaussMethod(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            double max = matrix[i][i];
            int maxRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(max)) {
                    max = matrix[j][i];
                    maxRow = j;
                }
            }
            for (int j = i; j < n + 1; j++) {
                double tmp = matrix[maxRow][j];
                matrix[maxRow][j] = matrix[i][j];
                matrix[i][j] = tmp;
            }
            for (int j = i + 1; j < n; j++) {
                double c = -matrix[j][i] / matrix[i][i];
                for (int k = i; k < n + 1; k++) {
                    if (i == k) {
                        matrix[j][k] = 0;
                    } else {
                        matrix[j][k] += c * matrix[i][k];
                    }
                }
            }
        }
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = matrix[i][n] / matrix[i][i];
            for (int k = i - 1; k >= 0; k--) {
                matrix[k][n] -= matrix[k][i] * x[i];
            }
        }
        for (double currX : x) {
            System.out.printf("%.2f\t", currX);
        }
    }

    public static int simpleIteration(double[][] matrix, int norm) {
        int amountIters = 0;
        double[] newX = new double[matrix[0].length - 1];
        double currentEps;
        double[] oldX = new double[newX.length]; // начальные значения равные нулю

        double[][] matrixB = new double[matrix.length][matrix[0].length - 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    matrixB[i][j] = 0;
                } else {
                    matrixB[i][j] = -matrix[i][j] / matrix[i][i]; // диагональ - нули, остальное в левую часть и делим на диагональный элемент
                }
            }
        }

        do {
            for (int i = 0; i < matrix.length; i++) {
                double sum = 0;
                for (int j = 0; j < matrix.length; j++) {
                    sum += matrixB[i][j] * oldX[j];
                }
                newX[i] = sum + matrix[i][matrix[0].length - 1] / matrix[i][i];
            }
            currentEps = findNorm(norm, newX, oldX);
            System.arraycopy(newX, 0, oldX, 0, oldX.length);
            amountIters++;
        } while (currentEps > EPS);
        if (norm == 1) {
            for (double x : newX) {
                System.out.printf("%.2f\t", x);
            }
        }
        return amountIters;
    }

    public static int zeidelMethod(double[][] matrix, int normNumber) {
        int size = matrix.length;
        int amountIters = 0;
        double currentEps;
        double[] previousVariableValues = new double[size];
        double[] currentVariableValues;
        for (int i = 0; i < size; i++) {
            previousVariableValues[i] = 0.0;
        }
        do {
            // Введем вектор значений неизвестных на текущем шаге
            currentVariableValues = new double[size];
            // Посчитаем значения неизвестных на текущей итерации
            for (int i = 0; i < size; i++) {
                currentVariableValues[i] = matrix[i][size];
                // Вычитаем сумму по всем отличным от i-ой неизвестным
                for (int j = 0; j < size; j++) {
                    // При j < i можем использовать уже посчитанные
                    // на этой итерации значения неизвестных
                    if (j < i) {
                        currentVariableValues[i] -= matrix[i][j] * currentVariableValues[j];
                    }
                    // При j > i используем значения с прошлой итерации
                    if (j > i) {
                        currentVariableValues[i] -= matrix[i][j] * previousVariableValues[j];
                    }
                }
                // Делим на коэффициент при i-ой неизвестной
                currentVariableValues[i] /= matrix[i][i];
            }
            amountIters++;
            // Переходим к следующей итерации, так
            // что текущие значения неизвестных
            // становятся значениями на предыдущей итерации
            currentEps = findNorm(normNumber, currentVariableValues, previousVariableValues);
            previousVariableValues = currentVariableValues;
        } while (currentEps > EPS);

        if (normNumber == 1) {
            for (int i = 0; i < size; i++) {
                System.out.printf("%.2f\t", previousVariableValues[i]);
            }
        }
        return amountIters;
    }

    public static double findNorm(int numOfNorm, double[] currentVariableValues, double[] previousVariableValues) {
        double norm = 0;
        int size = currentVariableValues.length;
        switch (numOfNorm) {
            // Для нормы ∞
            case (1) -> {
                double[] values = new double[size];
                for (int i = 0; i < size; ++i) {
                    values[i] = Math.abs(currentVariableValues[i] - previousVariableValues[i]);
                }
                DoubleSummaryStatistics stat = Arrays.stream(values).summaryStatistics();
                return stat.getMax();
            }
            // Для нормы ||X||₁
            case (2) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.abs(currentVariableValues[i] - previousVariableValues[i]);
                }
                return norm;
            }
            // Для нормы ||X||₂ₗ
            case (3) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.pow(currentVariableValues[i] - previousVariableValues[i], L * 2);
                }
                return Math.pow(norm, 1. / (L * 2));
            }
        }
        return 0;
    }
}