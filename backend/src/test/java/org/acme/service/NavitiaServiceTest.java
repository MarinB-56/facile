package org.acme.service;

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
  void shouldFilterNonRelevantJourneysFirst(){
    JourneyProposalsDTO journeyProposals = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample1_navitia_journey");
    JourneyProposalsDTO journeyProposalsFiltered = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample1_navitia_journey_filtered");

    journeyProposals = navitiaService.filterNonRelevantJourneys(journeyProposals);

    assertThat(journeyProposals).usingRecursiveComparison().isEqualTo(journeyProposalsFiltered);
  }

  @Test
  void shouldFilterNonRelevantJourneysSecond(){
    JourneyProposalsDTO journeyProposalsSecond = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample2_navitia_journey");
    JourneyProposalsDTO journeyProposalsFilteredSecond = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample2_navitia_journey_filtered");

    journeyProposalsSecond = navitiaService.filterNonRelevantJourneys(journeyProposalsSecond);

    assertThat(journeyProposalsSecond).usingRecursiveComparison().isEqualTo(journeyProposalsFilteredSecond);
  }

  @Test
  void shouldFilterNonRelevantJourneysThird(){
    JourneyProposalsDTO journeyProposalsThird = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample3_navitia_journey");
    JourneyProposalsDTO journeyProposalsFilteredThird = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample3_navitia_journey_filtered");

    journeyProposalsThird = navitiaService.filterNonRelevantJourneys(journeyProposalsThird);

    assertThat(journeyProposalsThird).usingRecursiveComparison().isEqualTo(journeyProposalsFilteredThird);
  }

  @Test
  void shouldFilterNonRelevantJourneysFourth(){
    JourneyProposalsDTO journeyProposalsFourth = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample4_navitia_journey");
    JourneyProposalsDTO journeyProposalsFilteredFourth = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample4_navitia_journey_filtered");

    journeyProposalsFourth = navitiaService.filterNonRelevantJourneys(journeyProposalsFourth);

    assertThat(journeyProposalsFourth).usingRecursiveComparison().isEqualTo(journeyProposalsFilteredFourth);
  }

  @Test
  void shouldFilterNonRelevantJourneysFifth(){
    JourneyProposalsDTO journeyProposalsFifth = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample5_navitia_journey");
    JourneyProposalsDTO journeyProposalsFilteredFifth = mockLoaderService.getJourneyProposalsDTOFromNavitiaMock("sample5_navitia_journey_filtered");

    journeyProposalsFifth = navitiaService.filterNonRelevantJourneys(journeyProposalsFifth);

    assertThat(journeyProposalsFifth).usingRecursiveComparison().isEqualTo(journeyProposalsFilteredFifth);
  }
}
