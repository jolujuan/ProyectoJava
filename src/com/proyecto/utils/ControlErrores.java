package com.proyecto.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.proyecto.clases.Actor;
import com.proyecto.clases.Director;
import com.proyecto.clases.Pelicula;

public class ControlErrores {

	/// VALIDAR MENU PRINCIPAL ///
	public static int validarMenuPrincipal() {
		int n = 0;
		boolean correcto = false;
		Scanner entrada = new Scanner(System.in);

		do {
			if (!entrada.hasNextInt()) {
				System.err.println("Error: No has introducido un numero.");
				entrada.nextLine();
			} else {
				n = entrada.nextInt();
				if (n == 3 || n == 1 || n == 2) {
					correcto = true;
				} else {
					System.err.println("Error: El numero introducido debe 1, 2  o 3.");
					entrada.nextLine();
				}
			}
		} while (!correcto);

		return n;
	}

	/// VALIDAR MENU SECUNDARIO ///
	public static int validarSecundario() {
		int n = 0;
		boolean correcto = false;
		Scanner entrada = new Scanner(System.in);

		do {
			if (!entrada.hasNextInt()) {
				System.err.println("Error: No has introducido un numero.");
				entrada.nextLine();
			} else {
				n = entrada.nextInt();
				if (n == 1 || n == 2 || n == 3 || n == 4 || n == 5 || n == 6 || n == 7 || n == 8 || n == 9) {
					correcto = true;
				} else {
					System.err.println("Error: El numero introducido debe ser 1, 2, 3, 4, 5, 6, 7, 8 o 9.");
					entrada.nextLine();
				}
			}
		} while (!correcto);

		return n;
	}

	/// VALIDAR MENU TERCIARIO ///
	public static int validarTerciario() {
		int n = 0;
		boolean correcto = false;
		Scanner entrada = new Scanner(System.in);

		do {
			if (!entrada.hasNextInt()) {
				System.err.println("Error: No has introducido un numero.");
				entrada.nextLine();
			} else {
				n = entrada.nextInt();
				if (n == 1 || n == 2 || n == 3 || n == 4) {
					correcto = true;
				} else {
					System.err.println("Error: El numero introducido debe ser 1, 2, 3 o 4.");
					entrada.nextLine();
				}
			}
		} while (!correcto);

		return n;
	}

	/// VALIDAR MENU TERCIARIO ///
	public static int validarTernario() {
		int n = 0;
		boolean correcto = false;
		Scanner entrada = new Scanner(System.in);

		do {
			if (!entrada.hasNextInt()) {
				System.err.println("Error: No has introducido un numero.");
				entrada.nextLine();
			} else {
				n = entrada.nextInt();
				if (n == 1 || n == 2 || n == 3 || n == 4 || n == 5) {
					correcto = true;
				} else {
					System.err.println("Error: El numero introducido debe ser 1, 2, 3, 4 o 5.");
					entrada.nextLine();
				}
			}
		} while (!correcto);

		return n;
	}

	// VALIDAR UN INTEGER //
	public static int validarInt() {
		int n = 0;
		boolean correcto = false;
		Scanner entrada = new Scanner(System.in);

		do {
			if (!entrada.hasNextInt()) {
				System.err.println("Error: No has introducido un numero.");
				entrada.nextLine();
			} else {
				n = entrada.nextInt();
				correcto = true;
			}
		} while (!correcto);

		return n;
	}

	// VALIDAR UN STRING //
	public static String validarString() {
		Scanner entrada = new Scanner(System.in);
		String cadena = "";
		boolean verdad = false;

		do {
			cadena = entrada.nextLine().trim().replaceAll("\\s+", " ").replaceAll("\\t+", " ");

			if (cadena.equals("")) {
				System.err.println("Error: El campo no puede estar vacio.");
			} else if (cadena.length() > 20) {
				System.err.println("Error: El dato introducido no puede tener mas de 20 caracteres.");
			} else {
				verdad = true;
			}
		} while (!verdad);

		return cadena;
	}

	public static String validarStringSinNumeros() {
		Scanner entrada = new Scanner(System.in);
		String cadena = "";
		boolean verdad = false;

		do {
			cadena = entrada.nextLine().trim().replaceAll("\\s+", " ").replaceAll("\\t+", " ");

			if (cadena.equals("")) {
				System.err.println("Error: El campo no puede estar vacio.");
			} else if (cadena.length() > 20) {
				System.err.println("Error: El dato introducido no puede tener mas de 20 caracteres.");
			}
			if (!cadena.matches("^[^\\d]+$")) {
				System.err.println("Error: El dato introducido no puede contener numeros.");
			} else {
				verdad = true;
			}
		} while (!verdad);

		return cadena;
	}

	// VALIDAR PASSWORD //
	public static String pedirContraseña() {
		Scanner entrada = new Scanner(System.in);
		String password = "", passwordRep = "";
		boolean verdad = false;

		do {
			System.out.println("Introduce la nueva contraseña:");
			password = entrada.nextLine().trim().replace("\t", " ").replace(" ", "");

			if (password.equals("")) {
				System.err.println("Error: La contraseña no puede estar vacia.");

			} else if (password.length() < 5 || password.length() > 8) {
				System.err.println("Error: La contraseña debe ser minimo de 5 caracteres y maximo de 8.");
			} else {
				System.out.println("Repite la contraseña: ");
				passwordRep = entrada.nextLine().trim().replace("\t", " ").replace(" ", "");
				if (password.equals(passwordRep)) {
					verdad = true;
				} else {
					System.err.println("Error: No ha introducido la misma contraseña. Vuelva a introducirla.");
				}
			}
		} while (!verdad);

		return password;
	}

	// VALIDAR UN CORREO ELECTRONICO
	public static String validarEmail() {
		Scanner entrada = new Scanner(System.in);
		String email = "";
		boolean correcto = false;
		do {

			email = entrada.nextLine();
			if (!Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").matcher(email).find()) {
				System.err.println("Error: Email no valido vuelve a introducirlo:");
			} else {
				correcto = true;
			}
		} while (!correcto);

		return email;
	}

	// VALIDAR FECHA //
	public static String validarFecha() {
		Scanner entrada = new Scanner(System.in);
		String fecha = "";
		boolean correcto = false;

		do {
			fecha = entrada.nextLine();
			if (!Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$").matcher(fecha)
					.find()) {
				System.err.println("Error: Fecha no valida.");
			} else {
				// ESTO ES PARA COMPROBAR QUE CUANDO INTRODUCIMOS LA FECHA, EL AÑO INTRODUCIDO
				// NO SEA MAYOR QUE EL ACTUAL
				try {
					LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					if (date.getYear() > LocalDate.now().getYear()) {
						System.err.println("Error: El año no puede ser mayor que el actual.");
					} else {
						correcto = true;
					}
				} catch (DateTimeParseException e) {
					System.err.println("Error: " + e);
				}
			}
		} while (!correcto);
		return fecha;

	}

	// VALIDAR UN FILE //

	// VALIDAR UN USUARI //
	// ---------------------------------------------------------------------------------------------------------------
	public static String validaUsuario() {
		try {
			File f = new File("src/com/proyecto/utils/passWords.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			System.out.println("Introduce el nombre de usuario: ");
			String usr = ControlErrores.validarString();
			Funciones.devolverNombreUser(usr);

			System.out.println("Introduce la contraseña: ");
			String pwd = ControlErrores.validarString();

			String linia = br.readLine();
			boolean trobat = false;
			String rol = "";
			while ((linia = br.readLine()) != null && !trobat) {
				String[] dades = linia.split(":::");

				if (dades.length == 5) { // asegurarse de que hay suficientes columnas

					if (dades[0].equals(usr)) {
						trobat = true;

						// Començem a encriptar
						try {
							System.out.println(dades[2].getBytes());
							KeySpec spec = new PBEKeySpec(pwd.toCharArray(), Funciones.revertirSalto(dades[2]),
									Integer.parseInt(dades[1]), Integer.parseInt(dades[3]));
							SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
							byte[] hash = factory.generateSecret(spec).getEncoded();
							pwd = Base64.getEncoder().encodeToString(hash);
							System.out.println(dades[4]);
							System.out.println(pwd);
							if (dades[4].equals(pwd)) {
								System.out.println("\nHola " + usr + ", has iniciado sesion " + "\u2714");
							} else {
								trobat = true;
								System.err.println("ERROR. Contraseña errónea para el usuario " + usr);
								validaUsuario();
							}
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvalidKeySpecException e) {
							System.out.println("Error: " + e);
						}

					}
				}
			}
			if (!trobat) {
				System.err.println("ERROR. No se encontró un usuario con el nombre: " + usr);
				validaUsuario();
			}
			br.close();
			return rol;
		} catch (IOException e) {
			System.err.println("Error: " + e);
			return "";
		}
	}
	// VALIDAR LLISTES GENERALS //
	// ---------------------------------------------------------------------------------------------------------------

	// VALIDAR PELICULA //
	public static boolean validaPeliGeneral(String nom, ArrayList<Pelicula> pelis) {
		boolean trobat = false;
		int i = 0;
		while (i < pelis.size() && !trobat) {
			Pelicula peli = pelis.get(i);
			if (peli.getNombrePelicula().toLowerCase().equals(nom.toLowerCase())) {
				System.err.println("ERROR: Esta película ya esta en la lista general.");
				System.out.println("Película: " + peli.toString() + "\n");
				trobat = true;
			}
			i++;
		}
		return trobat;
	}

	// VALIDAR ACTOR //
	public static boolean validaActorGeneral(String nomicognoms, ArrayList<Actor> actors) {
		boolean trobat = false;
		int i = 0;
		while (i < actors.size() && !trobat) {
			Actor actor = actors.get(i);
			if (actor.getNombYApellActor().toLowerCase().equals(nomicognoms.toLowerCase())) {
				System.err.println("ERROR: Este actor ya esta en la lista general.");
				System.out.println("Actor: " + actor.toString() + "\n");
				trobat = true;
			}
			i++;
		}
		return trobat;
	}

	// VALIDAR DIRECTOR //
	public static boolean validaDirectorGeneral(String nomicognoms, ArrayList<Director> directors) {
		boolean trobat = false;
		int i = 0;
		while (i < directors.size() && !trobat) {
			Director director = directors.get(i);
			if (director.getNombYApellDirector().toLowerCase().equals(nomicognoms.toLowerCase())) {
				System.err.println("ERROR: Este director ya esta en la lista general.");
				System.out.println("Director: " + director.toString() + "\n");
				trobat = true;
			}
			i++;
		}
		return trobat;
	}

	// VALIDAR IMAGENES
	// ---------------------------------------------------------------------------------------------------------------

	// con este metodo verificamos si el archivo seleccionado es una imagen
	public static boolean ValidarImagen(String fileName) {
		String extension = validarExtension(fileName);

		if (extension != null) {
			return extension.equals("jpg") || extension.equals("png") || extension.equals("gif")
					|| extension.equals("jpeg");
		}
		return false;
	}

	// con este metodo sacamos la extension del archivo pa comprovar luego si es una
	// imagen
	public static String validarExtension(String fileName) {
		int punto = fileName.lastIndexOf(".");

		if (punto > 0) {
			return fileName.substring(punto + 1);
		}

		return null;
	}
}
