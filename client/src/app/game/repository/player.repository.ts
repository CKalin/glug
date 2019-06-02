import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {RxStomp} from '@stomp/rx-stomp';
import {Observable} from 'rxjs';
import {filter, map, tap} from 'rxjs/operators';
import {Action, PlayerCreatedAction, PlayerJoinedAction} from '../model/actions';
import {Player} from '../model/player';
import {StompClientRepository} from './stomp.client.repository';

@Injectable()
export class PlayerRepository {

  private client: RxStomp;

  constructor(private http: HttpClient, clientRepository: StompClientRepository) {
    this.client = clientRepository.getClient();
  }

  register(playerName: string): Observable<Player> {
    const p = new HttpParams().append('name', playerName);
    return this.http.get<PlayerCreatedAction>('/api/player/create', {params: p}).pipe(map(response => {
      return {
        id: response.playerId,
        name: response.name,
        you: true
      } as Player;
    }));
  }

  getPlayerJoinedEvents(code: string): Observable<PlayerJoinedAction> {
    return this.client.watch('/topic/game/' + code)
        .pipe(map(r => JSON.parse(r.body) as Action))
        .pipe(filter(a => a.action === 'PLAYER_JOINED'))
        .pipe(map(a => a as PlayerJoinedAction));
  }
}
