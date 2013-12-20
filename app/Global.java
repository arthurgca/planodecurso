import java.util.Locale;
import java.text.ParseException;

import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;

import models.CatalogoDeDisciplinas;
import models.Disciplina;

public class Global extends GlobalSettings {

    private static final CatalogoDeDisciplinas catalogoDeDisciplinas = new CatalogoDeDisciplinas();

    public void onStart(Application app) {
        configureFormatters();
        configureDisciplinas();
    }

    private void configureFormatters() {
        Formatters.register(Disciplina.class, new SimpleFormatter<Disciplina>() {
          @Override
          public Disciplina parse(String input, Locale l) throws ParseException {
              Disciplina disciplina = null;

              try {
                  disciplina = catalogoDeDisciplinas.get(input);
              } catch (IllegalArgumentException e) {
                  throw new ParseException("No valid input", 0);
              }

              return disciplina;
          }

          @Override
          public String print(Disciplina disciplina, Locale l) {
              return disciplina.getId();
          }});
    }

    private void configureDisciplinas() {
        CatalogoDeDisciplinas.register("CALCULO1", "Calculo Diferencial e Integral I", 4);
        CatalogoDeDisciplinas.register("VETORIAL", "Álgebra Vetorial e Geometria Analítica", 4);
        CatalogoDeDisciplinas.register("LPT", "Leitura e Produção de Textos", 4);
        CatalogoDeDisciplinas.register("P1", "Programação I", 4);
        CatalogoDeDisciplinas.register("IC", "Introdução à Computação", 4);
        CatalogoDeDisciplinas.register("LP1", "Laboratório de Programação I", 4);

        CatalogoDeDisciplinas.register("CALCULO2", "Cálculo Diferencial e Integral II", 4, "CALCULO1");
        CatalogoDeDisciplinas.register("DISCRETA", "Matemática Discreta", 4);
        CatalogoDeDisciplinas.register("MC", "Metodologia Científica", 4);
        CatalogoDeDisciplinas.register("P2", "Programação II", 4, "P1", "LP1", "IC");
        CatalogoDeDisciplinas.register("TG", "Teoria dos Grafos", 2, "P1", "LP1");
        CatalogoDeDisciplinas.register("FC", "Fundamentos de Física Clássica", 4, "CALCULO1", "VETORIAL");
        CatalogoDeDisciplinas.register("LP2", "Laboratório de Programação II", 4, "P1", "LP1", "IC");

        CatalogoDeDisciplinas.register("LINEAR", "Álgebra Linear", 4, "VETORIAL");
        CatalogoDeDisciplinas.register("PROBABILIDADE", "Probabilidade e Estatística", 4, "CALCULO2");
        CatalogoDeDisciplinas.register("TC", "Teoria da Computação", 4, "TG", "IC", "DISCRETA");
        CatalogoDeDisciplinas.register("EDA", "Estruturas de Dados e Algoritmos", 4, "P2", "LP2", "TG");
        CatalogoDeDisciplinas.register("FM", "Fundamentos de Física Moderna", 4, "CALCULO2", "FC");
        CatalogoDeDisciplinas.register("GI", "Gerência da Informação", 4);
        CatalogoDeDisciplinas.register("LEDA", "Lab de Estruturas de Dados e Algoritmos", 4, "P2", "LP2", "TG");
    }

}
