package dambi.meteorites.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "fallen")
public class Meteorite {

    @Id
    private String idn;

    private String name;
    private String nametype;
    private String recclass;
    private String mass;
    private String fall;
    private String year;
    private String reclat;
    private String reclong;
    
    @Field("geolocation")
    private Geolocation geolocation;

    public Meteorite() {

    }

    public Meteorite(String idn, String name, String nametype, String recclass, String mass, String fall, String year,
            String reclat, String reclong, Geolocation geolocation) {
        this.idn = idn;
        this.name = name;
        this.nametype = nametype;
        this.recclass = recclass;
        this.mass = mass;
        this.fall = fall;
        this.year = year;
        this.reclat = reclat;
        this.reclong = reclong;
        this.geolocation = geolocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public String getRecclass() {
        return recclass;
    }

    public void setRecclass(String recclass) {
        this.recclass = recclass;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getFall() {
        return fall;
    }

    public void setFall(String fall) {
        this.fall = fall;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReclat() {
        return reclat;
    }

    public void setReclat(String reclat) {
        this.reclat = reclat;
    }

    public String getReclong() {
        return reclong;
    }

    public void setReclong(String reclong) {
        this.reclong = reclong;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    @Override
    public String toString() {
        return "Meteorite [id=" + idn + ", name=" + name + ", nametype=" + nametype + ", recclass=" + recclass
                + ", mass=" + mass + ", fall=" + fall + ", year=" + year + ", reclat=" + reclat + ", reclong=" + reclong
                + ", geolocation=" + geolocation + "]";
    }

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    
}
