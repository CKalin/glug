import {Component, OnInit} from '@angular/core';
import {AlertController} from '@ionic/angular';
import {flatMap} from 'rxjs/operators';
import {GameService} from './service/game.service';
import {PlayerService} from './service/player.service';

@Component({
  templateUrl: 'game.page.html',
  styleUrls: ['game.page.scss']
})
export class GamePage implements OnInit {
  public playerName = '';

  constructor(
      private playerService: PlayerService,
      private gameService: GameService,
      private alertController: AlertController) {
  }

  ngOnInit(): void {
  }

  createGame() {
    this.playerService.register(this.playerName)
        .pipe(flatMap(p => this.gameService.createGame(p.id)))
        .subscribe();
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
                this.playerService.register(this.playerName)
                    .subscribe(p => this.gameService.joinGame(form.gameId as string, p.id));
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
