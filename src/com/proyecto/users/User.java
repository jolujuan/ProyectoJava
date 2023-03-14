package com.proyecto.users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.proyecto.clases.Director;
import com.proyecto.clases.Pelicula;

public class User {

	// ATRIBUTOS //
	protected static int countId = 0;
	protected static int id;
	protected String nombre;
	protected String apellidos;
	protected String contrasenia;
	protected String email;
	protected String poblacion;
	protected String fechaNacimiento;

	Rol roles;

	public enum Rol {
		USUARIO, ADMIN
	}

	/// CONTRUCTOR ///
	public User(int id, String nombre, String apellidos, String contrasenia, String email, String poblacion, Rol roles,
			String fechaNacimiento) {

		super();
		// La ruta para obtener el id
		String ruta = "src/com/proyecto/utils/usersGuardados.txt";

		// Para contador cero obtener el ultimo numero
		if (countId == 0) {
			countId = obtenerId(ruta);
		}
		// Cada vez ira autoincrementando el contador
		countId++;

		// El contador es igual al id
		User.id = countId;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contrasenia = contrasenia;
		this.email = email;
		this.poblacion = poblacion;
		this.roles = roles;
		this.fechaNacimiento = fechaNacimiento;
	}

	// Omitimos el encabezado y obtenemos la posicion 1 respecto al id
	// Despues de sacar el ultimo numero en la lectura, comparamos con contador
	public int obtenerId(String ruta) {
		int idActual = 0;
		int idContador = 0;
		File fitxer = new File(ruta);

		try {
			BufferedReader llegir = new BufferedReader(new FileReader(fitxer));

			String line;
			try {
				while ((line = llegir.readLine()) != null) {

					if (!line.contains("#")) {
						String[] array = line.split("\\|");

						idActual = Integer.parseInt(array[1]);

						if (idActual > idContador) {
							idContador = idActual;
						}
					}
				}
				llegir.close();
			} catch (IOException e) {
				System.out.println("Error: " + e);
			}
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		return idContador;
	}

	/// GETTERS Y SETTERS
	public static int getId() {
		return id;
	}

	public void setId(int id) {
		User.id = id;
	}

	public static int getCountId() {
		return countId;
	}

	public static void setCountId(int countId) {
		User.countId = countId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public Rol getRoles() {
		return roles;
	}

	public void setRoles(Rol roles) {
		this.roles = roles;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/// FUNCIONES AÑADIR, VER LISTAS PERSONALES Y GLOBALES ///
	// LISTAS PERSONALES DE CADA USUARIO //
	public void addPeliculaListaPersonal(Pelicula idPeli) {

	}

	public void visualizarPeliculaListaPersonal(Pelicula idPeli) {

	}

	public void addDirectorListaPersonal(Director idDirector) {

	}

	public void visualizarDirectorListaPersonal(Director idDirector) {
	}

	public void addActorListaPersonal(Pelicula idPeli) {

	}

	public void visualizarActorListaPersonal(Pelicula idPeli) {

	}

	/// LISTAS GLOBALES ///
	public void addPeliculaListaGlobal(Pelicula idPeli) {

	}

	public void visualizarPeliculaListaGlobal(Pelicula idPeli) {

	}

	public void addDirectorListaGlobal(Director idDirector) {

	}

	public void visualizarDirectorListaGlobal(Director idDirector) {

	}

	public void addActorListaGlobal(Pelicula idPeli) {

	}

	public void visualizarActorListaGlobal(Pelicula idPeli) {

	}

	/// TOSTRING -> PARA MOSTRAR LA INFORMACION ///
	@Override
	public String toString() {
		return "Id: " + id + "\nNombre: " + nombre + "\nApellidos: " + apellidos + "\nContraseña: " + contrasenia
				+ "\nEmail: " + email + "\nPoblacion: " + poblacion + "\nRol: " + roles + "\nFecha Nacimiento: "
				+ fechaNacimiento;
	}
}
