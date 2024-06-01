package com.example.javalab2.dto.MoviesDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CreateMovieDto {
    private String title;
    private String description;
    private String genre;
    private Long directorId;
    private LocalDate dateOfRelease;
    private Integer duration;
}
