package com.example.javalab2.services;

import com.example.javalab2.mappers.DirectorMapper;
import com.example.javalab2.repositories.DirectorRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;

    private final DirectorMapper directorMapper;


}
