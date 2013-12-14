package controllers;

import java.util.*;

import play.*;
import play.mvc.*;

import models.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        ArrayList<Periodo> periodos = new ArrayList<Periodo>();
        periodos.add(new Periodo());
        return ok(index.render(periodos));
    }

}
