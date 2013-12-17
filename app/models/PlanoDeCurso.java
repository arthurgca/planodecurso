package models;

import java.util.*;

import javax.validation.*;

import play.data.validation.Constraints.*;

public class PlanoDeCurso {

    private static final CatalogoDeDisciplinas catalogoDeDisciplinas = new CatalogoDeDisciplinas();

    @Valid
    private List<Periodo> periodos;

    public PlanoDeCurso() {
        this.periodos = new ArrayList<Periodo>();
    }

    public PlanoDeCurso(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public static PlanoDeCurso criarPlanoFera() {
        List<Disciplina> disciplinas = new ArrayList();
        disciplinas.add(catalogoDeDisciplinas.get("CALCULO1"));
        disciplinas.add(catalogoDeDisciplinas.get("VETORIAL"));
        disciplinas.add(catalogoDeDisciplinas.get("LPT"));
        disciplinas.add(catalogoDeDisciplinas.get("P1"));
        disciplinas.add(catalogoDeDisciplinas.get("IC"));
        disciplinas.add(catalogoDeDisciplinas.get("LP1"));

        List<Periodo> periodos = new ArrayList<Periodo>();
        periodos.add(new Periodo(1, disciplinas));

        return new PlanoDeCurso(periodos);
    }
}
