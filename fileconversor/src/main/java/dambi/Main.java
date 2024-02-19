package dambi;

import java.io.FileNotFoundException;
import java.util.Scanner;

import dambi.conversors.CSVConversor;
import dambi.conversors.ExtractList;
import dambi.conversors.MongoExport;
import dambi.conversors.MongoImport;
import dambi.conversors.XMLConversor;
import dambi.model.Meteorites;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        int choice;

        do {    
        
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("MAIN MENU");
            System.out.println("-------------------------------------");
            System.out.println("1) Convert to CSV");
            System.out.println("2) Convert to XML");
            System.out.println("3) Import to MongoDB");
            System.out.println("4) Export from MongoDB to JSON");
            System.out.println("5) Exit application");
            System.out.println("-------------------------------------");
            System.out.println("Select an action");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Meteorites meteoritesList = ExtractList.extractObjectList();
                    CSVConversor.convertMeteoritesToCsv(meteoritesList);
                    break;
            
                case 2:
                    Meteorites meteoritexml = ExtractList.extractObjectList();
                    XMLConversor.convertMeteoritesToXml(meteoritexml);
                    break;
                
                case 3:
                    Meteorites meteorites = ExtractList.extractObjectList();
                    MongoImport.importJSONToMongo(meteorites);
                    break;

                case 4:
                    MongoExport.exportFromMongo();
                    break;
                
                case 5:
                    break;

                default:
                    break;
            }

        } while (choice != 5);
    }
}