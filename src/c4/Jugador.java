package c4;

import java.util.Scanner;

public class Jugador {

	// Atributos

	private String nombre;
	private String fichas;
	private int num_jugadas;
	private boolean human;
	int id;


	Jugador() {

	}
	
	
	Jugador(String fichas, int id, boolean human) {
		super();

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.print("Introduzca nombre del jugador " + id + ": ");
		this.nombre = scan.next();
		this.human = human;
		this.fichas = fichas;
		this.id = id;
	}

	public void tirada() {
		num_jugadas++;
	}

	// Getters & Setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFichas() {
		return fichas;
	}

	public void setFichas(String fichas) {
		this.fichas = fichas;
	}

	public int getNum_jugadas() {
		return num_jugadas;
	}

	public void setNum_jugadas(int num_jugadas) {
		this.num_jugadas = num_jugadas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHuman() {
		return human;
	}

	public void setHuman(boolean human) {
		this.human = human;
	}

}