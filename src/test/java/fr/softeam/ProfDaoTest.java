package fr.softeam;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.softeam.dao.ProfesseurDao;
import fr.softeam.model.Professeur;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProfDaoTest {

	@Autowired
	private ProfesseurDao professeurDao;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void firstTest() {
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
