package com.smartcart.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartcart.domain.enumeration.UnitEnum;

/**
 * A Product.
 */
@Entity
@Table(name = "PRODUCT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="product")
public class Product implements Serializable {
	
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "barcode", unique = true)
    private String barcode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private UnitEnum unit;
    
    @Column(name = "quantity")
    private long quantity;

    @OneToMany(mappedBy = "sourceProduct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Edge> sourceEdgess = new ArrayList<>();
    
    @ManyToOne(optional=true)
    @JoinColumn(name="category", referencedColumnName="id", insertable=true, updatable=true)
    private Category category;
    
    @OneToMany(mappedBy = "product")
    private Set<Price> productPrices = new HashSet<>();
    

    public Product() {};
    
    public Product (String barcode, String name, String description, UnitEnum unit, long quantity) {
    	this.barcode = barcode;
    	this.name = name;
    	this.description = description;
    	this.unit = unit;
    	this.quantity = quantity;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UnitEnum getUnit() {
        return unit;
    }

    public void setUnit(UnitEnum unit) {
        this.unit = unit;
    }

    public List<Edge> getSourceEdgess() {
        return sourceEdgess;
    }

    public void setSourceEdgess(List<Edge> edges) {
        this.sourceEdgess = edges;
    }

    public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Price> getProductPrices() {
		return productPrices;
	}

	public void setProductPrices(Set<Price> productPrices) {
		this.productPrices = productPrices;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if ( ! Objects.equals(id, product.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", barcode='" + barcode + "'" +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", unit='" + unit + "'" +
                '}';
    }
}
