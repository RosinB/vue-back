package com.aic.edudemo.vuebackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventAddDto {

    private Integer eventId;

    private String eventPerformer;

    private String eventName;

    private String eventDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate eventDate;

    private LocalTime eventTime;

    private String eventLocation;

    private String eventType;

    private String eventStatus;

    private Integer hostId;

    private String	picEventTicket ;




}
