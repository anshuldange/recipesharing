package com.recipe.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.model.Recipe;
import com.recipe.model.User;
import com.recipe.repository.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService{
	
	@Autowired
	private RecipeRepository recipeRepository;

	@Override
	public Recipe createRecipe(Recipe recipe, User user) {
		// TODO Auto-generated method stub
		Recipe createdRecipe = new Recipe();
		createdRecipe.setTitle(recipe.getTitle());
		createdRecipe.setImage(recipe.getImage());
		createdRecipe.setDescription(recipe.getDescription());
		createdRecipe.setUser(user);
		createdRecipe.setCreatedAt(LocalDateTime.now());
		return recipeRepository.save(createdRecipe);
	}

	@Override
	public Recipe findRecipeByid(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Recipe> opt = recipeRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new Exception("recipe not found with id " + id);
	}

	@Override
	public void deleteRecipe(Long id) throws Exception {
		// TODO Auto-generated method stub
		findRecipeByid(id);
		recipeRepository.deleteById(id);
		
	}

	@Override
	public Recipe updateRecipe(Recipe recipe, Long id) throws Exception {
		// TODO Auto-generated method stub
		Recipe oldRecipe = findRecipeByid(id);
		
		if(recipe.getTitle()!=null) {
			oldRecipe.setTitle(recipe.getTitle());
		}
		if(recipe.getImage()!=null) {
			oldRecipe.setImage(recipe.getImage());
		}
		if(recipe.getDescription()!=null) {
			oldRecipe.setDescription(recipe.getDescription());
		}
		if(recipe.isVegetarian()!=false) {
			oldRecipe.setVegetarian(true);
		}
		return recipeRepository.save(oldRecipe);
	}

	@Override
	public List<Recipe> findAllRecipe() {
		// TODO Auto-generated method stub
		return recipeRepository.findAll();
	}

	@Override
	public Recipe likeRecipe(Long recipeId, User user) throws Exception {
		// TODO Auto-generated method stub
		Recipe recipe = findRecipeByid(recipeId);
		if(recipe.getLikes().contains(user.getId())){
			recipe.getLikes().remove(user.getId());
		}
		else {
			recipe.getLikes().add(user.getId());
		}
		return recipeRepository.save(recipe);
	}

	

	

}
