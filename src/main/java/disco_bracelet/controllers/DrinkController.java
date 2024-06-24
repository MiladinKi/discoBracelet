package disco_bracelet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import disco_bracelet.controllers.utils.RESTError;
import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.repositories.DrinkRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet")
public class DrinkController {
	
	@Autowired
	private DrinkRepository drinkRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/newDrink")
	public ResponseEntity<?> newDrink(@Valid @RequestBody DrinkEntity drink){
		try {
			DrinkEntity newDrink = new DrinkEntity();
			newDrink.setName(drink.getName());
			newDrink.setPrice(drink.getPrice());
			drinkRepository.save(drink);
			return new ResponseEntity<>(newDrink, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
