package de.thkoeln.glug.data;

import java.util.ArrayList;

public class Constants {
	public static enum color{Rot, Gelb, Grün, Schwarz, Blau, Magenta}
	public static enum objectShape{Dreieck, Quadrat, Kreis, Sechseck, Parallelogramm, Trapez, Fünfeck}
	public static enum questionTyp{FarbeHintergrund, FarbeObjekt, FarbeObjektrand, Text, FormObjekt}

	public static ArrayList<color> getArrayListColors(){
		ArrayList<color> colorArrayList = new ArrayList<color>();
		for (color color : color.values()) {
			colorArrayList.add(color);
		}
		return colorArrayList;
	}
	public static ArrayList<objectShape> getArrayListShape(){
		ArrayList<objectShape> shapeArrayList = new ArrayList<objectShape>();
		for (objectShape shape : objectShape.values()) {
			shapeArrayList.add(shape);
		}
		return shapeArrayList;
	}
	public static ArrayList<questionTyp> getArrayListQuestionTyp(){
		ArrayList<questionTyp> shapeArrayList = new ArrayList<questionTyp>();
		for (questionTyp questionTyp : questionTyp.values()) {
			shapeArrayList.add(questionTyp);
		}
		return shapeArrayList;
	}
}
