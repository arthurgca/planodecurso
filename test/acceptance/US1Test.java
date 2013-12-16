import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.fluentlenium.core.domain.FluentWebElement;

public class US1Test {

    @Test
    public void deveMostrarDisciplinasAlocadasNoPrimeiroPeriodo() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");

                FluentWebElement periodo1 = browser.findFirst(".periodo-1");

                assertThat(periodo1).isNotNull();

                assertThat(periodo1.getText())
                    .contains("Calculo Diferencial e Integral I")
                    .contains("Álgebra Vetorial e Geometria Analítica")
                    .contains("Leitura e Produção de Textos")
                    .contains("Programação I")
                    .contains("Introdução à Computação")
                    .contains("Laboratório de Programação I");
            }
        });
    }

    @Test
    public void deveMostrarTotaldeCreditosNoPrimeiroPeriodo() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");

                FluentWebElement periodo1 = browser.findFirst(".periodo-1");

                assertThat(periodo1).isNotNull();

                assertThat(periodo1.getText()).contains("24 créditos");
            }
        });
    }

}
