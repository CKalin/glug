import {Component, OnInit} from '@angular/core';
import {Challenge} from '../service/challenge';
import {GameService} from '../service/game.service';
import {Message} from '../service/message';

@Component({
  templateUrl: 'game-challenge.page.html',
  styleUrls: ['game-challenge.page.scss']
})
export class GameChallengePage implements OnInit {

  challenge: Challenge;
  message: Message;

  constructor(private service: GameService) {
  }

  ngOnInit(): void {
    this.service.getChallengeUpdates().subscribe(c => {
      if (this.isChallenge(c)) {
        this.challenge = c as Challenge;
        this.message = null;
      } else {
        this.message = c as Message;
        this.challenge = null;
      }
    });
  }

  private isChallenge(c: Challenge | Message) {
    return (c as Challenge).question !== undefined;
  }

  answer(correct: boolean) {
    this.service.answer(this.challenge, correct);
  }
}
