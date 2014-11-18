package ru.tsystems.tsproject.ecare.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Selvin
 * on 30.09.2014.
 */

@Entity
@Table(name = "tariff")
@NamedQueries(
    {
        @NamedQuery(name = "Tariff.getAllTariffs", query = "SELECT t FROM Tariff t"),
        @NamedQuery(name = "Tariff.deleteAllTariffs", query = "DELETE FROM Tariff"),
        @NamedQuery(name = "Tariff.size", query="SELECT count(t) FROM Tariff t")
    })
public class Tariff implements Comparable<Tariff>{
    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tariff_title")
    private String title;

    @Column(name = "price")
    private int price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tariff", fetch = FetchType.EAGER)
    private Set<Option> options  = new HashSet<>();

    public Tariff() {
    }

    public Tariff(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public void addOption(Option op) {
        this.options.add(op);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tariff tariff = (Tariff) o;

        if (price != tariff.price) return false;
        if (!title.equals(tariff.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + price;
        return result;
    }

    @Override
    public int compareTo(Tariff o) {
        return this.toString().compareTo(o.toString());
    }
}
