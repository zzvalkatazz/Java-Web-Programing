/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartOptions;
import service.ReservationService;

@ManagedBean(name="adminStatsBean")
@ViewScoped
public class AdminStatsBean implements Serializable{
    @ManagedProperty(value ="#{loginBean}")
    private LoginBean loginBean;
    private long totalQueries;
    private final ReservationService reservationService = new ReservationService();
    private BarChartModel statusChart;
    
public void load() {
    buildChart();
}

    
  public void refresh(){
      buildChart();
  }
  private void buildChart(){
      statusChart = new BarChartModel();
      
      if(loginBean == null || !loginBean.isAdmin()){
        totalQueries = 0;
        ChartData data = new ChartData();
        statusChart.setData(data);
        return;
      }
      long approved = reservationService.countByStatus("APPROVED");
      long pending = reservationService.countByStatus("PENDING");
      long rejected = reservationService.countByStatus("REJECTED");
      long canceled = reservationService.countByStatus("CANCELED");
      
       totalQueries = reservationService.countAll();
      ChartData data = new ChartData();
      BarChartDataSet ds = new BarChartDataSet();
      
      ds.setLabel("Брой резервации");
      
      List<Number> values = new ArrayList<>();
      values.add(pending);
      values.add(approved);
      values.add(rejected);
      values.add(canceled);
      ds.setData(values);
      
      ds.setBackgroundColor(Arrays.asList(
           "#d4a100",  
            "#2e7d32",
            "#c62828",   
            "#ffa500"   
      ));
      
      data.addChartDataSet(ds);
      data.setLabels(Arrays.asList("PENDING", "APPROVED", "REJECTED", "CANCELED"));
      
    statusChart.setData(data);
      
     BarChartOptions options = new BarChartOptions();

CartesianScales scales = new CartesianScales();
CartesianLinearAxes yAxis = new CartesianLinearAxes();

CartesianLinearTicks ticks = new CartesianLinearTicks();
ticks.setBeginAtZero(true);   
ticks.setStepSize(1);         
ticks.setPrecision(0);        
ticks.setMin(0);              

yAxis.setTicks(ticks);
scales.addYAxesData(yAxis);

options.setScales(scales);
statusChart.setOptions(options);

  }
  public BarChartModel getStatusChart(){
      return statusChart;
  }
    public void setStatusChart(BarChartModel statusChart){
       this.statusChart=statusChart;
  }
  
  public void setLoginBean(LoginBean loginBean){
      this.loginBean = loginBean;
  }
  
  public long getTotal(){
      return totalQueries;
  }
}