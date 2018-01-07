package fr.softeam;

import java.util.HashSet;

import javax.transaction.Transactional;

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
import fr.softeam.util.AbstractCommonLanceurTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class P4BatchInsertTest extends AbstractCommonLanceurTest {

	@Autowired
	private ProfesseurDao professeurDao;

	private long chrono;

	private int cptEleve = 0;

	@Test
	@SuppressWarnings("unchecked")
	public void insertionDeTailleMoyenneTest() {
		for (int i = 0; i < 10000; i++) {
			Classe c = creerClasseAvecProfesseurEtEleves(i);
			getEntityManager().persist(c);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void insertionMassiveTest() {
		for (int i = 0; i < 100000; i++) {
			Classe c = creerClasseAvecProfesseurEtEleves(i);
			getEntityManager().persist(c);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void insertionMassiveTest_explicationCache() {
		Classe c = creerClasseAvecProfesseurEtEleves(0);
		getEntityManager().persist(c);
		getEntityManager().flush();

		getEntityManager().refresh(c);
		long idProf = c.getProfesseur().getId();
		getEntityManager().clear();

		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);

		System.out.println("1ére requête :");

		getEntityManager().find(Professeur.class, idProf);

		getEntityManager().find(Professeur.class, idProf);
		System.out.println("2éme requête avec un clear avant :");

		getEntityManager().clear();
		getEntityManager().find(Professeur.class, idProf);

		System.out.println("Le cache 1er ou 2éme niveau ne marche que lorsqu'on utilise l'id de l'entity");
		getEntityManager().createQuery("from Professeur p where p.nom='Professeur1'").getResultList();
		getEntityManager().createQuery("from Professeur p where p.nom='Professeur1'").getResultList();
	}

	@Override
	public void init() {
		chrono = System.currentTimeMillis();
	}

	@Override
	public void end() {
		long temps = System.currentTimeMillis() - chrono;
		System.out.println("Tps insertion des classes : " + temps + " ms");
		super.end();
	}

	private Classe creerClasseAvecProfesseurEtEleves(int cptClasse) {

		Classe c = new Classe();
		c.setNom("classe" + cptClasse);

		Professeur p = new Professeur();
		p.setNom("Professeur" + cptClasse);

		c.setProfesseur(p);

		c.setEleves(new HashSet<Eleve>());
		for (int j = 0; j < 25; j++) {
			Eleve e = new Eleve();
			e.setNom("eleve" + cptEleve++);
			c.addEleve(e);
		}
		return c;
	}

}
