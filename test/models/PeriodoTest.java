package models;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class PeriodoTest {

    @Test
    public void deveCalcularTotalDeCreditos() {
        Periodo periodo = new Periodo(1,
                                      new Disciplina("D1", null, 4),
                                      new Disciplina("D2", null, 3),
                                      null,
                                      new Disciplina("D4", null, 0));
        assertThat(periodo.getTotalCreditos()).isEqualTo(7);
    }

    @Test
    public void deveCompararIgualdadeUsandSemestre() {
        assertThat(new Periodo(2, new Disciplina("D1", null, 4)))
            .isEqualTo(new Periodo(2, new Disciplina("D2", null, 4)));

        assertThat(new Periodo(2, new Disciplina("D1", null, 4)))
            .isNotEqualTo(new Periodo(3, new Disciplina("D1", null, 4)));;
    }

}
