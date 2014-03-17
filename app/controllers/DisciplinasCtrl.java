package controllers;

import play.mvc.*;
import play.libs.*;

import models.*;

public class DisciplinasCtrl extends AreaPrivada {

    public static Result listar() {
        return ok(Json.toJson(getDisciplinas()));
    }

}
