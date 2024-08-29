package com.fjdev.rackapirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "booklist")
@Data  // Lombok para generar getters, setters, toString, etc.
public class BookList {

    @Id
    @Column(length = 20)  // Definir el tamaño de la columna 'id'
    private String id;

    @Column(nullable = false)
    private int pagerecount;

    @Column(nullable = false)
    private int pagereader;

    @Column(nullable = false)
    private boolean statebook;

    @Column(columnDefinition = "TEXT")
    private String urlimage;

    @Column(length = 100, nullable = false)
    private String bookname;

    // Relación con la tabla 'user'
    @ManyToOne(fetch = FetchType.LAZY)  // Muchos libros pueden pertenecer a un mismo usuario
    @JoinColumn(name = "nameC", referencedColumnName = "nameC", 
                foreignKey = @ForeignKey(name = "fk_user_nameC"))
    @JsonIgnore
    private User user;

}