package controllers;

import static org.junit.Assert.*;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.session;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;

import com.google.common.collect.ImmutableMap;

public class CadastroTest extends test.TestBase{

	@Test
	public void cadastroSucesso() {
	    Result resultadoCadastro = callAction(
	        controllers.routes.ref.Application.submeteCadastro(),
	        fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
	            "email", "teste@example.com",
	            "nome", "Teste",
	            "senha", "senhateste"))
	    );
	    Result resultadoLogin = callAction(
		        controllers.routes.ref.Application.autenticar(),
		        fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
		            "email", "teste@example.com",
		            "senha", "senhateste"))
		    );
	    assertEquals(303, status(resultadoLogin));
	    assertEquals("teste@example.com", session(resultadoLogin).get("email"));
	}
	
	@Test
	public void cadastroComErro() {
	    Result resultadoCadastro = callAction(
	        controllers.routes.ref.Application.submeteCadastro(),
	        fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
	            "email", "usuario1@example.com",
	            "nome", "Blabla",
	            "senha", "Blabla"))
	    );
	    assertEquals(400, status(resultadoCadastro));
	    assertNull("teste@example.com", session(resultadoCadastro).get("email"));
	}
    
    
}
