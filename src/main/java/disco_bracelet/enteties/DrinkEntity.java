package disco_bracelet.enteties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "bracelet")
	private BraceletEntity bracelet;

	public DrinkEntity() {
		super();
	}

	public DrinkEntity(Integer id, @NotNull(message = "Name of drink must be provided!") String name,
			@NotNull(message = "Price of drink must be provided!") Double price, BraceletEntity bracelet) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.bracelet = bracelet;
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

	public BraceletEntity getBracelet() {
		return bracelet;
	}

	public void setBracelet(BraceletEntity bracelet) {
		this.bracelet = bracelet;
	}

}
