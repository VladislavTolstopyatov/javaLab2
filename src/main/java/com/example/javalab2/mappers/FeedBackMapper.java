package com.example.javalab2.mappers;

import com.example.javalab2.dto.FeedbackDto;
import com.example.javalab2.entities.Feedback;
import com.example.javalab2.repositories.MovieRepository;
import com.example.javalab2.repositories.UserRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FeedBackMapper implements Mappable<Feedback, FeedbackDto> {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FeedbackDto toDto(Feedback feedbackEntity) {
        if (feedbackEntity == null) {
            return null;
        }

        return FeedbackDto.builder()
                .id(feedbackEntity.getId())
                .title(feedbackEntity.getMovie().getTitle())
                .nickName(feedbackEntity.getUser().getNickName())
                .feedbackDate(feedbackEntity.getFeedbackDate())
                .feedback(feedbackEntity.getFeedback()).build();
    }

    @Override
    public Feedback toEntity(FeedbackDto dto) {
        if (dto == null) {
            return null;
        }
        return Feedback.builder()
                .id(dto.getId())
                .movie(movieRepository.findMovieByTitle(dto.getTitle()))
                .user(userRepository.findUserByNickName(dto.getNickName()))
                .feedbackDate(dto.getFeedbackDate())
                .feedback(dto.getFeedback())
                .build();
    }

    @Override
    public List<FeedbackDto> toDto(List<Feedback> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(this::toDto).toList();
    }

    @Override
    public List<Feedback> toEntities(List<FeedbackDto> dto) {
        if (dto == null) {
            return null;
        }
        return dto.stream().map(this::toEntity).toList();
    }
}
