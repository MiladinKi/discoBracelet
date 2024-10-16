package disco_bracelet.enteties.dtoes;

public class GuestDTO {
	private Integer id;
	private String name;
	private String lastname;
	private Long IdDocument;
	private Long phoneNumber;
	private BraceletDTO bracelet;

	public GuestDTO() {

	}

	public GuestDTO(Integer id, String name, String lastname, Long idDocument, Long phoneNumber, BraceletDTO bracelet) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		IdDocument = idDocument;
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
		return IdDocument;
	}

	public void setIdDocument(Long idDocument) {
		IdDocument = idDocument;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public BraceletDTO getBracelet() {
		return bracelet;
	}

	public void setBracelet(BraceletDTO bracelet) {
		this.bracelet = bracelet;
	}

}
