package disco_bracelet.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import disco_bracelet.controllers.utils.RESTError;
import disco_bracelet.enteties.SalesHistoryEntity;
import disco_bracelet.enteties.WaiterDrinkEntity;
import disco_bracelet.enteties.WaiterEntity;
import disco_bracelet.enteties.dtoes.WaiterDrinkDTO;
import disco_bracelet.enteties.dtoes.WaiterDrinkReportDTO;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import disco_bracelet.repositories.SalesHistoryRepository;
import disco_bracelet.repositories.WaiterDrinkRepository;
import disco_bracelet.repositories.WaiterRepository;
import disco_bracelet.services.WaiterDrinkService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet/waiters")
@CrossOrigin(origins = "http://localhost:3000")
public class WaiterController {

	@Autowired
	private WaiterRepository waiterRepository;

	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private BraceletRepository braceletRepository;

	@Autowired
	private WaiterDrinkRepository waiterDrinkRepository;

	@Autowired
	private WaiterDrinkService waiterDrinkService;
	
	@Autowired
	private SalesHistoryRepository salesHistoryRepository;

	@RequestMapping(method = RequestMethod.POST)
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

	@RequestMapping(method = RequestMethod.GET, path = "/{waiterId}")
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

	@RequestMapping(method = RequestMethod.GET)
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

	@RequestMapping(method = RequestMethod.PUT, path = "/changeById/{waiterId}")
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

	@RequestMapping(method = RequestMethod.DELETE, path = "/{waiterId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer waiterId) {
		try {
			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
			if (waiter == null) {
				return new ResponseEntity<>("Waiter not found", HttpStatus.NOT_FOUND);
			}
			waiterRepository.delete(waiter);
			return new ResponseEntity<>(Collections.singletonMap("message", "Waiter is deleted"), HttpStatus.OK);
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

//	@RequestMapping(method = RequestMethod.GET, path = "/drinksByWaiters")
//	public Map<String, Object> getDrinksByDateRange(
//	        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//	        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//
//	    if (startDate == null) {
//	        startDate = LocalDateTime.now().minusMonths(1);
//	    }
//	    if (endDate == null) {
//	        endDate = LocalDateTime.now();
//	    }
//
//	    // Logovanje datuma radi provere
//	    System.out.println("Fetching drinks from " + startDate + " to " + endDate);
//
//	    // Pozivanje metode iz servisnog sloja koja vraća WaiterDrinkEntity listu
//	    List<WaiterDrinkEntity> drinks = waiterDrinkService.getDrinksByDateRange(startDate, endDate);
//
//	    // Konverzija iz WaiterDrinkEntity u WaiterDrinkDTO
//	    List<WaiterDrinkDTO> drinkDTOs = drinks.stream().map(drink -> new WaiterDrinkDTO(
//	        drink.getId(),
//	        drink.getWaiter().getName(),
//	        drink.getDrink().getName(),
//	        drink.getQuantity(),
//	        drink.getCreatedAt(),
//	        drink.getDrink().getPrice() * drink.getQuantity()  // Pretpostavljam da imate cenu u DrinkEntity
//	    )).collect(Collectors.toList());
//
//	    // Izračunavanje ukupne zarade svih pića
//	    double totalEarnings = drinkDTOs.stream()
//	                                    .mapToDouble(WaiterDrinkDTO::getTotalPrice)
//	                                    .sum();
//
//	    // Vraćanje drinkDTOs i totalEarnings u mapu kao odgovor
//	    Map<String, Object> response = new HashMap<>();
//	    response.put("drinks", drinkDTOs);
//	    response.put("totalEarnings", totalEarnings);
//
//	    return response;
//	}
//
//
//
//	@RequestMapping(method = RequestMethod.GET, path = "/drinksByWaiter")
//	public List<WaiterDrinkDTO> getDrinksByWaiterAndDateRange(
//	        @RequestParam("waiterId") Integer waiterId,
//	        @RequestParam(value = "startDate", required = false) 
//	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//	        @RequestParam(value = "endDate", required = false) 
//	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//
//	    if (startDate == null) {
//	        startDate = LocalDateTime.now().minusMonths(1);
//	    }
//	    if (endDate == null) {
//	        endDate = LocalDateTime.now();
//	    }
//
//	    // Pozivanje metode iz servisnog sloja koja vraća WaiterDrinkEntity listu
//	    List<WaiterDrinkEntity> drinks = waiterDrinkService.getDrinksByWaiterAndDateRange(waiterId, startDate, endDate);
//
//	    // Konverzija iz WaiterDrinkEntity u WaiterDrinkDTO
//	    return drinks.stream().map(drink -> new WaiterDrinkDTO(
//	        drink.getId(),
//	        drink.getWaiter().getName(),  // Pretpostavljam da WaiterEntity ima getName() metod
//	        drink.getDrink().getName(),   // Pretpostavljam da DrinkEntity ima getName() metod
//	        drink.getQuantity(),
//	        drink.getCreatedAt(),
//	        drink.getDrink().getPrice() * drink.getQuantity()
//		)).collect(Collectors.toList());
//	}

	@RequestMapping(method = RequestMethod.GET, path = "/drinksByWaiters")
	public Map<String, Object> getDrinksByDateRange(
	        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
	        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

	    if (startDate == null) {
	        startDate = LocalDateTime.now().minusMonths(1);
	    }
	    if (endDate == null) {
	        endDate = LocalDateTime.now();
	    }

	    // Logovanje datuma radi provere
	    System.out.println("Fetching drinks from " + startDate + " to " + endDate);

	    // Pozivanje metode iz SalesHistoryRepository koja vraća SalesHistoryEntity listu
	    List<SalesHistoryEntity> salesHistory = salesHistoryRepository.findBySoldAtBetween(startDate, endDate);

	    // Konverzija iz SalesHistoryEntity u WaiterDrinkDTO
	    List<WaiterDrinkDTO> drinkDTOs = salesHistory.stream().map(sale -> new WaiterDrinkDTO(
	        sale.getId(),
	        sale.getWaiter().getName(),
	        sale.getDrink().getName(),
	        sale.getQuantity(),
	        sale.getSoldAt(),
	        sale.getDrink().getPrice() * sale.getQuantity()
	    )).collect(Collectors.toList());

	    // Izračunavanje ukupne zarade svih pića
	    double totalEarnings = drinkDTOs.stream()
	                                    .mapToDouble(WaiterDrinkDTO::getTotalPrice)
	                                    .sum();

	    // Vraćanje drinkDTOs i totalEarnings u mapu kao odgovor
	    Map<String, Object> response = new HashMap<>();
	    response.put("drinks", drinkDTOs);
	    response.put("totalEarnings", totalEarnings);

	    return response;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/drinksByWaiter")
	public List<WaiterDrinkDTO> getDrinksByWaiterAndDateRange(
	        @RequestParam("waiterId") Integer waiterId,
	        @RequestParam(value = "startDate", required = false) 
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
	        @RequestParam(value = "endDate", required = false) 
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

	    if (startDate == null) {
	        startDate = LocalDateTime.now().minusMonths(1);
	    }
	    if (endDate == null) {
	        endDate = LocalDateTime.now();
	    }

	    // Pozivanje metode iz SalesHistoryRepository koja vraća SalesHistoryEntity listu
	    WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
	    if (waiter == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter not found");
	    }
	    List<SalesHistoryEntity> salesHistory = salesHistoryRepository.findByWaiterAndSoldAtBetween(waiter, startDate, endDate);
	    System.out.println("Total drinks fetched: " + salesHistory.size());

	    // Konverzija iz SalesHistoryEntity u WaiterDrinkDTO
	    return salesHistory.stream().map(sale -> new WaiterDrinkDTO(
	        sale.getId(),
	        sale.getWaiter().getName(),
	        sale.getDrink().getName(),
	        sale.getQuantity(),
	        sale.getSoldAt(),
	        sale.getDrink().getPrice() * sale.getQuantity()
	    )).collect(Collectors.toList());
	    
	}


}
