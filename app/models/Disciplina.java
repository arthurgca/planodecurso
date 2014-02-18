package models;

import java.util.List;
import java.util.ArrayList;

public class Disciplina {

	private int id;

	private String nome;

	private int creditos;

	private int periodo;

	private int dificuldade;

	private List<Disciplina> dependencias;

	public Disciplina() {
	}

	public Disciplina(int id, String nome, int creditos, int periodo,
			int dificuldade) {
		this(id, nome, creditos, periodo, dificuldade, new ArrayList<Disciplina>());
	}

	public Disciplina(int id, String nome, int creditos, int periodo,
			int dificuldade, List<Disciplina> dependencias) {
		this.id = id;
		this.nome = nome;
		this.creditos = creditos;
		this.periodo = periodo;
		this.dificuldade = dificuldade;
		this.dependencias = new ArrayList<Disciplina>();

		if (dependencias != null) {
			for (Disciplina dependencia : dependencias) {
				this.dependencias.add(dependencia);
			}
		}
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getCreditos() {
		return creditos;
	}

	public int getPeriodo() {
		return periodo;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public List<Disciplina> getDependencias() {
		return dependencias;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Disciplina)) {
			return false;
		}

		return getId() == ((Disciplina) obj).getId();
	}

	public int hashCode() {
		return 7 * this.getId();
	}

}
