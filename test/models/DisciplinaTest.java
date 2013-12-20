package models;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class DisciplinaTest {

    @Test
    public void deveCompararIgualdadeUsandId() {
        assertThat(new Disciplina("SEMINARIOS", "Seminários", 2))
            .isEqualTo(new Disciplina("SEMINARIOS", "Seminários", 4));

        assertThat(new Disciplina("SEMINARIO", "Seminários", 2))
            .isNotEqualTo(new Disciplina("SEMINARIOS", "Seminários", 2));
    }

}
