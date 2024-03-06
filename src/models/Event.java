package models;

import java.util.Date;
import java.util.Objects;

public class Event {

    public Event(int id, String name, String type, Date date, String desc, String imagePath, String local, int nombresP) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.desc = desc;
        this.imagePath = imagePath;
        this.local = local;
        this.nombresP = nombresP;
    }


    private   float latitude ;

    private float longitude;

    public Event(float latitude, float longitude, int id, String name, String type, Date date, String desc, String imagePath, String local, int nombresP) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.desc = desc;
        this.imagePath = imagePath;
        this.local = local;
        this.nombresP = nombresP;
    }

    int id;
    String name;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    String type;
    Date date;
    String desc;
    String imagePath;
    String local;

    public Event(int id, int nombresP) {
        this.id = id;
        this.nombresP = nombresP;
    }

    public int getNombresP() {
        return nombresP;
    }

    public void setNombresP(int nombresP) {
        this.nombresP = nombresP;
    }

    int nombresP ;

    public Event(int id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    public Event() {

    }
    public Event(String name, String type, Date date, String desc, String imagePath, String local) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.desc = desc;
        this.imagePath = imagePath;
        this.local = local;
    }

    public Event(int id, String name, String type, Date date, String desc, String imagePath, String local) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.desc = desc;
        this.imagePath = imagePath;
        this.local = local;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && Objects.equals(name, event.name) && Objects.equals(type, event.type) && Objects.equals(date, event.date) && Objects.equals(desc, event.desc) && Objects.equals(imagePath, event.imagePath) && Objects.equals(local, event.local);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, date, desc, imagePath, local);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", desc='" + desc + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", local='" + local + '\'' +
                '}';
    }
}