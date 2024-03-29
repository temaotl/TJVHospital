package org.example.hospital.controller;



import org.example.hospital.business.AbstractCrudService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractCrudController<E, D, R extends CrudRepository<E, Long>> {
    protected final AbstractCrudService<D, Long, E, R> service;
    protected final Function<E, D> toDtoConverter;
    protected final Function<D, E> toEntityConverter;

    public AbstractCrudController(AbstractCrudService<D, Long, E, R> service,
                                  Function<E, D> toDtoConverter,
                                  Function<D, E> toEntityConverter) {
        this.service = service;
        this.toDtoConverter = toDtoConverter;
        this.toEntityConverter = toEntityConverter;
    }

    @GetMapping
    public ResponseEntity<List<D>> readAll() {
        List<D> dtos = service.readAll().stream()
                .map(toDtoConverter)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> readOne(@PathVariable Long id) {
        Optional<E> entity = service.readById(id);
        return entity.map(e -> ResponseEntity.ok(toDtoConverter.apply(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<D> create(@RequestBody D dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public abstract ResponseEntity<?> update(@RequestBody D dto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

