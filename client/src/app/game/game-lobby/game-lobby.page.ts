import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {GameService} from '../service/game.service';
import {Player} from '../service/player';

@Component({
  templateUrl: 'game-lobby.page.html',
  styleUrls: ['game-lobby.page.scss'],
})
export class GameLobbyPage implements OnInit {
  participants: Array<Player> = [];
  code = '';
  mode: 'create' | 'join' = 'create';

  constructor(private service: GameService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.params.subscribe(p => this.mode = p.mode);
    this.service.getParticipants().subscribe(p => this.participants = p);
    this.service.getCode().subscribe(c => this.code = c);
  }

  startGame() {
    this.router.navigateByUrl('/challenge');
  }
}
