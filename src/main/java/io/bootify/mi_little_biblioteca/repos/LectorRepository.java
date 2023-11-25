package io.bootify.mi_little_biblioteca.repos;

import io.bootify.mi_little_biblioteca.domain.Lector;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectorRepository extends JpaRepository<Lector, Long> {
}
