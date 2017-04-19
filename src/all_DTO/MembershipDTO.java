package all_DTO;
public class MembershipDTO {

	public MembershipDTO(String member_id, String password, String name, int age, String phone_number) {
		super();
		this.member_id = member_id;
		this.password = password;
		this.name = name;
		this.age = age;
		this.phone_number = phone_number;
	}

	private int member_number;
	private String member_id;
	private String password;
	private String name;
	private int age;
	private String phone_number;
	private int auth;
	private int seat;
	private int minutes;
	
	public MembershipDTO() {
		
	}

	

	public int getMember_number() {
		return member_number;
	}

	public void setMember_number(int member_number) {
		this.member_number = member_number;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
}
