import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.fluentlenium.core.domain.FluentWebElement;

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
}
