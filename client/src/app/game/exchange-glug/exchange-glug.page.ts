import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Player} from '../service/player';

@Component({
  templateUrl: 'exchange-glug.page.html',
  styleUrls: ['exchange-glug.page.scss']
})
export class ExchangeGlugPage implements OnInit {
  openGlugs = 0;
  players: Array<Player> = [];
  timeLeft = 0;

  constructor(private service: GameService) {
  }

  ngOnInit(): void {
    this.service.getOpenGlugs().subscribe(g => this.openGlugs = g);
    this.service.getTimeLeft().subscribe(g => this.timeLeft = g);
    this.service.getParticipants().subscribe(p => this.players = p);
  }

  addGlug(player: Player) {
    this.service.addGlug(player);
  }
}
