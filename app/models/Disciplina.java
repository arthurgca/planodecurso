package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public final class Disciplina extends Model {

    @Id
    public int id;

    public String nome;

    public int creditos;

    @ManyToMany
    @JoinTable(name="disciplina_requisitos",
               joinColumns={@JoinColumn(name="disciplina_id", referencedColumnName="id")},
               inverseJoinColumns={@JoinColumn(name="requisito_id", referencedColumnName="id")})
    public Set<Disciplina> requisitos = new HashSet<Disciplina>();

    public Disciplina() {
    }

    public Disciplina(int id, String nome, int creditos) {
        this(id, nome, creditos, new HashSet<Disciplina>());
    }

    public Disciplina(int id, String nome, int creditos, Set<Disciplina> requisitos) {
        this.id = id;
        this.nome = nome;
        this.creditos = creditos;
        this.requisitos = requisitos;
    }

    public static Finder<Integer,Disciplina> find =
        new Finder<Integer,Disciplina>(Integer.class, Disciplina.class);

    public static Disciplina get(int i) {
        return find.byId(i);
    }

    public static Disciplina get(String nome) {
        return find.where().eq("nome", nome).findUnique();
    }

    public static Set<Disciplina> getAll() {
        return new HashSet<Disciplina>(find.all());
    }

}
