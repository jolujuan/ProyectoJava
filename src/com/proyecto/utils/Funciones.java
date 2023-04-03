package com.proyecto.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.proyecto.Ismael.MostrarNombreIsma;
import com.proyecto.clases.Actor;
import com.proyecto.clases.Director;
import com.proyecto.clases.Pelicula;
import com.proyecto.edu.MostrarNombreEdu;
import com.proyecto.javier.MostrarNombreJavier;
import com.proyecto.joseluis.MostrarNombreJoselu;
import com.proyecto.maikol.MostrarNombreMaikol;
import com.proyecto.users.Cliente;
import com.proyecto.users.User;
import com.proyecto.users.User.Rol;

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
		Cliente N1 = new Cliente(User.getId(), usuario, apellidos, contraseña, email, poblacion, User.Rol.USUARIO,
				fecha, null, null, null);

		// Creamos el usuario con la funcion
		nomUser = obtenerNomUser(User.getId(), email);

		// Pasamos los parametros del objeto a la funcíon guardar usuarios
		guardarUsuario(nomUser, User.getId(), usuario, apellidos, email, contraseña, poblacion, User.Rol.USUARIO,
				fecha);

		// Pasamos el parametro usuario para crear carpeta
		crearCarpeta(nomUser);

		return false;
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
			while ((lenght = rutaOrigen.read()) > 0) {
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

			System.out.println("\nUsuario guardado correctamente" + " \u2714");
			System.out.println(
					"\u001B[1mTu nombre de usuario es: " + "\u001B[0m" + "\u001B[1m" + nomUser + "\u001B[0m\n");

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
		if (vacio.length() < 0 || vacio.length() == 0) {
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
	public static void abrirImagenNavegador(String rutaImagen) {
		// OBTENEMOS EL NOMBRE DEL SISTEMA OPERATIVO EN MINUSCULAS //
		String sistemaOperativo = System.getProperty("os.name").toLowerCase();
		if (sistemaOperativo.indexOf("win") >= 0) {
			rutaImagen = "file:\\" + rutaImagen;
		} else if (sistemaOperativo.indexOf("mac") >= 0) {
			rutaImagen = "file://" + rutaImagen;
		} else {
			rutaImagen = "file;//" + rutaImagen;
		}

		File archivo = new File(rutaImagen);

		try {
			Desktop.getDesktop().browse(archivo.toURI());
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	// ELIMINAR USUARIO //

	// ETC ETC //

}