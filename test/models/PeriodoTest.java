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
        Periodo periodo = new Periodo(1,
                                      new Disciplina("D1", null, 4),
                                      new Disciplina("D2", null, 3),
                                      null,
                                      new Disciplina("D4", null, 0));
        assertThat(periodo.getTotalCreditos()).isEqualTo(7);
    }

}
