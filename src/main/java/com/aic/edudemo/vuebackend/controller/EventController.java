package com.aic.edudemo.vuebackend.controller;


import com.aic.edudemo.vuebackend.service.EventService;
import com.aic.edudemo.vuebackend.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping( "/event")
@RequiredArgsConstructor
public class EventController {


    private final EventService eventService;
    @GetMapping("/eventpic")
    ResponseEntity<ApiResponse<Object>> getEventsPic(){

        System.out.println("tes123");
        return ResponseEntity.ok(ApiResponse.success("success",eventService.getEventList()));

    }

}
