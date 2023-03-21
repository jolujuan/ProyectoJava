package com.proyecto.utils;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.proyecto.Ismael.MostrarNombreIsma;
import com.proyecto.clases.Actor;

import com.proyecto.clases.Director;
import com.proyecto.clases.Pelicula;
import com.proyecto.edu.MostrarNombreEdu;
import com.proyecto.javier.MostrarNombreJavier;
import com.proyecto.joseluis.MostrarNombreJoselu;
import com.proyecto.users.Cliente;
import com.proyecto.users.User;
import com.proyecto.users.User.Rol;

public class Funciones {

	// MOSTRAR CREADORES DEL CODIGO //
	public static void mostrarColaboradores() {
		MostrarNombreEdu colaborador1 = new MostrarNombreEdu();
		MostrarNombreIsma colaborador2 = new MostrarNombreIsma();
		MostrarNombreJavier colaborador3 = new MostrarNombreJavier();
		MostrarNombreJoselu colaborador4 = new MostrarNombreJoselu();

		System.out.println(colaborador1 + "\n" + colaborador2 + "\n" + colaborador3 + "\n" + colaborador4 + "\n");
	}

	static Scanner leer = new Scanner(System.in);
	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

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
	public static void registrarUsuario() {
		System.out.println("Introduce tu nombre:");
		String usuario = ControlErrores.validarString();

		System.out.println("Introduce los apellidos:");
		String apellidos = ControlErrores.validarString();

		System.out.println("Introduce tu email:");
		String email = ControlErrores.validarEmail();

		System.out.println("Introduce tu poblacion:");
		String poblacion = ControlErrores.validarString();

		System.out.println("Introduce tu fecha de nacimiento (dd/mm/aaaa):");
		String fecha = ControlErrores.validarFecha();

		// pedir contraseña
		String contraseña = ControlErrores.pedirContraseña();

		// registro finish
		System.out.println("Registro completado");

		// Array List como null, para guardar solo información de los usuarios
		// Obtenemos el ID de usuario con una funcion en la clase
		Cliente N1 = new Cliente(User.getId(), usuario, apellidos, contraseña, email, poblacion, User.Rol.USUARIO,
				fecha, null, null, null);

		// Creamos el usuario con la funcion
		nomUser = obtenerNomUser(User.getId(), email);

		// Mostramos los datos del cliente y su numro de usuario
		System.out.println("\n" + N1.toString());
		System.out.println("Tu nombre de usuario es: " + nomUser);

		// Pasamos los parametros del objeto a la funcíon guardar usuarios
		guardarUsuario(nomUser, User.getId(), usuario, apellidos, email, contraseña, poblacion, User.Rol.USUARIO,
				fecha);
		// Pasamos el parametro usuario para crear carpeta
		crearCarpeta(nomUser);

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

				listACtor.createNewFile();
				listDirector.createNewFile();
				listPelicula.createNewFile();

				// System.out.println("El usuario se ha creado correctamente");
			} else {
				System.out.println("No se ha podido crear el usuario (quizas el usuario ya existe)");
				System.out.println("Vuelve a registrar tu usuario: ");
				Funciones.registrarUsuario();
			}
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
		return nomUser;
	}

	// GUARDAR USUARIOS EN FICHERO TXT //
	// ---------------------------------------------------------------------------------------------------------------
	public static void guardarUsuario(String nomUser, int ID, String nombre, String apellidos, String email,
			String contraseña, String poblacion, Rol rol, String fecha) {
		try {
			File file = new File("src/com/proyecto/utils/usersGuardados.txt");
			PrintWriter escriureUser = new PrintWriter(new FileWriter(file, true));

			// Escribir los datos del usuario en un formato fijo
			String datos = String.format("%-17s|%03d|%-18s|%-18s|%-30s|%-18s|%-13s|%-12s|%-14s", nomUser, ID, nombre,
					apellidos, email, contraseña, poblacion, rol, fecha);

			// Comprobar si el archivo está vacío para escribir el encabezado
			if (file.length() == 0) {
				escriureUser.println(
						"#USUARIO         |ID | Nombre           | Apellidos        | Email                        | Contraseña       | Población   | Rol        | Fecha        ");
				escriureUser.println(
						"#----------------+---+------------------+------------------+------------------------------+------------------+-------------+------------+--------------");
			}

			// Escribir los datos del usuario en el archivo
			escriureUser.println(datos);
			escriureUser.close();

			System.out.println("\nUsuario guardado correctamente" + " \u2714" + "\n");
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
	}// ---------------------------------------------------------------------------------------------------------------

	// LOGIN USUARIO //
	public static boolean validaUsuario() {
		try {
			File f = new File("src/com/proyecto/utils/usersGuardados.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			System.out.println("Introduce el nombre de usuario: ");
			String usr = ControlErrores.validarString();
			devolverNombreUser(usr);

			System.out.println("Introduce la contraseña: ");
			String pwd = ControlErrores.validarString();

			String linia = br.readLine();
			linia = br.readLine();
			boolean trobat = false;
			boolean login = false;
			while ((linia = br.readLine()) != null && !trobat) {
				String[] dades = linia.split("[|]");

				if (dades.length >= 6) { // asegurarse de que hay suficientes columnas
					dades[0] = dades[0].trim();
					dades[5] = dades[5].trim();

					if (dades[0].equals(usr)) {
						trobat = true;
						if (dades[5].equals(pwd)) {
							System.out.println("\nHola " + usr + ", has iniciado sesion " + "\u2714");
							// missatge benvinguda, nom apellido
							login = true;
						} else {
							trobat = true;
							System.out.println("ERROR. Contraseña errónea para el usuario " + usr);
						}
					}
				}
			}
			if (!trobat) {
				System.out.println("ERROR. No se encontró un usuario con el nombre: " + usr);
			}
			br.close();
			return login;
		} catch (IOException e) {
			System.err.println("Error: " + e);
			return false;
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
		System.out.println("Introduce el nombre de la pelicula:");
		String pelicula = ControlErrores.validarString();

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
		System.out.println("Introduce el nombre del actor:");
		String nom = ControlErrores.validarString();

		System.out.println("Introduce los apellidos del actor:");
		String apellidos = ControlErrores.validarString();

		System.out.println("Introduce la edad del actor:");
		int edad = ControlErrores.validarInt();

		System.out.println("Introduce la nacionalidad del actor:");
		String nacionalidad = ControlErrores.validarString();

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
		System.out.println("Introduce el nombre del director:");
		String nom = ControlErrores.validarString();

		System.out.println("Introduce los apellidos del director:");
		String apellidos = ControlErrores.validarString();

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
	private static <objeto> void borrarListaGeneralPelicula(String archivo, String mensaje,
			ArrayList<objeto> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.out.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<objeto>) reader.readObject();
					System.out.println(mensaje);

					for (Object item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (objeto item : listaArray) {
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
							System.out.println("El numero que has puesto no esta en la lista");
						} else {
							for (Object item : listaArray) {
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
	private static <objeto> void borrarListaGeneralDirector(String archivo, String mensaje,
			ArrayList<objeto> listaArray) {
		File fitxer = new File(archivo);
//		System.out.println("tamaño"+fitxer);s
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.out.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<objeto>) reader.readObject();
					System.out.println(mensaje);

					for (Object item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (objeto item : listaArray) {
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
							System.out.println("El numero que has puesto no esta en la lista");
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

	// BORRAR DATOS LISTAS GENERALES- actor
	private static <objeto> void borrarListaGeneralActor(String archivo, String mensaje, ArrayList<objeto> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0 || listaArray.size() <= 0) {
			System.out.println("No hay nada que mostrar");
		} else {
			try {
				// Obrim fitxer per a lectura
				FileInputStream file = new FileInputStream(archivo);
				ObjectInputStream reader = new ObjectInputStream(file);
				try {
					// Llegim l'objecte que hi ha al fitxer (1 sol array List)
					listaArray = (ArrayList<objeto>) reader.readObject();
					System.out.println(mensaje);

					for (Object item : listaArray) {
						System.out.println(item.toString());
						System.out.println();
					}

					// Calcular correctamente el rango de los ids
					int min = Integer.MAX_VALUE;
					int max = Integer.MIN_VALUE;

					for (objeto item : listaArray) {
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
							System.out.println("El numero que has puesto no esta en la lista");
						} else {
							for (Object item : listaArray) {
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

	// MOSTRAR LISTAS GENERALES
	// ---------------------------------------------------------------------------------------------------------------

	public static void mostrarListasGenerales(int opcion) {
		switch (opcion) {
		case 1:
			mostrarListaGeneral("src/com/proyecto/listasPeliculas/peliculas.llista",
					"La lista general de Peliculas es:\n", new ArrayList<Pelicula>());
			break;
		case 2:
			mostrarListaGeneral("src/com/proyecto/listasPeliculas/actores.llista", "La lista general de Actores es:\n",
					new ArrayList<Actor>());
			break;
		case 3:
			mostrarListaGeneral("src/com/proyecto/listasPeliculas/directores.llista",
					"La lista general de Directores es:\n", new ArrayList<Director>());
			break;
		default:
			System.out.println("Opcion no valida");
			break;
		}
	}

	private static <objeto> void mostrarListaGeneral(String archivo, String mensaje, ArrayList<objeto> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() < 0 || fitxer.length() == 0) {
			System.out.println("No hay nada que mostrar");
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
			System.out.println("No puedes añadir nada ya que la lista general esta vacia");
		} else {
			System.out.println("Introduce el numero de la pelicula que quieres:");
			for (Pelicula i : PelisGeneral) {
				System.out.println(i.toString());
			}
			int numPeliACopiar = ControlErrores.validarInt();

			if (numPeliACopiar > PelisGeneral.size()) {
				System.out.println("El numero que has puesto no esta en la lista");
			} else {
				Pelicula personal = PelisGeneral.get(numPeliACopiar - 1);
				PelisPersonal.add(personal);
				registrarListaPersonalPelicula();
			}
		}
	}

	// GUARDAR DATOS LISTA PERSONAL PELICULA //
	public static void registrarListaPersonalPelicula() {
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
			oos.writeObject(PelisPersonal);
			oos.flush();
			oos.close();
			System.out.println("La pelicula se ha registrado correctamente");
			
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
		if (vacio.length() < 0 || vacio.length() == 0) {
			System.out.println("No puedes añadir nada ya que la lista general esta vacia");
		} else {
			System.out.println("Introduce el numero del Actor/a que quieres:");
			for (Actor i : ActorGeneral) {
				System.out.println(i.toString());
			}
			int numPeliACopiar = ControlErrores.validarInt();

			if (numPeliACopiar > PelisGeneral.size()) {
				System.out.println("El numero que has puesto no esta en la lista");
			} else {
				Actor personal = ActorGeneral.get(numPeliACopiar - 1);
				ActorPersonal.add(personal);
				registrarListaPersonalActor();
			}
		}
	}

	// GUARDAR DATOS LISTA PERSONAL ACTOR //
	public static void registrarListaPersonalActor() {
		// serialització
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			// obrim el fitxer per escriure, sense afegir
			// només tindrem un ArrayList d'objectes

			fout = new FileOutputStream("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista", false);
			oos = new ObjectOutputStream(fout);
			// escrivim ArrayList sencer en el fitxer (1 sol objecte)
			oos.writeObject(ActorPersonal);
			oos.flush();
			oos.close();
			System.out.println("El actor/a se ha registrado correctamente");
			
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
			System.out.println("No puedes añadir nada ya que la lista general esta vacia");
		} else {
			System.out.println("Introduce el numero del Director/a que quieres:");
			for (Director i : DirectorGeneral) {
				System.out.println(i.toString());
			}
			int numPeliACopiar = ControlErrores.validarInt();

			if (numPeliACopiar > DirectorGeneral.size()) {
				System.out.println("El numero que has puesto no esta en la lista");
			} else {
				Director personal = DirectorGeneral.get(numPeliACopiar - 1);
				DirectorPersonal.add(personal);
				registrarListaPersonalDirector();
			}
		}
	}

	// GUARDAR DATOS LISTA PERSONAL DIRECTOR //
	public static void registrarListaPersonalDirector() {
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
			oos.writeObject(DirectorPersonal);
			oos.flush();
			oos.close();
			System.out.println("El director/a se ha registrado correctamente");
			
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

	// MOSTRAR LISTAS PERSONALES //
	// ---------------------------------------------------------------------------------------------------------------

	public static void mostrarListasPersonales(int opcion) {
		switch (opcion) {
		case 1:
			mostrarListaPersonal("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/pelicula.llista",
					"La lista personal de Peliculas es:\n", new ArrayList<Pelicula>());
			break;
		case 2:
			mostrarListaPersonal("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/actor.llista",
					"La lista personal de Actores es:\n", new ArrayList<Actor>());
			break;
		case 3:
			mostrarListaPersonal("src/com/proyecto/usuariosCarpetas/" + nomUserFinal + "/director.llista",
					"La lista personal de Directores es:\n", new ArrayList<Director>());
			break;
		default:
			System.out.println("Opcion no valida");
			break;
		}
	}

	private static <objeto> void mostrarListaPersonal(String archivo, String mensaje, ArrayList<objeto> listaArray) {
		File fitxer = new File(archivo);
		if (fitxer.length() == 0) {
			System.out.println("No hay nada que mostrar");
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

	// CARGAR LOS ARRAYS GENERALES AL PRINCIPIO DEL PROGRAMA //
	// ---------------------------------------------------------------------------------------------------------------
	public static void cargarArrayslist() {
		// CARGAR LISTA GENERAL PELICULA //
		File peligenral = new File("src/com/proyecto/listasPeliculas/peliculas.llista");
		if (peligenral.length() == 0 || peligenral.length() < 0) {

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
					DirectorPersonal=(ArrayList<Director>)reader.readObject();
				} catch (Exception ex) {
//				System.err.println("Error: " + ex);
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
					PelisPersonal=(ArrayList<Pelicula>)reader.readObject();
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
					ActorPersonal=(ArrayList<Actor>)reader.readObject();
				} catch (Exception ex) {
				}

				reader.close();
				file.close();
			} catch (Exception ex) {
			}
		}
	}

	// ELIMINAR USUARIO //

	// ETC ETC //

}
