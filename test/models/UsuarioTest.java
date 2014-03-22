package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;
import com.fasterxml.jackson.databind.JsonNode;

public class UsuarioTest extends test.TestBase {

    Usuario u1;

    @Before
    public void setUp() {
        u1 = new Usuario("MyString", "MyString", "MyString");
        u1.save();
    }

    @Test
    public void construtor() {
        assertEquals("MyString", u1.email);
        assertEquals("MyString", u1.nome);
        assertEquals("MyString", u1.senha);
    }

    @Test
    public void autenticar() {
        assertEquals(u1, u1.autenticar("MyString", "MyString"));
    }

    @Test
    public void toJson() {
        JsonNode node = Json.toJson(u1);
        assertEquals("MyString", node.get("email").textValue());
        assertEquals("MyString", node.get("nome").textValue());
        assertNull(node.get("senha"));
        assertNotNull(node.get("planoDeCurso"));
    }
}
