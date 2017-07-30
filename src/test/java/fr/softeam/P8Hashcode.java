package fr.softeam;

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
public class P8Hashcode {

	private EntityManager entityManager = null;
	private EntityTransaction transaction = null;
	private Classe classe1 = null;
	private Classe classe2 = null;
	private Eleve eleve1 = null;
	private Eleve eleve2;

	@Autowired
	private ProfesseurDao professeurDao;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniSansProbleme() {

		createClasseCM1AvecEleveRobertDansUneTransactionIsole();

		openNewTransaction();

		classe2 = getClasseCm1();
		eleve2 = getEleveRobert();

		System.out.println("On ajoute eleve2 à classe2");
		classe2.addEleve(eleve2);

		afficherClasse(classe2, "classe2");

		System.out.println("L'éléve Robert est bien présent qu'une seule fois");

		closeTransaction();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme() {
		classe1 = createClasseCM1AvecEleveRobertDansUneTransactionIsole();

		openNewTransaction();

		classe2 = getClasseCm1();
		eleve2 = getEleveRobert();

		if (!classe1.getEleves().contains(eleve2)) {
			System.out.println("classe 1 ne contient pas eleve2");
		}
		System.out.println("");

		System.out.println("On retire eleve1 de classe2");
		classe2.removeEleve(eleve1);
		afficherClasse(classe2, "classe2");
		System.out.println("L'éléve Robert est toujours présent dans classe2");

		System.out.println("On ajoute eleve1 à classe2");
		classe2.addEleve(eleve1);
		afficherClasse(classe2, "classe2");
		System.out.println("L'éléve Robert est présent deux fois dans classe2");
		closeTransaction();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme_trace() {
		createClasseCM1AvecEleveRobertDansUneTransactionIsole();

		openNewTransaction();

		classe2 = getClasseCm1();
		eleve2 = getEleveRobert();

		System.out.println("Eleve1 " + eleve1.hashCode());
		System.out.println("Eleve2 " + eleve2.hashCode());
		System.out.println("");

		System.out.println("On ajoute eleve1 à classe2");
		classe2.addEleve(eleve1);
		System.out.println("Liste des éléves de classe2");
		for (Eleve pro : classe2.getEleves()) {
			System.out.println(" - " + pro.getNom() + " " + pro.hashCode());
		}
		closeTransaction();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme_unesolution() {
		createClasseCM1AvecEleveRobertDansUneTransactionIsole();

		openNewTransaction();

		classe2 = getClasseCm1();
		eleve2 = getEleveRobert();

		System.out.println("Eleve1 " + eleve1.hashCode());
		System.out.println("Eleve2 " + eleve2.hashCode());
		eleve1 = entityManager.merge(eleve1);
		System.out.println("Eleve1 aprés merge " + eleve1.hashCode() + "\n");

		System.out.println("On retire eleve1 de classe2");
		classe2.removeEleve(eleve1);
		afficherClasse(classe2, "classe2");
		System.out.println("Il n'y a plus d'éléve dans classe2 :)\n");

		System.out.println("On ajoute eleve1 et eleve2 à classe2");
		classe2.addEleve(eleve1);
		classe2.addEleve(eleve2);
		afficherClasse(classe2, "classe2");
		System.out.println("Il n'y a qu'un éléve Robert dans classe2 :)");

		if (!classe1.getEleves().contains(eleve2)) {
			System.out.println("classe 1 ne contient pas eleve2 :(");
		}

		entityManager.persist(classe2);
		closeTransaction();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeNonRedefiniAvecProbleme_autreProbleme() {
		createClasseCM1AvecEleveRobertDansUneTransactionIsole();

		openNewTransaction();

		classe2 = getClasseCm1();

		eleve2 = new Eleve();
		eleve2.setNom("Robert");

		System.out.println("On ajoute eleve2 à classe2");
		classe2.addEleve(eleve2);
		afficherClasse(classe2, "classe2");

		entityManager.persist(classe2);
		closeTransaction();
	}

	@Test
	@SuppressWarnings("unchecked")
	// Ici le hash code a été défini avec l'id technique
	public void hashCodeRedefini1() {
		createClasseCM1AvecEleveRobertDansUneTransactionIsole();

		openNewTransaction();

		classe2 = getClasseCm1();
		eleve2 = getEleveRobert();

		System.out.println("Eleve1 " + eleve1.hashCode());
		System.out.println("Eleve2 " + eleve2.hashCode());
		System.out.println("");

		System.out.println("On ajoute eleve1 à classe2");
		classe2.addEleve(eleve1);
		System.out.println("Liste des éléves de classe2");
		for (Eleve pro : classe2.getEleves()) {
			System.out.println(" - " + pro.getNom() + " " + pro.hashCode());
		}
		closeTransaction();
	}

	@Test
	@SuppressWarnings("unchecked")
	// 1er lancement avec hashcode sur idtechnique => Erreur
	// 2éme lancement avec hashcode sur nom
	public void hashCodeRedefini2() {
		createClasseCM1AvecEleveRobertDansUneTransactionIsole();

		openNewTransaction();
		classe2 = getClasseCm1();
		eleve2 = new Eleve();
		eleve2.setNom("Robert");

		System.out.println("On ajoute une nouvelle instance de l'éléve Robert à classe2");
		classe2.addEleve(eleve2);
		afficherClasse(classe2, "classe2");

		Eleve eleve3 = new Eleve();
		eleve3.setNom("René");

		System.out.println("On ajoute un nouvel éléve René à classe2");
		classe2.addEleve(eleve3);
		afficherClasse(classe2, "classe2");

		entityManager.persist(classe2);
		closeTransaction();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hashCodeRedefini66() {
		createClasseCM1AvecEleveRobertDansUneTransactionIsole();

		try {
			openNewTransaction();
			classe2 = getClasseCm1();
			eleve2 = new Eleve();
			eleve2.setNom("Robert");

			System.out.println(eleve2.equals(classe2.getEleves().iterator().next()));
			System.out.println(classe2.getEleves().contains(eleve2));

			/*
			 * System.out.println(
			 * "On ajoute une nouvelle instance de l'éléve Robert à classe2");
			 * classe2.addEleve(eleve2); afficherClasse(classe2, "classe2");
			 * 
			 * Eleve eleve3 = new Eleve(); eleve3.setNom("René");
			 * 
			 * System.out.println("On ajoute un nouvel éléve René à classe2");
			 * classe2.addEleve(eleve3); afficherClasse(classe2, "classe2");
			 * 
			 * entityManager.persist(classe2); transaction.commit();
			 */
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
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
	}

	@After
	public void end() {

		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.type")).setLevel(Level.ERROR);
	}

	private Classe createClasseCM1AvecEleveRobertDansUneTransactionIsole() {
		System.out.println("*Init du jeu de test");
		try {
			openNewTransaction();

			classe1 = new Classe();
			classe1.setNom("Classe CM1");

			eleve1 = new Eleve();
			eleve1.setNom("Robert");

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

		afficherClasse(classe1, "classe1");
		System.out.println("*Fin de transaction");
		System.out.println("");

		return classe1;
	}

	private Eleve getEleveRobert() {
		System.out.println("On récupére dans l'instance eleve2, l'éléve qui a pour nom 'Robert'");
		System.out.println("");
		return (Eleve) entityManager.createQuery("from Eleve e where e.nom='Robert'").getSingleResult();
	}

	private Classe getClasseCm1() {
		System.out.println("On récupére dans l'instance classe2, la classe qui a pour nom 'Classe CM1'");
		return (Classe) entityManager.createQuery("from Classe c where c.nom='Classe CM1'").getSingleResult();
	}

	private void afficherClasse(Classe classe, String nomInstance) {
		System.out.println("Liste des éléves de la classe " + classe.getNom() + " instance=" + nomInstance);
		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
		System.out.println("");
	}

	private void openNewTransaction() {
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
		System.out.println("*Début de transaction");
	}

	private void closeTransaction() {
		transaction.commit();
		entityManager.close();
	}
}
