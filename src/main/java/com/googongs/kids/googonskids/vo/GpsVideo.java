package com.googongs.kids.googonskids.vo;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class GpsVideo {

    @Id
    private Long id;

    @Column
    private Float x;

    @Column
    private Float y;

    @Column
    private Integer second;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Video video;
}
