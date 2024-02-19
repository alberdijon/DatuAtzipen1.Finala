package dambi.meteorites.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dambi.meteorites.model.Geolocation;
import dambi.meteorites.model.Meteorite;
import dambi.meteorites.model.MeteoriteRepository;

@RestController
@RequestMapping(path = "/meteorite")
public class MainController {

    @Autowired
    private MeteoriteRepository meteoriteRepository;

    /**
     * Funtzio honek GET request-ak egiteko mapping-a ezartzen du. Endpoint-a
     * zehazten du eta endpoint-aren
     * funtzionamendua ezartzen du. Gure meteoritoen errepositorioko meteorito guztiak
     * bilatzen ditu eta JSON formatuan
     * bueltatzen ditu automatikoki (XML formatuan ere bueltatu dezake findAll()
     * funtzioak). Kasu honetan
     * Meteorite klaseko Iterable-ak bueltatzen ditu.
     * 
     * @return Iterable<Meteorite> Meteorite motako Iterable bat, honek meteorito guztiak bere gain hartuz
     */
    @GetMapping(path = "/guztiak")
    public @ResponseBody Iterable<Meteorite> getMeteorites() {
        return meteoriteRepository.denak();
    }

    /**
     * Funtzio honek GET request-ak egiteko Endpoint zehatza ezartzen du. Funtzioak
     * String motako
     * parametro bat jasotzen du kasu honetan meteoritoaren identifikadorea izanik.
     * Parametro horrekin
     * meteoritoen errepositorioan identifikadore hori duen meteoritoa topatzen du eta
     * bueltatzen du JSON formatuan.
     * Eskaera hau interesgarria da soilik meteorito bat topatzea nahi baduzu, hala ere
     * kontutan izan behar da
     * bere identifikadorea ezagutzea beharrezkoa dela.
     * 
     * @param String id Meteorito zehatzaren identifikadorea String formatuan
     * @return Meteorite Pasatako identifikadorearekin bat etortzen duen meteoritoa
     */
    @GetMapping(path = "/meteoritoid")
    public @ResponseBody Meteorite findMeteoriteId(@RequestParam String id) {
        return meteoriteRepository.bilatuBat(id);
    }

    /**
     * Funtzio honek GET request-ak egiteko Endpoint zehatza ezartzen du. Funtzioak
     * String motako
     * parametro bat jasotzen du kasu honetan meteoritoaren izena izanik.
     * Parametro horrekin
     * meteoritoen errepositorioan izen hori duen meteoritoa topatzen du eta
     * bueltatzen du JSON formatuan.
     * Eskaera hau interesgarria da soilik meteorito bat topatzea nahi baduzu, hala ere
     * kontutan izan behar da
     * bere izena ezagutzea beharrezkoa dela.
     * 
     * @param String id Meteorito zehatzaren izena String formatuan
     * @return Meteorite Pasatako izenarekin bat etortzen duen meteoritoa
     */
    @GetMapping(path = "/meteoritoizena")
    public @ResponseBody Meteorite findMeteoriteName(@RequestParam String name) {
        return meteoriteRepository.bilatuIzena(name);
    }

    /**
     * Funtzio honek POST motako request-ak egiteko Endpoint-a mapeatzen du. Funtzio
     * honen bitartez datu basean
     * meteorito berri bat gehitu daiteke. Horretarako Meteorite motako meteorito bat sortzen da
     * konstruktorean zehaztutako
     * parametroekin, behin meteoritoa sortuta meteoritoen errepositoriaren gordetzen da.
     * 
     * @param idn String
     * @param name String
     * @param nametype String
     * @param recclas String
     * @param mass String
     * @param fall String
     * @param year String
     * @param reclat String
     * @param reclong String
     * @param geolocation Geolocation
     * 
     * @return String Meteoritoa era egokian gehitu delaren mezu bat String formatuan
     */
    @PostMapping(value = "/metberria")
    public @ResponseBody String metBerriaGehitu(@RequestParam String idn, @RequestParam String name, @RequestParam String nametype, @RequestParam String recclass, @RequestParam String mass, @RequestParam String fall,
    @RequestParam String year, @RequestParam String reclat, @RequestParam String reclong, @RequestBody Geolocation geolocation) {

        Meteorite m = new Meteorite();
        m.setIdn(idn);
        m.setName(name);
        m.setNametype(nametype);
        m.setRecclass(recclass);
        m.setMass(mass);
        m.setFall(fall);
        m.setYear(year);
        m.setReclat(reclat);
        m.setReclong(reclong);
        m.setGeolocation(geolocation);
        meteoriteRepository.gorde(m);
        
        return "Meteoritoa era egokian gehitu da";
    }

    /**
     * Funtzio honek DELETE motatako request-ak egiteko Endpoint bat mapeatzen du.
     * Funtzio honek String
     * motako parametro bat jasotzen du. Delete motatako request-ak mongo
     * errepositorioan aurkitzen den
     * delete funtzioari deitzen dio eta honek ezabatu diren meteorito kopurua bueltatzen
     * du behin hura ezabatzean.
     * Beti bat ezabatuko du eta ez bada bat jasotzen errore bat gertatu dela
     * jakin daiteke.
     * 
     * @param idn Meteoritoaren identifikadorea String formatuan
     * 
     * @return String Meteoritoa era egokian edo desegokian gorde delaren mezu bat String formatuan
     * 
     */
    @DeleteMapping(path = "/metezabatu")
    public @ResponseBody String deleteMeteorite(@RequestParam String idn) {

        try {
            long deleted = meteoriteRepository.ezabatu(idn);

            if (deleted > 0) {
                return ("Meteoritoa era egokian ezabatu da");
            } else {
                return ("Errore bat gertatu da meteoritoa ezabatzen, saiatu berriro mesedez...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore bat gertatu da meteoritoa ezabatzen, saiatu berriro mesedez...";
        }
    }

    /**
     * Funtzio honek PUT motatako request-ak egiteko Endpoint-a mapeatzen du.
     * Funtzio honen bitartez meteorito
     * zehatz bat eguneratu daiteke. Hau lortzeko POST-ean egiten den moduan
     * meteoritoaren parametroak bidaltzen
     * dira request-aren bitartez. Parametro horiek metoritoaren balore berriak
     * izango dira. Behin horiek jasota meteoritoa eguneratzen da
     * 
     * @param idn String
     * @param name String
     * @param nametype String
     * @param recclas String
     * @param mass String
     * @param fall String
     * @param year String
     * @param reclat String
     * @param reclong String
     * @param geolocation Geolocation
     */
    @PutMapping(value = "/meteoritoaeguneratu")
    public ResponseEntity<Meteorite> updateMeteorite(@RequestParam String idn, @RequestParam String name, @RequestParam String nametype, @RequestParam String recclass, @RequestParam String mass, @RequestParam String fall,
    @RequestParam String year, @RequestParam String reclat, @RequestParam String reclong, @RequestBody Geolocation geolocation) {

        try {
            Meteorite m = meteoriteRepository.bilatuBat(idn);
            m.setName(name);
            m.setNametype(nametype);
            m.setRecclass(recclass);
            m.setMass(mass);
            m.setFall(fall);
            m.setYear(year);
            m.setReclat(reclat);
            m.setReclong(reclong);
            m.setGeolocation(geolocation);
            meteoriteRepository.gorde(m);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    
}
