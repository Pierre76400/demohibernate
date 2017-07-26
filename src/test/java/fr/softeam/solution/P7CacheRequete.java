package fr.softeam.solution;

import java.util.HashMap;
import java.util.Map;

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
public class P7CacheRequete {

    @Autowired
    private ProfesseurDao professeurDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager = null;

    private EntityTransaction transaction = null;

    private ClasseP3 classe = null;

    @Test
    @SuppressWarnings("unchecked")
    public void requeteSansCache() {

        openTransaction();
        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='classe1'").getSingleResult();
        closeTransaction();
        System.out.println("Classe : " + classe.getNom());

        openTransaction();
        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='classe1'").getSingleResult();
        closeTransaction();
        System.out.println("Classe : " + classe.getNom());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void requeteAvecCache() {
        openTransaction();
        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='classe1'").setHint("org.hibernate.cacheable",
                                                                                                       Boolean.TRUE).getSingleResult();
        closeTransaction();
        System.out.println("Classe : " + classe.getNom());

        openTransaction();
        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='classe1'").setHint("org.hibernate.cacheable",
                                                                                                       Boolean.TRUE).getSingleResult();
        closeTransaction();
        System.out.println("Classe : " + classe.getNom());
    }

    // Rajouter au niveau des entity Classe et au niveau Professeur , le cache :
    // @Cache(usage =CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Test
    @SuppressWarnings("unchecked")
    public void requeteAvecCache_solution() {
        openTransaction();
        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='classe1'").setHint("org.hibernate.cacheable",
                                                                                                       Boolean.TRUE).getSingleResult();
        closeTransaction();
        System.out.println("Classe : " + classe.getNom());

        openTransaction();
        classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='classe1'").setHint("org.hibernate.cacheable",
                                                                                                       Boolean.TRUE).getSingleResult();
        closeTransaction();
        System.out.println("Classe : " + classe.getNom());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void requeteAvecCacheOneToMany() {

        openTransaction();
        classe = (ClasseP3) entityManager.createQuery("select distinct c from ClasseP3 c" + " join fetch c.eleves"
                                                              + " join fetch c.professeur where c.nom='classe1'").setHint("org.hibernate.cacheable",
                                                                                                                          Boolean.TRUE).getSingleResult();
        closeTransaction();
        afficherClasse(classe);

        openTransaction();
        classe = (ClasseP3) entityManager.createQuery("select distinct c from ClasseP3 c" + " join fetch c.eleves"
                                                              + " join fetch c.professeur where c.nom='classe1'").setHint("org.hibernate.cacheable",
                                                                                                                          Boolean.TRUE).getSingleResult();
        afficherClasse(classe);
        closeTransaction();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cacheManuel() {
        Map<String, ClasseP3> cache = new HashMap<>();
        String classeRecherchee = "classe1";

        openTransaction();

        if (!cache.containsKey(classeRecherchee)) {
            classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='" + classeRecherchee + "'").getSingleResult();
            cache.put(classe.getNom(), classe);
        } else {
            classe = cache.get(classeRecherchee);
        }

        closeTransaction();
        System.out.println("Classe : " + classe.getNom());

        openTransaction();
        if (!cache.containsKey(classeRecherchee)) {
            classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='" + classeRecherchee + "'").getSingleResult();
            cache.put(classe.getNom(), classe);
        } else {
            classe = cache.get(classeRecherchee);
        }
        System.out.println("Classe : " + classe.getNom());
        closeTransaction();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cacheManuelQuiNeMarchePlus() {
        Map<String, ClasseP3> cache = new HashMap<>();
        String classeRecherchee = "classe1";

        openTransaction();

        if (!cache.containsKey(classeRecherchee)) {
            classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='" + classeRecherchee + "'").getSingleResult();
            cache.put(classe.getNom(), classe);
        } else {
            classe = cache.get(classeRecherchee);
        }

        closeTransaction();
        System.out.println("Classe : " + classe.getNom());

        openTransaction();
        if (!cache.containsKey(classeRecherchee)) {
            classe = (ClasseP3) entityManager.createQuery("from ClasseP3 c where c.nom='" + classeRecherchee + "'").getSingleResult();
            cache.put(classe.getNom(), classe);
        } else {
            classe = cache.get(classeRecherchee);
        }
        System.out.println("Classe : " + classe.getNom());
        afficherClasse(classe);
        closeTransaction();
    }

    private void afficherClasse(ClasseP3 classe) {
        System.out.println("Classe : " + classe.getNom());
        for (EleveP3 e : classe.getEleves()) {
            System.out.print("Eleve = " + e.getId() + " " + e.getNom() + "  ");
        }

        System.out.println("");
        System.out.println("");
    }

    @Before
    public void init() {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

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

            transaction.commit();
        } catch (Throwable e) {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
    }

    @After
    public void end() {

        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.type")).setLevel(Level.ERROR);
    }

    private void closeTransaction() {
        transaction.commit();
        entityManager.close();
    }

    private void openTransaction() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

}
