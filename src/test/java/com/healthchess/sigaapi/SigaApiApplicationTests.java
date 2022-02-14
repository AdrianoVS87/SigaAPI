package com.healthchess.sigaapi;




import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigaApiApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class SigaApiApplicationTests {

    @Test
    public void contextLoads(){

    }

}
