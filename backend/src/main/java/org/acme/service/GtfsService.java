package org.acme.service;

import java.io.File;
import java.sql.Date;
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

import org.jboss.logging.Logger;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Transfer;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.serialization.mappings.StopTimeFieldMappingFactory;


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

            System.out.println("✅ GTFS chargé : " + store.getAllStops().size() + " arrêts, " +
                              store.getAllRoutes().size() + " routes, " +
                              store.getAllTrips().size() + " trajets" +
                              store.getAllTransfers().size() + " transfers.");
        } catch (Exception e) {
              System.err.println("❌ Erreur lors du chargement du GTFS : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Comment donner la direction ?
    public void getScheduledTripsFromTo(String departureStopId, String arrivalStopId){
      // Récupération des stopTime de la gare de départ(stopSequence == 1)
      List<StopTime> stopTimeDeparture = this.stopTimes.stream()
                  .filter(stop -> stop.getStop().getId().getId().contentEquals(departureStopId) && stop.getStopSequence() == 1)
                  .collect(Collectors.toList());

      // Récupération des stopTime de la gare d'arrivée (stopSequence == 2)
      List<StopTime> stopTimeArrival = this.stopTimes.stream()
                  .filter(stop -> stop.getStop().getId().getId().contentEquals(arrivalStopId) && stop.getStopSequence() == 2)
                  .collect(Collectors.toList());

      // Je ne garde que les stop times où departureStopId a le numéro 1
      // Je cherche le stopTime avec le trip id 11806
      StopTime arret1 = stopTimeDeparture.stream()
                  .filter(s -> s.getTrip().getId().getId().contentEquals("11806"))
                  .findAny()
                  .orElseThrow(() -> new IllegalStateException("StopTime introuvable pour l’ID 11806"));

      StopTime arret2 = stopTimeArrival.stream()
                  .filter(s -> s.getTrip().getId().getId().contentEquals("11806"))
                  .findAny()
                  .orElseThrow(() -> new IllegalStateException("StopTime introuvable pour l’ID 11806"));

      System.out.println("Trajet: " + StopTimeFieldMappingFactory.getSecondsAsString(arret1.getArrivalTime()) + " - " + arret2);
      System.out.println("Nombre d'horaires au départ de Belle-Ile : " + stopTimeDeparture.size());

      for(StopTime st : stopTimeDeparture){
        System.out.println(st);
      }
      // Trajet: StopTime(seq=1 stop=29_I56BIP trip=29_11806 times=12:45:00-12:45:00) - StopTime(seq=2 stop=29_I56QUI trip=29_11806 times=13:35:00-13:35:00)
    }

    // Récupération des trajets Quiberon -> Belle ile
    public List<Integer> getTripsFromTo(String departureId, String arrivalId){
      // departureId a un sequenceStop à 1
      // arrivalId a un sequenceStop à 2
      List<StopTime> stopTimesFromTo = this.stopTimes.stream()
                  .filter(st -> (st.getStop().getId().getId().equals(departureId) && st.getStopSequence() == 1)
                    || (st.getStop().getId().getId().equals(arrivalId) && st.getStopSequence() == 2))
                  .collect(Collectors.toList());

      // J'ai une liste avec tous les trip_id depart -> arrivée
      LocalDate travelDate = LocalDate.of(2025, 10, 11);

      // Je récupère tous les service_id qui correspondent à ma date (via Calendar)
      List<ServiceCalendar> servicesOnDate = this.calendars.stream()
                              .filter(c -> {
                                LocalDate start = c.getStartDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                LocalDate end = c.getEndDate().getAsDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                return ( !start.isAfter(travelDate) && !end.isBefore(travelDate) );
                              })
                              .collect(Collectors.toList());

                                
      // System.out.println("Nombre de stop Q & B: " + stops.size());
      // for(StopTime element : stopTimesFromTo){
      //   System.out.println(element);
      // }

      System.out.println("Nombre de service trouvés qui correspondent à " + travelDate + " : " + servicesOnDate.size());
      for(ServiceCalendar service : servicesOnDate){
        System.out.println(service);
      }

      return new ArrayList<Integer>();
    }

    public Collection<StopTime> getStopTimes(){
      return this.stopTimes;
    }

    // public List<Trip> getTrips(){
    //   //List<Trip> routeIds = this.trips.stream().filter(element -> element.getRoute().getId().getId().contentEquals("2") ).collect(Collectors.toList());
    //   List<Trip> quiberon_bi = this.trips.stream().filter(element -> element.getRoute().getLongName().contentEquals("Quiberon <> Belle-Île-en-Mer")).collect(Collectors.toList());
    //   // System.out.println("Nombre de trips avec longName Quiberon <> Belle-Île-en-Mer:" + quiberon_bi.size());

    //   return quiberon_bi;
    // }

    // public Collection<Stop> getStops(){
    //   return this.stops;
    // }



}
