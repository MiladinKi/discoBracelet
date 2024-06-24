package disco_bracelet.enteties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class BraceletEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String manufacturer;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private LocalDate yearOfProduction;

	@OneToOne(mappedBy = "bracelet", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private UserEntity user;

	@OneToMany(mappedBy = "bracelet", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DrinkEntity> drinks = new ArrayList<>();

	public BraceletEntity() {
		super();
	}

	public BraceletEntity(Integer id, String manufacturer, LocalDate yearOfProduction, UserEntity user,
			List<DrinkEntity> drinks) {
		super();
		this.id = id;
		this.manufacturer = manufacturer;
		this.yearOfProduction = yearOfProduction;
		this.user = user;
		this.drinks = drinks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public LocalDate getYearOfProduction() {
		return yearOfProduction;
	}

	public void setYearOfProduction(LocalDate yearOfProduction) {
		this.yearOfProduction = yearOfProduction;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<DrinkEntity> getDrinks() {
		return drinks;
	}

	public void setDrinks(List<DrinkEntity> drinks) {
		this.drinks = drinks;
	}

}
