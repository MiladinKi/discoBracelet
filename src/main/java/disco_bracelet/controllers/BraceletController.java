package disco_bracelet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import disco_bracelet.controllers.utils.RESTError;
import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.repositories.BraceletRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet")
public class BraceletController {
	
	@Autowired
	private BraceletRepository braceletRepository;
	
	@RequestMapping(method = RequestMethod.POST, path = "/newBracelet")
	public ResponseEntity<?> addBracelet(@Valid @RequestBody BraceletEntity bracelet){
		try {
			BraceletEntity newBracelet = new BraceletEntity();
			newBracelet.setManufacturer(bracelet.getManufacturer());
			newBracelet.setYearOfProduction(bracelet.getYearOfProduction());
			braceletRepository.save(newBracelet);
			return new ResponseEntity<>(newBracelet, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured" + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
	}

}
