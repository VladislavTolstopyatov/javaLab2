package com.example.javalab2.controller;

import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.exception.ActorFioAlreadyExistsException;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.ActorService;
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
@Tag(name = "Actor Controller",description = "Обрабатывает запросы, связанные с актёрами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorController {
    private final ActorService actorService;
    @Operation(summary = "Добавить нового актёра")
    @PostMapping
    public ResponseEntity<String> saveActor(@RequestBody ActorDto actorDto) throws ActorFioAlreadyExistsException {
        actorService.saveActor(actorDto);
        return new ResponseEntity<>("Actor saved successfully", HttpStatus.CREATED);
    }
    @Operation(summary = "Получить актёра по id")
    @GetMapping("/actor/{id}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable("id") Long actorId) throws EntityNotFoundException {
        return new ResponseEntity<>(actorService.findActorById(actorId), HttpStatus.OK);
    }
    @Operation(summary = "Получить список всех актёров")
    @GetMapping
    public ResponseEntity<List<ActorDto>> getAllActors() {
        return new ResponseEntity<>(actorService.findAllActors(), HttpStatus.OK);
    }
    @Operation(summary = "Получить актёра по фио")
    @GetMapping("/actor/fio")
    public ResponseEntity<ActorDto> getActorByFio(@RequestParam String fio) throws EntityNotFoundException {
        return new ResponseEntity<>(actorService.findActorByFio(fio), HttpStatus.OK);
    }
    @Operation(summary = "Получить список актёров по дате рождения")
    @GetMapping("/birthdate")
    public ResponseEntity<List<ActorDto>> getActorsByBirthdate(@RequestParam LocalDate birthdate) {
        return new ResponseEntity<>(actorService.findActorsByBirthdate(birthdate), HttpStatus.OK);
    }
    @Operation(summary = "Удалить всех актёров")
    @DeleteMapping
    public ResponseEntity<String> deleteAllActors() {
        actorService.deleteAllActors();
        return new ResponseEntity<>("All actors have been deleted", HttpStatus.OK);
    }
    @Operation(summary = "Удалить актёра по id")
    @DeleteMapping("/actor/{id}")
    public ResponseEntity<String> deleteActorById(@PathVariable("id") Long actorId) {
        actorService.deleteActorById(actorId);
        return new ResponseEntity<>(String.format("Actor with id %d have been deleted", actorId), HttpStatus.OK);
    }
    @Operation(summary = "Удалить актёра по id")
    @DeleteMapping("/actor/fio")
    public ResponseEntity<String> deleteActorByFio(@RequestParam String fio) {
        actorService.deleteActorByFio(fio);
        return new ResponseEntity<>(String.format("Actor with fio %s have been deleted", fio), HttpStatus.OK);
    }
}
