package com.aic.edudemo.vuebackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="pic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pic {

    @Id
    @Column(name="pic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer picId;

    //活動Id
    @Column(name="event_id")
    private Integer eventId;


    @Column(name="pic_event_ticket")
    private String	picEventTicket ;

    @Column(name="pic_event_list")
    private String picEventList;

    @Column(name="pic_event_section")
    private String picEventSection;


}