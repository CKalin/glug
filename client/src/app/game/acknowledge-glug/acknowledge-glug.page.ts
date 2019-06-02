import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Player} from '../model/player';
import {PlayerService} from '../service/player.service';

@Component({
  templateUrl: 'acknowledge-glug.page.html',
  styleUrls: ['acknowledge-glug.page.scss']
})
export class AcknowledgeGlugPage implements OnInit {
  players: Array<Player> = [];
  you: Player;

  chart = {
    data: [],
    columnNames: [
      'Von', 'An', 'Glugs'
    ],
    options: {
      width: '100%',
      animation: {
        duration: 1000,
        easing: 'out',
      },
    }
  };

  constructor(private game: GameService, private player: PlayerService) {
  }

  ngOnInit(): void {
    this.player.getParticipants().subscribe(p => this.players = p);
    this.player.getYourSelf().subscribe(y => {
      if (y.slugCountReceived === 0) {
        y.glugsAcknowledged = true;
      }
      this.you = y;
    });

    function prepareData(s: Array<Array<any>>) {
      return s.map(i => {
        const n = [];
        n.push(...i);
        n[1] = n[1] + ' ';
        return n;
      });
    }

    this.game.getGlugStatistics().subscribe(s => this.chart.data = prepareData(s));
  }

  acknowledgeGlugs() {
    this.game.acknowledgeGlugs();
  }

  startNewRound() {
    this.game.startNewRound();
  }

  allAcknowledged() {
    return !this.players.some(p => !p.glugsAcknowledged);
  }
}
