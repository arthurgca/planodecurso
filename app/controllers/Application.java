package controllers;

import java.util.ArrayList;
import java.util.List;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import models.Periodo;
import models.PlanoDeCurso;
import models.CatalogoDeDisciplinas;
import views.html.index;

public class Application extends Controller {

    static final CatalogoDeDisciplinas disciplinas = new CatalogoDeDisciplinas();

    static final Form<PlanoDeCurso> planoForm = Form.form(PlanoDeCurso.class);

    public static Result index() {
        PlanoDeCurso plano = PlanoDeCurso.criarPlanoInicial();
        return ok(index.render(appendBlankForm(planoForm.fill(plano)), disciplinas));
    }

    public static Result submit() {
        Form<PlanoDeCurso> filledForm = trimBlankForms(planoForm.bindFromRequest());

        if(filledForm.hasErrors()) {
            return badRequest(index.render(filledForm, disciplinas));
        } else {
            PlanoDeCurso plano = filledForm.get();
            return ok(index.render(appendBlankForm(planoForm.fill(plano)), disciplinas));
        }
    }

    private static Form<PlanoDeCurso> trimBlankForms(Form<PlanoDeCurso> form) {
        if (!form.hasErrors()) {
            PlanoDeCurso plano = form.get();

            List<Periodo> blanks = new ArrayList<Periodo>();
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
