package fr.softeam.solution;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.softeam.dao.ProfesseurDao;
import fr.softeam.model.Classe;
import fr.softeam.model.Eleve;
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
		Classe classe = (Classe) getEntityManager().createQuery(
				"from Classe c left join fetch c.professeur where c.professeur.nom='" + nomProfesseur + "'")
				.getSingleResult();
		System.out.println("Liste des éléves du professeur: " + nomProfesseur);

		for (Eleve pro : classe.getEleves()) {
			System.out.println(" - " + pro.getNom());
		}
	}

}
