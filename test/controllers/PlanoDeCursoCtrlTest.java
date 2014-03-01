package controllers;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;

import play.mvc.*;
import play.test.*;
import static play.test.Helpers.*;
import static play.mvc.Http.Status.*;

public class PlanoDeCursoCtrlTest extends test.TestBase {
    @Test
    public void exibir() {
        Result result = callAction(controllers.routes.ref.PlanoDeCursoCtrl.exibir());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
    }
}
