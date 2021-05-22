package com.googongs.kids.googonskids.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
