package com.googongs.kids.googonskids.vo;

import javax.persistence.*;

@Table
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

}
