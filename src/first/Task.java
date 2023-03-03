package first;

import java.util.LinkedList;
import java.util.Scanner;

public class Task {
    public static final double eps = 0.0001;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        LinkedList<Operationable> function = new LinkedList<>();
        LinkedList<Operationable> derivative = new LinkedList<>();
        LinkedList<Points> points = new LinkedList<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Введите параметр а для второго номера:");
        int a = in.nextInt();
        System.out.println("Введите параметр n для второго номера:");
        int n = in.nextInt();

        function.add(x -> Math.sin(x) - 2 * Math.pow(x, 2) + 0.5);
        function.add(x -> Math.pow(x, n) - a);
        function.add(x -> Math.sqrt(1 - Math.pow(x, 2)) - Math.exp(x) + 0.1);
        function.add(x -> Math.pow(x, 6) - 5 * Math.pow(x, 3) - 2);
        function.add(x -> Math.log(x) - (1 / (1 + Math.pow(x, 2))));
        function.add(x -> Math.pow(3, x) - 5 * Math.pow(x, 2) + 1);

        derivative.add(x -> -4 * x + Math.cos(x));
        derivative.add(x -> n * Math.pow(x, n) / n);
        derivative.add(x -> -x / Math.sqrt(1 - Math.pow(x, 2)) - Math.exp(x));
        derivative.add(x -> 6 * Math.pow(x, 5) - 15 * x * x);
        derivative.add(x -> 2 * x / Math.pow(x * x + 1, 2) + 1 / x);
        derivative.add(x -> Math.pow(3, x) * Math.log(3) - 10 * x);

        points.add(new Points(0, 1, 1));
        points.add(new Points(1, 2, 1));
        points.add(new Points(0, 1, 0));
        points.add(new Points(-1, 0, -1));
        points.add(new Points(1, 2, 1));
        points.add(new Points(0, 1, 1));

        for (int i = 0; i < function.size(); i++) {
            System.out.println(ANSI_RED + "Номер примера:" + (i + 1) + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "Метод дихотомии" + ANSI_RESET);
            calculateDichotomy(function.get(i), derivative.get(i), points.get(i).x1, points.get(i).x2);
            System.out.println(ANSI_YELLOW + "Метод Ньютона" + ANSI_RESET);
            calculateNewton(function.get(i), derivative.get(i), points.get(i).newtonNumber);
        }

    }

    private static void calculateNewton(Operationable func, Operationable der, double x) {
        int numberIteration = 0;
        double nextX;
        double approximation;
        do {
            numberIteration++;
            nextX = x - func.calculate(x) / der.calculate(x);
            approximation = Math.abs(nextX - x);
            x = nextX;
            System.out.println("Итерация № " + numberIteration + ";");
            System.out.printf("x =   %.9f\n", x);
            System.out.printf("f(x) =  %.8f\n", func.calculate(x));
            System.out.printf("f`(x) = %.8f\n", der.calculate(x));
        } while (Math.abs(func.calculate(x)) > eps && approximation > eps);
    }

    private static void calculateDichotomy(Operationable func, Operationable der, double x1, double x2) {
        double c, x;
        x = 0;
        int countIterations;
        for (countIterations = 1; Math.abs(x1 - x2) > eps; countIterations++) {
            c = (x1 + x2) / 2;
            if (func.calculate(x1) * func.calculate(c) < 0) x2 = c;
            else if (func.calculate(x1) * func.calculate(c) > 0) {
                x1 = c;
                x = (x1 + x2) / 2;
            } else {
                if (func.calculate(x1) == 0) {
                    x = x1;
                } else {
                    x = c;
                }
                System.out.println("Итерация № " + countIterations + ";");
                System.out.printf("x =   %.9f\n", x);
                System.out.printf("f(x) =  %.8f\n", func.calculate(x));
                System.out.printf("f`(x) = %.8f\n", der.calculate(x));
                break;
            }
            System.out.println("Итерация № " + countIterations + ";");
            System.out.printf("x =   %.9f\n", x);
            System.out.printf("f(x) =  %.8f\n", func.calculate(x));
            System.out.printf("f`(x) = %.8f\n", der.calculate(x));
        }
    }

}

class Points {

    double x1;
    double x2;
    double newtonNumber;

    public Points(double x1, double x2, double newtonNumber) {
        this.x1 = x1;
        this.x2 = x2;
        this.newtonNumber = newtonNumber;
    }

    public Points(double newtonNumber) {
        this.newtonNumber = newtonNumber;
    }

}

interface Operationable {
    double calculate(double x);
}
