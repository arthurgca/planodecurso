package controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.Json;

import models.Periodo;
import models.PlanoDeCurso;
import models.CatalogoDeDisciplinas;
import views.html.index;

public class Application extends Controller {

    private static final CatalogoDeDisciplinas disciplinas = new CatalogoDeDisciplinas();

    private static final Form<PlanoDeCurso> planoForm = Form.form(PlanoDeCurso.class);

    public static Result index() {
        return ok(index.render());
    }

    public static Result disciplinasJson() {
        return ok(Json.toJson(disciplinas.getAll()));
    }

}
