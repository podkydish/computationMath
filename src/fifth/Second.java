package fifth;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Second {
    public static void main(String[] args) {
        List<FunctionData> list = new ArrayList<>();
        list.add(new FunctionData(((x) -> Math.pow(x, 3) - 6.5 * Math.pow(x, 2) + 11 * x - 4), 2, 4));
        list.add(new FunctionData((x -> 3 * Math.cos(Math.PI * x / 8)), 0.5, 3));
        list.add(new FunctionData((x -> Math.exp(-x / 4) * Math.sin(x / 3)), 4, 10));
        list.add(new FunctionData((x -> 8 * x * Math.exp(-1 * Math.pow(x, 2) / 12)), 0, 12));
        List<FunctionData> functions = list;
        List<Point> task;
        List<Integer> splittings = new ArrayList<>(Arrays.asList(3, 4, 8, 10, 16, 32, 64, 256));
        double count = 1;
        int mode;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер подпункта (1 или 2)");
        mode = scanner.nextInt();
        for (FunctionData function : functions) {
            if (mode == 2) {
                List<Point> points = generatePoints(16, function);
                double x, lagrange, newton, funсtionY;
                System.out.println("Уравнение"+ count +"\nВведите значение x в пределах [" +function.getA()+ ";" + function.getB() + "]");
                x = scanner.nextDouble();

                lagrange = First.lagrangePolynome(points, points.size(), x);
                newton = First.newtonPolynome(points, x, points.size());
                funсtionY = function.getFormula().calculate(x);

                System.out.println("Значение полинома Лагранжа: " + lagrange);
                System.out.println("Значение полинома Ньютона: " + newton);
                System.out.println("Значение функции: " + funсtionY + "\n");

            } else if (mode == 1) {
                for (Integer splitting :
                        splittings) {
                    task = generatePoints(splitting, function);
                    XYSeries lagrangePolynomeSeries = new XYSeries("полином Лагранжа");
                    XYSeries newtonPolynomeSeries = new XYSeries("полином Ньютона");
                    XYSeries functionSeries = new XYSeries("function");

                    for (double x = task.get(0).getX(); x < task.get(task.size() - 1).getX(); x += 0.00001) {
                        lagrangePolynomeSeries.add(x, First.lagrangePolynome(task, task.size(), x));
                        newtonPolynomeSeries.add(x, First.newtonPolynome(task, x, task.size()));
                        functionSeries.add(x, function.getFormula().calculate(x));
                    }

                    showGraphic(lagrangePolynomeSeries, "полином лагранжа, задание " + count + ", колличество разбиений " + splitting);
                    showGraphic(newtonPolynomeSeries, "полином ньютона, задание " + count + ", колличество разбиений " + splitting);
                    showGraphic(functionSeries, "график функции, задание " + count + ", колличество разбиений " + splitting);
                }
            }
            count++;
        }
    }
    private static List<Point> generatePoints(int countOfPoints, FunctionData functionData) {
        List<Point> points = new ArrayList<>();
        double step = Math.abs(functionData.getB() - functionData.getA()) / countOfPoints;
        double currentX = functionData.getA();
        for (int i = 0; i < countOfPoints; i++) {
            points.add(new Point(currentX, functionData.getFormula().calculate(currentX)));
            currentX += step;
        }
        return points;
    }

    public static void showGraphic(XYSeries series, String title) {
        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(title, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(500, 500);
        frame.show();
    }
}

class FunctionData {
    private FormulaOneParameter<Double> formula;
    private double a;
    private double b;

    public FormulaOneParameter<Double> getFormula() {
        return formula;
    }
    public double getA() {
        return a;
    }
    public double getB() {
        return b;
    }
    public FunctionData(FormulaOneParameter<Double> formula, double a, double b) {
        this.formula = formula;
        this.a = a;
        this.b = b;
    }
}
