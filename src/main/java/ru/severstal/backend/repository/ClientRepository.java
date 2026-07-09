package ru.severstal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.severstal.backend.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
}
