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

    public Disciplina(String nome, int creditos, String categoria) {
        this(nome, creditos, categoria, null);
    }

    public Disciplina(String nome, int creditos, String categoria, Disciplina[] requisitos) {
        this.nome = nome;
        this.creditos = creditos;
        this.categoria = categoria;

        if (requisitos != null)  {
            for (Disciplina r : requisitos) {
                this.requisitos.add(r);
            }
        }
    }

    public Set<Disciplina> getRequisitosInsatisfeitos(Set<Disciplina> disciplinas) {
        Set<Disciplina> insatisfeitos = new HashSet<Disciplina>(this.requisitos);
        insatisfeitos.removeAll(disciplinas);
        return insatisfeitos;
    }

    public static Finder<Long,Disciplina> find =
        new Finder<Long,Disciplina>(Long.class, Disciplina.class);
}
