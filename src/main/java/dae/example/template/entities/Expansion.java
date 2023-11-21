package dae.example.template.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="EXPANSION")
public class Expansion {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name="NAME")
    private String name;
    @Column(name="YEAR")
    private int year;
    @Column(name="OWNED")
    private boolean owned = false;

    public Expansion() {    }

    public Expansion(String name, int year, boolean owned) {
        this.name = name;
        this.year = year;
        this.owned = owned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expansion expansion = (Expansion) o;
        return year == expansion.year &&
                owned == expansion.owned &&
                id.equals(expansion.id) &&
                name.equals(expansion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, owned);
    }

    @Override
    public String toString() {
        return "Expansion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", owned=" + owned +
                '}';
    }
}
