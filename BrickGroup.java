package uk.ac.reading.xt016916.ballgame;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class BrickGroup extends Brick { //creates array used for a group of bricks


	private ArrayList <BrickGroup> brickGroupBitmap;
	
	
	public BrickGroup(float brickX, float brickY){		
			setBrickX(brickX);
			setBrickY(brickY);
		
	}
	
	public ArrayList <BrickGroup> getBrickGroup(){
		return this.brickGroupBitmap;
		
	}
	


}
