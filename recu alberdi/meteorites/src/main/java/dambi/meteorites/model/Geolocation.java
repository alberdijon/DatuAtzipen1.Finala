package dambi.meteorites.model;

import java.util.List;

public class Geolocation {
    
    private String type;
    private List<Double> coordinates;

    public Geolocation() {

    }

    public Geolocation(String type, List<Double> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<Double> getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Geolocation [type=" + type + ", coordinates=" + coordinates + "]";
    }

}
