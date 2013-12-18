package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    static final CatalogoDeDisciplinas disciplinas = new CatalogoDeDisciplinas();

    static final Form<PlanoDeCurso> planoForm = Form.form(PlanoDeCurso.class);

    public static Result index() {
        PlanoDeCurso planoInicial = PlanoDeCurso.criarPlanoInicial();
        return ok(index.render(planoForm.fill(planoInicial), disciplinas.getAll()));
    }

    public static Result submit() {
        Form<PlanoDeCurso> filledForm = planoForm.bindFromRequest();

        if(filledForm.hasErrors()) {
            return badRequest(index.render(filledForm, disciplinas.getAll()));
        } else {
            PlanoDeCurso plano = filledForm.get();
            return ok(index.render(planoForm.fill(plano), disciplinas.getAll()));
        }
    }

}
