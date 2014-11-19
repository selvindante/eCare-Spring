package ru.tsystems.tsproject.ecare.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Selvin
 * on 30.09.2014.
 */

@Entity
@Table(name = "option1")
@NamedQueries(
        {
            @NamedQuery(name = "Option.getAllOptions", query = "SELECT o FROM Option o"),
            @NamedQuery(name = "Option.findOptionByTitleAndTariffId", query = "SELECT o FROM Option o WHERE o.title = :title AND o.tariff.id = :id"),
            @NamedQuery(name = "Option.getAllOptionsForTariff", query = "SELECT o FROM Option o WHERE o.tariff.id = :id"),
            @NamedQuery(name = "Option.deleteAllOptions", query = "DELETE FROM Option"),
            @NamedQuery(name = "Option.deleteAllOptionsForTariff", query = "DELETE FROM Option WHERE tariff.id = ?1"),
            @NamedQuery(name = "Option.size", query="SELECT count(o) FROM Option o")
        })
public class Option implements Comparable<Option>{
    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "option_title")
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "cnct_cost")
    private int costOfConnection;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name="dependent_option",
                    joinColumns={ @JoinColumn(name="option_id", referencedColumnName="option_id") },
                    inverseJoinColumns={ @JoinColumn(name="dependent_option_id", referencedColumnName="option_id") }
            )
    private Set<Option> dependentOptions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name="incompatible_option",
                    joinColumns={ @JoinColumn(name="option_id", referencedColumnName="option_id") },
                    inverseJoinColumns={ @JoinColumn(name="incompatible_option_id", referencedColumnName="option_id") }
            )
    private Set<Option> incompatibleOptions = new HashSet<>();

    public Option() {
    }

    public Option(Tariff tariff, String title, int price, int costOfConnection) {
        this.tariff = tariff;
        this.title = title;
        this.price = price;
        this.costOfConnection = costOfConnection;
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

    public int getCostOfConnection() {
        return costOfConnection;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public Set<Option> getDependentOptions() {
        return dependentOptions;
    }

    public void addDependentOption(Option op) {
        this.dependentOptions.add(op);
    }

    public void deleteDependentOption(Option op) {
        this.dependentOptions.remove(op);
    }

    public Set<Option> getIncompatibleOptions() {
        return incompatibleOptions;
    }

    public void addIncompatibleOption(Option op) {
        this.incompatibleOptions.add(op);
    }

    public void deleteIncompatibleOption(Option op) {
        this.incompatibleOptions.remove(op);
    }

    @Override
    public String toString() {
        return "Option{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", costOfConnection=" + costOfConnection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (costOfConnection != option.costOfConnection) return false;
        if (id != option.id) return false;
        if (price != option.price) return false;
        if (title != null ? !title.equals(option.title) : option.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + costOfConnection;
        return result;
    }

    @Override
    public int compareTo(Option o) {
        return this.toString().compareTo(o.toString());
    }
}
