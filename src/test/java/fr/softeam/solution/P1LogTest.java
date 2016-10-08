package fr.softeam.solution;

import java.util.List;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import fr.softeam.model.Professeur;
import fr.softeam.util.AbstractCommonLanceurSansLogTest;

public class P1LogTest extends AbstractCommonLanceurSansLogTest {

	@Test
	@SuppressWarnings("unchecked")
	public void afficherProfesseurTest() {
		Professeur prof1 = (Professeur) getEntityManager().createQuery("from Professeur where nom=?")
				.setParameter(1, "Professeur1").getSingleResult();
		System.out.println("Professeur avec l'id 1 : " + prof1.getNom());

		List<Professeur> profs = getEntityManager().createQuery("from Professeur").getResultList();
		System.out.println("Liste des professeurs :");
		for (Professeur pro : profs) {
			System.out.println(" - " + pro.getNom());
		}

	}

	@Test
	@SuppressWarnings("unchecked")
	public void afficherProfesseurTest_avec_trace_sql() {
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);

		Professeur prof1 = (Professeur) getEntityManager().createQuery("from Professeur where nom=?")
				.setParameter(1, "Professeur1").getSingleResult();
		System.out.println("Professeur avec l'id 1 : " + prof1.getNom());

		List<Professeur> profs = getEntityManager().createQuery("from Professeur").getResultList();
		System.out.println("Liste des professeurs :");
		for (Professeur pro : profs) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void afficherProfesseurTest_ajoutFormattage() {
		// Il faut activer la propriété hibernate "format_sql"
		//
		// Dans spring boot on peut accéder aux propriétés hibernate dans le
		// fichier "application.properties"
		// Exemple pour "format_sql" :
		// "spring.jpa.properties.hibernate.format_sql=true"
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);

		Professeur prof1 = (Professeur) getEntityManager().createQuery("from Professeur where nom=?")
				.setParameter(1, "Professeur1").getSingleResult();
		System.out.println("Professeur avec l'id 1 : " + prof1.getNom());

		List<Professeur> profs = getEntityManager().createQuery("from Professeur").getResultList();
		System.out.println("Liste des professeurs :");
		for (Professeur pro : profs) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void afficherProfesseurTest_ajoutProvenanceRequete() {
		// Il faut activer la propriété hibernate "use_sql_comments"
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);

		Professeur prof1 = (Professeur) getEntityManager().createQuery("from Professeur where nom=?")
				.setParameter(1, "Professeur1").getSingleResult();
		System.out.println("Professeur avec l'id 1 : " + prof1.getNom());

		List<Professeur> profs = getEntityManager().createQuery("from Professeur").getResultList();
		System.out.println("Liste des professeurs :");
		for (Professeur pro : profs) {
			System.out.println(" - " + pro.getNom());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void afficherProfesseurTest_ajoutParametreRequete() {
		// Il faut activer la propriété hibernate "use_sql_comments"
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.type")).setLevel(Level.TRACE);

		Professeur prof1 = (Professeur) getEntityManager().createQuery("from Professeur where nom=?")
				.setParameter(1, "Professeur1").getSingleResult();
		System.out.println("Professeur avec l'id 1 : " + prof1.getNom());

		List<Professeur> profs = getEntityManager().createQuery("from Professeur").getResultList();
		System.out.println("Liste des professeurs :");
		for (Professeur pro : profs) {
			System.out.println(" - " + pro.getNom());
		}
	}
}
