import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';

@Injectable()
export class GameService {
  public playerName: string;
  private participants: Subject<Array<Player>> = new BehaviorSubject(
      [{name: 'Christian', isYou: true, id: 'Test'}] // Test Data
  );
  private code: Subject<string> = new BehaviorSubject(
      '483487' // Test Data
  );

  createGame() {
    this.participants.next([{name: this.playerName, isYou: true, id: ''}]);
  }

  getParticipants(): Observable<Array<Player>> {
    return this.participants.asObservable();
  }

  getCode(): Observable<string> {
    return this.code.asObservable();
  }

  joinGame(gameId: string) {

  }
}

export class Player {
  name: string;
  id: string;
  isYou = false;
}
