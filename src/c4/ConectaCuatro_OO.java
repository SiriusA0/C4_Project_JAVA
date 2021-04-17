package c4;

import java.util.Scanner;

public class ConectaCuatro_OO {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		boolean jugarOtraVez = false;

		do {

			Conecta4 juego = new Conecta4(8, 8);
			juego.jugar();
			System.out.print("¿Desea jugar otra partida? 'Y' para volver a jugar");
			String respuesta = scan.nextLine();
			if (respuesta.equals("Y")) {
				jugarOtraVez = true;
			} else {
				jugarOtraVez = false;
			}

		} while (jugarOtraVez);

	}

}