package com.smartcart.repository;

import com.smartcart.domain.Edge;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Edge entity.
 */
public interface EdgeRepository extends JpaRepository<Edge,Long> {

}
