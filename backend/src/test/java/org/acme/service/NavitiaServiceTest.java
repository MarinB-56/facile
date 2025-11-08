package org.acme.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.acme.dto.JourneyProposalsDTO;
import org.acme.utils.MockLoaderService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class NavitiaServiceTest {

  @Inject
  MockLoaderService mockLoaderService;

  @Inject
  NavitiaService navitiaService;

  @Test
  void shouldFilterNonRelevantJourneys(){
    JourneyProposalsDTO journeyProposals = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample1_navitia_journey");
    JourneyProposalsDTO journeyProposalsFiltered = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample1_navitia_journey_filtered");

    JourneyProposalsDTO journeyProposalsSecond = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample2_navitia_journey");
    JourneyProposalsDTO journeyProposalsFilteredSecond = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample2_navitia_journey_filtered");

    JourneyProposalsDTO journeyProposalsThird = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample3_navitia_journey");
    JourneyProposalsDTO journeyProposalsFilteredThird = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample3_navitia_journey_filtered");

    JourneyProposalsDTO journeyProposalsFourth = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample4_navitia_journey");
    JourneyProposalsDTO journeyProposalsFilteredFourth = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample4_navitia_journey_filtered");


    journeyProposals = navitiaService.filterNonRelevantJourneys(journeyProposals);
    journeyProposalsSecond = navitiaService.filterNonRelevantJourneys(journeyProposalsSecond);
    journeyProposalsThird = navitiaService.filterNonRelevantJourneys(journeyProposalsThird);
    journeyProposalsFourth = navitiaService.filterNonRelevantJourneys(journeyProposalsFourth);

    assertThat(journeyProposals).usingRecursiveComparison().isEqualTo(journeyProposalsFiltered);
    assertThat(journeyProposalsSecond).usingRecursiveComparison().isEqualTo(journeyProposalsFilteredSecond);
    assertThat(journeyProposalsThird).usingRecursiveComparison().isEqualTo(journeyProposalsFilteredThird);
    assertThat(journeyProposalsFourth).usingRecursiveComparison().isEqualTo(journeyProposalsFilteredFourth);
  }
}
