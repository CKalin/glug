import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AlertController} from '@ionic/angular';
import {GameService} from './service/game.service';

@Component({
  templateUrl: 'game.page.html',
  styleUrls: ['game.page.scss']
})
export class GamePage implements OnInit {
  public playerName = '';

  constructor(private gameService: GameService, private router: Router, private alertController: AlertController) {
  }

  ngOnInit(): void {
  }

  createGame() {
    this.gameService.createGame(this.playerName);
  }

  joinGame() {
    this.presentAlertPrompt();
  }

  async presentAlertPrompt() {
    const alert = await this.alertController.create({
      header: 'Spiel Beitreten',
      message: 'Bitte Spiel ID eingeben',
      inputs: [
        {
          name: 'gameId',
          type: 'number',
          label: 'Spiel ID',
          placeholder: ''
        }
      ],
      buttons: [
        {
          text: 'Abbrechen',
          role: 'cancel',
          cssClass: 'secondary'
        }, {
          text: 'Beitreten',
          handler: (form) => {
            this.gameService.validateAccessCode(form.gameId).subscribe(valid => {
              if (valid) {
                this.gameService.joinGame(this.playerName, form.gameId);
              } else {
                this.presetInvalidCodePopup();
              }
            });
          }
        }
      ]
    });

    await alert.present();
  }

  async presetInvalidCodePopup() {
    const alert = await this.alertController.create({
      header: 'Fehler',
      subHeader: 'Code Ungültig',
      message: 'Bitte überprüfe den Code und versuche es erneut.',
      buttons: ['OK']
    });

    await alert.present();
  }
}
