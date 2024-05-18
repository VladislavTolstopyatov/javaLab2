package com.example.javalab2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackDto {
    private Long id;
    private String title;
    private String nickName;
    private LocalDate feedbackDate;
    private String feedback;
}
