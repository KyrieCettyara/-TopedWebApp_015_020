package del.ac.id.demo.jpa;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document("items")
public class Item {
	@Id
	String id;
	private String item_name,color,accept_installment_payment;
	private double stock,price,discount,rating;
	private int sold,seen,length;
	
	@Field("item_detail")
	private ItemDetail itemDetail;
	
	public Item() {}
	public Item(
			final String item_name,
			final String color,
			final double stock,
			final double price,
			final double discount,
			final double rating,
			final int sold,
			final int seen,
			final int length,
			final ItemDetail itemDetail){
		this.item_name = item_name;
		this.color = color;
		this.length = length;
		this.stock = stock;
		this.price = price;
		this.discount = discount;
		this.rating = rating;
		this.sold = sold;
		this.seen = seen;
		this.length = length;
		this.itemDetail = itemDetail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public void setItem_name(String item_name) {this.item_name = item_name;}
	public void setColor(String color) {this.color = color;}
	public void setAccept_intallment_payment(String accept_intallment_payment) {
		this.accept_installment_payment = accept_intallment_payment;
	}
	public void setStock(double stock){this.stock = stock;}
	public void setPrice(double price) {this.price = price;}
	public void setDiscount(double discount) {this.discount = discount;}
	public void setRating(double rating) {this.rating = rating;}
	public void setSold(int sold) {this.sold = sold;}
	public void setSeen(int seen) {this.seen = seen;}
	public void setLength(int length) {this.length = length;}
	public void setItemDetail(ItemDetail itemDetail) {this.itemDetail = itemDetail;}
	
	public String getItem_name() {return this.item_name;}
	public String getColor() {return this.color;}
	public String getAccept_install_payment() {return this.accept_installment_payment;}
	public double getStock() {return this.stock;}
	public double getPrice() {return this.price;}
	public double getDiscount() {return this.discount;}
	public double getRating() {return this.rating;}
	public int getSold() {return this.sold;}
	public int getSeen() {return this.seen;}
	public int getLength() {return this.length;}
	public ItemDetail getItemDetail() {return this.itemDetail;}
}
