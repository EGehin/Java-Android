package uk.ac.reading.xt016916.ballgame;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;


public class Paddle extends Brick {
		
	
	private Bitmap paddleBit;

	public Paddle(){
		setBrickX(-200);
		setBrickY(-200);
		setBrickBit(paddleBit);
	}

	

	public Bitmap getPaddleBit(){
		return this.paddleBit;
	}
	
}
