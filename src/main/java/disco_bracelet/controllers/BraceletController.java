package disco_bracelet.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import com.google.zxing.WriterException;
import disco_bracelet.services.BraceletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import disco_bracelet.enteties.GuestEntity;
import disco_bracelet.enteties.SalesHistoryEntity;
import disco_bracelet.enteties.WaiterDrinkEntity;
import disco_bracelet.enteties.WaiterEntity;
import disco_bracelet.enteties.dtoes.BraceletDTO;
import disco_bracelet.enteties.dtoes.BraceletDrinkDTO;
import disco_bracelet.enteties.dtoes.GuestDTO;
import disco_bracelet.mappers.BraceletMapper;
import disco_bracelet.mappers.GuestMapper;
import disco_bracelet.repositories.BraceletDrinkRepository;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import disco_bracelet.repositories.GuestRepository;
import disco_bracelet.repositories.SalesHistoryRepository;
import disco_bracelet.repositories.WaiterDrinkRepository;
import disco_bracelet.repositories.WaiterRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import disco_bracelet.enteties.dtoes.DrinkRequest;


@RestController
@RequestMapping(path = "/discoBracelet/bracelets")
@CrossOrigin(origins = "http://localhost:3000")
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

	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private SalesHistoryRepository salesHistoryRepository;

	@Autowired
	private BraceletService braceletService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addBracelet(@Valid @RequestBody BraceletEntity bracelet) {
		try {
			BraceletEntity newBracelet = new BraceletEntity();
			newBracelet.setBarCode(bracelet.getBarCode());
			newBracelet.setYearOfProduction(bracelet.getYearOfProduction());
			braceletRepository.save(newBracelet);
			return new ResponseEntity<>(newBracelet, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured" + e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(method = RequestMethod.POST, path = "/generate")
	public ResponseEntity<?> generateBracelet(@RequestBody BraceletDTO request) throws IOException {
	try {
		BraceletEntity newBracelet = braceletService.createNewBracelet(request.getYearOfProduction());
		return new ResponseEntity<>(newBracelet, HttpStatus.CREATED);
	} catch (WriterException e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/{braceletId}")
	public ResponseEntity<?> newGuest(@Valid @RequestBody GuestDTO guestDTO, @PathVariable Integer braceletId) {
		try {
			// Pronađi narukvicu po ID-u
			Optional<BraceletEntity> braceletOpt = braceletRepository.findById(braceletId);
			if (!braceletOpt.isPresent()) {
				return new ResponseEntity<>(new RESTError(1, "Bracelet not found"), HttpStatus.NOT_FOUND);
			}

			BraceletEntity bracelet = braceletOpt.get();

			// Provera da li je narukvica već dodeljena nekom gostu
			if (bracelet.getGuest() != null) {
				return new ResponseEntity<>(new RESTError(3, "Bracelet is already assigned to a guest"),
						HttpStatus.BAD_REQUEST);
			}

			// Kreiraj novog gosta i dodeli mu narukvicu
			GuestEntity newGuest = new GuestEntity();
			newGuest.setName(guestDTO.getName());
			newGuest.setLastname(guestDTO.getLastname());
			newGuest.setIdDocument(guestDTO.getIdDocument());
			newGuest.setPhoneNumber(guestDTO.getPhoneNumber());
			newGuest.setBracelet(bracelet);

			// Snimi novog gosta
			newGuest = guestRepository.save(newGuest);

			// Ažuriraj narukvicu da pokazuje na novog gosta
			bracelet.setGuest(newGuest);
			braceletRepository.save(bracelet);

			GuestDTO newGuestDTO = GuestMapper.toDTO(newGuest);
			return new ResponseEntity<>(newGuestDTO, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
	

	


	@RequestMapping(method = RequestMethod.GET, path = "/{braceletId}")
	public Object getBraceletById(@PathVariable Integer braceletId) {
	    try {
	        System.out.println("Fetching bracelet with ID: " + braceletId);
	        Optional<BraceletEntity> bracelet = braceletRepository.findById(braceletId);
	        System.out.println("Fetched bracelet: " + bracelet);
	        return ResponseEntity.ok(bracelet);
	    } catch (Exception e) {
	        e.printStackTrace(); // Loguj detaljno izuzetke
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
//	public ResponseEntity<?> getBraceletById(@PathVariable Integer braceletId) {
//		try {
//			System.out.println("Received request for bracelet with ID: " + braceletId); // Loguj ID
//			Optional<BraceletEntity> braceletOpt = braceletRepository.findById(braceletId);
//			if (braceletOpt.isPresent()) {
//				return new ResponseEntity<>(braceletOpt.get(), HttpStatus.OK);
//			} else {
//				return new ResponseEntity<>(new RESTError(1, "Bracelet not found"), HttpStatus.NOT_FOUND);
//			}
//		} catch (Exception e) {
//			System.out.println("Exception occurred: " + e.getMessage()); // Loguj grešku
//			return new ResponseEntity<>(new RESTError(2, "Exception occurred " + e.getMessage()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	public ResponseEntity<?> getBraceletById(@PathVariable Integer braceletId) {
//		try {
//			BraceletEntity bracelet = braceletRepository.findById(braceletId).get();
//			if (bracelet == null) {
//				return new ResponseEntity<>(new RESTError(1, "Bracelet not found"), HttpStatus.BAD_REQUEST);
//			} else {
//				return new ResponseEntity<>(bracelet, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(2, "Exception occured " + e.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllBracelets() {
		try {
			List<BraceletEntity> bracelets = (List<BraceletEntity>) braceletRepository.findAll();

			List<BraceletDTO> braceletDTOs = bracelets.stream().map(bracelet -> {
				BraceletDTO dto = new BraceletDTO();
				dto.setId(bracelet.getId());
				dto.setBarCode(bracelet.getBarCode());
				dto.setYearOfProduction(bracelet.getYearOfProduction());

				if (bracelet.getGuest() != null) {
					dto.setId(bracelet.getGuest().getId());
				}

				return dto;
			}).collect(Collectors.toList());

			return new ResponseEntity<>(braceletDTOs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<?> getAllBracelets() {
//		try {
//			Iterable<BraceletEntity> bracelets = braceletRepository.findAll();
//			return new ResponseEntity<>(bracelets, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//	}

//	@RequestMapping(method = RequestMethod.PUT, path = "/changeById/{braceletId}")
//	public ResponseEntity<?> changeBracelet(@RequestBody BraceletEntity updatedBracelet,
//			@PathVariable Integer braceletId) {
//		try {
//			BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
//			if (bracelet == null) {
//				return new ResponseEntity<>("Bracelet not found", HttpStatus.NOT_FOUND);
//			}
//			bracelet.setManufacturer(updatedBracelet.getManufacturer());
//			bracelet.setYearOfProduction(updatedBracelet.getYearOfProduction());
//			braceletRepository.save(updatedBracelet);
//			return new ResponseEntity<>(bracelet, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//	}

	@RequestMapping(method = RequestMethod.PUT, path = "/changeById/{braceletId}")
//	public ResponseEntity<?> changeBracelet(@RequestBody BraceletDTO updatedBracelet,
//	                                        @PathVariable Integer braceletId) {
//	    try {
//	        BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
//	        if (bracelet == null) {
//	            return new ResponseEntity<>("Bracelet not found", HttpStatus.NOT_FOUND);
//	        }
//	        bracelet.setManufacturer(updatedBracelet.getManufacturer());
//	        bracelet.setYearOfProduction(updatedBracelet.getYearOfProduction());
//	        braceletRepository.save(bracelet);
//	        return new ResponseEntity<>(bracelet, HttpStatus.OK);
//	    } catch (Exception e) {
//	        return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()), HttpStatus.BAD_REQUEST);
//	    }
//	}
	public ResponseEntity<?> changeBracelet(@RequestBody BraceletDTO updatedBracelet,
			@PathVariable Integer braceletId) {
		try {
//			System.out.println("Updating bracelet with ID: " + braceletId); // Loguj ID
			Optional<BraceletEntity> braceletOpt = braceletRepository.findById(braceletId);
			if (braceletOpt.isPresent()) {
				BraceletEntity bracelet = braceletOpt.get();
				bracelet.setBarCode(updatedBracelet.getBarCode());
				bracelet.setYearOfProduction(updatedBracelet.getYearOfProduction());
				braceletRepository.save(bracelet);
				return new ResponseEntity<>(bracelet, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new RESTError(1, "Bracelet not found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
//			System.out.println("Exception occurred: " + e.getMessage()); // Loguj grešku
			return new ResponseEntity<>(new RESTError(2, "Exception occurred " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{braceletId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer braceletId) {
		BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
		try {

			if (bracelet == null) {
				return new ResponseEntity<>("Bracelet not found", HttpStatus.NOT_FOUND);
			}
			braceletRepository.delete(bracelet);
			return new ResponseEntity<>(Collections.singletonMap("message", "Bracelet is deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

	//metoda za pregled slobodnih narukvica
	@RequestMapping(method = RequestMethod.GET, path = "/availableBracelets")
	public ResponseEntity<?> getAvailableBracelets() {
		try {
			List<BraceletEntity> availableBracelets = braceletRepository.findByGuestIsNull();
			return new ResponseEntity<>(availableBracelets, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	//metoda za broj slobodnih narukvica
	@RequestMapping(method = RequestMethod.GET, path = "/availableBracelets/count")
	public ResponseEntity<?> getAvailableBraceletsCount() {
	    try {
	        long availableBraceletsCount = braceletRepository.countByGuestIsNull();
	        return new ResponseEntity<>(availableBraceletsCount, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(2, "Exception occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}

	//metoda za dodelu pica konobaru
	 @RequestMapping(method = RequestMethod.POST, path = "/assignDrinksToWaiter/{waiterId}")
	    public ResponseEntity<?> assignDrinksToWaiter(@RequestBody List<DrinkRequest> drinks, @PathVariable Integer waiterId) {
	        try {
	            WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
	            if (waiter == null) {
	                return new ResponseEntity<>("Waiter with ID " + waiterId + " not found!", HttpStatus.NOT_FOUND);
	            }

	            for (DrinkRequest drinkRequest : drinks) {
	                DrinkEntity drink = drinkRepository.findById(drinkRequest.getDrinkId()).orElse(null);
	                if (drink == null) {
	                    return new ResponseEntity<>("Drink with ID " + drinkRequest.getDrinkId() + " not found!", HttpStatus.NOT_FOUND);
	                }

	                WaiterDrinkEntity waiterDrink = waiterDrinkRepository.findByWaiterAndDrink(waiter, drink);
	                if (waiterDrink == null) {
	                    waiterDrink = new WaiterDrinkEntity();
	                    waiterDrink.setWaiter(waiter);
	                    waiterDrink.setDrink(drink);
	                    waiterDrink.setQuantity(drinkRequest.getQuantity());
	                } else {
	                    waiterDrink.setQuantity(waiterDrink.getQuantity() + drinkRequest.getQuantity());
	                }
	                waiterDrinkRepository.save(waiterDrink);
	            }

	            return new ResponseEntity<>("Drinks assigned to waiter successfully", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
	                    HttpStatus.BAD_REQUEST);
	        }
	    }



//	@Transactional
//	@RequestMapping(method = RequestMethod.POST, path = "/assignDrinksToWaiter/{waiterId}")
//	public ResponseEntity<?> assignDrinksToWaiter(@RequestBody List<Integer> drinksId, @PathVariable Integer waiterId) {
//		try {
//			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
//			if (waiter == null) {
//				return new ResponseEntity<>("Waiter with ID " + waiterId + " not found!", HttpStatus.NOT_FOUND);
//			}
//			for (Integer drinkId : drinksId) {
//				DrinkEntity drink = drinkRepository.findById(drinkId).orElse(null);
//				if (drink == null) {
//					return new ResponseEntity<>("Drink with ID " + drinkId + " not found!", HttpStatus.NOT_FOUND);
//				}
//				WaiterDrinkEntity waiterDrink = waiterDrinkRepository.findByWaiterAndDrink(waiter, drink);
//				if (waiterDrink == null) {
//					waiterDrink = new WaiterDrinkEntity();
//					waiterDrink.setWaiter(waiter);
//					waiterDrink.setDrink(drink);
//					waiterDrink.setQuantity(1);
//				} else {
//					Integer currentQuantity = waiterDrink.getQuantity();
//					if (currentQuantity == null) {
//						currentQuantity = 1;
//					}
//					waiterDrink.setQuantity(waiterDrink.getQuantity() + 1);
//				}
//				waiterDrinkRepository.save(waiterDrink);
//
//			}
//
//			return new ResponseEntity<>("Drinks assigned to waiter successfully", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//	}

	 //metoda za pregled pica po narukvici
	 @RequestMapping(method = RequestMethod.GET, path = "/bracelet/{braceletId}/drinks")
	 public ResponseEntity<?> getDrinksByBracelet(@PathVariable Integer braceletId) {
	     try {
	         BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
	         if (bracelet == null) {
	             return new ResponseEntity<>("Bracelet not found", HttpStatus.NOT_FOUND);
	         }

	         List<BraceletDrinkEntity> braceletDrinks = braceletDrinkRepository.findByBracelet(bracelet);

	         // Loguj podatke da proveriš da li su pića ispravno učitana
	         for (BraceletDrinkEntity bd : braceletDrinks) {
	             System.out.println("Drink ID: " + bd.getDrink().getId());
	             System.out.println("Drink Name: " + bd.getDrink().getName());
	             System.out.println("Drink Price: " + bd.getDrink().getPrice());
	         }

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


	
//metoda za dodelu pica narukvici
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

	             // Dodavanje u SalesHistoryEntity
	             SalesHistoryEntity salesHistory = new SalesHistoryEntity();
	             salesHistory.setWaiter(waiter);
	             salesHistory.setDrink(drink);
	             salesHistory.setQuantity(assignment.getQuantity());
	             salesHistory.setSoldAt(LocalDateTime.now());
	             salesHistoryRepository.save(salesHistory);

	             // Ažuriranje stanja na narukvici
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

//	@Transactional
//	@RequestMapping(method = RequestMethod.POST, path = "/assignDrinksToBracelets/{waiterId}")
//	public ResponseEntity<?> assignDrinksToBracelets(@PathVariable Integer waiterId,
//			@RequestBody List<BraceletDrinkDTO> drinkAssignments) {
//		try {
//			WaiterEntity waiter = waiterRepository.findById(waiterId).orElse(null);
//			if (waiter == null) {
//				return new ResponseEntity<>("Waiter not found", HttpStatus.NOT_FOUND);
//			}
//
//			for (BraceletDrinkDTO assignment : drinkAssignments) {
//				DrinkEntity drink = drinkRepository.findById(assignment.getDrinkId()).orElse(null);
//				BraceletEntity bracelet = braceletRepository.findById(assignment.getBraceletId()).orElse(null);
//
//				if (drink == null) {
//					return new ResponseEntity<>("Drink with ID " + assignment.getDrinkId() + " not found",
//							HttpStatus.NOT_FOUND);
//				}
//
//				if (bracelet == null) {
//					return new ResponseEntity<>("Bracelet with ID " + assignment.getBraceletId() + " not found",
//							HttpStatus.NOT_FOUND);
//				}
//
//				// Proveri da li je piće dodeljeno konobaru
//				WaiterDrinkEntity waiterDrink = waiterDrinkRepository.findByWaiterAndDrink(waiter, drink);
//
//				if (waiterDrink == null || waiterDrink.getQuantity() < assignment.getQuantity()) {
//					return new ResponseEntity<>("Not enough drinks on the waiter's stock", HttpStatus.BAD_REQUEST);
//				}
//
//				waiterDrink.setQuantity(waiterDrink.getQuantity() - assignment.getQuantity());
//				waiterDrinkRepository.save(waiterDrink);
//
//				BraceletDrinkEntity braceletDrink = braceletDrinkRepository.findByBraceletAndDrink(bracelet, drink);
//				if (braceletDrink == null) {
//					braceletDrink = new BraceletDrinkEntity();
//					braceletDrink.setBracelet(bracelet);
//					braceletDrink.setDrink(drink);
//					braceletDrink.setQuantity(assignment.getQuantity());
//				} else {
//					braceletDrink.setQuantity(braceletDrink.getQuantity() + assignment.getQuantity());
//				}
//				braceletDrinkRepository.save(braceletDrink);
//			}
//
//			return new ResponseEntity<>("Drinks assigned to bracelets successfully", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//	}
	
	//metoda za prikaz narukvica koje imaju zaduzena pica 
//	@RequestMapping(method = RequestMethod.GET, path = "/withDrinks")
//	public ResponseEntity<?> getBraceletsWithDrinks() {
//	    try {
//	        List<BraceletEntity> braceletsWithDrinks = braceletRepository.findBraceletsWithDrinks();  Ovo treba implementirati
//	        return new ResponseEntity<>(braceletsWithDrinks, HttpStatus.OK);
//	    } catch (Exception e) {
//	        return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
//	    }
//	}



}
