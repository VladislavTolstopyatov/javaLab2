package com.example.javalab2.controllers;

import com.example.javalab2.dto.DirectorDto;
import com.example.javalab2.exceptions.ModelNotFoundException;
import com.example.javalab2.services.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @PostMapping("/directors/save")
    public ResponseEntity<String> saveDirector(@RequestBody DirectorDto directorDto) {
        directorService.saveDirector(directorDto);
        return new ResponseEntity<>("Director saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/directors/{id}")
    public ResponseEntity<DirectorDto> getDirectorById(@PathVariable("id") Long directorId) throws ModelNotFoundException {
        return new ResponseEntity<>(directorService.findDirectorById(directorId), HttpStatus.OK);
    }

    @GetMapping("/directors")
    public ResponseEntity<List<DirectorDto>> getAllDirectors() {
        return new ResponseEntity<>(directorService.findAllDirectors(), HttpStatus.OK);
    }

    @GetMapping("/directors/names/{name}")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(directorService.findDirectorsByName(name), HttpStatus.OK);
    }

    @GetMapping("/directors/surnames/{surname}")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsBySurname(@PathVariable("surname") String surname) {
        return new ResponseEntity<>(directorService.findDirectorsBySurname(surname), HttpStatus.OK);
    }

    @GetMapping("/directors/patronymics/{patronymic}")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsByPatronymic(@PathVariable("patronymic") String patronymic) {
        return new ResponseEntity<>(directorService.findDirectorsByPatronymic(patronymic), HttpStatus.OK);
    }

    @GetMapping("/directors/birthdates/{birthdate}")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsByBirthdate(@PathVariable("birthdate") LocalDate birthdate) {
        return new ResponseEntity<>(directorService.findDirectorsByBirthdate(birthdate), HttpStatus.OK);
    }

    @GetMapping("/directors/oscars/{oscar}")
    public ResponseEntity<List<DirectorDto>> getAllDirectorsByOscar(@PathVariable("oscar") Boolean oscar) {
        return new ResponseEntity<>(directorService.findDirectorsByOscar(oscar), HttpStatus.OK);
    }

    @DeleteMapping("/directors/delete")
    public ResponseEntity<String> deleteAllDirectors() {
        directorService.deleteAllDirectors();
        return new ResponseEntity<>("Directors have been deleted", HttpStatus.OK);
    }

    @DeleteMapping("/directors/delete/{id}")
    public ResponseEntity<String> deleteDirectorById(@PathVariable("id") Long directorId) {
        directorService.deleteDirectorById(directorId);
        return new ResponseEntity<>(String.format("Director with id %d have been deleted", directorId), HttpStatus.OK);
    }

    @DeleteMapping("/directors/fio/{name}/{surname}/{patronymic}")
    public ResponseEntity<String> deleteDirectorByNameSurnameAndPatronymic(
            @PathVariable("name") String name,
            @PathVariable("surname") String surname,
            @PathVariable("patronymic") String patronymic) {

        directorService.deleteDirectorByNameAndSurnameAndPatronymic(name, surname, patronymic);
        return new ResponseEntity<>(String.format("Director with name %s, " +
                "surname %s, " +
                "patronymic %s  have been deleted", name, surname, patronymic), HttpStatus.OK);
    }
}
