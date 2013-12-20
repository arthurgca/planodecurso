package acceptance;

import org.junit.Test;

import play.libs.F.Callback;
import play.test.TestBrowser;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.fluentlenium.adapter.util.SharedDriver;
import org.fluentlenium.core.domain.FluentWebElement;

import static org.fest.assertions.Assertions.assertThat;

@SharedDriver(type = SharedDriver.SharedType.ONCE)
public class US1Test {

    @Test
    public void deveMostrarDisciplinasAlocadasNoPrimeiroPeriodo() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");

                FluentWebElement periodo1 = browser.findFirst("#periodo-1");
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

                FluentWebElement periodo1 = browser.findFirst("#periodo-1");
                assertThat(periodo1.getText()).contains("24 créditos");
            }
        });
    }

}
