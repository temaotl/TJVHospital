package org.example.hospital.business;

import org.modelmapper.ModelMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional(readOnly = true)
public abstract class AbstractCrudService <D,K,E,R extends CrudRepository<E,K> > {

    protected final Function <E, D> toDtoConverter;
    protected final R  repository;
    private final ModelMapper modelMapper;



    protected AbstractCrudService(Function<E, D> toDtoConverter, R repository, ModelMapper modelMapper) {
        this.toDtoConverter = toDtoConverter;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public E create(E entity) { return  repository.save(entity); }
    public Optional<E> readById(K id) { return  repository.findById(id); }

    public  Set<E> readAll() {
        return
                StreamSupport.stream(
                        repository.findAll().spliterator(),
                        false
                ).collect(Collectors.toSet());
    }
    @Transactional
     public void update(D dto,K id) {
        Optional <E> entityOpt = repository.findById(id);
        entityOpt.ifPresent(
                entity->  {
                    modelMapper.map(dto,entity);
                    repository.save(entity);
                }
        );

    }

    @Transactional
    public void deleteById(K id) { repository.deleteById(id); }

}
