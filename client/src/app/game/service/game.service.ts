import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {map} from 'rxjs/operators';
import {Challenge} from './challenge';
import {Message} from './message';
import {Player} from './player';

@Injectable()
export class GameService {
  public playerName: string;
  private participants: Subject<Array<Player>> = new BehaviorSubject(
      [
        {name: 'Christian', isYou: true, id: 'Test', glugs: 3, glugsAcknowledged: true, isLeader: true},
        {name: 'Sebastian', isYou: false, id: 'Test2', glugs: 7, glugsAcknowledged: false, isLeader: false},
        {name: 'Florian', isYou: false, id: 'Test3', glugs: 8, glugsAcknowledged: false, isLeader: false},
        {name: 'Erik', isYou: false, id: 'Test4', glugs: 4, glugsAcknowledged: true, isLeader: false},
        {name: 'Steffen', isYou: false, id: 'Test5', glugs: 0, glugsAcknowledged: true, isLeader: false}
      ] // Test Data
  );

  private statistics = new BehaviorSubject(
      [
        [ 'Christian', 'Florian', 3 ],
        [ 'Florian', 'Erik', 2 ],
        [ 'Christian', 'Sebastian', 3 ],
        [ 'Erik', 'Florian', 4 ],
        [ 'Sebastian', 'Erik', 2 ],
        [ 'Sebastian', 'Florian', 1 ],
        [ 'Erik', 'Christian', 3 ],
        [ 'Steffen', 'Sebastian', 4 ]
      ]
  );

  private code: Subject<string> = new BehaviorSubject(
      '483487' // Test Data
  );
  private openGlugs: Subject<number> = new BehaviorSubject(
      12 // Test Data
  );
  private challengeUpdates: Subject<Challenge | Message> = new BehaviorSubject(
      {
        color: 'blue',
        border: 'red',
        object: 'hexagon',
        text: 'gelb',
        textColor: 'yellow',
        question: 'Das Quadrat ist blau.'
      } as Challenge // Test Data
      // {
      //   text: '3',
      //   subtext: 'Du hast du letzte Challenge gewonnen.'
      // } as Message
  );
  private timeLeft: Subject<number> = new BehaviorSubject(
      5 // Test Data
  );

  createGame() {
  }

  getParticipants(): Observable<Array<Player>> {
    return this.participants.asObservable();
  }
  getYourself(): Observable<Player> {
    return this.participants.asObservable()
        .pipe(map(list => list.filter(p => p.isYou)[0]));
  }

  getCode(): Observable<string> {
    return this.code.asObservable();
  }

  getChallengeUpdates(): Observable<Challenge | Message> {
    return this.challengeUpdates.asObservable();
  }

  joinGame(gameId: any) {
  }

  addGlug(player: Player) {
  }

  acknowledgeGlugs() {
  }

  getOpenGlugs(): Observable<number> {
    return this.openGlugs.asObservable();
  }

  getTimeLeft(): Observable<number> {
    return this.timeLeft.asObservable();
  }

  startNewRound() {
  }

  getGlugStatistics(): Observable<Array<Array<any>>> {
    return this.statistics.asObservable();
  }

  answer(challenge: Challenge, correct: boolean) {
  }
}








