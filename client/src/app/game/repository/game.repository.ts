import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {RxStomp} from '@stomp/rx-stomp';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Action, AllocateSlugAction, ChallengeAnswerAction, ConfirmSlugAction, GameCreatedAction, JoinGameAction} from '../model/actions';
import {StompClientRepository} from './stomp.client.repository';

@Injectable()
export class GameRepository {
  private client: RxStomp;

  constructor(private http: HttpClient, private clientRepository: StompClientRepository) {
    this.client = clientRepository.getClient();
  }

  public createGame(creatorPlayerId: number): Observable<string> {
    const p = new HttpParams().append('playerId', creatorPlayerId.toString());
    return this.http.get<GameCreatedAction>('/api/game/create', {params: p})
        .pipe(map(a => a.accessCode));
  }

  public validateAccessCode(code: string) {
    const p = new HttpParams().append('accessCode', code);
    return this.http.get<boolean>('/api/game/isPresent', {params: p});
  }

  public watchGame(code: string): Observable<Action> {
    return this.client.watch('/topic/game/' + code)
        .pipe(map(r => JSON.parse(r.body) as Action));
  }

  public joinGame(code: string, playerId: number) {
    const payload: JoinGameAction = {
      action: 'JOIN_GAME',
      playerId
    };
    this.client.publish({destination: '/app/game/' + code + '/player', body: JSON.stringify(payload)});
  }

  public startNewRound(code: string) {
    this.client.publish({destination: '/app/game/' + code + '/start'});
  }

  public sendAnswer(code: string, challengeId: number, answerId: number, playerId: number) {
    const answer = {answerId, playerId, challengeId} as ChallengeAnswerAction;
    this.client.publish({destination: '/app/game/' + code + '/answer', body: JSON.stringify(answer)});
  }

  addGlug(code: string, roundId: number, fromPlayerId: number, toPlayerId: number) {
    const answer = {roundId, fromPlayerId, toPlayerId} as AllocateSlugAction;
    this.client.publish({destination: '/app/game/' + code + '/allocateSlug', body: JSON.stringify(answer)});
  }

  acknowledgeGlugs(code: string, roundId: number, playerId: number) {
    const action = {roundId, playerId,  action: 'CONFIRM_SLUGS'} as ConfirmSlugAction;
    this.client.publish({destination: '/app/game/' + code + '/confirmSlugs', body: JSON.stringify(action)});
  }
}
