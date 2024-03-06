package models;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Reservations  {
    int idreservation;
    int userId  ;
    int idevent;
    int nbrPerssone;


    public Reservations(int idreservation, int userId, int idevent, int nbrPerssone, String email, int num_tel) {
        this.idreservation = idreservation;
        this.userId = userId;
        this.idevent = idevent;
        this.nbrPerssone = nbrPerssone;
        this.email = email;
        this.num_tel = num_tel;
    }

    String email;
    int num_tel ;
    String etat ;

    public Reservations(int idreservation, String etat) {
        this.idreservation = idreservation;
        this.etat = etat;
    }

    public Reservations() {
    }

    public Reservations(int userId, int idevent, int nbrPerssone, String email, int num_tel, String etat) {
        this.userId = userId;
        this.idevent = idevent;
        this.nbrPerssone = nbrPerssone;
        this.email = email;
        this.num_tel = num_tel;
        this.etat = etat;
    }

    public Reservations(int idreservation, int userId, int idevent, int nbrPerssone, String email, int num_tel, String etat) {
        this.idreservation = idreservation;
        this.userId = userId;
        this.idevent = idevent;
        this.nbrPerssone = nbrPerssone;
        this.email = email;
        this.num_tel = num_tel;
        this.etat = etat;
    }
    public static void sendEmail(String to, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("abdelmajidbenaissia@gmail.com", "lqxb dotm nshf acfg");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("abdelmajidbenaissia@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Reservations{" +
                "idreservation=" + idreservation +
                ", userId=" + userId +
                ", idevent=" + idevent +
                ", nbrPerssone=" + nbrPerssone +
                ", email='" + email + '\'' +
                ", num_tel=" + num_tel +
                ", etat='" + etat + '\'' +
                '}';
    }

    public int getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(int idreservation) {
        this.idreservation = idreservation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public int getNbrPerssone() {
        return nbrPerssone;
    }

    public void setNbrPerssone(int nbrPerssone) {
        this.nbrPerssone = nbrPerssone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}