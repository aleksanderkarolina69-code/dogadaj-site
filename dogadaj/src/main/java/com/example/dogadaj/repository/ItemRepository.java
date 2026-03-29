package com.example.dogadaj.repository;

import com.example.dogadaj.domain.Item;
import com.example.dogadaj.domain.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    public List<Item> findByServices(Services services);
}
