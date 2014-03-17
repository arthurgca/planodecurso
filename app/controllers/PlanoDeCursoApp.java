package controllers;

import models.Usuario;
import play.mvc.*;
import views.html.meucurso;

public class PlanoDeCursoApp extends PrivateControllerBase {
	
	@Security.Authenticated(Autenticador.class)
    public static Result index() {
        return ok(meucurso.render(Usuario.find.byId(request().username())));
    }


}
