package com.smartcart.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;


/**
 * A Edge.
 */
@Entity
@Table(name = "EDGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="edge")
public class Edge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "value", precision=10, scale=2, nullable = false)
    private BigDecimal value;

    @ManyToOne(optional=false)
    @JoinColumn(name="sourceproduct_id", referencedColumnName="id", insertable=true, updatable=false)
    private Product sourceProduct;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="targetproduct_id", referencedColumnName="id", insertable=true, updatable=false)
    private Product targetProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Product getSourceProduct() {
        return sourceProduct;
    }

    public void setSourceProduct(Product product) {
        this.sourceProduct = product;
    }

    public Product getTargetProduct() {
        return targetProduct;
    }

    public void setTargetProduct(Product product) {
        this.targetProduct = product;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Edge edge = (Edge) o;

        if ( ! Objects.equals(id, edge.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", value='" + value + "'" +
                ", sourceProduct='" + sourceProduct + "'" +
                ", targetProduct='" + targetProduct + "'" +
                '}';
    }
}
