package disco_bracelet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;

@Service
public class DrinkService {

	@Autowired
	private BraceletRepository braceletRepository;
	
	@Autowired
	private DrinkRepository drinkRepository;
	
	public List<DrinkEntity> getDrinksByBracelet(Integer braceletId){
		BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
		if(bracelet == null) {
			throw new RuntimeException("Bracelet not found!");
		}
		return drinkRepository.findByBracelet(bracelet);
	}
}
