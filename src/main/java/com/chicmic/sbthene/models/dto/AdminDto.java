package com.chicmic.sbthene.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
@Data
@RequiredArgsConstructor
public class AdminDto {
    private Long adminId;

    private String name;
    @NotNull
    private String email;
    private Integer number;
    @JsonFormat(pattern="dd/mm/yyyy")
    private Date datebirth;
    private byte[] profilePhoto;
}
