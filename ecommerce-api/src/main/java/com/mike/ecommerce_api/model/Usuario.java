package com.mike.ecommerce_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique=true, nullable = false)
    private String email;

    private String password;

    private String role; // user or admin

}
