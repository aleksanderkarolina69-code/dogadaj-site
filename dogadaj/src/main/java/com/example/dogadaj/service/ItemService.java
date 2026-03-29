package com.example.dogadaj.service;

import com.example.dogadaj.domain.Item;
import com.example.dogadaj.domain.Services;
import com.example.dogadaj.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item add(Item item){
        return itemRepository.save(item);
    }

    public List<Item> findByShoppinList(Services services){
        return itemRepository.findByServices(services);
    }

    public List<Item> findByServicesId(Integer id){
        return itemRepository.findByServices(new Services(){{
            setId(id);
        }});
    }

    public Optional<Item> findById(Integer id) {
        return itemRepository.findById(id);
    }

    public void delete(Integer id){
        itemRepository.deleteById(id);
    }

}
