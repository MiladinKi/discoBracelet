package disco_bracelet.enteties;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class GuestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull(message = "Name must be provided!")
	@Size(min = 2, max = 20, message = "Name must be between {min} and {max} characters long!")
	private String name;
	@NotNull(message = "Lastname must be provided!")
	@Size(min = 2, max = 20, message = "Lastname must be between {min} and {max} characters long")
	private String lastname;
	@Column(unique = true)
	@NotNull(message = "Id document must be provided")
	@Digits(integer = 13, fraction = 0, message = "Personal ID must have exactly 13 digits")
	private Long idDocument;
	@Column(unique = true)
	private Long phoneNumber;

	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "bracelet_id")
	@JsonIgnore
	private BraceletEntity bracelet;

	public GuestEntity() {
		super();
	}

	public GuestEntity(Integer id,
			@NotNull(message = "Name must be provided!") @Size(min = 2, max = 20, message = "Name must be between {min} and {max} characters long!") String name,
			@NotNull(message = "Lastname must be provided!") @Size(min = 2, max = 20, message = "Lastname must be between {min} and {max} characters long") String lastname,
			@NotNull(message = "Id document must be provided") @Digits(integer = 13, fraction = 0, message = "Personal ID must have exactly 13 digits") Long idDocument,
			Long phoneNumber, BraceletEntity bracelet) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.idDocument = idDocument;
		this.phoneNumber = phoneNumber;
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Long getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(Long idDocument) {
		this.idDocument = idDocument;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public BraceletEntity getBracelet() {
		return bracelet;
	}

	public void setBracelet(BraceletEntity bracelet) {
		this.bracelet = bracelet;
	}

}
