package com.aic.edudemo.vuebackend.controller;


import com.aic.edudemo.vuebackend.domain.dto.EventAddDto;
import com.aic.edudemo.vuebackend.domain.entity.Event;
import com.aic.edudemo.vuebackend.service.EventService;
import com.aic.edudemo.vuebackend.utils.ApiResponse;
import com.aic.edudemo.vuebackend.utils.mapstruct.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping( "/event")
@RequiredArgsConstructor
public class EventController {


    private final EventService eventService;
    private final EventMapper eventMapper;
    @GetMapping("/eventpic")
    ResponseEntity<ApiResponse<Object>> getEventsPic(){

        return ResponseEntity.ok(ApiResponse.success("success",eventService.getEventList()));

    }

    @GetMapping("/allpic")
    ResponseEntity<ApiResponse<Object>> getAllEventsPic(){

        return ResponseEntity.ok(ApiResponse.success("success",eventService.getPicList()));
    }

    @PostMapping("/add")
    ResponseEntity<ApiResponse<Object>> postEvent(
            @ModelAttribute EventAddDto dto, // 自動解析 JSON
            @RequestParam(value = "eventTicketPic", required = false) MultipartFile eventTicketPic

    ){
        System.out.println(dto.toString()+"圖片"+eventTicketPic);
        eventService.addEvent(dto,eventTicketPic);
        return ResponseEntity.ok(ApiResponse.success("success",null));

    }








}

