package com.smartcart.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smartcart.domain.Product;
import com.smartcart.service.DatabaseInitializerService;
import com.smartcart.service.UserService;

@RestController
@RequestMapping("/api/initDb")
public class DatabaseInitializerResource {

    private final Logger log = LoggerFactory.getLogger(DatabaseInitializerResource.class);
    
    @Inject
    private DatabaseInitializerService dbService;
    
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> reloadDatabase() throws URISyntaxException {
        log.debug("REST request to INITIALIZE DATABSE!");

        // here comes the testdata
        
        dbService.loadData();
        
        
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Void>(responseHeaders, HttpStatus.CREATED);

    }
    
}
