import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {GameChallengePage} from './game-challenge/game-challenge.page';
import {GameLobbyPage} from './game-lobby/game-lobby.page';

import {GamePage} from './game.page';
import {GameService} from './service/game.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild([
      {
        path: '',
        component: GamePage
      },
      {
        path: 'lobby/:mode',
        component: GameLobbyPage
      },
      {
        path: 'challenge',
        component: GameChallengePage
      }
    ])
  ],
  providers: [GameService],
  declarations: [GamePage, GameLobbyPage, GameChallengePage]
})
export class GameModule {
}
