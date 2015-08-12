package com.smartcart.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.annotations.Document;


/**
 * A Price.
 */
@Entity
@Table(name = "PRICE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="price")
public class Price implements Serializable {
	
	private final static Logger log = LoggerFactory.getLogger(Price.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Price() {
    	// default
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Price price = (Price) o;

        if ( ! Objects.equals(id, price.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price='" + price + "'" +
                '}';
    }

	public static Price priceBuilder(List<Product> products, List<Shop> shops, String productBar, String brandName, BigDecimal bigDecimal) {
		Product p1 = products.stream()
				.filter(p -> p.getBarcode().equals(productBar))
				.findFirst()
				.get();

		Shop s1 = null;
		try {
		s1 = shops.stream()
				.filter(s -> s.getBrand().getBrand().name().equalsIgnoreCase(brandName))
				.findFirst()
				.get();
		} catch (Exception e) {
			log.error("Not found brandname? " + brandName, e);
		}

		return priceBuilder(p1, s1, bigDecimal);
		
	}

	public static Price priceBuilder(Product p1, Shop s1, BigDecimal value) {
		Price price = new Price();
		price.setPrice(value);
		
		price.setProduct(p1);
		price.setShop(s1);
		
		p1.getProductPrices().add(price);
		s1.getPrices().add(price);
		
		return price;
	}
}
