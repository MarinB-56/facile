package org.acme.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.xml.bind.DataBindingException;

/*
 * Tester la désérialisation Json
 */
@QuarkusTest
public class NavitiaDtoMappingTest {

  @Test
  void shouldDeserializeJson(){
    ObjectMapper mapper = new ObjectMapper();
    // Ignore les propriétés présentes dans le json mais non présentes dans les objets DTO
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    InputStream jsonStream = getClass().getResourceAsStream("/navitia_sample.json");
    assertNotNull(jsonStream, "Le fichier JSON n'a pas été trouvé !");

    JourneyProposalsDTO journeyProposalsDTO = null;
    // Désérialisation du Json
    try{
      journeyProposalsDTO = mapper.readValue(jsonStream, JourneyProposalsDTO.class);
    }catch(StreamReadException e){
      e.printStackTrace();
    }catch(IOException e ){
      e.printStackTrace();
    }catch(DataBindingException e){
      e.printStackTrace();
    }

    assertFalse(journeyProposalsDTO.getJourneyProposals().isEmpty());
  }
}
