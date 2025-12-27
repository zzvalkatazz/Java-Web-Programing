/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartModel;

import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import service.ReservationService;
@ManagedBean(name = "adminTimelineBean")
@ViewScoped
public class AdminTimelineBean implements Serializable{
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
     private final ReservationService reservationService = new ReservationService();
     
     private LineChartModel timelineChart;
     private int days = 30;
     
     public void load(){
         buildChart();
     }
     
     public void refresh(){
         buildChart();
     }
     
     private void buildChart(){
         timelineChart = new LineChartModel();
         
         if(loginBean == null || loginBean.getLoggedUser() == null){
            timelineChart.setData(new ChartData());
             return;
         }
         
         List<Object[]> rows = reservationService.countByDayLastNDays(days);
         
         Map<LocalDate, Integer> map =new HashMap<>();
         for(Object [] r : rows){
             Date d =(Date) r[0];
             Object cObj = r[1];
             
             int c;
             if(cObj instanceof BigInteger) c = ((BigInteger) cObj).intValue();
             else if (cObj instanceof Number) c =((Number) cObj).intValue();
             else c = Integer.parseInt(String.valueOf(cObj));
             
             map.put(d.toLocalDate(),c);
         }
         LocalDate start = LocalDate.now();
         LocalDate end = LocalDate.now().plusDays(days);
         
         DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM");
         List<String> labels;
        labels = new ArrayList<>();
         List<Object> values = new ArrayList<>();
         
         for(LocalDate d = start; !d.isAfter(end); d= d.plusDays(1)){
             labels.add(d.format(fmt));
             values.add(map.getOrDefault(d, 0));
         }
         ChartData data = new ChartData();
         
         LineChartDataSet ds = new LineChartDataSet();
         ds.setLabel("Резервации спрямо ден");
         ds.setData(values);
         ds.setFill(false);
         
         data.addChartDataSet(ds);
         data.setLabels(labels);
         
         timelineChart.setData(data);
         
         LineChartOptions options = new LineChartOptions();
         CartesianScales scales = new CartesianScales();
         
         CartesianLinearAxes yAxis = new CartesianLinearAxes();
         CartesianLinearTicks ticks = new CartesianLinearTicks();
         ticks.setBeginAtZero(true);
         ticks.setMin(0);
         ticks.setStepSize(1);
         ticks.setPrecision(0);
         
         yAxis.setTicks(ticks);
         scales.addXAxesData(yAxis);
         
         options.setScales(scales);
         timelineChart.setOptions(options);
     }
     public LineChartModel getTimelineChart(){
         return timelineChart;
     }
     public int getDays(){
         return days;
     }
     
     public void setDays(int days){
         this.days = days;
     }
     
     public void setLoginBean(LoginBean loginBean){
         this.loginBean = loginBean;
     }
}
