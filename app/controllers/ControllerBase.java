package controllers;

import java.util.*;

import play.mvc.*;

import models.*;

public abstract class ControllerBase extends Controller {

    protected static PlanoDeCurso getPlanoDeCurso() {
        return PlanoDeCurso.getPlanoInicial();
    }

    protected static Disciplina getDisciplina(int id) {
        return Disciplina.Registro.get(id);
    }

    protected static 
Set<Disciplina> getDisciplinas() {
        return Disciplina.Registro.getAll();
    }

}
