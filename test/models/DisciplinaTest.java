package models;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class DisciplinaTest {

    @Test
    public void deveCompararIgualdadeUsandId() {
        assertThat(new Disciplina("SEMINARIOS", "Seminários", 2))
            .isEqualTo(new Disciplina("SEMINARIOS", "Seminários", 4));

        assertThat(new Disciplina("SEMINARIO", "Seminários", 2))
            .isNotEqualTo(new Disciplina("SEMINARIOS", "Seminários", 2));
    }
    
    @Test
    public void deveCriarDisciplinasOptativas() {
        assertThat(new Disciplina("OPT_TESTE", "Seminários", 2).isOptativa());
        assertFalse(new Disciplina("FALSE", "Seminários", 2).isOptativa());
    }
    
    @Test
    public void deveCriarDisciplinasTECC() {
        assertThat(new Disciplina("TECC_TESTE", "Seminários", 2).isTECC());
        assertFalse(new Disciplina("FALSE", "Seminários", 2).isTECC());
    }

}

    


