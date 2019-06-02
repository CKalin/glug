import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Player} from '../model/player';
import {PlayerService} from '../service/player.service';

@Component({
  templateUrl: 'exchange-glug.page.html',
  styleUrls: ['exchange-glug.page.scss']
})
export class ExchangeGlugPage implements OnInit {
  openGlugs = 0;
  players: Array<Player> = [];
  timeLeft = null;

  constructor(private game: GameService, private player: PlayerService) {
  }

  ngOnInit(): void {
    this.game.getTimeLeft().subscribe(g => this.timeLeft = g);
    this.player.getParticipants().subscribe(p => this.players = p);
    this.player.getYourSelf().subscribe(p => this.openGlugs = p.slugCountToAllocate);
  }

  addGlug(player: Player) {
    if (this.openGlugs > 0) {
      this.openGlugs--;
      player.slugCountReceived++;
      this.game.addGlug(player);
    }
  }
}
