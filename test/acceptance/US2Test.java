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

import org.openqa.selenium.interactions.Actions;

import static org.fest.assertions.Assertions.assertThat;

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
                    assertThat(periodo1.getText()).contains("Calculo Diferencial e Integral I");

                    browser.click("#periodo-1 .CALCULO1 .close");

                    periodo1 = browser.findFirst("#periodo-1");
                    assertThat(periodo1.getText()).doesNotContain("Calculo Diferencial e Integral I");
                }
            });
    }

    @Test
    public void devePermitirAlocarDisciplinas() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
        		private void forceAwait(long miliseconds) {
                    try {
                        Thread.sleep(miliseconds);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
        		}
        		
                public void invoke(TestBrowser browser) {
                    browser.goTo("http://localhost:3333");

                    FluentWebElement periodo2 = browser.findFirst("#periodo-2 .sortable-list");
                    FluentWebElement grafos = browser.findFirst("#disciplinas-ofertadas .disciplina.TG");

                    new Actions(browser.getDriver())
                    	.dragAndDrop(grafos.getElement(), periodo2.getElement()).perform();

                    forceAwait(5000);
                    
                    periodo2 = browser.findFirst("#periodo-2 .sortable-list");
                    assertThat(periodo2.getText()).contains("Teoria dos Grafos");
                }
            });
    }

    @Test
    public void deveValidarMinimoDeCreditos() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
                public void invoke(TestBrowser browser) {
                    browser.goTo("http://localhost:3333");

                    browser.click("#periodo-1 .CALCULO1 .close");
                    browser.click("#periodo-1 .VETORIAL .close");
                    browser.click("#periodo-1 .LPT .close");

                    assertThat(browser.pageSource()).contains("deve ter um mínimo");
                }
            });
    }

    @Test
    public void deveValidarMaximoDeCreditos() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
	    		private void forceAwait(long miliseconds) {
	                try {
	                    Thread.sleep(miliseconds);
	                } catch(InterruptedException ex) {
	                    Thread.currentThread().interrupt();
	                }
	    		}
	    		
                public void invoke(TestBrowser browser) {
                    browser.goTo("http://localhost:3333");

                    FluentWebElement periodo1 = browser.findFirst("#periodo-1 .sortable-list");
                    FluentWebElement grafos = browser.findFirst("#disciplinas-ofertadas .disciplina.TG");

                    new Actions(browser.getDriver())
                    	.dragAndDrop(grafos.getElement(), periodo1.getElement()).perform();

                    forceAwait(5000);

                    periodo1 = browser.findFirst("#periodo-1 .sortable-list");
                    FluentWebElement fisicaClassica = browser.findFirst("#disciplinas-ofertadas .disciplina.FC");

                    new Actions(browser.getDriver())
                		.dragAndDrop(fisicaClassica.getElement(), periodo1.getElement()).perform();

                    forceAwait(5000);

                    assertThat(browser.pageSource()).contains("deve ter um máximo");
                }
            });
    }

}
