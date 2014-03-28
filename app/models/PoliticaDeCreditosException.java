package models;

/**
 * Representa uma violação de uma política de créditos.
 */
public class PoliticaDeCreditosException extends Exception {

    public PoliticaDeCreditosException(String msg) {
        super(msg);
    }

}
