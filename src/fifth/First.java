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

public class First {

    public static void main(String[] args) {
        List<List<Point>> tasks = preparePoints();
        double count = 1;
        JFrame currentFrame;
        for (List<Point> task :
                tasks) {
            XYSeries lagrangePolynomeSeries = new XYSeries("Полином Лагранжа");
            XYSeries newtonPolynomeSeries = new XYSeries("Полином Ньютона");
            for (double x = task.get(0).getX(); x < task.get(task.size() - 1).getX(); x += 0.1) {
                lagrangePolynomeSeries.add(x, lagrangePolynome(task, task.size(), x));
                newtonPolynomeSeries.add(x, newtonPolynome(task, x, task.size()));
            }
            currentFrame = showGraphic(lagrangePolynomeSeries, "полином Лагранжа, задание " + count);
            do {
                if (!currentFrame.isActive()) {
                    currentFrame = showGraphic(newtonPolynomeSeries, "полином Ньютона, задание " + count);
                }
            } while (currentFrame.isActive());

            count++;
        }
    }

    public static JFrame showGraphic(XYSeries series, String title) {
        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(title, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(500, 500);
        frame.show();
        return frame;
    }

    public static double lagrangePolynome(List<Point> points, int n, double x) {
        double result = 0;
        for (int i = 0; i < n; i++) {
            result += points.get(i).getY() * lagrangeMultiplier(points, i, x);
        }
        return result;
    }

    public static double newtonPolynome(List<Point> points, double x, int n) {
        double result = 0, buffer;
        result += points.get(0).getY();
        for (int i = 0; i < n; i++) {
            buffer = 1;
            for (int j = 0; j < i + 1; j++) {
                buffer *= (x - points.get(j).getX());
            }
            if (buffer != 0) {
                buffer *= newtonFunction(points.subList(0, i == n - 1 ? n : i + 2));
            }
            result += buffer;
        }
        return result;
    }

    public static double lagrangeMultiplier(List<Point> points, int i, double x) {
        double result = 1;
        for (int j = 0; j < points.size(); j++) {
            if (j != i) {
                result *= (x - points.get(j).getX()) / (points.get(i).getX() - points.get(j).getX());
            }
        }
        return result;
    }

    public static double newtonFunction(List<Point> points) {
        double result = 0;
        if (points.size() == 2) {
            result += (points.get(1).getY() - points.get(0).getY()) / (points.get(1).getX() - points.get(0).getX());
        } else {
            double firstF = newtonFunction(new ArrayList<>(points.subList(1, points.size())));
            double secondF = newtonFunction(new ArrayList<>(points.subList(0, points.size() - 1)));
            double deltax = points.get(points.size() - 1).getX() - points.get(0).getX();
            result += (firstF - secondF) / deltax;
        }
        return result;
    }

    private static List<List<Point>> preparePoints() {
        List<List<Point>> tasks = new ArrayList<>();
        tasks.add(Arrays.asList(
                new Point(-1.0, 0.86603),
                new Point(0.0, 1.0),
                new Point(1.0, 0.86603),
                new Point(2.0, 0.50),
                new Point(3.0, 0.0),
                new Point(4.0, -0.50)
        ));
        tasks.add(Arrays.asList(
                new Point(-0.9, -0.36892),
                new Point(0.0, 0.0),
                new Point(0.9, 0.36892),
                new Point(1.8, 0.85408),
                new Point(2.7, 1.7856),
                new Point(3.6, 6.3138)
        ));
        tasks.add(Arrays.asList(
                new Point(1.0, 2.4142),
                new Point(1.9, 1.0818),
                new Point(2.8, 0.50953),
                new Point(3.7, 0.11836),
                new Point(4.6, -0.24008),
                new Point(5.5, -0.66818)
        ));
        return tasks;
    }
}

class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
