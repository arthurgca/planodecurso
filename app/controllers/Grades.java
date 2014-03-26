package controllers;

import play.mvc.*;
import play.libs.*;

import models.*;

public class Grades extends Controller {

    public static Result listar() {
        return ok(Json.toJson(Grade.originais()));
    }

    public static Result exibir(Long gradeId) {
        return ok(Json.toJson(Grade.find.byId(gradeId)));
    }

}
