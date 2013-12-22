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
        configureDisciplinas();
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
        
        CatalogoDeDisciplinas.register("OAC", "Organização e Arquitetura de Computadores", 4, "EDA", "LEDA","FM");
        CatalogoDeDisciplinas.register("LOAC", "Laboratório de Organização e Arquitetura de Computadores", 4, "EDA", "LEDA","FM");
        CatalogoDeDisciplinas.register("PLP", "Paradigmas de Linguagens de Programação", 2, "EDA", "LEDA", "TC");
        CatalogoDeDisciplinas.register("METODOS", "Métodos Estatísticos", 4, "LINEAR", "PROBABILIDADE");
        CatalogoDeDisciplinas.register("LOGICA", "Lógica Matemática", 4, "TC");
        CatalogoDeDisciplinas.register("SI1", "Sistemas de Informação I", 4, "GI");
        CatalogoDeDisciplinas.register("ES", "Engenharia de Software I", 4, "PROBABILIDADE", "P2", "LP2");
        
        CatalogoDeDisciplinas.register("ATAL", "Análise e Técnicas de Algoritmos", 4, "EDA", "LEDA", "CALCULO2", "LOGICA");
        CatalogoDeDisciplinas.register("REDES", "Redes de Computadores", 4, "OAC", "LOAC");
        CatalogoDeDisciplinas.register("COMPILADORES", "Compiladores", 4, "OAC", "LOAC", "PLP");
        CatalogoDeDisciplinas.register("BD1", "Bancos de Dados I", 4, "SI1");
        CatalogoDeDisciplinas.register("SI2", "Sistemas de Informação II", 4, "SI1");
        CatalogoDeDisciplinas.register("LES", "Laboratório de Engenharia de Software", 2, "ES");
        CatalogoDeDisciplinas.register("INFOSOC", "Informática e Sociedade", 2);
        
        //Optativas
        CatalogoDeDisciplinas.register("OPT_FUT", "Futsal", 2);
        CatalogoDeDisciplinas.register("OPT_BASQ", "Basquetebol", 2);
        CatalogoDeDisciplinas.register("OPT_SOCIND", "Sociologia Industrial I", 3);
        CatalogoDeDisciplinas.register("OPT_ADM", "Administração", 4);
        CatalogoDeDisciplinas.register("OPT_ECON", "Economia", 4);
        CatalogoDeDisciplinas.register("OPT_CALCULO3", "Cálculo Diferencial e Integral III", 4, "CALCULO2");
        CatalogoDeDisciplinas.register("OPT_EQDIF", "Equações Diferenciais", 4, "CALCULO2", "LINEAR");
        CatalogoDeDisciplinas.register("OPT_ING", "Inglês", 4);
    
        
        //TECC's
        CatalogoDeDisciplinas.register("TECC_ADMFINC", "TECC (Administração Financeira)", 4);
        CatalogoDeDisciplinas.register("TECC_RV", "TECC (Realidade Virtual)", 4, "SI1");
        CatalogoDeDisciplinas.register("TECC_ADMSI", "TECC (Administração de Sistemas)", 4);
        CatalogoDeDisciplinas.register("TECC_ANALISE1", "TECC (Análise de Dados I)", 4, "PROBABILIDADE");
        CatalogoDeDisciplinas.register("TECC_ARQSOFT", "TECC (Arquitetura de Software)", 4, "ES", "SI1");
        CatalogoDeDisciplinas.register("TECC_DDM", "TECC (Desenvolvimento Dirigido de Modelos)", 4, "ES");
    }

}
