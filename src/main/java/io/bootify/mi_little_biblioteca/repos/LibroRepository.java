package io.bootify.mi_little_biblioteca.repos;

import io.bootify.mi_little_biblioteca.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro, Long> {
}
