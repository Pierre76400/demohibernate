package fr.softeam.solution;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class P3ChargementTest {

    @Autowired
    private ProfesseurDao professeurDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager = null;

    private EntityTransaction transaction = null;

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesElevesDUnProfesseurTest() {

        String nomProfesseur = "Professeur1";
        ClasseP3 classe = null;

        beginTransaction();

        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.professeur.nom='" + nomProfesseur + "'").getSingleResult();

        closeTransaction();

        afficherClasse(nomProfesseur, classe);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesElevesDUnProfesseurTest_sansErreurOptionHibernate() {
        // Passer la propriété "enable_lazy_load_no_trans" à true
        String nomProfesseur = "Professeur1";
        ClasseP3 classe = null;

        beginTransaction();

        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.professeur.nom='" + nomProfesseur + "'").getSingleResult();

        closeTransaction();

        afficherClasse(nomProfesseur, classe);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesElevesDUnProfesseurTest_sansErreurEager() {
        // Passer la relation professeurP3.eleves à eager
        String nomProfesseur = "Professeur1";
        ClasseP3 classe = null;

        beginTransaction();

        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.professeur.nom='" + nomProfesseur + "'").getSingleResult();

        closeTransaction();

        afficherClasse(nomProfesseur, classe);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherLesClassesDUnProfesseurTest() {
        // Passer la relation professeurP3.eleves à eager
        String nomProfesseur = "Professeur1";
        List<ClasseP3> classes = null;

        beginTransaction();

        classes = entityManager.createQuery("from ClasseP3 c left join fetch c.professeur where c.professeur.nom='" + nomProfesseur + "'").getResultList();
        System.out.println("Liste des classes du professeur: " + nomProfesseur);
        closeTransaction();

        for (ClasseP3 pro : classes) {
            System.out.println(" - " + pro.getNom());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesElevesDUnProfesseurTest_solution() {
        String nomProfesseur = "Professeur1";
        ClasseP3 classe = null;

        beginTransaction();

        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c " + "join fetch c.eleves where c.professeur.nom='" + nomProfesseur
                                                              + "'").getSingleResult();

        closeTransaction();

        afficherClasse(nomProfesseur, classe);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void afficherListeDesElevesDUnProfesseurTest_proxy() {
        ClasseP3 classe = null;

        beginTransaction();

        classe = entityManager.getReference(ClasseP3.class, 1l);
        classe.getEleves();
        closeTransaction();
    }

    @Before
    public void init() {
        beginTransaction();

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
            entityManager.persist(c);
        }

        closeTransaction();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
    }

    private void beginTransaction() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    private void closeTransaction() {
        transaction.commit();
        entityManager.close();
    }

    @After
    public void end() {

        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.type")).setLevel(Level.ERROR);
    }

    private void afficherClasse(String nomProfesseur, ClasseP3 classe) {
        System.out.println("Liste des éléves du professeur " + nomProfesseur + ": ");

        for (EleveP3 pro : classe.getEleves()) {
            System.out.println(" - " + pro.getNom());
        }
    }

}
