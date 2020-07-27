package entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {


    @XmlAttribute
    private int orderNumber;
    private double totalPrice;
    private ArrayList<Position> list;

    public Order() {
    }
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    public ArrayList<Position> getList() {
        return list;
    }

    public void setList(ArrayList<Position> list) {
        this.list = list;
    }

    public Order(int orderNumber, float totalPrice, ArrayList<Position> list) {
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.list = list;

    }



    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
