package models;

import java.util.*;

import javax.validation.*;

import play.data.validation.Constraints.*;

public class Periodo {

    @Required
    private int semestre;

    private List<Disciplina> disciplinas;

    public Periodo(int semestre, List<Disciplina> disciplinas) {
        this.semestre = semestre;
        this.disciplinas = disciplinas;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

}
