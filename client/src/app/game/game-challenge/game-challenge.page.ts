import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';

interface Challenge {
  color: Color;
  border: Color;
  object: 'circle' | 'triangle' | 'square' | 'pentagon' | 'hexagon';
  text: string;
  textColor: Color;
  question: string;
}

type Color = 'blue' | 'yellow' | 'green' | 'brown' | 'grey' | 'red' | 'orange';

@Component({
  templateUrl: 'game-challenge.page.html',
  styleUrls: ['game-challenge.page.scss']
})
export class GameChallengePage implements OnInit {

  challenge: Challenge = {
    color: 'blue',
    border: 'red',
    object: 'hexagon',
    text: 'gelb',
    textColor: 'yellow',
    question: 'Das Quadrat ist blau.'
  };
  roundCount = 1;

  constructor(private service: GameService) {
  }

  ngOnInit(): void {
  }
}
