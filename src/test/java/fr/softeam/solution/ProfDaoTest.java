package fr.softeam.solution;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
import fr.softeam.model.Classe;
import fr.softeam.model.Eleve;
import fr.softeam.model.Professeur;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProfDaoTest {

    @Autowired
    private ProfesseurDao professeurDao;

    @Autowired
    private EntityManager entityManager;

    @Before
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
                c.getEleves().add(e);
            }
            entityManager.persist(c);
        }
    }

    @After
    public void end() {

        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.ERROR);
    }

    @Test
    public void firstTest() {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);

        Professeur p = new Professeur();

        p.setNom("Tournesol");
        p.setPrenom("Triton");

        professeurDao.save(p);

        entityManager.clear();
        entityManager.flush();

        for (Professeur pro : professeurDao.findAll()) {
            System.out.println(pro.getNom());
        }

    }
}
