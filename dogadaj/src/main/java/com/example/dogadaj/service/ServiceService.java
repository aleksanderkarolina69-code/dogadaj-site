package com.example.dogadaj.service;

import com.example.dogadaj.domain.Services;
import com.example.dogadaj.domain.User;
import com.example.dogadaj.repository.ServicesRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final ServicesRepositories servicesRepositories;

    public ServiceService(ServicesRepositories servicesRepositories) {
        this.servicesRepositories = servicesRepositories;
    }

    /**
     * Tworzenie nowej usługi
     */
    public Services create(Services service) {
        return servicesRepositories.save(service);
    }

    /**
     * Pobieranie wszystkich usług danego użytkownika
     */
    public List<Services> findByUser(User user) {
        return servicesRepositories.findByUser(user);
    }

    /**
     * Pobieranie usługi po ID
     */
    public Optional<Services> findById(Integer id) {
        return servicesRepositories.findById(id);
    }

    /**
     * Zapisywanie / aktualizacja usługi
     */
    public Services save(Services service) {
        return servicesRepositories.save(service);
    }

    /**
     * Usuwanie usługi po ID
     */
    public void delete(Integer id) {
        servicesRepositories.deleteById(id);
    }
}