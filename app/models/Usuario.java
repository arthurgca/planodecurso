package models;

import javax.persistence.*;
import play.db.ebean.*;

import com.fasterxml.jackson.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

@Entity
public class Usuario extends Model {

    @Id
    public String email;

    public String nome;

    @JsonIgnore
    public String senha;

    @OneToOne
    public Plano plano;

    public Usuario(String email, String nome, String senha) {
      this.email = email;
      this.nome = nome;
      this.senha = BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public static Finder<String,Usuario> find =
        new Finder<String,Usuario>(String.class, Usuario.class);

    public static Usuario autenticar(String email, String senha) {
        Usuario usuario = Usuario.find.where().eq("email", email).findUnique();
        if(usuario != null && BCrypt.checkpw(senha, usuario.senha)) {
            return usuario;
        } else {
            return null;
        }
    }
}
