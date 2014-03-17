package controllers;

import models.Usuario;
import play.data.*;
import static play.data.Form.*;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;

public class Application extends Controller {
	
    public static Result index() {
        return ok(index.render());
    }
    
    public static Result login() {
        return ok(login.render(Form.form(Login.class)));
    }
     
    public static Result autenticar() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                routes.PlanoDeCursoApp.index());
        }
    }
    
    public static Result logout() {
        session().clear();
        flash("success", "Foi deslogado com sucesso!");
        return redirect(
            routes.Application.login()
        );
    }
    
    public static class Login {

        public String email;
        public String senha;

        public String validate() {
            if (Usuario.autenticar(email, senha) == null) {
              return "Usuario ou senha com erro";
            }
            return null;
        }
    }

}
