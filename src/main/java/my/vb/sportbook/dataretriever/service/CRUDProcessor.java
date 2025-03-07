package my.vb.sportbook.dataretriever.service;

import lombok.RequiredArgsConstructor;
import my.vb.sportbook.dataretriever.exception.EntityNotFoundException;
import my.vb.sportbook.dataretriever.model.IndexedEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class CRUDProcessor<T extends IndexedEntity, E extends MongoRepository<T, Long>, D> implements CRUDMethods<D> {

    private final CacheService cacheService;

    @Override
    @CachePut(value = "entitiesCache", key = "#dto.id")
    public D create(D dto) {
        return convertFrom(getRepository().save(convertTo(dto)));
    }

    @Override
    @CachePut(value = "entitiesCache", key = "#dto.id")
    public D update(D dto) {
        T t = convertTo(dto);
        if (t == null || t.getId() == null) {
            throw new IllegalArgumentException("DTO cannot be converted to an entity or id is null");
        }
            return convertFrom(Optional.of(getRepository().findById(t.getId()))
                .map(v -> getRepository().save(t))
                .orElse(null));
    }

    @Override
    @CacheEvict(value = "entitiesCache", key = "#dto.id")
    public void delete(D dto) {
        getRepository().delete(convertTo(dto));
    }

    @Override
    @CacheEvict(value = "entitiesCache", key = "#id", beforeInvocation = false)
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    @Cacheable(value = "entitiesCache", key = "#id")
    public D findById(Long id) {
        return convertFrom(getRepository().findById(id).orElseThrow(() ->
                new EntityNotFoundException
                        ("Entity with id " + id + " not found")));

    }

    public abstract E getRepository();

    public abstract T convertTo(D dto);

    public abstract D convertFrom(T t);
}
