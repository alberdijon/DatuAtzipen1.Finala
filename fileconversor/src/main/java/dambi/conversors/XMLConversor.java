package dambi.conversors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

import dambi.model.Meteorites;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

/**
 * 
 * Klase hau XML konbertsorea da, klaseak XML dokumentuak atzitzeko funtzioa
 * du. XML dokumentuak atzizteko objektuen modeloak JAXB anotazioekin
 * konfiguratzen dira mapeoa egiteko eta XML formatura bihurtu ahal izateko.
 * 
 * @author Jon Alberdi
 */
public class XMLConversor {

    /**
     * 
     * Funtzio honek Meteorite motako lista bat jasotzen du eta horren bitartez
     * XML fitxategi bat atzitzen du. Objektuen modeloak JAXB anotazioak erabiliz
     * mapeatu dira puntu honetan gai izateko XML formatura pasatzeko. Fitxategia 
     * Marshaller baten bitartez sortzen da marshal metodoa erabiliz, funtzioari
     * meteoritoen lista eta OutputStream bat pasatzen zaio. Hasieran erabiltzaileak
     * fitxategiaren izena zehaztuko du eta erroan dagoen data direktorioan sortuko da.
     * 
     * @param meteorites
     */
    public static void convertMeteoritesToXml(Meteorites meteorites) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a name for the XML file please: ");
        String fileName = sc.nextLine();
        fileName = "data/" + fileName + ".xml"; // Ajusta la ruta según tu estructura de directorios

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Meteorites.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // Formatea la salida del XML
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Escribe el XML al archivo
            OutputStream os = new FileOutputStream(new File(fileName));
            jaxbMarshaller.marshal(meteorites, os);
            os.close();

            System.out.println("XML file created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
