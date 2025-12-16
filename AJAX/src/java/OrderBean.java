
import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vk300
 */
@Named("order")          // достъп: #{count}
@SessionScoped
public class OrderBean implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private static int count =0;
    private double price =10.0;
    private double total;
    private String discountText;
    
    public OrderBean(){
        updateInfo();
    }
    public void increment(){
        count++;
        updateInfo();
        
    }
    
    public void reset(){
        count =0;
        updateInfo();
    }
    
    private void updateInfo() {
        if (count == 0) {
            discountText = "Няма поръчани стоки";
            total = 0;
        } else if (count <= 10) {
            discountText = "Цена на дребно";
            total = count * price;
        } else {
            discountText = "Цена на едро (10% отстъпка)";
            total = count * price * 0.9; // 10% отстъпка
        }
    }
    
    public int getCount() {
        return count;
    }

    public double getTotal() {
        return total;
    }

    public String getDiscountText() {
        return discountText;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price){
        this.price = price;
        updateInfo();
    }
}
