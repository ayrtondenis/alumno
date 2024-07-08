package com.encora.alumno.controller;

import com.encora.alumno.model.Alumno;
import com.encora.alumno.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    @Autowired
    private AlumnoService service;

    @GetMapping("/activos")
    public Flux<Alumno> getAllAlumnosActivos() {
        return service.getAllAlumnosActivos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Alumno> saveAlumno(@RequestBody Alumno alumno) {
        return service.saveAlumno(alumno)
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()));
                });
    }
}
