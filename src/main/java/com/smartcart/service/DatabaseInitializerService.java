package com.smartcart.service;


import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smartcart.domain.Category;
import com.smartcart.domain.Edge;
import com.smartcart.domain.Price;
import com.smartcart.domain.Product;
import com.smartcart.domain.Shop;
import com.smartcart.repository.CategoryRepository;
import com.smartcart.repository.EdgeRepository;
import com.smartcart.repository.PriceRepository;
import com.smartcart.repository.ProductRepository;
import com.smartcart.repository.ShopRepository;
import com.smartcart.service.util.DatabaseInitializerHelper;


@Service
public class DatabaseInitializerService {

	private final Logger log = LoggerFactory.getLogger(DatabaseInitializerService.class);
	
	@Inject
	private CategoryRepository categoryRepository;
	
	@Inject
	private EdgeRepository edgeRepository;
	
	@Inject
	private ProductRepository productRepository;
	
	@Inject
	private PriceRepository priceRepository;
	
	@Inject
	private ShopRepository shopRepository;
	
    public boolean loadData() {
        log.debug("Database initialization started...");
        
        edgeRepository.deleteAll();
        log.debug("Database initialization - Edges deleted!");
        
        priceRepository.deleteAll();
        log.debug("Database initialization - Prices deleted!");
        
        shopRepository.deleteAll();
        log.debug("Database initialization - Shops deleted!");

        productRepository.deleteAll();
        log.debug("Database initialization - Products deleted!");
        
        categoryRepository.deleteAll();
        log.debug("Database initialization - Categories deleted!");
        
        List<Category> categories = DatabaseInitializerHelper.initCategories();
        
        List<Product> products = DatabaseInitializerHelper.initProducts(categories);

        List<Edge> edges = DatabaseInitializerHelper.initEdges(products);
        
        List<Shop> shops =  DatabaseInitializerHelper.initShops();

        List<Price> prices =  DatabaseInitializerHelper.initPrices(products, shops);

        categories.forEach(c -> callBfs(c));
        products.forEach(p -> productRepository.save(p));
        edges.forEach(e -> edgeRepository.save(e));
        shops.forEach(s -> shopRepository.save(s));
        prices.forEach(p -> priceRepository.save(p));
        
        log.debug("Database initialization finished!");
        return true;
    }
    
    /**
     * Recursive Breadth First to save 
     * @param category
     */
    private void callBfs(Category category) {
        categoryRepository.save(category);
        category.getChildren().forEach(c -> callBfs(c));
    }
}
