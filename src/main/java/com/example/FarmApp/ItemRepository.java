package com.example.FarmApp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ItemRepository extends CrudRepository<Item, String> {

    Item findItemByItemName(String itemName);
    Item findItemById(Integer id);

    @Transactional
    void deleteById(Integer id);
}
