import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean(name = "chartBean")
@ViewScoped
public class ChartBean implements Serializable {

    private LineChartModel lineModel;
    private BarChartModel barModel;

    public ChartBean() {
        createLineModel();
        createBarModel();
    }

   
    public void createLineModel() {
        lineModel = new LineChartModel();
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
        lineModel.setTitle("Население на Бургас (линейна)");
        lineModel.setLegendPosition("e");

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Година");

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Население");
        yAxis.setMin(0);
    }

 
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
        barModel.setTitle("Население на Бургас (стълбовидна)");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Година");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Население");
        yAxis.setMin(0);
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }
}
