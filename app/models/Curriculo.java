package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Curriculo extends Model {

    @Id
    private int id;

    private String nome;

    private int maxPeriodos;

    private int minCreditosPeriodo;

    private int maxCreditosPeriodo;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Disciplina> disciplinas = new HashSet<Disciplina>();

    @JsonIgnore
    @OneToMany(mappedBy = "curriculo")
    private List<Grade> grades = new LinkedList<Grade>();

    private Curriculo(Builder builder) {
        this.nome = builder.nome;
        this.maxPeriodos = builder.maxPeriodos;
        this.minCreditosPeriodo = builder.minCreditosPeriodo;
        this.maxCreditosPeriodo = builder.maxCreditosPeriodo;
        this.disciplinas = builder.disciplinas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public int getMaxPeriodos() {
        return maxPeriodos;
    }

    public void setMaxPeriodos(int maxPeriodos) {
        this.maxPeriodos = maxPeriodos;
    }

    public int getMinCreditosPeriodo() {
        return minCreditosPeriodo;
    }

    public void setMinCreditosPeriodo(int minCreditosPeriodo) {
        this.minCreditosPeriodo = minCreditosPeriodo;
    }

    public int getMaxCreditosPeriodo() {
        return maxCreditosPeriodo;
    }

    public void setMaxCreditosPeriodo(int maxCreditosPeriodo) {
        this.maxCreditosPeriodo = maxCreditosPeriodo;
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Disciplina getDisciplina(Long id) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId().equals(id)) {
                return disciplina;
            }
        }

        return null;
    }

    public Disciplina getDisciplina(String nome) {
        for (Disciplina d : disciplinas) {
            if (d.getNome().equals(nome)) {
                return d;
            }
        }

        return null;
    }

    public List<Grade> getGradesOriginais() {
        List<Grade> resultado = new LinkedList<Grade>();
        for (Grade g : grades) {
            if (g.isOriginal()) {
                resultado.add(g);
            }
        }
        return resultado;
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
