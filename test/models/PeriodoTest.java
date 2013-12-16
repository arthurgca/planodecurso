import org.junit.*;

import java.util.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class PeriodoTest {

    @Test
    public void deveCalcularTotalDeCreditos() {
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();

        disciplinas.add(new Disciplina("Seminários", 2));
        disciplinas.add(new Disciplina("Teoria dos Grafos", 2));

        Periodo periodo = new Periodo(1, disciplinas);

        assertThat(periodo.getTotalCreditos()).isEqualTo(4);

        periodo.addDisciplina(new Disciplina("Sistemas da Informação", 4));

        assertThat(periodo.getTotalCreditos()).isEqualTo(8);
    }

}
