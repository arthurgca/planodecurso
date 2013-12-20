package models;

import java.util.*;
import javax.validation.*;

import play.data.validation.Constraints.*;

public class Periodo {

    @Required
    private int semestre;

    @Valid
    private List<Disciplina> disciplinas;

    public Periodo() {
        this.semestre = 1;
        this.disciplinas = new ArrayList<Disciplina>();
    }

    public Periodo(int semestre, Disciplina... disciplinas) {
        this.semestre = semestre;
        this.disciplinas = new ArrayList<Disciplina>();

        for (Disciplina disciplina : disciplinas) {
            if (disciplina != null) {
                this.disciplinas.add(disciplina);
            }
        }
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

    public int getTotalCreditos() {
        int total = 0;

        for (Disciplina disciplina : getDisciplinas()) {
            if (disciplina != null) {
                total += disciplina.getCreditos();
            }
        }

        return total;
    }

    public boolean isEmpty() {
        return disciplinas.isEmpty();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (obj == this) {
            return true;
        }
        
        if (!(obj instanceof Periodo)) {
            return false;
        }

        return getSemestre() == (((Periodo) obj).getSemestre());
    }

    public int hashCode() {
        return 7 * Integer.valueOf(this.semestre).hashCode();
    }
}
