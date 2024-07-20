package disco_bracelet.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import aj.org.objectweb.asm.commons.Method;
import disco_bracelet.controllers.utils.RESTError;
import disco_bracelet.enteties.BraceletDrinkEntity;
import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.enteties.WaiterDrinkEntity;
import disco_bracelet.enteties.WaiterEntity;
import disco_bracelet.enteties.dtoes.BraceletDrinkDTO;
import disco_bracelet.repositories.BraceletDrinkRepository;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import disco_bracelet.repositories.WaiterDrinkRepository;
import disco_bracelet.repositories.WaiterRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet")
public class BraceletController {

	@Autowired
	private BraceletRepository braceletRepository;

	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private WaiterRepository waiterRepository;

	@Autowired
	private WaiterDrinkRepository waiterDrinkRepository;

	@Autowired
	private BraceletDrinkRepository braceletDrinkRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/newBracelet")
	public ResponseEntity<?> addBracelet(@Valid @RequestBody BraceletEntity bracelet) {
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

	@RequestMapping(method = RequestMethod.GET, path = "/braceletId/{braceletId}")
	public ResponseEntity<?> getBraceletById(@PathVariable Integer braceletId) {
		try {
			BraceletEntity bracelet = braceletRepository.findById(braceletId).get();
			if (bracelet == null) {
				return new ResponseEntity<>(new RESTError(1, "Bracelet not found"), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(bracelet, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(2, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/allBracelets")
	public ResponseEntity<?> getAllBracelets() {
		try {
			Iterable<BraceletEntity> bracelets = braceletRepository.findAll();
			return new ResponseEntity<>(bracelets, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/changeById/{braceletId}")
	public ResponseEntity<?> changeBracelet(@RequestBody BraceletEntity updatedBracelet,
			@PathVariable Integer braceletId) {
		try {
			BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
			if (bracelet == null) {
				return new ResponseEntity<>("Bracelet not found", HttpStatus.NOT_FOUND);
			}
			bracelet.setManufacturer(updatedBracelet.getManufacturer());
			bracelet.setYearOfProduction(updatedBracelet.getYearOfProduction());
			braceletRepository.save(updatedBracelet);
			return new ResponseEntity<>(bracelet, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteById/{braceletId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer braceletId) {
		BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
		try {

			if (bracelet == null) {
				return new ResponseEntity<>("Bracelet not found", HttpStatus.NOT_FOUND);
			}
			braceletRepository.delete(bracelet);
			return new ResponseEntity<>("Bracelet is deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "/assignDrinksToWaiter/{waiterId}")
	public ResponseEntity<?> assignDrinksToWaiter(@RequestBody List<Integer> drinksId, @PathVariable Integer waiterId) {
		try {
			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
			if (waiter == null) {
				return new ResponseEntity<>("Waiter with ID " + waiterId + " not found!", HttpStatus.NOT_FOUND);
			}
			for (Integer drinkId : drinksId) {
				DrinkEntity drink = drinkRepository.findById(drinkId).orElse(null);
				if (drink == null) {
					return new ResponseEntity<>("Drink with ID " + drinkId + " not found!", HttpStatus.NOT_FOUND);
				}
				WaiterDrinkEntity waiterDrink = waiterDrinkRepository.findByWaiterAndDrink(waiter, drink);
				if (waiterDrink == null) {
					waiterDrink = new WaiterDrinkEntity();
					waiterDrink.setWaiter(waiter);
					waiterDrink.setDrink(drink);
					waiterDrink.setQuantity(1);
				} else {
					Integer currentQuantity = waiterDrink.getQuantity();
					if (currentQuantity == null) {
						currentQuantity = 1;
					}
					waiterDrink.setQuantity(waiterDrink.getQuantity() + 1);
				}
				waiterDrinkRepository.save(waiterDrink);

			}

			return new ResponseEntity<>("Drinks assigned to waiter successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/bracelet/{braceletId}/drinks")
	public ResponseEntity<?> getDrinksByBracelet(@PathVariable Integer braceletId) {
		try {
			BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
			if (bracelet == null) {
				return new ResponseEntity<>("Bracelet not found", HttpStatus.NOT_FOUND);
			}

			List<BraceletDrinkEntity> braceletDrinks = braceletDrinkRepository.findByBracelet(bracelet);
			List<BraceletDrinkDTO> dtoList = braceletDrinks.stream()
					.map(bd -> new BraceletDrinkDTO(bd.getDrink().getId(), bd.getDrink().getName(),
							bd.getDrink().getPrice(), bd.getQuantity(), bracelet.getId()))
					.collect(Collectors.toList());

			return new ResponseEntity<>(dtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "/assignDrinksToBracelets/{waiterId}")
	public ResponseEntity<?> assignDrinksToBracelets(@PathVariable Integer waiterId,
			@RequestBody List<BraceletDrinkDTO> drinkAssignments) {
		try {
			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
			if (waiter == null) {
				return new ResponseEntity<>("Waiter not found", HttpStatus.NOT_FOUND);
			}

			for (BraceletDrinkDTO assignment : drinkAssignments) {
				DrinkEntity drink = drinkRepository.findById(assignment.getDrinkId()).orElse(null);
				BraceletEntity bracelet = braceletRepository.findById(assignment.getBraceletId()).orElse(null);

				if (drink == null) {
					return new ResponseEntity<>("Drink with ID " + assignment.getDrinkId() + " not found",
							HttpStatus.NOT_FOUND);
				}

				if (bracelet == null) {
					return new ResponseEntity<>("Bracelet with ID " + assignment.getBraceletId() + " not found",
							HttpStatus.NOT_FOUND);
				}

				// Proveri da li je piće dodeljeno konobaru
				WaiterDrinkEntity waiterDrink = waiterDrinkRepository.findByWaiterAndDrink(waiter, drink);

				if (waiterDrink == null || waiterDrink.getQuantity() < assignment.getQuantity()) {
					return new ResponseEntity<>("Not enough drinks on the waiter's stock", HttpStatus.BAD_REQUEST);
				}

				waiterDrink.setQuantity(waiterDrink.getQuantity() - assignment.getQuantity());
				waiterDrinkRepository.save(waiterDrink);

				BraceletDrinkEntity braceletDrink = braceletDrinkRepository.findByBraceletAndDrink(bracelet, drink);
				if (braceletDrink == null) {
					braceletDrink = new BraceletDrinkEntity();
					braceletDrink.setBracelet(bracelet);
					braceletDrink.setDrink(drink);
					braceletDrink.setQuantity(assignment.getQuantity());
				} else {
					braceletDrink.setQuantity(braceletDrink.getQuantity() + assignment.getQuantity());
				}
				braceletDrinkRepository.save(braceletDrink);
			}

			return new ResponseEntity<>("Drinks assigned to bracelets successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

}
