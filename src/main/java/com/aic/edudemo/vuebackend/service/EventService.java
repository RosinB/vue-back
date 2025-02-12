package com.aic.edudemo.vuebackend.service;

import com.aic.edudemo.vuebackend.domain.dto.EventAddDto;
import com.aic.edudemo.vuebackend.domain.dto.EventListDto;
import com.aic.edudemo.vuebackend.domain.entity.Event;
import com.aic.edudemo.vuebackend.domain.entity.Pic;
import com.aic.edudemo.vuebackend.repository.EventRepository;
import com.aic.edudemo.vuebackend.repository.PicRepository;
import com.aic.edudemo.vuebackend.utils.mapstruct.EventMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final PicRepository picRepository;
    private final EventMapper eventMapper;
    private static final String UPLOAD_DIR = "C:\\Users\\Rosin Huang\\Desktop\\vue-front\\client\\public\\img\\";

    public List<EventListDto> getEventList(){
        try{
            return eventRepository.findAllEventPics();
        } catch (RuntimeException e) {
            log.error("資料庫抓取錯誤{}",e.getMessage());
            return Collections.emptyList();

        }
    }

    @Transactional
    public void addEvent(EventAddDto dto,MultipartFile file){


        try{
            Event event = eventMapper.toEntity(dto);
            Event saveEvent = eventRepository.save(event);


            String pathName=saveImage(file);
            System.out.println("路徑是"+pathName);
            Pic pic=new Pic( null, saveEvent.getEventId(), pathName,pathName,pathName);
            picRepository.save(pic);
        } catch (Exception e) {
           log.error("錯誤{}",e.getMessage());
        }


    }


    public List<Pic> getPicList(){
        try{
            return  picRepository.findAll();
        } catch (Exception e) {
            log.error("錯誤{}",e.getMessage());
           return Collections.emptyList();
        }
    }



    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("檔案為空，無法儲存！");
        }

        Files.createDirectories(Paths.get(UPLOAD_DIR)); // 確保資料夾存在
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = UPLOAD_DIR + fileName;

        file.transferTo(new File(filePath)); // 儲存圖片

        return "img/" + fileName; // 回傳圖片相對路徑，讓前端可以使用
    }
}



