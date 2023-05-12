package com.proyecto.clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.proyecto.utils.ControlErrores;
import com.proyecto.utils.Funciones;

public class Director implements  Serializable {

	/// ATRIBUTOS DIRECTOR ///
	private static int countIdDirector = 0;
	private int idDirector;
	private String nombreDirector;
	private String apellidoDirector;
	private int edadDirector;
	private int goyas;
	private String nomUser=Funciones.nomUserFinal;

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
		this.nomUser=Funciones.nomUserFinal;
	}
	
	// Mostrar datos de la pelicula
		public void mostrarDatosDirector() {
			String datos = "\n 1-> Nombre : " + nombreDirector + "\n 2-> Apellidos: " + apellidoDirector + "\n 3-> Edad: "
					+ edadDirector + "\n 4-> Goyas: " + goyas;
			System.out.println(datos);
		}

		// modificar datos de la pelicula
		public String[] modificarDatosDirector() {
			String[] retorno = new String[3];
			String datos = "\n 1-> Nombre : " + nombreDirector + "\n 2-> Apellidos: " + apellidoDirector + "\n 3-> Edad: "
					+ edadDirector + "\n 4-> Goyas: " + goyas;
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
						System.out.println("El nombre del director es: " + nombreDirector);
						System.out.print("Que nombre le quieres poner: ");

						String nombre = ControlErrores.validarString();
						retorno[2] = nombre;
						retorno[1] = "nombre";

						break;
					}
					case 2: {
						System.out.println("Los apellidos del director son: " + apellidoDirector);
						System.out.print("Que apellidos le quieres poner: ");
						String apellidos = ControlErrores.validarString();
						retorno[2] = apellidos;
						retorno[1] = "apellidos";
						break;
					}
					case 3: {
						System.out.println("La edad del director es: " + edadDirector);
						System.out.print("Que edad le quieres poner: ");
						int edad = ControlErrores.validarInt();
						retorno[2] = String.valueOf(edad);
						retorno[1] = "edad";
						break;
					}
					case 4: {
						System.out.println("Los goyas del director son: " + goyas);
						System.out.print("Quantos goyas le quieres poner: ");
						int goyas = ControlErrores.validarInt();
						retorno[2] = String.valueOf(goyas);
						retorno[1] = "goyas";
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
	
	public String getNombYApellDirector() {
		return nombreDirector+" "+apellidoDirector;
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
	
	public  String getNomUser() {
		return this.nomUser;
	}

	@Override
	public String toString() {
		return idDirector + "-. " + nombreDirector + " " + apellidoDirector+" -> "+nomUser;
	}

}
