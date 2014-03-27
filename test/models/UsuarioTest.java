package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import org.mindrot.jbcrypt.BCrypt;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;

import com.fasterxml.jackson.databind.JsonNode;

public class UsuarioTest extends WithApplication {

    Usuario u1;

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        u1 = new Usuario("MyString", "MyString", "MyString");
    }

    @Test
    public void construtor() {
        assertEquals("MyString", u1.getEmail());
        assertEquals("MyString", u1.getNome());
        assertTrue(BCrypt.checkpw("MyString", u1.getSenha()));
    }

    @Test
    public void autenticar() {
        u1.save();
        assertEquals(u1, u1.autenticar("MyString", "MyString"));
    }

    @Test
    public void toJson() {
        JsonNode node = Json.toJson(u1);
        assertEquals("MyString", node.get("email").textValue());
        assertEquals("MyString", node.get("nome").textValue());
        assertNull(node.get("senha"));
        assertNotNull(node.get("plano"));
    }
}
