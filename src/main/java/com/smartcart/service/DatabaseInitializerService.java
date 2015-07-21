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
        
        Category category = 
        		new Category("FRUIT_AND_VEGETABLES")
        			.addSub(new Category("VEGETABLES"))
        			.addSub(new Category("FRUIT")
        				.addSub(new Category("APPLE"))
        				.addSub(new Category("PEAR"))
        			);
        
        callBfs(category);
        
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
