package com.smartcart.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.smartcart.domain.enumeration.UnitEnum;
import com.smartcart.repository.CategoryRepository;
import com.smartcart.repository.EdgeRepository;
import com.smartcart.repository.PriceRepository;
import com.smartcart.repository.ProductRepository;
import com.smartcart.repository.ShopRepository;


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
        
        categoryRepository.deleteAll();
        log.debug("Database initialization - Categories deleted!");
        
        shopRepository.deleteAll();
        log.debug("Database initialization - Shops deleted!");

        productRepository.deleteAll();
        log.debug("Database initialization - Products deleted!");
        
        List<Category> categories = Arrays.asList( 
        		new Category("MC_FRUIT_AND_VEGETABLES", "Zöldség, gyümölcs")
        			.addSub(new Category("C_VEGETABLES", "Zoldség"))
        			.addSub(new Category("C_FRUIT", "Gyümölcs")
       			), 
        		new Category("MC_MILK_EGG", "Tejtermék")
    				.addSub(new Category("C_MILK", "Tej"))
    				.addSub(new Category("C_EGG", "Tojás")
    			), 
        		new Category("MC_BAKERY", "Pékáru"), 
				new Category("MC_MEAT", "Hús"),
				new Category("MC_FOOD", "Alapvető élémiszer"),
				new Category("MC_DRINKS", "Üdítő")
					.addSub(new Category("C_MINERAL_WATER", "Ásványvíz"))
					.addSub(new Category("C_COFFEE", "Kávé"))
					.addSub(new Category("C_TEE", "Tea"))
					.addSub(new Category("C_JUICE", "Gyümölcslé"))
					.addSub(new Category("C_BEVERAGES", "Üdítőitalok")
				),
				new Category("MC_ALCOHOLIC", "Alkoholos italok")
        			.addSub(new Category("C_BEER", "Sör"))
        			.addSub(new Category("C_CIDER", "Cider"))
        			.addSub(new Category("C_LIQUOR", "Szeszesital"))
        			.addSub(new Category("C_WINE", "Bor"))
        );
        
        List<Product> products = new ArrayList<Product>();
        products.add(Product.productBuilder("5990000000001", "Beck's világos sör 5%", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("5990000000002", "Carlsberg minőségi világos sör 5%", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("5990000000003", "Dreher Classic világos sör 5,2%", "", UnitEnum.ml, 500, categories, "C_BEER"));
        products.add(Product.productBuilder("5990000000004", "Heineken prémium világos sör 5%", "", UnitEnum.ml, 400, categories, "C_BEER"));
        products.add(Product.productBuilder("5990000000005", "Heineken prémium világos sör 5%", "", UnitEnum.ml, 330, categories, "C_BEER"));

        List<Edge> edges = new ArrayList<Edge>();
        edges.addAll(Edge.edgeBuilder(products, "5990000000001", "5990000000002", new BigDecimal(0.80) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000002", "5990000000003", new BigDecimal(0.70) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000003", "5990000000004", new BigDecimal(0.75) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000004", "5990000000005", new BigDecimal(0.75) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000005", "5990000000001", new BigDecimal(0.75) ));
        
        List<Shop> shops = new ArrayList<Shop>();
        shops.add(new Shop("TESCO"));
        shops.add(new Shop("INTERSPAR"));
        shops.add(new Shop("LIDL"));
        shops.add(new Shop("ALDI"));
        shops.add(new Shop("PENNY"));
        shops.add(new Shop("CBA"));
        
        List<Price> prices = new ArrayList<Price>();
        prices.add(Price.priceBuilder(products, shops, "5990000000001", "TESCO", new BigDecimal(199)));
        prices.add(Price.priceBuilder(products, shops, "5990000000001", "INTERSPAR", new BigDecimal(229)));
        prices.add(Price.priceBuilder(products, shops, "5990000000001", "ALDI", new BigDecimal(219)));

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
