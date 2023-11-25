package io.bootify.mi_little_biblioteca.repos;

import io.bootify.mi_little_biblioteca.domain.Bibliotecario;
import io.bootify.mi_little_biblioteca.domain.Lector;
import io.bootify.mi_little_biblioteca.domain.Libro;
import io.bootify.mi_little_biblioteca.domain.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Prestamo findFirstByLibro(Libro libro);

    Prestamo findFirstByLector(Lector lector);

    Prestamo findFirstByBibliotecario(Bibliotecario bibliotecario);

}
