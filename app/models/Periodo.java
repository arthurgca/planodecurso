package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Periodo extends Model {

    @Id
    private Long id;

    private int semestre;

    @JsonIgnore
    @ManyToMany
    private List<Disciplina> disciplinas = new LinkedList<Disciplina>();

    public Periodo(int semestre) {
        this(semestre, null);
    }

    public Periodo(int semestre, Disciplina[] disciplinas) {
        this.semestre = semestre;

        if (disciplinas != null)  {
            for (Disciplina d : disciplinas) {
                this.disciplinas.add(d);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getNome() {
        return String.format("%sº Período", semestre);
    }

    public int getTotalCreditos() {
        int totalCreditos = 0;
        for (Disciplina disciplina : disciplinas) {
            totalCreditos += disciplina.getCreditos();
        }
        return totalCreditos;
    }

    public void programar(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public void desprogramar(Disciplina disciplina) {
        disciplinas.remove(disciplina);
    }

    public static Finder<Long,Periodo> find =
        new Finder<Long,Periodo>(Long.class, Periodo.class);
}
