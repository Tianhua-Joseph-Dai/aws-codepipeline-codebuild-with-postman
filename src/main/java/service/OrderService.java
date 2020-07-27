package service;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import entity.Order;
import entity.Position;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static app.Server.*;

@Path("OrderServices")
@Produces(MediaType.APPLICATION_JSON)
public class OrderService {

    //新增订单
    @Path("orders")
    @POST
    public Order add(Order newOrder) {
        Connection con = null;
        try {
            //添加一个order, 并向Order里添加几个position
            //取出order的数据并填到order表中
            con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            PreparedStatement st = con.prepareStatement("insert into orders(ordernumber)values (?)");
            st.setInt(1, newOrder.getOrderNumber());
            int result = st.executeUpdate();
            //取出order的数据所有position数据并填到order表中
            ArrayList<Position> list = newOrder.getList();
            for (Position p : list) {
                st = con.prepareStatement("insert into positions(positionId,articleName,quantity,price)values (?,?,?,?)");
                st.setInt(1, p.getPositionId());
                st.setString(2, p.getArticleName());
                st.setInt(3, p.getQuantity());
                st.setDouble(4, p.getPrice());
                result = st.executeUpdate();
            }

            con.close();
            return newOrder;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newOrder;
    }

    //删除订单
    @Path("orders/{orderNumber}")
    @DELETE
    public Order remove(@PathParam("orderNumber") int orderID) {
        Order s = null;
        try {
            Connection con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            Statement statement = (Statement) con.createStatement();
            String query = "DELETE FROM orders WHERE ordernumber = " + orderID;
            int results = statement.executeUpdate(query);
            con.close();
            if (results != 1) {
                System.out.println("not found");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return s;
    }

    //查找订单
    @Path("orders/{orderNumber}")
    @GET
    public Order getOrderById(@PathParam("orderNumber") int orderID) {

        try {
            Connection con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            Statement statement = (Statement) con.createStatement();
            String query = "select * from orders where orderNumber=" + orderID;
            ResultSet resultSet = statement.executeQuery(query);
            boolean exist = resultSet.first();
            Order o = new Order();
            if (exist) {
                int orderNumber = resultSet.getInt("ordernumber");
                o.setOrderNumber(orderNumber);
                double totalprice = resultSet.getDouble("totalprice");
                o.setTotalPrice(totalprice);
            }
            statement = (Statement) con.createStatement();
            query = "select * from positions where orderNumber=" + orderID;
            resultSet = statement.executeQuery(query);
            ArrayList<Position> p = new ArrayList<>();
            while (resultSet.next()) {
                Position po = new Position();
                po.setPositionId(resultSet.getInt("positionId"));
                po.setArticleName(resultSet.getString("articleName"));
                po.setQuantity(resultSet.getInt("quantity"));
                po.setPrice(resultSet.getDouble("price"));
                p.add(po);
            }
            o.getList().addAll(p);
            return o;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //更改订单
    @Path("orders/{orderNumber}")
    @PUT
    public Position update(@PathParam("orderNumber") int orderID, int positionId, Position newPositionData) {
        try {
            Connection con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            Statement statement = (Statement) con.createStatement();
            String query = "UPDATE Position SET positionId = " + newPositionData.getPositionId() + ", articleName ="
                    + newPositionData.getArticleName() + ",quantity=" + newPositionData.getQuantity()
                    + ",price=" + newPositionData.getPrice() + "where orderNumber=" + orderID + "and positionId=" + positionId;
            int results = statement.executeUpdate(query);
            con.close();
            if (results != 1) {
                System.out.println("ERROR while modifying data");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }


    //查看所有订单
    @Path("orders")
    @GET
    public Collection<Order> getAllOrders() {
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            Statement statement = (Statement) con.createStatement();
            String query = "select * from checklist;";
            ResultSet rs = statement.executeQuery(query);
            ArrayList<Position> p = new ArrayList<>();
            while (rs.next()) {
                Position po = new Position();
                po.getOrdernumber(rs.getInt("ordernumber"));
                po.setPositionId(rs.getInt("positionId"));
                po.setArticleName(rs.getString("articleName"));
                po.setQuantity(rs.getInt("quantity"));
                po.setPrice(rs.getDouble("price"));
                p.add(po);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }

}
