package fr.softeam.solution;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.softeam.dao.ProfesseurDao;
import fr.softeam.model.Classe;
import fr.softeam.util.AbstractCommonLanceurTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class P2ParametreTest extends AbstractCommonLanceurTest {

    @Autowired
    private ProfesseurDao professeurDao;

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesDUnProfesseurTest() {
        String nomProfesseur = "Professeur1";
        List<Classe> classes = getEntityManager().createQuery("from Classe c left join fetch c.professeur where c.professeur.nom='"
                                                                      + nomProfesseur + "'").getResultList();
        System.out.println("Liste des classes du professeur: " + nomProfesseur);

        for (Classe pro : classes) {
            System.out.println(" - " + pro.getNom());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesDUnProfesseur_Probleme_Test() {
        String nomProfesseur = "Professeur'";
        List<Classe> classes = getEntityManager().createQuery("from Classe c left join fetch c.professeur where c.professeur.nom='"
                                                                      + nomProfesseur + "'").getResultList();
        System.out.println("Liste des classes du professeur: " + nomProfesseur);

        for (Classe pro : classes) {
            System.out.println(" - " + pro.getNom());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesDUnProfesseur_ProblemePlusGrave_Test() {
        String nomProfesseur = "' or ''='";
        List<Classe> classes = getEntityManager().createQuery("from Classe c left join fetch c.professeur where c.professeur.nom='"
                                                                      + nomProfesseur + "'").getResultList();
        System.out.println("Liste des classes du professeur: " + nomProfesseur);

        for (Classe pro : classes) {
            System.out.println(" - " + pro.getNom());
        }
    }
}
