import java.util.concurrent.*;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import static play.test.Helpers.*;

import static org.junit.Assert.*;

import static org.fest.assertions.Assertions.*;

import org.fluentlenium.adapter.util.SharedDriver;
import org.fluentlenium.core.domain.FluentWebElement;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Actions.*;

@SharedDriver(type = SharedDriver.SharedType.ONCE)
public class US2Test {

    @Test
    public void deveMostrarDisciplinasOfertadasAteOTerceiroPeriodo() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");

                assertThat(browser.pageSource())
                    .contains("Calculo Diferencial e Integral I")
                    .contains("Álgebra Vetorial e Geometria Analítica")
                    .contains("Leitura e Produção de Textos")
                    .contains("Programação I")
                    .contains("Introdução à Computação")
                    .contains("Laboratório de Programação I");

                assertThat(browser.pageSource())
                    .contains("Cálculo Diferencial e Integral II")
                    .contains("Matemática Discreta")
                    .contains("Metodologia Científica")
                    .contains("Programação II")
                    .contains("Teoria dos Grafos")
                    .contains("Fundamentos de Física Clássica")
                    .contains("Laboratório de Programação II");

                assertThat(browser.pageSource())
                    .contains("Álgebra Linear")
                    .contains("Probabilidade e Estatística")
                    .contains("Teoria da Computação")
                    .contains("Estruturas de Dados e Algoritmos")
                    .contains("Fundamentos de Física Moderna")
                    .contains("Gerência da Informação")
                    .contains("Lab de Estruturas de Dados e Algoritmos");
            }
        });
    }


    @Test
    public void devePermitirDesalocarDisciplinasAlocadas() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
                public void invoke(TestBrowser browser) {
                    browser.goTo("http://localhost:3333");

                    FluentWebElement periodo1 = browser.findFirst("#periodo-1");

                    assertThat(periodo1.getText())
                        .contains("Calculo Diferencial e Integral I");

                    browser.click("#periodo-1 .CALCULO1 .close");

                    periodo1 = browser.findFirst("#periodo-1");

                    assertThat(periodo1.getText())
                        .doesNotContain("Calculo Diferencial e Integral I");
                }
            });
    }

    @Test
    public void devePermitirAlocarDisciplinas() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
                public void invoke(TestBrowser browser) {
                    browser.goTo("http://localhost:3333");

                    FluentWebElement periodo2 = browser.findFirst("#periodo-2 .sortable-list");

                    assertThat(periodo2.getText())
                        .doesNotContain("Teoria dos Grafos");

                    FluentWebElement teoriaDosGrafos = browser.findFirst("#disciplinas-ofertadas .disciplina.TG");

                    Actions builder = new Actions(browser.getDriver());
                    builder.dragAndDrop(teoriaDosGrafos.getElement(),
                                        periodo2.getElement()).perform();

                    try {
                        Thread.sleep(5000);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    periodo2 = browser.findFirst("#periodo-2");

                    assertThat(periodo2.getText())
                        .contains("Teoria dos Grafos");
                }
            });
    }

}
