package controllers;

import play.mvc.*;
import play.libs.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.DynamicForm;
import play.data.Form;
import java.util.List;

import models.*;
import views.html.*;

public class PlanoDeCursoApp extends AreaPrivada {

    public static Result index() {
        return ok(home.render(getUsuarioAtual()));
    }

    public static Result busca() {
        return ok(busca.render(getUsuarioAtual(), Estudante.find.all()));
    }

    public static Result buscaNome() {
        DynamicForm form = Form.form().bindFromRequest();
        String nome = form.get("nome");
        List<Estudante> estudantes = Estudante.find.where().like("nome", "%"+nome+"%").findList();
        return ok(busca.render(getUsuarioAtual(), estudantes));
}

    public static Result verPlano(Long planoId) {
        Estudante estudanteBusca = Estudante.find.where().eq("plano_id", planoId).findUnique();
        if (estudanteBusca != null) {
            return ok(verplano.render(getUsuarioAtual(), estudanteBusca));
        }
        return index();
    }



}
