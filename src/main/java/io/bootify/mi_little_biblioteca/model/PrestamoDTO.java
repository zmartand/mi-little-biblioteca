package io.bootify.mi_little_biblioteca.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrestamoDTO {

    private Long id;

    @NotNull
    private LocalDate fechaPrestamo;

    @NotNull
    private LocalDate fechaDevolucion;

    @NotNull
    private Long libro;

    @NotNull
    private Long lector;

    @NotNull
    private Long bibliotecario;

}
