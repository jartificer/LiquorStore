package com.example.liquorstore.repository.liquors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiquorRepository extends JpaRepository<Liquor, Integer> {
  Optional<Liquor> findLiquorByName(String name);

}
