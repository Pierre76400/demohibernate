package fr.softeam;

import java.util.HashMap;
import java.util.Map;

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
public class P6PbCache2 {

	@Autowired
	private ProfesseurDao professeurDao;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager = null;

	private EntityTransaction transaction = null;

	private Map<String, Classe> cache = new HashMap<>();

	@Test
	@SuppressWarnings("unchecked")
	public void problemePerformance() {
		String classeRecherchee = "classe1";
		Classe classe = null;

		openTransaction();
		classe = (Classe) entityManager.createQuery("from Classe c where c.nom='" + classeRecherchee + "'")
				.getSingleResult();
		closeTransaction();

		System.out.println("Classe : " + classe.getNom());

		openTransaction();
		classe = (Classe) entityManager.createQuery("from Classe c where c.nom='" + classeRecherchee + "'")
				.getSingleResult();
		System.out.println("Classe : " + classe.getNom());
		closeTransaction();
	}

	private void afficherClasse(Classe classe) {
		System.out.println("Classe : " + classe.getNom());
		for (Eleve e : classe.getEleves()) {
			System.out.print("Eleve = " + e.getId() + " " + e.getNom() + "  ");
		}

		System.out.println("");
		System.out.println("");
	}

	@Before
	public void init() {
		try {
			entityManager = entityManagerFactory.createEntityManager();
			transaction = entityManager.getTransaction();
			transaction.begin();

			int cptEleve = 0;
			for (int i = 0; i < 6; i++) {
				Classe c = new Classe();
				c.setNom("classe" + i);

				Professeur p = new Professeur();
				p.setNom("Professeur" + i);

				c.setProfesseur(p);

				// c.setEleves();
				for (int j = 0; j < 5; j++) {
					Eleve e = new Eleve();
					e.setNom("eleve" + cptEleve++);
					c.addEleve(e);
				}
				entityManager.persist(c);
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
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
	}

	@After
	public void end() {

		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.type")).setLevel(Level.ERROR);
	}

	private void closeTransaction() {
		transaction.commit();
		entityManager.close();
	}

	private void openTransaction() {
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}

	private Classe getOrPutInCache(String classeRecherchee) {
		Classe classe;
		if (!cache.containsKey(classeRecherchee)) {
			classe = (Classe) entityManager.createQuery("from Classe c where c.nom='" + classeRecherchee + "'")
					.getSingleResult();
			cache.put(classe.getNom(), classe);
		} else {
			classe = cache.get(classeRecherchee);
		}
		return classe;
	}
}
