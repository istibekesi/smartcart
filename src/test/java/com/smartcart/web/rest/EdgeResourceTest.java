package com.smartcart.web.rest;

import com.smartcart.Application;
import com.smartcart.domain.Edge;
import com.smartcart.repository.EdgeRepository;
import com.smartcart.repository.search.EdgeSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EdgeResource REST controller.
 *
 * @see EdgeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EdgeResourceTest {


    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(1);

    @Inject
    private EdgeRepository edgeRepository;

    @Inject
    private EdgeSearchRepository edgeSearchRepository;

    private MockMvc restEdgeMockMvc;

    private Edge edge;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EdgeResource edgeResource = new EdgeResource();
        ReflectionTestUtils.setField(edgeResource, "edgeRepository", edgeRepository);
        ReflectionTestUtils.setField(edgeResource, "edgeSearchRepository", edgeSearchRepository);
        this.restEdgeMockMvc = MockMvcBuilders.standaloneSetup(edgeResource).build();
    }

    @Before
    public void initTest() {
        edge = new Edge();
        edge.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createEdge() throws Exception {
        int databaseSizeBeforeCreate = edgeRepository.findAll().size();

        // Create the Edge
        restEdgeMockMvc.perform(post("/api/edges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(edge)))
                .andExpect(status().isCreated());

        // Validate the Edge in the database
        List<Edge> edges = edgeRepository.findAll();
        assertThat(edges).hasSize(databaseSizeBeforeCreate + 1);
        Edge testEdge = edges.get(edges.size() - 1);
        assertThat(testEdge.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(edgeRepository.findAll()).hasSize(0);
        // set the field null
        edge.setValue(null);

        // Create the Edge, which fails.
        restEdgeMockMvc.perform(post("/api/edges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(edge)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Edge> edges = edgeRepository.findAll();
        assertThat(edges).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllEdges() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);

        // Get all the edges
        restEdgeMockMvc.perform(get("/api/edges"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(edge.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getEdge() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);

        // Get the edge
        restEdgeMockMvc.perform(get("/api/edges/{id}", edge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(edge.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEdge() throws Exception {
        // Get the edge
        restEdgeMockMvc.perform(get("/api/edges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEdge() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);

		int databaseSizeBeforeUpdate = edgeRepository.findAll().size();

        // Update the edge
        edge.setValue(UPDATED_VALUE);
        restEdgeMockMvc.perform(put("/api/edges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(edge)))
                .andExpect(status().isOk());

        // Validate the Edge in the database
        List<Edge> edges = edgeRepository.findAll();
        assertThat(edges).hasSize(databaseSizeBeforeUpdate);
        Edge testEdge = edges.get(edges.size() - 1);
        assertThat(testEdge.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteEdge() throws Exception {
        // Initialize the database
        edgeRepository.saveAndFlush(edge);

		int databaseSizeBeforeDelete = edgeRepository.findAll().size();

        // Get the edge
        restEdgeMockMvc.perform(delete("/api/edges/{id}", edge.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Edge> edges = edgeRepository.findAll();
        assertThat(edges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
