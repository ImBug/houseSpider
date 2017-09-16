package water.house.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name ="deal")
public class DealInfo extends PropertyInfo{
	
	private long id;
	private String hourseId;
	private String price;
	private String tax;
	private String downPayment;
	private String ontime;
	private String mortgage;
	private String share;
	private String ownertype;
	private String descr;
	private String cause;
	private String avgPrice;
	private Date recordTime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHourseId() {
		return hourseId;
	}
	public void setHourseId(String hourseId) {
		this.hourseId = hourseId;
	}
	public String getAvgPrice() {
		return avgPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getDownPayment() {
		return downPayment;
	}
	public void setDownPayment(String downPayment) {
		this.downPayment = downPayment;
	}
	public String getOntime() {
		return ontime;
	}
	public void setOntime(String ontime) {
		this.ontime = ontime;
	}
	public String getMortgage() {
		return mortgage;
	}
	public void setMortgage(String mortgage) {
		this.mortgage = mortgage;
	}
	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
	}
	public String getOwnertype() {
		return ownertype;
	}
	public void setOwnertype(String ownertype) {
		this.ownertype = ownertype;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public void setAvgPrice(String textOfTag) {
		this.avgPrice =textOfTag;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	
}
