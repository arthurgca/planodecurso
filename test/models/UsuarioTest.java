package models;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsuarioTest extends test.TestBase {

	@Test
	public void testaAutenticacao() {
		Usuario user = new Usuario("teste@hotmail.com", "Usuario", "senha");
		user.save();
		assertEquals(user.autenticar("teste@hotmail.com", "senha"), user);
	}

}