package com.aic.edudemo.vuebackend.repository;

import com.aic.edudemo.vuebackend.domain.dto.EventListDto;
import com.aic.edudemo.vuebackend.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository  extends JpaRepository<Event, Integer> {

    @Query("SELECT new com.aic.edudemo.vuebackend.domain.dto.EventListDto(" +
           "p.picId, " +
           "e.eventName, " +
           "e.eventDate, " +
           "e.eventTime, " +
           "p.picEventTicket, " +
           "p.picEventList, " +
           "e.eventPerformer) " +
           "FROM Pic p " +
           "JOIN Event e ON p.eventId = e.eventId")
    List<EventListDto> findAllEventPics();



}
