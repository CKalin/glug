export class Challenge {
  id: number;
  color: Color;
  border: Color;
  backgroundColor: Color;
  object: Shape;
  text: string;
  textColor: Color;
  question: string;
  answers: Array<Answer>;
}

export class Answer {
  id: number;
  text: string;
}

export type Color = 'blue' | 'yellow' | 'green' | 'brown' | 'grey' | 'red' | 'orange' | 'purple';
export type Shape = 'circle' | 'triangle' | 'square' | 'pentagon' | 'hexagon';
