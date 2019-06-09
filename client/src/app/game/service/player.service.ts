import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject, Subscription} from 'rxjs';
import {map, take, tap} from 'rxjs/operators';
import {ConfirmSlugAction, RoundFinishedAction} from '../model/actions';
import {Player} from '../model/player';
import {PlayerRepository} from '../repository/player.repository';

@Injectable()
export class PlayerService {

  private you: Player;
  private youSubject: Subject<Player> = new BehaviorSubject(null);
  private participants: Subject<Array<Player>> = new BehaviorSubject([]);
  private activeGameSubscription: Subscription;

  constructor(private repository: PlayerRepository) {
    this.participants.asObservable()
        .pipe(map(list => list.filter(p => p.you)[0]))
        .subscribe(this.youSubject);
    this.youSubject.asObservable().subscribe(y => this.you = y);
  }

  public register(playerName: string): Observable<Player> {
    return this.repository.register(playerName)
        .pipe(tap(p => this.youSubject.next(p)));
  }

  public watchGame(code: string) {
    if (this.activeGameSubscription) {
      this.activeGameSubscription.unsubscribe();
    }
    this.activeGameSubscription = this.repository.getPlayerJoinedEvents(code)
        .pipe(map(players => this.processPlayers(players.inGamePlayers)))
        .subscribe(this.participants);
  }

  public getParticipants(): Observable<Array<Player>> {
    return this.participants;
  }

  public getYourSelf(): Observable<Player> {
    return this.youSubject.asObservable();
  }

  private processPlayers(players: Array<Player>) {
    return players.map(p => {
      if (p.id === this.you.id) {
        return Object.assign({}, p, {you: true});
      }
      return p;
    });
  }

  registerResults(action: RoundFinishedAction) {
    this.participants
        .pipe(take(1))
        .pipe(map(all => {
          return all.map(p => {
            const match = action.results.filter(r => (r as any).playerId === p.id);
            return Object.assign({}, p, match[0], {glugsAcknowledged: false});
          });
        }))
        .subscribe(x => this.participants.next(x));
  }

  registerSlugConfirmed(action: ConfirmSlugAction) {
    this.participants
        .pipe(take(1))
        .pipe(map(all => {
          return all.map(p => {
            if (action.playerId === p.id) {
              return Object.assign({}, p, {glugsAcknowledged: true});
            }
            return p;
          });
        }))
        .subscribe(x => this.participants.next(x));
  }
}
