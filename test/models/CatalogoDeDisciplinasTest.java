import org.junit.*;

import java.util.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class CatalogoDeDisciplinasTest {

    private CatalogoDeDisciplinas catalogoDeDisciplinas;

    @Before
    public void setup() {
        catalogoDeDisciplinas = new CatalogoDeDisciplinas();
    }

    @Test
    public void deveRetornarDisciplinaPorId() {
        assertThat(catalogoDeDisciplinas.get("CALCULO2"))
            .isInstanceOf(Disciplina.class);
    }

    @Test(expected=IllegalArgumentException.class)
    public void deveLancarExcecaoQuandoIdDaDisciplinaNaoExiste() {
        catalogoDeDisciplinas.get("MINECRAFT");
    }

    @Test
    public void deveRetornarTodasAsDisciplinas() {
        assertThat(catalogoDeDisciplinas.getAll()).onProperty("id")
            .contains("CALCULO1",
                      "CALCULO2",
                      "PROBABILIDADE");
    }

}
