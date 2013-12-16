import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class PlanoDeCursoTest {

    @Test
    public void deveCriarPlanoPrimeiroPeriodo() {
        PlanoDeCurso plano = PlanoDeCurso.criarPlanoFera();
        assertThat(plano.getPeriodos().get(0).getDisciplinas()).onProperty("nome")
            .containsOnly("Calculo Diferencial e Integral I",
                          "Álgebra Vetorial e Geometria Analítica",
                          "Leitura e Produção de Textos",
                          "Programação I",
                          "Introdução à Computação",
                          "Laboratório de Programação I");
    }

}
