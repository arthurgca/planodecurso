package controllers;

import play.mvc.*;

import views.html.index;

public class PlanoDeCursoApp extends ControllerBase {

    public static Result index() {
        return ok(index.render());
    }

}
