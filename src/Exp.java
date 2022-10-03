public class Exp {
    private static double f(double x, double b) { // Задаём и находим функцию f(x)= ln(x)
        return Math.log(x) -b;
    }

    private static double df(double x, double b) { // Задаем и находим производную функции
        return 1/x;
    }

    public static void main(String[] args) {
        System.out.println("Вычисление числа пи с помощью lnx =1");
        double e = 0.00000001;
        double x = 3;
        double b = 1;
        int numberIteration;
        for (numberIteration = 1; Math.abs(f(x,b)) > e; numberIteration++) { // Задаём условия для нахождения приближённого значения
            if (df(x,b) == 0) {
                break;
            }
            x = x - f(x,b) / df(x,b);   // Формула нахождения следующего х
            System.out.println("Итерация № " + numberIteration + ";");
            System.out.printf("x =   %.9f\n", x);
            System.out.printf("f(x) =  %.8f\n", f(x,b));
            System.out.printf("f`(x) = %.8f\n", df(x,b));
        }
        System.out.println("Точность достигается");
    }
}
