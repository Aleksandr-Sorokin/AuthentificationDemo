package ru.sorokin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sorokin.entity.ClientEntity;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {
}
