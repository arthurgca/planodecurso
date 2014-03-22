package controllers;

import java.util.*;

import play.mvc.*;
import com.avaje.ebean.*;

import models.*;

import config.Global;

@Security.Authenticated(Autenticador.class)
public abstract class AreaPrivada extends Controller {

    protected static PlanoDeCurso getPlanoDeCurso() {
        return PlanoDeCurso.find.all().get(0);
    }
}
