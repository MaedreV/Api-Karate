package com.projeto.demo.domain.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "torneios")
public class Torneio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento;

    @ManyToOne
    @JoinColumn(name = "estilo_id")
    private Estilo estilo;

    @ManyToMany
    @JoinTable(
            name = "torneio_aluno",
            joinColumns = @JoinColumn(name = "torneio_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> participantes;

    public Torneio() {
    }

    public Torneio(Long id, String nome, LocalDate dataEvento, Estilo estilo, List<Aluno> participantes) {
        this.id = id;
        this.nome = nome;
        this.dataEvento = dataEvento;
        this.estilo = estilo;
        this.participantes = participantes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public List<Aluno> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Aluno> participantes) {
        this.participantes = participantes;
    }
}
