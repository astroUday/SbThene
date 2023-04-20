package com.chicmic.sbthene.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    private String role;
    private String name;
    private String email;
    private String phone;
    private boolean enabled;
    private boolean softDeleted;

}
