package fr.softeam.util;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.qos.logback.classic.Level;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public abstract class AbstractCommonLanceurTest extends AbstractCommonLanceurSansLogTest {

    @Before
    public void init() {
        super.init();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
    }

}
