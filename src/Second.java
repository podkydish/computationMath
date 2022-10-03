public class Second {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private static double fa(double x) {
        return Math.sin(x) - 2 * x * x + 0.5;
    }

    private static double dfa(double x) {
        return -4 * x + Math.cos(x);
    }

    public static void main(String[] args) {
        calculateNewton();
        //Метод дихотомии
        calculateDichotomy();
    }

    private static void calculateNewton() {
        System.out.println(ANSI_RED + "Метод половинного деления: " + ANSI_RESET);
        double e = 0.0001;
        double x = 3;
        int numberIteration;
        for (numberIteration = 1; Math.abs(fa(x)) > e; numberIteration++) {

            if (dfa(x) == 0) {
                break;
            }
            x = x - fa(x) / dfa(x);
            System.out.println("Итерация № " + numberIteration + ";");
            System.out.printf("x =   %.9f\n", x);
            System.out.printf("f(x) =  %.8f\n", fa(x));
            System.out.printf("f`(x) = %.8f\n", dfa(x));
        }
    }

    private static void calculateDichotomy() {
        double a, b, c, x;
        double e = 0.0001;
        System.out.println(ANSI_RED + "\n\nМетод дихотомии: " + ANSI_RESET);

        a = 0;
        b = 1;
        x = 0;
        int countIterations;
        for (countIterations = 1; Math.abs(a - b) > e; countIterations++) {
            c = (a + b) / 2;
            if (fa(a) * fa(c) <= 0) b = c;
            else {
                a = c;
                x = (a + b) / 2;
            }
            System.out.println("Итерация № " + countIterations + ";");
            System.out.printf("x =   %.9f\n", x);
            System.out.printf("f(x) =  %.8f\n", fa(x));
            System.out.printf("f`(x) = %.8f\n", dfa(x));
        }
    }

}

