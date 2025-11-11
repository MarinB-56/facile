package org.acme.service;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.dto.DisplayInformationsDTO;
import org.acme.dto.JourneyDTO;
import org.acme.dto.SectionDTO;
import org.acme.dto.StopPointDTO;
import org.acme.dto.TripDTO;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.serialization.GtfsReader;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class GtfsService {

    // private static final Logger LOG = Logger.getLogger(GtfsService.class);
    private Collection<Stop> stops;
    private Collection<org.onebusaway.gtfs.model.Trip> trips;
    private Collection<org.onebusaway.gtfs.model.Route> routes;
    private org.onebusaway.gtfs.model.Route agency;
    private Collection<StopTime> stopTimes;
    private Collection<ServiceCalendar> calendars;
    private Collection<ServiceCalendarDate> calendarDates;

    private String BELLE_ILE_ID = "I56BIP";
    private String QUIBERON_ID = "I56QUI";

    private Set<SectionDTO> gtfsSections;

    @PostConstruct
    public void init() {
      System.out.println("Initialisation de GTFS Service");
        try {
            File gtfsFile = new File("data/BREIZHGO_BATEAU_56.gtfs.zip");

            GtfsReader reader = new GtfsReader();
            reader.setInputLocation(gtfsFile);

            GtfsDaoImpl store = new GtfsDaoImpl();
            reader.setEntityStore(store);

            reader.run();

            this.stops = store.getAllStops();
            this.trips = store.getAllTrips();
            this.routes = store.getAllRoutes();
            this.stopTimes = store.getAllStopTimes();
            this.calendars = store.getAllCalendars();
            this.calendarDates = store.getAllCalendarDates();

            System.out.println("✅ GTFS chargé : " + store.getAllStops().size() + " arrêts, " +
                              store.getAllRoutes().size() + " routes, " +
                              store.getAllTrips().size() + " trajets" +
                              store.getAllTransfers().size() + " transfers.");
        } catch (Exception e) {
              System.err.println("❌ Erreur lors du chargement du GTFS : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Récupération des trajets en Bateau
    public  Map<String, List<StopTime>> getTripsFromTo(TripDTO trip){

      String departureGtfsId;
      String arrivalGtfsId;
      LocalDateTime travelDate = trip.getDate();

      if(trip.getDeparture().getName().contains("Quiberon")){
        // On quitte Belle ile
        departureGtfsId = this.BELLE_ILE_ID;
        arrivalGtfsId = this.QUIBERON_ID;
      }else{
        departureGtfsId = this.QUIBERON_ID;
        arrivalGtfsId = this.BELLE_ILE_ID;
      }

      // 1) Récupération des trip_id qui correspondent à un trajet depart -> arrivée
      Map<String, List<StopTime>> stopTimesFromTo = this.stopTimes.stream()
                                        .filter(st -> (st.getStop().getId().getId().equals(departureGtfsId) && st.getStopSequence() == 1)
                                          || (st.getStop().getId().getId().equals(arrivalGtfsId) && st.getStopSequence() == 2))
                                        .collect(Collectors.groupingBy(st -> st.getTrip().getId().getId()));
      // ==> On a une collection de stopTimes qui sont reliés à des trip_id (map)

      // Suppression des trajets avec un seul stopTime (signifie que le départ ou l'arrivée ne concerne pas un trajet départ -> arrivée)
      // ex: Quiberon -> Houat (Quiberon apparait bien comme un départ (avec stop sequence à 1) mais n'est pas relié à un trajet avec notre arrivée)
      stopTimesFromTo.entrySet().removeIf(element -> element.getValue().size() != 2);

      // 2) Récupération des services qui sont supprimés à la date recherchée
      Set<String> deletedServicesOnDate = this.calendarDates.stream()
                                  .filter(cd -> {
                                    LocalDate date = cd.getDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    return (date.equals(travelDate.toLocalDate()));
                                  })
                                  .map(c -> c.getServiceId().getId())
                                  .collect(Collectors.toSet());

      // 3) Récupération des services qui opèrent le jour demandé
      DayOfWeek weekDay = travelDate.getDayOfWeek();

      // Liste de service_id (services opérant le jour demandé)
      Set<String> validServicesOnDate = this.calendars.stream()
                    .filter(c -> {
                        LocalDate start = c.getStartDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate end = c.getEndDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (start.isAfter(travelDate.toLocalDate()) || end.isBefore(travelDate.toLocalDate())) return false;

                        // Vérifier le jour de la semaine
                        switch(weekDay) {
                            case MONDAY: return c.getMonday() == 1;
                            case TUESDAY: return c.getTuesday() == 1;
                            case WEDNESDAY: return c.getWednesday() == 1;
                            case THURSDAY: return c.getThursday() == 1;
                            case FRIDAY: return c.getFriday() == 1;
                            case SATURDAY: return c.getSaturday() == 1;
                            case SUNDAY: return c.getSunday() == 1;
                            default: return false;
                        }
                    })
                    .filter(c -> !deletedServicesOnDate.contains(c.getServiceId().getId()))
                    .map(c -> c.getServiceId().getId())
                    .collect(Collectors.toSet());

      // 4) Recoupage des StopTime départ -> arrivée avec (les services opérant - les services supprimés)
      Map<String, List<StopTime>> validTrips = this.trips.stream()
                      .filter(t -> validServicesOnDate.contains(t.getServiceId().getId()))
                      .filter(t -> stopTimesFromTo.keySet().contains(t.getId().getId()))
                      .collect(Collectors.toMap(
                        t -> t.getId().getId(),
                        t -> stopTimesFromTo.get(t.getId().getId())
                      ));

      // 5) On supprime les trajets qui sont avant l'heure demandée
      Map<String, List<StopTime>> filteredTrips = validTrips.entrySet().stream()
        .filter(entry -> {
          // Find the departure StopTime (stopSequence == 1)
          StopTime departureStopTime = entry.getValue().stream()
            .filter(st -> st.getStopSequence() == 1)
            .findFirst()
            .orElse(null);
          if (departureStopTime == null) return false;
          // Convert GTFS time (seconds since midnight) to LocalDateTime
          int departureSeconds = departureStopTime.getDepartureTime();
          LocalDate tripDate = trip.getDate().toLocalDate();
          LocalDateTime departureDateTime = tripDate.atStartOfDay().plusSeconds(departureSeconds);
          return !departureDateTime.isBefore(trip.getDate());
        })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

      return filteredTrips;
    }

    public Set<SectionDTO> getSectionsFromGtfsData(TripDTO trip){

      System.out.println("1");
      Map<String, List<StopTime>> gtfsValidTrips = getTripsFromTo(trip);
      System.out.println("2");
      // On récupère les block ID et on les associe aux trajets ID (pour récupérer le nom du bateau)
      Map<String, String> tripBlockIds = this.trips.stream()
                      .filter(t -> gtfsValidTrips.keySet().contains(t.getId().getId()))
                      .collect(Collectors.toMap(
                        t -> t.getId().getId(),
                        t -> t.getBlockId())
                      );

      for(String key : tripBlockIds.keySet()){
        System.out.println(tripBlockIds.get(key));
      }

      System.out.println("Nombre de bateaux trouvés:  " + gtfsValidTrips.size());
      Set<SectionDTO> gtfsSections = new HashSet<SectionDTO>();

      // Pour unifier le format de LocalDateTime (yyyyMMdd'T'HHmmss)
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

      // Formatage de gtfsValidTrips en SectionDTO
      // for (List<StopTime> stopTime : gtfsValidTrips.values()) {
      for(String key : gtfsValidTrips.keySet()){

        List<StopTime> stopTimeList = gtfsValidTrips.get(key);
        String tripBlockId = tripBlockIds.get(key);

        SectionDTO section = new SectionDTO();

        StopTime departure = stopTimeList.get(0);
        StopTime arrival = stopTimeList.get(1);

        StopPointDTO from = new StopPointDTO();
        from.setId(departure.getStop().getId().getId()); // QUI56
        from.setName( this.stops.stream()
                        .filter(s -> s.getId().getId().equals(from.getId()))
                        .map(Stop::getName)
                        .findFirst()
                        .orElse(null)
                    );
        from.embeddedType = "harbor";

        StopPointDTO to = new StopPointDTO();
        to.setId(arrival.getStop().getId().getId()); // BI56
        to.setName( this.stops.stream()
                        .filter(s -> s.getId().getId().equals(to.getId()))
                        .map(Stop::getName)
                        .findFirst()
                        .orElse(null)
                  );
        from.embeddedType = "harbor";

        DisplayInformationsDTO informations = new DisplayInformationsDTO();
        informations.setCompany("BreizhGo Océane");
        informations.setPhysicalMode("Bateau");

        if(tripBlockId.contains("VDLS")){
          informations.setNetwork("Vindilis");
        }else if(tripBlockId.contains("BNGR")){
          informations.setNetwork("Bangor");
        }else if(tripBlockId.contains("IDGX")){
          informations.setNetwork("Île de Groix");
        }else{
          informations.setNetwork("Bateau inconnu");
        }

        LocalTime departureTime = LocalTime.ofSecondOfDay(departure.getDepartureTime());
        LocalTime arrivalTime = LocalTime.ofSecondOfDay(arrival.getArrivalTime());

        LocalDateTime tripDate = trip.getDate();

        LocalDateTime departureDateTime = tripDate.toLocalDate().atTime(departureTime);
        LocalDateTime arrivLocalDateTime = tripDate.toLocalDate().atTime(arrivalTime);

        section.setFrom(from);
        section.setTo(to);

        section.setDepartureDateTime(departureDateTime.format(formatter));
        section.setArrivalDateTime(arrivLocalDateTime.format(formatter));

        section.setSectionDuration(arrival.getArrivalTime() - departure.getDepartureTime());

        section.setType("public_transport");
        section.setDisplayInformationsDTO(informations);

        System.out.print(section.getDepartureDateTime() + " " + section.getArrivalDateTime());
        System.out.println(section.getDisplayInformationsDTO().getNetwork());

        gtfsSections.add(section);
      }


      // Tri des sections par heure de départ
      List<SectionDTO> sortedSections = new ArrayList<>(gtfsSections);

      sortedSections.sort(Comparator.comparing(
          SectionDTO::getDepartureDateTime,
          Comparator.nullsLast(Comparator.naturalOrder())
      ));

      // ✅ On reconvertit la liste triée en Set en gardant l'ordre
      gtfsSections = new LinkedHashSet<>(sortedSections);

      return gtfsSections;
    }

    public Collection<StopTime> getStopTimes(){
      return this.stopTimes;
    }

}
