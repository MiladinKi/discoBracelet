package disco_bracelet.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.repositories.BraceletRepository;

import java.util.List;

@Service
public class BraceletService {

    @Autowired
    private BraceletRepository braceletRepository;

//    public List<BraceletEntity> getBraceletsWithDrinks() {
//        return braceletRepository.findBraceletsWithDrinks();
//    }
}

