package com.proyecto.main;

import com.proyecto.utils.ControlErrores;
import com.proyecto.utils.Funciones;

public class Main {

	public static void main(String[] args) {

		int menuPrincipal = 0, menuUsuario = 0, menuUsuarioSecundario = 0, menuAdmin = 0, menuAdminSecundariol = 0;
		;
		// MENU PRINCIPAL //

		do {
//			Funciones.mostrarColaboradores();
			System.out.println("Escoja una opcion: \n-> 1. PRUEBAS \n-> 2. Login \n-> 3. Salir");
			menuPrincipal = ControlErrores.validarMenuPrincipal();
			switch (menuPrincipal) {
			case 1:
				Funciones.cargarArrayslist();
				do {
					System.out.println(
							"\n-> 1. Borrar pelicula \n-> 2. Borrar Actor \n-> 3. Borrar Director \n-> 4. Salir");
					menuAdminSecundariol = ControlErrores.validarTerciario();
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

				///// AQUI IRA LA FUNCION DE registrarUser() ///////////
//				System.out.println("-----Registrar Usuario-----");
//				Funciones.registrarUsuario();
				break;
			case 2:
				///// AQUI IRA LA FUNCION DE loginUser() ///////////
				System.out.println("-----Iniciar Sesion-----");
				if (Funciones.validaUsuario()) {
					String admin = "admin";
					// dependiendo de si es admin o no Se dirigira a los menus de admin o de users
					if (Funciones.nomUserFinal.equals(admin)) {

						do {
							System.out.println("Bienvenido, estas en el modo administrador ");
							System.out.println(
									"\n-> 1. Ver lista general  \n-> 2. Añadir nuevo elemento a lista general \n-> 3. Borrar elemento lista general \n-> 4. Salir");
							menuAdmin = ControlErrores.validarTerciario();

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

							default:
								System.out.println("Salir");
								break;
							}

						} while (menuAdmin != 4);
						break;

					} else {

						do {
							System.out.println(
									"\n-> 1. Ver lista general \n-> 2. Ver lista personal \n-> 3. Añadir nuevo elemento a lista personal \n-> 4. Salir");
							menuUsuario = ControlErrores.validarTerciario();
							switch (menuUsuario) {
							case 1:
								Funciones.cargarArrayslist();
								System.out.println("-----Ver lista GENERAL-----");
								do {
									System.out.println(
											"\n-> 1. Ver Peliculas \n-> 2. Ver Actores \n-> 3. Ver Directores \n-> 4. Salir");
									menuUsuarioSecundario = ControlErrores.validarTerciario();
//									Funciones.cargarArrayslist();
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
//									Funciones.cargarArrayslist();
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
//									Funciones.cargarArrayslist();
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

							default:
								System.out.println("Salir");
							}
						} while (menuUsuario != 4);
					}
				}
				break;
			default:
				System.out.println("Has cerrado el programa maquina");
			}
		} while (menuPrincipal != 3);
	}
}
