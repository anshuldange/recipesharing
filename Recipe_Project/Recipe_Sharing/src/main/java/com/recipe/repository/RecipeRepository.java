package com.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.model.Recipe;


public interface RecipeRepository extends JpaRepository<Recipe, Long>{

}
