package com.chicmic.sbthene.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long adminId;

    private String name;
    @NotNull
    private String email;
    private String password;
    public String role;
    public boolean enabled;
    private String number;
    @JsonFormat(pattern="dd/mm/yyyy")
    private Date dateOfBirth;
    private byte[] profilePhoto;
    private String image;

    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy = "admin")
    @JsonIgnore
    private Set<UserToken> userTokenSet=new HashSet<>();

}
