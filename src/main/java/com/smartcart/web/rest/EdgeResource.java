package com.smartcart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smartcart.domain.Edge;
import com.smartcart.repository.EdgeRepository;
import com.smartcart.repository.search.EdgeSearchRepository;
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
 * REST controller for managing Edge.
 */
@RestController
@RequestMapping("/api")
public class EdgeResource {

    private final Logger log = LoggerFactory.getLogger(EdgeResource.class);

    @Inject
    private EdgeRepository edgeRepository;

    @Inject
    private EdgeSearchRepository edgeSearchRepository;

    /**
     * POST  /edges -> Create a new edge.
     */
    @RequestMapping(value = "/edges",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Edge edge) throws URISyntaxException {
        log.debug("REST request to save Edge : {}", edge);
        if (edge.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new edge cannot already have an ID").build();
        }
        edgeRepository.save(edge);
        edgeSearchRepository.save(edge);
        return ResponseEntity.created(new URI("/api/edges/" + edge.getId())).build();
    }

    /**
     * PUT  /edges -> Updates an existing edge.
     */
    @RequestMapping(value = "/edges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Edge edge) throws URISyntaxException {
        log.debug("REST request to update Edge : {}", edge);
        if (edge.getId() == null) {
            return create(edge);
        }
        edgeRepository.save(edge);
        edgeSearchRepository.save(edge);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /edges -> get all the edges.
     */
    @RequestMapping(value = "/edges",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Edge>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Edge> page = edgeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/edges", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /edges/:id -> get the "id" edge.
     */
    @RequestMapping(value = "/edges/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Edge> get(@PathVariable Long id) {
        log.debug("REST request to get Edge : {}", id);
        return Optional.ofNullable(edgeRepository.findOne(id))
            .map(edge -> new ResponseEntity<>(
                edge,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /edges/:id -> delete the "id" edge.
     */
    @RequestMapping(value = "/edges/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Edge : {}", id);
        edgeRepository.delete(id);
        edgeSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/edges/:query -> search for the edge corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/edges/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Edge> search(@PathVariable String query) {
        return StreamSupport
            .stream(edgeSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
