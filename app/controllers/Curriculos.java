package controllers;

import play.mvc.*;
import play.libs.*;

import models.*;

public class Curriculos extends Controller {

    public static Result listar() {
        return ok(Json.toJson(Curriculo.find.all()));
    }

    public static Result exibir(int curriculoId) {
        return ok(Json.toJson(Curriculo.find.byId(curriculoId)));
    }

}
