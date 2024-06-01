package com.example.javalab2.controllers;

import com.example.javalab2.dto.ActorDto;
import com.example.javalab2.exceptions.ActorFioAlreadyExistsException;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.services.ActorService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorController {
    private final ActorService actorService;

    @PostMapping("/save")
    public ResponseEntity<String> saveActor(@RequestBody ActorDto actorDto) throws ActorFioAlreadyExistsException {
        actorService.saveActor(actorDto);
        return new ResponseEntity<>("Actor saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable("id") Long actorId) throws ModelNotFoundException {
        return new ResponseEntity<>(actorService.findActorById(actorId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActorDto>> getAllActors() {
        return new ResponseEntity<>(actorService.findAllActors(), HttpStatus.OK);
    }

    @GetMapping("/fio")
    public ResponseEntity<ActorDto> getActorByFio(@RequestParam String fio) throws ModelNotFoundException {
        return new ResponseEntity<>(actorService.findActorByFio(fio), HttpStatus.OK);
    }

    @GetMapping("/birthdate")
    public ResponseEntity<List<ActorDto>> getActorsByBirthdate(@RequestParam LocalDate birthdate) {
        return new ResponseEntity<>(actorService.findActorsByBirthdate(birthdate), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAllActors() {
        actorService.deleteAllActors();
        return new ResponseEntity<>("All actors have been deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActorById(@PathVariable("id") Long actorId) {
        actorService.deleteActorById(actorId);
        return new ResponseEntity<>(String.format("Actor with id %d have been deleted", actorId), HttpStatus.OK);
    }

    @DeleteMapping("/deleteByFio/{fio}")
    public ResponseEntity<String> deleteActorByFio(@PathVariable("fio") String fio) {
        actorService.deleteActorByFio(fio);
        return new ResponseEntity<>(String.format("Actor with fio %s have been deleted", fio), HttpStatus.OK);
    }
}
