package dambi.meteorites.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import jakarta.annotation.PostConstruct;

import static com.mongodb.client.model.Filters.eq;

/**
 * Klase hau web zerbitzuaren Mongo errepositorioa izango litzateke. Honek, Meteorite errepositoaren 
 * funtzioak implementatzen ditu interfaze bat delako lehen hau. Interfazean zehaztu diren funtzioak
 * era sakonago batean mapeatzen ditu eta hauek Mongo datu basearen kolekzioarekin zelan erlazionatzen
 * diren zehazten du.
 * 
 * Interfaze honen funtzioak implementatzeaz gain Mongoren bezeroa eta datu basearen kolekzioa ezartzen ditu
 * transakzioak era egokian aurrera eraman ahal izateko.
 * 
 * @author Jon Alberdi
 */
@Repository
public class MongoDBMeteoriteRepository implements MeteoriteRepository {
    
    @Autowired
    private MongoClient client;
    private MongoCollection<Meteorite> meteoriteCollection;

    /**
     * Funtzio honek Mongo datu basea zein den eta datu basean zein kolekzio aukeratu behar duen zehazten du.
     */
    @PostConstruct
    void init() {
        meteoriteCollection = client.getDatabase("meteorites").getCollection("fallen", Meteorite.class);
    }

    /**
     * Funtzio honek meteoritoen kolekzioan aurkitzen diren meteorito guztiak bilatzen ditu eta ArrayList batera 
     * pasatzen ditu. Find funtzioak Meteorite motako Iterableak hartzen ditu eta into metodoarekin ArrayList-era
     * bolkatzen ditu.
     * 
     * @return List<Meteorite> Meteorito guztien List bat
     */
    @Override
    public List<Meteorite> denak() {
        return meteoriteCollection.find().into(new ArrayList<>());
    }

    /**
     * Funtzio honek meteoritoen kolekzioan bilaketa bat egiten du, horretarako topatu nahi den meteoritoaren
     * identifikadorea pasatzen da. Identifikadorea edukita meteoritoen kolekzioan pasatako parametroarekin 
     * bat egiten duen meteoritoa topatu eta bueltatzen du. Eq importazio estatikoa erabilita find metodoari
     * filtroa pasatzen zaio.
     * 
     * @return Meteorite Bilatu den meteoritoa
     */
    @Override
    public Meteorite bilatuBat(String idn) {
        return meteoriteCollection.find(eq("idn", idn)).first();
    }

    /**
     * Funtzio honek meteoritoen kolekzioan bilaketa bat egiten du, horretarako topatu nahi den meteoritoaren
     * izena pasatzen da. Izena edukita meteoritoen kolekzioan pasatako parametroarekin 
     * bat egiten duen meteoritoa topatu eta bueltatzen du. Eq importazio estatikoa erabilita find metodoari
     * filtroa pasatzen zaio.
     * 
     * @return Meteorite Bilatu den meteoritoa
     */
    @Override
    public Meteorite bilatuIzena(String name) {
        return meteoriteCollection.find(eq("name", name)).first();
    }

    /**
     * Funtzio honek meteorito berri bat kolekziora gehitzen du. Alde batetik gehitu nahi den meteoritoa pasatzen da
     * eta meteorito hori insertOne metodoaren bitartez kolekziora gehitzen da. Azkenik gehitu berri den meteoritoa
     * bueltatzen da.
     * 
     * @param Meteorite Meteorite klaseko objektu bat
     * @return Meteorite Gehitu berri den Meteorite klaseko objektua
     */
    @Override
    public Meteorite gorde(Meteorite meteorite) {
        meteoriteCollection.insertOne(meteorite);
        return meteorite;
    }

    /**
     * Funtzio honek parametro bidez pasatzen den identifikadoreakin bat egiten duen meteoritoa ezabatzen
     * du. Filtro bidez identifikadorea pasatzen da eta bat egiten duen meteoritoa ezabatzen da. GetDeletedCount
     * funtzioaren bitartez ezabatu den kopurua bueltatzen da.
     * 
     * @param String Ezabatu nahi den meteoritoaren identifikadorea
     * @return long Ezabatu den meteorito kopurua long formatuan
     */
    @Override 
    public long ezabatu(String idn) {
        return meteoriteCollection.deleteMany(eq("idn", idn)).getDeletedCount();
    }

}
