package com.example.javalab2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DirectorDto {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthdate;
    private Boolean oscar;
    private List<MovieDto> movieDtoList;
}
