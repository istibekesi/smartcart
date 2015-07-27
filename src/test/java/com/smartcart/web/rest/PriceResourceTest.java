package com.smartcart.web.rest;

import com.smartcart.Application;
import com.smartcart.domain.Price;
import com.smartcart.repository.PriceRepository;
import com.smartcart.repository.search.PriceSearchRepository;

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
 * Test class for the PriceResource REST controller.
 *
 * @see PriceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PriceResourceTest {


    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    @Inject
    private PriceRepository priceRepository;

    @Inject
    private PriceSearchRepository priceSearchRepository;

    private MockMvc restPriceMockMvc;

    private Price price;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PriceResource priceResource = new PriceResource();
        ReflectionTestUtils.setField(priceResource, "priceRepository", priceRepository);
        ReflectionTestUtils.setField(priceResource, "priceSearchRepository", priceSearchRepository);
        this.restPriceMockMvc = MockMvcBuilders.standaloneSetup(priceResource).build();
    }

    @Before
    public void initTest() {
        price = new Price();
        price.setPrice(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createPrice() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // Create the Price
        restPriceMockMvc.perform(post("/api/prices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(price)))
                .andExpect(status().isCreated());

        // Validate the Price in the database
        List<Price> prices = priceRepository.findAll();
        assertThat(prices).hasSize(databaseSizeBeforeCreate + 1);
        Price testPrice = prices.get(prices.size() - 1);
        assertThat(testPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(priceRepository.findAll()).hasSize(0);
        // set the field null
        price.setPrice(null);

        // Create the Price, which fails.
        restPriceMockMvc.perform(post("/api/prices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(price)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Price> prices = priceRepository.findAll();
        assertThat(prices).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPrices() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the prices
        restPriceMockMvc.perform(get("/api/prices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(price.getId().intValue())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getPrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", price.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(price.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrice() throws Exception {
        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

		int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Update the price
        price.setPrice(UPDATED_PRICE);
        restPriceMockMvc.perform(put("/api/prices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(price)))
                .andExpect(status().isOk());

        // Validate the Price in the database
        List<Price> prices = priceRepository.findAll();
        assertThat(prices).hasSize(databaseSizeBeforeUpdate);
        Price testPrice = prices.get(prices.size() - 1);
        assertThat(testPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void deletePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

		int databaseSizeBeforeDelete = priceRepository.findAll().size();

        // Get the price
        restPriceMockMvc.perform(delete("/api/prices/{id}", price.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Price> prices = priceRepository.findAll();
        assertThat(prices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
