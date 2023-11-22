package dae.example.template;

import dae.example.template.services.DataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TemplateApplicationTests {

    @Autowired
    DataService dataService;

    @Test
    public void test() {
        //Implement me
    }

}
