package modelo;



import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.ventaDeCoches.Concesionario;


public class GestionConcesionario {

	public static void menu()  {
		Scanner sc;
		int opcion = -1;
		do {
			
				System.out.println("GESTION DE CONCESIONARIOS: "+
						"\n1.-Listado de Concesionarios."+
						"\n2.- Alta a un Concesionario."+
						"\n3.- Modificar un Concesionario."+
						"\n4.- Baja a un Concesionario."+					
						"\n Pulse 0 para salir");
				
					sc = new Scanner(System.in);
					opcion = sc.nextInt();
				switch (opcion) {
				case 1:
						listado();
					break;
				case 2:
						alta();
					break;
					
				case 3:
					modificacion();
					break;
					
				case 4:
					baja();
				
				case 0: Main.menuPrincipal(); 
				
				default: 
					if (opcion > 4 || opcion < 0) {
						System.out.println("Elija una de las opciones existentes...");
					}
				}
			
			
			
		}while (opcion != 0);

	}
	/**
	 * 
	 */	
	public static void listado() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		Query q = em.createNamedQuery("Concesionario.findAll");
		
		List<Concesionario> concesionarios = (List<Concesionario>) q.getResultList();
		
		for (Concesionario conc : concesionarios) {
			System.out.println("Concesionario: "+ conc.getId() + " CIF: "+ conc.getCif() + " nombre: " + conc.getNombre() + " localidad: " + conc.getLocalidad());
		}
		em.close();
		
	}
	
	private static void alta() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();

		Concesionario conc = new Concesionario();
		System.out.println("Introduzca 'Cif' del Concesionario:");
		conc.setCif(Utils.getStringConsola());
		System.out.print("\nIntroduzca 'Nombre' del Concesionario: ");
		conc.setNombre(Utils.getStringConsola());
		System.out.print("\nIntroduzca 'Localidad' del Concesionario: ");
		conc.setLocalidad(Utils.getStringConsola());
		
		em.getTransaction().begin();
		em.persist(conc);
		em.getTransaction().commit();
		
		TypedQuery<Concesionario> q = em.createQuery("SELECT c FROM Concesionario as c", Concesionario.class);
		
		List<Concesionario> concesionarios = q.getResultList();
		
		for (Concesionario concEnLista : concesionarios) {
			System.out.println("Concesionario: "+ conc.getId() + " CIF: "+ conc.getCif() + " nombre: " + conc.getNombre() + " localidad: " + conc.getLocalidad());
		}
		
		em.close();
	}

	/**
	 * 
	 * @throws ErrorBBDDException
	 */
	private static void modificacion(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();
		int id = -2;
		do {
			System.out.println("\n\tIntroduzca ID del Concesionario ('-1' - ver listado, '0' - salir): ");
			id = Utils.getIntConsola(-1);
			
			if (id == -1) listado();
			
			else {
				TypedQuery<Concesionario> q = em.createQuery("SELECT c FROM Concesionario as c where c.id ='"+id+"'", Concesionario.class);
				
				List<Concesionario> concesionarios = q.getResultList();
				
				em.getTransaction().begin();
				for (Concesionario concEnLista : concesionarios) {
					System.out.println("Introduzca 'CIF' para el Concesionario");
					concEnLista.setCif(Utils.getStringConsola());
					System.out.println("Introduzca 'nombre' para el Concesionario");
					concEnLista.setNombre(Utils.getStringConsola());
					System.out.println("Introduzca 'localidad' para el Concesionario");
					concEnLista.setLocalidad(Utils.getStringConsola());
					
					em.persist(concEnLista);
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
	 */
	private static void baja()  {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();
		int id = -2;
		do {
			System.out.println("\n\tIntroduzca ID del Concesionario ('-1' - ver listado, '0' - salir): ");
			id = Utils.getIntConsola(-1);
			if (id == -1) listado();
			
			else {
				TypedQuery<Concesionario> q = em.createQuery("SELECT c FROM Concesionario as c where c.cif = '"+ id+"'", Concesionario.class);
				
				List<Concesionario> concesionarios = q.getResultList();
				
				em.getTransaction().begin();
				for (Concesionario concEnLista : concesionarios) {
					em.remove(concEnLista);
				}
				em.getTransaction().commit();
				em.close();
				System.out.println("El registro ha sido eliminado correctamente!");
			}
			if (id == 0) Main.menuPrincipal();
		}while(id != 0);
	}

}
