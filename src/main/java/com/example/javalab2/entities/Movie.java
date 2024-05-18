package com.example.javalab2.entities;

import com.example.javalab2.entities.enums.Genre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @NotNull
    @Column(name = "title", unique = true)
    private String title;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @NotNull
    @Column(name = "date_of_release")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfRelease;

    @NotNull
    @Column(name = "duration")
    private Integer duration;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "movie")
    private List<Feedback> feedbacks = new ArrayList<>();
}
