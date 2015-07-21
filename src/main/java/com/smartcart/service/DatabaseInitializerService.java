package com.smartcart.service;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smartcart.domain.Category;
import com.smartcart.repository.CategoryRepository;


@Service
public class DatabaseInitializerService {

	private final Logger log = LoggerFactory.getLogger(DatabaseInitializerService.class);
	
	@Inject
	private CategoryRepository categoryRepository;
	
    public boolean loadData() {
        log.debug("Database initialization started...");
        
        categoryRepository.deleteAll();
        log.debug("Database initialization - Categories deleted!");
        
        Category[] categories = { 
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
					.addSub(new Category("BEVREGES", "Üdítőitalok")
				),
				new Category("MC_ALCOHOLIC", "Alkoholos italok")
        };
        
        
        for (Category c : categories) {
        	callBfs(c);
        }
        //callBfs(category);
        
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
