package fr.softeam;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.softeam.dao.ProfesseurDao;
import fr.softeam.model.Professeur;
import fr.softeam.util.AbstractCommonLanceurSansLogTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class P1LogTest extends AbstractCommonLanceurSansLogTest {

	@Autowired
	private ProfesseurDao professeurDao;

	@Test
	@SuppressWarnings("unchecked")
	public void afficherProfesseurTest() {
		Professeur prof1 = (Professeur) getEntityManager()
				.createQuery("from Professeur where nom=?")
				.setParameter(1, "Professeur1").getSingleResult();
		System.out.println("Professeur avec l'id 1 : " + prof1.getNom());

		List<Professeur> profs = getEntityManager().createQuery(
				"from Professeur").getResultList();
		System.out.println("Liste des professeurs :");
		for (Professeur pro : profs) {
			System.out.println(" - " + pro.getNom());
		}
	}

}
