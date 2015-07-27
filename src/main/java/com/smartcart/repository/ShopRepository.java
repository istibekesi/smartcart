package com.smartcart.repository;

import com.smartcart.domain.Shop;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Shop entity.
 */
public interface ShopRepository extends JpaRepository<Shop,Long> {

}
