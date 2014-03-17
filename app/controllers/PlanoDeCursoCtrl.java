package controllers;

import play.mvc.*;
import play.libs.*;

import models.*;

public class PlanoDeCursoCtrl extends PrivateControllerBase {

    public static Result exibir() {
        return ok(Json.toJson(getPlanoDeCurso()));
    }

}
