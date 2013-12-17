import org.junit.*;

import java.util.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class PeriodoTest {

    private static final CatalogoDeDisciplinas catalogoDeDisciplinas = new CatalogoDeDisciplinas();

    @Test
    public void deveCalcularTotalDeCreditos() {
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();

        disciplinas.add(catalogoDeDisciplinas.get("TC"));
        disciplinas.add(catalogoDeDisciplinas.get("TG"));

        Periodo periodo = new Periodo(1, disciplinas);

        assertThat(periodo.getTotalCreditos()).isEqualTo(6);

        periodo.addDisciplina(catalogoDeDisciplinas.get("LP2"));

        assertThat(periodo.getTotalCreditos()).isEqualTo(10);
    }

}
