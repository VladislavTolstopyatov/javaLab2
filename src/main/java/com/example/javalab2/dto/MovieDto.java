package com.example.javalab2.dto;

import com.example.javalab2.entities.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private String directorFio;
    private LocalDate dateOfRelease;
    private Integer duration;
    List<FeedBackDto> feedBackDtoList;
}
