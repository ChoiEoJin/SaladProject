package pay.model;

import java.sql.Timestamp;

public class PayBean {
	
	private String paynum;
	private String paypname;
	private int payprice;
	private Timestamp paydate;
	private String paywho;
	
	
	public String getPaynum() {
		return paynum;
	}
	public void setPaynum(String paynum) {
		this.paynum = paynum;
	}
	public String getPaypname() {
		return paypname;
	}
	public void setPaypname(String paypname) {
		this.paypname = paypname;
	}
	public int getPayprice() {
		return payprice;
	}
	public void setPayprice(int payprice) {
		this.payprice = payprice;
	}
	public Timestamp getPaydate() {
		return paydate;
	}
	public void setPaydate(Timestamp paydate) {
		this.paydate = paydate;
	}
	public String getPaywho() {
		return paywho;
	}
	public void setPaywho(String paywho) {
		this.paywho = paywho;
	}
	
	
	
}
