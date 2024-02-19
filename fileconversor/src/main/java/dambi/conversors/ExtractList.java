package dambi.conversors;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.json.*;

import dambi.model.Geolocation;
import dambi.model.Meteorite;
import dambi.model.Meteorites;

/**
 * 
 * Klase honetan JSON dokumentu batetik Meteorite klaseko objektuen lista
 * batera pasatzeko operazioa egiten duen funtzioa aurkitzen da. Konbertsio
 * horrekin programak egiten dituen operazioak era errazago batean egitea ahalbidetzen
 * du. Objektu lista horrekin geroago konbertsioak eta inportazioa egiten dira.
 * 
 * @author Jon Alberdi
 */
public class ExtractList {

    /**
     * 
     * Funtzio honek JSON dokumentu batetik abiatuz Meteorite klaseko objektuen
     * lista bat bueltatzen du. Lehenik erabiltzaileari datu jatorri moduan zehaztuko
     * den JSON dokumentuaren izena eskatuko dio, kontutan eduki behar da JSON 
     * dokumentua erroan dagoen data direktorioan sartu behar dela. Behin izena sartuta
     * JsonReader baten bitartez JSON dokumentua irakutzen da eta banan banan datuak ateratzen 
     * joango da. Geolocation-aren kasuan lehenik nuloa den ala ez konprobatzen du. Objektu 
     * bakoitzaren lektura lista baten barruan gordetzen da eta azkenik lista hori bueltatzen da.
     * 
     * 
     * @return Meteorites klaseko objektu bat, aldi berean Meteorite lista bat izanik
     * @throws FileNotFoundException
     */
    public static Meteorites extractObjectList() throws FileNotFoundException {
        Meteorites meteorites = new Meteorites();
        List<Meteorite> meteoriteList = new ArrayList<>();
        JsonObject meteoriteJObj;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the input file: ");
        String fileName = sc.nextLine();
        String pathName = "data/" + fileName;

        try {
            JsonReader reader = Json.createReader(new FileReader(pathName));
            JsonStructure jStruct = reader.read();
            JsonArray jArray = jStruct.asJsonArray();

            for (int i = 0; i < jArray.size(); i++) {
                meteoriteJObj = jArray.getJsonObject(i);

                // Manejo seguro de "geolocation"
                JsonObject geoJObj = null;
                if (meteoriteJObj.containsKey("geolocation") && meteoriteJObj.get("geolocation").getValueType() == JsonValue.ValueType.OBJECT) {
                    geoJObj = meteoriteJObj.getJsonObject("geolocation");
                }

                Geolocation geolocation = null;
                if (geoJObj != null) {
                    JsonArray coordinates = geoJObj.getJsonArray("coordinates");
                    List<Double> coordsList = new ArrayList<>();
                    if (coordinates != null) {
                        for (JsonValue val : coordinates) {
                            if (val.getValueType() == JsonValue.ValueType.NUMBER) {
                                coordsList.add(((JsonNumber) val).doubleValue());
                            } else {
                                System.out.println("One of the coordinates is not a number");
                            }
                        }
                    }
                    geolocation = new Geolocation(geoJObj.getString("type", "Unknown"), coordsList);
                }

                Meteorite meteorite = new Meteorite(
                        meteoriteJObj.getString("idn", "Unknown"),
                        meteoriteJObj.getString("name", "Unknown"),
                        meteoriteJObj.getString("nametype", "Unknown"),
                        meteoriteJObj.getString("recclass", "Unknown"),
                        meteoriteJObj.getString("mass", "0"), // Asumiendo "0" como valor predeterminado
                        meteoriteJObj.getString("fall", "Unknown"),
                        meteoriteJObj.getString("year", "Unknown"),
                        meteoriteJObj.getString("reclat", "0.0"), // Asumiendo "0.0" como valor predeterminado
                        meteoriteJObj.getString("reclong", "0.0"), // Asumiendo "0.0" como valor predeterminado
                        geolocation);

                meteoriteList.add(meteorite);
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nWARNING: File not found. Please try again");
        } catch (Exception e) {
            e.printStackTrace();
        }

        meteorites.setMeteorites(meteoriteList);
        return meteorites;
    }
}
