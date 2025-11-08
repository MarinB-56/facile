package org.acme.utils;

import org.acme.dto.JourneyProposalsDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MockLoaderService {

  private ObjectMapper objectMapper = new ObjectMapper();

  public JourneyProposalsDTO getJourneyProposalsDTOFromNavitiaMock(String fileName){
    try {
      JourneyProposalsDTO journeyProposalsDTO = objectMapper.readValue(
          getClass().getResourceAsStream("/" + fileName + ".json"),
          JourneyProposalsDTO.class
      );

      return journeyProposalsDTO;

    } catch (Exception e) {
      System.out.println(e);
    }

    return new JourneyProposalsDTO();
  }
}
