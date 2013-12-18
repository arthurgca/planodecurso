import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class PlanoDeCursoTest {

    @Test
    public void deveCriarPlanoInicialComDisciplinasDoPrimeiroPeriodo() {
        PlanoDeCurso plano = PlanoDeCurso.criarPlanoInicial();

        assertThat(plano.getPeriodos().size()).isEqualTo(1);

        assertThat(plano.getPeriodos().get(0).getDisciplinas()).onProperty("id")
            .containsOnly("CALCULO1", "VETORIAL", "LPT", "P1", "IC", "LP1");
    }

}
