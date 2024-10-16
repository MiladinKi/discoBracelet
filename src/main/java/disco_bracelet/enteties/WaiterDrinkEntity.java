package disco_bracelet.enteties;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WaiterDrinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Preporučeno za automatsko generisanje ID iz baze
    private Integer id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id")
    private WaiterEntity waiter;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    private DrinkEntity drink;

    private Integer quantity = 1;

    @CreationTimestamp // Automatski postavlja vreme kreiranja entiteta
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public WaiterDrinkEntity() {
        super();
    }

    public WaiterDrinkEntity(Integer id, WaiterEntity waiter, DrinkEntity drink, Integer quantity, LocalDateTime createdAt) {
        super();
        this.id = id;
        this.waiter = waiter;
        this.drink = drink;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
