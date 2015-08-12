package com.smartcart.repository.search;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.smartcart.Application;
import com.smartcart.domain.Product;
import com.smartcart.repository.ProductRepository;
import com.smartcart.service.DatabaseInitializerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductSearchRepositoryTest {
	
	private final Logger log = LoggerFactory.getLogger(ProductSearchRepositoryTest.class);

    @Inject
    private ProductSearchRepository productSearchRepository;
    
    @Inject
    private ProductRepository productRepository;
    
    @Inject
    DatabaseInitializerService databaseInitializerService;
    
    @Before
    public void initRepo(){
    	//databaseInitializerService.loadData();
    }
    
    @Test
    public void shouldReturnListOfProductsByName() {
        //given
    	productSearchRepository.index(new Product(new Long(1), "First product test"));
    	productSearchRepository.index(new Product(new Long(2), "Second product"));
        //when
        List<Product> products = productSearchRepository.findByName("product");
        //then
        assertEquals(products.size(), 2);
    }
    
    @Test
    public void testExistingProducts() {

    	//List<Product> myProducts = productRepository.findAll();
    	//log.debug("myProducts:" + myProducts.size());
    	
    	//List<Product> mySearchProducts = productSearchRepository.findAll();
    	//log.debug("mySearchProducts:" + mySearchProducts.size());
    	
        //List<Product> elasticProducts = productSearchRepository.findByName("sör");

        //assertTrue(elasticProducts.size() == 0);
    }
    
}
