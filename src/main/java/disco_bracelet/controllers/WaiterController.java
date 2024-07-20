package disco_bracelet.controllers;

import java.util.ArrayList;
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
import disco_bracelet.enteties.WaiterDrinkEntity;
import disco_bracelet.enteties.WaiterEntity;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import disco_bracelet.repositories.WaiterDrinkRepository;
import disco_bracelet.repositories.WaiterRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet")
public class WaiterController {

	@Autowired
	private WaiterRepository waiterRepository;

	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private BraceletRepository braceletRepository;

	@Autowired
	private WaiterDrinkRepository waiterDrinkRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/waiters/newWaiter")
	public ResponseEntity<?> addWaiter(@Valid @RequestBody WaiterEntity waiter) {
		try {
			WaiterEntity newWaiter = new WaiterEntity();
			newWaiter.setName(waiter.getName());
			newWaiter.setLastname(waiter.getLastname());
			newWaiter.setUsername(waiter.getUsername());
			newWaiter.setPassword(waiter.getPassword());
			waiterRepository.save(newWaiter);
			return new ResponseEntity<>(newWaiter, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/waiters/byWaiterId/{waiterId}")
	public ResponseEntity<?> getById(@PathVariable Integer waiterId) {
		try {
			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
			if (waiter == null) {
				return new ResponseEntity<>(new RESTError(1, "Waiter not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(waiter, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(2, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/waiters/allWaiters")
	public ResponseEntity<?> getAllWaiters() {
		try {
			Iterable<WaiterEntity> waiters = new ArrayList<>();
			waiters = waiterRepository.findAll();
			return new ResponseEntity<>(waiters, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/waiters/changeById/{waiterId}")
	public ResponseEntity<?> changeWaiter(@RequestBody WaiterEntity updatedWaiter, @PathVariable Integer waiterId) {
		try {
			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
			if (waiter == null) {
				return new ResponseEntity<>("Waiter not found", HttpStatus.NOT_FOUND);
			}
			waiter.setName(updatedWaiter.getName());
			waiter.setLastname(updatedWaiter.getLastname());
			waiter.setUsername(updatedWaiter.getUsername());
			waiter.setPassword(updatedWaiter.getPassword());
			waiterRepository.save(waiter);
			return new ResponseEntity<>(waiter, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/waiters/deleteById/{waiterId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer waiterId) {
		try {
			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
			if (waiter == null) {
				return new ResponseEntity<>("Waiter not found", HttpStatus.NOT_FOUND);
			}
			waiterRepository.delete(waiter);
			return new ResponseEntity<>("Waiter is deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/clearWaiterStock/{waiterId}")
	public ResponseEntity<?> clearWaiterStock(@PathVariable Integer waiterId) {
		try {
			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
			if (waiter == null) {
				return new ResponseEntity<>("Waiter not found", HttpStatus.BAD_REQUEST);
			}
			List<WaiterDrinkEntity> waiterDrinks = waiterDrinkRepository.findByWaiter(waiter);
			for (WaiterDrinkEntity waiterDrink : waiterDrinks) {
				waiterDrinkRepository.delete(waiterDrink);
			}
			return new ResponseEntity<>("Waiter stock cleared successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
}
