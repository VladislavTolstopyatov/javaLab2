package com.example.javalab2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ActorDto {
    private Long id;
    private String fio;
    private LocalDate birthdate;
}
