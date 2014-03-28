package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Uma Disciplina que pode ser programada para um Período.
 */
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

    /**
     * Constrói uma disciplina com nome, número de créditos.
     * @throws NullPointerException se {@code nome == null}
     * @throws IllegalArgumentException se {@code creditos < 0} ou {@code nome == ""}
     */
    public Disciplina(String nome, int creditos) {
        setNome(nome);
        setCreditos(creditos);
    }

    /**
     * @return o id da disciplina
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id o id da disciplina
     * @throws NullPointerException se {@code id == null}
     * @throws IllegalArgumentException se {@code id < 1}
     */
    public void setId(Long id) {
        Parametro.naoNulo("id", id);
        Parametro.maiorQueZero("id", id);

        this.id = id;
    }

    /**
     * @return o nome da disciplina
     */
     public String getNome() {
         return nome;
     }

    /**
     * @param nome o nome para a disciplina
     * @throws NullPointerException se {@code nome == null}
     * @throws IllegalArgumentException se {@code nome == ""}
     */
    public void setNome(String nome) {
        Parametro.naoNulo("nome", nome);
        Parametro.naoVazio("nome", nome);

        this.nome = nome;
    }

    /**
     * @return o número de créditos da disciplina
     */
    public int getCreditos() {
        return creditos;
    }

    /**
     * @param creditos o número de creditos para a disciplina
     * @throws IllegalArgumentException se {@code creditos < 1}
     */
    public void setCreditos(int creditos) {
        Parametro.maiorQueZero("creditos", creditos);

        this.creditos = creditos;
    }

    /**
     * @return a categoria da disciplina
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria a categoria para a disciplina
     * @throws NullPointerException se {@code categoria == null}
     * @throws IllegalArgumentException se {@code categoria == ""}
     */
    public void setCategoria(String categoria) {
        Parametro.naoNulo("categoria", categoria);
        Parametro.naoVazio("categoria", categoria);

        this.categoria = categoria;
    }

    /**
     * @return o conjunto de requisitos da disciplina
     */
    public Set<Disciplina> getRequisitos() {
        return requisitos;
    }

    /**
     * @param requisitos um conjunto de requisitos para a disciplina
     * @throws NullPointerException se {@code requisitos == null}
     */
    public void setRequisitos(Set<Disciplina> requisitos) {
        Parametro.naoNulo("requisitos", requisitos);

        this.requisitos = new HashSet<Disciplina>(requisitos);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getNome(), getId());
    }

    /** Finder para o Ebean */
    public static Finder<Long,Disciplina> find =
        new Finder<Long,Disciplina>(Long.class, Disciplina.class);
}
