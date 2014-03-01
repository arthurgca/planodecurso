package controllers;

import play.mvc.*;
import play.libs.*;

import models.*;

public class PlanoDeCursoCtrl extends ControllerBase {

    public static Result exibir() {
        return ok(Json.toJson(getPlanoDeCurso()));
    }

}
