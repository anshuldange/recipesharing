import { Component, Input, input } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import {MatIconModule} from '@angular/material/icon';
import { EditRecipeFormComponent } from '../edit-recipe-form/edit-recipe-form.component';
import { RecipeServiceService } from '../../services/recipe/recipe-service.service';
import { DeleteConfirmationComponent } from '../../opendilog/delete-confirmation/delete-confirmation.component';
import { DeleteConfirmationData } from '../../opendilog/delete-confirmation/delete-confirmation.component';

@Component({
  selector: 'app-recipe-card',
  standalone: true,
  imports: [
    MatCardModule, 
    MatButtonModule,
    MatIconModule,
    DeleteConfirmationComponent
  ],
  templateUrl: './recipe-card.component.html',
  styleUrl: './recipe-card.component.scss'
})
export class RecipeCardComponent {

  @Input() recipe:any

  constructor(public dialog: MatDialog, private recipeService: RecipeServiceService){}

  onDeleteClick() {
    const confirmationData: DeleteConfirmationData = {
      message: 'Are you sure you want to delete this item?',
      title: ''
    };

    const dialogRef = this.dialog.open(DeleteConfirmationComponent, {
      width: '250px', // Optional: Set dialog width
      data: confirmationData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.recipeService.deleteRecipe(this.recipe.id).subscribe(
          (data:any)=>console.log("data deleted successfully", data),
          (error)=>console.log("error", error) 
        )
        console.log('Item deleted!');
      } else {
        console.log('Deletion cancelled.');
      }
    });
  }

  handleOpenEditRecipeForm(){
    this.dialog.open(EditRecipeFormComponent, 
      { data: this.recipe})
  }

  // handleDeleteRecipe(){
  //   this.recipeService.deleteRecipe(this.recipe.id).subscribe()
  // }

}
