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
import com.smartcart.domain.Product;
import com.smartcart.domain.enumeration.UnitEnum;
import com.smartcart.repository.CategoryRepository;
import com.smartcart.repository.EdgeRepository;
import com.smartcart.repository.ProductRepository;


@Service
public class DatabaseInitializerService {

	private final Logger log = LoggerFactory.getLogger(DatabaseInitializerService.class);
	
	@Inject
	private CategoryRepository categoryRepository;
	
	@Inject
	private EdgeRepository edgeRepository;
	
	@Inject
	private ProductRepository productRepository;
	
    public boolean loadData() {
        log.debug("Database initialization started...");
        
        categoryRepository.deleteAll();
        log.debug("Database initialization - Categories deleted!");
        
        edgeRepository.deleteAll();
        log.debug("Database initialization - Edges deleted!");
        
        productRepository.deleteAll();
        log.debug("Database initialization - Products deleted!");
        
        
        List<Category> categories = Arrays.asList( 
        		new Category("MC_FRUIT_AND_VEGETABLES", "Zöldség, gyümölcs")
        			.addSub(new Category("VEGETABLES", "Zoldség"))
        			.addSub(new Category("FRUIT", "Gyümölcs")
       			), 
        		new Category("MC_MILK_EGG", "Tejtermék")
    				.addSub(new Category("MILK", "Tej"))
    				.addSub(new Category("EGG", "Tojás")
    			), 
        		new Category("MC_BAKERY", "Pékáru"), 
				new Category("MC_MEAT", "Hús"),
				new Category("MC_FOOD", "Alapvető élémiszer"),
				new Category("MC_DRINKS", "Üdítő")
					.addSub(new Category("MINERAL_WATER", "Ásványvíz"))
					.addSub(new Category("COFFEE", "Kávé"))
					.addSub(new Category("TEE", "Tea"))
					.addSub(new Category("JUICE", "Gyümölcslé"))
					.addSub(new Category("BEVERAGES", "Üdítőitalok")
				),
				new Category("MC_ALCOHOLIC", "Alkoholos italok")
        			.addSub(new Category("BEER", "Sör"))
        			.addSub(new Category("CIDER", "Cider"))
        			.addSub(new Category("LIQUOR", "Szeszesital"))
        			.addSub(new Category("WINE", "Bor"))
        );
        
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("5990000000001", "Beck's világos sör 5%", "", UnitEnum.ml, 500));
        products.add(new Product("5990000000002", "Carlsberg minőségi világos sör 5%", "", UnitEnum.ml, 500));
        products.add(new Product("5990000000003", "Dreher Classic világos sör 5,2%", "", UnitEnum.ml, 500));
        products.add(new Product("5990000000004", "Heineken prémium világos sör 5%", "", UnitEnum.ml, 400));
        products.add(new Product("5990000000005", "Heineken prémium világos sör 5%", "", UnitEnum.ml, 330));


        List<Edge> edges = new ArrayList<Edge>();
        edges.addAll(Edge.edgeBuilder(products, "5990000000001", "5990000000002", new BigDecimal(0.80) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000002", "5990000000003", new BigDecimal(0.70) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000003", "5990000000004", new BigDecimal(0.75) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000004", "5990000000005", new BigDecimal(0.75) ));
        edges.addAll(Edge.edgeBuilder(products, "5990000000005", "5990000000001", new BigDecimal(0.75) ));

        categories.forEach(c -> callBfs(c));
        products.forEach(p -> productRepository.save(p));
        edges.forEach(e -> edgeRepository.save(e));
        
        log.debug("Database initialization - Default categories added!");
        
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
