package service;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import entity.GrouponEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.*;

import static app.Server.*;

@Path("GroupOnEvents")
@Produces(MediaType.APPLICATION_JSON)
public class EventService {


    //新增一个事件
    @Path("events")
    @POST
    public GrouponEvent add(GrouponEvent newGroupOnEvent) {
        try {
            Connection con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            PreparedStatement st = con.prepareStatement("insert into grouponevent(eventid,name," +
                    "description,date,pickupaddress)" + "values (?,?,?,?,?)");
            st.setInt(1, newGroupOnEvent.getEventId());
            st.setString(2, newGroupOnEvent.getName());
            st.setString(3, newGroupOnEvent.getDescription());
            st.setString(4, newGroupOnEvent.getDate());
            st.setString(5, newGroupOnEvent.getPickupAddress());
            int result = st.executeUpdate();
            con.close();
            return newGroupOnEvent;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return newGroupOnEvent;
    }

    //删除事件通过ID
    @Path("events/{eventId}")
    @DELETE
    public GrouponEvent remove(@PathParam("eventId") int eventID) {
        GrouponEvent g = null;
        try {
            Connection con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            Statement statement = (Statement) con.createStatement();
            String query = "DELETE FROM grouponevent WHERE eventid= " + eventID;
            int results = statement.executeUpdate(query);
            con.close();
            if (results != 1) {
                System.out.println("This event is not available");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return g;
    }

    // 查询一个事件通过id
    @Path("events/{eventId}")
    @GET
    public GrouponEvent geteventById(@PathParam("eventId") int eventID) {
        Connection newConnection = null;
        try {
            newConnection = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            Statement statement = (Statement) newConnection.createStatement();
            String query = "select * from grouponevent where eventid=" + eventID;
            ResultSet resultSet = statement.executeQuery(query);
            boolean exist = resultSet.first();
            if (exist) {
                GrouponEvent event = new GrouponEvent();
                String name = resultSet.getString("name");
                event.setName(name);
                String description = resultSet.getString("description");
                event.setDescription(description);
                String date = resultSet.getString("date");
                event.setDate(date);
                String pickupAddress = resultSet.getString("pickupaddress");
                event.setPickupAddress(pickupAddress);
                return event;
            } else {
                System.out.println("No result found!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
        // Methode annotieren und ausimplementieren und folgende "throw"-Anweisung löschen!
        //throw new IllegalStateException("method 'getStudentById' needs to be implemented first");

    }

    //通过id更改一个事件
    @Path("events/{eventId}")
    @PUT
    public GrouponEvent update(@PathParam("eventId") int eventID, GrouponEvent newEventData) {
        if (eventID != newEventData.getEventId())
            System.out.println("The event number mustn't be modified!");
        if ("".equals(newEventData.getName()) || newEventData.getName() == null
                || "".equals(newEventData.getDescription()) || newEventData.getDescription() == null
                || "".equals(newEventData.getDate()) || newEventData.getDate() == null
                || "".equals(newEventData.getPickupAddress()) || newEventData.getPickupAddress() == null)
            System.out.println("Name, description, datum, pickup address cannot be empty");
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            Statement statement = (Statement) con.createStatement();
            String query = "UPDATE grouponevent SET name = " + newEventData.getName() +
                    ", description = " + newEventData.getDescription() +
                    ", date = " + newEventData.getDate() + ", pickupaddress = " +
                    "" + newEventData.getPickupAddress() +
                    " WHERE evenid = " + eventID;
            int results = statement.executeUpdate(query);
            con.close();
            if (results != 1) {
                System.out.println("ERROR while modifying data");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newEventData;
    }

    @Path("events")
    @GET
    public Collection<GrouponEvent> getAllEvents(String query) {
        Connection con = null;
        List<GrouponEvent> groupon = new LinkedList<>();
        try {
            con = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
            Statement statement = (Statement) con.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                GrouponEvent g = new GrouponEvent();
                g.setEventId(result.getInt("eventid"));
                g.setName(result.getString("name"));
                g.setDescription(result.getString("description"));
                g.setDate(result.getString("date"));
                g.setPickupAddress(result.getString("pickupaddress"));
                groupon.add(g);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return groupon;
    }


}
