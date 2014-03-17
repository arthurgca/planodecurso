package controllers;

import play.mvc.*;

import views.html.meucurso;

public class PlanoDeCursoApp extends PrivateControllerBase {

    public static Result index() {
        return ok(meucurso.render());
    }


}
