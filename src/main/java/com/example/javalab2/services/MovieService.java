package com.example.javalab2.services;

import com.example.javalab2.mappers.MovieMapper;
import com.example.javalab2.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
}
