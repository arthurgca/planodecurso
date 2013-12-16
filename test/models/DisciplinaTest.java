import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class DisciplinaTest {

    @Test
    public void deveCompararIgualdadeUsandoNome() {
        assertThat(new Disciplina("Seminários", 2)).isEqualTo(new Disciplina("Seminários", 4));
        assertThat(new Disciplina("Seminários", 2)).isNotEqualTo(new Disciplina("Teoria dos Grafos", 4));
    }

}
