package dae.example.template.beans;

import dae.example.template.entities.Data;
import dae.example.template.entities.Message;
import dae.example.template.entities.Role;
import dae.example.template.services.DataService;
import dae.example.template.util.OpenAIConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
@Scope("view")
public class DataBean {
    @Autowired
    private DataService dataService;

    private List<Data> data;

    private String uploadData;

    private String inputData;

    private HashMap<Integer, Message> history;

    @PostConstruct
    public void init() {
        data = dataService.findAll();
        history = new HashMap<>();
    }

    public List<Data> getDatas() {
        return data;
    }

    public void setDatas(List<Data> data) {
        this.data = data;
    }

    public String getUploadData() {
        return uploadData;
    }

    public void setUploadData(String uploadData) {
        this.uploadData = uploadData;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public void uploadAndSave() {
        String summary = OpenAIConnector.createSummary(uploadData);
        String embedding = OpenAIConnector.createEmbedding(summary);
        Data data = new Data(uploadData, summary, 0, embedding);
        dataService.saveOrUpdate(data);
    }

    public void send() {
        String answer = dataService.predictAnswer(inputData);
        history.put(history.size()+1, new Message(inputData, Role.CUSTOMER));
        history.put(history.size()+1, new Message(answer, Role.BOT));
        inputData = null;
    }

    public HashMap<Integer, Message> getHistory() {
        return history;
    }
}