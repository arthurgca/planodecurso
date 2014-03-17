package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        if (isAutenticado()) {
            return redirect(routes.PlanoDeCursoApp.index());
        } else {
            return ok(index.render());
        }
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
        flash("success", "Logout efetuado com sucesso.");
        return redirect(
            routes.Application.login()
        );
    }

    public static Result cadastrar() {
        return ok(cadastrar.render(Form.form(Usuario.class)));
    }

    public static Result submeteCadastro() {
        Form<Usuario> cadastroForm = Form.form(Usuario.class).bindFromRequest();
        if(cadastroForm.hasErrors()) {
            return badRequest(cadastrar.render(cadastroForm));
        }
        if(Usuario.find.all().contains(cadastroForm.get())) {
            cadastroForm.reject("Esse Nome de Usuário já existe");
            return badRequest(cadastrar.render(cadastroForm));
        } else {
            Usuario usuario = cadastroForm.get();
            usuario.save();
            flash("success", "Usuário cadastrado com sucesso. " +
                  "Faça login para continuar.");
            return redirect(routes.Application.login());
        }
    }

    public static class Login {

        public String email;
        public String senha;

        public String validate() {
            if (Usuario.autenticar(email, senha) == null) {
                return "E-mail ou senha inválidos.";
            }
            return null;
        }
    }

    private static boolean isAutenticado() {
        return session().get("email") != null;
    }
}
