package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class EstudanteTest {

    @Test(expected = IllegalArgumentException.class)
    public void invariantesDoConstrutor1() {
        new Estudante("", "E0", "MyString");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invariantesDoConstrutor2() {
        new Estudante("mystring@mystring.com", "E0", "");
    }

    @Test
    public void invariantesDoConstrutor3() {
        Estudante e0 = new Estudante("mystring@example.com", "E0", "MyString");
        assertNotEquals("MyString", e0.getSenha());
    }

    @Test
    public void toStringOverride() {
        assertEquals("E0 (mystring@example.com)", new Estudante("mystring@example.com", "E0", "MyString").toString());
    }

}
