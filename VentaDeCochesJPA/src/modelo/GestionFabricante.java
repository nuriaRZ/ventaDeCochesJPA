package modelo;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modelo.ventaDeCoches.Fabricante;
import modelo.ventaDeCoches.controladores.ErrorBBDDException;
import modelo.Utils;




public class GestionFabricante {

	/**
	 * @throws ParseException 
	 * 
	 */
	public static void menuGestion() throws ParseException, ErrorBBDDException {

		int opcionElegida = -1;
		do {
			System.out.println("\n\t\t\tGESTIÓN DE FABRICANTES");
			
			System.out.println("\n\t1.- Listado de fabricantes.");
			System.out.println("\t2.- Alta de fabricante.");
			System.out.println("\t3.- Modificación de fabricante.");
			System.out.println("\t4.- Baja de fabricante.");
			System.out.println("\t0.- Salir");
			System.out.println("\n\tElija una opción: ");
			
			opcionElegida = Utils.getIntConsola(0, 4);
			
			switch (opcionElegida) {
			case 0:
				
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
				break;
			}
		} while (opcionElegida != 0);
	}
	
	
	private static void listado() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		Query q = em.createNamedQuery("Fabricante.findAll");
		
		List<Fabricante> fabricantes = (List<Fabricante>) q.getResultList();
		
		for (Fabricante fab : fabricantes) {
			System.out.println("Fabricante: "+ fab.getId() + " CIF: "+ fab.getCif() + " nombre: " + fab.getNombre());
		}
		em.close();
	}
	
	private static void alta() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();

		Fabricante fab = new Fabricante();
		System.out.println("Introduzca 'Cif' del fabricante:");
		fab.setCif(Utils.getStringConsola());
		System.out.print("\nIntroduzca 'Nombre' del fabricante: ");
		fab.setNombre(Utils.getStringConsola());
		
		em.getTransaction().begin();
		em.persist(fab);
		em.getTransaction().commit();
		
		TypedQuery<Fabricante> q = em.createQuery("SELECT f FROM Fabricante as f", Fabricante.class);
		
		List<Fabricante> fabricantes = q.getResultList();
		
		for (Fabricante fabEnLista : fabricantes) {
			System.out.println("Fabricante: " + fabEnLista.getId() + " CIF: " + fabEnLista.getCif() + " Nombre: " + fabEnLista.getNombre());
		}
		
		em.close();
	}
	
	private static void modificacion() throws ParseException, ErrorBBDDException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();
		int id = -2;
		do {
			System.out.println("\n\tIntroduzca ID del fabricante ('-1' - ver listado, '0' - salir): ");
			id = Utils.getIntConsola(-1);
			if (id == -1) listado();
			if (id == 0) Main.menuPrincipal();
			else {
				TypedQuery<Fabricante> q = em.createQuery("SELECT f FROM Fabricante as f where f.id = "+ id, Fabricante.class);
				
				List<Fabricante> fabricantes = q.getResultList();
				
				em.getTransaction().begin();
				for (Fabricante fabEnLista : fabricantes) {
					fabEnLista.setNombre("Modificado");
					em.persist(fabEnLista);
				}
				em.getTransaction().commit();
				
				em.close();
			}
		}while(id != 0);
		
		
		

		
		
	}
	
	private static void baja() throws ParseException, ErrorBBDDException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VentaDeCoches");

		EntityManager em = entityManagerFactory.createEntityManager();
		int id = -2;
		do {
			System.out.println("\n\tIntroduzca ID del fabricante ('-1' - ver listado, '0' - salir): ");
			id = Utils.getIntConsola(-1);
			if (id == -1) listado();
			if (id == 0) Main.menuPrincipal();
			else {
				TypedQuery<Fabricante> q = em.createQuery("SELECT f FROM Fabricante as f where f.cif = '"+ id+"'", Fabricante.class);
				
				List<Fabricante> fabricantes = q.getResultList();
				
				em.getTransaction().begin();
				for (Fabricante fabEnLista : fabricantes) {
					em.remove(fabEnLista);
				}
				em.getTransaction().commit();
				
				
				em.close();
			}
		}while(id != 0);
		
		
	}
	
	
	
	

	

}
