package services;
import models.Event;
import utils.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventServices {
    private Connection connection;

    public EventServices () {

        connection = (Connection) ConnectionUtil.conDB();
    }
    public void addEvent(Event e) {
        int nbrP=0 ;
        String qry = "INSERT INTO `event` (`name`, `type`, `date`, `desc`, `eventpath`, `localisation`,`nombresP`,`latitude`,`longitude`) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(qry);
            stm.setString(1, e.getName());
            stm.setString(2, e.getType());
            stm.setDate(3, new java.sql.Date(e.getDate().getTime()));
            stm.setString(4, e.getDesc());
            stm.setString(5, e.getImagePath());
            stm.setString(6, e.getLocal());
            stm.setInt(7,nbrP);
            stm.setFloat(8, e.getLatitude());
            stm.setFloat(9, e.getLongitude());

            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
  public List<Event> getEvent() {
      String req1 = "SELECT * FROM event";
      List<Event> events = new ArrayList<>();
      try {
          Statement stm = connection.createStatement();
          ResultSet rs = stm.executeQuery(req1);


          while (rs.next()) {
              Event e1 = new Event();
              e1.setId(rs.getInt("id"));
              e1.setName(rs.getString("name"));
              e1.setType(rs.getString("type"));
              e1.setDate(new Date(rs.getDate("date").getTime()));
              e1.setDesc(rs.getString("desc"));
              e1.setLocal(rs.getString("localisation"));
              e1.setImagePath(rs.getString("eventpath"));
              e1.setLatitude(rs.getFloat("latitude"));
              e1.setLongitude(rs.getFloat("longitude"));




              events.add(e1);

              System.out.println("the event is: " + e1.toString());
          }
      } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      }
      return events;
  }

    public void updateEvent(Event e) {
        String qry = "UPDATE `event` SET `name`=?, `type`=?, `date`=?, `desc`=?, `eventpath`=?, `localisation`=? , `latitude`=?, `longitude`=? WHERE `id`=?";
        try {
            PreparedStatement stm = connection.prepareStatement(qry);
            stm.setString(1, e.getName());
            stm.setString(2, e.getType());
            stm.setObject(3, e.getDate());
            stm.setString(4, e.getDesc());
            stm.setString(5, e.getImagePath());
            stm.setString(6, e.getLocal());
            stm.setFloat(7, e.getLatitude());
            stm.setFloat(8, e.getLongitude());
            stm.setInt(9, e.getId()); // Utilisez e.getId() pour définir l'ID de l'événement

            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void deleteEvent(int eventId) {
        try {
            // Supprimer d'abord les réservations liées à cet événement
            String deleteReservationsQuery = "DELETE FROM reservations WHERE idev=?";
            PreparedStatement deleteReservationsStatement = connection.prepareStatement(deleteReservationsQuery);
            deleteReservationsStatement.setInt(1, eventId);
            deleteReservationsStatement.executeUpdate();

            // Ensuite, supprimer l'événement lui-même
            String deleteEventQuery = "DELETE FROM event WHERE id=?";
            PreparedStatement deleteEventStatement = connection.prepareStatement(deleteEventQuery);
            deleteEventStatement.setInt(1, eventId);
            deleteEventStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }






    public Event getFirstEvent() {
        String req = "SELECT * FROM event ORDER BY id ASC LIMIT 1"; // Sélectionnez le premier événement par ordre d'ID
        Event firstEvent = null;
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(req);
            if (rs.next()) {
                firstEvent = new Event();
                firstEvent.setId(rs.getInt("id"));
                firstEvent.setName(rs.getString("name"));
                firstEvent.setType(rs.getString("type"));
                firstEvent.setDate(new Date(rs.getDate("date").getTime()));
                firstEvent.setDesc(rs.getString("desc"));
                firstEvent.setLocal(rs.getString("localisation"));
                firstEvent.setImagePath(rs.getString("eventpath"));
                System.out.println("The first event is: " + firstEvent.toString());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return firstEvent;
    }
    public List<Event> getEventsByName(String eventName) {
        String query = "SELECT * FROM event WHERE name = ?";
        List<Event> events = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, eventName);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setType(rs.getString("type"));
                e.setDate(rs.getDate("date"));
                e.setDesc(rs.getString("desc"));
                e.setImagePath(rs.getString("eventpath"));
                e.setLocal(rs.getString("localisation"));

                events.add(e);

                System.out.println("Event: " + e.toString());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return events;
    }




    public List<Event> getTopThreeEventsByParticipants() {
        String query = "SELECT * FROM event ORDER BY nombresP DESC LIMIT 3"; // Sélectionne les trois événements avec le plus grand nombre de participants
        List<Event> topEvents = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                Date date = resultSet.getDate("date");
                String desc = resultSet.getString("desc");
                String imagePath = resultSet.getString("eventpath");
                String local = resultSet.getString("localisation");

                Event event = new Event(id, name, type, date, desc, imagePath, local);
                topEvents.add(event);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Gérer les exceptions SQL
        }

        return topEvents;
    }
    public Event getLastInsertedEvent() throws SQLException {
        String req = "SELECT * FROM event ORDER BY id DESC LIMIT 1";
        PreparedStatement pre = connection.prepareStatement(req);
        ResultSet rs = pre.executeQuery();

        Event e = new Event();

        if (rs.next()) {

            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));
            e.setType(rs.getString("type"));
            e.setDate(rs.getDate("date"));
            e.setDesc(rs.getString("desc"));
            e.setImagePath(rs.getString("eventpath"));
            e.setLocal(rs.getString("localisation"));
            // Set other attributes as needed
        }

        // Close resources
        if (rs != null) {
            rs.close();
        }
        if (pre != null) {
            pre.close();
        }

        return e;
    }




}















