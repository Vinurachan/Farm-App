package com.example.FarmApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    private List<String> message = new ArrayList<>();

    @GetMapping(path="/")
    public String ItemsDisplay(Model model) {
        Iterable<Item> listItems = itemRepository.findAll();
        model.addAttribute("listItems", listItems);
        return "index";
    }

    @PostMapping(path = "/item/add")
    public String addNewItem(@RequestParam String itemName, @RequestParam double unitPrice, @RequestParam int units, Model model){
        if(!message.isEmpty()){
            message.remove(0);
        }
            if (itemRepository.findItemByItemName(itemName)==null){
                Item item = new Item();
                item.setItemName(itemName);
                item.setUnitPrice(unitPrice);
                item.setStockIn(units);
                itemRepository.save(item);
                message.add(itemName+" added to the stock");
                model.addAttribute("alert",message);
                return "AddItem";
            }else {
                message.add("Item already exists. Please update item details");
                model.addAttribute("alert",message);
                return "AddItem";
            }
    }

    @GetMapping(path="/item/update/{id}")
    public ModelAndView selectItem(@PathVariable("id") Integer id) {
        ModelAndView mav = new ModelAndView("EditItem");
        Item item = itemRepository.findItemById(id);
        mav.addObject("item", item);
        return mav;
    }

    @PostMapping(path = "/item/update/{mode}")
    public String updateItemDetails(@PathVariable String mode, @ModelAttribute("item") Item item, @RequestParam String stockChange) {
        if(!stockChange.isEmpty()) {
            if (mode.equals("in")) {
                Item old = itemRepository.findItemByItemName(item.getItemName());
                item.setUnitPrice(item.getUnitPrice());
                item.setStockIn(old.getStockIn()+Integer.valueOf(stockChange));
                item.setStockOut(old.getStockOut());
                itemRepository.save(item);
                return "redirect:/";
            } else {
                Item old = itemRepository.findItemByItemName(item.getItemName());
                item.setUnitPrice(item.getUnitPrice());
                item.setStockIn(old.getStockIn());
                item.setStockOut(old.getStockOut()+Integer.valueOf(stockChange));
                itemRepository.save(item);
                return "redirect:/";
            }
        }else{
            Item old = itemRepository.findItemByItemName(item.getItemName());
            item.setUnitPrice(item.getUnitPrice());
            item.setStockIn(old.getStockIn());
            item.setStockOut(old.getStockOut());
            itemRepository.save(item);
            return "redirect:/";
        }
    }

    @GetMapping("/item/clearstock/{id}")
    public String clearItemsStock(@PathVariable Integer id) {
        Item old = itemRepository.findItemById(id);
        old.setStockIn(0);
        old.setStockOut(0);
        itemRepository.save(old);
        return "redirect:/";
    }

    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable Integer id) {
        itemRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping(path="/item/report")
    public String getStockReport(Model model) {
        Iterable<Item> stockItems = itemRepository.findAll();
        model.addAttribute("stockItems", stockItems);
        return "StockReport";
    }
}
