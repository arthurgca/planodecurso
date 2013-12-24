package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

import models.CatalogoDeDisciplinas;
import views.html.index;

public class Application extends Controller {

    private static final CatalogoDeDisciplinas disciplinas = new CatalogoDeDisciplinas();

    public static Result index() {
        return ok(index.render());
    }

    public static Result disciplinasJson() {
        return ok(Json.toJson(disciplinas.getAll()));
    }

}
