package com.example.FarmApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LabourerController {

    @Autowired
    private LabourerRepository labourerRepository;
    private List<String> message = new ArrayList<>();

    @GetMapping(path="/labourer")
    public String LabourersDisplay(Model model) {
        Iterable<Labourer> listLabourers = labourerRepository.findAll();
        model.addAttribute("listLabourers", listLabourers);
        return "Labourer";
    }

    @PostMapping(path = "/labourer/add")
    public String addNewLabourer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age,
                                 @RequestParam String address, @RequestParam int mobileNumber, Model model){
        if(!message.isEmpty()){
            message.remove(0);
        }
        if (labourerRepository.findLabourerByMobileNumber(mobileNumber)==null){
            Labourer labourer = new Labourer();
            labourer.setFirstName(firstName);
            labourer.setLastName(lastName);
            labourer.setAge(age);
            labourer.setAddress(address);
            labourer.setMobileNumber(mobileNumber);
            labourerRepository.save(labourer);
            message.add(firstName+" "+lastName+" added to the Labourer List");
            model.addAttribute("alert",message);
            return "AddLabourer";
        }else {
            message.add("Labourer already exists. Please update personal details");
            model.addAttribute("alert",message);
            return "AddLabourer";
        }
    }

    @GetMapping(path="/labourer/update/{id}")
    public ModelAndView selectLabourer(@PathVariable("id") Integer id) {
        ModelAndView mav = new ModelAndView("EditLabourer");
        Labourer labourer = labourerRepository.findLabourerById(id);
        mav.addObject("labourer", labourer);
        return mav;
    }

    @PostMapping(path = "/labourer/update")
    public String updateLabourerDetails(@ModelAttribute("labourer") Labourer labourer) {
        labourerRepository.save(labourer);
        return "redirect:/labourer";
    }

    @GetMapping("/labourer/delete/{id}")
    public String deleteLabourer(@PathVariable Integer id) {
        labourerRepository.deleteById(id);
        return "redirect:/labourer";
    }
}
