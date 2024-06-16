package com.example.javalab2.controller;

import com.example.javalab2.dto.DirectorDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.DirectorService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Director Controller",description = "Обрабатывает запросы, связанные с режиссёрами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService directorService;

    @Operation(summary = "Добавить нового режиссёра")
    @PostMapping
    public ResponseEntity<String> saveDirector(@RequestBody DirectorDto directorDto) {
        directorService.saveDirector(directorDto);
        return new ResponseEntity<>("Director saved successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Получить режиссёра по id")
    @GetMapping("/director/{id}")
    public ResponseEntity<DirectorDto> getDirectorById(@PathVariable("id") Long directorId) throws EntityNotFoundException {
        return new ResponseEntity<>(directorService.findDirectorById(directorId), HttpStatus.OK);
    }

    @Operation(summary = "Получить список всех режиссёров")
    @GetMapping
    public ResponseEntity<List<DirectorDto>> getAllDirectors() {
        return new ResponseEntity<>(directorService.findAllDirectors(), HttpStatus.OK);
    }

    @Operation(summary = "Получить список режиссёров по имени")
    @GetMapping("/name")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsByName(@RequestParam String name) {
        return new ResponseEntity<>(directorService.findDirectorsByName(name), HttpStatus.OK);
    }

    @Operation(summary = "Получить список режиссёров по фамилии")
    @GetMapping("/surname")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsBySurname(@RequestParam String surname) {
        return new ResponseEntity<>(directorService.findDirectorsBySurname(surname), HttpStatus.OK);
    }

    @Operation(summary = "Получить список режиссёров по отчеству")
    @GetMapping("/patronymic")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsByPatronymic(@RequestParam String patronymic) {
        return new ResponseEntity<>(directorService.findDirectorsByPatronymic(patronymic), HttpStatus.OK);
    }

    @Operation(summary = "Получить список режиссёров по дате рождения")
    @GetMapping("/birthdate")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsByBirthdate(@RequestParam LocalDate birthdate) {
        return new ResponseEntity<>(directorService.findDirectorsByBirthdate(birthdate), HttpStatus.OK);
    }

    @Operation(summary = "Получить список режиссёров по наличию оскара")
    @GetMapping("/oscar")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsByOscar(@RequestParam Boolean oscar) {
        return new ResponseEntity<>(directorService.findDirectorsByOscar(oscar), HttpStatus.OK);
    }

    @Operation(summary = "Удалить всех режиссёров")
    @DeleteMapping
    public ResponseEntity<String> deleteAllDirectors() {
        directorService.deleteAllDirectors();
        return new ResponseEntity<>("Directors have been deleted", HttpStatus.OK);
    }

    @Operation(summary = "Удалить режиссёра по id")
    @DeleteMapping("/director/{id}")
    public ResponseEntity<String> deleteDirectorById(@PathVariable("id") Long directorId) {
        directorService.deleteDirectorById(directorId);
        return new ResponseEntity<>(String.format("Director with id %d have been deleted", directorId), HttpStatus.OK);
    }
}
