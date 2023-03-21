package com.proyecto.clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Pelicula  implements Serializable  {

	/// ATRIBUTOS PELICULAS ///
	private static int countIdPelicula = 0;
	private int idPelicula;
	private String nombrePelicula;
	private int duracion;
	private String anioEmision;
	private String genero;

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
		return idPelicula + "-. " + nombrePelicula;
	}

}
