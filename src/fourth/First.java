package fourth;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.LinkedList;

public class First {
    static int CHOOSE = 1;
    private static final double L_VALUE = 2;
    static final int exponent = 4;
    public static final double EPS = Math.pow(10, (-exponent));

    public static void main(String[] args) {
        LinkedList<RealMatrix> matrixLinkedList = new LinkedList<>();
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{2, 2, -1, 1}, {4, 3, -1, 2}, {8, 5, -3, 4},
                {3, 3, -2, 2}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, 1, -1, 1}, {1, 4, -1, -1}, {-1, -1, 5, 1},
                {1, -1, 1, 3}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{2.8, 2.1, -1.3, 0.3}, {-1.4, 4.5, -7.7, 1.3},
                {0.6, 2.1, -5.8, 2.4}, {3.5, -6.5, 3.2, -7.9}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, 1, 1, 0, 1}, {1, 3, 1, 1, 0}, {1, 1, 5, -1, -1},
                {0, 1, -1, 4, 0}, {1, 0, -1, 0, 4}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, -1, 0, -1, 0, 0}, {-1, 4, -1, 0, -1, 0},
                {0, -1, 4, 0, 0, -1}, {-1, 0, 0, 4, -1, 0}, {0, -1, 0, -1, 4, -1}, {0, 0, -1, 0, -1, 4}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, -1, 0, 0, 0, 0}, {-1, 4, -1, 0, 0, 0}, {0, -1, 4, 0, 0, 0},
                {0, 0, 0, 4, -1, 0}, {0, 0, 0, -1, 4, -1}, {0, 0, 0, 0, -1, 4}}, false));

        LinkedList<RealMatrix> constantsLinkedList = new LinkedList<>();
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{4, 6, 12, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{-2, -1, 0, 1}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 1, 1, 1}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{6, 6, 6, 6, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{0, 5, 0, 6, -2, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{0, 5, 0, 6, -2, 6}));

        LinkedList<RealMatrix> approxLinkedList = new LinkedList<>();
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 1.1, -1, -1}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{-0.8, 0, 0.3, 0.7}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.1, 0.2, -0.1, 0.3}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.5, 0.7, 1.5, 1.7, 1.4}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 2, 1, 2, 1, 2}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.3, 1.5, 0.3, 1.5, 0.2, 1.5}));
        for (int i = 0; i < matrixLinkedList.size(); i++) {
            System.out.println("?????????? 4.1" + (i + 1));
            System.out.println("?????????????????? ??????????");
            speedyDescent(matrixLinkedList.get(i), approxLinkedList.get(i), constantsLinkedList.get(i), CHOOSE);
            System.out.println("\n?????????????????????? ??????????????");
            minimalResiduals(matrixLinkedList.get(i), approxLinkedList.get(i), constantsLinkedList.get(i), CHOOSE);
            System.out.println();
        }
    }

    //?????????????????? ??????????
    public static void speedyDescent(RealMatrix matrix, RealMatrix approximations, RealMatrix constants, int choose) {
        RealMatrix oldApproximations;
        double norma;
        double stepLength;
        int k = 0;
        RealMatrix discrepancy;
        do {
            // ?????????????? ???????????? ?????????????? ???? ?????????????? ????????
            discrepancy = (matrix.multiply(approximations)).subtract(constants);
            // ?????????????????? ?????????????? ???? ?????????????????????? - ?????????? ??????????
            RealMatrix mulMatrixDis = matrix.multiply(discrepancy);
            //
            double divisible = 0;
            double divider = 0;
            for (int i = 0; i < discrepancy.getRowDimension(); i++) {
                divisible += discrepancy.getEntry(i, 0) * discrepancy.getEntry(i, 0);
                divider += discrepancy.getEntry(i, 0) * mulMatrixDis.getEntry(i, 0);
            }
            //??????
            stepLength = divisible / divider;
            oldApproximations = approximations;
            //???????????????????????? ??????????????????????
            approximations = approximations.subtract(discrepancy.scalarMultiply(stepLength));
            //??????????
            norma = norm(approximations, oldApproximations, choose);
            k++;
        } while (norma > EPS);
        System.out.println("???????????????????? ???????????????? " + k);
        System.out.println("x1= " + oldApproximations.getEntry(0, 0) + "; x2=" + oldApproximations.getEntry(1, 0) +
                "; x3=" + oldApproximations.getEntry(2, 0) + "; x4=" + oldApproximations.getEntry(3, 0));
    }

    // ?????????????????????? ??????????????
    public static void minimalResiduals(RealMatrix matrix, RealMatrix approximations, RealMatrix constants, int choose) {
        RealMatrix oldApproximations;
        double norma;
        double stepLength;
        int k = 0;
        RealMatrix discrepancy;
        do {
            // ?????????????? ???????????? ?????????????? ???? ?????????????? ????????
            discrepancy = (matrix.multiply(approximations)).subtract(constants);
            // ?????????????????? ?????????????? ???? ?????????????????????? ?????????????????? ????????????
            RealMatrix mulMatrixDis = matrix.multiply(discrepancy);
            double divisible = 0;   //??????????????
            double divider = 0;     //????????????????
            for (int i = 0; i < discrepancy.getRowDimension(); i++) {
                divisible += mulMatrixDis.getEntry(i, 0) * discrepancy.getEntry(i, 0);
                divider += mulMatrixDis.getEntry(i, 0) * mulMatrixDis.getEntry(i, 0);
            }
            //??????
            stepLength = divisible / divider;
            oldApproximations = approximations;
            //???????????????????????? ??????????????????????
            approximations = approximations.subtract(discrepancy.scalarMultiply(stepLength));
            //??????????
            norma = norm(approximations, oldApproximations, choose);
            k++;
        } while (norma > EPS);
        System.out.println("???????????????????? ???????????????? " + k);
        System.out.println("x1= " + oldApproximations.getEntry(0, 0) + "; x2=" + oldApproximations.getEntry(1, 0) +
                "; x3=" + oldApproximations.getEntry(2, 0) + "; x4=" + oldApproximations.getEntry(3, 0));
    }

    //??????????
    public static double norm(RealMatrix approximations2, RealMatrix oldApproximations, int c) {
        RealMatrix buffNorm;
        buffNorm = approximations2.subtract(oldApproximations);
        int size = approximations2.getRowDimension();
        double num = 0;
        switch (c) {
            case (1) -> {
                for (int i = 0; i < size; ++i) {
                    buffNorm.setEntry(i, 0, Math.abs(buffNorm.getEntry(i, 0)));
                    if (buffNorm.getEntry(i, 0) > num) {
                        num = buffNorm.getEntry(i, 0);
                    }
                }
            }
            case (2) -> {
                for (int i = 0; i < size; ++i) {
                    num += Math.abs(buffNorm.getEntry(i, 0));
                }
            }
            case (3) -> {
                for (int i = 0; i < size; ++i) {
                    num += Math.pow((buffNorm.getEntry(i, 0)), 2 * L_VALUE);
                }
                num = Math.pow(num, 1 / (2 * L_VALUE));
            }
        }
        return num;
    }
}
