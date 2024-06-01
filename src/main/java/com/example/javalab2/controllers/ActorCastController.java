package com.example.javalab2.controllers;

import com.example.javalab2.dto.ActorCastDto;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.services.ActorCastService;
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
public class ActorCastController {
    private final ActorCastService actorCastService;

    @PostMapping("/actorsCasts/save")
    public ResponseEntity<String> saveActorCast(@RequestBody ActorCastDto actorCastDto) {
        actorCastService.saveActorCast(actorCastDto);
        return new ResponseEntity<>("ActorCast saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/actorsCasts")
    public ResponseEntity<List<ActorCastDto>> getAllActorsCasts() {
        return new ResponseEntity<>(actorCastService.findAllActorsCasts(), HttpStatus.OK);
    }

    @GetMapping("/actorsCasts/{id}")
    public ResponseEntity<ActorCastDto> getActorCastById(@PathVariable("id") Long actorCastId) throws ModelNotFoundException {
        return new ResponseEntity<>(actorCastService.findActorCastById(actorCastId), HttpStatus.OK);
    }

    @GetMapping("/actorsCasts/movies/{title}")
    public ResponseEntity<List<ActorCastDto>> getActorsCastsByMovieTitle(@PathVariable("title") String title) {
        return new ResponseEntity<>(actorCastService.findAllActorsCastByMovieTitle(title), HttpStatus.OK);
    }

    @GetMapping("/actorsCasts/actorsFio/{fio}")
    public ResponseEntity<List<ActorCastDto>> getActorsCastsByActorFio(@PathVariable("fio") String fio) {
        return new ResponseEntity<>(actorCastService.findAllActorsCastByActorFio(fio), HttpStatus.OK);
    }

    @DeleteMapping("/actorsCasts/delete")
    public ResponseEntity<String> deleteAllActorsCasts() {
        actorCastService.deleteAllActorsCast();
        return new ResponseEntity<>("All actorsCasts have been deleted", HttpStatus.OK);
    }

    @DeleteMapping("/actorsCasts/delete/{id}")
    public ResponseEntity<String> deleteActorCastById(@PathVariable("id") Long actorCastId) {
        actorCastService.deleteActorCastById(actorCastId);
        return new ResponseEntity<>(String.format("ActorCast with id %d have been deleted", actorCastId), HttpStatus.OK);
    }
}
