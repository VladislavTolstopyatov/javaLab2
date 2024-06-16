package com.example.javalab2.controller;

import com.example.javalab2.dto.ActorCastDto;
import com.example.javalab2.exception.EntityNotFoundException;
import com.example.javalab2.service.ActorCastService;
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

import java.util.List;

@Tag(name = "ActorCast Controller",description = "Обрабатывает запросы, связанные с актёрскими составами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/actorsCasts")
public class ActorCastController {
    private final ActorCastService actorCastService;

    @Operation(summary = "Добавить новый актерский состав")
    @PostMapping
    public ResponseEntity<String> saveActorCast(@RequestBody ActorCastDto actorCastDto) {
        actorCastService.saveActorCast(actorCastDto);
        return new ResponseEntity<>("ActorCast saved successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Получить все актерские составы")
    @GetMapping
    public ResponseEntity<List<ActorCastDto>> getAllActorsCasts() {
        return new ResponseEntity<>(actorCastService.findAllActorsCasts(), HttpStatus.OK);
    }

    @Operation(summary = "Получить актёрский состав по id")
    @GetMapping("/{id}")
    public ResponseEntity<ActorCastDto> getActorCastById(@PathVariable("id") Long actorCastId) throws EntityNotFoundException {
        return new ResponseEntity<>(actorCastService.findActorCastById(actorCastId), HttpStatus.OK);
    }

    @Operation(summary = "Получить актёрский состав по названию фильма")
    @GetMapping("/title")
    public ResponseEntity<List<ActorCastDto>> getActorsCastsByMovieTitle(@RequestParam String title) {
        return new ResponseEntity<>(actorCastService.findAllActorsCastByMovieTitle(title), HttpStatus.OK);
    }

    @Operation(summary = "Получить актёрский состав по фио актёра")
    @GetMapping("/fio")
    public ResponseEntity<List<ActorCastDto>> getActorsCastsByActorFio(@RequestParam String fio) {
        return new ResponseEntity<>(actorCastService.findAllActorsCastByActorFio(fio), HttpStatus.OK);
    }

    @Operation(summary = "Удалить все актерские составы")
    @DeleteMapping
    public ResponseEntity<String> deleteAllActorsCasts() {
        actorCastService.deleteAllActorsCast();
        return new ResponseEntity<>("All actorsCasts have been deleted", HttpStatus.OK);
    }

    @Operation(summary = "Удалить актёрский состав по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActorCastById(@PathVariable("id") Long actorCastId) {
        actorCastService.deleteActorCastById(actorCastId);
        return new ResponseEntity<>(String.format("ActorCast with id %d have been deleted", actorCastId), HttpStatus.OK);
    }
}
