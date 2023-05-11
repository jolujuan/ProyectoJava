package com.proyecto.clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.proyecto.utils.ControlErrores;
import com.proyecto.utils.Funciones;

public class Actor implements Serializable {

	/// ATRIBUTOS ACTORES ///
	private static int countIdActor = 0;
	private int idActor;
	private String nombreActor;
	private String apellidoActor;
	private int edadActor;
	private String nacionalidadActor;
	private String nomUser=Funciones.nomUserFinal;

	/// CONSTRUCTOR ///
	public Actor(String nombreActor, String apellidoActor, int edadActor, String nacionalidadActor) {
		super();

		// La ruta para obtener el id
		String ruta = "src/com/proyecto/listasPeliculas/actores.llista";

		// Para contador cero obtener el ultimo numero
		if (countIdActor == 0) {
			countIdActor = obtenerId(ruta);
		}
		// Cada vez ira autoincrementando el contador
		countIdActor++;

		this.idActor = countIdActor; // ID AUTOINCREMENTADO
		this.nombreActor = nombreActor;
		this.apellidoActor = apellidoActor;
		this.edadActor = edadActor;
		this.nacionalidadActor = nacionalidadActor;
		this.nomUser = Funciones.nomUserFinal;
	}

	// Mostrar datos de la pelicula
	public void mostrarDatosActor() {
		String datos = "\n 1-> Nombre : " + nombreActor + "\n 2-> Apellidos: " + apellidoActor + "\n 3-> Edad: "
				+ edadActor + "\n 4-> Nacionalidad: " + nacionalidadActor;
		System.out.println(datos);
	}

	// modificar datos de la pelicula
	public String[] modificarDatosActor() {
		String[] retorno = new String[3];
		String datos = "\n 1-> Nombre : " + nombreActor + "\n 2-> Apellidos: " + apellidoActor + "\n 3-> Edad: "
				+ edadActor + "\n 4-> Nacionalidad: " + nacionalidadActor;
		System.out.println(datos);
		boolean encertat = false;
		int n = 0;
		do {
			System.out.println("\nSelecciona una opción (pulse -1 para salir)");
			n = ControlErrores.validarInt();
			if (n == -1) {
				System.out.println("Has cancelado modificar la lista");
				encertat = true;

			} else if (!(n >= 1 && n <= 4)) {
				System.err.println("El numero que has puesto no esta en la lista");
			} else {
				System.out.println("Has seleccionado la " + n + " opción");
				switch (n) {
				case 1: {
					System.out.println("El nombre del actor es: " + nombreActor);
					System.out.print("Que nombre le quieres poner: ");

					String nombre = ControlErrores.validarString();
					retorno[2] = nombre;
					retorno[1] = "nombre";

					break;
				}
				case 2: {
					System.out.println("Los apellidos del actor son: " + apellidoActor);
					System.out.print("Que apellidos le quieres poner: ");
					String apellidos = ControlErrores.validarString();
					retorno[2] = apellidos;
					retorno[1] = "apellidos";
					break;
				}
				case 3: {
					System.out.println("La edad del actor es: " + edadActor);
					System.out.print("Que edad le quieres poner: ");
					int edad = ControlErrores.validarInt();
					retorno[2] = String.valueOf(edad);
					retorno[1] = "edad";
					break;
				}
				case 4: {
					System.out.println("La nacionalidad del actor es: " + nacionalidadActor);
					System.out.print("Que nacionalidad le quieres poner: ");
					String nacionalidad = ControlErrores.validarString();
					retorno[2] = nacionalidad;
					retorno[1] = "nacionalidad";
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + n);
				}

				encertat = true;

			}
		} while (!encertat);
		return retorno;
	}

	// Haremos una comparacion hacia el ultimo id de la clase y lo asignaremos
	// dentro del contructor, incrementandolo
	public int obtenerId(String ruta) {
		int contador = 0;
		File fitxer = new File(ruta);
		if (fitxer.length() == 0) {
		} else {
			try {

				FileInputStream file = new FileInputStream(fitxer);
				ObjectInputStream reader = new ObjectInputStream(file);

				try {
					ArrayList<Actor> ActorGeneral = (ArrayList<Actor>) reader.readObject();

					for (Actor actores : ActorGeneral) {
						if (actores.getIdActor() > contador) {
							contador = actores.getIdActor();
						}
					}

				} catch (IOException e) {
					System.out.println("Error: " + e);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				reader.close();
			} catch (IOException e) {
				System.out.println("Error: " + e);
			}
		}
		return contador;
	}

	/// GETTERS Y SETTERS ///

	public int getId() {
		return idActor;
	}

	public int getIdActor() {
		return idActor;
	}

	public void setIdActor(int idActor) {
		this.idActor = idActor;
	}

	public static int getCountIdActor() {
		return countIdActor;
	}

	public static void setCountIdActor(int countIdActor) {
		Actor.countIdActor = countIdActor;
	}

	public String getNombreActor() {
		return nombreActor;
	}

	public void setNombreActor(String nombreActor) {
		this.nombreActor = nombreActor;
	}

	public String getApellidoActor() {
		return apellidoActor;
	}

	public void setApellidoActor(String apellidoActor) {
		this.apellidoActor = apellidoActor;
	}

	public String getNombYApellActor() {
		return nombreActor + " " + apellidoActor;
	}

	public int getEdadActor() {
		return edadActor;
	}

	public void setEdadActor(int edadActor) {
		this.edadActor = edadActor;
	}

	public String getNacionalidadActor() {
		return nacionalidadActor;
	}

	public void setNacionalidadActor(String nacionalidadActor) {
		this.nacionalidadActor = nacionalidadActor;
	}
	
	public  String getNomUser() {
		return this.nomUser;
	}

	@Override
	public String toString() {
		return idActor + "-. " + nombreActor + " " + apellidoActor + " -> " + nomUser;
	}

}
