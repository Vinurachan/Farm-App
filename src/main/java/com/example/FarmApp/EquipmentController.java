package com.example.FarmApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EquipmentController {

    @Autowired
    private EquipmentRepository equipmentRepository;
    private List<String> message = new ArrayList<>();

    @GetMapping(path="/equipment")
    public String EquipmentDisplay(Model model) {
        Iterable<Equipment> listEquipment = equipmentRepository.findAll();
        model.addAttribute("listEquipment", listEquipment);
        return "Equipment";
    }

    @PostMapping(path = "/equipment/add")
    public String addNewEquipment(@RequestParam String equipmentName, @RequestParam int units, Model model){
        if(!message.isEmpty()){
            message.remove(0);
        }
        if (equipmentRepository.findEquipmentByEquipmentName(equipmentName)==null){
            Equipment equipment = new Equipment();
            equipment.setEquipmentName(equipmentName);
            equipment.setUnits(units);
            equipmentRepository.save(equipment);
            message.add(equipmentName+" added to the Equipment List");
            model.addAttribute("alert",message);
            return "AddEquipment";
        }else {
            message.add("Equipment already exists. Please update equipment details");
            model.addAttribute("alert",message);
            return "AddEquipment";
        }
    }

    @GetMapping(path="/equipment/update/{id}")
    public ModelAndView selectEquipment(@PathVariable("id") Integer id) {
        ModelAndView mav = new ModelAndView("EditEquipment");
        Equipment equipment = equipmentRepository.findEquipmentById(id);
        mav.addObject("equipment", equipment);
        return mav;
    }

    @PostMapping(path = "/equipment/update")
    public String updateEquipmentDetails(@ModelAttribute("equipment") Equipment equipment) {
        equipmentRepository.save(equipment);
        return "redirect:/equipment";
    }

    @GetMapping("/equipment/delete/{id}")
    public String deleteEquipment(@PathVariable Integer id) {
        equipmentRepository.deleteById(id);
        return "redirect:/equipment";
    }
}
