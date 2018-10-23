package cart.model;

public class cartBean {
	private int cartnum;
	private String pnum;
	private String pname;
	private int qty;
	private int price;
	private String userid;
	private int multipleprice;
	
	
	public int getMultipleprice() {
		return multipleprice;
	}
	public void setMultipleprice(int multipleprice) {
		this.multipleprice = multipleprice;
	}
	public int getCartnum() {
		return cartnum;
	}
	public void setCartnum(int cartnum) {
		this.cartnum = cartnum;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	
}
