import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Player} from '../service/player';

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

  constructor(private service: GameService) {
  }

  ngOnInit(): void {
    this.service.getParticipants().subscribe(p => this.players = p);
    this.service.getYourself().subscribe(y => this.you = y);

    function prepareData(s: Array<Array<any>>) {
      console.log(s);
      return s.map(i => {
        const n = [];
        n.push(...i);
        n[1] = n[1] + ' ';
        return n;
      });
    }

    this.service.getGlugStatistics().subscribe(s => this.chart.data = prepareData(s));
  }

  acknowledgeGlugs() {
    this.service.acknowledgeGlugs();
  }

  startNewRound() {
    this.service.startNewRound();
  }

  allAcknowledged() {
    return !this.players.some(p => !p.glugsAcknowledged);
  }
}
