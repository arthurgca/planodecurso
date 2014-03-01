package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;

import com.fasterxml.jackson.databind.JsonNode;

public class DisciplinaTest extends test.TestBase {
    @Test
    public void serializaCorretamente() {
        JsonNode node = Json.toJson(disciplina("Programação II"));

        assertEquals(7, node.get("id").intValue());
        assertEquals("Programação II", node.get("nome").textValue());
        assertEquals(4, node.get("creditos").intValue());

        Iterator<JsonNode> requisitoNodes = node.get("requisitos").elements();

        Set<String> requisitos = new HashSet<String>();
        requisitos.add(requisitoNodes.next().get("nome").textValue());
        requisitos.add(requisitoNodes.next().get("nome").textValue());
        requisitos.add(requisitoNodes.next().get("nome").textValue());

        assertTrue(requisitos.contains("Programação I"));
        assertTrue(requisitos.contains("Int. à Computação"));
        assertTrue(requisitos.contains("Lab. de Programação I"));

        assertFalse(requisitoNodes.hasNext());
    }
}
