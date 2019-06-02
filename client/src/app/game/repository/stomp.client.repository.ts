import {Injectable} from '@angular/core';
import {RxStomp} from '@stomp/rx-stomp';
import * as SockJS from 'sockjs-client';

@Injectable()
export class StompClientRepository {
  private serverUrl = '/glug/';
  private stompClient: RxStomp;

  constructor() {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection() {
    this.stompClient = new RxStomp();
    this.stompClient.configure({webSocketFactory: () => new SockJS(this.serverUrl)});
    this.stompClient.activate();
  }

  public getClient(): RxStomp {
    return this.stompClient;
  }
}
