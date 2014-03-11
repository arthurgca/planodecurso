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
        assertEquals(7, node.get("id").numberValue());
        assertEquals("Programação II", node.get("nome").textValue());
        assertEquals(4, node.get("creditos").numberValue());
        assertTrue(node.get("requisitos").isArray());
    }
}
