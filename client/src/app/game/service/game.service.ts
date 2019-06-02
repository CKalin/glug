import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject, Observable, Subject, zip} from 'rxjs';
import {filter, first, map, tap} from 'rxjs/operators';
import {ChallengeAnswerAction, CountdownAction, NewChallengeAction, RoundFinishedAction} from '../model/actions';
import {Answer, Challenge} from '../model/challenge';
import {Message} from '../model/message';
import {Player} from '../model/player';
import {translations} from '../model/translations';
import {GameRepository} from '../repository/game.repository';
import {PlayerService} from './player.service';

@Injectable()
export class GameService {

  private challengeUpdates: Subject<Challenge | Message> = new BehaviorSubject(null);
  private answers: Subject<ChallengeAnswerAction> = new BehaviorSubject(null);
  private timeLeft: Subject<number> = new BehaviorSubject(null);
  private roundId: number;

  constructor(private router: Router, private game: GameRepository, private player: PlayerService) {
  }

  private code: Subject<string> = new BehaviorSubject('');

  createGame(playerId: number) {
    const result = this.game.createGame(playerId)
        .pipe(tap(id => this.connectByCode(id, playerId)));
    this.router.navigateByUrl('lobby/create');
    return result;
  }

  connectByCode(code: string, playerId: number) {
    this.code.next(code);
    this.connectToGame(code, playerId);
  }

  validateAccessCode(code: string): Observable<boolean> {
    return this.game.validateAccessCode(code);
  }

  private connectToGame(code: string, playerId: number) {
    this.game.connect(code, playerId).subscribe(action => {
      switch (action.action) {
        case 'COUNT_DOWN':
          this.router.navigateByUrl('/challenge');
          this.challengeUpdates.next({
            text: (action as CountdownAction).count,
            subtext: 'Gleich gehts los!'
          });
          break;
        case 'NEW_CHALLENGE':
          const q = action as NewChallengeAction;
          this.challengeUpdates.next({
            id: q.challengeId,
            color: q.colorObject,
            border: q.colorObjectBorder,
            backgroundColor: q.colorBackground,
            object: q.shape,
            text: translations[q.text],
            textColor: q.colorText,
            question: q.question,
            answers: q.answers.map(a => ({text: translations[a.text], id: a.id}))
          });
          break;
        case 'CHALLENGE_ANSWERED':
          this.answers.next(action as ChallengeAnswerAction);
          break;
        case 'ALL_SLUGS_ALLOCATED':
          this.router.navigateByUrl('/acknowledge');
          this.player.registerResults(action as RoundFinishedAction);
          break;
        case 'ROUND_FINISHED':
          this.router.navigateByUrl('/exchange');
          const finished = action as RoundFinishedAction;
          this.roundId = finished.roundId;
          this.player.registerResults(finished);
          break;
        default:
          console.log(action);
      }
    });
    this.player.connect(code);
  }

  getAnswers() {
    return this.answers.asObservable();
  }

  getCode(): Observable<string> {
    return this.code.asObservable()
        .pipe(filter(code => code !== ''));
  }

  joinGame(gameId: string, playerId: number) {
    this.connectByCode(gameId, playerId);
    this.router.navigateByUrl('lobby/join');
  }

  startNewRound() {
    this.code.asObservable()
        .pipe(first())
        .pipe(map(c => this.game.startNewRound(c)))
        .subscribe();
  }

  // *************************** MOCKS **********************************

  getChallengeUpdates(): Observable<Challenge | Message> {
    return this.challengeUpdates.asObservable();
  }

  answer(challenge: Challenge, answer: Answer) {
    this.getCodeAndPlayer()
        .subscribe(([c, p]) => this.game.sendAnswer(c, challenge.id, answer.id, p));
  }

  addGlug(player: Player) {
    this.getCodeAndPlayer()
        .subscribe(([c, p]) => this.game.addGlug(c, this.roundId, p, player.id));
  }

  acknowledgeGlugs() {
  }

  getTimeLeft(): Observable<number> {
    return this.timeLeft.asObservable();
  }

  getGlugStatistics(): Observable<Array<Array<any>>> {
    return this.statistics.asObservable();
  }

  private statistics = new BehaviorSubject(
      [
        ['Christian', 'Florian', 3],
        ['Florian', 'Erik', 2],
        ['Christian', 'Sebastian', 3],
        ['Erik', 'Florian', 4],
        ['Sebastian', 'Erik', 2],
        ['Sebastian', 'Florian', 1],
        ['Erik', 'Christian', 3],
        ['Steffen', 'Sebastian', 4]
      ]
  );

  private getCodeAndPlayer() {
    const code = this.code.asObservable()
        .pipe(first());
    const playerId = this.player.getYourSelf()
        .pipe(map(p => (p as Player).id))
        .pipe(first());
    return zip(code, playerId);
  }
}








