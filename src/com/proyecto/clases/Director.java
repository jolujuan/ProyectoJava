package com.proyecto.clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Director implements  Serializable {

	/// ATRIBUTOS DIRECTOR ///
	private static int countIdDirector = 0;
	private int idDirector;
	private String nombreDirector;
	private String apellidoDirector;
	private int edadDirector;
	private int goyas;

	/// CONSTRUCTOR ///
	public Director(String nombreDirector, String apellidoDirector, int edadDirector, int goyas) {
		super();

		// La ruta para obtener el id
		String ruta = "src/com/proyecto/listasPeliculas/directores.llista";

		// Para contador cero obtener el ultimo numero
		if (countIdDirector == 0) {
			countIdDirector = obtenerId(ruta);
		}
		// Cada vez ira autoincrementando el contador
		countIdDirector++;

		this.idDirector = countIdDirector; // ID AUTOINCREMENTADO
		this.nombreDirector = nombreDirector;
		this.apellidoDirector = apellidoDirector;
		this.edadDirector = edadDirector;
		this.goyas = goyas;
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
					ArrayList<Director> DirectorGeneral = (ArrayList<Director>) reader.readObject();

					for (Director directores : DirectorGeneral) {
						if (directores.getIdDirector() > contador) {
							contador = directores.getIdDirector();
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
		return idDirector;
	}

	public int getIdDirector() {
		return idDirector;
	}

	public void setIdDirector(int idDirector) {
		this.idDirector = idDirector;
	}

	public static int getCountIdDirector() {
		return countIdDirector;
	}

	public static void setCountIdDirector(int countIdDirector) {
		Director.countIdDirector = countIdDirector;
	}

	public String getNombreDirector() {
		return nombreDirector;
	}

	public void setNombreDirector(String nombreDirector) {
		this.nombreDirector = nombreDirector;
	}

	public String getApellidoDirector() {
		return apellidoDirector;
	}

	public void setApellidoDirector(String apellidoDirector) {
		this.apellidoDirector = apellidoDirector;
	}

	public int getEdadDirector() {
		return edadDirector;
	}

	public void setEdadDirector(int edadDirector) {
		this.edadDirector = edadDirector;
	}

	public int getGoyas() {
		return goyas;
	}

	public void setGoyas(int goyas) {
		this.goyas = goyas;
	}

	@Override
	public String toString() {
		return idDirector + "-. " + nombreDirector + " " + apellidoDirector;
	}

}
