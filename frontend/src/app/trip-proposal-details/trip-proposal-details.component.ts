import { Component } from '@angular/core';
import { Journey } from '../models/journey.model';
import { DurationPipePipe } from '../pipes/duration-pipe/duration-pipe.pipe';
import { ConnectionPipePipe } from '../pipes/connection-pipe/connection-pipe.pipe';
import { TripProposalSectionComponent } from "./trip-proposal-section/trip-proposal-section.component";
import { TripHeaderComponentComponent } from '../trip-header-component/trip-header-component.component';

@Component({
  selector: 'app-trip-proposal-details',
  imports: [
    DurationPipePipe,
    ConnectionPipePipe,
    TripProposalSectionComponent,
    TripHeaderComponentComponent
  ],
  templateUrl: './trip-proposal-details.component.html',
  styleUrl: './trip-proposal-details.component.scss'
})
export class TripProposalDetailsComponent {
  journey: Journey | undefined;

  ngOnInit(){
    this.journey = history.state.journey;

    console.log(this.journey);


    // Arbitrairement, on met toutes les sections à train
    // if (this.journey?.sections) {
    //   for (const section of this.journey.sections) {
    //     if(section.type === "Boat"){
    //       // Do something with section
    //       console.log("OUI MINS");
    //       section.type = "public_transport"
    //       section.transport_type = "boat";
    //     }else{
    //       section.transport_type = "train";
    //     }
    //     console.log(section);
    //   }
    // }
  }
}


  // trip: Journey = {
  //   duration: 28140,
  //   durations : {
  //     total: 28140,
  //     walking: -3960,
  //   },
  //   journeyFirstSection: {
  //     arrival_date_time: "20250922T095100",
  //     departure_date_time: "20250922T054900",
  //     duration: 14520,
  //     from: {
  //       embedded_type: "stop_point",
  //       id: "stop_point:SNCF:87755009:LongDistanceTrain",
  //       name: "Toulon (Toulon)"
  //     },
  //     sectionDuration: 14520,
  //     to: {
  //       embedded_type: "stop_point",
  //       id: "stop_point:SNCF:87686006:LongDistanceTrain",
  //       name : "Paris - Gare de Lyon - Hall 1 & 2 (Paris)"
  //     },
  //     type: "public_transport",
  //     transporter: ""
  //   },
  //   journeyLastSection: {
  //     type : "public_transport",
  //     sectionDuration : 9660,
  //     duration : 9660,
  //     from: {
  //       id : "stop_point:SNCF:87391003:LongDistanceTrain",
  //       name : "Paris - Montparnasse - Hall 1 & 2 (Paris)",
  //       embedded_type : "stop_point"
  //     },
  //     to: {
  //       id : "stop_point:SNCF:87476200:LongDistanceTrain",
  //       name : "Auray (Auray)",
  //       embedded_type : "stop_point"
  //     },
  //     departure_date_time : "20250922T105700",
  //     arrival_date_time : "20250922T133800"
  //   },
  //   firstDeparturDateTime : "2025-09-22T05:49:00",
  //   nb_transfers : 1,
  //   sections: [
  //     {
  //       from: {
  //         id : "stop_point:SNCF:87755009:LongDistanceTrain",
  //         name : "Toulon (Toulon)",
  //         embedded_type : "stop_point"
  //       },
  //         to : {
  //           id : "stop_point:SNCF:87686006:LongDistanceTrain",
  //           name : "Paris - Gare de Lyon - Hall 1 & 2 (Paris)",
  //           embedded_type : "stop_point"
  //         },
  //         type : "public_transport",
  //         transporter: "SNCF",
  //         sectionDuration : 14520,
  //         duration : 14520,
  //         departure_date_time : "20250922T054900",
  //         arrival_date_time : "20250922T095100"
  //     },
  //     {
  //       from : {
  //         id : "stop_point:SNCF:87686006:LongDistanceTrain",
  //         name : "Paris - Gare de Lyon - Hall 1 & 2 (Paris)",
  //         embedded_type : "stop_point"
  //       },
  //       to : {
  //         id : "stop_point:SNCF:87391003:LongDistanceTrain",
  //         name : "Paris - Montparnasse - Hall 1 & 2 (Paris)",
  //         embedded_type : "stop_point"
  //       },
  //       type : "Walking",
  //       sectionDuration : 3960,
  //       duration : 3960,
  //       departure_date_time : "20250922T105700",
  //       arrival_date_time : "20250922T095100"
  //     },
  //     {
  //       from : {
  //         id : "stop_point:SNCF:87391003:LongDistanceTrain",
  //         name : "Paris - Montparnasse - Hall 1 & 2 (Paris)",
  //         embedded_type : "stop_point"
  //       },
  //       to: {
  //         id : "stop_point:SNCF:87476200:LongDistanceTrain",
  //         name : "Auray (Auray)",
  //         embedded_type : "stop_point"
  //       },
  //       type : "public_transport",
  //       sectionDuration : 9660,
  //       duration : 9660,
  //       departure_date_time : "20250922T105700",
  //       arrival_date_time : "20250922T133800"
  //     }
  //   ]
  // }
