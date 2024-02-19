package dambi.model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Meteorites")
@XmlSeeAlso({Meteorite.class})
public class Meteorites {

    List<Meteorite> meteorites;

    public List<Meteorite> getMeteorites() {
        return meteorites;
    }

    public void setMeteorites(List<Meteorite> meteorites) {
        this.meteorites = meteorites;
    }
}
