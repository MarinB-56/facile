package org.acme.utils;

import org.acme.dto.JourneyProposalsDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MockLoaderService {

  private ObjectMapper objectMapper = new ObjectMapper();

  public JourneyProposalsDTO getJourneyProposalsDTOFromNavitiaMock(){
    try {
      JourneyProposalsDTO journeyProposalsDTO = objectMapper.readValue(
          getClass().getResourceAsStream("/sample1_navitia_journey.json"),
          JourneyProposalsDTO.class
      );

      return journeyProposalsDTO;

    } catch (Exception e) {
      System.out.println(e);
    }

    return new JourneyProposalsDTO();
  }
}
