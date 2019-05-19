import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {RxStomp} from '@stomp/rx-stomp';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {filter, map, tap} from 'rxjs/operators';
import * as SockJS from 'sockjs-client';
import {Challenge} from './challenge';
import {Message} from './message';
import {Player} from './player';

@Injectable()
export class GameService {
  private players: Subject<Array<Player>> = new BehaviorSubject([]);

  private _player: Player;
  private player: Subject<Player> = new BehaviorSubject(null);

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

  private code: Subject<string> = new BehaviorSubject('');
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
  private serverUrl = '/glug/';
  private stompClient: RxStomp;

  constructor(private router: Router, private http: HttpClient) {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection() {
    this.stompClient = new RxStomp();
    this.stompClient.configure({webSocketFactory: () => new SockJS(this.serverUrl)});
    this.stompClient.activate();
  }

  sendMessage() {
    this.stompClient.publish({destination: '/app/game', body: 'Hallo'});
  }

  createGame(playerName: string) {
    this.register(playerName).subscribe(player => {
      const p = new HttpParams().append('playerId', player.id);
      this.http.get<string>('/api/game/create', {params: p})
          .subscribe(id => {
            this.setCode(id);
          });
    });
    this.router.navigateByUrl('lobby/create');
  }

  setCode(code: string) {
    this.code.next(code);
    this.connectToGame(code);
  }

  validateAccessCode(code: string): Observable<boolean> {
    const p = new HttpParams().append('accessCode', code);
    return this.http.get<boolean>('/api/game/isPresent', {params: p});
  }

  register(playerName: string): Observable<Player> {
    const p = new HttpParams().append('name', playerName);
    return this.http.get<Player>('/api/player/create', {params: p}).pipe(map(player => {
      this._player = player;
      this.player.next(player);
      return player;
    }));
  }

  private connectToGame(code: string) {
    this.stompClient.watch('/topic/game/' + code + '/players')
        .subscribe(r => this.players.next(this.processPlayers(JSON.parse(r.body) as Array<Player>)));
    this.stompClient.publish({destination: '/app/game/' + code + '/player', body: this._player.id});
  }

  private processPlayers(players: Array<Player>) {
    return players.map(p => {
      if (p.id === this._player.id) {
        return Object.assign({}, p, {you: true});
      }
      return p;
    });
  }

  getParticipants(): Observable<Array<Player>> {
    return this.players.asObservable();
  }

  getYourself(): Observable<Player> {
    return this.players.asObservable()
        .pipe(map(list => list.filter(p => p.you)[0]))
        .pipe(tap(p => this._player = p));
  }

  getCode(): Observable<string> {
    return this.code.asObservable()
        .pipe(filter(code => code !== ''));
  }

  getChallengeUpdates(): Observable<Challenge | Message> {
    return this.challengeUpdates.asObservable();
  }

  joinGame(name: string, gameId: any) {
    this.register(name).subscribe(
        () => this.setCode(gameId)
    );
    this.router.navigateByUrl('lobby/join');
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








