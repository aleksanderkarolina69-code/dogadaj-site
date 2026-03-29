package com.example.dogadaj.repository;

import com.example.dogadaj.domain.Services;
import com.example.dogadaj.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepositories extends JpaRepository<Services, Integer> {
    List<Services> findByUser(User user);
}
