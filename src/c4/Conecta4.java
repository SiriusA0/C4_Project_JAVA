package c4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Conecta4 {

	private Tablero tablero;
	private int num_jugadores;
	private Jugador[] player;

	private String fichas_disponibles[] = { "R", "A", "J", "H", "L", "V" };

	// Lista posiciones prohibidas
	static List<Integer> BannedPositions = new ArrayList<>();

	// Estado del juego
	private boolean tableroLleno = false;
	private boolean hayGanador = false;
	static int turno;

	public Conecta4(int num_filas, int num_cols) {

		System.out.println("Introduzca numero de jugadores: ");
		this.num_jugadores = introducirDato();
		if (this.num_jugadores > 1) {
			System.out.println();

			this.tablero = new Tablero(num_filas, num_cols, " ");

			// Inicializacion de jugadores y asignacion de fichas
			this.player = new Jugador[num_jugadores];
			for (int i = 0; i < num_jugadores; i++) {
				this.player[i] = new Jugador(fichas_disponibles[i], i + 1, true);
			}
		} else {

			if (this.num_jugadores == 0) {

				System.out.println("Función no disponible, encintrará bugs");
				this.tablero = new Tablero(num_filas, num_cols, " ");

				this.player = new Jugador[2];
				this.player[0] = new Jugador("O", 1, false);
				this.player[1] = new Jugador("X", 2, false);

				this.num_jugadores = 2;

			} else {
				// Si se introduce un 1 en la solicitud de numero de jugadores, se inicializa
				// maquina, una clase heredada de jugador
				System.out.println();
				this.tablero = new Tablero(num_filas, num_cols, " ");

				// Inicializacion de jugadores
				this.player = new Jugador[2];
				this.player[0] = new Jugador("O", 1, true);
				this.player[1] = new Jugador("X", 2, false);

				// Una vez incializada la maquina cambiamos el numero de jugadores a 2 para que
				// el turno se cuente correctamente
				this.num_jugadores = 2;

			}

		}
	}

	public void jugar() {
		System.out.println("Inicio del juego");
		turno = 0;

		do {
			Jugador current_player = player[turno % num_jugadores];
			Jugador no_current_player = player[((turno + 1) % num_jugadores)];

			System.out.println("Jugada " + (turno + 1) + ". Turno de " + current_player.getNombre() + "." + " ("
					+ current_player.getFichas() + "): ");

			int user_col = -1;

			// Se comprueba si el jugador es humano, si no, else -> tirada de la maquina

			user_col = arbolDeDecisiones(current_player, no_current_player);

			System.out.println();

			tablero.tirada(current_player.getFichas(), user_col);
			current_player.tirada();

			tablero.print();

			if (tablero.check_victoria(current_player)) {
				System.out.println("El ganador es: " + current_player.getNombre());
				hayGanador = true;
			}

			tableroLleno = !(turno < tablero.MAX_CASILLAS);

		} while (!hayGanador && !tableroLleno);

	}

	private int arbolDeDecisiones(Jugador current_player, Jugador no_current_player) {

		int user_col;
		if (current_player.isHuman()) {

			System.out.print("Introduzca columna " + current_player.getNombre());
			user_col = introducirDato() - 1;// Columna introducida por el jugador

		} else {

			// Comprobacion tres fichas seguidas de la maquina
			user_col = tablero.AIPlay(current_player, no_current_player, 3);

			if (user_col == -1) { // Comprobacion tres fichas seguidas del humano
				user_col = tablero.AIPlay(no_current_player, current_player, 3);
			}
			if (user_col == -1) { // Comprobacion dos fichas seguidas del humano
				user_col = tablero.AIPlay(no_current_player, current_player, 2);
			}

			if (user_col == -1) { // Comprobacion tres fichas seguidas de la maquina
				user_col = tablero.AIPlay(current_player, no_current_player, 2);
			}

			if (user_col == -1) { // Random TODO Posiciones prohibidas en lista

				if (turno < 6) {

					user_col = tablero.RandomPlay(3, tablero.getNum_columnas() - 5);
					System.out.print("Apertura");

				} else {
					System.out.print("Random");
					int randomCol = -1;
					do {
						randomCol = tablero.RandomPlay(0, -1);
					} while (BannedPositions.contains(randomCol));
					user_col = randomCol;

				}
			}
		}
		System.out.println("Posiciones prohibidas -> anterior" + BannedPositions);
		BannedPositions.clear();
		System.out.println("Posiciones prohibidas -> limpia" + BannedPositions);
		System.out.println(user_col);
		return user_col;
	}

	public int introducirDato() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		int entrada = 0;
		boolean isNum = false;
		do {
			try {

				entrada = Integer.parseInt(scan.next());
				isNum = true;
			} catch (NumberFormatException e) {
				System.out.print("Error de entrada, introduzca de nuevo el numero: ");
			}
		} while (!isNum);

		return entrada;

	}

}
