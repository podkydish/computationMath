package second;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    private static int task;
    private static double minRange;
    private static double maxRange;
    private static double x, a, b, c, A = 2;
    private static double lambda;

    private static double eps;


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Введите параметр k: ");
        int k = in.nextInt();
        eps = Math.pow(10, -k);
        System.out.println("Задание 1.1:");
        task = 1;
        minRange = -3;
        maxRange = 1;
        x = -(1 / 3);
        getResult();
        System.out.println("Задание 1.2:");
        task = 2;
        minRange = 1;
        maxRange = 2;
        x = 1;
        getResult();
        System.out.println("Задание 1.3:");
        task = 3;
        minRange = 2.5;
        maxRange = 5;
        x = 0;
        getResult();
        System.out.println("Задание 1.4:");
        task = 4;
        minRange = 0;
        maxRange = 1;
        x = 0;
        getResult();
        System.out.println("Задание 1.5:");
        task = 5;
        minRange = 0.5;
        maxRange = 1;
        x = 1;
        getResult();
        System.out.println("Задание 1.6:");
        task = 6;
        minRange = 0;
        maxRange = 2;
        x = -0.5;
        getResult();


        LinkedList<FuncSec> funcSec = new LinkedList<>();
        LinkedList<Points> pn = new LinkedList<>();
        LinkedList<FuncSec> funcThird = new LinkedList<>();

        funcSec.add((x, y, a, alpha, betta) -> Math.tan(x * y + a) - Math.pow(x, 2));
        funcSec.add((x, y, a, alpha, betta) -> Math.pow(x, 2) * alpha + Math.pow(y, 2) * betta - 1);
        funcSec.add((x, y, a, alpha, betta) -> (1 / Math.pow(Math.cos(x * y + a), 2)) * y - 2 * x);
        funcSec.add((x, y, a, alpha, betta) -> (1 / Math.pow(Math.cos(x * y + a), 2)) * x);
        funcSec.add((x, y, a, alpha, betta) -> 2 * x * alpha);
        funcSec.add((x, y, a, alpha, betta) -> 2 * y * betta);

        pn.add(new Points(0.2, 0.6, 2, 0.8, 0.5));
        pn.add(new Points(0.4, 0.8, 2, -0.3, 0.6));
        pn.add(new Points(0.3, 0.2, 3, 1, 0.5));
        pn.add(new Points(0, 0.6, 2, 0.6, 0.6));


        // функция
        funcThird.add((x, y, a, alpha, betta) -> Math.pow(x, 2) + Math.pow(y, 2) - 2);
        funcThird.add(((x, y, a, alpha, betta) -> Math.exp(x - 1) + Math.pow(y, 3) - 2));
        // производная по x
        funcThird.add((x, y, a, alpha, betta) -> 2 * x);
        funcThird.add((x, y, a, alpha, betta) -> 2 * y);
        // производная по y
        funcThird.add((x, y, a, alpha, betta) -> Math.exp(x - 1));
        funcThird.add((x, y, a, alpha, betta) -> 3 * Math.pow(y, 3));

        LinkedList<Points> pn2 = new LinkedList<>();
        pn2.add(new Points(0, 0, 0, 1, 1));


        for (int i = 0; i < 4; i++) {
            System.out.println("\n№ 2.1." + (i + 1));
            newton(funcSec.get(0), funcSec.get(1), funcSec.get(2), funcSec.get(3), funcSec.get(4),
                    funcSec.get(5), pn.get(i));
        }

        System.out.println("\n\n\n№2.2");
        newton(funcThird.get(0), funcThird.get(1), funcThird.get(2), funcThird.get(3), funcThird.get(4),
                funcThird.get(5), pn2.get(0));

    }

    public static double function11(double x) {
        return Math.pow(x, 3) + Math.pow(x, 2) - x + 0.5;
    }

    public static double derivative11(double x) {
        return 3 * Math.pow(x, 2) + 2 * x - 1;
    }

    public static double function12(double x) {
        return (Math.exp(x) / A) - x - 1;
    }

    public static double derivative12(double x) {
        return Math.exp(x) / A - 1;
    }

    public static double function13(double x) {
        return Math.pow(x, 3) - 20 * x + 1;
    }

    public static double derivative13(double x) {
        return 3 * Math.pow(x, 2) - 20;
    }

    public static double function14(double x) {
        return Math.pow(2, x) + Math.pow(x, 2) - 2;
    }

    public static double derivative14(double x) {
        return Math.log(2) * Math.pow(2, x) + 2 * x;
    }

    public static double function15(double x) {
        return x * Math.log(x + 2) - 1 + Math.pow(x, 2);
    }

    public static double derivative15(double x) {
        return Math.log(x + 2) + (x / (x + 2)) + 2 * x;
    }

    public static double function16(double x) {
        double A = 2;
        return Math.pow(x, 3) / A - 2 * Math.cos(x);
    }

    public static double derivative16(double x) {
        return 1.5 * Math.pow(x, 2) + 2 * Math.sin(x);
    }

    public static double searchX(double minRange, double maxRange, double x) {
        a = currentFunction(minRange);
        b = currentFunction(maxRange);
        c = currentDerivative(x);
        return a >= b && a >= c ? minRange : b >= a && b >= c ? maxRange : x;
    }

    public static double getLambda(double x) {
        return 1. / currentDerivative(x);
    }

    public static double currentFunction(double x) {
        switch (task) {
            case (1):
                return function11(x);
            case (2):
                return function12(x);
            case (3):
                return function13(x);
            case (4):
                return function14(x);
            case (5):
                return function15(x);
            case (6):
                return function16(x);

        }
        return x;
    }

    public static double currentDerivative(double x) {
        return switch (task) {
            case (1) -> derivative11(x);
            case (2) -> derivative12(x);
            case (3) -> derivative13(x);
            case (4) -> derivative14(x);
            case (5) -> derivative15(x);
            case (6) -> derivative16(x);
            default -> x;
        };
    }

    private static double approximation(double lambda, double x, double eps) {
        double x0;
        int iterations = 0;
        do {
            x0 = x;
            x = x - lambda * currentFunction(x);
            iterations++;
        } while (Math.abs(x - x0) >= eps);
        System.out.println("Количество итераций: " + iterations);
        return x0;
    }

    private static void getResult() {
        double result;
        x = searchX(minRange, maxRange, x);
        lambda = getLambda(x);
        result = approximation(lambda, x, eps);
        System.out.println("Ответ: " + result);
    }


    interface FuncSec {
        double calculate(double x, double y, double a, double alpha, double betta);
    }

    static class Points {
        double x, y, a, min, max, alpha, betta;

        public Points(double a, double alpha, double betta, double x, double y) {
            this.a = a;
            this.alpha = alpha;
            this.betta = betta;
            this.x = x;
            this.y = y;
        }
    }

    public static void newton(FuncSec f, FuncSec f2, FuncSec a, FuncSec b, FuncSec c, FuncSec d, Points pn) {
        int i = 0;
        double x1 = pn.x;
        System.out.println("x1: " + x1);
        double y1 = pn.y;
        System.out.println("x2: " + y1);
        double x, y;
        double a2 = pn.a;
        double alpha = pn.alpha;
        double betta = pn.betta;
        do {
            x = x1;
            y = y1;

            double function1 = f.calculate(x, y, a2, alpha, betta);
            double function2 = f2.calculate(x, y, a2, alpha, betta);

            double functionA = a.calculate(x, y, a2, alpha, betta);
            double functionB = b.calculate(x, y, a2, alpha, betta);
            double functionC = c.calculate(x, y, a2, alpha, betta);
            double functionD = d.calculate(x, y, a2, alpha, betta);

            double detA1 = (function1 * functionB) - (function2 * functionD);
            double detA2 = (functionA * function2) - (function1 * functionC);
            double detJ = (functionA * functionD) - (functionB * functionC);

            x1 = x - (detA1 / detJ);
            y1 = y - (detA2 / detJ);
            i++;
        } while (Math.sqrt(Math.pow(f.calculate(x1, y1, a2, alpha, betta), 2) + Math.pow(f2.calculate(x1, y1, a2, alpha, betta), 2)) > eps &&
                Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)) > eps);
        System.out.println("Ответ: (" + x1 + "; " + y1 + ")");
        System.out.println("Количество итераций: " + i);
    }
}