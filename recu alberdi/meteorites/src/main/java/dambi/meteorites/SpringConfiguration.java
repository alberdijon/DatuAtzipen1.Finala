package dambi.meteorites;


import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Klase honen bitartez aplikazioaren eta Mongo-ren arteko konexioa eta BSON dokumentuen
 * eta Java objektuen arteko konbertsioa egiten da. Atributu moduan, application.properties dokumentuan zehazten den 
 * konexio string-a hartzen du. MongoClient funtzioan konbertsioa egiten da eta mongo-ren
 * bezeroa bueltatzen da konexioa egiteko.
 * 
 * @author Jon Alberdi
 */
@Configuration
public class SpringConfiguration {
    
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Bean  
    public MongoClient mongoClient() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());//JavaObject <=> BSONdocument konbertsioa gauzatzeko 
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        return MongoClients.create(MongoClientSettings.builder()
                                                    .applyConnectionString(new ConnectionString(connectionString))
                                                    .codecRegistry(codecRegistry)
                                                    .build());
        //return MongoClients.create();

    }
}
