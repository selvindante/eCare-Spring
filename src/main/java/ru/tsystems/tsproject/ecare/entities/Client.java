package ru.tsystems.tsproject.ecare.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Selvin
 * on 30.09.2014.
 */

@Entity
@Table(name = "client")
@NamedQueries(
    {
        @NamedQuery(name = "Client.getAllClients", query = "SELECT c FROM Client c WHERE c.role = 'client'"),
        @NamedQuery(name = "Client.findClientByLoginAndPassword", query = "SELECT c FROM Client c WHERE c.email = :login AND c.password = :password"),
        @NamedQuery(name = "Client.findClientByNumber", query = "SELECT cn.client FROM Contract cn WHERE cn.number = :number"),
        @NamedQuery(name = "Client.findClientByLogin", query = "SELECT c FROM Client c WHERE c.email = :login"),
        @NamedQuery(name = "Client.deleteAllClients", query = "DELETE FROM Client WHERE role = 'client'"),
        @NamedQuery(name = "Client.size", query="SELECT count(c) FROM Client c WHERE c.role = 'client'")
    })
public class Client implements Comparable<Client>{
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Column(name = "passport")
    private Long passport;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "amount")
    private int amount = 0;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Contract> contracts = new ArrayList<>();

    public Client() {
    }

    public Client(String name, String lastname, Date birthDate, Long passport, String address, String email, String password, String role, int amount) {
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.passport = passport;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullName() {
        if(lastname != null) return name + " " + lastname;
        else return name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBirthDateToString() {
        return (new java.sql.Date(birthDate.getTime())).toString();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getPassport() {
        return passport;
    }

    public void setPassport(Long passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void addContract(Contract cn) {
        this.contracts.add(cn);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", passport=" + passport +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (passport != client.passport) return false;
        if (address != null ? !address.equals(client.address) : client.address != null) return false;
        if (!email.equals(client.email)) return false;
        if (lastname != null ? !lastname.equals(client.lastname) : client.lastname != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (!password.equals(client.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (int) (passport ^ (passport >>> 32));
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public int compareTo(Client o) {
        int cmp = name.compareTo(o.name);
        return cmp == 0 ? email.compareTo(o.email) : cmp;
    }
}