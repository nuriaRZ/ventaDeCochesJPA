package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;


import modelo.ventaDeCoches.controladores.ErrorBBDDException;
import modelo.ventaDeCoches.controladores.ImposibleConectarException;

public class Main {
	
	public static void menuPrincipal() throws ParseException, ErrorBBDDException {
		Scanner sc;
		int opcion;
		do {
			System.out.println("GESTION DE VENTAS: "+
					"\n1.-Gestion de Fabricantes."+
					"\n2.- Gestion de Concesionario."+
					"\n3.- Gestion de Clientes."+
					"\n4.- Gestion de Coches."+
					"\n5.- Gestion de Ventas."+
					"\n Pulse 0 para salir");
			
			 sc = new Scanner(System.in);
			
			  opcion = sc.nextInt();
			  System.out.println("opcion: "+ opcion);	  
				
				switch (opcion) {
				case 0:
					System.out.println("Adios");
					break;
					
				case 1:
					GestionFabricante.menuGestion();
				
				case 2: 
					
					
				case 3:
						
				case 4:
					
				
				case 5:
					
				
				default: 	
					if(opcion > 5 || opcion < 0) {
						System.out.println("Elija una de las opciones existentes...");
					}
			
				}
				
					
				System.out.println();
			
		}while(opcion != 0);


	}

	public static void main(String[] args) throws ParseException, ErrorBBDDException {
		
		
			menuPrincipal();
			
		
	}

}
