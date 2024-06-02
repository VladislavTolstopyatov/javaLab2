package com.example.javalab2.services;

import com.example.javalab2.dto.FeedbackDto;
import com.example.javalab2.entities.Feedback;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.mappers.FeedBackMapper;
import com.example.javalab2.repositories.FeedBackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = {"main"})
@RequiredArgsConstructor
public class FeedBackService {
    private final FeedBackRepository feedBackRepository;
    private final FeedBackMapper feedBackMapper;

    public FeedbackDto saveFeedBack(FeedbackDto feedBackDto) {
        Feedback feedback = feedBackRepository.save(feedBackMapper.toEntity(feedBackDto));
        return feedBackMapper.toDto(feedback);
    }

    public void deleteFeedBackById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        feedBackRepository.deleteById(id);
    }

    public List<FeedbackDto> findAllFeedBacks() {
        return feedBackMapper.toDto(feedBackRepository.findAll());
    }

    public void deleteAllFeedBacks() {
        feedBackRepository.deleteAll();
    }

    public void deleteAllFeedBacksByUserId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        feedBackRepository.deleteFeedbacksByUserId(id);
    }

    public void deleteAllFeedBacksByMovieId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        feedBackRepository.deleteFeedbacksByMovieId(id);
    }

//    public void deleteAllFeedBacksByMovieIdAndUserId(Long movieId, Long userId) {
//        if (movieId <= 0 || userId <= 0) {
//            throw new IllegalArgumentException("1 or more id <= 0");
//        }
//        feedBackRepository.deleteFeedbacksByUserIdAndMovieId(movieId, userId);
//    }

    public FeedbackDto findFeedBackById(Long id) throws ModelNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }

        Optional<Feedback> optionalFeedback = feedBackRepository.findById(id);
        if (optionalFeedback.isPresent()) {
            return feedBackMapper.toDto(optionalFeedback.get());
        } else {
            throw new ModelNotFoundException(String.format("Feedback with id %d not found", id));
        }
    }

    public List<FeedbackDto> findFeedBacksByUserId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        return feedBackMapper.toDto(feedBackRepository.findFeedbacksByUserId(id));
    }

    @Cacheable
    public List<FeedbackDto> findFeedBacksByMovieId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        return feedBackMapper.toDto(feedBackRepository.findFeedbacksByMovieId(id));
    }

    public List<FeedbackDto> findFeedBacksByMovieIdAndUserId(Long movieId, Long userId) {
        if (movieId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("1 or more id <= 0");
        }
        return feedBackMapper.toDto(feedBackRepository.findFeedbacksByUserIdAndMovieId(movieId, userId));
    }
}
