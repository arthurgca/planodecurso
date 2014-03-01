package config;

import java.util.*;
import java.text.*;

import play.*;
import play.libs.*;
import play.data.format.*;
import play.data.format.Formatters.*;

import models.*;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        configurarDadosIniciais(app);
        configurarDataBinds(app);
    }

    private void configurarDadosIniciais(Application app) {
        @SuppressWarnings("unchecked")
        Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");

        for(Object obj : all.get("disciplinas")) {
            Disciplina disciplina = (Disciplina) obj;

            Set<Disciplina> requisitos = new HashSet<Disciplina>();

            if (disciplina.requisitos != null) {
                for (Disciplina requisito : disciplina.requisitos) {
                    requisitos.add(Disciplina.Registro.get(requisito.id));
                }
            }

            Disciplina.Registro.registrarDisciplina(disciplina.id,
                                                    disciplina.nome,
                                                    disciplina.creditos,
                                                    requisitos);
        }
    }

    private void configurarDataBinds(Application app) {
        Formatters.register(Disciplina.class,
                            new SimpleFormatter<Disciplina>() {
                                @Override
                                    public Disciplina parse(String input, Locale locale)
                                    throws ParseException {
                                    return Disciplina.Registro.get(Integer.valueOf(input));
                                }

                                @Override
                                    public String print(Disciplina disciplina, Locale locale) {
                                    return String.valueOf(disciplina.id);
                                }

                            });
    }
}
