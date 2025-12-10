package spring.demo.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.demo.annotation.dao.AnimalDao;
import spring.demo.annotation.model.Animal;
import spring.demo.annotation.model.AnimalType;

@Component
@Transactional
public class AnimalService {
    @Autowired
    AnimalDao animalDao;

    public Animal createAnimal(AnimalType type, String ownerId, String name, int age) {
        return animalDao.createAnimal(type, ownerId, name, age);
    }
}
