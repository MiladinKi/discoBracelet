package disco_bracelet.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import disco_bracelet.controllers.utils.RESTError;
import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import disco_bracelet.repositories.WaiterRepository;
import disco_bracelet.services.DrinkService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet/drinks")
@CrossOrigin(origins = "http://localhost:3000")
public class DrinkController {

	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private BraceletRepository braceletRepository;

	@Autowired
	private WaiterRepository waiterRepository;

	@Autowired
	private DrinkService drinkService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> newDrink(@RequestParam("name") String name, @RequestParam("price") Double price,
			@RequestParam("manufacturer") String manufacturer, @RequestParam("volume") Double volume,
			@RequestParam("drinkImage") MultipartFile imageFile) {
		try {
			DrinkEntity newDrink = new DrinkEntity();
			newDrink.setName(name);
			newDrink.setPrice(price);
			newDrink.setManufacturer(manufacturer);
			newDrink.setVolume(volume);

			// Snimi sliku i dobij URL ili putanju do slike
			String drinkImage = saveImage(imageFile);
			newDrink.setDrinkImage(drinkImage);

			// Sačuvaj novi entitet u bazu
			drinkRepository.save(newDrink);

			return new ResponseEntity<>(newDrink, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{drinkId}")
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

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAllDrinks() {
		try {
			Iterable<DrinkEntity> drinks = drinkRepository.findAll();
			return new ResponseEntity<>(drinks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/changeById/{drinkId}")
	public ResponseEntity<?> changeDrink(@PathVariable Integer drinkId, 
	                                     @RequestPart(value = "drinkImage", required = false) MultipartFile drinkImage,
	                                     @RequestParam("name") String name,
	                                     @RequestParam("price") Double price,
	                                     @RequestParam("manufacturer") String manufacturer,
	                                     @RequestParam("volume") Double volume) {
	    try {
	        DrinkEntity drink = drinkRepository.findById(drinkId).orElse(null);
	        if (drink == null) {
	            return new ResponseEntity<>("Drink not found", HttpStatus.NOT_FOUND);
	        }
	        
	        drink.setName(name);
	        drink.setPrice(price);
	        drink.setManufacturer(manufacturer);
	        drink.setVolume(volume);

	        if (drinkImage != null && !drinkImage.isEmpty()) {
	            String fileName = saveImage(drinkImage);
	            drink.setDrinkImage(fileName); // Pretpostavlja se da imaš polje `image` u `DrinkEntity`
	        }

	        drinkRepository.save(drink);
	        return new ResponseEntity<>(drink, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}


	@RequestMapping(method = RequestMethod.DELETE, path = "/{drinkId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer drinkId) {
		try {
			DrinkEntity drink = drinkRepository.findById(drinkId).orElse(null);
			if (drink == null) {
				return new ResponseEntity<>("Drink not found", HttpStatus.NOT_FOUND);
			}
			drinkRepository.delete(drink);
			return new ResponseEntity<>(Collections.singletonMap("message", "Drink is deleted"), HttpStatus.OK);
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

	// metoda za cuvanje slika
	private String saveImage(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		String uploadDir = "D:/Programiranje/sts/discoBracelet/src/main/resources/images";
		File dest = new File(uploadDir + "/" + fileName);
		file.transferTo(dest);
		return fileName; // Primer URL-a do slike
	}
}
