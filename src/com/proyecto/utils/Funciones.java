package com.proyecto.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.proyecto.Ismael.MostrarNombreIsma;
import com.proyecto.clases.Actor;
import com.proyecto.clases.Director;
import com.proyecto.clases.Pelicula;
import com.proyecto.edu.MostrarNombreEdu;
import com.proyecto.javier.MostrarNombreJavier;
import com.proyecto.joseluis.MostrarNombreJoselu;
import com.proyecto.maikol.MostrarNombreMaikol;
import com.proyecto.users.Cliente;
import com.proyecto.users.Rol;
import com.proyecto.users.User;

public class Funciones {

	// MOSTRAR CREADORES DEL CODIGO //
	public static String mostrarColaboradores() {
		MostrarNombreEdu colaborador1 = new MostrarNombreEdu();
		MostrarNombreIsma colaborador2 = new MostrarNombreIsma();
		MostrarNombreJavier colaborador3 = new MostrarNombreJavier();
		MostrarNombreJoselu colaborador4 = new MostrarNombreJoselu();
		MostrarNombreMaikol colaborador5 = new MostrarNombreMaikol();
		return "<html><div style='text-align:center;'>-------Creado por-------<br>" + colaborador1 + "<br>"
				+ colaborador2 + "<br>" + colaborador3 + "<br>" + colaborador4 + "<br>" + colaborador5
				+ "</div></html>";

	}

	static Scanner leer = new Scanner(System.in);
	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

	// VARIABLES GLOBALES STATICAS //
	private static final int LONGITUD_SALTO = 16;
	private static final int FORTALEZA = 65536;
	private static final int LONGITUD_HASH = 64 * 8;

	// Varible nombre usuario
	public static String nomUser = "";
	public static String userCarpeta = "";

	// Variable para crear listas personales
	public static String nomUserFinal;

	// Listas generales
	public static ArrayList<Pelicula> PelisGeneral = new ArrayList<Pelicula>();
	public static ArrayList<Director> DirectorGeneral = new ArrayList<Director>();
	public static ArrayList<Actor> ActorGeneral = new ArrayList<Actor>();

	// Listas personales
	public static ArrayList<Pelicula> PelisPersonal = new ArrayList<Pelicula>();
	public static ArrayList<Director> DirectorPersonal = new ArrayList<Director>();
	public static ArrayList<Actor> ActorPersonal = new ArrayList<Actor>();

	// REGISTRO USUARIO //
	public static boolean registrarUsuario() {
		final String cancelar = "-1";

		System.out.println("Introduce tu nombre:");
		String usuario = ControlErrores.validarString();
		if (usuario.equals(cancelar)) {
			return true; // Se ha cancelado la operación
		}

		System.out.println("Introduce los apellidos:");
		String apellidos = ControlErrores.validarString();
		if (apellidos.equals(cancelar)) {
			return true; // Se ha cancelado la operación
		}

		System.out.println("Introduce tu email:");
		String email = ControlErrores.validarEmail();
		if (email.equals(cancelar)) {
			return true; // Se ha cancelado la operación
		}

		System.out.println("Introduce tu poblacion:");
		String poblacion = ControlErrores.validarString();
		if (poblacion.equals(cancelar)) {
			return true; // Se ha cancelado la operación
		}

		System.out.println("Introduce tu fecha de nacimiento (dd/mm/aaaa):");
		String fecha = ControlErrores.validarFecha();
		if (fecha.equals(cancelar)) {
			return true; // Se ha cancelado la operación
		}

		// pedir contraseña
		String contraseña = ControlErrores.pedirContraseña();
		if (contraseña.equals(cancelar)) {
			return true; // Se ha cancelado la operación
		}

		// registro finish

		System.out.println("Registro completado");

		// Array List como null, para guardar solo información de los usuarios
		// Obtenemos el ID de usuario con una funcion en la clase
		Cliente N1 = new Cliente(User.getId(), usuario, apellidos, contraseña, email, poblacion, Rol.USUARIO, fecha,
				null, null, null);

		// Creamos el usuario con la funcion
		nomUser = obtenerNomUser(User.getId(), email);

		// Pasamos los parametros del objeto a la funcíon guardar usuarios
		guardarUsuario(nomUser, User.getId(), usuario, apellidos, email, poblacion, Rol.USUARIO, fecha);

		// Pasamos el parametro usuario para crear carpeta
		crearCarpeta(nomUser);
		encriptarPassword(nomUser, contraseña);
		return false;
	}

	// ENCRIPTAR CONTRASEÑA //
	public static void encriptarPassword(String nombreUsuario, String passWord) {
		byte[] salto = null;

		try {
			File archivo = new File("src/com/proyecto/utils/passWords.txt");
			FileWriter escribir = new FileWriter(archivo, true);

			try {
				SecureRandom random = new SecureRandom();
				salto = new byte[LONGITUD_SALTO];
				random.nextBytes(salto);

				KeySpec spec = new PBEKeySpec(passWord.toCharArray(), salto, FORTALEZA, LONGITUD_HASH);
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

				byte[] hash = factory.generateSecret(spec).getEncoded();
				passWord = Base64.getEncoder().encodeToString(hash);
				// Utilizamos un metodo para convertir los datos byte a String
				escribir.write(nombreUsuario + ":::" + FORTALEZA + ":::" + conversionSalto(salto) + ":::"
						+ LONGITUD_HASH + ":::" + passWord + "\n");

			} catch (NoSuchAlgorithmException e) {
				System.out.println("Error: " + e);
			} catch (InvalidKeySpecException e) {
				System.out.println("Error: " + e);
			}
			escribir.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	// GENERAR SALTO //
	public static String conversionSalto(byte[] salto) {
		String saltosTexto = "";
		for (int i = 0; i < salto.length; i++) {
			// PASAMOS LA CONVERSION DE BYTES A CADENA
			saltosTexto += String.format("%02x", salto[i]);
		}
		return saltosTexto;
	}

	public static byte[] revertirSalto(String salto) {
		byte[] saltoBytes = new byte[salto.length() / 2];// Cada dos caracters de la String representen un byte de
															// l'array
		for (int i = 0; i < saltoBytes.length; i++) {
			int rango = i * 2;
			String pareja = salto.substring(rango, rango + 2);
			saltoBytes[i] = (byte) Integer.parseInt(pareja, 16);
		}
		return saltoBytes;
	}

	// OBTENER NOMBRE USUARIO //
	public static String obtenerNomUser(int id, String email) {
		int posFinal = email.lastIndexOf("@");
		email = email.substring(0, posFinal);
		return nomUser = "" + id + email;
	}

	// CREAR CARPETA USUARIO Y LISTAS //
	public static String crearCarpeta(String nomUser) {

		try {
			File NuevaCarpeta = new File("src/com/proyecto/usuariosCarpetas/" + nomUser);
			boolean creado = NuevaCarpeta.mkdir();
			if (creado == true) {
				File listACtor = new File("src/com/proyecto/usuariosCarpetas/" + nomUser + "/actor.llista");
				File listDirector = new File("src/com/proyecto/usuariosCarpetas/" + nomUser + "/director.llista");
				File listPelicula = new File("src/com/proyecto/usuariosCarpetas/" + nomUser + "/pelicula.llista");

				// ESTA ES LA RUTA QUE LE PASAREMOS A LA FUNCION DE CREAR LA IMAGEN POR DEFECTO
				File rutaCarpetaUsuario = new File("src/com/proyecto/usuariosCarpetas/" + nomUser);
				crearImagenPorDefecto(rutaCarpetaUsuario);

				listACtor.createNewFile();
				listDirector.createNewFile();
				listPelicula.createNewFile();

				// System.out.println("El usuario se ha creado correctamente");
			} else {
				System.err.println("No se ha podido crear el usuario (quizas el usuario ya existe)");
				System.out.println("Vuelve a registrar tu usuario: ");
				Funciones.registrarUsuario();
			}
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
		return nomUser;
	}

	/// CREAR IMAGEN POR DEFECTO AL REGISTRAR UN USUARIO ///
	public static void crearImagenPorDefecto(File ruta) {
		try {
			// ABRIR EL ARCHIVO DONDE SE ENCUENTRA LA IMAGEN
			FileInputStream rutaOrigen = new FileInputStream("src/com/proyecto/imagenes/porDefecto.png");
			// ABRIR PARA ESCRIBIR EL ARCHIVO DE IMAGEN EN LA CARPETA DE USUARIO
			FileOutputStream rutaDestino = new FileOutputStream(ruta + "/" + "porDefecto.png");

			// CREAMOS UN BUFFER DE BYTES PARA ALMACENAR TEMPORALMENTE LOS DATOS LEIDOS
			byte[] buffer = new byte[1024];

			// LEER DATOS DEL FILEINPUTSTREAM Y ESCRIBIRLOS EN EL FILEOUTPUSTREAM HASTA QUE
			// NO HAYA MAS DATOS QUE LEER
			int lenght;
			while ((lenght = rutaOrigen.read(buffer)) > 0) {
				rutaDestino.write(buffer, 0, lenght);
			}

			// CERRAMOS
			rutaOrigen.close();
			rutaDestino.close();
		} catch (Exception e) {
			System.out.println("Error imagen: " + e);
		}
	}

	// GUARDAR USUARIOS EN FICHERO TXT //
	// ---------------------------------------------------------------------------------------------------------------
	public static void guardarUsuario(String nomUser, int ID, String nombre, String apellidos, String email,
			String poblacion, Rol rol, String fecha) {
		try {
			File file = new File("src/com/proyecto/utils/usersGuardados.txt");
			PrintWriter escriureUser = new PrintWriter(new FileWriter(file, true));

			String nomImage = comprobarNombreImagen();
			String contraseña = "x";
			// Escribir los datos del usuario en un formato fijo
			String datos = String.format("%-17s|%03d|%-18s|%-18s|%-18s|%-30s|%-10s|%-13s|%-12s|%-14s", nomUser, ID,
					nomImage, nombre, apellidos, email, contraseña, poblacion, rol, fecha);

			// Comprobar si el archivo está vacío para escribir el encabezado
			if (file.length() == 0) {
				escriureUser.println(
						"#USUARIO         |ID | IMAGEN           | Nombre           | Apellidos        | Email                        |Contraseña| Población   | Rol        | Fecha        ");
				escriureUser.println(
						"#----------------+---+------------------+------------------+------------------+------------------------------+----------+-------------+------------+--------------");
			}

			// Escribir los datos del usuario en el archivo
			escriureUser.println(datos);
			escriureUser.close();

			System.out.println("\nUsuario guardado correctamente" + " \u2714");
			System.out.println("Tu nombre de usuario es: " + nomUser + " \u2714");

		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
	}

	// GUARDAR EL USUARIO PARA LISTAS PERSONALES //
	public static String devolverNombreUser(String usr) {
		return nomUserFinal = usr;
	}

	// PEDIR Y GUARDAR DATOS LISTAS GENERALES //
	// ---------------------------------------------------------------------------------------------------------------

	// PEDIR DATOS LISTA PELICULA GENERAL
	public static void pedirListaGeneralPelicula() {
		String pelicula;
		do {
			System.out.println("Introduce el nombre de la pelicula:");
			pelicula = ControlErrores.validarString();
		} while (ControlErrores.validaPeliGeneral(pelicula, PelisGeneral));

		System.out.println("Introduce la duración:");
		int duracio = ControlErrores.validarInt();

		System.out.println("Introduce la fecha de emision (dd/mm/aaaa):");
		String fechaEmisio = ControlErrores.validarFecha();

		System.out.println("Introduce el genero:");
		String genero = ControlErrores.validarString();

		registrarListaGeneralPelicula(pelicula, duracio, fechaEmisio, genero);
		System.out.println("Se ha guardado correctamente " + "\u2714");
	}

	// GUARDAR DATOS PELICULA GENERAL //
	public static void registrarListaGeneralPelicula(String pelicula, int duracio, String fechaEmisio, String genero) {

		// Crear la nueva Pelicula
		Pelicula peliculasCreadas = new Pelicula(pelicula, duracio, fechaEmisio, genero);
		PelisGeneral.add(peliculasCreadas);

		// serialització
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			// obrim el fitxer per escriure, sense afegir
			// només tindrem un ArrayList d'objectes

			fout = new FileOutputStream("src/com/proyecto/listasPeliculas/peliculas.llista", false);
			oos = new ObjectOutputStream(fout);
			// escrivim ArrayList sencer en el fitxer (1 sol objecte)
			oos.writeObject(PelisGeneral);
			oos.flush();
			oos.close();
			System.out.println("La pelicula se ha registrado correctamente");

		} catch (Exception ex) {
			System.err.println("Error en registrar general.pelicula.llista " + ex);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// PEDIR DATOS LISTA ACTOR GENERAL //
	// ---------------------------------------------------------------------------------------------------------------
	public static void pedirListaGeneralActor() {
		String nom;
		String apellidos;
		do {
			System.out.println("Introduce el nombre del actor:");
			nom = ControlErrores.validarString();

			System.out.println("Introduce los apellidos del actor:");
			apellidos = ControlErrores.validarString();
		} while (ControlErrores.validaActorGeneral(nom + " " + apellidos, ActorGeneral));

		System.out.println("Introduce la edad del actor:");
		int edad = ControlErrores.validarInt();

		System.out.println("Introduce la nacionalidad del actor:");
		String nacionalidad = ControlErrores.validarStringSinNumeros();

		registrarListaGeneralActor(nom, apellidos, edad, nacionalidad);
		System.out.println("Se ha guardado correctamente " + "\u2714");
	}

	// GUARDAR DATOS LISTA ACTOR GENERAL //
	public static void registrarListaGeneralActor(String nom, String apellidos, int edad, String nacionalidad) {

		Actor actoresCreados = new Actor(nom, apellidos, edad, nacionalidad);
		ActorGeneral.add(actoresCreados);

		// serialització
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			// obrim el fitxer per escriure, sense afegir
			// només tindrem un ArrayList d'objectes

			fout = new FileOutputStream("src/com/proyecto/listasPeliculas/actores.llista", false);
			oos = new ObjectOutputStream(fout);
			// escrivim ArrayList sencer en el fitxer (1 sol objecte)
			oos.writeObject(ActorGeneral);
			oos.flush();
			oos.close();
			System.out.println("El actor/a se ha registrado correctamente");

		} catch (Exception ex) {
			System.err.println("Error en registrar general.actor.llista " + ex);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// PEDIR DATOS LISTA DIRECTOR GENERAL //
	// ---------------------------------------------------------------------------------------------------------------
	public static void pedirListaGeneralDirector() {
		String nom;
		String apellidos;

		do {
			System.out.println("Introduce el nombre del director:");
			nom = ControlErrores.validarString();

			System.out.println("Introduce los apellidos del director:");
			apellidos = ControlErrores.validarString();
		} while (ControlErrores.validaDirectorGeneral(nom + " " + apellidos, DirectorGeneral));

		System.out.println("Introduce la edad del director:");
		int edad = ControlErrores.validarInt();

		System.out.println("Introduce los goyas del director:");
		int goyas = ControlErrores.validarInt();

		registrarListaGeneralDirector(nom, apellidos, edad, goyas);

		System.out.println("Se ha guardado correctamente " + "\u2714");
	}

	// GUARDAR DATOS LISTA DIRECTOR GENERAL //
	public static void registrarListaGeneralDirector(String nom, String apellidos, int edad, int goyas) {
		Director directoresCreados = new Director(nom, apellidos, edad, goyas);
		DirectorGeneral.add(directoresCreados);

		// serialització
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			// obrim el fitxer per escriure, sense afegir
			// només tindrem un ArrayList d'objectes

			fout = new FileOutputStream("src/com/proyecto/listasPeliculas/directores.llista", false);
			oos = new ObjectOutputStream(fout);
			// escrivim ArrayList sencer en el fitxer (1 sol objecte)
			oos.writeObject(DirectorGeneral);
			oos.flush();
			oos.close();
			System.out.println("El director/a se ha registrado correctamente");

		} catch (Exception ex) {
			System.err.println("Error: " + ex);
			ex.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception ex) {
					System.err.println("Error: " + ex);
					ex.printStackTrace();
				}
			}
		}
	}

	// BORRAR DATOS LISTAS GENERALES
	// ---------------------------------------------------------------------------------------------------------------

	public static void borrarListaGeneral(int opcion) {
		switch (opcion) {
		case 1:
			borrarListaGeneralPelicula("src/com/proyecto/listasPeliculas/peliculas.llista",
					"La lista general de Peliculas es:\n", PelisGeneral);
			break;
		case 2:
			borrarListaGeneralActor("src/com/proyecto/listasPeliculas/actores.llista",
					"La lista general de Actores es:\n", ActorGeneral);
			break;
		case 3:
			borrarListaGeneralDirector("src/com/proyecto/listasPeliculas/directores.llista",
					"La lista general de Directores es:\n", DirectorGeneral);
			break;
		default:
			System.out.println("Opcion no valida");
			break;
		}
	}

	// BORRAR DATOS LISTAS GENERALES- pelicula
	private static void borrarListaGeneralPelicula(String archivo, String mensaje, ArrayList<Pelicula> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Pelicula>) reader.readObject();
					System.out.println(mensaje);

					for (Pelicula item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Pelicula item : listaArray) {
						int id = ((Pelicula) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}

					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a borrar( pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado el borrado de la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {
							for (Pelicula item : listaArray) {
								if (((Pelicula) item).getId() == idUser) {
									listaArray.remove(item);
									System.out.println("Se ha borrado correctamente");
									encertat = true;
									break;
								}
							}
						}
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// BORRAR DATOS LISTAS GENERALES- director
	private static void borrarListaGeneralDirector(String archivo, String mensaje, ArrayList<Director> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Director>) reader.readObject();
					System.out.println(mensaje);

					for (Object item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Director item : listaArray) {
						int id = ((Director) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}

					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a borrar( pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado el borrado de la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {
							for (Director item : listaArray) {
								if (((Director) item).getId() == idUser) {
									listaArray.remove(item);
									System.out.println("Se ha borrado correctamente");
									encertat = true;
									break;
								}
							}
						}
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// BORRAR DATOS LISTAS GENERALES- actor
	private static void borrarListaGeneralActor(String archivo, String mensaje, ArrayList<Actor> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Actor>) reader.readObject();
					System.out.println(mensaje);

					for (Actor item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Actor item : listaArray) {
						int id = ((Actor) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}

					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a borrar( pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado el borrado de la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {
							for (Actor item : listaArray) {
								if (((Actor) item).getId() == idUser) {
									listaArray.remove(item);
									System.out.println("Se ha borrado correctamente");
									encertat = true;
									break;
								}
							}
						}
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// MODIFICAR DATOS LISTAS GENERALES
	// ---------------------------------------------------------------------------------------------------------------

	public static void modificarListaGeneral(int opcion) {
		switch (opcion) {
		case 1:
			modificarListaGeneralPelicula("src/com/proyecto/listasPeliculas/peliculas.llista",
					"La lista general de Peliculas es:\n", PelisGeneral);
			break;
		case 2:
			modificarListaGeneralActor("src/com/proyecto/listasPeliculas/actores.llista",
					"La lista general de Actores es:\n", ActorGeneral);
			break;
		case 3:
			modificarListaGeneralDirector("src/com/proyecto/listasPeliculas/directores.llista",
					"La lista general de Directores es:\n", DirectorGeneral);
			break;
		default:
			System.out.println("Opcion no valida");
			break;
		}
	}

	// MODIFICAR DATOS LISTAS GENERALES- Pelicula
	private static void modificarListaGeneralPelicula(String archivo, String mensaje, ArrayList<Pelicula> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Pelicula>) reader.readObject();
					System.out.println(mensaje);

					for (Pelicula item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Pelicula item : listaArray) {
						int id = ((Pelicula) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}
					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a modificar (pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado modificar la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {

							for (Pelicula item : listaArray) {

								if (((Pelicula) item).getId() == idUser) {

									String[] modificacion = item.modificarDatosPelicula();
									switch (modificacion[1]) {
									case "pelicula": {
										for (Pelicula item2 : listaArray) {

											if (((Pelicula) item2).getNombrePelicula().equals(modificacion[2])) {
												System.out.println("Nombre pelicula " + item2.getNombrePelicula());
												System.err.println("\nYa existe la pelicula, operación cancelada");
												modificarListaGeneral(1);
											} else {

												if (((Pelicula) item2).getId() == idUser) {
													item2.setNombrePelicula(modificacion[2]);
													item2.mostrarDatospelicula();
												}
											}
										}
										break;
									}
									case "fechaEmisio": {
										for (Pelicula item2 : listaArray) {
											if (((Pelicula) item2).getId() == idUser) {
												item2.setNombrePelicula(modificacion[2]);
												item2.mostrarDatospelicula();
											}
										}
										break;
									}
									case "genero": {
										for (Pelicula item2 : listaArray) {
											if (((Pelicula) item2).getId() == idUser) {
												item2.setGenero(modificacion[2]);
												item2.mostrarDatospelicula();
											}
										}
										break;
									}
									case "duracion": {
										for (Pelicula item2 : listaArray) {
											if (((Pelicula) item2).getId() == idUser) {
												item2.setDuracion(Integer.valueOf(modificacion[2]));
												item2.mostrarDatospelicula();
											}
										}
										break;
									}
									default:
										throw new IllegalArgumentException("Unexpected value: " + modificacion);
									}
									System.out.println("\nSe ha Modificado correctamente");
									break;
								}
							}
						}
						encertat = true;
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// MODIFICAR DATOS LISTAS GENERALES- Actor
	private static void modificarListaGeneralActor(String archivo, String mensaje, ArrayList<Actor> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Actor>) reader.readObject();
					System.out.println(mensaje);

					for (Actor item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Actor item : listaArray) {
						int id = ((Actor) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}
					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a modificar (pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado modificar la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {

							for (Actor item : listaArray) {

								if (((Actor) item).getId() == idUser) {

									String[] modificacion = item.modificarDatosActor();
									switch (modificacion[1]) {
									case "nombre": {
										for (Actor item2 : listaArray) {

											if (((Actor) item2).getNombreActor().equals(modificacion[2])) {
												System.out.println("\nNombre actor " + item2.getNombreActor());
												System.err.println("Ya existe el actor, operación cancelada");
												modificarListaGeneral(2);
											} else {

												if (((Actor) item2).getId() == idUser) {
													item2.setNombreActor(modificacion[2]);
													item2.mostrarDatosActor();
												}
											}
										}
										break;
									}
									case "apellidos": {
										for (Actor item2 : listaArray) {
											if (((Actor) item2).getId() == idUser) {
												item2.setApellidoActor(modificacion[2]);
												item2.mostrarDatosActor();
											}
										}
										break;
									}
									case "edad": {
										for (Actor item2 : listaArray) {
											if (((Actor) item2).getId() == idUser) {
												item2.setEdadActor(Integer.valueOf(modificacion[2]));
												item2.mostrarDatosActor();
											}
										}
										break;
									}
									case "nacionalidad": {
										for (Actor item2 : listaArray) {
											if (((Actor) item2).getId() == idUser) {
												item2.setNacionalidadActor(modificacion[2]);
												item2.mostrarDatosActor();
											}
										}
										break;
									}
									default:
										throw new IllegalArgumentException("Unexpected value: " + modificacion);
									}
									System.out.println("\nSe ha Modificado correctamente");
									break;
								}
							}
						}
						encertat = true;
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// MODIFICAR DATOS LISTAS GENERALES- Director
	private static void modificarListaGeneralDirector(String archivo, String mensaje, ArrayList<Director> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Director>) reader.readObject();
					System.out.println(mensaje);

					for (Director item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Director item : listaArray) {
						int id = ((Director) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}
					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a modificar (pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado modificar la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {

							for (Director item : listaArray) {

								if (((Director) item).getId() == idUser) {

									String[] modificacion = item.modificarDatosDirector();
									switch (modificacion[1]) {
									case "nombre": {
										for (Director item2 : listaArray) {

											if (((Director) item2).getNombreDirector().equals(modificacion[2])) {
												System.out.println("\nNombre director " + item2.getNombreDirector());
												System.err.println("Ya existe el director, operación cancelada");
												modificarListaGeneral(2);
											} else {

												if (((Director) item2).getId() == idUser) {
													item2.setNombreDirector(modificacion[2]);
													item2.mostrarDatosDirector();
												}
											}
										}
										break;
									}
									case "apellidos": {
										for (Director item2 : listaArray) {
											if (((Director) item2).getId() == idUser) {
												item2.setApellidoDirector(modificacion[2]);
												item2.mostrarDatosDirector();
											}
										}
										break;
									}
									case "edad": {
										for (Director item2 : listaArray) {
											if (((Director) item2).getId() == idUser) {
												item2.setEdadDirector(Integer.valueOf(modificacion[2]));
												item2.mostrarDatosDirector();
											}
										}
										break;
									}
									case "goyas": {
										for (Director item2 : listaArray) {
											if (((Director) item2).getId() == idUser) {
												item2.setGoyas(Integer.valueOf(modificacion[2]));
												item2.mostrarDatosDirector();
											}
										}
										break;
									}
									default:
										throw new IllegalArgumentException("Unexpected value: " + modificacion);
									}
									System.out.println("\nSe ha Modificado correctamente");
									break;
								}
							}
						}
						encertat = true;
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// MODIFICAR DATOS LISTAS PERSONALES
	// ---------------------------------------------------------------------------------------------------------------

	public static void modificarListaPersonal(int opcion) {
		switch (opcion) {
		case 1:
			modificarListaPersonalPelicula("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/pelicula.llista",
					"La lista personal de Peliculas es:\n", PelisPersonal);
			break;
		case 2:
			modificarListaPersonalActor("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista",
					"La lista personal de Actores es:\n", ActorPersonal);
			break;
		case 3:
			modificarListaPersonalDirector("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/director.llista",
					"La lista personal de Directores es:\n", DirectorPersonal);
			break;
		default:
			System.out.println("Opcion no valida");
			break;
		}
	}

	// MODIFICAR DATOS LISTAS PERSONALES- Pelicula
	private static void modificarListaPersonalPelicula(String archivo, String mensaje, ArrayList<Pelicula> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Pelicula>) reader.readObject();
					System.out.println(mensaje);

					for (Pelicula item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Pelicula item : listaArray) {
						int id = ((Pelicula) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}
					boolean encertat = false;
					int idUser = 0;

					do {
						System.out.println("Seleccione id del elemento a modificar (pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado modificar la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {

							for (Pelicula item : listaArray) {

								if (((Pelicula) item).getId() == idUser) {

									// Comprobar que la introducido el usuario
									if (((Pelicula) item).getNomUser().equals(nomUserFinal)) {

										String[] modificacion = item.modificarDatosPelicula();
										switch (modificacion[1]) {
										case "pelicula": {
											for (Pelicula item2 : listaArray) {

												if (((Pelicula) item2).getNombrePelicula().equals(modificacion[2])) {
													System.out.println("Nombre pelicula " + item2.getNombrePelicula());
													System.err.println("\nYa existe la pelicula, operación cancelada");
													modificarListaGeneral(1);
												} else {

													if (((Pelicula) item2).getId() == idUser) {
														item2.setNombrePelicula(modificacion[2]);
														item2.mostrarDatospelicula();
													}
												}
											}
											break;
										}
										case "fechaEmisio": {
											for (Pelicula item2 : listaArray) {
												if (((Pelicula) item2).getId() == idUser) {
													item2.setNombrePelicula(modificacion[2]);
													item2.mostrarDatospelicula();
												}
											}
											break;
										}
										case "genero": {
											for (Pelicula item2 : listaArray) {
												if (((Pelicula) item2).getId() == idUser) {
													item2.setGenero(modificacion[2]);
													item2.mostrarDatospelicula();
												}
											}
											break;
										}
										case "duracion": {
											for (Pelicula item2 : listaArray) {
												if (((Pelicula) item2).getId() == idUser) {
													item2.setDuracion(Integer.valueOf(modificacion[2]));
													item2.mostrarDatospelicula();
												}
											}
											break;
										}
										default:
											throw new IllegalArgumentException("Unexpected value: " + modificacion);
										}
										System.out.println("\nSe ha Modificado correctamente");
										break;

									} else {
										// Usuario puede modificar lo que el ha introducido
										System.err
												.println("No puedes modificar una lista introducida por otra persona");
									}
								}
							}
						}
						encertat = true;
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// MODIFICAR LISTA PERSONAL-actor
	private static void modificarListaPersonalActor(String archivo, String mensaje, ArrayList<Actor> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Actor>) reader.readObject();
					System.out.println(mensaje);

					for (Actor item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Actor item : listaArray) {
						int id = ((Actor) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}
					boolean encertat = false;
					int idUser = 0;

					do {
						System.out.println("Seleccione id del actor a modificar (pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado modificar la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {

							for (Actor item : listaArray) {

								if (((Actor) item).getId() == idUser) {

									// Comprobar que la introducido el usuario
									if (((Actor) item).getNomUser().equals(nomUserFinal)) {

										String[] modificacion = item.modificarDatosActor();
										switch (modificacion[1]) {
										case "nombre": {
											for (Actor item2 : listaArray) {

												if (((Actor) item2).getNombreActor().equals(modificacion[2])) {
													System.out.println("Nombre Actor: " + item2.getNombreActor());
													System.err.println("\nYa existe el actor, operación cancelada");
													modificarListaGeneral(1);
												} else {

													if (((Actor) item2).getId() == idUser) {
														item2.setNombreActor(modificacion[2]);
														item2.mostrarDatosActor();
													}
												}
											}
											break;
										}
										case "apellidos": {
											for (Actor item2 : listaArray) {
												if (((Actor) item2).getId() == idUser) {
													item2.setApellidoActor(modificacion[2]);
													item2.mostrarDatosActor();
												}
											}
											break;
										}
										case "edad": {
											for (Actor item2 : listaArray) {
												if (((Actor) item2).getId() == idUser) {
													item2.setEdadActor(Integer.parseInt(modificacion[2]));
													item2.mostrarDatosActor();
												}
											}
											break;
										}
										case "nacionalidad": {
											for (Actor item2 : listaArray) {
												if (((Actor) item2).getId() == idUser) {
													item2.setNacionalidadActor(modificacion[2]);
													item2.mostrarDatosActor();
												}
											}
											break;
										}
										default:
											throw new IllegalArgumentException("Unexpected value: " + modificacion);
										}
										System.out.println("\nSe ha Modificado correctamente");
										break;

									} else {
										// Usuario puede modificar lo que el ha introducido
										System.err
												.println("No puedes modificar una lista introducida por otra persona");
									}
								}
							}
						}
						encertat = true;
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// MODIFICAR DATOS LISTAS PERSONALES-director
	private static void modificarListaPersonalDirector(String archivo, String mensaje, ArrayList<Director> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Director>) reader.readObject();
					System.out.println(mensaje);

					for (Director item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Director item : listaArray) {
						int id = ((Director) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}
					boolean encertat = false;
					int idUser = 0;

					do {
						System.out.println("Seleccione id del elemento a modificar (pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado modificar la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {

							for (Director item : listaArray) {

								if (((Director) item).getId() == idUser) {

									// Comprobar que la introducido el usuario
									if (((Director) item).getNomUser().equals(nomUserFinal)) {

										String[] modificacion = item.modificarDatosDirector();
										switch (modificacion[1]) {
										case "nombre": {
											for (Director item2 : listaArray) {

												if (((Director) item2).getNombreDirector().equals(modificacion[2])) {
													System.out.println("Nombre director " + item2.getNombreDirector());
													System.err.println("\nYa existe la pelicula, operación cancelada");
													modificarListaGeneral(1);
												} else {

													if (((Director) item2).getId() == idUser) {
														item2.setNombreDirector(modificacion[2]);
														item2.mostrarDatosDirector();
													}
												}
											}
											break;
										}
										case "apellidos": {
											for (Director item2 : listaArray) {
												if (((Director) item2).getId() == idUser) {
													item2.setApellidoDirector(modificacion[2]);
													item2.mostrarDatosDirector();
												}
											}
											break;
										}
										case "edad": {
											for (Director item2 : listaArray) {
												if (((Director) item2).getId() == idUser) {
													item2.setEdadDirector(Integer.parseInt(modificacion[2]));
													item2.mostrarDatosDirector();
												}
											}
											break;
										}
										case "goyas": {
											for (Director item2 : listaArray) {
												if (((Director) item2).getId() == idUser) {
													item2.setGoyas(Integer.valueOf(modificacion[2]));
													item2.mostrarDatosDirector();
												}
											}
											break;
										}
										default:
											throw new IllegalArgumentException("Unexpected value: " + modificacion);
										}
										System.out.println("\nSe ha Modificado correctamente");
										break;

									} else {
										// Usuario puede modificar lo que el ha introducido
										System.err
												.println("No puedes modificar una lista introducida por otra persona");
									}
								}
							}
						}
						encertat = true;
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// MOSTRAR LISTAS GENERALES
	// ---------------------------------------------------------------------------------------------------------------

	public static void mostrarListasGenerales(int opcion) {
		switch (opcion) {
		case 1:
			mostrarListaGeneral("src/com/proyecto/listasPeliculas/peliculas.llista",
					"La lista general de Peliculas es:\n", PelisGeneral);
			break;
		case 2:
			mostrarListaGeneral("src/com/proyecto/listasPeliculas/actores.llista", "La lista general de Actores es:\n",
					ActorGeneral);
			break;
		case 3:
			mostrarListaGeneral("src/com/proyecto/listasPeliculas/directores.llista",
					"La lista general de Directores es:\n", DirectorGeneral);
			break;
		default:
			System.out.println("Opcion no valida");
			break;
		}
	}

	private static <objeto> void mostrarListaGeneral(String archivo, String mensaje, ArrayList<objeto> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<objeto>) reader.readObject();
					System.out.println(mensaje);

					for (objeto item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}
				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// PEDIR Y GUARDAR DATOS LISTAS PERSONALES //
	// ---------------------------------------------------------------------------------------------------------------

	// PEDIR DATOS LISTA PERSONAL PELICULA //
	public static void pedirListaPersonalPelicula() {
		File vacio = new File("src/com/proyecto/listasPeliculas/peliculas.llista");
		if (vacio.length() < 0 || vacio.length() == 0) {
			System.err.println("No puedes añadir nada ya que la lista general esta vacia");
		} else {
			System.out.println("Introduce el  id de la Pelicula que quieres ( pulse -1 para salir)");
			for (Pelicula i : PelisGeneral) {
				System.out.println(i.toString());
			}

			boolean encertat = false;
			int idUser = 0;

			do {
				idUser = ControlErrores.validarInt();

				if (idUser == -1) {
					System.out.println("Has cancelado la selección de la lista");
					encertat = true;
				} else {
					Pelicula peliculaSeleccionada = null;
					for (Pelicula item : PelisGeneral) {
						if (item.getId() == idUser) {
							peliculaSeleccionada = item;
							break;
						}
					}
					if (peliculaSeleccionada == null) {
						System.err.println("La película seleccionada no se ha encontrado en la lista general.");
					} else {
						boolean repetida = false;
						for (Pelicula item : PelisPersonal) {
							if (item.getId() == peliculaSeleccionada.getId()) {
								System.err.println("La película seleccionada ya existe en tu lista personal.");
								repetida = true;
								break;
							}
						}
						if (!repetida) {
							PelisPersonal.add(peliculaSeleccionada);
							registrarListaPersonalPelicula(PelisPersonal, "agregar");
							encertat = true;
						}
					}
				}
			} while (!encertat);
		}
	}

	// GUARDAR DATOS LISTA PERSONAL PELICULA //
	public static void registrarListaPersonalPelicula(ArrayList<Pelicula> llistaArray, String nombreMetodo) {
		// serialització
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			// obrim el fitxer per escriure, sense afegir
			// només tindrem un ArrayList d'objectes

			fout = new FileOutputStream("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/pelicula.llista",
					false);
			oos = new ObjectOutputStream(fout);
			// escrivim ArrayList sencer en el fitxer (1 sol objecte)
			oos.writeObject(llistaArray);
			oos.flush();
			oos.close();

			if (nombreMetodo.equals("agregar")) {
				System.out.println("La película ha sido registrada correctamente a tu lista personal.");

			} else if (nombreMetodo.equals("actualizar")) {
				System.out.println("Se procedera a borrar los elementos que no existen...");

			}

		} catch (Exception ex) {
			System.err.println("Error en registrar personal.pelicula.llista " + ex);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception ex) {
					System.err.println("Error en registrar personal.pelicula.llista " + ex);
				}
			}
		}
	}

	// PEDIR DATO LISTA PERSONAL ACTOR //
	public static void pedirListaPersonalActor() {
		File vacio = new File("src/com/proyecto/listasPeliculas/actores.llista");
		if (vacio.length() < 0 ||  vacio.length() == 0) {
			System.err.println("No puedes añadir nada ya que la lista general esta vacia");
		} else {
			System.out.println("Introduce el  id del Actor/a que quieres (pulse -1 para salir)");
			for (Actor i : ActorGeneral) {
				System.out.println(i.toString());
			}

			boolean encertat = false;
			int idUser = 0;

			do {
				idUser = ControlErrores.validarInt();

				if (idUser == -1) {
					System.out.println("Has cancelado la selección de la lista");
					encertat = true;
				} else {
					Actor ActorSeleccionado = null;
					for (Actor item : ActorGeneral) { // -interestellar
						if (item.getId() == idUser) { // -saturno
							ActorSeleccionado = item; // 6-jupiter
							break;
						}
					}
					if (ActorSeleccionado == null) {
						System.err.println("El actor/a seleccionado no se ha encontrado en la lista general.");
					} else {
						boolean repetida = false;
						for (Actor item : ActorPersonal) {
							if (item.getId() == ActorSeleccionado.getId()) {
								System.err.println("El actor/a seleccionado ya existe en tu lista personal.");
								repetida = true;
								break;
							}
						}
						if (!repetida) {
							ActorPersonal.add(ActorSeleccionado);
							registrarListaPersonalActor(ActorPersonal, "agregar");
							encertat = true;
						}
					}
				}
			} while (!encertat);
		}
	}

	// GUARDAR DATOS LISTA PERSONAL ACTOR //
	public static void registrarListaPersonalActor(ArrayList<Actor> llistaArray, String nombreMetodo) {
		// serialització
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			// obrim el fitxer per escriure, sense afegir
			// només tindrem un ArrayList d'objectes

			fout = new FileOutputStream("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista", false);
			oos = new ObjectOutputStream(fout);
			// escrivim ArrayList sencer en el fitxer (1 sol objecte)
			oos.writeObject(llistaArray);
			oos.flush();
			oos.close();

			if (nombreMetodo.equals("agregar")) {
				System.out.println("El actor/a ha sido registrado correctamente a tu lista personal.");

			} else if (nombreMetodo.equals("actualizar")) {
				System.out.println("Se procedera a borrar los elementos que no existen...");

			}
		} catch (Exception ex) {
			System.err.println("Error en registrar personal.actor.llista " + ex);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception ex) {
					System.err.println("Error en registrar personal.actor.llista " + ex);
				}
			}
		}
	}

	// PEDIR LISTA PERSONAL DIRECTOR //
	public static void pedirListaPersonalDirector() {
		File vacio = new File("src/com/proyecto/listasPeliculas/directores.llista");
		if (vacio.length() < 0 || vacio.length() == 0) {
			System.err.println("No puedes añadir nada ya que la lista general esta vacia");
		} else {
			System.out.println("Introduce el  id de la Pelicula que quieres ( pulse -1 para salir)");
			for (Director i : DirectorGeneral) {
				System.out.println(i.toString());
			}

			boolean encertat = false;
			int idUser = 0;

			do {
				idUser = ControlErrores.validarInt();

				if (idUser == -1) {
					System.out.println("Has cancelado la selección de la lista");
					encertat = true;
				} else {
					Director directorSeleccionado = null;
					for (Director item : DirectorGeneral) {
						if (item.getId() == idUser) {
							directorSeleccionado = item;
							break;
						}
					}
					if (directorSeleccionado == null) {
						System.err.println("El director/a seleccionado no se ha encontrado en la lista general.");
					} else {
						boolean repetida = false;
						for (Director item : DirectorPersonal) {
							if (item.getId() == directorSeleccionado.getId()) {
								System.err.println("El director/a seleccionado ya existe en tu lista personal.");
								repetida = true;
								break;
							}
						}
						if (!repetida) {
							DirectorPersonal.add(directorSeleccionado);
							registrarListaPersonalDirector(DirectorPersonal, "agregar");
							encertat = true;
						}
					}
				}
			} while (!encertat);
		}
	}

	// GUARDAR DATOS LISTA PERSONAL DIRECTOR //
	public static void registrarListaPersonalDirector(ArrayList<Director> llistaArray, String nombreMetodo) {
		// serialització
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			// obrim el fitxer per escriure, sense afegir
			// només tindrem un ArrayList d'objectes

			fout = new FileOutputStream("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/director.llista",
					false);
			oos = new ObjectOutputStream(fout);
			// escrivim ArrayList sencer en el fitxer (1 sol objecte)
			oos.writeObject(llistaArray);
			oos.flush();
			oos.close();

			if (nombreMetodo.equals("agregar")) {
				System.out.println("El director/a ha sido registrado correctamente a tu lista personal.");

			} else if (nombreMetodo.equals("actualizar")) {
				System.out.println("Se procedera a borrar los elementos que no existen...");

			}

		} catch (Exception ex) {
			System.err.println("Error en registrar personal.director.llista " + ex);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception ex) {
					System.err.println("Error en registrar personal.director.llista " + ex);
				}
			}
		}
	}

	// BORRAR DATOS LISTAS PERSONALES
	// ---------------------------------------------------------------------------------------------------------------

	public static void borrarListaPersonal(int opcion) {
		switch (opcion) {
		case 1:
			borrarListaPersonalPelicula("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/pelicula.llista",
					"La lista personal de Peliculas es:\n", PelisPersonal);
			break;
		case 2:
			borrarListaPersonalActor("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista",
					"La lista personal de Actores es:\n", ActorPersonal);
			break;
		case 3:
			borrarListaPersonalDirector("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/director.llista",
					"La lista personal de Directores es:\n", DirectorPersonal);
			break;
		default:
			System.out.println("Opcion no valida");
			break;
		}
	}

	// BORRAR DATOS LISTAS PERSONALES- pelicula
	private static void borrarListaPersonalPelicula(String archivo, String mensaje, ArrayList<Pelicula> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Pelicula>) reader.readObject();
					System.out.println(mensaje);

					for (Pelicula item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Pelicula item : listaArray) {
						int id = ((Pelicula) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}

					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a borrar( pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado el borrado de la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {
							for (Pelicula item : listaArray) {
								if (((Pelicula) item).getId() == idUser) {
									listaArray.remove(item);
									System.out.println("Se ha borrado correctamente");
									encertat = true;
									break;
								}
							}
						}
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// BORRAR DATOS LISTAS PERSONALES- director
	private static void borrarListaPersonalDirector(String archivo, String mensaje, ArrayList<Director> listaArray) {
		File fitxer = new File(archivo);
//		System.out.println("tamaño"+fitxer);s
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Director>) reader.readObject();
					System.out.println(mensaje);

					for (Director item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Director item : listaArray) {
						int id = ((Director) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}

					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a borrar( pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado el borrado de la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {
							for (Object item : listaArray) {
								if (((Director) item).getId() == idUser) {
									listaArray.remove(item);
									System.out.println("Se ha borrado correctamente");
									encertat = true;
									break;
								}
							}
						}
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// BORRAR DATOS LISTAS PERSONALES- actor
	private static void borrarListaPersonalActor(String archivo, String mensaje, ArrayList<Actor> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<Actor>) reader.readObject();
					System.out.println(mensaje);

					for (Actor item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (Actor item : listaArray) {
						int id = ((Actor) item).getId();
						if (id > max) {
							max = id;
						}
						if (id < min) {
							min = id;
						}
					}

					boolean encertat = false;
					int idUser = 0;
					do {
						System.out.println("Seleccione id del elemento a borrar( pulse -1 para salir)");
						idUser = ControlErrores.validarInt();
						if (idUser == -1) {
							System.out.println("Has cancelado el borrado de la lista");
							encertat = true;

						} else if (idUser > max || idUser < min) {
							System.err.println("El numero que has puesto no esta en la lista");
						} else {
							for (Actor item : listaArray) {
								if (((Actor) item).getId() == idUser) {
									listaArray.remove(item);
									System.out.println("Se ha borrado correctamente");
									encertat = true;
									break;
								}
							}
						}
					} while (!encertat);

					ObjectOutputStream oos = null;
					FileOutputStream fout = null;

					fout = new FileOutputStream(archivo, false);
					oos = new ObjectOutputStream(fout);
					// escrivim ArrayList sencer en el fitxer (1 sol objecte)
					oos.writeObject(listaArray);
					oos.flush();
					oos.close();

				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// MODIFICAR LISTAS GENERALES

	// MOSTRAR LISTAS PERSONALES //
	// ---------------------------------------------------------------------------------------------------------------

	public static void mostrarListasPersonales(int opcion) {
		switch (opcion) {
		case 1:
			mostrarListaPersonal("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/pelicula.llista",
					"La lista personal de Peliculas es:\n", PelisPersonal);
			break;
		case 2:
			mostrarListaPersonal("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista",
					"La lista personal de Actores es:\n", ActorPersonal);
			break;
		case 3:
			mostrarListaPersonal("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/director.llista",
					"La lista personal de Directores es:\n", DirectorPersonal);
			break;
		default:
			System.out.println("Opcion no valida");
			break;
		}
	}

	private static <objeto> void mostrarListaPersonal(String archivo, String mensaje, ArrayList<objeto> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.err.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<objeto>) reader.readObject();
					System.out.println(mensaje);

					for (objeto item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}
				} catch (Exception ex) {
					System.err.println("Error en llegir " + archivo + ": " + ex);
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
				System.err.println("Error en llegir " + archivo + ": " + ex);
			}
		}
	}

	// COMPROBAR SI HA HABIDO MODIFICACION //
	// ---------------------------------------------------------------------------------------------------------------

	// COMPROBAR PELICULAS //
	public static void comprobarModificacionUsuarioPelicula() {
		File peligeneral = new File("src/com/proyecto/listasPeliculas/peliculas.llista");
		if (peligeneral.length() == 0 || peligeneral.length() < 0) {
		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream("src/com/proyecto/listasPeliculas/peliculas.llista");
				ObjectInputStream reader = new ObjectInputStream(file);

				FileInputStream filePersonal = new FileInputStream(
						"src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/pelicula.llista");
				ObjectInputStream readerPersonal = new ObjectInputStream(filePersonal);

				try {
					// llegim l'objecte que hi ha al fitxer (1 sol array List)
					PelisGeneral = (ArrayList<Pelicula>) reader.readObject();
					PelisPersonal = (ArrayList<Pelicula>) readerPersonal.readObject();

					boolean elementosBorrados = false;
					List<Pelicula> pelisBorrar = new ArrayList<>();

					for (int i = 0; i < PelisPersonal.size(); i++) {
						Pelicula peliculaPersonal = PelisPersonal.get(i);
						int idPersonal = peliculaPersonal.getId();
						boolean trobat = false;

						// Si el id no coincide significara que la pelicula en la lista personal no
						// existe en la general, por lo tanto trobat se evalua como false
						for (int j = 0; j < PelisGeneral.size(); j++) {
							Pelicula peliculaGeneral = PelisGeneral.get(j);
							if ((peliculaGeneral.getId() == idPersonal)) {
								trobat = true;
								break;
							}
						}
						if (!trobat) {
							elementosBorrados = true;
							pelisBorrar.add(peliculaPersonal);
						}
					}
					if (elementosBorrados) {
						System.out.println(
								"\nExistem peliculas en tu lista personal que han sido borrados de la general");
						System.out.println(
								"¿Desea borrarlos de la personal? (pulse 1 para borrarlos o -1 para cancelar)");
						int borrado = ControlErrores.validarInt();
						if (borrado == -1) {
							System.out.println("Se ha cancelado la operación");
						} else if (borrado == 1) {
							PelisPersonal.removeAll(pelisBorrar);
							registrarListaPersonalPelicula(PelisPersonal, "actualizar");
						}
					}
				} catch (Exception ex) {
				}

				reader.close();
				file.close();
				filePersonal.close();
				readerPersonal.close();
			} catch (Exception ex) {
			}
		}
	}

	// COMPROBAR ACTORES //
	public static void comprobarModificacionUsuarioActor() {
		File peligeneral = new File("src/com/proyecto/listasPeliculas/actores.llista");
		if (peligeneral.length() == 0 || peligeneral.length() < 0) {
		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream("src/com/proyecto/listasPeliculas/actores.llista");
				ObjectInputStream reader = new ObjectInputStream(file);

				FileInputStream filePersonal = new FileInputStream(
						"src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista");
				ObjectInputStream readerPersonal = new ObjectInputStream(filePersonal);

				try {
					// llegim l'objecte que hi ha al fitxer (1 sol array List)
					ActorGeneral = (ArrayList<Actor>) reader.readObject();
					ActorPersonal = (ArrayList<Actor>) readerPersonal.readObject();

					boolean elementosBorrados = false;
					List<Actor> actoresBorrar = new ArrayList<>();

					for (int i = 0; i < ActorPersonal.size(); i++) {
						Actor actorPersonal = ActorPersonal.get(i);
						int idPersonal = actorPersonal.getId();
						boolean trobat = false;

						// Si el id no coincide significara que la pelicula en la lista personal no
						// existe en la general, por lo tanto trobat se evalua como false
						for (int j = 0; j < ActorGeneral.size(); j++) {
							Actor actorGeneral = ActorGeneral.get(j);
							if ((actorGeneral.getId() == idPersonal)) {
								trobat = true;
								break;
							}
						}
						if (!trobat) {
							elementosBorrados = true;
							actoresBorrar.add(actorPersonal);
						}
					}
					if (elementosBorrados) {
						System.out
								.println("\nExistem Actores en tu lista personal que han sido borrados de la general");
						System.out.println(
								"¿Desea borrarlos de la personal? (pulse 1 para borrarlos o -1 para cancelar)");
						int borrado = ControlErrores.validarInt();
						if (borrado == -1) {
							System.out.println("Se ha cancelado la operación");
						} else if (borrado == 1) {
							ActorPersonal.removeAll(actoresBorrar);
							registrarListaPersonalActor(ActorPersonal, "actualizar");
						}
					}
				} catch (Exception ex) {
				}

				reader.close();
				file.close();
				filePersonal.close();
				readerPersonal.close();
			} catch (Exception ex) {
			}
		}
	}

	// COMPROBAR DIRECTORES //
	public static void comprobarModificacionUsuarioDirector() {
		File peligeneral = new File("src/com/proyecto/listasPeliculas/directores.llista");
		if (peligeneral.length() == 0 || peligeneral.length() < 0) {
		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream("src/com/proyecto/listasPeliculas/directores.llista");
				ObjectInputStream reader = new ObjectInputStream(file);

				FileInputStream filePersonal = new FileInputStream(
						"src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/director.llista");
				ObjectInputStream readerPersonal = new ObjectInputStream(filePersonal);

				try {
					// llegim l'objecte que hi ha al fitxer (1 sol array List)
					DirectorGeneral = (ArrayList<Director>) reader.readObject();
					DirectorPersonal = (ArrayList<Director>) readerPersonal.readObject();

					boolean elementosBorrados = false;
					List<Director> directorBorrar = new ArrayList<>();

					for (int i = 0; i < DirectorPersonal.size(); i++) {
						Director directorPersonal = DirectorPersonal.get(i);
						int idPersonal = directorPersonal.getId();
						boolean trobat = false;

						// Si el id no coincide significara que la pelicula en la lista personal no
						// existe en la general, por lo tanto trobat se evalua como false
						for (int j = 0; j < DirectorGeneral.size(); j++) {
							Director directorGeneral = DirectorGeneral.get(j);
							if ((directorGeneral.getId() == idPersonal)) {
								trobat = true;
								break;
							}
						}
						if (!trobat) {
							elementosBorrados = true;
							directorBorrar.add(directorPersonal);
						}
					}
					if (elementosBorrados) {
						System.out.println(
								"\nExistem Directores en tu lista personal que han sido borrados de la general");
						System.out.println(
								"¿Desea borrarlos de la personal? (pulse 1 para borrarlos o -1 para cancelar)");
						int borrado = ControlErrores.validarInt();
						if (borrado == -1) {
							System.out.println("Se ha cancelado la operación");
						} else if (borrado == 1) {
							DirectorPersonal.removeAll(directorBorrar);
							registrarListaPersonalDirector(DirectorPersonal, "actualizar");
						}
					}
				} catch (Exception ex) {
				}

				reader.close();
				file.close();
				filePersonal.close();
				readerPersonal.close();
			} catch (Exception ex) {
			}
		}
	}

	// CARGAR LOS ARRAYS GENERALES AL PRINCIPIO DEL PROGRAMA //
	// ---------------------------------------------------------------------------------------------------------------
	public static void cargarArrayslist() {
		// CARGAR LISTA GENERAL PELICULA //
		File peligeneral = new File("src/com/proyecto/listasPeliculas/peliculas.llista");
		if (peligeneral.length() == 0 || peligeneral.length() < 0) {

		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream("src/com/proyecto/listasPeliculas/peliculas.llista");
				ObjectInputStream reader = new ObjectInputStream(file);

				try {
					// llegim l'objecte que hi ha al fitxer (1 sol array List)
					PelisGeneral = (ArrayList<Pelicula>) reader.readObject();

				} catch (Exception ex) {
				}

				reader.close();
				file.close();
			} catch (Exception ex) {
			}
		}

		// CARGAR LISTA GENERAL DE ACTOR //
		File actorGeneral = new File("src/com/proyecto/listasPeliculas/actores.llista");
		if (actorGeneral.length() == 0 || actorGeneral.length() < 0) {

		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream("src/com/proyecto/listasPeliculas/actores.llista");
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// llegim l'objecte que hi ha al fitxer (1 sol array List)
					ActorGeneral = (ArrayList<Actor>) reader.readObject();
				} catch (Exception ex) {
				}

				reader.close();
				file.close();
			} catch (Exception ex) {
			}
		}
		// CARGAR LISTA GENERAL DIRECTOR //
		File directorGeneral = new File("src/com/proyecto/listasPeliculas/directores.llista");
		if (directorGeneral.length() == 0 || directorGeneral.length() < 0) {

		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream("src/com/proyecto/listasPeliculas/directores.llista");
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					DirectorGeneral = (ArrayList<Director>) reader.readObject();
				} catch (Exception ex) {
				}

				reader.close();
				file.close();
			} catch (Exception ex) {
			}
		}

		// CARGAR LOS ARRAYS PERSONALES AL PRINCIPIO DEL PROGRAMA //
		// ---------------------------------------------------------------------------------------------------------------

		// CARGAR LISTA PERSONAL DE DIRECTOR //
		File directorPersonal = new File("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/director.llista");
		if (directorPersonal.length() == 0 || directorPersonal.length() < 0) {

		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(
						"src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/director.llista");
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					DirectorPersonal = (ArrayList<Director>) reader.readObject();
				} catch (Exception ex) {
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
			}
		}

		// CARGAR LISTA PERSONAL DE PELICULA //
		File peliculaPersonal = new File("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/pelicula.llista");
		if (peliculaPersonal.length() == 0 || peliculaPersonal.length() < 0) {

		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(
						"src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/pelicula.llista");
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					PelisPersonal = (ArrayList<Pelicula>) reader.readObject();
				} catch (Exception ex) {
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
			}
		}

		// CARGAR LISTA PERSONAL DE ACTOR //
		File actorPersonal = new File("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista");
		if (actorPersonal.length() == 0 || actorPersonal.length() < 0) {

		} else {
			try {
				// obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(
						"src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista");
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					ActorPersonal = (ArrayList<Actor>) reader.readObject();
				} catch (Exception ex) {
				}
				reader.close();
				file.close();
			} catch (Exception ex) {
			}
		}
	}

	/// METODO PARA ABRIR LA IMAGEN /// AUN NO ESTA TERMINADO
	// ---------------------------------------------------------------------------------------------------------------

	public static void abrirImagenNavegador(String nombreImagen) {

		// OBTENEMOS EL NOMBRE DEL SISTEMA EN MINUSCULAS
		String rutaImagen = System.getProperty("user.dir") + "/src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/"
				+ nombreImagen;
		String comando = "";

		// COMPROBAMOS SI ES MAC, WINDOWS O OTRO
		if (System.getProperty("os.name").startsWith("Windows")) {
			comando = "cmd /c start chrome file:///" + rutaImagen;
		} else if (System.getProperty("os.name").startsWith("Mac")) {
			comando = "open -a /Applications/Google\\ Chrome.app " + rutaImagen;
		} else {
			comando = "google-chrome " + rutaImagen;
		}

		// EJECUTAMOS EL COMANDO DE ACUERDO AL SISMTE OPERATIVO PARA ABRIR LA IMAGEN EN
		// EL NAVEGADOR
		try {
			Runtime.getRuntime().exec(comando);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// COMPROBAR SI LA IMAGEN HA CAMBIADO
	// ---------------------------------------------------------------------------------------------------------------

	public static String comprobarNombreImagen() {
		String nombreFinal = "porDefecto.png"; // Establecer la imagen por defecto como nombre inicial

		// Definir la ruta de la carpeta del usuario
		String rutaImagen = "src/com/proyecto/usuariosCarpetas/" + nomUserFinal;
		File carpeta = new File(rutaImagen);

		// Si la carpeta del usuario existe
		if (carpeta.exists()) {
			// Obtener la lista de archivos en la carpeta
			File[] archivos = carpeta.listFiles();
			for (File archivo : archivos) {
				// Si el archivo es una imagen y no contiene "llista" en su nombre
				if (archivo.isFile() && !archivo.getName().contains("llista")) {
					// Guardar la extensión del archivo
					String extension = archivo.getName().substring(archivo.getName().lastIndexOf("."));
					// Si el nombre del archivo contiene "porDefecto.png", mantenerlo como nombre
					// final
					if (archivo.getName().contains("porDefecto.png")) {
						nombreFinal = archivo.getName();
					} else {
						// Si no, establecer el nombre del usuario y la extensión como nombre final
						nombreFinal = nomUserFinal + extension;
					}
					break; // Salir del bucle, ya que se encontró una imagen válida
				}
			}
		}
		// Devolver el nombre final de la imagen
		return nombreFinal;
	}

	// CAMBIAR LA IMAGEN
	// ---------------------------------------------------------------------------------------------------------------

	public static void cambiarImagen() {
		// OBTENEMOS EL NOMBRE DE LA IMAGEN EN NUESTRA CARPETA DE USUARIO //
		String nombreImagen;
		System.out.println();
		String rutaImagen = "src/com/proyecto/usuariosCarpetas/" + nomUserFinal;
		File archivo = new File(rutaImagen);
		if (archivo.exists() && archivo.isDirectory()) { // Verificar que el directorio exista y sea un directorio
			File[] files = archivo.listFiles();
			for (File file : files) {
				// Verificar que sea un archivo y que tenga una extensión de imagen
				if (file.isFile() && ControlErrores.ValidarImagen(file.getName())) {
					// guardamos el nombre de la imagen
					nombreImagen = file.getName();
					System.out.println("Nombre de la imagen inicial: " + nombreImagen);
				}
			}
		} else {
			System.err.println("El directorio especificado no existe o no es un directorio");
		}

		// TOCA PEDIR AL USUARIO LA NUEVA RUTA DE LA IMAGEN

		String nuevaImagen = "";
		boolean encertatRuta = false;
		do {

			System.out.println("Introduce la ruta de la nueva imagen, para cancelar pulse (-1):");
			nuevaImagen = leer.nextLine();

			File fotoAGuardar = new File(nuevaImagen);
			// Validamos que la ruta que se introduzca sea de una imagen
			if ((fotoAGuardar.exists()) && fotoAGuardar.isFile()) {
				encertatRuta = true;
			} else {
				encertatRuta = false;
			}

			// Dar opcion para cancelar
			if (nuevaImagen.equals("-1")) {
				break;
			}

			if (ControlErrores.ValidarImagen(fotoAGuardar.getName())) {
				// copiamos la imagen
				try {
					// ABRIR EL ARCHIVO DONDE SE ENCUENTRA LA IMAGEN
					FileInputStream rutaOrigen = new FileInputStream(nuevaImagen);

					// ABRIR PARA ESCRIBIR EL ARCHIVO DE IMAGEN EN LA CARPETA DE USUARIO
					FileOutputStream rutaDestino = new FileOutputStream(
							"src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/" + nomUserFinal + "."
									+ ControlErrores.validarExtension(fotoAGuardar.getName()));

					// CREAMOS UN BUFFER DE BYTES PARA ALMACENAR TEMPORALMENTE LOS DATOS LEIDOS
					byte[] buffer = new byte[1024];

					// LEER DATOS DEL FILEINPUTSTREAM Y ESCRIBIRLOS EN EL FILEOUTPUSTREAM HASTA QUE
					// NO HAYA MAS DATOS QUE LEER
					int lenght;
					while ((lenght = rutaOrigen.read(buffer)) > 0) {
						rutaDestino.write(buffer, 0, lenght);
					}
					System.out.println("\nLa foto se ha cambiado correctamente");
					// CERRAMOS
					rutaOrigen.close();
					rutaDestino.close();

					// ELIMINAMOS LA IMAGEN ORIGINAL
					try {
						// Crear una instancia de la clase File utilizando la ruta del archivo
						String ruta = "src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/porDefecto.png";
						File imagenOriginal = new File(ruta);

						// Eliminar el archivo solo si existe, sin mostrar mensajes ni lanzar
						// excepciones
						if (imagenOriginal.exists()) {
							imagenOriginal.delete();
						}
					} catch (Exception e) {
						// No hacer nada si ocurre una excepción
					}
				} catch (Exception e) {
				}
				// ahora cambiamos el nombre
				// ESTO NO HACE FALTA DE MOMENTO
//				if (archivo.exists() && archivo.isDirectory()) { // Verificar que el directorio exista y sea un directorio
//		            File[] files = archivo.listFiles();
//		            for (File file : files) {
//		                if (file.isFile() && ValidarImagen(file.getName())) { // Verificar que sea un archivo y que tenga una extensión de imagen
//		                  
//		                	File nuevoNombre =new File("src/com/proyecto/usuariosCarpetas/" + nomUserFinal+"/"+nomUserFinal+"."+validarExtension(fotoAGuardar.getName()));
//		                	//guardamos el nombre de la imagen
//		                	
//		                	System.out.println(file.getAbsolutePath()+"-------absoluta");
//		                	System.out.println(nuevoNombre.getAbsolutePath()+"-------absoluta");
//		                	boolean  cambiado=file.renameTo(nuevoNombre);
//		                	System.out.println(cambiado);
//
//		                	System.out.println(nuevoNombre+"Cambio de nombre del archivo");
//		                }
//		            }
//		        } else {
//		            System.err.println("No se ha cambiado el nombre");
//		        }	
//			}

			} else {
				System.err.println("La ruta introducida es erronea");
				System.err.println("Proceso finalizado");
			}
		} while (!encertatRuta && !(nuevaImagen.equals("-1")));

	}

	// CAMBIAR EL NOMBRE DE L A IMAGEN EN EL REGISTRO
	// ---------------------------------------------------------------------------------------------------------------

	public static void cambiarImagenRegistro(String nombreExtension) {
		// Obtener el nombre de la extensión
		int posExtensio = nombreExtension.lastIndexOf(".");
		nombreExtension = nombreExtension.substring(posExtensio, nombreExtension.length());

		File originalFile = new File("src/com/proyecto/utils/usersGuardados.txt");
		File tempFile = new File("src/com/proyecto/utils/tempFile.txt");

		try {
			// Abrir el archivo original para lectura
			FileReader fr = new FileReader(originalFile);
			BufferedReader br = new BufferedReader(fr);

			// Abrir un archivo temporal para escritura
			FileWriter fw = new FileWriter(tempFile);
			BufferedWriter bw = new BufferedWriter(fw);

			String linia;
			while ((linia = br.readLine()) != null) {
				String[] dades = linia.split("[|]");

				// Si la línea contiene el nombre de usuario deseado
				if (dades[0].contains(nomUserFinal)) {
					// Calcular la posición de inicio de la columna que queremos cambiar
					int pos = linia.indexOf(dades[0]) + dades[0].length() + 1 + dades[1].length() + 1;

					// Calcular la diferencia de longitud entre la cadena original y la nueva
					int diferencia = dades[2].length() - (nomUserFinal + nombreExtension).length();
					String espacios = "";

					// Si la diferencia es positiva, agregar espacios para mantener el formato de
					// las columnas
					if (diferencia > 0) {
						for (int i = 0; i < diferencia; i++) {
							espacios += " ";
						}
					}

					// Construir la nueva línea con la columna actualizada y mantener las otras
					// columnas intactas
					String newLine = linia.substring(0, pos) + nomUserFinal + nombreExtension + espacios
							+ linia.substring(pos + dades[2].length());
					bw.write(newLine);
				} else {
					// Si la línea no contiene el nombre de usuario, copiar la línea sin cambios al
					// archivo temporal
					bw.write(linia);
				}
				// Agregar un salto de línea en el archivo temporal
				bw.newLine();
			}

			System.out.println("La imagen ha sido actualizada del registro");
			br.close();
			bw.close();
			originalFile.delete();
			// Renombrar el archivo temporal como el archivo original
			tempFile.renameTo(originalFile);

		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
	}

	// ELIMINAR USUARIO //

	// ETC ETC //

}