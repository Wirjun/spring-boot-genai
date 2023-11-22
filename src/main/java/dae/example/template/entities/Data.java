package dae.example.template.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="DATA")
public class Data {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name="TEXT")
    private String text;
    @Column(name="SUMMARY")
    private String summary;
    @Column(name="TOKEN")
    private Integer token;
    @Column(name="VECTOR")
    private String vector;

    public Data() {    }

    public Data(Long id, String text, String summary, Integer token, String vector) {
        this.id = id;
        this.text = text;
        this.summary = summary;
        this.token = token;
        this.vector = vector;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(id, data.id) && Objects.equals(text, data.text) && Objects.equals(summary, data.summary) && Objects.equals(token, data.token) && Objects.equals(vector, data.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, summary, token, vector);
    }

    @Override
    public String toString() {
        return "Expansion{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", summary='" + summary + '\'' +
                ", token=" + token +
                ", vector='" + vector + '\'' +
                '}';
    }
}
