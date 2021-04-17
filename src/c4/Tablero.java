package c4;

public class Tablero {

	private int num_filas;
	private int num_columnas;
	// tablero en s� mismo
	private String contenido[][];

	// Posiciones que significan victorial del jugador
	//  int[] posicionesProhibidas = new int[0];

	public final int MAX_CASILLAS;

	// el valor que tiene el hueco vac�o del tablero por defecto ' '
	private String valor_vacio;

	// Constructor

	Tablero(int num_filas, int num_columnas, String valor_vacio) {
		super();
		this.num_filas = num_filas;
		this.num_columnas = num_columnas;
		this.valor_vacio = valor_vacio;
		contenido = new String[num_filas][num_columnas];
		this.MAX_CASILLAS = num_columnas * num_filas;

//		for (int i = 1; i < (num_columnas - 1); i++) {
//
//			posicionesProhibidas[i] = -1;
//
//		}

		ini_tablero();
	}

	// Contador vertical: funci�n que se limita a contar las fichas en cada columna.

	public int contadorVertical(String currentChip, int contadorObjetivo) {

		int contador = 0;

		// Recorre la fila de izquierda a derecha.

		for (int columna = 0; columna < num_columnas; columna++) {
			contador = 0;

			// Recorre la columna de arriba a abajo.

			for (int fila = 0; fila < num_filas; fila++) {

				if (contenido[fila][columna].equals(currentChip)) {
					contador++;

				} else {

					contador = 0;
				}

				if ((contador == contadorObjetivo) && (fila > (contadorObjetivo - 1))
						&& contenido[fila - contadorObjetivo][columna].equals(valor_vacio)) {

					// fila > 2 supone el l�mite para que la comprobaci�n de la posici�n fila - 3
					// (vac�a) no
					// se salga del tablero

					System.out.println("AI vertical " + columna);

					return columna;

				}

			}
		}
		return -1;
	}

	public int contadorHorizontal(String currentChip, int contadorObjetivo) {

		int contador = 0;

		for (int fila = num_filas - 1; fila >= 0; fila--) {

			contador = 0;

			for (int columna = 0; columna < num_columnas; columna++) {

				if (contenido[fila][columna].equals(currentChip)) {
					contador++;
				} else {
					contador = 0;
				}

				if ((contador == contadorObjetivo) && (columna > (contadorObjetivo - 1))
						&& contenido[fila][columna - contadorObjetivo].equals(valor_vacio)) {

					if ((fila == (num_columnas - 1))
							|| !contenido[fila + 1][columna - contadorObjetivo].equals(valor_vacio)) {

						System.out.println("AI horizontal " + (columna - contadorObjetivo));

						return columna - contadorObjetivo;
					}

				}
				if ((contador == contadorObjetivo) && (columna < (num_columnas - 1))
						&& contenido[fila][columna + 1].equals(valor_vacio)) {

					if ((fila == (num_filas - 1)) || !contenido[fila + 1][columna + 1].equals(valor_vacio)) {

						System.out.println("AI horizontal " + (columna + 1));

						return columna + 1;
					}

				}

				if (contadorObjetivo == 3) {

					// Comprobacion de la posicion M _ M M o R _ R R

					if ((columna < (num_columnas - 3)) && ((contenido[fila][columna].equals(currentChip)
							&& contenido[fila][columna + 1].equals(valor_vacio)
							&& contenido[fila][columna + 2].equals(currentChip)
							&& contenido[fila][columna + 3].equals(currentChip)))) {

						// Comprobacion posicion inferior a la posicion de victoria.

						if ((fila == (num_filas - 1)) || !contenido[fila + 1][columna + 1].equals(valor_vacio)) {

							System.out.println("AI horizontal " + columna);

							return columna + 1;
						}

					}

					// Comprobaci�n de la posici�n M M _ M o R R _ R

					if ((columna < (num_columnas - 3)) && ((contenido[fila][columna].equals(currentChip)
							&& contenido[fila][columna + 1].equals(currentChip)
							&& contenido[fila][columna + 2].equals(valor_vacio)
							&& contenido[fila][columna + 3].equals(currentChip)))) {

						// Comprobacion posicion inferior a la posicion de victoria.

						if ((fila == (num_filas - 1)) || !contenido[fila + 1][columna + 2].equals(valor_vacio)) {

							System.out.println("AI horizontal " + columna);

							return columna + 2;
						}

					}
				}

			}
		}
		return -1;

	}

	public int contadorDiagonal(String currentChip, int contadorObjetivo) {

		// Selecci�n de columna

		for (int columna = 0; columna < num_columnas; columna++) {

			// Selecci�n de fila

			for (int fila = 0; fila < num_filas; fila++) {
				int col = columna;
				int fil = fila;
				int contador = 0;

				// Avance por la diagonal

				while (col < num_columnas && fil < num_filas) {

					if (contenido[fil][col].equals(currentChip)) {
						contador++;

					} else {

						contador = 0;
					}

					// Posicion superior izquierda
					if ((contador == contadorObjetivo) && (col > (contadorObjetivo - 1))
							&& (fil > (contadorObjetivo - 1))
							&& contenido[fil - contadorObjetivo][col - contadorObjetivo].equals(valor_vacio)

							&& (!contenido[fil - (contadorObjetivo - 1)][col - contadorObjetivo].equals(valor_vacio))) {

						System.out.println("AI diagonal " + (col - contadorObjetivo));

						return col - contadorObjetivo;

						// Posicion inferior derecha fila == ultima fila
					} else if (((contador == contadorObjetivo) && ((fil + 1) < (num_filas))
							&& ((col + 1) < (num_columnas)) && contenido[fil + 1][col + 1].equals(valor_vacio)))

					{

						System.out.println("AI diagonal " + (col + 1));

						return col + 1;

						// // Posicion inferior derecha, fila != ultima fila, comprueba la inferior
					} else if ((contador == contadorObjetivo) && (col < (num_columnas - (contadorObjetivo - 1)))
							&& (fil < (num_filas - (contadorObjetivo - 1)))
							&& (contenido[fil + 1][col + 1].equals(valor_vacio)
									&& (!contenido[fil + (contadorObjetivo - 1)][col + 1].equals(valor_vacio)))) {

						System.out.println("AI diagonal " + (col + 1));

						return col + 1;

						// Comprobaci�n hueco en currentChip valor_vacio currentChip currentChip

					}

					if (contadorObjetivo == 3) {

						if ((col < (num_columnas - 3)) && (fil < (num_filas - 3))
								&& ((contenido[fil][col].equals(currentChip)
										&& contenido[fil + 1][col + 1].equals(valor_vacio)
										&& !contenido[fil + 2][col + 1].equals(valor_vacio)
										&& contenido[fil + 2][col + 2].equals(currentChip)
										&& contenido[fil + 3][col + 3].equals(currentChip)))) {

							System.out.println("AI diagonal " + (col + 1));

							return col + 1;

							// Comprobaci�n hueco en currentChip currentChip valor_vacio currentChip

						} else if ((col < (num_columnas - 3)) && (fil < (num_filas - 3))
								&& ((contenido[fil][col].equals(currentChip)
										&& contenido[fil + 1][col + 1].equals(currentChip)
										&& contenido[fil + 2][col + 2].equals(valor_vacio)
										&& !contenido[fil + 3][col + 2].equals(valor_vacio)
										&& contenido[fil + 3][col + 3].equals(currentChip)))) {

							System.out.println("AI diagonal " + (col + 2));

							return col + 2;

						}
					}
					col++;
					fil++;

				}

			}
		}

		return -1;
	}

	public int contadorAntiDiagonal(String currentChip, int contadorObjetivo) {

		// Seleccion de columna
		for (int columna = num_columnas - 1; columna >= 0; columna--) {

			// Seleccion de fila
			for (int fila = 0; fila < num_filas; fila++) {
				int col = columna;
				int fil = fila;
				int contador = 0;

				// Avance por la diagonal
				while (col >= 0 && fil < num_filas) {

					if (contenido[fil][col].equals(currentChip)) {
						contador++;

					} else {

						contador = 0;
					}

					// Posicion superior derecha
					if ((contador == contadorObjetivo) && (col < num_columnas - contadorObjetivo)
							&& (fil > (contadorObjetivo - 1))
							&& contenido[fil - contadorObjetivo][col + contadorObjetivo].equals(valor_vacio)
							&& (!contenido[fil - (contadorObjetivo - 1)][col + contadorObjetivo].equals(valor_vacio))) {

						System.out.println("AI antidiagonal " + (col + contadorObjetivo));

						return col + contadorObjetivo;

						// Comprobaci�n l�mite inferior izquierdo.
					} else if (((contador == contadorObjetivo) && ((fil + 1) < (num_filas)) && (col > 0)
							&& contenido[fil + 1][col - 1].equals(valor_vacio))) {

						// Comprobaci�n hueco inferior lleno para no regalar la jugada
						if (fil + 1 == num_filas - 1) {

							System.out.println("AI antidiagonal " + (col - 1));

							return col - 1;

						} else if ((fil + 1 < num_filas - 1)
								&& !contenido[fil + (contadorObjetivo - 1)][col - 1].equals(valor_vacio)) {

							System.out.println("AI antidiagonal " + (col - 1));

							return col - 1;

						}

						// Comprobaci�n hueco en currentChip valor_vacio currentChip currentChip
						// De derecha a izquierda

					}

					if (contadorObjetivo == 3) {

						if ((col > 2) && (fil < (num_filas - 3))
								&& ((contenido[fil][col].equals(currentChip)
										&& contenido[fil + 1][col - 1].equals(valor_vacio)
										&& !contenido[fil + 2][col - 1].equals(valor_vacio)
										&& contenido[fil + 2][col - 2].equals(currentChip)
										&& contenido[fil + 3][col - 3].equals(currentChip)))) {

							System.out.println("AI antidiagonal " + col);

							return col - 1;

							// Comprobaci�n hueco en currentChip currentChip valor_vacio currentChip
							// De derecha a izquierda

						} else if ((col > 2) && (fil < (num_filas - 3))
								&& ((contenido[fil][col].equals(currentChip)
										&& contenido[fil + 1][col - 1].equals(currentChip)
										&& contenido[fil + 2][col - 2].equals(valor_vacio)
										&& !contenido[fil + 3][col - 2].equals(valor_vacio)
										&& contenido[fil + 3][col - 3].equals(currentChip)))) {

							System.out.println("AI antidiagonal " + col);

							return col - 2;

						}
					}

					col--;
					fil++;

				}

			}
		}
		return -1;

	}

	public int AIPlay(String currentChip, int contadorObjetivo) {

		int user_col = -1;

		user_col = contadorVertical(currentChip, contadorObjetivo);

		if (user_col == -1) {

			user_col = contadorHorizontal(currentChip, contadorObjetivo);

		}

		if (user_col == -1) {

			user_col = contadorDiagonal(currentChip, contadorObjetivo);

		}

		if (user_col == -1) {

			user_col = contadorAntiDiagonal(currentChip, contadorObjetivo);

		}

		return user_col;
	}

	public int RandomPlay(int limInferior, int limSuperior) {

		if (limSuperior == -1) {

			limSuperior = (num_filas - 1);
		}

		int user_col = (int) (limInferior + (Math.random() * limSuperior) + 1);

		return user_col;
	}

	private boolean check_diagonal(Jugador player) {
		// Selección de columna
		for (int columna = 0; columna < getNum_columnas(); columna++) {

			// Selección de fila
			for (int fila = 0; fila < getNum_filas(); fila++) {
				int col = columna;
				int fil = fila;
				int cont = 0;

				while (col < getNum_columnas() && fil < getNum_filas()) {
					if (contenido[fil][col].equals(player.getFichas())) {
						cont++;
					} else {
						cont = 0;
					}

					if (cont == 4) {
						return true;
					}

					col++;
					fil++;

				}

			}
		}
		// Antidiagonal
		for (int columna = getNum_columnas() - 1; columna >= 0; columna--) {

			// Selección de fila
			for (int fila = 0; fila < getNum_filas(); fila++) {
				int col = columna;
				int fil = fila;
				int cont = 0;

				while (col >= 0 && fil < getNum_filas()) {
					if (contenido[fil][col].equals(player.getFichas())) {
						cont++;
					} else {
						cont = 0;
					}

					if (cont == 4) {
						return true;
					}

					col--;
					fil++;

				}

			}
		}
		return false;
	}

	private boolean check_vertical(Jugador player) {
		int cont = 0;

		for (int columna = 0; columna < getNum_columnas(); columna++) {
			cont = 0;

			for (int fila = 0; fila < getNum_filas(); fila++) {

				if (contenido[fila][columna].equals(player.getFichas())) {
					cont++;
				} else {
					cont = 0;
				}

				if (cont == 4) {
					return true;
				}

			}

		}
		return false;

	}

	private boolean check_horizontal(Jugador player) {
		int cont = 0;

		for (int fila = getNum_filas() - 1; fila >= 0; fila--) {
			cont = 0;

			for (int columna = 0; columna < getNum_columnas(); columna++) {
				if (contenido[fila][columna].equals(player.getFichas())) {
					cont++;
				} else {
					cont = 0;
				}

				if (cont == 4) {
					return true;
				}
			}
		}
		return false;
	}

	// Condici�n de victoria
	public boolean check_victoria(Jugador player) {
		return check_diagonal(player) || check_horizontal(player) || check_vertical(player);

	}

	// Jugada
	public void tirada(String fichas, int user_col) {

		if (user_col >= getNum_columnas() || user_col < 0) {
			System.out.print("Fuera de rango");
			System.out.println();
			return;
		}

		if (!contenido[0][user_col].equals(valor_vacio)) {
			System.out.println("Columna llena");
			System.out.println();
			return;
		}

		for (int i = num_filas - 1; i >= 0; i--) {
			if (contenido[i][user_col].equals(valor_vacio)) {
				contenido[i][user_col] = fichas;
				Conecta4.turno++;
				return;

			}
		}

	}

	// Inicialización del tablero
	public void ini_tablero() {
		for (int i = 0; i < getNum_filas(); i++) {
			for (int j = 0; j < getNum_columnas(); j++) {
				contenido[i][j] = valor_vacio;
			}
		}
	}

	// Dibujado del tablero
	public void print() {

		for (int i = 0; i < getNum_filas(); i++) {

			for (int j = 0; j < getNum_columnas(); j++) {
				if (j == 0)
					System.out.print((i + 1) + " | ");

				System.out.print(contenido[i][j] + " ");

				if (j == (getNum_columnas() - 1))
					System.out.print(" | " + (i + 1));

			}

			System.out.println();

			if (i == getNum_filas() - 1) {
				System.out.print("   -");
				for (int k = 0; k < getNum_columnas(); k++) {
					System.out.print("--");
				}
				System.out.println();

				System.out.print("    ");
				for (int k = 0; k < getNum_columnas(); k++) {
					System.out.print("" + (k + 1) + " ");
				}
				System.out.println();
			}
		}
	}

	// Getters & Setters
	public int getNum_filas() {
		return num_filas;
	}

	public void setNum_filas(int num_filas) {
		this.num_filas = num_filas;
	}

	public int getNum_columnas() {
		return num_columnas;
	}

	public void setNum_columnas(int columnas) {
		this.num_columnas = columnas;
	}

	public String getValor_vacio() {
		return valor_vacio;
	}

	public void setValor_vacio(String valor_vacio) {
		this.valor_vacio = valor_vacio;
	}

	public String[][] getContenido() {
		return contenido;
	}

	public void setContenido(String[][] contenido) {
		this.contenido = contenido;
	}

}