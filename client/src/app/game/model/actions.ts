import {Color, Shape} from './challenge';
import {Player} from './player';

export interface Action {
  action: string;
}

export interface ChallengeAnswerAction extends Action {
  challengeId: number;
  playerId: number;
  answerId: number;
}

export interface CountdownAction extends Action {
  count: string;
}

export interface NewChallengeAction extends Action {
  roundId: number;
  challengeId: number;
  answers: Array<ChallengeAnswer>;
  colorBackground: Color;
  colorObject: Color;
  colorObjectBorder: Color;
  colorText: Color;
  question: string;
  questionType: string;
  shape: Shape;
  text: Color;
}

export interface RoundFinishedAction extends Action {
  results: Array<Player>;
  roundId: number;
}

export interface AllocateSlugAction extends Action {
  roundId: number;
  fromPlayerId: number;
  toPlayerId: number;
}

export interface ChallengeAnswerAction extends Action {
  playerId: number;
  answerId: number;
  challengeId: number;
  correct: boolean;
}

export interface ChallengeAnswer {
  text: Color;
  id: number;
}

export interface PlayerCreatedAction extends Action {
  playerId: number;
  name: string;
}

export interface JoinGameAction extends Action {
  playerId: number;
}

export interface GameCreatedAction extends Action {
  accessCode: string;
  gameId: string;
}

export interface PlayerJoinedAction extends Action {
  inGamePlayers: Array<Player>;
  joinedPlayer: Player;
}
