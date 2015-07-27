package com.smartcart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smartcart.domain.Shop;
import com.smartcart.repository.ShopRepository;
import com.smartcart.repository.search.ShopSearchRepository;
import com.smartcart.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Shop.
 */
@RestController
@RequestMapping("/api")
public class ShopResource {

    private final Logger log = LoggerFactory.getLogger(ShopResource.class);

    @Inject
    private ShopRepository shopRepository;

    @Inject
    private ShopSearchRepository shopSearchRepository;

    /**
     * POST  /shops -> Create a new shop.
     */
    @RequestMapping(value = "/shops",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Shop shop) throws URISyntaxException {
        log.debug("REST request to save Shop : {}", shop);
        if (shop.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shop cannot already have an ID").build();
        }
        shopRepository.save(shop);
        shopSearchRepository.save(shop);
        return ResponseEntity.created(new URI("/api/shops/" + shop.getId())).build();
    }

    /**
     * PUT  /shops -> Updates an existing shop.
     */
    @RequestMapping(value = "/shops",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Shop shop) throws URISyntaxException {
        log.debug("REST request to update Shop : {}", shop);
        if (shop.getId() == null) {
            return create(shop);
        }
        shopRepository.save(shop);
        shopSearchRepository.save(shop);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /shops -> get all the shops.
     */
    @RequestMapping(value = "/shops",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Shop>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Shop> page = shopRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shops", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shops/:id -> get the "id" shop.
     */
    @RequestMapping(value = "/shops/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shop> get(@PathVariable Long id) {
        log.debug("REST request to get Shop : {}", id);
        return Optional.ofNullable(shopRepository.findOne(id))
            .map(shop -> new ResponseEntity<>(
                shop,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shops/:id -> delete the "id" shop.
     */
    @RequestMapping(value = "/shops/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Shop : {}", id);
        shopRepository.delete(id);
        shopSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/shops/:query -> search for the shop corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/shops/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Shop> search(@PathVariable String query) {
        return StreamSupport
            .stream(shopSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
