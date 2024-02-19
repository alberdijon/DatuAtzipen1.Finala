package dambi.conversors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dambi.model.Meteorite;
import dambi.model.Meteorites;

/**
 * 
 * Klase honetan JSON dokumentu batetik irakurritako lista batetik gure MongoDB-ko datu basera datuak
 * inportatzeko funtzioa aurkitzen da. Beti JSON dokumentu batetik abiatuko 
 * da eta erroan dagoen data direktorioan gordeko dugun eta Meteorite klaseko
 * mapeoarekin bat egiten duen azpiegitura duen JSON dokumentuak inportatu 
 * ahal izango ditu. Dokumentu horretatik lektura bat egiten da eta Meteorite lista bat
 * ateratzen da. Lista horretako datuak inportatuko dira geroago.
 * 
 * @author Jon Alberdi
 */
public class MongoImport {

    /**
     * 
     * Funtzio honek JSON dokumentu baten dauzkagun meteoritoen datuak gure datu basera inportatzen 
     * ditu. Dokumentu horretatik datuak irakurri eta Meteorite klaseko objektuen lista bat ateratzen da.
     * Funtzioak lista hori jasotzen du eta inportazioa egiten du.
     * 
     * Lehenik mongo bezero bat sortzen da eta konexioa egiten da konexio string baten bitartez.
     * Hortaz gain datu basearen eta kolekzioaren izena zehazten dira datuak bertan inportatzeko. 
     * 
     * Hurrengo pausoan Document motako lista bat sortzen da eta jaso duen lista horretako objektu 
     * bakoitza irakutzen da Document-era konbertsioa egiteko. Mongo gai izango da Document horiek
     * irakutzeko beraz behin Document lista horrekin gure kolekzioara insertMany funtzioaren bitartez datuak
     * sartuko ditu.
     * 
     * @param meteorites
     */
    public static void importJSONToMongo(Meteorites meteorites) {

        String connectionString = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("meteorites");
            MongoCollection<Document> collection = database.getCollection("fallen");
    
            List<Document> meteoriteDocuments = new ArrayList<>();
            for (Meteorite meteorite : meteorites.getMeteorites()) {
                Document geoDoc = null;
                if (meteorite.getGeolocation() != null) {
                    geoDoc = new Document("type", meteorite.getGeolocation().getType())
                            .append("coordinates", meteorite.getGeolocation().getCoordinates());
                }
                Document doc = new Document("idn", meteorite.getIdn())
                        .append("name", meteorite.getName())
                        .append("nametype", meteorite.getNametype())
                        .append("recclass", meteorite.getRecclass())
                        .append("mass", meteorite.getMass())
                        .append("fall", meteorite.getFall())
                        .append("year", meteorite.getYear())
                        .append("reclat", meteorite.getReclat())
                        .append("reclong", meteorite.getReclong())
                        .append("geolocation", geoDoc); // Usa geoDoc, que puede ser null
                meteoriteDocuments.add(doc);
            }
    
            if (!meteoriteDocuments.isEmpty()) {
                collection.insertMany(meteoriteDocuments);
                System.out.println(meteoriteDocuments.size() + " meteorites inserted.");
            } else {
                System.out.println("No meteorites to insert.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
