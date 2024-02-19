package dambi.meteorites.model;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Klase honetan meteoritoen errepositorioaren funtzioak zehazten dira. Funtzio hauek
 * beharrezkoak diren eragiketak izango lirateke. Funtzio hauek geroago Mongo errepositorioan implementatuko dira.
 * 
 * @author Jon Alberdi
 */
@Repository
public interface MeteoriteRepository {

    List<Meteorite> denak();
    Meteorite bilatuBat(String idn);
    Meteorite bilatuIzena(String name);
    Meteorite gorde(Meteorite meteorite);
    long ezabatu(String idn);
}
