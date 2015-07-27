package com.smartcart.repository.search;

import com.smartcart.domain.Shop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Shop entity.
 */
public interface ShopSearchRepository extends ElasticsearchRepository<Shop, Long> {
}
