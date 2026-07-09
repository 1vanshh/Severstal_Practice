package ru.severstal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.severstal.backend.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
}
