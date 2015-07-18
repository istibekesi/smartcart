package com.smartcart.service;


import java.util.HashSet;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

        /*
        Category category = 
        		new Category.ProductCategoryBuilder("FRUIT_AND_VEGETABLES")
        			.sub(new Category.ProductCategoryBuilder("FRUIT").build())
        			.sub(new Category.ProductCategoryBuilder("VEGETABLES").build())
        		.build();
        categoryRepository.save(category);
        */
        
        Category category1 = new Category();
        category1.setName("FRUIT_AND_VEGETABLES");
        category1.setParent(null);
        
        Category category1_1 = new Category();
        category1_1.setName("FRUIT");
        category1_1.setParent(category1);
        
        Category category1_2 = new Category();
        category1_2.setName("VEGETABLES");
        category1_2.setParent(category1);
        
        HashSet<Category> c = new HashSet<Category>();
        c.add(category1_1);
        c.add(category1_2);
        category1.setChildren(c);
        
        categoryRepository.save(category1);
        categoryRepository.save(category1_1);
        categoryRepository.save(category1_2);
        
        
        log.debug("Database initialization finished!");
        return true;
    }
}
