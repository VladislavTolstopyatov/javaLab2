package com.example.javalab2.controller;

import com.example.javalab2.dto.FeedbackDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.FeedBackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Feedback Controller",description = "Обрабатывает запросы, связанные с отзывами к фильмам")
@RestController
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedBackService feedBackService;

    @Operation(summary = "Добавить отзыв к фильму по id фильма")
    @PostMapping("movies/movie/{id}/feedbacks")
    public ResponseEntity<String> saveFeedback(@RequestBody FeedbackDto feedbackDto) {
        feedBackService.saveFeedBack(feedbackDto);
        return new ResponseEntity<>("Feedback saved successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Получить все отзывы")
    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackDto>> getAllFeedbacks() {
        return new ResponseEntity<>(feedBackService.findAllFeedBacks(), HttpStatus.OK);
    }

    @Operation(summary = "Получить отзыв по id")
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable("id") Long feedbackId) throws EntityNotFoundException {
        return new ResponseEntity<>(feedBackService.findFeedBackById(feedbackId), HttpStatus.OK);
    }

    @Operation(summary = "Получить отзывы пользователя по id пользователя")
    @GetMapping("/feedbacks/userId/{id}")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByUserId(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(feedBackService.findFeedBacksByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Получить отзывы о фильмы по id фильма")
    @GetMapping("movies/movie/{id}/feedbacks")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByMovieId(@PathVariable("id") Long movieId) {
        return new ResponseEntity<>(feedBackService.findFeedBacksByMovieId(movieId), HttpStatus.OK);
    }

    @Operation(summary = "Удалить все отзывы")
    @DeleteMapping("/feedbacks")
    public ResponseEntity<String> deleteAllFeedbacks() {
        feedBackService.deleteAllFeedBacks();
        return new ResponseEntity<>("All feedbacks have been deleted", HttpStatus.OK);
    }

    @Operation(summary = "Удалить отзыв по id")
    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<String> deleteFeedbackById(@PathVariable("id") Long feedbackId) {
        feedBackService.deleteFeedBackById(feedbackId);
        return new ResponseEntity<>(String.format("Feedback with id %d have been deleted", feedbackId), HttpStatus.OK);
    }

    @Operation(summary = "Удалить отзывы у фильма по id фильма")
    @DeleteMapping("/feedbacks/movieId/{id}")
    public ResponseEntity<String> deleteFeedbacksByMovieId(@PathVariable("id") Long movieId) {
        feedBackService.deleteAllFeedBacksByMovieId(movieId);
        return new ResponseEntity<>(String.format("Feedbacks by movieId %d have been deleted", movieId), HttpStatus.OK);
    }

    @Operation(summary = "Удалить отзывы пользователя по id пользователя")
    @DeleteMapping("/feedbacks/userId/{id}")
    public ResponseEntity<String> deleteFeedBackByUserId(@PathVariable("id") Long userId) {
        feedBackService.deleteAllFeedBacksByUserId(userId);
        return new ResponseEntity<>(String.format("Feedbacks by userId %d have been deleted", userId), HttpStatus.OK);
    }
}
