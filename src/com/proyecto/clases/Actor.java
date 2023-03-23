package com.proyecto.clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Actor implements  Serializable {

	/// ATRIBUTOS ACTORES ///
	private static int countIdActor = 0;
	private int idActor;
	private String nombreActor;
	private String apellidoActor;
	private int edadActor;
	private String nacionalidadActor;

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
		return nombreActor+" "+apellidoActor;
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

	@Override
	public String toString() {
		return idActor + "-. " + nombreActor + " " + apellidoActor;
	}

}
