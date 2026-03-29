package com.example.dogadaj.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;
    @NotEmpty
    private String name, surname, password, country;
    @NotNull(message = "Data urodzenia jest wymagana")
    private LocalDate yearofbirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Services> servicesList;

    public User(String name, String surname, String password, String email, String country, LocalDate yearofbirth, List<Services> servicesList) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.country = country;
        this.yearofbirth = yearofbirth;
        this.servicesList = servicesList;
    }
}
