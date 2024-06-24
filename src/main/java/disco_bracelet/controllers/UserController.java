package disco_bracelet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import disco_bracelet.controllers.utils.RESTError;
import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.UserEntity;
import disco_bracelet.repositories.BraceletRepository;
import disco_bracelet.repositories.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/discoBracelet")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BraceletRepository braceletRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/newUser/braceletId/{braceletId}")
	public ResponseEntity<?> newUser(@Valid @RequestBody UserEntity user, 
			@PathVariable Integer braceletId){
		try {
			UserEntity newUser = new UserEntity();
			BraceletEntity bracelet = braceletRepository.findById(braceletId).get();
			if (bracelet == null) {
				return new ResponseEntity<>(new RESTError(1, "Bracelet not found"), HttpStatus.NOT_FOUND);

			}
			newUser.setName(user.getName());
			newUser.setLastname(user.getLastname());
			newUser.setIdDocument(user.getIdDocument());
			newUser.setPhoneNumber(user.getPhoneNumber());
			newUser.setBracelet(bracelet);
			userRepository.save(newUser);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(2, "Exception occured" + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
