package com.smartcart.repository.search;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartcart.domain.Product;

/**
 * Spring Data ElasticSearch repository for the Product entity.
 */
public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
	
    List<Product> findByName(String name);
    List<Product> findByName(String name, Pageable pageable);
    List<Product> findByNameAndId(String name, String id);
    
    List<Product> findAll();
	
}
