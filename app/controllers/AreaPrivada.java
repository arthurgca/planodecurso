package controllers;

import java.util.*;

import play.mvc.*;
import com.avaje.ebean.*;

import models.*;

import config.Global;

@Security.Authenticated(Autenticador.class)
public abstract class AreaPrivada extends Controller {

    protected static Estudante getUsuarioAtual() {
        return Estudante.find.byId(request().username());
    }

    protected static Plano getPlano() {
        return getUsuarioAtual().getPlano();
    }

    protected static Curriculo getCurriculo() {
        return getPlano().getCurriculo();
    }
}
