package all_DTO;
public class DailySettlementDTO {

	private String date;
	private int hours;
	private int hours_income;
	private int product_income;
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getHours() {
		return hours;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public int getHours_income() {
		return hours_income;
	}
	
	public void setHours_income(int hours_income) {
		this.hours_income = hours_income;
	}
	
	public int getProduct_income() {
		return product_income;
	}
	
	public void setProduct_income(int product_income) {
		this.product_income = product_income;
	}	
	
}
