package dambi.conversors;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * Klase honetan gure MongoDB datu basetik JSON dokumentu batera exportazioa
 * egiteko funtzioa aurkitzen da. 
 * 
 * @author Jon Alberdi
 */
public class MongoExport {

    /**
     * Funtzio honek Mongo datubasetik JSON dokumentu batera datuen exportazioa egiten du.
     * Horretarako lehenik mongo bezero bat sortzen da konexioa egiteko eta datu basearen zein
     * kolekzioaren izena zehazten dira datuak ateratzeko. Ondoren erabiltzaileari sortuko den
     * dokumentuaren izena eskatu dio, behin izena zehaztuta FileWriter baten bitartez dokumentua 
     * idazten joango da. 
     * 
     * JSON-em kasuan iterableak aurkituko ditu kolekzioan, iterableak Document tipokoak dira, behin
     * horiek aurkituta JSON formatura pasako ditu eta fitxategian idatziko ditu.
     */
    public static void exportFromMongo() {

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("meteorites");
            MongoCollection<Document> collection = database.getCollection("fallen");

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the name of the JSON document: ");
            String fileName = sc.nextLine();

            String pathName = "data/" + fileName + ".json";

            Path jsonFilePath = Paths.get(pathName);

            try (FileWriter jsonWriter = new FileWriter(jsonFilePath.toFile())) {
                FindIterable<Document> documents = collection.find();

                jsonWriter.write("[\n");

                int documentCount = 0;
                for (Document document : documents) {
                    
                    jsonWriter.write(document.toJson());

                    if (++documentCount < collection.countDocuments()) {
                        jsonWriter.write(",\n");
                    }
                }

                jsonWriter.write("\n]");

                System.out.println("Export succesfully completed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
