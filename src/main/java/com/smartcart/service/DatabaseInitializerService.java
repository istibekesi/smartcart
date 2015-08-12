package com.smartcart.service;


import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcart.domain.Brand;
import com.smartcart.domain.Category;
import com.smartcart.domain.Edge;
import com.smartcart.domain.Price;
import com.smartcart.domain.Product;
import com.smartcart.domain.Shop;
import com.smartcart.repository.BrandRepository;
import com.smartcart.repository.CategoryRepository;
import com.smartcart.repository.EdgeRepository;
import com.smartcart.repository.PriceRepository;
import com.smartcart.repository.ProductRepository;
import com.smartcart.repository.ShopRepository;
import com.smartcart.repository.search.ProductSearchRepository;
import com.smartcart.service.util.DatabaseInitializerHelper;



@Service
@Transactional
public class DatabaseInitializerService {

	private final Logger log = LoggerFactory.getLogger(DatabaseInitializerService.class);
	
	@Inject
	public CategoryRepository categoryRepository;
	
	@Inject
	public EdgeRepository edgeRepository;
	
	@Inject
	public ProductRepository productRepository;
	
	@Inject
	public ProductSearchRepository productSearchRepository;
	
	@Inject
	public PriceRepository priceRepository;
	
	@Inject
	public ShopRepository shopRepository;
	
	@Inject
	public BrandRepository brandRepository;
	
	
    public boolean loadData() {
        log.debug("Database initialization started...");
        
        truncateDatabase();
        
        rebuildDatabase();
        
        //buildDatabaseIndex();
        
        log.debug("Database initialization finished!");
        return true;
    }
    
    public void truncateDatabase() {
        edgeRepository.deleteAll();
        log.debug("Database initialization - Edges deleted!");
        
        priceRepository.deleteAll();
        log.debug("Database initialization - Prices deleted!");
        
        shopRepository.deleteAll();
        log.debug("Database initialization - Shops deleted!");

        productRepository.deleteAll();
        log.debug("Database initialization - Products deleted!");
        
        productSearchRepository.deleteAll();
        log.debug("Database initialization - Products deleted!");
        
        categoryRepository.deleteAll();
        log.debug("Database initialization - Categories deleted!");
        
        
    }
    
    public void rebuildDatabase() {
        List<Category> categories = DatabaseInitializerHelper.initCategories();
        
        List<Product> products = DatabaseInitializerHelper.initProducts(categories);

        List<Edge> edges = DatabaseInitializerHelper.initEdges(products);
        
        List<Brand> brands =  DatabaseInitializerHelper.initBrands();
        
        List<Shop> shops =  DatabaseInitializerHelper.initShops(brands);

        List<Price> prices =  DatabaseInitializerHelper.initPrices(products, shops);

        categories.forEach(c -> callBfs(c));
        products.forEach(p -> productRepository.save(p));
        edges.forEach(e -> edgeRepository.save(e));
        brands.forEach(b -> brandRepository.save(b));
        shops.forEach(s -> shopRepository.save(s));
        prices.forEach(p -> priceRepository.save(p));
        
    }
    
    public void buildDatabaseIndex() {
    	
    	
        log.debug("Indexin the products... ?");
        List<Product> allSavedProds = productRepository.findAll();
        
        allSavedProds.forEach(
       		prod -> {
       			Hibernate.initialize(prod.getProductPrices());
       			
       			// TODO : fix this shit
       			//productSearchRepository.index( prod );
       		}
        );
        
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
