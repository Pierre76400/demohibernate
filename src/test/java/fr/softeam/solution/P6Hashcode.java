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
import fr.softeam.model.Professeur;

@RunWith(SpringRunner.class)
@SpringBootTest
public class P6Hashcode {

	@Autowired
	private ProfesseurDao professeurDao;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniSansProbleme() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe = null;
		Eleve eleve = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'").getSingleResult();

			eleve = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();
			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			classe.addEleve(eleve);
			classe.addEleve(eleve2);

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

		System.out.println("Liste des éléves de la classe0 : ");

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe = null;
		Eleve eleve = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'").getSingleResult();

			eleve = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();
			classe.addEleve(eleve);

			entityManager.persist(classe);
			transaction.commit();
			entityManager.clear();
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

			classe = entityManager.merge(classe);
			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			/*
			 * System.out.println("Pro Liste des éléves de la classe0 : ");
			 * 
			 * for (Eleve pro : classe.getEleves()) { System.out.println(" - " +
			 * pro.getNom()); }
			 */

			if (eleve2.equals(eleve)) {
				System.out.println("eleve2 est égal à eleve");
			}

			classe.addEleve(eleve2);

			eleve = entityManager.merge(eleve);

			entityManager.persist(classe);

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

		System.out.println("Liste des éléves de la classe0 : ");

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme2() {

		EntityManager entityManager = null;
		EntityManager entityManager2 = null;
		EntityTransaction transaction = null;
		EntityTransaction transaction2 = null;
		Classe classe = null;
		Eleve eleve = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager2 = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();
			transaction2 = entityManager2.getTransaction();
			transaction2.begin();

			classe = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'").getSingleResult();

			eleve = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();
			classe.addEleve(eleve);

			eleve2 = (Eleve) entityManager2.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();
			classe.addEleve(eleve2);

			System.out.println("Liste des éléves de la classe0 : ");

			for (Eleve pro : classe.getEleves()) {
				System.out.println(" - " + pro.getNom());
			}
			entityManager.persist(classe);
			transaction.commit();
			entityManager.clear();
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

			classe = entityManager.merge(classe);
			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			/*
			 * System.out.println("Pro Liste des éléves de la classe0 : ");
			 * 
			 * for (Eleve pro : classe.getEleves()) { System.out.println(" - " +
			 * pro.getNom()); }
			 */

			if (eleve2.equals(eleve)) {
				System.out.println("eleve2 est égal à eleve");
			}

			classe.addEleve(eleve2);

			eleve = entityManager.merge(eleve);

			entityManager.persist(classe);

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

		System.out.println("Liste des éléves de la classe0 : ");

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme3() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe = null;
		Eleve eleve = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'").getSingleResult();

			eleve = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();
			eleve2 = new Eleve();
			eleve2.setNom("eleve0");

			classe.addEleve(eleve);
			classe.addEleve(eleve2);

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

		System.out.println("Liste des éléves de la classe0 : ");

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme4() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe = null;
		Eleve eleve = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'").getSingleResult();

			eleve = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			classe.addEleve(eleve);
			entityManager.persist(classe);
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
			System.out.println("eleve=" + classe.getEleves().iterator().next().toString());
			classe = entityManager.merge(classe);

			System.out.println("eleve=" + classe.getEleves().iterator().next().toString());

			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			System.out.println("eleve2=" + classe.getEleves().iterator().next().toString());
			/*
			 * System.out.println("Pro Liste des éléves de la classe0 : ");
			 * 
			 * for (Eleve pro : classe.getEleves()) { System.out.println(" - " +
			 * pro.getNom()); }
			 */

			if (eleve2.equals(eleve)) {
				System.out.println("eleve2 est égal à eleve");
			}

			classe.addEleve(eleve2);

			eleve = entityManager.merge(eleve);

			entityManager.persist(classe);

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

		System.out.println("Liste des éléves de la classe0 : ");

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme5() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe = null;
		Eleve eleve = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'").getSingleResult();

			eleve = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();
			System.out.println("eleve=" + eleve);
			classe.addEleve(eleve);
			entityManager.persist(classe);
			entityManager.clear();
			transaction.commit();

			transaction = entityManager.getTransaction();
			transaction.begin();

			eleve2 = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();
			System.out.println("eleve2=" + eleve2);
			classe.addEleve(eleve2);
			entityManager.persist(classe);

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

		System.out.println("Liste des éléves de la classe0 : ");

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme6() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe = null;
		Eleve eleve = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();

			Classe c = new Classe();
			c.setNom("classe1");

			Eleve e = new Eleve();
			e.setNom("eleve1");

			c.addEleve(e);

			entityManager.persist(e);
			entityManager.persist(c);
			entityManager.merge(c);
			// entityManager.clear();
			/*
			 * System.out.println("eleve=" + eleve); classe.addEleve(eleve);
			 * entityManager.persist(c); entityManager.clear();
			 * transaction.commit();
			 */

			EntityManager entityManager2 = entityManagerFactory.createEntityManager();

			Classe c2 = entityManager2.find(Classe.class, c.getId());
			Eleve e2 = entityManager2.find(Eleve.class, e.getId());

			boolean contains = c.getEleves().contains(e2);

			c2.addEleve(e2);

			c2.removeEleve(e2);
			entityManager2.merge(c2);

		} catch (Throwable e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		System.out.println("Liste des éléves de la classe0 : ");

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme10() {

		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		Classe classe = null;
		Eleve eleve = null;
		Eleve eleve2 = null;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			classe = (Classe) entityManager.createQuery("from Classe c where c.nom='classe0'").getSingleResult();

			eleve = (Eleve) entityManager.createQuery("from Eleve e where e.nom='eleve0'").getSingleResult();

			classe.addEleve(eleve);
			entityManager.persist(classe);
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

			if (classe.getEleves().contains(eleve2)) {
				System.out.println("classe 1 contient eleve2");
			} else {
				System.out.println("classe 1 ne contient  pas eleve2");
			}

			classe.addEleve(eleve2);

			for (Eleve pro : classe.getEleves()) {
				System.out.println(" - " + pro.getNom());
			}

			classe2.removeEleve(eleve);

			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom());
			}
			classe2.addEleve(eleve);

			for (Eleve pro : classe2.getEleves()) {
				System.out.println(" - " + pro.getNom());
			}
			entityManager.persist(classe);
			entityManager.persist(classe2);

			/*
			 * p2.getChilds.add(c2);//problem2:childs contains c1 and c2 boolean
			 * remove=p2.getChilds.remove(c2);//problem3:remove==false
			 * entityManager1.merge(p2);//problem4: hibernate will deal with c2
			 * 
			 * 
			 * eleve = entityManager.merge(eleve);
			 * 
			 * entityManager.persist(classe);
			 */
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

		System.out.println("Liste des éléves de la classe0 : ");

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Before
	public void init() {
		EntityManager entityManager = null;
		EntityTransaction transaction = null;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			Classe c = new Classe();
			c.setNom("classe0");

			Professeur p = new Professeur();
			p.setNom("Professeur0");

			c.setProfesseur(p);

			entityManager.persist(c);

			Eleve e = new Eleve();
			e.setNom("eleve0");
			entityManager.persist(e);

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
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
	}

	@After
	public void end() {

		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.type")).setLevel(Level.ERROR);
	}
}
