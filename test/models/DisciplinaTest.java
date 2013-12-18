import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class DisciplinaTest {

    @Test
    public void deveCompararIgualdadeUsandId() {
        assertThat(new Disciplina("SEMINARIOS", "Seminários", 2))
            .isEqualTo(new Disciplina("SEMINARIOS", "Seminários", 4));

        assertThat(new Disciplina("SEMINARIO", "Seminários", 2))
            .isNotEqualTo(new Disciplina("SEMINARIOS", "Seminários", 2));
    }

}
