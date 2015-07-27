package com.smartcart.web.rest;

import com.smartcart.Application;
import com.smartcart.domain.Shop;
import com.smartcart.repository.ShopRepository;
import com.smartcart.repository.search.ShopSearchRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ShopResource REST controller.
 *
 * @see ShopResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShopResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";

    @Inject
    private ShopRepository shopRepository;

    @Inject
    private ShopSearchRepository shopSearchRepository;

    private MockMvc restShopMockMvc;

    private Shop shop;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShopResource shopResource = new ShopResource();
        ReflectionTestUtils.setField(shopResource, "shopRepository", shopRepository);
        ReflectionTestUtils.setField(shopResource, "shopSearchRepository", shopSearchRepository);
        this.restShopMockMvc = MockMvcBuilders.standaloneSetup(shopResource).build();
    }

    @Before
    public void initTest() {
        shop = new Shop();
        shop.setName(DEFAULT_NAME);
        shop.setAddress(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createShop() throws Exception {
        int databaseSizeBeforeCreate = shopRepository.findAll().size();

        // Create the Shop
        restShopMockMvc.perform(post("/api/shops")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shop)))
                .andExpect(status().isCreated());

        // Validate the Shop in the database
        List<Shop> shops = shopRepository.findAll();
        assertThat(shops).hasSize(databaseSizeBeforeCreate + 1);
        Shop testShop = shops.get(shops.size() - 1);
        assertThat(testShop.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShop.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(shopRepository.findAll()).hasSize(0);
        // set the field null
        shop.setName(null);

        // Create the Shop, which fails.
        restShopMockMvc.perform(post("/api/shops")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shop)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Shop> shops = shopRepository.findAll();
        assertThat(shops).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllShops() throws Exception {
        // Initialize the database
        shopRepository.saveAndFlush(shop);

        // Get all the shops
        restShopMockMvc.perform(get("/api/shops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(shop.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getShop() throws Exception {
        // Initialize the database
        shopRepository.saveAndFlush(shop);

        // Get the shop
        restShopMockMvc.perform(get("/api/shops/{id}", shop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shop.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShop() throws Exception {
        // Get the shop
        restShopMockMvc.perform(get("/api/shops/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShop() throws Exception {
        // Initialize the database
        shopRepository.saveAndFlush(shop);

		int databaseSizeBeforeUpdate = shopRepository.findAll().size();

        // Update the shop
        shop.setName(UPDATED_NAME);
        shop.setAddress(UPDATED_ADDRESS);
        restShopMockMvc.perform(put("/api/shops")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shop)))
                .andExpect(status().isOk());

        // Validate the Shop in the database
        List<Shop> shops = shopRepository.findAll();
        assertThat(shops).hasSize(databaseSizeBeforeUpdate);
        Shop testShop = shops.get(shops.size() - 1);
        assertThat(testShop.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShop.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteShop() throws Exception {
        // Initialize the database
        shopRepository.saveAndFlush(shop);

		int databaseSizeBeforeDelete = shopRepository.findAll().size();

        // Get the shop
        restShopMockMvc.perform(delete("/api/shops/{id}", shop.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Shop> shops = shopRepository.findAll();
        assertThat(shops).hasSize(databaseSizeBeforeDelete - 1);
    }
}
