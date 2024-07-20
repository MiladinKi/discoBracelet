package disco_bracelet.enteties;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class DrinkEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull(message = "Name of drink must be provided!")
	private String name;
	@NotNull(message = "Price of drink must be provided!")
	private Double price;

	@NotNull(message = "Manufacturer of drink must be provided!")
	private String manufacturer;
	@NotNull(message = "Volume of drink must be provided!")
	private Double volume;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "bracelet_id")
	@JsonIgnoreProperties("drinks")
	private BraceletEntity bracelet;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "waiter_id")
	@JsonIgnore
	private WaiterEntity waiter;

	@OneToMany(mappedBy = "drink", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<WaiterDrinkEntity> waiterDrinks = new ArrayList<>();

	public DrinkEntity() {
		super();
	}

	public DrinkEntity(Integer id, @NotNull(message = "Name of drink must be provided!") String name,
			@NotNull(message = "Price of drink must be provided!") Double price,
			@NotNull(message = "Manufacturer of drink must be provided!") String manufacturer,
			@NotNull(message = "Volume of drink must be provided!") Double volume, BraceletEntity bracelet,
			WaiterEntity waiter, List<WaiterDrinkEntity> waiterDrinks) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.manufacturer = manufacturer;
		this.volume = volume;
		this.bracelet = bracelet;
		this.waiter = waiter;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public BraceletEntity getBracelet() {
		return bracelet;
	}

	public void setBracelet(BraceletEntity bracelet) {
		this.bracelet = bracelet;
	}

	public WaiterEntity getWaiter() {
		return waiter;
	}

	public void setWaiter(WaiterEntity waiter) {
		this.waiter = waiter;
	}

	public List<WaiterDrinkEntity> getWaiterDrinks() {
		return waiterDrinks;
	}

	public void setWaiterDrinks(List<WaiterDrinkEntity> waiterDrinks) {
		this.waiterDrinks = waiterDrinks;
	}

}
