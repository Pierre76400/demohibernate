package fr.softeam;

import java.util.List;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import fr.softeam.model.Professeur;
import fr.softeam.util.AbstractCommonLanceurSansLogTest;

public class P1LogTest extends AbstractCommonLanceurSansLogTest {

	@Test
	@SuppressWarnings("unchecked")
	public void afficherProfesseursTest() {
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("fr.softeam.solution")).setLevel(Level.ERROR);
		Professeur prof1 = (Professeur) getEntityManager().createQuery("from Professeur where nom=?")
				.setParameter(1, "Professeur1").getSingleResult();
		System.out.println("Professeur avec l'id 1 : " + prof1.getNom());

		List<Professeur> profs = getEntityManager().createQuery("from Professeur").getResultList();
		afficherListeDesProfesseurs(profs);
	}

	private void afficherListeDesProfesseurs(List<Professeur> profs) {
		System.out.println("Liste des professeurs :");
		for (Professeur pro : profs) {
			System.out.println(" - " + pro.getNom());
		}
	}
}