package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Periodo extends Model {
    private static final int MAX_CREDITOS_SEMESTRE = 28;

    @Id
    public Long id;

    public int semestre;

    @ManyToMany
    public Set<Disciplina> disciplinas = new HashSet<Disciplina>();

    public Periodo(int semestre) {
        if (semestre < 1) {
            throw new IllegalArgumentException("semestre deve ser >= 1");
        }

        this.semestre = semestre;
    }

    public int getTotalCreditos() {
        int totalCreditos = 0;
        for (Disciplina disciplina : disciplinas) {
            totalCreditos += disciplina.creditos;
        }
        return totalCreditos;
    }

    public void alocarDisciplina(Disciplina disciplina) throws ErroDeAlocacaoException {
        validarAlocacao(disciplina);
        disciplinas.add(disciplina);
    }

    public void desalocarDisciplina(Disciplina disciplina) {
        if (disciplinas.contains(disciplina)) {
            disciplinas.remove(disciplina);
        }
    }

    public static Finder<Long,Periodo> find =
        new Finder<Long,Periodo>(Long.class, Periodo.class);
}
