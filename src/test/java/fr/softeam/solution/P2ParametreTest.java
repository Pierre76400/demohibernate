package fr.softeam.solution;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
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
        afficherClasse(nomProfesseur, classes);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesDUnProfesseur_Probleme_Test() {
        String nomProfesseur = "'";

        List<Classe> classes = getEntityManager().createQuery("from Classe c left join fetch c.professeur where c.professeur.nom='"
                                                                      + nomProfesseur + "'").getResultList();
        afficherClasse(nomProfesseur, classes);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesDUnProfesseur_ProblemePlusGrave_Test() {
        String nomProfesseur = "' or ''='";
        List<Classe> classes = getEntityManager().createQuery("from Classe c left join fetch c.professeur where c.professeur.nom='"
                                                                      + nomProfesseur + "'").getResultList();
        afficherClasse(nomProfesseur, classes);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesDUnProfesseur_soluce() {
        String nomProfesseur = "' or ''='";
        List<Classe> classes = getEntityManager().createQuery("from Classe c left join fetch c.professeur where c.professeur.nom=:nomProfesseur").setParameter("nomProfesseur",
                                                                                                                                                               nomProfesseur).getResultList();
        afficherClasse(nomProfesseur, classes);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void paramListe() {
        List<String> nomProfesseurs = Arrays.asList("Professeur1", "Professeur2");

        String queryHql = "from Classe c left join fetch c.professeur where c.professeur.nom in(";

        for (String s : nomProfesseurs) {
            queryHql = queryHql + "?,";
        }

        queryHql = queryHql.substring(0, queryHql.length() - 1);
        queryHql += ")";

        Query query = getEntityManager().createQuery(queryHql);

        int posParam = 1;

        for (String s : nomProfesseurs) {
            query.setParameter(posParam++, s);
        }

        List<Classe> classes = query.getResultList();

        afficherClassesDUnProfesseur(nomProfesseurs, classes);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void paramListe_soluce() {
        List<String> nomProfesseurs = Arrays.asList("Professeur1", "Professeur2");

        List<Classe> classes = getEntityManager().createQuery("from Classe c left join fetch c.professeur where c.professeur.nom  in (:nomProfesseurs)").setParameter("nomProfesseurs",
                                                                                                                                                                      nomProfesseurs).getResultList();

        afficherClassesDUnProfesseur(nomProfesseurs, classes);
    }

    private void afficherClasse(String nomProfesseur, List<Classe> classes) {
        System.out.println("Liste des classes du professeur: " + nomProfesseur);

        for (Classe pro : classes) {
            System.out.println(" - " + pro.getNom());
        }
    }

    private void afficherClassesDUnProfesseur(List<String> nomProfesseurs, List<Classe> classes) {
        System.out.println("Liste des classes des professeurs: " + StringUtils.join(nomProfesseurs, ","));

        for (Classe pro : classes) {
            System.out.println(" - " + pro.getNom());
        }
    }
}
