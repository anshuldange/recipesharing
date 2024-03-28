import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';

export interface DeleteConfirmationData {
  title: string; // Optional title for the dialog
  message: string; // The confirmation message
}

@Component({
  selector: 'app-delete-confirmation',
  standalone: true,
  imports: [MatDialogModule],
  templateUrl: './delete-confirmation.component.html',
  styleUrl: './delete-confirmation.component.scss'
})
export class DeleteConfirmationComponent {

  constructor(
    public dialogRef: MatDialogRef<DeleteConfirmationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteConfirmationData
  ) {}

  onConfirmClick(): void {
    this.dialogRef.close(true); // Pass true on confirmation
  }

  onCloseClick(): void {
    this.dialogRef.close(false); // Pass false on cancellation
  }
}

