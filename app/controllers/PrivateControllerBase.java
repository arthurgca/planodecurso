package controllers;

import java.util.*;

import play.mvc.*;
import com.avaje.ebean.*;

import models.*;

import config.Global;

public abstract class PrivateControllerBase extends Controller {

    protected static PlanoDeCurso getPlanoDeCurso() {
        return Global.PLANO_DE_CURSO_GLOBAL;
    }

    protected static Disciplina getDisciplina(int id) {
        return Disciplina.get(id);
    }

    protected static void resetarDemo() {
        Ebean.delete(PlanoDeCurso.find.all());
        Global.PLANO_DE_CURSO_GLOBAL = PlanoDeCurso.criarPlanoInicial();
    }

    protected static Set<Disciplina> getDisciplinas() {
        return Disciplina.getAll();
    }

}
