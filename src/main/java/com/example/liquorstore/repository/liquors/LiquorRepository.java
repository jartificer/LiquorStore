package com.example.liquorstore.repository.liquors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LiquorRepository extends JpaRepository<Liquor, Integer> {

}
