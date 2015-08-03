package com.smartcart.domain.helper;

import com.smartcart.domain.Product;

public class ProductPair {

	private Product product1;
	private Product product2;
	
	public ProductPair(Product product1, Product product2) {
		this.product1 = product1;
		this.product2 = product2;
	}
	
	public ProductPair(Object product1, Object product2) {
		this.product1 = (Product) product1;
		this.product2 = (Product) product2;
	}
	
	public Product getProduct1() {
		return product1;
	}
	public void setProduct1(Product product1) {
		this.product1 = product1;
	}
	public Product getProduct2() {
		return product2;
	}
	public void setProduct2(Product product2) {
		this.product2 = product2;
	}
	
	
}
