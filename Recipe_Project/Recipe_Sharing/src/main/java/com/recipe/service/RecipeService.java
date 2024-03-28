package com.recipe.service;

import java.util.List;

import com.recipe.model.Recipe;
import com.recipe.model.User;

public interface RecipeService {

	public Recipe createRecipe(Recipe recipe, User user);

	public Recipe findRecipeByid(Long id) throws Exception;

	public void deleteRecipe(Long id) throws Exception;

	public Recipe updateRecipe(Recipe recipe, Long id) throws Exception;

	public List<Recipe> findAllRecipe();

	public Recipe likeRecipe(Long recipeId, User user) throws Exception;

//	Recipe likeRecipe(Long id, User user) throws Exception;
}
