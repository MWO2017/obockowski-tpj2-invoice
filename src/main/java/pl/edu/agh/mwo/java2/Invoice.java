package pl.edu.agh.mwo.java2;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class Invoice {

	HashMap<Product, Integer> products;
	BigDecimal tax;
	String number;

	private Invoice() {
		this.products = new HashMap<Product, Integer>();
		this.tax=BigDecimal.ZERO;
	}

	public void addProduct(Product product) {
		this.tax=product.getTaxPercent();
		int counts = products.containsKey(product) ? products.get(product) + 1 : 1;
		products.put(product, counts);
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException();
		}
		this.tax=product.getTaxPercent();
		int counts = products.containsKey(product) ? products.get(product) + quantity : quantity;
		products.put(product, counts);

	}

	public String getNumber() {
		return this.number;
	}

	public BigDecimal getSubtotal() {
		BigDecimal price = new BigDecimal("0.00");
		Set<Product> s = products.keySet();
		for (Product p : s) {
			//System.out.println("cena: " + p.getPrice());
			price = price.add(p.getPrice().multiply((new BigDecimal(products.get(p)).setScale(2))));
		}
		//System.out.println("SUMA: " + price);
		return price.setScale(2);
	}

	public BigDecimal getTax() {
		return this.tax;
	}

	public BigDecimal getTotal() {
		BigDecimal price = new BigDecimal("0.00");
		Set<Product> s = products.keySet();
		for (Product p : s) {
			//cena * podatek
/*			BigDecimal temp=p.getPriceWithTax();
			temp.setScale(2, BigDecimal.ROUND_FLOOR);
			temp=temp.multiply((new BigDecimal(products.get(p)).setScale(2,BigDecimal.ROUND_FLOOR)));
			System.out.println("cena: " + temp);
			price = price.add(temp);
*/		
			//System.out.println("produkt: "+p.getPriceWithTax());
			BigDecimal temp=p.getPriceWithTax().multiply((new BigDecimal(products.get(p))));
			price = price.add(temp).setScale(2,BigDecimal.ROUND_CEILING);
		}
		System.out.println("cena: " + price);
		return price;
	}

	public static Invoice create() {
		return new Invoice();
	}

}
