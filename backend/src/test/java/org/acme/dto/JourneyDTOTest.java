package org.acme.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.xml.bind.DataBindingException;

@QuarkusTest
public class JourneyDTOTest {

  private JourneyDTO journeyDTO;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

  @BeforeEach
  void setup() throws Exception{
    //Création d'un objet JourneyDTO
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    InputStream jsonStream = getClass().getResourceAsStream("/navitia_sample.json");
    assertNotNull(jsonStream, "Le fichier JSON n'a pas été trouvé !");

    JourneyProposalsDTO journeyProposalsDTO = null;
    try{
      journeyProposalsDTO = mapper.readValue(jsonStream, JourneyProposalsDTO.class);
    }catch(StreamReadException e){
      e.printStackTrace();
    }catch(IOException e ){
      e.printStackTrace();
    }catch(DataBindingException e){
      e.printStackTrace();
    }

    this.journeyDTO = journeyProposalsDTO.getJourneyProposals().get(0);
  }

  @Test
  void shouldGetFirstDepartureDateTimeFormatted(){

    LocalDateTime firstDepartureDateTime = this.journeyDTO.getFirstDeparturDateTime();
    LocalDateTime expectedDateTime = LocalDateTime.parse("20251030T074930", this.formatter);

    assertEquals(expectedDateTime, firstDepartureDateTime, "L'heure de départ du voyage n'est pas la bonne");
  }

  @Test
  void shouldGetLastArrivalDateTimeFormatted(){

    LocalDateTime lastArrivalDateTime = this.journeyDTO.getLastArrivalDateTime();
    LocalDateTime expectedDateTime = LocalDateTime.parse("20251030T165300", formatter);

    assertEquals(expectedDateTime, lastArrivalDateTime, "L'heure d'arrivée du voyage n'est pas la bonne");
  }
}
