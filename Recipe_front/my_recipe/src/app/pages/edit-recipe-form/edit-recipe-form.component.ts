import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { RecipeServiceService } from '../../services/recipe/recipe-service.service';

@Component({
  selector: 'app-edit-recipe-form',
  standalone: true,
  imports: [
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
    MatButtonModule,
    MatRadioModule  
  ],
  templateUrl: './edit-recipe-form.component.html',
  styleUrl: './edit-recipe-form.component.scss'
})
export class EditRecipeFormComponent {

  recipeItem:any={
    title:"",
    description:"",
    foodType:"",
    image:""
  } //;

  constructor(private recipeService: RecipeServiceService,
    @Inject(MAT_DIALOG_DATA) public recipe:any
    ){}

  onSubmit(){
    this.recipeService.updateRecipe(this.recipeItem).subscribe(
      {
        next:data=>console.log("update",data),
        error:error=>console.log("error", error)
      }
    )
    console.log("values", this.recipeItem)
  }

  ngOnInit(){
    this.recipeItem=this.recipe
  }

}
