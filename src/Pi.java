public class Pi {
    private static double f(double x) { // Задаём и находим функцию f(x)= sin(x)
        return Math.sin(x);
    }

    private static double df(double x) { // Задаем и находим производную функции
        return Math.cos(x);
    }

    public static void main(String[] args) {
        System.out.println("Вычисление числа пи с помощью sin(x)=0");
        double e = 0.00000001;
        double x = 2;
        int numberIteration;
        for (numberIteration = 1; Math.abs(f(x)) > e; numberIteration++) { // Задаём условия для нахождения приближённого значения
            if (df(x) == 0) {
                break;
            }
            x = x - f(x) / df(x);   // Формула нахождения следующего х
            System.out.println("Итерация № " + numberIteration + ";");
            System.out.printf("x =   %.9f\n", x);
            System.out.printf("f(x) =  %.8f\n", f(x));
            System.out.printf("f`(x) = %.8f\n", df(x));
        }
        System.out.println("Точность достигается");
    }

}
