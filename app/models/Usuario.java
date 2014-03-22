package models;

import javax.persistence.*;
import play.db.ebean.*;

import com.fasterxml.jackson.annotation.*;

@Entity
public class Usuario extends Model {

    @Id
    public String email;

    public String nome;

    @JsonIgnore
    public String senha;

    @OneToOne
    public PlanoDeCurso planoDeCurso;

    public Usuario(String email, String nome, String senha) {
      this.email = email;
      this.nome = nome;
      this.senha = senha;
    }

    public void setPlanoDeCurso(PlanoDeCurso planoDeCurso) {
        this.planoDeCurso = planoDeCurso;
    }

    public static Finder<String,Usuario> find =
        new Finder<String,Usuario>(String.class, Usuario.class);

    public static Usuario autenticar(String email, String senha) {
        return find.where().eq("email", email)
            .eq("senha", senha).findUnique();
    }
}
