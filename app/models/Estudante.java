package models;

import javax.persistence.*;

import play.db.ebean.*;
import com.fasterxml.jackson.annotation.*;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Um estudante que usa o plano de curso.
 */
@Entity
public class Estudante extends Model {

    @Id
    private String email;

    private String nome;

    @JsonIgnore
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    private Plano plano;

    /**
     * Constrói um estudante com o email, nome e senha dados.
     *
     * @param email o email único para o estudante
     * @param nome o nome para o estudante
     * @param senha a senha para o estudante no sistema
     * @throws NullPointerException se algum parâmetro for nulo
     * @throws IllegalArgumentException se algum parâmetro for a string vazia
     */
    public Estudante(String email, String nome, String senha) {
        setEmail(email);
        setNome(nome);
        setHash(senha);
    }

    /**
     * @return o email do estudante
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email o email para o estudante
     * @throws NullPointerException se {@code email == null}
     * @throws IllegalArgumentException se {@code email == ""}
     */
    public void setEmail(String email) {
        Parametro.naoNulo("email", email);
        Parametro.naoVazio("email", email);

        this.email = email;
    }

    /**
     * @return o nome do estudante
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome o nome para o estudante
     * @throws NullPointerException se {@code nome == null}
     * @throws IllegalArgumentException se {@code nome == ""}
     */
    public void setNome(String nome) {
        Parametro.naoNulo("nome", nome);
        Parametro.naoVazio("nome", nome);

        this.nome = nome;
    }

    /**
     * @return a senha do estudante
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha a senha para o estudante
     * @throws NullPointerException se {@code senha == null}
     * @throws IllegalArgumentException se {@code senha == ""}
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return o plano do estudante
     */
    public Plano getPlano() {
        return plano;
    }

    /**
     * @param plano o plano para o estudante
     * @throws NullPointerException se {@code plano == null}
     */
    public void setPlano(Plano plano) {
        Parametro.naoNulo("plano", plano);

        this.plano = plano;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getNome(), getEmail());
    }

    private void setHash(String senha) {
        Parametro.naoNulo("senha", senha);
        Parametro.naoVazio("senha", senha);

        this.senha = BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    private static boolean check(String senha, String hash) {
        return BCrypt.checkpw(senha, hash);
    }

    /**
     * @param email o email do estudante
     * @param senha a senha do estudante
     * @throws NullPointerException se algum parâmetro for nulo
     * @throws IllegalArgumentException se algum parâmetro é a string vazia
     */
    public static Estudante autenticar(String email, String senha) {
        Parametro.naoNulo("email", email);
        Parametro.naoNulo("senha", senha);
        Parametro.naoVazio("email", email);
        Parametro.naoVazio("senha", senha);

        Estudante estudante = Estudante.find.where().eq("email", email).findUnique();

        if(estudante != null && check(senha, estudante.senha)) {
            return estudante;
        } else {
            return null;
        }
    }

    /** Finder para o Ebean */
    public static Finder<String, Estudante> find =
        new Finder<String, Estudante>(String.class, Estudante.class);
}
