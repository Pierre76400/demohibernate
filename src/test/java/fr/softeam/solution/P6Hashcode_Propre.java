package fr.softeam.solution;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.qos.logback.classic.Level;
import fr.softeam.dao.ProfesseurDao;
import fr.softeam.model.Classe;
import fr.softeam.model.Eleve;

@RunWith(SpringRunner.class)
@SpringBootTest
public class P6Hashcode_Propre {

	@Autowired
	private ProfesseurDao professeurDao;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniSansProbleme() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			System.out.println("On ajoute eleve2 à classe2");
			classe2.addEleve(eleve2);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom());
			}

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			if (classe1.getEleves().contains(eleve2)) {
				System.out.println("classe 1 contient eleve2");
			} else {
				System.out.println("classe 1 ne contient  pas eleve2");
			}
			System.out.println("");

			System.out.println("On retire eleve1 de classe2");
			classe2.removeEleve(eleve1);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom());
			}
			System.out.println("");

			System.out.println("On ajoute eleve1 à classe2");
			classe2.addEleve(eleve1);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom());
			}
			entityManager.persist(classe2);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme_trace() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			System.out.println("Eleve1 " + eleve1);
			System.out.println("Eleve2 " + eleve2);
			System.out.println("");

			if (classe1.getEleves().contains(eleve2)) {
				System.out.println("classe 1 contient eleve2");
			} else {
				System.out.println("classe 1 ne contient  pas eleve2");
			}
			System.out.println("");

			System.out.println("On retire eleve1 de classe2");
			classe2.removeEleve(eleve1);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}
			System.out.println("");

			System.out.println("On ajoute eleve1 à classe2");
			classe2.addEleve(eleve1);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}
			entityManager.persist(classe2);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme_unesolution() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			System.out.println("Eleve1 " + eleve1);
			System.out.println("Eleve2 " + eleve2);
			System.out.println("");
			eleve1 = entityManager.merge(eleve1);
			System.out.println("Eleve1 aprés merge " + eleve1);

			if (classe1.getEleves().contains(eleve2)) {
				System.out.println("classe 1 contient eleve2");
			} else {
				System.out.println("classe 1 ne contient  pas eleve2");
			}
			System.out.println("");

			System.out.println("On retire eleve1 de classe2");
			classe2.removeEleve(eleve1);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}
			System.out.println("");

			System.out.println("On ajoute eleve1 à classe2");
			classe2.addEleve(eleve1);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}
			entityManager.persist(classe2);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme_autreProbleme() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			eleve2 = new Eleve();
			eleve2.setNom("eleve0");

			System.out.println("On ajoute eleve2 à classe2");
			classe2.addEleve(eleve2);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}
			entityManager.persist(classe2);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	// Redéfinir hashcode avec eclipse
	public void hashCodeRedefini1() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);
			entityManager.clear();
			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			classe2.addEleve(eleve1);
			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}

			if (classe2.getEleves().contains(eleve1)) {
				System.out.println("classe 1 contient eleve2");
			} else {
				System.out.println("classe 1 ne contient  pas eleve2");
			}
			System.out.println("");

			System.out.println("On retire eleve1 de classe2");
			classe2.removeEleve(eleve1);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}
			System.out.println("");

			System.out.println("On ajoute eleve1 à classe2");
			classe2.addEleve(eleve1);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}
			entityManager.persist(classe2);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	// Ici le hash code a été défini avec l'id technique
	public void hashCodeRedefini2() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			eleve2 = new Eleve();
			eleve2.setNom("eleve0");

			System.out.println("On ajoute eleve2 à classe2");
			classe2.addEleve(eleve2);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}

			Eleve eleve3 = new Eleve();
			eleve3.setNom("eleve3");

			System.out.println("On ajoute un nouvelle éléve à classe2");
			classe2.addEleve(eleve3);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}

			entityManager.persist(classe2);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	// Ici le hash code a été défini avec le nom
	public void hashCodeRedefini3() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			eleve2 = new Eleve();
			eleve2.setNom("eleve0");

			System.out.println("On ajoute eleve2 à classe2");
			classe2.addEleve(eleve2);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}

			Eleve eleve3 = new Eleve();
			eleve3.setNom("eleve3");

			System.out.println("On ajoute un nouvelle éléve à classe2");
			classe2.addEleve(eleve3);

			System.out.println("Liste des éléves de classe2");
			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom() + " " + pro);
			}

			entityManager.persist(classe2);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	// Ici le hash code a été défini avec le nom
	public void hashCodeRedefini4() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe1 = null;
		Eleve eleve1 = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe1 = new Classe();
			classe1.setNom("classe0");

			eleve1 = new Eleve();
			eleve1.setNom("eleve0");

			classe1.addEleve(eleve1);
			entityManager.persist(classe1);
			entityManager.merge(classe1);
			entityManager.merge(eleve1);

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe classe2 = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'")
					.getSingleResult();

			if (classe2.isEleveExist(eleve1)) {
				System.out.println("On ajoute eleve2 à classe2");
			}

			transaction.commit();
		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Before
	public void init() {
		/*
		 * EntityManager entityManager = null; EntityTransaction transaction =
		 * null; try { entityManager =
		 * entityManagerFactory.createEntityManager(); transaction =
		 * entityManager.getTransaction(); transaction.begin();
		 * 
		 * Classe c = new Classe(); c.setNom("classe0");
		 * 
		 * Professeur p = new Professeur(); p.setNom("Professeur0");
		 * 
		 * c.setProfesseur(p);
		 * 
		 * entityManager.persist(c);
		 * 
		 * Eleve e = new Eleve(); e.setNom("eleve0"); entityManager.persist(e);
		 * 
		 * transaction.commit(); } catch (Throwable e) { if (transaction != null
		 * && transaction.isActive()) transaction.rollback(); throw e; } finally
		 * { if (entityManager != null) { entityManager.close(); } }
		 */
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
	}

	@After
	public void end() {

		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.type")).setLevel(Level.ERROR);
	}
}
