package entity;

import java.math.BigDecimal;

public class Position {
    private int positionId;
    private String articleName;
    private int quantity;
    private double price;



    private int ordernumber;

    public Position(int positionId, String articleName, int quantity, double price) {
        this.positionId = positionId;
        this.articleName = articleName;
        this.quantity = quantity;
        this.price = price;
    }

    public Position() {
    }



    public int getOrdernumber(int ordernumber) {
        return this.ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }
    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
