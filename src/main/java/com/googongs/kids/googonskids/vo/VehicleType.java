package com.googongs.kids.googonskids.vo;

import javax.persistence.*;

@Table
@Entity
public class VehicleType {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String brand;

    @Column
    private String name;
}
