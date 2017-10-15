package water.house.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import water.tool.util.number.NumberUtil;

@Entity(name ="deal")
public class DealInfo extends PropertyInfo implements Comparable<DealInfo>{
	
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
		this.downPayment = NumberUtil.fetchNumberFrom(downPayment);
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
		this.avgPrice = NumberUtil.fetchNumberFrom(textOfTag);
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avgPrice == null) ? 0 : avgPrice.hashCode());
		result = prime * result + ((cause == null) ? 0 : cause.hashCode());
		result = prime * result + ((descr == null) ? 0 : descr.hashCode());
		result = prime * result + ((downPayment == null) ? 0 : downPayment.hashCode());
		result = prime * result + ((hourseId == null) ? 0 : hourseId.hashCode());
		result = prime * result + ((mortgage == null) ? 0 : mortgage.hashCode());
		result = prime * result + ((ontime == null) ? 0 : ontime.hashCode());
		result = prime * result + ((ownertype == null) ? 0 : ownertype.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((tax == null) ? 0 : tax.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DealInfo other = (DealInfo) obj;
		if (hourseId == null) {
			if (other.hourseId != null)
				return false;
		} else if (!hourseId.equals(other.hourseId))
			return false;
		if (avgPrice == null) {
			if (other.avgPrice != null)
				return false;
		} else if (!avgPrice.equals(other.avgPrice))
			return false;
		if (cause == null) {
			if (other.cause != null)
				return false;
		} else if (!cause.equals(other.cause))
			return false;
		if (descr == null) {
			if (other.descr != null)
				return false;
		} else if (!descr.equals(other.descr))
			return false;
		if (downPayment == null) {
			if (other.downPayment != null)
				return false;
		} else if (!downPayment.equals(other.downPayment))
			return false;
		if (mortgage == null) {
			if (other.mortgage != null)
				return false;
		} else if (!mortgage.equals(other.mortgage))
			return false;
		if (ontime == null) {
			if (other.ontime != null)
				return false;
		} else if (!ontime.equals(other.ontime))
			return false;
		if (ownertype == null) {
			if (other.ownertype != null)
				return false;
		} else if (!ownertype.equals(other.ownertype))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (tax == null) {
			if (other.tax != null)
				return false;
		} else if (!tax.equals(other.tax))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(DealInfo o) {
		return price.compareTo(o.price);
	}
	
	
}
