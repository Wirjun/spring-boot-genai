package dae.example.template.beans;

import dae.example.template.entities.Data;
import dae.example.template.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

@Component
@Scope("view")
public class DataBean {
    @Autowired
    private DataService dataService;

    private List<Data> data;

    private String inputData;

    @PostConstruct
    public void init() {
        data = dataService.findAll();
    }

    public List<Data> getDatas() {
        return data;
    }

    public void setDatas(List<Data> data) {
        this.data = data;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public void redirect() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().redirect("/index.xhtml");
    }

    public void uploadAndSave() {
        String summary = dataService.createSummary(inputData);
        String embedding = dataService.createEmbedding(summary);
        Data data = new Data(inputData, summary, 0, embedding);
        dataService.saveOrUpdate(data);
    }
}