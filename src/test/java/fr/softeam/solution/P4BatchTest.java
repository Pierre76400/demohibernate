package fr.softeam.solution;

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
public class P4BatchTest extends AbstractCommonLanceurTest {

	@Autowired
	private ProfesseurDao professeurDao;

	@Test
	@SuppressWarnings("unchecked")
	public void insertionUnPeuMassiveTest() {
		long chrono = System.currentTimeMillis();
		int cptEleve = 0;
		for (int i = 0; i < 10000; i++) {
			Classe c = new Classe();
			c.setNom("classe" + i);

			Professeur p = new Professeur();
			p.setNom("Professeur" + i);

			c.setProfesseur(p);

			c.setEleves(new HashSet<Eleve>());
			for (int j = 0; j < 25; j++) {
				Eleve e = new Eleve();
				e.setNom("eleve" + cptEleve++);
				c.addEleve(e);
			}
			getEntityManager().persist(c);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void insertionMassiveTest() {
		long chrono = System.currentTimeMillis();
		int cptEleve = 0;
		for (int i = 0; i < 100000; i++) {
			Classe c = new Classe();
			c.setNom("classe" + i);

			Professeur p = new Professeur();
			p.setNom("Professeur" + i);

			c.setProfesseur(p);

			c.setEleves(new HashSet<Eleve>());
			for (int j = 0; j < 25; j++) {
				Eleve e = new Eleve();
				e.setNom("eleve" + cptEleve++);
				c.addEleve(e);
			}
			getEntityManager().persist(c);
		}

		long temps = System.currentTimeMillis() - chrono;
		System.out.println("Tps insertion des professeurs : " + temps + " ms");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void insertionMassiveTest_avecTrace() {
		long chrono = System.currentTimeMillis();
		int cptEleve = 0;
		for (int i = 0; i < 100000; i++) {
			Classe c = new Classe();
			c.setNom("classe" + i);

			Professeur p = new Professeur();
			p.setNom("Professeur" + i);

			c.setProfesseur(p);

			c.setEleves(new HashSet<Eleve>());
			for (int j = 0; j < 25; j++) {
				Eleve e = new Eleve();
				e.setNom("eleve" + cptEleve++);
				c.addEleve(e);
			}
			getEntityManager().persist(c);

			if (i % 10000 == 0) {
				System.out.println(i + " Tps insertion : " + (System.currentTimeMillis() - chrono) + " ms");
			}
		}

		long temps = System.currentTimeMillis() - chrono;
		System.out.println("Tps insertion des professeurs : " + temps + " ms");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void insertionMassiveTest_solution() {
		long chrono = System.currentTimeMillis();
		int cptEleve = 0;
		for (int i = 0; i < 100000; i++) {
			Classe c = new Classe();
			c.setNom("classe" + i);

			Professeur p = new Professeur();
			p.setNom("Professeur" + i);

			c.setProfesseur(p);

			c.setEleves(new HashSet<Eleve>());
			for (int j = 0; j < 25; j++) {
				Eleve e = new Eleve();
				e.setNom("eleve" + cptEleve++);
				c.addEleve(e);
			}
			getEntityManager().persist(c);

			if (i % 10000 == 0) {
				System.out.println(i + " Tps insertion : " + (System.currentTimeMillis() - chrono) + " ms");

				getEntityManager().clear();
			}
		}

		long temps = System.currentTimeMillis() - chrono;
		System.out.println("Tps insertion des professeurs : " + temps + " ms");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void insertionMassiveTest_explication() {
		int cptEleve = 0;
		long idProf = 0;

		for (int i = 0; i < 1; i++) {
			Classe c = new Classe();
			c.setNom("classe" + i);

			Professeur p = new Professeur();
			p.setNom("Professeur" + i);

			c.setProfesseur(p);

			c.setEleves(new HashSet<Eleve>());
			for (int j = 0; j < 25; j++) {
				Eleve e = new Eleve();
				e.setNom("eleve" + cptEleve++);
				c.addEleve(e);
			}
			getEntityManager().persist(c);
			idProf = c.getId();
		}

		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);

		System.out.println("1ére requête :");

		Professeur prof1 = (Professeur) getEntityManager().find(Professeur.class, idProf);

		System.out.println("2éme requête avec un clear avant :");

		getEntityManager().clear();
		prof1 = (Professeur) getEntityManager().find(Professeur.class, idProf);
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);

	}

	@Override
	public void init() {

	}
}
