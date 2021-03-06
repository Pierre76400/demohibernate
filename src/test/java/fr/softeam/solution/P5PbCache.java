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
public class P5PbCache extends AbstractCommonLanceurTest {

	@Autowired
	private ProfesseurDao professeurDao;

	@Test
	public void requeteDeleteSql() {

		int nbDelete = getEntityManager().createNativeQuery("delete from eleve").executeUpdate();
		System.out.println("Nb d'éléves supprimés : " + nbDelete);

		Eleve e = getEntityManager().find(Eleve.class, 1l);

		if (e != null) {
			System.out.println("Eleve avec l'id 1 trouvé");
		} else {
			System.out.println("Eleve avec l'id 1 pas trouvé");
		}
	}

	@Test
	public void requeteDeleteSql_solution() {

		int nbDelete = getEntityManager().createNativeQuery("delete from eleve").executeUpdate();
		System.out.println("Nb d'éléves supprimés : " + nbDelete);

		getEntityManager().clear();

		Eleve e = getEntityManager().find(Eleve.class, 1l);

		if (e != null) {
			System.out.println("Eleve avec l'id 1 trouvé");
		} else {

			System.out.println("Eleve avec l'id 1 pas trouvé");
		}
	}

	@Test
	public void requeteUpdateSql() {

		Eleve e = getEntityManager().find(Eleve.class, 1l);

		System.out.println("Nom de l'éléve avant requête SQL : " + e.getNom());

		getEntityManager().createNativeQuery("update eleve set nom='Dupond' where id=1").executeUpdate();

		System.out.println("Nom de l'éléve aprés requête SQL : " + e.getNom());
	}

	@Test
	public void requeteUpdateSql_solution() {

		Eleve e = getEntityManager().find(Eleve.class, 1l);

		System.out.println("Nom de l'éléve avant requête SQL : " + e.getNom());

		getEntityManager().createNativeQuery("update eleve set nom='Dupond' where id=1").executeUpdate();
		getEntityManager().refresh(e);
		System.out.println("Nom de l'éléve aprés requête SQL : " + e.getNom());
	}

	@Test
	public void requeteUpdateSql2() {

		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
		Eleve e = getEntityManager().find(Eleve.class, 1l);
		e.setNom("Durand");
		getEntityManager().persist(e);
		System.out.println("Nom de l'éléve avant requête SQL : " + e.getNom());

		getEntityManager().createNativeQuery("update eleve set nom='Dupond' where id=1").executeUpdate();
		getEntityManager().flush();
	}

	@Test
	public void requeteUpdateSql2_Explication() {

		Eleve e = getEntityManager().find(Eleve.class, 1l);
		e.setNom("Durand");
		getEntityManager().persist(e);
		System.out.println("Nom de l'éléve avant requête SQL : " + e.getNom());

		getEntityManager().createNativeQuery("update eleve set nom='Dupond' where id=1").executeUpdate();
		getEntityManager().flush();
		System.out.println("Nom de l'éléve aprés requête SQL : " + e.getNom());
	}

	@Test
	public void requeteUpdateSql2_Solution() {

		Eleve e = getEntityManager().find(Eleve.class, 1l);
		e.setNom("Durand");
		getEntityManager().persist(e);
		System.out.println("Nom de l'éléve avant requête SQL : " + e.getNom());
		getEntityManager().flush();

		getEntityManager().createNativeQuery("update eleve set nom='Dupond' where id=1").executeUpdate();
		getEntityManager().refresh(e);
		System.out.println("Nom de l'éléve aprés requête SQL : " + e.getNom());
	}

	@Override
	public void init() {
		int cptEleve = 0;
		for (int i = 0; i < 6; i++) {
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

		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
	}
}
