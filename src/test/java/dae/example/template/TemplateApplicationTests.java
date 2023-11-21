package dae.example.template;

import dae.example.template.entities.Expansion;
import dae.example.template.services.ExpansionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TemplateApplicationTests {

    @Autowired
    ExpansionService expansionService;

    @Test
    public void test() {
        Expansion expansion = expansionService.findById(1L);
        Assertions.assertEquals(expansion.getName(), "Core Set 2020");
    }

}
