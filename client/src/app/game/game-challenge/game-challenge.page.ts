import {Component, OnInit} from '@angular/core';
import {ChallengeAnswerAction} from '../model/actions';
import {Answer, Challenge} from '../model/challenge';
import {Player} from '../model/player';
import {GameService} from '../service/game.service';
import {Message} from '../model/message';
import {PlayerService} from '../service/player.service';

@Component({
  templateUrl: 'game-challenge.page.html',
  styleUrls: ['game-challenge.page.scss']
})
export class GameChallengePage implements OnInit {

  challenge: Challenge;
  message: Message;
  yourAnswer: ChallengeAnswerAction;
  lastAnswer: ChallengeAnswerAction;

  you: Player;
  participants: Array<Player> = [];

  constructor(private service: GameService, private playerService: PlayerService) {
  }

  ngOnInit(): void {
    this.playerService.getYourSelf().subscribe(p => this.you = p);
    this.playerService.getParticipants().subscribe(p => this.participants = p);
    this.service.getChallengeUpdates().subscribe(c => {
      if (this.isChallenge(c)) {
        this.challenge = c as Challenge;
        this.message = null;
      } else {
        this.message = c as Message;
        this.challenge = null;
      }
    });
    this.service.getAnswers().subscribe(a => {
      this.lastAnswer = a;
      if (this.you && a && this.you.id === a.playerId) {
        this.yourAnswer = a;
      }
    });
  }

  private isChallenge(c: Challenge | Message) {
    return (c as Challenge).question !== undefined;
  }

  sendAnswer(answer: Answer) {
    this.service.answer(this.challenge, answer);
  }

  getColor(answer: Answer) {
    if (this.yourAnswer && this.challenge && this.yourAnswer.challengeId === this.challenge.id && this.yourAnswer.answerId === answer.id) {
      if (this.yourAnswer.correct) {
        return 'success';
      }
      return 'danger';
    }
    return 'light';
  }
}
