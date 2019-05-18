export class Challenge {
  color: Color;
  border: Color;
  object: 'circle' | 'triangle' | 'square' | 'pentagon' | 'hexagon';
  text: string;
  textColor: Color;
  question: string;
}
export type Color = 'blue' | 'yellow' | 'green' | 'brown' | 'grey' | 'red' | 'orange' | 'purple';
