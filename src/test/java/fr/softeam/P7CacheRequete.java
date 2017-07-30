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
import fr.softeam.model.variante.ClasseP7;
import fr.softeam.model.variante.EleveP7;
import fr.softeam.model.variante.ProfesseurP7;

@RunWith(SpringRunner.class)
@SpringBootTest
public class P7CacheRequete {

	@Autowired
	private ProfesseurDao professeurDao;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager = null;

	private EntityTransaction transaction = null;

	private ClasseP7 classe = null;

	@Test
	@SuppressWarnings("unchecked")
	public void requetePeuEfficace() {

		openTransaction();
		classe = (ClasseP7) entityManager.createQuery("from ClasseP7 c where c.nom='classe1'").getSingleResult();
		closeTransaction();
		System.out.println("Classe : " + classe.getNom());

		openTransaction();
		classe = (ClasseP7) entityManager.createQuery("from ClasseP7 c where c.nom='classe1'").getSingleResult();
		closeTransaction();
		System.out.println("Classe : " + classe.getNom());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void requetePeuEfficaceAvecOneToMany() {

		openTransaction();
		classe = (ClasseP7) entityManager
				.createQuery(
						"select distinct c from ClasseP7 c" + " join fetch c.eleves"
								+ " join fetch c.professeur where c.nom='classe1'")
				.setHint("org.hibernate.cacheable", Boolean.TRUE).getSingleResult();
		closeTransaction();
		afficherClasse(classe);

		openTransaction();
		classe = (ClasseP7) entityManager
				.createQuery(
						"select distinct c from ClasseP7 c" + " join fetch c.eleves"
								+ " join fetch c.professeur where c.nom='classe1'")
				.setHint("org.hibernate.cacheable", Boolean.TRUE).getSingleResult();
		afficherClasse(classe);
		closeTransaction();
	}

	private void afficherClasse(ClasseP7 classe) {
		System.out.println("Classe : " + classe.getNom());
		for (EleveP7 e : classe.getEleves()) {
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
				ClasseP7 c = new ClasseP7();
				c.setNom("classe" + i);

				ProfesseurP7 p = new ProfesseurP7();
				p.setNom("Professeur" + i);

				c.setProfesseur(p);

				// c.setEleves();
				for (int j = 0; j < 5; j++) {
					EleveP7 e = new EleveP7();
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

}
