package disco_bracelet.enteties;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SalesHistoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private WaiterEntity waiter;

	@ManyToOne
	private DrinkEntity drink;

	private int quantity;

	private LocalDateTime soldAt;

	public SalesHistoryEntity() {
		super();
	}

	public SalesHistoryEntity(Long id, WaiterEntity waiter, DrinkEntity drink, int quantity, LocalDateTime soldAt) {
		super();
		this.id = id;
		this.waiter = waiter;
		this.drink = drink;
		this.quantity = quantity;
		this.soldAt = soldAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WaiterEntity getWaiter() {
		return waiter;
	}

	public void setWaiter(WaiterEntity waiter) {
		this.waiter = waiter;
	}

	public DrinkEntity getDrink() {
		return drink;
	}

	public void setDrink(DrinkEntity drink) {
		this.drink = drink;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getSoldAt() {
		return soldAt;
	}

	public void setSoldAt(LocalDateTime soldAt) {
		this.soldAt = soldAt;
	}

}
