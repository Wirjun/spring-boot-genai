package dae.example.template.beans;

import dae.example.template.entities.Data;
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

    private List<Data> data;

    @PostConstruct
    public void init() {
        data = expansionService.findAll();
    }

    public List<Data> getExpansions() {
        return data;
    }

    public void setExpansions(List<Data> data) {
        this.data = data;
    }

    public void redirect() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().redirect("/index.xhtml");
    }
}