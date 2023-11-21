package dae.example.template.beans;

import dae.example.template.entities.Expansion;
import dae.example.template.services.ExpansionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

@Component
@Scope("view")
public class ExpansionBean {
    @Autowired
    private ExpansionService expansionService;

    private List<Expansion> expansions;

    @PostConstruct
    public void init() {
        expansions = expansionService.findAll();
    }

    public List<Expansion> getExpansions() {
        return expansions;
    }

    public void setExpansions(List<Expansion> expansions) {
        this.expansions = expansions;
    }

    public void redirect() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().redirect("/index.xhtml");
    }
}