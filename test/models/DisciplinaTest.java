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
        d1 = new Disciplina(1, "Programação I", 4, 1, 4);
        d2 = new Disciplina(5, "Int. à Computacação", 4, 1, 2);
        d3 = new Disciplina(6, "Lab. de Programação I", 4, 1, 4);

        List<Disciplina> deps = new ArrayList<Disciplina>();
        deps.add(d1);
        deps.add(d2);
        deps.add(d3);

        d7 = new Disciplina(7, "Programação II", 4, 2, 8, deps);
    }

    @Test
    public void deveRetornarId() {
        assertEquals(1, d1.getId());
    }

    @Test
    public void deveRetornarNome() {
        assertEquals("Programação I", d1.getNome());
    }

    @Test
    public void deveRetornarCreditos() {
        assertEquals(4, d1.getCreditos());
    }

    @Test
    public void deveRetornarPeriodo() {
        assertEquals(1, d1.getPeriodo());
    }

    @Test
    public void deveRetornarDificuldade() {
        assertEquals(4, d1.getDificuldade());
    }

    @Test
    public void deveRetornarDependencias() {
        assertTrue(d1.getDependencias().isEmpty());
        assertTrue(d7.getDependencias().contains(d1));
        assertTrue(d7.getDependencias().contains(d2));
        assertTrue(d7.getDependencias().contains(d3));
    }

    @Test
    public void deveCompararIgualdadeUsandId() {
        assertTrue(d1.equals(d1));
        assertTrue(d1.equals(new Disciplina(1, "Fake", 7, 7, 7)));
    }

    @Test
    public void deveSerializarCorretamente() {
        JsonNode node = Json.toJson(d7);

        assertEquals(7, node.get("id").intValue());

        assertEquals("Programação II", node.get("nome").textValue());

        assertEquals(4, node.get("creditos").intValue());

        assertEquals(2, node.get("periodo").intValue());

        assertEquals(8, node.get("dificuldade").intValue());

        Iterator<JsonNode> deps = node.get("dependencias").elements();
        assertEquals("Programação I", deps.next().get("nome").textValue());
        assertEquals("Int. à Computacação", deps.next().get("nome").textValue());
        assertEquals("Lab. de Programação I", deps.next().get("nome")
                     .textValue());
        assertFalse(deps.hasNext());
    }
}
