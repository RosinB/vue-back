package com.aic.edudemo.vuebackend.service;

import com.aic.edudemo.vuebackend.domain.dto.EventListDto;
import com.aic.edudemo.vuebackend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;


    public List<EventListDto> getEventList(){

        try{

            log.info("抓取成功");
            List<EventListDto> dto=eventRepository.findAllEventPics();

            return dto;

        } catch (RuntimeException e) {
            e.printStackTrace();  // ✅ 印出完整的錯誤訊息

            log.error("資料庫抓取錯誤"+e.getMessage());
            return  null;
        }


    }

}
