import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {IonicModule} from '@ionic/angular';
import {GoogleChartsModule} from 'angular-google-charts';
import {AcknowledgeGlugPage} from './acknowledge-glug/acknowledge-glug.page';
import {ExchangeGlugPage} from './exchange-glug/exchange-glug.page';
import {GameChallengePage} from './game-challenge/game-challenge.page';
import {GameLobbyPage} from './game-lobby/game-lobby.page';

import {GamePage} from './game.page';
import {GameRepository} from './repository/game.repository';
import {PlayerRepository} from './repository/player.repository';
import {StompClientRepository} from './repository/stomp.client.repository';
import {GameService} from './service/game.service';
import {PlayerService} from './service/player.service';
import {StatisticsPage} from './statistic/statistics-page.component';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    IonicModule,
    GoogleChartsModule.forRoot(),
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
      },
      {
        path: 'exchange',
        component: ExchangeGlugPage
      },
      {
        path: 'acknowledge',
        component: AcknowledgeGlugPage
      },
      {
        path: 'statistics',
        component: StatisticsPage
      }
    ])
  ],
  providers: [GameService, PlayerService, GameRepository, PlayerRepository, StompClientRepository],
  declarations: [GamePage, GameLobbyPage, GameChallengePage, ExchangeGlugPage, AcknowledgeGlugPage, StatisticsPage]
})
export class GameModule {
}
