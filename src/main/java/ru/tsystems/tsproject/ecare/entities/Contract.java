package ru.tsystems.tsproject.ecare.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Selvin
 * on 30.09.2014.
 */

@Entity
@Table(name = "contract")
@NamedQueries(
    {
        @NamedQuery(name = "Contract.getAllContracts", query = "SELECT c FROM Contract c"),
        @NamedQuery(name = "Contract.findContractByNumber", query = "SELECT c FROM Contract c WHERE c.number = :number"),
        @NamedQuery(name = "Contract.getAllContractsForClient", query = "SELECT c FROM Contract c WHERE c.client.id = :id"),
        @NamedQuery(name = "Contract.deleteAllContracts", query="DELETE FROM Contract"),
        @NamedQuery(name = "Contract.deleteAllContractsForClient", query = "DELETE FROM Contract WHERE client.id = ?1"),
        @NamedQuery(name = "Contract.size", query="SELECT count(c) FROM Contract c")
    })
public class Contract implements Comparable<Contract>{
    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private long number;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @Column(name = "blckd_by_cl")
    private boolean isBlockedByClient = false;

    @Column(name = "blckd_by_op")
    private boolean isBlockedByOperator = false;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable
            (
                    name="connected_option",
                    joinColumns={ @JoinColumn(name="contract_id", referencedColumnName="contract_id") },
                    inverseJoinColumns={ @JoinColumn(name="option_id", referencedColumnName="option_id") }
            )
    private Set<Option> options = new HashSet<>();

    public Contract() {
    }

    public Contract(Client client, long number, Tariff tariff, boolean isBlockedByClient, boolean isBlockedByOperator) {
        this.client = client;
        this.number = number;
        this.tariff = tariff;
        this.isBlockedByClient = isBlockedByClient;
        this.isBlockedByOperator = isBlockedByOperator;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public boolean isBlockedByClient() {
        return isBlockedByClient;
    }

    public void setBlockByClient(boolean block) {
        if(!block && isBlockedByOperator) return;
        isBlockedByClient = block;
    }

    public boolean isBlockedByOperator() {
        return isBlockedByOperator;
    }

    public void setBlockByOperator(boolean block) {
        isBlockedByOperator = block;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void addOption(Option op) {
        this.options.add(op);
    }

    public void deleteOption(Option op) {
        this.options.remove(op);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "number=" + number +
                ", tariff=" + tariff +
                ", isBlockedByClient=" + isBlockedByClient +
                ", isBlockedByOperator=" + isBlockedByOperator +
                ", client=" + client +
                '}';
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (isBlockedByClient != contract.isBlockedByClient) return false;
        if (isBlockedByOperator != contract.isBlockedByOperator) return false;
        if (number != contract.number) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (number ^ (number >>> 32));
        result = 31 * result + (isBlockedByClient ? 1 : 0);
        result = 31 * result + (isBlockedByOperator ? 1 : 0);
        return result;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != contract.id) return false;
        if (isBlockedByClient != contract.isBlockedByClient) return false;
        if (isBlockedByOperator != contract.isBlockedByOperator) return false;
        if (number != contract.number) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (number ^ (number >>> 32));
        result = 31 * result + (isBlockedByClient ? 1 : 0);
        result = 31 * result + (isBlockedByOperator ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(Contract o) {
        return this.toString().compareTo(o.toString());
    }
}
