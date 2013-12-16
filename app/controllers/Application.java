package controllers;

import java.util.*;

import play.*;
import play.mvc.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        PlanoDeCurso planoDeCurso = PlanoDeCurso.criarPlanoFera();
        return ok(index.render(planoDeCurso.getPeriodos(), disciplinasOfertadas()));
    }

    private static List<Disciplina> disciplinasOfertadas() {
        List<Disciplina> disciplinasOfertadas = new ArrayList();
        for (int i = 1; i <= 20; i++) {
            disciplinasOfertadas.add(new Disciplina("Disciplina " + i, 4));
        }
        return disciplinasOfertadas;
    }

}
