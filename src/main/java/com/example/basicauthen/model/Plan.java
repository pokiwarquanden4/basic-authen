package com.example.basicauthen.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plans")
@Data
public class Plan implements Serializable {
    private static final long serialVersionUID = -3125367246396011635L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "limit_per_hour", nullable = false, unique = true)
    private Integer limitPerHour;
}
