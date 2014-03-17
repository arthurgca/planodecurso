package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.google.common.collect.ImmutableMap;

public class LogoutUsuarioTest extends test.TestBase {

    @Test
    public void sucesso() {
        Result result = callAction(
            controllers.routes.ref.Application.logout(),
            sessaoAutenticada());
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.login().url());
        assertThat(session(result).get("email")).isNull();
        assertThat(flash(result).get("success")).isNotNull();
    }

    @Test
    public void erroUsuarioNaoAutenticado() {
        Result result = callAction(
            controllers.routes.ref.Application.logout());
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.index().url());
        assertThat(session(result).get("email")).isNull();
        assertThat(flash(result).get("warning")).isNotNull();
    }

}
