package disco_bracelet.enteties;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class WaiterEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull(message = "Name must be provided!")
	@Size(min = 2, max = 20, message = "Name must be between {min} and {max} characters long!")
	private String name;
	@NotNull(message = "Lastname must be provided!")
	@Size(min = 2, max = 20, message = "Lastname must be between {min} and {max} characters long")
	private String lastname;
	@NotNull(message = "Username must be provided!")
	@Size(min = 2, max = 10, message = "Username must be between {min} and {max} characters long")
	private String username;
	@NotNull(message = "Password must be provided!")
	@Size(min = 2, max = 20, message = "Password must be between {min} and {max} characters long")
	private String password;
	private Double amount;

	@OneToMany(mappedBy = "waiter", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("waiter")
	private List<DrinkEntity> drinks = new ArrayList<>();

	@OneToMany(mappedBy = "waiter", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<WaiterDrinkEntity> waiterDrinks = new ArrayList<>();

	public WaiterEntity() {
		super();
	}

	public WaiterEntity(Integer id,
			@NotNull(message = "Name must be provided!") @Size(min = 2, max = 20, message = "Name must be between {min} and {max} characters long!") String name,
			@NotNull(message = "Lastname must be provided!") @Size(min = 2, max = 20, message = "Lastname must be between {min} and {max} characters long") String lastname,
			@NotNull(message = "Username must be provided!") @Size(min = 2, max = 10, message = "Username must be between {min} and {max} characters long") String username,
			@NotNull(message = "Password must be provided!") @Size(min = 2, max = 20, message = "Password must be between {min} and {max} characters long") String password,
			Double amount, List<DrinkEntity> drinks, List<WaiterDrinkEntity> waiterDrinks) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.amount = amount;
		this.drinks = drinks;
		this.waiterDrinks = waiterDrinks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public List<DrinkEntity> getDrinks() {
		return drinks;
	}

	public void setDrinks(List<DrinkEntity> drinks) {
		this.drinks = drinks;
	}

	public List<WaiterDrinkEntity> getWaiterDrinks() {
		return waiterDrinks;
	}

	public void setWaiterDrinks(List<WaiterDrinkEntity> waiterDrinks) {
		this.waiterDrinks = waiterDrinks;
	}

}
