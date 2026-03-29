package com.example.dogadaj.controller;

import com.example.dogadaj.domain.Item;
import com.example.dogadaj.domain.Services;           // zakładam, że to Twoja encja listy zakupów
import com.example.dogadaj.domain.User;
import com.example.dogadaj.service.ItemService;
import com.example.dogadaj.service.ServiceService;     // serwis do obsługi Services (listy)
import com.example.dogadaj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/services")
public class ServicesController {

    final private String title = "DOGadaj | Behawiorysta psów - Karolina Teper |";

    private final ServiceService serviceService;
    private final ItemService itemService;
    private final UserService userService;



    public ServicesController(ServiceService serviceService,
                              ItemService itemService,
                              UserService userService) {
        this.serviceService = serviceService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping
    public String listShoppingLists(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("title", title);
        User user = userService.findByEmail(email).orElse(null);

        model.addAttribute("lists", serviceService.findByUser(user));
        return "services";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("title", title);
        model.addAttribute("list", new Services());
        return "create";
    }

    @PostMapping("/create")
    public String createList(@ModelAttribute("list") Services list, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email).orElse(null);

        if (user != null) {
            list.setUser(user);
            serviceService.create(list);           // lub save()
        }

        return "redirect:/services";
    }

    @GetMapping("/view/{id}")
    public String viewList(@PathVariable Integer id, Model model) {
        Optional<Services> optList = serviceService.findById(id);

        if (optList.isPresent()) {
            Services list = optList.get();
            model.addAttribute("list", list);
            model.addAttribute("newItem", new Item());
            return "view";
        }

        return "redirect:/services";
    }

    @PostMapping("/view/{id}/addItem")
    public String addItem(@PathVariable Integer id, @ModelAttribute("newItem") Item item) {
        Optional<Services> optList = serviceService.findById(id);

        if (optList.isPresent()) {
            Services list = optList.get();
            item.setId(null);
            item.setServices(list);
            itemService.add(item);
        }

        return "redirect:/services/view/" + id;
    }

    @GetMapping("/view/{listId}/editItem/{itemId}")
    public String editItemForm(@PathVariable Integer listId,
                               @PathVariable Integer itemId,
                               Model model) {
        Optional<Services> optList = serviceService.findById(listId);
        Optional<Item> optItem = itemService.findById(itemId);

        if (optList.isPresent() && optItem.isPresent()) {
            model.addAttribute("list", optList.get());
            model.addAttribute("item", optItem.get());
            return "editItem";
        }

        return "redirect:/services/view/" + listId;
    }

    @PostMapping("/view/{listId}/editItem/{itemId}")
    public String updateItem(@PathVariable Integer listId,
                             @PathVariable Integer itemId,
                             @ModelAttribute("item") Item updatedItem) {

        Optional<Item> optItem = itemService.findById(itemId);
        if (optItem.isPresent()) {
            Item item = optItem.get();
            item.setName(updatedItem.getName());
            // ewentualnie inne pola: quantity, checked itp.
            itemService.add(item);        // lepiej save() zamiast add()
        }

        return "redirect:/services/view/" + listId;
    }

    @GetMapping("/edit/{id}")
    public String editListForm(@PathVariable Integer id, Model model) {
        Optional<Services> optList = serviceService.findById(id);

        if (optList.isPresent()) {
            model.addAttribute("list", optList.get());
            return "editList";
        }

        return "redirect:/services";
    }



    @PostMapping("/edit/{id}")
    public String updateList(@PathVariable Integer id, @ModelAttribute("list") Services updatedList) {
        Optional<Services> optList = serviceService.findById(id);

        if (optList.isPresent()) {
            Services existingList = optList.get();
            existingList.setTitle(updatedList.getTitle());
            existingList.setTime(updatedList.getTime());
            existingList.setPrice(updatedList.getPrice());

            serviceService.save(existingList);
        }

        return "redirect:/services";
    }

    @GetMapping("/view/{listId}/deleteItem/{itemId}")
    public String deleteItem(@PathVariable Integer listId, @PathVariable Integer itemId) {
        Optional<Services> optList = serviceService.findById(listId);

        if (optList.isPresent()) {
            Services list = optList.get();
            list.getItems().removeIf(item -> item.getId().equals(itemId));
            serviceService.save(list);
        }

        return "redirect:/services/view/" + listId;
    }

    @GetMapping("/delete/{id}")
    public String deleteList(@PathVariable Integer id) {
        serviceService.delete(id);
        return "redirect:/services";
    }
}