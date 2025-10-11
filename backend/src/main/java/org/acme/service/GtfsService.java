package org.acme.service;

import java.io.File;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    public List<Trip> getTripsFromTo(String departureId, String arrivalId){
      // Récupération des trips departure -> arrival (trip_id)
      Set<String> tripsId = this.stopTimes.stream()
                .filter(st -> (st.getStop().getId().getId().equals(departureId) && st.getStopSequence() == 1)
                          || (st.getStop().getId().getId().equals(arrivalId) && st.getStopSequence() == 2))
                .collect(Collectors.groupingBy(st -> st.getTrip().getId().getId()))
                .values().stream()
                .filter(list -> list.size() == 2) // ne garder que les trip_id présents 2 fois (1 departure & 1 arrival)
                .flatMap(List::stream)
                .map(c -> c.getTrip().getId().getId())
                .collect(Collectors.toSet());

      LocalDate travelDate = LocalDate.of(2025, 10, 12);

      // Je récupère tous les service_id qui sont supprimés à la date recherchée (via calendar_dates)
      Set<String> servicesDeletedOnDate = this.calendarDates.stream()
                                  .filter(cd -> {
                                    LocalDate date = cd.getDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    return (date.equals(travelDate));
                                  })
                                  .map(c -> c.getServiceId().getId())
                                  .collect(Collectors.toSet());

      // System.out.println("Nombre de service id supprimés le " + travelDate + ": " + servicesDeletedOnDate.size());
      // for(String s : servicesDeletedOnDate){
      //   System.out.println(s);
      // }

      // Pour avoir les jours de la semaine
      DayOfWeek day = travelDate.getDayOfWeek();

      // Je récupère tous les service_id qui sont prévus à la date recherchée (via Calendar)
      Set<String> servicesOnDate = this.calendars.stream()
                    .filter(c -> {
                        LocalDate start = c.getStartDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate end = c.getEndDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (start.isAfter(travelDate) || end.isBefore(travelDate)) return false;

                        // Vérifier le jour de la semaine
                        switch(day) {
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
                    .map(c -> c.getServiceId().getId())
                    .collect(Collectors.toSet());

      // Je recoupe les dates prévues et les dates supprimées pour avoir les dates effectives
      List<Trip> validTrips = this.trips.stream()
                      .filter(t -> servicesOnDate.contains(t.getServiceId().getId()))
                      .filter(t -> !servicesDeletedOnDate.contains(t.getServiceId().getId()))
                      .filter(t -> tripsId.contains(t.getId().getId()))
                      .collect(Collectors.toList());

      System.out.println("Nombre de trips valides: " + validTrips.size());
      for(Trip t : validTrips){
        System.out.println(t.getId().getId());
      }

      return validTrips;
    }

    public Collection<StopTime> getStopTimes(){
      return this.stopTimes;
    }

}
