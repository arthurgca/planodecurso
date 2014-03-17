package controllers;

import org.junit.*;
import static org.junit.Assert.*;

import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.google.common.collect.ImmutableMap;

public class LoginTest extends test.TestBase {
    
	@Test
	public void loginSucesso() {
	    Result result = callAction(
	        controllers.routes.ref.Application.autenticar(),
	        fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
	            "email", "bob@example.com",
	            "senha", "secret"))
	    );
	    assertEquals(303, status(result));
	    assertEquals("bob@example.com", session(result).get("email"));
	}
    
    @Test
    public void loginComErro() {
        Result result = callAction(
            controllers.routes.ref.Application.autenticar(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "email", "bob@example.com",
                "senha", "badpassword"))
        );
        assertEquals(400, status(result));
        assertNull(session(result).get("email"));
    }
    
    @Test
    public void mostraPaginaEstandoAutenticado() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.index(),
            fakeRequest().withSession("email", "bob@example.com")
        );
        assertEquals(200, status(result));
    }    

    @Test
    public void naoMostraPaginaSemAutenticacao() {
        Result result = callAction(
            controllers.routes.ref.PlanoDeCursoApp.index(),
            fakeRequest()
        );
        assertEquals(303, status(result));
        assertEquals("/login", header("Location", result));
    }

}