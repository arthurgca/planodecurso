package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class ParametroTest {

    @Test(expected = NullPointerException.class)
    public void naoNulo1() {
        Parametro.naoNulo("argumento", null);
    }

    @Test
    public void naoNulo2() {
        Parametro.naoNulo("argumento", "MyString");
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoVazioString1() {
        Parametro.naoVazio("argumento", "");
    }

    @Test
    public void naoVazioString2() {
        Parametro.naoVazio("argumento", "MyString");
    }

    @Test(expected = IllegalArgumentException.class)
    public void maiorQueZeroInt1() {
        Parametro.maiorQueZero("argumento", -1);
    }

    @Test
    public void maiorQueZeroInt2() {
        Parametro.maiorQueZero("argumento", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maiorQueZeroLong1() {
        Parametro.maiorQueZero("argumento", -1L);
    }

    @Test
    public void maiorQueZeroLong2() {
        Parametro.maiorQueZero("argumento", 1L);
    }

}
