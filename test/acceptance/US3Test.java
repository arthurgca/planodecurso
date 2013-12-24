package acceptance;

import org.junit.Test;
import org.openqa.selenium.interactions.Actions;

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
public class US3Test {

	@Test
	public void deveMostrarDisciplinasOfertadasAteOQuintoPeriodo() {
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

				assertThat(browser.pageSource())
				.contains("Métodos Estatísticos")
				.contains("Organização e Arquitetura de Computadores")
				.contains("Laboratório de Organização e Arquitetura de Computadores")
				.contains("Paradigmas de Linguagens de Programação")
				.contains("Lógica Matemática")
				.contains("Sistemas de Informação I")
				.contains("Engenharia de Software I");

				assertThat(browser.pageSource())
				.contains("Informática e Sociedade")
				.contains("Análise e Técnicas de Algoritmos")
				.contains("Compiladores")
				.contains("Redes de Computadores")
				.contains("Bancos de Dados I")
				.contains("Sistemas de Informação II")
				.contains("Laboratório de Engenharia de Software");

				assertThat(browser.pageSource())
				.contains("TECC (Administração Financeira)")
				.contains("TECC (Realidade Virtual)")
				.contains("TECC (Administração de Sistemas)")
				.contains("TECC (Análise de Dados I)")
				.contains("TECC (Arquitetura de Software)")
				.contains("TECC (Desenvolvimento Dirigido de Modelos)");
			}
		});
	}

	@Test
	public void deveAdicionarPeriodosAutomaticamente() {
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

				FluentWebElement programacao2 = browser.findFirst("#disciplinas-ofertadas .disciplina.P2");
				FluentWebElement periodo2 = browser.findFirst("#periodo-2 .sortable-list");
				new Actions(browser.getDriver())
				.dragAndDrop(programacao2.getElement(), periodo2.getElement()).perform();
				
				forceAwait(5000);
				
				periodo2 = browser.findFirst("#periodo-2 .sortable-list");
				FluentWebElement lp2 = browser.findFirst("#disciplinas-ofertadas .disciplina.LP2");
				new Actions(browser.getDriver())
				.dragAndDrop(lp2.getElement(), periodo2.getElement()).perform();
				
				forceAwait(5000);
				
				periodo2 = browser.findFirst("#periodo-2 .sortable-list");
				FluentWebElement discreta = browser.findFirst("#disciplinas-ofertadas .disciplina.DISCRETA");
				new Actions(browser.getDriver())
				.dragAndDrop(discreta.getElement(), periodo2.getElement()).perform();
				
				forceAwait(5000);
				
				FluentWebElement grafos = browser.findFirst("#disciplinas-ofertadas .disciplina.TG");
				periodo2 = browser.findFirst("#periodo-2 .sortable-list");
				new Actions(browser.getDriver())
				.dragAndDrop(grafos.getElement(), periodo2.getElement()).perform();		
		
				forceAwait(5000);

				FluentWebElement periodo3 = browser.findFirst("#periodo-3 .sortable-list");
				FluentWebElement linear = browser.findFirst("#disciplinas-ofertadas .disciplina.LINEAR");
				new Actions(browser.getDriver())
				.dragAndDrop(linear.getElement(), periodo3.getElement()).perform();	
				
				forceAwait(5000);
				
				periodo2 = browser.findFirst("#periodo-2 .sortable-list");
				assertThat(periodo2.getText())
				.contains("Programação II")
				.contains("Laboratório de Programação II")
				.contains("Matemática Discreta")
				.contains("Teoria dos Grafos");

				periodo3 = browser.findFirst("#periodo-3 .sortable-list");
				assertThat(periodo3.getText())
				.contains("Álgebra Linear");
			}
		});
	}






}
