package de.tekup.rest.data.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Person")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name =  "persoName", length = 50, nullable = false, unique = true)
    private String name;

    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.REMOVE)
    private AddressEntity address;

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<TelephoneNumberEntity> getPhones() {
        return phones;
    }

    public void setPhones(List<TelephoneNumberEntity> phones) {
        this.phones = phones;
    }

    public List<GamesEntity> getGames() {
        return games;
    }

    public void setGames(List<GamesEntity> games) {
        this.games = games;
    }

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private List<TelephoneNumberEntity> phones;

    @ManyToMany(mappedBy = "persons", cascade = CascadeType.REMOVE)
    private List<GamesEntity> games;

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
