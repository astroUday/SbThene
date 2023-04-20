package com.chicmic.sbthene.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user-token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uuidId;

    private String uuid;

    @ManyToOne
    @JsonIgnore
    private Admin admin;


}
