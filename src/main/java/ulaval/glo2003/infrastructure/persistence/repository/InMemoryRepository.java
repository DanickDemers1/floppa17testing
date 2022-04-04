package ulaval.glo2003.infrastructure.persistence.repository;

import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.entities.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryRepository<T extends Entity> implements Repository<T> {
    private final Map<UUID, T> database;

    public InMemoryRepository() {
        database = new HashMap<>();
    }

    @Override
    public T get(UUID id) {
        var entity = database.get(id);
        if (entity == null)
            throw new ItemNotFoundException();
        return entity;
    }

    @Override
    public Boolean exists(UUID id) {
        var entity = database.get(id);
        return entity != null;
    }

    @Override
    public void save(T entity) {
        UUID entityId = entity.getId();
        database.put(entityId, entity);
    }

    @Override
    public void remove(UUID entityId) {
        database.remove(entityId);
    }

    @Override
    public List<T> getAll(){
        return database.keySet().stream()
                .map(database::get)
                .collect(Collectors.toList());
    }
}
