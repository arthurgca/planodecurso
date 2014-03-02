package controllers;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import play.test.*;
import static play.test.Helpers.*;
import static play.mvc.Http.Status.*;

public class AlocacoesCtrlTest extends test.TestBase {
    @Test
    public void criar() {
        Result result = callAction(controllers.routes.ref.AlocacoesCtrl.criar(7, 45));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void criarErro() {
        Result result = callAction(controllers.routes.ref.AlocacoesCtrl.criar(2, 44));
        assertThat(status(result)).isEqualTo(BAD_REQUEST);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

    @Test
    public void deletar() {
        Result result = callAction(controllers.routes.ref.AlocacoesCtrl.deletar(1, 2));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }
}
