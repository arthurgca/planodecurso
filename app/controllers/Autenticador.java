package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Autenticador extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        ctx.flash().put(
          "warning",
          "Você precisa efetuar um login para fazer isso.");
        return redirect(routes.Application.login());
    }
}
