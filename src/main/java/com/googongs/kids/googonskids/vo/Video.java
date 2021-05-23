package com.googongs.kids.googonskids.vo;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
public class Video {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private User user;

    @DateTimeFormat
    private LocalDateTime drivingDate;

    @CreationTimestamp
    private LocalDateTime uploadedAt;
}
