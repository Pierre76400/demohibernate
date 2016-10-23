package fr.softeam.solution;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.qos.logback.classic.Level;
import fr.softeam.dao.ProfesseurDao;
import fr.softeam.model.variante.ClasseP3;
import fr.softeam.model.variante.EleveP3;
import fr.softeam.model.variante.ProfesseurP3;
import fr.softeam.util.AbstractCommonLanceurTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class P3ChargementTest extends AbstractCommonLanceurTest {

	@Autowired
	private ProfesseurDao professeurDao;

	@Test
	@SuppressWarnings("unchecked")
	public void afficherListeDesElevesDUnProfesseurTest() {
		String nomProfesseur = "Professeur1";
		ClasseP3 classe = (ClasseP3) getEntityManager().createQuery(
				"select c,c.professeur from ClasseP3 c where c.professeur.nom='" + nomProfesseur + "'")
				.getSingleResult();

		// List<EleveP3> l =
		// getEntityManager().createQuery("from EleveP3").getResultList();
		System.out.println("Liste des éléves du professeur " + nomProfesseur + ": ");

		// getEntityManager().createQuery("from ClasseP3").getResultList();
		for (EleveP3 pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void afficherListeDesElevesDUnProfesseurTest_SolutionPasPropre() {
		// Test identique au précédent mais passer la relation
		// ClasseP3.setEleves en eager
	}

	@Test
	@SuppressWarnings("unchecked")
	public void afficherListeDesElevesDUnProfesseurSolutionTest() {
		String nomProfesseur = "Professeur1";
		ClasseP3 classe = (ClasseP3) getEntityManager().createQuery(
				"from ClasseP3 c left join fetch c.professeur left join fetch c.eleves where c.professeur.nom='"
						+ nomProfesseur + "'").getSingleResult();
		System.out.println("Liste des éléves du professeur: " + nomProfesseur);

		for (EleveP3 pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void afficherLesClassesDUnProfesseurTest() {
		String nomProfesseur = "Professeur1";
		List<ClasseP3> classes = getEntityManager().createQuery(
				"from ClasseP3 c left join fetch c.professeur where c.professeur.nom='" + nomProfesseur + "'")
				.getResultList();
		System.out.println("Liste des classes du professeur: " + nomProfesseur);

		for (ClasseP3 pro : classes) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Override
	public void init() {
		int cptEleve = 0;
		for (int i = 0; i < 6; i++) {
			ClasseP3 c = new ClasseP3();
			c.setNom("classe" + i);

			ProfesseurP3 p = new ProfesseurP3();
			p.setNom("Professeur" + i);

			c.setProfesseur(p);

			// c.setEleves();
			for (int j = 0; j < 5; j++) {
				EleveP3 e = new EleveP3();
				e.setNom("eleve" + cptEleve++);
				c.addEleve(e);
			}
			getEntityManager().persist(c);
		}

		getEntityManager().clear();
		getEntityManager().flush();
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
	}
}
