package dambi.conversors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.stream.Collectors;

import dambi.model.Meteorite;
import dambi.model.Meteorites;

/**
 * Klase hau CSV fitxategi batera bihurtzeko funtzionalitatea zehazten du. Horretarako
 * CSV-aren dokumentu izena eskatuko dio erabiltzaileari eta konbertsioa egingo du.
 * Konbertsioa egiteko Meteorite klaseko objektu lista bat jasotzen du eta CSV fitxategia
 * idazten du. Lehenik eremuen izenburuak idazten ditu eta geroago listako objektu bakoitza idazten joango da atributu bakoitza
 * idatziz. Geolokalizazioaren kasuan batzuetan nuloa da bera atributu zehazt horretarako
 * lehenik konprobazioa egiten du.
 * 
 * @author Jon Alberdi
 */
public class CSVConversor {
    
    /**
     * Funtzio honek Meteorite klaseko lista bat jasotzen du eta hortik abiatuz 
     * CSV fitxategi bat idazten du. Erabiltzaileak CSV fitxategiaren izena sartuko
     * du eta erroan dagoen data direktorioan sortuko du fitxategia. 
     * 
     * Lehenik eremuen izenburuak idazten ditu eta geroago for batean objektu bakoitzaren
     * atributuak idazten ditu. Berezitasun bakarra geolocation atributua izango litzateke, 
     * kasu honetan gertatu daiteke meteorite objektu bat edo batzuek nuloa edukitzea atributu 
     * hau beraz lehenik konpprobazioa egiten du.
     * 
     * @param meteorites Meteorite klaseko objektuen lista
     */
    public static void convertMeteoritesToCsv(Meteorites meteorites) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter a name for the CSV file please: ");
    String fileName = sc.nextLine();
    fileName = "data/" + fileName + ".csv"; // Ajusta la ruta según tu estructura de directorios

    try {
        File convertedFile = new File(fileName);
        convertedFile.createNewFile();

        BufferedWriter outputStream = new BufferedWriter(new FileWriter(convertedFile, false));

        // Escribir cabecera del CSV
        outputStream.write("idn;name;nametype;recclass;mass;fall;year;reclat;reclong;geotype;coordinates\n");

        // Iterar sobre la lista de meteoritos y escribir sus datos
        for (Meteorite meteorite : meteorites.getMeteorites()) {
            String coordinates = "";
            if (meteorite.getGeolocation() != null && meteorite.getGeolocation().getCoordinates() != null) {
                // Convertir las coordenadas a un String separado por comas
                coordinates = meteorite.getGeolocation().getCoordinates().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","));
            }
            
            outputStream.write(meteorite.getIdn() + ";" + meteorite.getName() + ";" + meteorite.getNametype() + ";" +
                    meteorite.getRecclass() + ";" + meteorite.getMass() + ";" + meteorite.getFall() + ";" +
                    meteorite.getYear() + ";" + meteorite.getReclat() + ";" + meteorite.getReclong() + ";" +
                    (meteorite.getGeolocation() != null ? meteorite.getGeolocation().getType() : "") + ";" +
                    coordinates + "\n");
        }
        outputStream.close();
        System.out.println("CSV file created successfully.");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
