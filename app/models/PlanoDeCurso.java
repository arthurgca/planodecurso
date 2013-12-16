package models;

import java.util.*;

import javax.validation.*;

import play.data.validation.Constraints.*;

public class PlanoDeCurso {

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
        disciplinas.add(new Disciplina("Calculo Diferencial e Integral I", 4));
        disciplinas.add(new Disciplina("Álgebra Vetorial e Geometria Analítica", 4));
        disciplinas.add(new Disciplina("Leitura e Produção de Textos", 4));
        disciplinas.add(new Disciplina("Programação I", 4));
        disciplinas.add(new Disciplina("Introdução à Computação", 4));
        disciplinas.add(new Disciplina("Laboratório de Programação I", 4));

        List<Periodo> periodos = new ArrayList<Periodo>();
        periodos.add(new Periodo(1, disciplinas));

        return new PlanoDeCurso(periodos);
    }
}
