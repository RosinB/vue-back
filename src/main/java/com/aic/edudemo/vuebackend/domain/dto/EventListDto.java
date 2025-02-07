package com.aic.edudemo.vuebackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventListDto {
    private Integer eventId;

    private String eventName;

    private LocalDate eventDate;

    private LocalTime eventTime;
    private String eventTicketPic;
    private String eventTicketList;

    private String eventPerformer;
}
