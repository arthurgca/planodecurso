package controllers;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import static play.mvc.Http.Status.*;
import play.test.*;
import static play.test.Helpers.*;

public class ExibirCurriculoTest extends TestBase {

    @Before
    public void setUp() {
        carregarTestData();
    }

    @Test
    public void sucesso() {
        Result result = callAction(controllers.routes.ref.Curriculos.exibir(1));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }

}
