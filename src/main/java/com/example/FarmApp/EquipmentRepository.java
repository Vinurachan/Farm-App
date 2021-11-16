package com.example.FarmApp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EquipmentRepository extends CrudRepository<Equipment,String> {

    Equipment findEquipmentByEquipmentName(String equipmentName);
    Equipment findEquipmentById(Integer id);

    @Transactional
    void deleteById(Integer id);
}
