package com.proyecto.cars_dealer.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "Reviews")
@Data
@AllArgsConstructor
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El título no puede estar vacío")
    private String title;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer rating;

    @NotEmpty(message = "El comentario no puede estar vacío")
    @Column(columnDefinition = "TEXT")
    private String comment;

    @NotEmpty(message = "El modelo del auto no puede estar vacío")
    private String carModel;
}