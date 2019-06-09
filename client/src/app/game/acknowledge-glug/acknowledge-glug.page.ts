import {Component, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {SlugAllocation} from '../model/actions';
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
    this.game.getGlugStatistics()
        .pipe(map(s => this.prepareData(s)))
        .subscribe(s => this.chart.data = s);
  }

  prepareData(s: Array<SlugAllocation>) {
    return s.map(i => {
      const n = [];
      n.push(this.findPlayer(i.fromPlayerId).name, this.findPlayer(i.toPlayerId).name, i.slugCountAllocated);
      n[1] = n[1] + ' ';
      return n;
    });
  }

  findPlayer(id: number) {
    return this.players.filter(p => p.id === id)[0];
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
