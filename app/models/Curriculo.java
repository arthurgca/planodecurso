package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Curriculo extends Model {

    @Id
    public int id;

    public String nome;

    public int maxPeriodos;

    public int minCreditosPeriodo;

    public int maxCreditosPeriodo;

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Disciplina> disciplinas = new HashSet<Disciplina>();

    private Curriculo(Builder builder) {
        this.nome = builder.nome;
        this.maxPeriodos = builder.maxPeriodos;
        this.minCreditosPeriodo = builder.minCreditosPeriodo;
        this.maxCreditosPeriodo = builder.maxCreditosPeriodo;
        this.disciplinas = builder.disciplinas;
    }

    @Transient
    public Disciplina getDisciplina(Long id) {
        for (Disciplina d : disciplinas) {
            if (d.id == id) {
                return d;
            }
        }

        return null;
    }

    @Transient
    public Disciplina getDisciplina(String nome) {
        for (Disciplina d : disciplinas) {
            if (d.nome.equals(nome)) {
                return d;
            }
        }

        return null;
    }

    public static Finder<Integer,Curriculo> find =
        new Finder<Integer,Curriculo>(Integer.class, Curriculo.class);

    public static class Builder {

        public String nome;

        public int maxPeriodos = 14;

        public int minCreditosPeriodo = 14;

        public int maxCreditosPeriodo = 28;

        public Set<Disciplina> disciplinas = new HashSet<Disciplina>();

        public Builder(String nome) {
            this.nome = nome;
        }

        public Builder maxPeriodos(int maxPeriodos) {
            this.maxPeriodos = maxPeriodos;
            return this;
        }

        public Builder minCreditosPeriodo(int minCreditosPeriodo) {
            this.minCreditosPeriodo = minCreditosPeriodo;
            return this;
        }

        public Builder maxCreditosPeriodo(int maxCreditosPeriodo) {
            this.maxCreditosPeriodo = maxCreditosPeriodo;
            return this;
        }

        public Builder disciplina(Disciplina disciplina) {
            disciplinas.add(disciplina);
            return this;
        }

        public Curriculo build() {
            return new Curriculo(this);
        }
    }
}
