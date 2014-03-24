package controllers;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import play.test.*;
import static play.test.Helpers.*;
import static play.mvc.Http.Status.*;
import com.google.common.collect.ImmutableMap;

import models.*;

public class CadastrarUsuarioTest extends TestBase {

    @Before
    public void setUp() {
        carregarTestData();
    }

    @Test
    public void sucesso() {
        Result result = callAction(
            controllers.routes.ref.Application.submeteCadastro(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "email", "teste@example.com",
                "nome", "Teste",
                "senha", "senha",
                "confirmacao", "senha")));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.Application.login().url());
        assertThat(flash(result).get("success")).isNotNull();
    }

    @Test
    public void criarPlanoDeCurso() {
        Result result = callAction(
            controllers.routes.ref.Application.submeteCadastro(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "email", "teste@example.com",
                "nome", "Teste",
                "senha", "senha",
                "confirmacao", "senha")));
        assertThat(flash(result).get("success")).isNotNull();
        Usuario u1 = Usuario.find.byId("teste@example.com");
        assertNotNull(u1.planoDeCurso);
    }

    @Test
    public void erroEmailNaoUnico() {
        Result result = callAction(
            controllers.routes.ref.Application.submeteCadastro(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "email", "bob@example.com",
                "nome", "Outro Bob",
                "senha", "senha",
                "confirmacao", "senha")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroEmailInvalido() {
        Result result = callAction(
            controllers.routes.ref.Application.submeteCadastro(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "email", "alice.example.com",
                "nome", "MyString",
                "senha", "MyString",
                "confirmacao", "MyString")));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void erroUsuarioAutenticado() {
        Result result = callAction(
            controllers.routes.ref.Application.submeteCadastro(),
            fakeRequest()
                .withSession("email", "bob@example.com")
                .withFormUrlEncodedBody(ImmutableMap.of(
                    "email", "teste@example.com",
                    "nome", "Teste",
                    "senha", "senha",
                    "confirmacao", "senha")));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(redirectLocation(result)).isEqualTo(
            routes.PlanoDeCursoApp.index().url());
        assertThat(flash(result).get("warning")).isNotNull();
    }

}
