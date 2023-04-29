
package com.proyecto.main;

import com.proyecto.utils.ControlErrores;
import com.proyecto.utils.Funciones;
import com.proyecto.utils.Interficie;

public class Main {

	public static void main(String[] args) {

		int menuPrincipal = 0, menuUsuario = 0, menuUsuarioSecundario = 0, menuAdmin = 0, menuAdminSecundariol = 0;
		// MENU PRINCIPAL //

		do {
			System.out.println("Escoja una opcion: \n-> 1. PRUEBAS \n-> 2. LOGIN \n-> 3. SALIR");
			menuPrincipal = ControlErrores.validarMenuPrincipal();
			switch (menuPrincipal) {
			case 1:

				Funciones.cargarArrayslist();
				///// AQUI IRA LA FUNCION DE registrarUser() ///////////
				System.out.println("-----Registrar Usuario----- (pulse -1 para salir)");

				if (Funciones.registrarUsuario()) {
					System.out.println("La operación ha sido cancelada, salir del registro...\n");
					break;
				}

				break;
			case 2:
				///// AQUI IRA LA FUNCION DE loginUser() ///////////
				System.out.println("-----Iniciar Sesion-----");
				String rol = ControlErrores.validaUsuario();
				// dependiendo de si es admin o no Se dirigira a los menus de admin o de users
				if (rol.equals("admin")) {

					do {
						System.out.println("Bienvenido, estas en el modo administrador ");
						System.out.println(
								"\n-> 1. Ver lista general  \n-> 2. Añadir nuevo elemento a lista general \n-> 3. Borrar elemento lista general  \n-> 4. Modificar elemento lista general \n-> 5. Salir");
						menuAdmin = ControlErrores.validarTernario();

						switch (menuAdmin) {
						case 1:
							System.out.println("-----Ver lista GENERAL-----");
							do {
								System.out.println(
										"\n-> 1. Ver Peliculas \n-> 2. Ver Actores \n-> 3. Ver Directores \n-> 4. Salir");
								menuAdminSecundariol = ControlErrores.validarTerciario();
								Funciones.cargarArrayslist();
								switch (menuAdminSecundariol) {
								case 1:
									System.out.println("-----Ver Peliculas (Lista GENERAL)-----");
									Funciones.mostrarListasGenerales(1);
									break;
								case 2:
									System.out.println("-----Ver Actores (Lista GENERAL)-----");
									Funciones.mostrarListasGenerales(2);
									break;
								case 3:
									System.out.println("-----Ver Directores (Lista GENERAL)-----");
									Funciones.mostrarListasGenerales(3);
									break;
								
								default:
									System.out.println("Salir");
								}
							} while (menuAdminSecundariol != 4);
							break;

						case 2:
							System.out.println("-----Añadir elemento a lista GENERAL-----");
							do {
								System.out.println(
										"\n-> 1. Añadir Peliculas \n-> 2. Añadir Actores \n-> 3. Añadir Directores \n-> 4. Salir");
								menuAdminSecundariol = ControlErrores.validarTerciario();
								Funciones.cargarArrayslist();
								switch (menuAdminSecundariol) {
								case 1:
									System.out.println("-----Añádir Peliculas (Lista GENERAL)-----");
									Funciones.pedirListaGeneralPelicula();
									break;
								case 2:
									System.out.println("-----Añádir Actores (Lista GENERAL)-----");
									Funciones.pedirListaGeneralActor();
									break;
								case 3:
									System.out.println("-----Añádir Directores (Lista GENERAL)-----");
									Funciones.pedirListaGeneralDirector();
									break;
								default:
									System.out.println("Salir");
								}
							} while (menuAdminSecundariol != 4);
							break;

						case 3:
							System.out.println("-----Borrar elemento lista GENERAL-----");
							do {
								System.out.println(
										"\n-> 1. Borrar pelicula \n-> 2. Borrar Actor \n-> 3. Borrar Director \n-> 4. Salir");
								menuAdminSecundariol = ControlErrores.validarTerciario();
								Funciones.cargarArrayslist();
								switch (menuAdminSecundariol) {
								case 1: {
									Funciones.borrarListaGeneral(1);
									break;
								}
								case 2: {
									Funciones.borrarListaGeneral(2);
									break;
								}
								case 3: {
									Funciones.borrarListaGeneral(3);
									break;
								}
								default:
									System.out.println("Salir");
								}
							} while (menuAdminSecundariol != 4);
							break;
						case 4:
							System.out.println("-----Modificar elemento lista General-----");
							do {
								System.out.println(
										"\n-> 1. Modificar pelicula \n-> 2. Modificar Actor \n-> 3. Modificar Director \n-> 4. Salir");
								menuAdminSecundariol = ControlErrores.validarTerciario();
								Funciones.cargarArrayslist();
								switch (menuAdminSecundariol) {
								case 1: {
									Funciones.modificarListaGeneral(1);
									break;
								}
								case 2: {
//									Funciones.modificarListaGeneral(2);
									break;
								}
								case 3: {
//									Funciones.modificarListaGeneral(3);
									break;
								}
								default:
									System.out.println("Salir");
								}
							} while (menuAdminSecundariol != 4);
							
							
							break;
						default:
							System.out.println("Salir");
							break;
						}

					} while (menuAdmin != 5);
					break;

				} else {
					Funciones.comprobarModificacionUsuarioPelicula();
					Funciones.comprobarModificacionUsuarioActor();
					Funciones.comprobarModificacionUsuarioDirector();
					do {
						System.out.println(
								"\n-> 1. Ver lista general \n-> 2. Ver lista personal \n-> 3. Añadir nuevo elemento a lista personal \n-> 4. Añadir nuevo elemento a lista general \n-> 5. Ver imagen de perfil \n-> 6. Editar foto de perfil \n-> 7. Borrar elemento lista personal \n-> 8. Salir");
						menuUsuario = ControlErrores.validarSecundario();
						switch (menuUsuario) {
						case 1:
							Funciones.cargarArrayslist();
							System.out.println("-----Ver lista GENERAL-----");
							do {
								System.out.println(
										"\n-> 1. Ver Peliculas \n-> 2. Ver Actores \n-> 3. Ver Directores \n-> 4. Salir");
								menuUsuarioSecundario = ControlErrores.validarTerciario();
								switch (menuUsuarioSecundario) {
								case 1:
									System.out.println("-----Ver Peliculas (Lista GENERAL)-----");
									Funciones.mostrarListasGenerales(1);
									break;
								case 2:
									System.out.println("-----Ver Actores (Lista GENERAL)-----");
									Funciones.mostrarListasGenerales(2);
									break;
								case 3:
									System.out.println("-----Ver Directores (Lista GENERAL)-----");
									Funciones.mostrarListasGenerales(3);
									break;
								default:
									System.out.println("Salir");
								}
							} while (menuUsuarioSecundario != 4);
							break;

						case 2:
							Funciones.cargarArrayslist();
							System.out.println("-----Ver lista PERSONAL-----");
							do {
								System.out.println(
										"\n-> 1. Ver Peliculas \n-> 2. Ver Actores \n-> 3. Ver Directores \n-> 4. Salir");
								menuUsuarioSecundario = ControlErrores.validarTerciario();
								switch (menuUsuarioSecundario) {
								case 1:
									System.out.println("-----Ver Peliculas (Lista PERSONAL)-----");
									Funciones.mostrarListasPersonales(1);
									break;
								case 2:
									System.out.println("-----Ver Actores (Lista PERSONAL)-----");
									Funciones.mostrarListasPersonales(2);
									break;
								case 3:
									System.out.println("-----Ver Directores (Lista PERSONAL)-----");
									Funciones.mostrarListasPersonales(3);
									break;
								default:
									System.out.println("Salir");
								}
							} while (menuUsuarioSecundario != 4);
							break;

						case 3:
							Funciones.cargarArrayslist();
							System.out.println("-----Añadir elemento a lista PERSONAL-----");

							do {
								System.out.println(
										"\n-> 1. Añadir Peliculas \n-> 2. Añadir Actores \n-> 3. Añadir Directores \n-> 4. Salir");
								menuUsuarioSecundario = ControlErrores.validarTerciario();
								switch (menuUsuarioSecundario) {
								case 1:
									System.out.println("-----Añádir Peliculas (Lista PERSONAL)-----");
									Funciones.pedirListaPersonalPelicula();
									break;
								case 2:
									System.out.println("-----Añádir Actores (Lista PERSONAL)-----");
									Funciones.pedirListaPersonalActor();
									break;
								case 3:
									System.out.println("-----Añádir Directores (Lista PERSONAL)-----");
									Funciones.pedirListaPersonalDirector();
									break;
								default:
									System.out.println("Salir");
								}
							} while (menuUsuarioSecundario != 4);
							break;

						case 4:
							Funciones.cargarArrayslist();
							System.out.println("-----Añadir elemento a lista GENERAL-----");

							do {
								System.out.println(
										"\n-> 1. Añadir Peliculas \n-> 2. Añadir Actores \n-> 3. Añadir Directores \n-> 4. Salir");
								menuUsuarioSecundario = ControlErrores.validarTerciario();
								switch (menuUsuarioSecundario) {
								case 1:
									System.out.println("-----Añadir Peliculas (Lista GENERAL)-----");
									Funciones.pedirListaGeneralPelicula();
									break;
								case 2:
									System.out.println("-----Añadir Actores (Lista GENERAL)-----");
									Funciones.pedirListaGeneralActor();
									break;
								case 3:
									System.out.println("-----Añadir Directores (Lista GENERALl)-----");
									Funciones.pedirListaGeneralDirector();
									break;
								default:
									System.out.println("Salir");
								}
							} while (menuUsuarioSecundario != 4);
							break;
						case 5:
							System.out.println("-----Ver imagen de perfil-----");
							String nombreImagenVer = Funciones.comprobarNombreImagen();
							Funciones.abrirImagenNavegador(nombreImagenVer);
							break;
						case 6:
							System.out.println("-----Editar foto de perfil-----");
							Funciones.cambiarImagen();
							String nombreImagenCambiar = Funciones.comprobarNombreImagen();
							Funciones.cambiarImagenRegistro(nombreImagenCambiar);
							break;
						case 7:
							System.out.println("-----Borrar elemento lista PERSONAL-----");
							do {
								System.out.println(
										"\n-> 1. Borrar pelicula \n-> 2. Borrar Actor \n-> 3. Borrar Director \n-> 4. Salir");
								menuAdminSecundariol = ControlErrores.validarTerciario();
								Funciones.cargarArrayslist();
								switch (menuAdminSecundariol) {
								case 1: {
									Funciones.borrarListaPersonal(1);
									break;
								}
								case 2: {
									Funciones.borrarListaPersonal(2);
									break;
								}
								case 3: {
									Funciones.borrarListaPersonal(3);
									break;
								}
								default:
									System.out.println("Salir");
								}
							} while (menuAdminSecundariol != 4);
							break;
						

						default:
							System.out.println("Salir");
						}
					} while (menuUsuario != 8);
				}

				break;
			default:
				Interficie.interficieFinalitzat();
			}
		} while (menuPrincipal != 3);
	}
}
