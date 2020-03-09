package modelo;

import java.text.ParseException;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.ventaDeCoches.Cliente;



public class GestionCliente {

	public static void menu()  {
		Scanner sc;
		int opcion = -1;
		do {
			
				System.out.println("GESTION DE CLIENTES: "+
						"\n1.-Listado de Clientes."+
						"\n2.- Alta a un Cliente."+
						"\n3.- Modificar un Cliente."+
						"\n4.- Baja a un Cliente."+					
						"\n Pulse 0 para salir");
				
					sc = new Scanner(System.in);
					opcion = sc.nextInt();
				switch (opcion) {
				case 1:
						listado(true);
					break;
				case 2:
						alta();
					break;
					
				case 3:
					modificacion();
					break;
					
				case 4: baja();


				
				case 0: Main.menuPrincipal(); 
				
				default: 
					if (opcion > 4 || opcion < 0) {
						System.out.println("Elija una de las opciones existentes...");
					}
				
			}
			
			
		}while (opcion != 0);
	
	}	

	
	/**
	 * @throws ParseException 
	 * 
	 */
	public static void listado(boolean pausafinal) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		Query q = em.createNamedQuery("Cliente.findAll");
		
		List<Cliente> clientes = (List<Cliente>) q.getResultList();
		
		for (Cliente cli : clientes) {
			System.out.println("Cliente: "+ cli.getId() + " DNI/NIE: "+ cli.getDniNie() + " nombre: " + cli.getNombre()+ " apellidos: " + cli.getApellidos()+" localidad: " + cli.getLocalidad() +" FecNac: " + cli.getFechaNac()+" activo: " + cli.getActivo() );
		}
		em.close();
		
	}
	/**
	 * 
	 * @throws ErrorBBDDException
	 * @throws ParseException 
	 */
	private static void alta () {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();

		Cliente cli = new Cliente();
		System.out.println("Introduzca 'DNI/NIE' del Cliente:");
		cli.setDniNie(Utils.getStringConsola());
		System.out.print("\nIntroduzca 'Nombre' del Cliente: ");
		cli.setNombre(Utils.getStringConsola());
		System.out.print("\nIntroduzca 'Apellidos' del Cliente: ");
		cli.setApellidos(Utils.getStringConsola());
		System.out.print("\nIntroduzca 'Localidad' del Cliente: ");
		cli.setLocalidad(Utils.getStringConsola());
		System.out.print("\nIntroduzca 'Fecha Nacimiento' del Cliente: ");		
		cli.setFechaNac(null);
		
		
		em.getTransaction().begin();
		em.persist(cli);
		em.getTransaction().commit();
		
		TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente as c", Cliente.class);
		
		List<Cliente> clientes = q.getResultList();
		
		for (Cliente cliEnLista : clientes) {
			System.out.println("Cliente: "+ cliEnLista.getId() + " DNI/NIE: "+ cliEnLista.getDniNie() + " nombre: " + cliEnLista.getNombre()+ " apellidos: " + cliEnLista.getApellidos()+" localidad: " + cliEnLista.getLocalidad() +" FecNac: " + cliEnLista.getFechaNac()+" activo: " + cliEnLista.getActivo() );
		}
		
		em.close();
		
	}

	/**
	 * 
	 * @throws ErrorBBDDException
	 * @throws ParseException 
	 */
	private static void modificacion () {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();
		int id = -2;
		do {
			System.out.println("\n\tIntroduzca ID del Cliente ('-1' - ver listado, '0' - salir): ");
			id = Utils.getIntConsola(-1);
			if (id == -1) listado(true);
			
			else {
				TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente as c where c.id = "+ id+"'", Cliente.class);
				
				List<Cliente> clientes = q.getResultList();
				
				em.getTransaction().begin();
				for (Cliente cliEnLista : clientes) {
					System.out.println("Introduzca 'CIF' para el Cliente");
					cliEnLista.setDniNie(Utils.getStringConsola());
					System.out.println("Introduzca 'nombre' para el Cliente");
					cliEnLista.setNombre(Utils.getStringConsola());
					System.out.println("Introduzca 'apellidos' para el Cliente");
					cliEnLista.setApellidos(Utils.getStringConsola());
					System.out.println("Introduzca 'localidad' para el Cliente");
					cliEnLista.setLocalidad(Utils.getStringConsola());
					
					em.persist(cliEnLista);
					
				}
				em.getTransaction().commit();
				
				em.close();
			}
			if (id == 0) Main.menuPrincipal();
		}while(id != 0);
	}
	/**
	 * 
	 * @throws ErrorBBDDException
	 * @throws ParseException 
	 */
	private static void baja ()  {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();
		int id = -2;
		do {
			System.out.println("\n\tIntroduzca ID del Cliente ('-1' - ver listado, '0' - salir): ");
			id = Utils.getIntConsola(-1);
			if (id == -1) listado(true);
			
			else {
				TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente as c where c.cif = '"+ id+"'", Cliente.class);
				
				List<Cliente> clientes = q.getResultList();
				
				em.getTransaction().begin();
				for (Cliente cliEnLista : clientes) {
					em.remove(cliEnLista);
				}
				em.getTransaction().commit();
				em.close();
				System.out.println("El registro ha sido eliminado correctamente!");
			}
			if (id == 0) Main.menuPrincipal();
		}while(id != 0);
	}	

}
