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
import disco_bracelet.enteties.BraceletDrinkEntity;
import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.enteties.GuestEntity;
import disco_bracelet.repositories.BraceletDrinkRepository;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import disco_bracelet.repositories.GuestRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet")
public class GuestController {

	@Autowired
	private GuestRepository guestRepository;
	@Autowired
	private BraceletRepository braceletRepository;

	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private BraceletDrinkRepository braceletDrinkRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/newGuest/braceletId/{braceletId}")
	public ResponseEntity<?> newGuest(@Valid @RequestBody GuestEntity guest, @PathVariable Integer braceletId) {
		try {
			GuestEntity newGuest = new GuestEntity();
			BraceletEntity bracelet = braceletRepository.findById(braceletId).get();
			if (bracelet == null) {
				return new ResponseEntity<>(new RESTError(1, "Bracelet not found"), HttpStatus.NOT_FOUND);

			}
			newGuest.setName(guest.getName());
			newGuest.setLastname(guest.getLastname());
			newGuest.setIdDocument(guest.getIdDocument());
			newGuest.setPhoneNumber(guest.getPhoneNumber());
			newGuest.setBracelet(bracelet);
			guestRepository.save(newGuest);
			return new ResponseEntity<>(newGuest, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(2, "Exception occured" + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getAllPrice/{braceletId}")
	public ResponseEntity<?> getAllPriceByBracelet(@PathVariable Integer braceletId) {
		try {
			BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
			if (bracelet == null) {
				return new ResponseEntity<>("Bracelet not found", HttpStatus.NOT_FOUND);
			}

			List<BraceletDrinkEntity> braceletDrinks = braceletDrinkRepository.findByBracelet(bracelet);

			double total = 0.0;
			for (BraceletDrinkEntity braceletDrink : braceletDrinks) {
				total += braceletDrink.getDrink().getPrice() * braceletDrink.getQuantity();
			}

			return new ResponseEntity<>("Bill for braceletID " + braceletId + " is " + total, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/clearGuest/{braceletId}")
	public ResponseEntity<?> clearGuest(@PathVariable Integer braceletId) {
		try {
			BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
			if (bracelet == null) {
				return new ResponseEntity<>("Bravelet not found", HttpStatus.BAD_REQUEST);
			}
			List<BraceletDrinkEntity> braceletDrinks = braceletDrinkRepository.findByBracelet(bracelet);

			for (BraceletDrinkEntity braceletDrink : braceletDrinks) {
				braceletDrinkRepository.delete(braceletDrink);
			}

			bracelet.setGuest(null);

			braceletRepository.save(bracelet);
			return new ResponseEntity<>("Bracelet cleared successfully", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteGuest/{guestId}")
	public ResponseEntity<?> deleteGuest(@PathVariable Integer guestId) {
		try {
			GuestEntity guest = guestRepository.findById(guestId).orElse(null);
			if (guest == null) {
				return new ResponseEntity<>("Guest not found", HttpStatus.BAD_REQUEST);
			}
			guestRepository.delete(guest);
			return new ResponseEntity<>("Guest is deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
}
