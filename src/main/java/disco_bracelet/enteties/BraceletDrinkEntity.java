package disco_bracelet.enteties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BraceletDrinkEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "bracelet_id")
	private BraceletEntity bracelet;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "drink_id")
	private DrinkEntity drink;

	private Integer quantity;

	public BraceletDrinkEntity() {
		super();
	}

	public BraceletDrinkEntity(Integer id, BraceletEntity bracelet, DrinkEntity drink, Integer quantity) {
		super();
		this.id = id;
		this.bracelet = bracelet;
		this.drink = drink;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BraceletEntity getBracelet() {
		return bracelet;
	}

	public void setBracelet(BraceletEntity bracelet) {
		this.bracelet = bracelet;
	}

	public DrinkEntity getDrink() {
		return drink;
	}

	public void setDrink(DrinkEntity drink) {
		this.drink = drink;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
