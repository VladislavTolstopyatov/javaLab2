package com.example.javalab2.controller;

import com.example.javalab2.dto.FeedbackDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.FeedBackService;
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

@RestController
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedBackService feedBackService;

    @PostMapping("movies/movie/{id}/feedbacks")
    public ResponseEntity<String> saveFeedback(@RequestBody FeedbackDto feedbackDto) {
        feedBackService.saveFeedBack(feedbackDto);
        return new ResponseEntity<>("Feedback saved successfully", HttpStatus.CREATED);
    }

    // протестировано
    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackDto>> getAllFeedbacks() {
        return new ResponseEntity<>(feedBackService.findAllFeedBacks(), HttpStatus.OK);
    }

    // протестировано
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable("id") Long feedbackId) throws EntityNotFoundException {
        return new ResponseEntity<>(feedBackService.findFeedBackById(feedbackId), HttpStatus.OK);
    }

    @GetMapping("/feedbacks/userId/{id}")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByUserId(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(feedBackService.findFeedBacksByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("movies/movie/{id}/feedbacks")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByMovieId(@PathVariable("id") Long movieId) {
        return new ResponseEntity<>(feedBackService.findFeedBacksByMovieId(movieId), HttpStatus.OK);
    }

    @DeleteMapping("/feedbacks")
    public ResponseEntity<String> deleteAllFeedbacks() {
        feedBackService.deleteAllFeedBacks();
        return new ResponseEntity<>("All feedbacks have been deleted", HttpStatus.OK);
    }

    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<String> deleteFeedbackById(@PathVariable("id") Long feedbackId) {
        feedBackService.deleteFeedBackById(feedbackId);
        return new ResponseEntity<>(String.format("Feedback with id %d have been deleted", feedbackId), HttpStatus.OK);
    }

    @DeleteMapping("/feedbacks/movieId/{id}")
    public ResponseEntity<String> deleteFeedbacksByMovieId(@PathVariable("id") Long movieId) {
        feedBackService.deleteAllFeedBacksByMovieId(movieId);
        return new ResponseEntity<>(String.format("Feedbacks by movieId %d have been deleted", movieId), HttpStatus.OK);
    }

    @DeleteMapping("/feedbacks/userId/{id}")
    public ResponseEntity<String> deleteFeedBackByUserId(@PathVariable("id") Long userId) {
        feedBackService.deleteAllFeedBacksByUserId(userId);
        return new ResponseEntity<>(String.format("Feedbacks by userId %d have been deleted", userId), HttpStatus.OK);
    }
}
