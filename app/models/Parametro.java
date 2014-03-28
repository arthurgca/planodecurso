package models;

/**
 * Helper para validar checagem de parâmetros.
 */
public class Parametro {

    /**
     * @param param nome do parâmetro
     * @param obj objeto para checar
     * @throws NullPointerException se {@code obj == null}
     */
    public static void naoNulo(String param, Object obj) {
        if (obj == null) {
            throw new NullPointerException(param + " == null");
        }
    }

    /**
     * @param param nome do parâmetro
     * @param str string para checar
     * @throws IllegalArgumentException se {@code str == ""}
     */
    public static void naoVazio(String param, String str) {
        Parametro.naoNulo("param", param);

        if (str.isEmpty()) {
            throw new IllegalArgumentException("param == \"\"");
        }
    }

    /**
     * @param param nome do parâmetro
     * @param num número para checar
     * @throws IllegalArgumentException se {@code num < 1}
     */
    public static void maiorQueZero(String param, int num) {
        if (num < 1) {
            throw new IllegalArgumentException(param + " < 1: " + num);
        }
    }

    /**
     * @param param nome do parâmetro
     * @param num número para checar
     * @throws IllegalArgumentException se {@code num < 1}
     */
    public static void maiorQueZero(String param, Long num) {
        if (num < 1) {
            throw new IllegalArgumentException(param + " < 1: " + num);
        }
    }

    private Parametro() { }

}
