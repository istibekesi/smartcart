package com.smartcart.repository.search;

import com.smartcart.domain.Edge;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Edge entity.
 */
public interface EdgeSearchRepository extends ElasticsearchRepository<Edge, Long> {
}
