package controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.Json;

import models.Periodo;
import models.PlanoDeCurso;
import models.CatalogoDeDisciplinas;
import views.html.index;

public class Application extends Controller {

    private static final CatalogoDeDisciplinas disciplinas = new CatalogoDeDisciplinas();

    private static final Form<PlanoDeCurso> planoForm = Form.form(PlanoDeCurso.class);

    public static Result index() {
        PlanoDeCurso plano = PlanoDeCurso.criarPlanoInicial();
        return ok(index.render(appendBlankForm(planoForm.fill(plano)), disciplinas, null));
    }

    public static Result disciplinasJson() {
        return ok(Json.toJson(disciplinas.getAll()));
    }

    public static Result submit() {
        Form<PlanoDeCurso> filledForm = trimBlankForms(planoForm.bindFromRequest());

        PlanoDeCurso plano = filledForm.get();
        List<ValidationError> errors = plano.validateHack();

        if(errors != null) {
            return badRequest(index.render(filledForm, disciplinas, errors));
        } else {
            return ok(index.render(appendBlankForm(planoForm.fill(plano)), disciplinas, null));
        }
    }

    private static Form<PlanoDeCurso> trimBlankForms(Form<PlanoDeCurso> form) {
        if (!form.hasErrors()) {
            PlanoDeCurso plano = form.get();

            Queue<Periodo> blanks = new LinkedList<Periodo>();
            List<Periodo> periodos = new ArrayList<Periodo>();

            for (Periodo periodo : plano.getPeriodos()) {
                if (periodo.isEmpty()) {
                    blanks.add(periodo);
                } else {
                    if (!blanks.isEmpty()) {
                        periodos.addAll(blanks);
                        blanks.clear();
                    }
                    periodos.add(periodo);
                }
            }

            plano = new PlanoDeCurso(periodos.toArray(new Periodo[periodos.size()]));
            return planoForm.fill(plano);
        } else {
            return form;
        }
    }

    private static Form<PlanoDeCurso> appendBlankForm(Form<PlanoDeCurso> form) {
        PlanoDeCurso plano = form.get();
        if (plano.isEmpty()) {
            return planoForm.fill(new PlanoDeCurso(new Periodo(1)));
        } else {
            List<Periodo> periodos = plano.getPeriodos();
            Periodo ultimo = periodos.get(periodos.size() - 1);
            periodos.add(new Periodo(ultimo.getSemestre() + 1));
            return planoForm.fill(new PlanoDeCurso(periodos.toArray(new Periodo[periodos.size()])));
        }
    }

}
