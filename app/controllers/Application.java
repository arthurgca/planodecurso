package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    public static Form<Login> LoginForm = Form.form(Login.class);

    public static Form<Cadastro> CadastroForm = Form.form(Cadastro.class);

    public static Result index() {
        if (isAutenticado()) {
            return redirect(routes.PlanoDeCursoApp.index());
        } else {
            return ok(index.render());
        }
    }

    public static Result login() {
        if (isAutenticado()) {
            flash("warning", "Você já efetuou um login.");
            return redirect(routes.PlanoDeCursoApp.index());
        } else {
            return ok(login.render(LoginForm));
        }
    }

    public static Result autenticar() {
        Form<Login> form = LoginForm.bindFromRequest();

        if (isAutenticado()) {
            flash("warning", "Você já efetuou um login.");
            return redirect(routes.PlanoDeCursoApp.index());
        } else if (form.hasErrors()) {
            return badRequest(login.render(form));
        } else {
            session().clear();
            session("email", form.get().email);
            return redirect(routes.PlanoDeCursoApp.index());
        }
    }

    public static Result logout() {
        if (!isAutenticado()) {
            flash("warning", "Você não efetuou um login.");
            return redirect(routes.Application.index());
        } else {
            session().clear();
            flash("success", "Logout efetuado com sucesso.");
            return redirect(routes.Application.login());
        }
    }

    public static Result cadastrar() {
        if (isAutenticado()) {
            flash("warning", "Você já está cadastrado.");
            return redirect(routes.PlanoDeCursoApp.index());
        } else {
            return ok(cadastrar.render(CadastroForm));
        }
    }

    public static Result submeteCadastro() {
        if (isAutenticado()) {
            flash("warning", "Você já está cadastrado.");
            return redirect(routes.PlanoDeCursoApp.index());
        }

        Form<Cadastro> form = CadastroForm.bindFromRequest();

        if(form.hasErrors()) {
            return badRequest(cadastrar.render(form));
        } else {
            form.get().getUsuario().save();
            flash("success",
                  "Usuário cadastrado com sucesso. Faça login para continuar.");
            return redirect(routes.Application.login());
        }
    }

    public static class Login {
        public String email;
        public String senha;

        public String validate() {
            if (Usuario.autenticar(email, senha) == null) {
                return "E-mail ou senha inválidos.";
            } else {
                return null;
            }
        }
    }

    public static class Cadastro {
        public String nome;
        public String email;
        public String senha;
        public String confirmacao;

        public String validate() {
            if (nome.isEmpty()) {
                return "O campo Nome é obrigatório";
            } else if (email.isEmpty()) {
                return "O campo Email é obrigatório";
            } else if (Usuario.find.byId(email) != null) {
                return "Este Email já foi cadastrado";
            } else if (senha.isEmpty()) {
                return "O campo Senha é obrigatório";
            } else if (!senha.equals(confirmacao)) {
                return "O campo Confirmação está incorreto";
            }
            return null;
        }

        public Usuario getUsuario() {
            return new Usuario(email, nome, senha);
        }
    }

    private static boolean isAutenticado() {
        return session().get("email") != null;
    }
}
