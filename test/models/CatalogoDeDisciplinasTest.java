import org.junit.*;

import java.util.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class CatalogoDeDisciplinasTest {

    private CatalogoDeDisciplinas disciplinas;

    @Before
    public void setup() {
        disciplinas = new CatalogoDeDisciplinas();
    }

    @Test
    public void deveRetornarDisciplinaPorId() {
        assertThat(disciplinas.get("CALCULO2")).isInstanceOf(Disciplina.class);
    }

    @Test(expected=IllegalArgumentException.class)
    public void deveLancarExcecaoQuandoIdDaDisciplinaNaoExiste() {
        disciplinas.get("MINECRAFT");
    }

    @Test
    public void deveConterDisciplinasDoPrimeiroPeriodo() {
        assertThat(disciplinas.getAll()).onProperty("id")
            .contains("CALCULO1", "LPT", "P1", "LP1");
    }

    @Test
    public void deveConterDisciplinasDoSegundoPeriodo() {
        assertThat(disciplinas.getAll()).onProperty("id")
            .contains("CALCULO2", "DISCRETA", "P2");
    }

    @Test
    public void deveConterDisciplinasDoTerceiroPeriodo() {
        assertThat(disciplinas.getAll()).onProperty("id")
            .contains("LINEAR", "PROBABILIDADE", "EDA");
    }

}
