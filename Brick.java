package uk.ac.reading.xt016916.ballgame;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class Brick { //properties for a basic brick

	private Bitmap brickBit;
	private float brickX;
	private float brickY;
	
	
	public Brick(float x, float y){
		setBrickX(x);
		setBrickY(y);
		setBrickBit(brickBit);
	}
	
	public Brick(){
		setBrickX(-200);
		setBrickBit(brickBit);
	}
	
	
	public float getBrickX(){
			return this.brickX;
	}
	
	public float getBrickY(){
		return this.brickY;
	}
	
	public Bitmap getBrickBit(){
		return this.brickBit;
	}
	
	
	public void setBrickBit(Bitmap brickBit){
		this.brickBit = brickBit;
	}
	
	/*public void setBrickBit(Bitmap[] brickGroupBitmap){
		this.brickBitArray = brickGroupBitmap;
	}*/

	public void setBrickX(float brickX){
		this.brickX = brickX;
	}
	
	public void setBrickY(float brickY){
		this.brickY = brickY;
	}

	
}
