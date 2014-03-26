package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Periodo extends Model {

    @Id
    public Long id;

    public int semestre;

    @JsonIgnore
    @ManyToMany
    public List<Disciplina> disciplinas = new LinkedList<Disciplina>();

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

    public String getNome() {
        return String.format("%sº Período", semestre);
    }

    public int getTotalCreditos() {
        int totalCreditos = 0;
        for (Disciplina disciplina : disciplinas) {
            totalCreditos += disciplina.creditos;
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
