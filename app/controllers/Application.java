package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    static final CatalogoDeDisciplinas disciplinas = new CatalogoDeDisciplinas();

    static final Form<PlanoDeCurso> planoForm = Form.form(PlanoDeCurso.class);

    public static Result index() {
        PlanoDeCurso planoInicial = PlanoDeCurso.criarPlanoInicial();
        planoInicial = appendOrRemoveBlanks(planoInicial);
        return ok(index.render(planoForm.fill(planoInicial), disciplinas.getAll()));
    }

    public static Result submit() {
        Form<PlanoDeCurso> filledForm = planoForm.bindFromRequest();

        if(filledForm.hasErrors()) {
            return badRequest(index.render(filledForm, disciplinas.getAll()));
        } else {
            PlanoDeCurso plano = filledForm.get();
            plano = appendOrRemoveBlanks(plano);
            return ok(index.render(planoForm.fill(plano), disciplinas.getAll()));
        }
    }

    private static PlanoDeCurso appendOrRemoveBlanks(PlanoDeCurso plano) {
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

        if (periodos.size() > 0) {
            Periodo ultimo = periodos.get(periodos.size() - 1);
            periodos.add(new Periodo(ultimo.getSemestre() + 1));
        } else {
            periodos.add(new Periodo(1));
        }

        return new PlanoDeCurso(periodos.toArray(new Periodo[periodos.size()]));
    }

}
