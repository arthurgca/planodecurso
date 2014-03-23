package config;

import java.util.*;
import javax.persistence.*;

import play.*;
import play.libs.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {

    public static PlanoDeCurso PLANO_DE_CURSO_GLOBAL;

    @Override
    public void onStart(Application app) {
        carregarInitialData(app);
        criaUsuariosIniciais(app);
    }

    private void carregarInitialData(Application app) {
        if (Disciplina.find.all().isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String,List<Object>> all =
                (Map<String,List<Object>>) Yaml.load("initial-data.yml");

            Ebean.save(all.get("curriculos"));

            for (Object curriculo : all.get("curriculos")) {
                for(Disciplina disciplina : ((Curriculo) curriculo).disciplinas) {
                    Ebean.saveManyToManyAssociations(disciplina, "requisitos");
                }
            }

            Ebean.save(all.get("grades"));

            for (Object grade : all.get("grades")) {
                for(Periodo periodo : ((Grade) grade).periodos) {
                    Ebean.saveManyToManyAssociations(periodo, "disciplinas");
                }
            }
        }
    }
    
    private void criaUsuariosIniciais(Application app) {
        Usuario usuario;
        String email;
        String nome;
        String senha;
        for (int i = 0; i <= 30; i++) {
            email = String.format("usuario%d@example.com", i);
            nome = String.format("Usuario %d", i);
            senha = String.format("senha%d", i);
            usuario = new Usuario(email,nome,senha);
            usuario.setPlanoDeCurso(PlanoDeCurso.criarPlanoInicial());
            Ebean.save(usuario);
        }
    }
}
