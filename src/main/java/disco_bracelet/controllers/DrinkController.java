package disco_bracelet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import disco_bracelet.controllers.utils.RESTError;
import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import disco_bracelet.repositories.WaiterRepository;
import disco_bracelet.services.DrinkService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet")
public class DrinkController {

	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private BraceletRepository braceletRepository;

	@Autowired
	private WaiterRepository waiterRepository;

	@Autowired
	private DrinkService drinkService;

	@RequestMapping(method = RequestMethod.POST, path = "/drinks/newDrink")
	public ResponseEntity<?> newDrink(@Valid @RequestBody DrinkEntity drink) {
		try {
			DrinkEntity newDrink = new DrinkEntity();
			newDrink.setName(drink.getName());
			newDrink.setPrice(drink.getPrice());
			newDrink.setManufacturer(drink.getManufacturer());
			newDrink.setVolume(drink.getVolume());
			drinkRepository.save(drink);
			return new ResponseEntity<>(newDrink, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/drinks/findById/{drinkId}")
	public ResponseEntity<?> findById(@PathVariable Integer drinkId) {
		try {
			DrinkEntity drink = drinkRepository.findById(drinkId).orElse(null);
			if (drink == null) {
				return new ResponseEntity<>("Drink not found", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(drink, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/drinks/findAllDrinks")
	public ResponseEntity<?> findAllDrinks() {
		try {
			Iterable<DrinkEntity> drinks = drinkRepository.findAll();
			return new ResponseEntity<>(drinks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/drinks/changeById/{drinkId}")
	public ResponseEntity<?> changeDrink(@RequestBody DrinkEntity updateDrink, @PathVariable Integer drinkId) {
		try {
			DrinkEntity drink = drinkRepository.findById(drinkId).orElse(null);
			if (drink == null) {
				return new ResponseEntity<>("Drink not found", HttpStatus.NOT_FOUND);
			}
			drink.setName(updateDrink.getName());
			drink.setPrice(updateDrink.getPrice());
			drinkRepository.save(drink);
			return new ResponseEntity<>(drink, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/drinks/deleteById/{drinkId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer drinkId) {
		try {
			DrinkEntity drink = drinkRepository.findById(drinkId).orElse(null);
			if (drink == null) {
				return new ResponseEntity<>("Drink not found", HttpStatus.NOT_FOUND);
			}
			drinkRepository.delete(drink);
			return new ResponseEntity<>("Drink is deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getDrinksByBracelet/{braceletId}")
	public ResponseEntity<?> getDrinksByBracelet(@PathVariable Integer braceletId) {
		try {
			List<DrinkEntity> drinks = drinkService.getDrinksByBracelet(braceletId);
			if (drinks.isEmpty()) {
				return new ResponseEntity<>("No drinks found for bracelet ID " + braceletId, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(drinks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
}
