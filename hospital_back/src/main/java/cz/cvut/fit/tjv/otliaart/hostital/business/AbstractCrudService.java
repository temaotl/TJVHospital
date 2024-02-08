package cz.cvut.fit.tjv.otliaart.hostital.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Transactional(readOnly = true)
public abstract class AbstractCrudService<D, K, E, R extends CrudRepository<E, K>> {
    protected final R repository;

    protected Function<E, D> toDtoConverter;

    protected AbstractCrudService(R repository, Function<E, D> toDtoConverter) {
        this.toDtoConverter = toDtoConverter;
        this.repository = repository;
    }

    @Transactional
    public E create(E entity) {
        return repository.save(entity);
    }

    public Optional<E> readById(K id) {
        return repository.findById(id);
    }

    public List<E> readAll() {
        return (List<E>) repository.findAll();
    }

    @Transactional
    abstract public void update(E entity, Long id);

    @Transactional
    public void deleteById(K id) {
        repository.deleteById(id);
    }
}
