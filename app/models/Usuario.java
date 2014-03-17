package models;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Usuario extends Model {

    @Id
    public String email;
    public String nome;
    public String senha;
    
    public Usuario(String email, String nome, String senha) {
      this.email = email;
      this.nome = nome;
      this.senha = senha;
    }

    public static Finder<String,Usuario> find = new Finder<String,Usuario>(String.class, Usuario.class);
   
    public static Usuario authenticate(String email, String senha) {
        return find.where().eq("email", email)
            .eq("senha", senha).findUnique();
    }
}