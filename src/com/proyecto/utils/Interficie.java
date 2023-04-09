package com.proyecto.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Interficie {

	public static void interficieFinalitzat() {
		JFrame eixidaMenu = new JFrame("Programa Finalitzat!!");
		eixidaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon icono = new ImageIcon("src/com/proyecto/utils/icono.png");
		eixidaMenu.setIconImage(icono.getImage());

	
		JLabel mensaje = new JLabel("Adéu, fins aviat!");
		mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		Font font = new Font(Font.DIALOG, Font.BOLD + Font.ITALIC, 22);
		mensaje.setFont(font);
		mensaje.setForeground(Color.BLUE);
		mensaje.setOpaque(true);
		Color amarillo = new Color(255, 200, 0);
		mensaje.setBackground(amarillo);
		Border border = BorderFactory.createMatteBorder(7, 3, 7, 3, Color.BLUE);
		mensaje.setBorder(BorderFactory.createCompoundBorder(null, border));


		JLabel membres = new JLabel(Funciones.mostrarColaboradores());
		membres.setPreferredSize(new Dimension(750, 750));
		
		
		JLabel institut = new JLabel("IES Lluís Simarro");

		JLabel curs = new JLabel("1r DAW");

		JLabel asignatura = new JLabel("Programacio");

		JLabel any = new JLabel("Curs 22/23");

		centrarLabels(membres, institut, curs, asignatura, any);
		
		JPanel panell = new JPanel(new GridLayout(6, 1));
	   
		
		panell.add(mensaje);
		panell.add(membres);
		panell.add(institut);
		panell.add(curs);
		panell.add(asignatura);
		panell.add(any);
		
		eixidaMenu.setExtendedState(JFrame.NORMAL);
		
		 // Obtener la dimensión de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Establecer la altura de la ventana para que sea igual a la altura de la pantalla
        int alturaCompleta = screenSize.height;
        
		eixidaMenu.setSize(400, alturaCompleta);	
		eixidaMenu.add(panell, BorderLayout.CENTER);
		eixidaMenu.setVisible(true);

		eixidaMenu.setAlwaysOnTop(true);
	}

	public static void centrarLabels(JLabel... labels) {
		for (JLabel label : labels) {
			label.setHorizontalAlignment(SwingConstants.CENTER);
			Font font = new Font(Font.DIALOG, Font.BOLD + Font.ITALIC, 17);
			label.setFont(font);
			label.setForeground(Color.RED);
			Border border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK);
			label.setBorder(BorderFactory.createCompoundBorder(null, border));

		}
	}

}
