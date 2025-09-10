package org.acme.dto;

// Class pour garder en mémoire les éléments de compatibilité entre deux gares
public record TransferCompatibility(
  boolean isTransferPossible,
  boolean areSameStations,
  long transferDurationInSeconds, // en secondes
  SectionDTO arrivalSection,
  SectionDTO departureSection
) {}
