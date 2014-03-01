package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

public class DisciplinaTest {

    private Disciplina d1;
    private Disciplina d2;
    private Disciplina d3;
    private Disciplina d7;

    @Before
    public void setUp() {
        d1 = new Disciplina(1, "Programação I", 4);
        d2 = new Disciplina(5, "Int. à Computacação", 4);
        d3 = new Disciplina(6, "Lab. de Programação I", 4);

        List<Disciplina> deps = new ArrayList<Disciplina>();
        deps.add(d1);
        deps.add(d2);
        deps.add(d3);

        d7 = new Disciplina(7, "Programação II", 4, deps);
    }

    @Test
    public void deveCompararIgualdadeUsandId() {
        assertTrue(d1.equals(d1));
        assertTrue(d1.equals(new Disciplina(1, "Fake", 7)));
    }

    @Test
    public void deveSerializarCorretamente() {
        JsonNode node = Json.toJson(d7);

        assertEquals(7, node.get("id").intValue());

        assertEquals("Programação II", node.get("nome").textValue());

        assertEquals(4, node.get("creditos").intValue());

        Iterator<JsonNode> deps = node.get("requisitos").elements();
        assertEquals("Programação I", deps.next().get("nome").textValue());
        assertEquals("Int. à Computacação", deps.next().get("nome").textValue());
        assertEquals("Lab. de Programação I", deps.next().get("nome")
                     .textValue());
        assertFalse(deps.hasNext());
    }
}
