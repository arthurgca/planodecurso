package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    private static final CatalogoDeDisciplinas catalogoDeDisciplinas = new CatalogoDeDisciplinas();

    public static final Form<PlanoDeCurso> planoDeCursoForm = Form.form(PlanoDeCurso.class);

    public static Result index() {
        PlanoDeCurso planoDeCursoInicial = PlanoDeCurso.criarPlanoFera();
        return ok(index.render(planoDeCursoForm.fill(planoDeCursoInicial),
                               catalogoDeDisciplinas.getAll()));
    }

    public static Result submit() {
        return null;
    }
}
