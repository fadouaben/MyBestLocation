package benhajyedder.fadoua.mybestlocationdash;

public class MyPosition {
    public int id;
    public double longitude,latitude;
    public String description;

    public MyPosition(int id ,double longitude, double latitude, String description) {
        this.id=id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
    }

    @Override
    public String toString() {
        return "MyPosition{" +
                ", id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }
}
