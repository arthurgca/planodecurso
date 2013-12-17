package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    public static final Form<PlanoDeCurso> planoDeCursoForm = Form.form(PlanoDeCurso.class);

    public static Result index() {
        PlanoDeCurso planoDeCurso = PlanoDeCurso.criarPlanoFera();
        return ok(index.render(planoDeCursoForm.fill(PlanoDeCurso.criarPlanoFera()), disciplinasOfertadas()));
    }

    public static Result submit() {
        return null;
    }

    private static List<Disciplina> disciplinasOfertadas() {
        List<Disciplina> disciplinas = new ArrayList();

        disciplinas.add(new Disciplina("Calculo Diferencial e Integral I", 4));
        disciplinas.add(new Disciplina("Álgebra Vetorial e Geometria Analítica", 4));
        disciplinas.add(new Disciplina("Leitura e Produção de Textos", 4));
        disciplinas.add(new Disciplina("Programação I", 4));
        disciplinas.add(new Disciplina("Introdução à Computação", 4));
        disciplinas.add(new Disciplina("Laboratório de Programação I", 4));

        disciplinas.add(new Disciplina("Cálculo Diferencial e Integral II", 4));
        disciplinas.add(new Disciplina("Matemática Discreta", 4));
        disciplinas.add(new Disciplina("Metodologia Científica", 4));
        disciplinas.add(new Disciplina("Programação II", 4));
        disciplinas.add(new Disciplina("Teoria dos Grafos", 2));
        disciplinas.add(new Disciplina("Fundamentos de Física Clássica", 4));
        disciplinas.add(new Disciplina("Laboratório de Programação II", 4));

        disciplinas.add(new Disciplina("Álgebra Linear", 4));
        disciplinas.add(new Disciplina("Probabilidade e Estatística", 4));
        disciplinas.add(new Disciplina("Teoria da Computação", 4));
        disciplinas.add(new Disciplina("Estruturas de Dados e Algoritmos", 4));
        disciplinas.add(new Disciplina("Fundamentos de Física Moderna", 2));
        disciplinas.add(new Disciplina("Gerência da Informação", 4));
        disciplinas.add(new Disciplina("Lab de Estruturas de Dados e Algoritmos", 4));

        return disciplinas;
    }

}
