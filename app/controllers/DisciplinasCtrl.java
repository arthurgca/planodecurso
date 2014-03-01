package controllers;

import play.mvc.*;
import play.libs.*;

import models.*;

public class DisciplinasCtrl extends ControllerBase {

    public static Result listar() {
        return ok(Json.toJson(getDisciplinas()));
    }

}
