package com.aic.edudemo.vuebackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "event_performer")
    private String eventPerformer;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name="event_time")
    private LocalTime eventTime;

    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "event_status")
    private String eventStatus;

    @Column(name = "host_id")
    private Integer hostId;

    @Column(name="event_salesdate")
    private LocalDate eventSalesDate;

    @Column(name="event_salestime")
    private LocalTime eventSalesTime;



}