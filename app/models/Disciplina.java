package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Disciplina extends Model {

    @Id
    private Long id;

    private String nome;

    private int creditos;

    private String categoria;

    @ManyToMany
    @JoinTable(name="disciplina_requisitos",
               joinColumns={@JoinColumn(name="disciplina_id", referencedColumnName="id")},
               inverseJoinColumns={@JoinColumn(name="requisito_id", referencedColumnName="id")})
    private Set<Disciplina> requisitos = new HashSet<Disciplina>();

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

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Set<Disciplina> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(Set<Disciplina> requisitos) {
        this.requisitos = new HashSet<Disciplina>(requisitos);
    }

    public Set<Disciplina> getRequisitosInsatisfeitos(Set<Disciplina> disciplinas) {
        Set<Disciplina> insatisfeitos = new HashSet<Disciplina>(this.requisitos);
        insatisfeitos.removeAll(disciplinas);
        return insatisfeitos;
    }

    public static Finder<Long,Disciplina> find =
        new Finder<Long,Disciplina>(Long.class, Disciplina.class);
}
