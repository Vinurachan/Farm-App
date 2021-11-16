package com.example.FarmApp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LabourerRepository extends CrudRepository<Labourer,Integer> {

    Labourer findLabourerByMobileNumber(int mobileNumber);
    Labourer findLabourerById(Integer id);

    @Transactional
    void deleteById(Integer id);
}
