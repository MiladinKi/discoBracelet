package disco_bracelet.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import disco_bracelet.enteties.WaiterEntity;
import disco_bracelet.enteties.dtoes.GuestDTO;
import disco_bracelet.mappers.GuestMapper;
import disco_bracelet.repositories.BraceletDrinkRepository;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.DrinkRepository;
import disco_bracelet.repositories.GuestRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet/guests")
@CrossOrigin(origins = "http://localhost:3000")
public class GuestController {

	@Autowired
	private GuestRepository guestRepository;
	@Autowired
	private BraceletRepository braceletRepository;

	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private BraceletDrinkRepository braceletDrinkRepository;

//	@RequestMapping(method = RequestMethod.POST, path = "/{braceletId}")
//	public ResponseEntity<?> newGuest(@Valid @RequestBody GuestEntity guest, @PathVariable Integer braceletId) {
//		try {
//			// Pronađi narukvicu po ID-u
//			Optional<BraceletEntity> braceletOpt = braceletRepository.findById(braceletId);
//			if (!braceletOpt.isPresent()) {
//				return new ResponseEntity<>(new RESTError(1, "Bracelet not found"), HttpStatus.NOT_FOUND);
//			}
//
//			BraceletEntity bracelet = braceletOpt.get();
//
//			// Provera da li je narukvica već dodeljena nekom gostu
//			if (bracelet.getGuest() != null) {
//				return new ResponseEntity<>(new RESTError(3, "Bracelet is already assigned to a guest"),
//						HttpStatus.BAD_REQUEST);
//			}
//
//			// Kreiraj novog gosta i dodeli mu narukvicu
//			GuestEntity newGuest = new GuestEntity();
//			newGuest.setName(guest.getName());
//			newGuest.setLastname(guest.getLastname());
//			newGuest.setIdDocument(guest.getIdDocument());
//			newGuest.setPhoneNumber(guest.getPhoneNumber());
//			newGuest.setBracelet(bracelet);
//
//			// Snimi novog gosta
//			guestRepository.save(newGuest);
//
//			// Ažuriraj narukvicu da pokazuje na novog gosta
//			bracelet.setGuest(newGuest);
//			braceletRepository.save(bracelet);
//
//			return new ResponseEntity<>(newGuest, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(2, "Exception occurred: " + e.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//	}
	
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

	        // Mapiraj DTO u entitet
	        GuestEntity newGuest = GuestMapper.toEntity(guestDTO);
	        newGuest.setBracelet(bracelet);

	        // Snimi novog gosta
	        guestRepository.save(newGuest);

	        // Ažuriraj narukvicu da pokazuje na novog gosta
	        bracelet.setGuest(newGuest);
	        braceletRepository.save(bracelet);

	        // Mapiraj entitet nazad u DTO za vraćanje kao odgovor
	        GuestDTO savedGuestDTO = GuestMapper.toDTO(newGuest);

	        return new ResponseEntity<>(savedGuestDTO, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(2, "Exception occurred: " + e.getMessage()),
	                HttpStatus.BAD_REQUEST);
	    }
	}


	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllGuests() {
		try {
			List<GuestEntity> guests = (List<GuestEntity>) guestRepository.findAll();
			List<GuestDTO> guestDTOs = guests.stream().map(GuestMapper::toDTO).collect(Collectors.toList());

			return new ResponseEntity<>(guestDTOs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occured " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{guestId}")
	public ResponseEntity<?> getGuestById(@PathVariable Integer guestId) {
	    Optional<GuestEntity> guestOpt = guestRepository.findById(guestId);
	    if (guestOpt.isPresent()) {
	        GuestDTO guestDTO = GuestMapper.toDTO(guestOpt.get());
	        return new ResponseEntity<>(guestDTO, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(new RESTError(1, "Guest not found"), HttpStatus.NOT_FOUND);
	    }
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/changeById/{guestId}")
	public ResponseEntity<?> changeGuest(@PathVariable Integer guestId, @RequestBody GuestDTO updatedGuest) {
	    try {
	        GuestEntity guest = guestRepository.findById(guestId).orElse(null);
	        if (guest == null) {
	            return new ResponseEntity<>("Guest not found", HttpStatus.NOT_FOUND);
	        }
	        guest.setName(updatedGuest.getName());
	        guest.setLastname(updatedGuest.getLastname());
	        guest.setIdDocument(updatedGuest.getIdDocument());
	        guest.setPhoneNumber(updatedGuest.getPhoneNumber());

	        // Očekuje se da `updatedGuest.getBracelet()` vrati objekat, a ne niz
	        if (updatedGuest.getBracelet() != null) {
	            BraceletEntity bracelet = braceletRepository.findById(updatedGuest.getBracelet().getId()).orElse(null);
	            if (bracelet != null) {
	                guest.setBracelet(bracelet);
	            }
	        }

	        guestRepository.save(guest);
	        return new ResponseEntity<>(guest, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(1, "Exception occurred " + e.getMessage()), HttpStatus.BAD_REQUEST);
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

	//metoda za brisanje stanja gestove narukvice
	@RequestMapping(method = RequestMethod.POST, path = "/clearGuest/{braceletId}")
	public ResponseEntity<?> clearGuest(@PathVariable Integer braceletId) {
		try {
			BraceletEntity bracelet = braceletRepository.findById(braceletId).orElse(null);
			if (bracelet == null) {
				return new ResponseEntity<>("Bracelet not found", HttpStatus.BAD_REQUEST);
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

//	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteGuest/{guestId}")
//	public ResponseEntity<?> deleteGuest(@PathVariable Integer guestId) {
//		try {
//			GuestEntity guest = guestRepository.findById(guestId).orElse(null);
//			if (guest == null) {
//				return new ResponseEntity<>("Guest not found", HttpStatus.BAD_REQUEST);
//			}
//			guestRepository.delete(guest);
//			return new ResponseEntity<>("Guest is deleted", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//	}
	@RequestMapping(method = RequestMethod.DELETE, path = "/{guestId}")
	public ResponseEntity<?> deleteGuest(@PathVariable Integer guestId) {
	    try {
	        // Pronađi gosta po ID-u
	        GuestEntity guest = guestRepository.findById(guestId).orElse(null);
	        if (guest == null) {
	            return new ResponseEntity<>("Guest not found", HttpStatus.BAD_REQUEST);
	        }

	        // Pronađi narukvicu koja je dodeljena ovom gostu (ako postoji)
	        BraceletEntity bracelet = guest.getBracelet();
	        if (bracelet != null) {
	            // Ukloni referencu na gosta iz narukvice
	            bracelet.setGuest(null);
	            // Sačuvaj ažuriranu narukvicu
	            braceletRepository.save(bracelet);
	        }

	        // Obriši gosta
	        guestRepository.delete(guest);

	        return new ResponseEntity<>(Collections.singletonMap("message","Guest is deleted"), HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new RESTError(1, "Exception occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}

	@RequestMapping(method = RequestMethod.POST, path = "/assignBracelet/{braceletId}")
	public ResponseEntity<?> assignBraceletToGuest(@Valid @RequestBody GuestEntity guest,
			@PathVariable Integer braceletId) {
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

			// Dodeli narukvicu gostu
			guest.setBracelet(bracelet);

			// Snimi novog gosta
			guestRepository.save(guest);

			// Ažuriraj narukvicu da pokazuje na novog gosta
			bracelet.setGuest(guest);
			braceletRepository.save(bracelet);

			return new ResponseEntity<>(guest, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
}
