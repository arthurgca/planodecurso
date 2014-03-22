package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Disciplina extends Model {

    @Id
    public Long id;

    public String nome;

    public int creditos;

    public String categoria;

    @ManyToMany
    @JoinTable(name="disciplina_requisitos",
               joinColumns={@JoinColumn(name="disciplina_id", referencedColumnName="id")},
               inverseJoinColumns={@JoinColumn(name="requisito_id", referencedColumnName="id")})
    public Set<Disciplina> requisitos = new HashSet<Disciplina>();

    public Disciplina() {
    }

    public Disciplina(int id, String nome, int creditos, String categoria) {
        this(id, nome, creditos, categoria, new HashSet<Disciplina>());
    }

    public Disciplina(int id, String nome, int creditos, String categoria, Set<Disciplina> requisitos) {
        this.id = id;
        this.nome = nome;
        this.creditos = creditos;
        this.categoria = categoria;
        this.requisitos = requisitos;
    }

    public static Finder<Long,Disciplina> find =
        new Finder<Long,Disciplina>(Long.class, Disciplina.class);
}
