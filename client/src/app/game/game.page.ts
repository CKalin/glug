import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AlertController} from '@ionic/angular';
import {GameService} from './service/game.service';

@Component({
  templateUrl: 'game.page.html',
  styleUrls: ['game.page.scss'],
})
export class GamePage implements OnInit {
  name = '';

  constructor(private gameService: GameService, private router: Router, private alertController: AlertController) {
  }

  ngOnInit(): void {
    this.name = this.gameService.playerName;
  }

  createGame() {
    this.gameService.createGame();
    this.router.navigateByUrl('lobby/create');
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
          type: 'text',
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
            this.gameService.joinGame(form.gameId);
          }
        }
      ]
    });

    await alert.present();
  }

  nameChanged(name: any) {
    this.name = name;
    this.gameService.playerName = name;
  }
}
