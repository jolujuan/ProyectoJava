package com.proyecto.clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import com.proyecto.utils.ControlErrores;
import com.proyecto.utils.Funciones;

public class Pelicula implements Serializable {

	/// ATRIBUTOS PELICULAS ///
	private static int countIdPelicula = 0;
	private int idPelicula;
	private String nombrePelicula;
	private int duracion;
	private String anioEmision;
	private String genero;
	private String nomUser;

	/// CONSTRUCTOR ///
	public Pelicula(String nombrePelicula, int duracion, String anioEmision, String genero) {
		super();

		// La ruta para obtener el id
		String ruta = "src/com/proyecto/listasPeliculas/peliculas.llista";

		// Para contador cero obtener el ultimo numero
		if (countIdPelicula == 0) {
			countIdPelicula = obtenerId(ruta);
		}
		// Cada vez ira autoincrementando el contador
		countIdPelicula++;

		this.idPelicula = countIdPelicula; // ID AUTOINCREMENTADO
		this.nombrePelicula = nombrePelicula;
		this.duracion = duracion;
		this.anioEmision = anioEmision;
		this.genero = genero;
		this.nomUser = Funciones.nomUserFinal;
	}

	// Mostrar datos de la pelicula
	public void mostrarDatospelicula() {
		String datos = " 1-> Nombre : " + nombrePelicula + "\n 2-> Duracion: " + duracion + "\n 3-> Año de emision: "
				+ anioEmision + "\n 4-> Genero: " + genero;
		System.out.println(datos);
	}

	// modificar datos de la pelicula
	public String[] modificarDatosPelicula() {
		String[] retorno = new String[3];
		String datos = " 1-> Nombre: " + nombrePelicula + "\n 2-> Duración: " + duracion + "\n 3-> Año de emisión: "
				+ anioEmision + "\n 4-> Genero: " + genero;
		System.out.println(datos);
		boolean encertat = false;
		int n = 0;
		do {
			System.out.println("Selecciona una opción (pulse -1 para salir)");
			n = ControlErrores.validarInt();
			if (n == -1) {
				System.out.println("Has cancelado el borrado de la lista");
				encertat = true;

			} else if (!(n >= 1 && n <= 4)) {
				System.err.println("El numeroooo que has puesto no esta en la lista");
			} else {
				System.out.println("Has seleccionado la " + n + " opción");
				switch (n) {
				case 1: {
					System.out.println("El nombre de la pelicula es: "+nombrePelicula);
					System.out.println("Que nombre le quieres poner:");
					
					String pelicula = ControlErrores.validarString();
					retorno[2] = pelicula;
					retorno[1] = "pelicula";

					break;
				}
				case 2: {
					System.out.println("La duración de la pelicula es: "+duracion);
					System.out.println("Que duración le quieres poner:");
					int duracion = ControlErrores.validarInt();
					retorno[2] = String.valueOf(duracion);
					retorno[1] = "duracion";
					break;
				}
				case 3: {
					System.out.println("La fecha de la pelicula es: "+anioEmision);
					System.out.println("Que fecha le quieres poner:");
					String fechaEmisio = ControlErrores.validarFecha();
					retorno[2] = fechaEmisio;
					retorno[1] = "fechaEmisio";
					break;
				}
				case 4: {
					System.out.println("El genero de la pelicula es: "+genero);
					System.out.println("Que genero le quieres poner:");
					String genero = ControlErrores.validarString();
					retorno[2] = genero;
					retorno[1] = "genero";
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
					ArrayList<Pelicula> PelisGeneral = (ArrayList<Pelicula>) reader.readObject();

					for (Pelicula pelicula : PelisGeneral) {
						if (pelicula.getIdPelicula() > contador) {
							contador = pelicula.getIdPelicula();
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
		return idPelicula;
	}

	public int getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(int idPelicula) {
		this.idPelicula = idPelicula;
	}

	public static int getCountIdPelicula() {
		return countIdPelicula;
	}

	public static void setCountIdPelicula(int countIdPelicula) {
		Pelicula.countIdPelicula = countIdPelicula;
	}

	public String getNombrePelicula() {
		return nombrePelicula;
	}

	public void setNombrePelicula(String nombrePelicula) {
		this.nombrePelicula = nombrePelicula;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getAnioEmision() {
		return anioEmision;
	}

	public void setAnioEmision(String anioEmision) {
		this.anioEmision = anioEmision;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return idPelicula + "-. " + nombrePelicula + " -> " + nomUser;
	}

}
