import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Player} from '../service/player';

@Component({
  templateUrl: 'statistics-page.component.html',
  styleUrls: ['statistics-page.component.scss']
})
export class StatisticsPage implements OnInit {

  constructor(private service: GameService) {
  }

  ngOnInit(): void {
  }
}
