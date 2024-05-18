package com.example.javalab2.services;

import com.example.javalab2.mappers.FeedBackMapper;
import com.example.javalab2.repositories.FeedBackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final FeedBackRepository feedBackRepository;
    private final FeedBackMapper feedBackMapper;
}
