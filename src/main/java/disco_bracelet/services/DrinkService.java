package disco_bracelet.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.enteties.dtoes.DrinkDTO;
import disco_bracelet.mappers.DrinkMapper;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import java.util.Optional;

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
	
	
	  public DrinkDTO findById(Integer id) {
	        Optional<DrinkEntity> optionalDrink = drinkRepository.findById(id);
	        if (optionalDrink.isPresent()) {
	            return DrinkMapper.toDTO(optionalDrink.get());
	        }
	        return null; // Ili baci izuzetak ako je to prikladno
	    }
	
}
