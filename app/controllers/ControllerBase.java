package controllers;

import java.util.*;

import play.mvc.*;

import models.*;

import config.Global;

public abstract class ControllerBase extends Controller {

    protected static PlanoDeCurso getPlanoDeCurso() {
        return Global.PLANO_DE_CURSO_GLOBAL;
    }

    protected static Disciplina getDisciplina(int id) {
        return Disciplina.Registro.get(id);
    }

    protected static void resetarDemo() {
        Global.PLANO_DE_CURSO_GLOBAL = PlanoDeCurso.getPlanoInicial();
    }

    protected static Set<Disciplina> getDisciplinas() {
        return Disciplina.Registro.getAll();
    }

}
