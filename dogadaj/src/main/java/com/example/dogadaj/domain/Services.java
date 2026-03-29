package com.example.dogadaj.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "items")   // zapobiega problemom z toString
@NoArgsConstructor
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String title;          // używasz "title" – zostawiamy

    private double time;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // RELACJA JEDEN DO WIELU z Item
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    // Konstruktor z parametrami
    public Services(String title, double time, double price) {
        this.title = title;
        this.time = time;
        this.price = price;
    }

    // Pomocnicza metoda do dodawania itemów
    public void addItem(Item item) {
        this.items.add(item);
        item.setServices(this);
    }
}