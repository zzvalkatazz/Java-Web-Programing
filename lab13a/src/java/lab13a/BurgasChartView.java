/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab13a;



import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.*;

@ManagedBean(name = "chartBean")
@ViewScoped
public class BurgasChartView implements Serializable {

    private CartesianChartModel lineModel;
    private BarChartModel barModel;

    public ChartBean() {
        createLineModel();
        createBarModel();
    }

    // LINE CHART (линейна диаграма)
    public void createLineModel() {
        lineModel = new CartesianChartModel();
        LineChartSeries series = new LineChartSeries();
        series.setLabel("Население на Бургас");

        series.set(1887, 5700);
        series.set(1910, 14900);
        series.set(1934, 36200);
        series.set(1946, 44400);
        series.set(1956, 79100);
        series.set(1965, 106100);
        series.set(1975, 148700);
        series.set(1985, 182300);
        series.set(1992, 195800);
        series.set(2001, 192300);
        series.set(2011, 200200);
        series.set(2013, 219200);
        series.set(2015, 206400);

        lineModel.addSeries(series);
    }

    // BAR CHART (стълбовидна диаграма)
    public void createBarModel() {
        barModel = new BarChartModel();
        ChartSeries pop = new ChartSeries();
        pop.setLabel("Население");

        pop.set("1887", 5700);
        pop.set("1910", 14900);
        pop.set("1934", 36200);
        pop.set("1946", 44400);
        pop.set("1956", 79100);
        pop.set("1965", 106100);
        pop.set("1975", 148700);
        pop.set("1985", 182300);
        pop.set("1992", 195800);
        pop.set("2001", 192300);
        pop.set("2011", 200200);
        pop.set("2013", 219200);
        pop.set("2015", 206400);

        barModel.addSeries(pop);
        barModel.setLegendPosition("ne");
    }

    public CartesianChartModel getLineModel() {
        return lineModel;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }
}


