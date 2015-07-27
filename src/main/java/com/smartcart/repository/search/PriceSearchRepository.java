package com.smartcart.repository.search;

import com.smartcart.domain.Price;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Price entity.
 */
public interface PriceSearchRepository extends ElasticsearchRepository<Price, Long> {
}
