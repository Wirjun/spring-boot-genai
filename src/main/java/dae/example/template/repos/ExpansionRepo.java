package dae.example.template.repos;

import dae.example.template.entities.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpansionRepo extends JpaRepository<Data, Long> {}
