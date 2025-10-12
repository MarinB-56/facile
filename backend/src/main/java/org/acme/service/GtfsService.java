package org.acme.service;

import java.io.File;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Set;

import org.jboss.logging.Logger;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Transfer;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.serialization.mappings.StopTimeFieldMappingFactory;

import com.aayushatharva.brotli4j.common.annotations.Local;

import io.quarkus.runtime.Startup;
import io.vertx.mutiny.ext.web.Route;
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

    @PostConstruct
    public void init() {
      // LOG.info("Initialisation de GTFS");
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

    // Récupération des trajets Quiberon -> Belle ile
    public  Map<String, List<StopTime>> getTripsFromTo(String departureId, String arrivalId){
      /*
       *  TODO : Déplacer traverDate en argument de la fonction
       */
      LocalDate travelDate = LocalDate.of(2025, 10, 12);

      // 1) Récupération des trip_id qui correspondent à un trajet depart -> arrivée
      Map<String, List<StopTime>> stopTimesFromTo = this.stopTimes.stream()
                                        .filter(st -> (st.getStop().getId().getId().equals(departureId) && st.getStopSequence() == 1)
                                          || (st.getStop().getId().getId().equals(arrivalId) && st.getStopSequence() == 2))
                                        .collect(Collectors.groupingBy(st -> st.getTrip().getId().getId()));
      // ==> On a une collection de stopTimes qui sont reliés à des trip_id (map)

      // Suppression des trajets avec un seul stopTime (signifie que le départ ou l'arrivée ne concerne pas un trajet départ -> arrivée)
      // ex: Quiberon -> Houat (Quiberon apparait bien comme un départ (avec stop sequence à 1) mais n'est pas relié à un trajet avec notre arrivée)
      stopTimesFromTo.entrySet().removeIf(element -> element.getValue().size() != 2);

      // 2) Récupération des services qui opèrent le jour demandé
      DayOfWeek weekDay = travelDate.getDayOfWeek();

      // 3) Récupération des services qui sont supprimés à la date recherchée
      Set<String> deletedServicesOnDate = this.calendarDates.stream()
                                  .filter(cd -> {
                                    LocalDate date = cd.getDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    return (date.equals(travelDate));
                                  })
                                  .map(c -> c.getServiceId().getId())
                                  .collect(Collectors.toSet());

      // Liste de service_id (services opérant le jour demandé)
      Set<String> validServicesOnDate = this.calendars.stream()
                    .filter(c -> {
                        LocalDate start = c.getStartDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate end = c.getEndDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (start.isAfter(travelDate) || end.isBefore(travelDate)) return false;

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

      // 4) Recoupage des StopTime départ -> arrivée avec les services opérant - les services supprimés
      Map<String, List<StopTime>> validTrips = this.trips.stream()
                      .filter(t -> validServicesOnDate.contains(t.getServiceId().getId()))
                      .filter(t -> stopTimesFromTo.keySet().contains(t.getId().getId()))
                      .collect(Collectors.toMap(
                        t -> t.getId().getId(),
                        t -> stopTimesFromTo.get(t.getId().getId())
                      ));

      return validTrips;
    }

    public Collection<StopTime> getStopTimes(){
      return this.stopTimes;
    }

}
