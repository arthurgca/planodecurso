package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.google.common.collect.ImmutableMap;

public class LoginUsuarioTest extends TestBase {

    @Before
    public void setUp() {
        carregarTestData();
    }

    @Test
    public void sucesso() {
        Result result = callAction(
            controllers.routes.ref.Application.autenticar(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "email", "bob@example.com",
                "senha", "secret")));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.PlanoDeCursoApp.index().url());
        assertThat(session(result).get("email")).isEqualTo("bob@example.com");
    }

    @Test
    public void erroDadosInvalidos() {
        Result result = callAction(
            controllers.routes.ref.Application.autenticar(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "email", "bob@example.com",
                "senha", "badsecret")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(session(result).get("email")).isNull();
    }

    @Test
    public void erroUsuarioAutenticado() {
        Result result = callAction(
            controllers.routes.ref.Application.autenticar(),
            fakeRequest()
                .withSession("email", "bob@example.com")
                .withFormUrlEncodedBody(ImmutableMap.of(
                    "email", "bob@example.com",
                    "senha", "secret")));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.PlanoDeCursoApp.index().url());
        assertThat(flash(result).get("warning")).isNotNull();
    }

}
