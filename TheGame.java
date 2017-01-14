package uk.ac.reading.xt016916.ballgame;

//Other parts of the android libraries that we use
import uk.ac.reading.xt016916.ballgame.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.SensorManager;
import android.os.SystemClock;


public class TheGame extends GameThread{

	public int getCanvasHeight(){
		return this.mCanvasHeight;
	}

	public int getCanvasWidth(){
		return this.mCanvasWidth;
	}


	Paddle paddle = new Paddle();
	Bitmap mainPaddle = paddle.getPaddleBit();

	Bitmap [] brick = new Bitmap[6];

	ArrayList <Brick> brickGroup = new ArrayList<Brick>();

	boolean isValid = true;
	private float [] brickX;

	private float paddleX;
	private float paddleXspeed;
	private float paddleY;
	private float paddleYspeed;

	//Will store the image of a ball
	private Bitmap[] mBall = new Bitmap[3];


	//The X and Y position of the ball on the screen (middle of ball)
	private float [] mBallX = new float[3];
	private float [] mBallY= new float[3];

	//The speed (pixel/second) of the ball in direction X and Y
	private float [] mBallSpeedX = new float[3];
	private float [] mBallSpeedY = new float[3];


	//This is run before anything else, so we can prepare things here
	public TheGame(GameView gameView) {
		//House keeping
		super(gameView);

		/*
		for(int i =0;i<brickGroup.size();i++){
			brickGroup.add(new Brick());

		}
		 */



		brickGroup.add(new Brick());
		brickGroup.add(new Brick());
		brickGroup.add(new Brick());
		brickGroup.add(new Brick());
		brickGroup.add(new Brick());
		brickGroup.add(new Brick());




		for(int i =0;i<brickGroup.size();i++){
			brick[i] = brickGroup.get(i).getBrickBit();
		}
		
		
		

		//int p = brick[0].getWidth();

		for(int i =0;i<brickGroup.size();i++){
			brickGroup.get(i).setBrickX(i*80);
		}



		//Prepare the image so we can draw it on the screen (using a canvas)
		for(int i = 0; i<3; i++){
			mBall[i] = BitmapFactory.decodeResource
					(gameView.getContext().getResources(), 
							R.drawable.small_red_ball);
		}


		for(int i = 0;i<brickGroup.size();i++){
			brick[i] = BitmapFactory.decodeResource
					(gameView.getContext().getResources(), 
							R.drawable.blocksmall);
		}

		mainPaddle = BitmapFactory.decodeResource
				(gameView.getContext().getResources(), 
						R.drawable.block1);



	}

	//This is run before a new game (also after an old game)
	@Override
	public void setupBeginning() {
		//Initialise speeds

		for(int i=0;i<3;i++){

			mBallSpeedX[i] = 200+(i*70); 
			mBallSpeedY[i] = 200+(i*70);
			paddleXspeed = 0;

		}


		//Place the ball in the middle of the screen.
		//mBall.Width() and mBall.getHeight() gives us the height and width of the image of the ball

		paddle.setBrickX((mCanvasWidth/2)-(mainPaddle.getWidth()/2));
		paddle.setBrickY((mCanvasHeight)-(mCanvasHeight/5));

		mBallX[0] = mCanvasWidth / 2;
		mBallY[0] = mCanvasHeight / 2;
		mBallX[1] = mCanvasWidth / 2;
		mBallY[1] = mCanvasHeight / 4;
		mBallX[2] = mCanvasWidth / 4;
		mBallY[2] = mCanvasHeight / 2;
	}

	@Override
	protected void doDraw(Canvas canvas) {
		//If there isn't a canvas to draw on do nothing
		//It is ok not understanding what is happening here
		if(canvas == null) return;

		super.doDraw(canvas);


		//draw the image of the ball using the X and Y of the ball
		//drawBitmap uses top left corner as reference, we use middle of picture
		//null means that we will use the image without any extra features (called Paint)
		for(int i = 0;i<3; i++){
			canvas.drawBitmap(mBall[i], mBallX[i] - mBall[i].getWidth() / 2, mBallY[i] - mBall[i].getHeight() / 2, null);



		}

		
		
		
		for(int i = 0;i<brickGroup.size(); i++){
			canvas.drawBitmap(brick[i],  mCanvasWidth/8 + brickGroup.get(i).getBrickX(), 20, null);		
		}



		canvas.drawBitmap(mainPaddle, paddle.getBrickX(), paddle.getBrickY(), null);


	}

	//This is run whenever the phone is touched by the user

	@Override
	protected void actionOnTouch(float x, float y) {
		//Increase/decrease the speed of the ball making the ball move towards the touch

		paddleXspeed = x - paddle.getBrickX();
		
		//mBallSpeedX = x - mBallX;
		//mBallSpeedY = y - mBallY;
	}


	/*
	//This is run whenever the phone moves around its axises 
	@Override
	protected void actionWhenPhoneMoved(float xDirection, float yDirection, float zDirection) {
		//Increase/decrease the speed of the ball

		if(paddle.getBrickX() >= 0 && paddle.getBrickX() <= mCanvasWidth) {
			//Change the X value according to the x direction of the phone
			paddle.setBrickX(paddle.getBrickX()-xDirection);

			//after movement ensure it is still on the screen
			if(paddle.getBrickX() < 0) paddle.setBrickX(0);
			if(paddle.getBrickX() > mCanvasWidth) paddle.setBrickX (mCanvasWidth);			
		}

		//paddle.setBrickX(paddle.getBrickX() - 1.5f * xDirection);
		//paddle.setBrickY(paddle.getBrickY() - 1.5f * yDirection);
		//mBallSpeedX = mBallSpeedX - 1.5f * xDirection;
		//mBallSpeedY = mBallSpeedY - 1.5f * yDirection;
	}
	*/


	//This is run just before the game "scenario" is printed on the screen
	@Override
	protected void updateGame(float secondsElapsed) {
		//Move the ball's X and Y using the speed (pixel/sec)


		for(int i = 0;i<3;i++){

			mBallX[i] = mBallX[i] + secondsElapsed * mBallSpeedX[i];
			mBallY[i] = mBallY[i] + secondsElapsed * mBallSpeedY[i];
			//paddleX = paddleX + secondsElapsed * paddleXspeed;
			
			
			paddle.setBrickX(paddle.getBrickX() + secondsElapsed * paddleXspeed);
			
			if(paddle.getBrickX() <0-180)
				paddle.setBrickX(-180);
			
			if(paddle.getBrickX()>mCanvasWidth-40)
				paddle.setBrickX(mCanvasWidth-40);
			
			
			
			

			//paddleY = paddleY + secondsElapsed * paddleYspeed;

			if (mBallSpeedX[i] >= 100 && mBallSpeedY[i] >= 100){//top left to bottom right


				if(mBallX[i] > mCanvasWidth){
					mBallSpeedX[i] = -(mBallSpeedX[i]);

				}


				if(mBallY[i] > paddle.getBrickY()&& mBallY[i] < (paddle.getBrickY()+5) && mBallX[i] > paddle.getBrickX() && mBallX[i] < paddle.getBrickX()+mainPaddle.getWidth()){
					mBallSpeedY[i] = -(mBallSpeedY[i]);
				}


				if(mBallY[i] > mCanvasHeight){//runs off screen
					//mBallSpeedY[i] = -(mBallSpeedY[i]);
				}





				for(int j=0;j<brickGroup.size();j++){
					if((mBallX[i] >= brickGroup.get(j).getBrickX() + brick[j].getWidth()&& mBallX[i] <= brickGroup.get(j).getBrickX()+brick[j].getWidth()*2) 
							&& mBallY[i] >= brickGroup.get(j).getBrickY() && mBallY[i] <= brickGroup.get(j).getBrickY()+brick[j].getHeight()){
						mBallSpeedX[i] = -(mBallSpeedX[i]);
						brickGroup.get(j).setBrickX(-200);
						updateScore(5);
					}

				}




				/*for(int j=0;j<brickGroup.size();j++){
					if(collisionCheck(brickGroup.get(j),mBallX[i],mBallY[i]) == true){
						mBallSpeedX[i] = -(mBallSpeedX[i]);
						brickGroup.get(j).setBrickX(-200);
					}	
				}*/

			}

			if(mBallSpeedX[i] <= -100 && mBallSpeedY[i] >= 100){//top right to bottom left
				if(mBallY[i] > mCanvasHeight){
					//mBallSpeedY[i] = -(mBallSpeedY[i]); //ball runs off screen
				}
				if(mBallX[i] <=0){
					mBallSpeedX[i] = -(mBallSpeedX[i]);
				}

				if(mBallY[i] > paddle.getBrickY() && mBallY[i] < (paddle.getBrickY()+5) && mBallX[i] > paddle.getBrickX() && mBallX[i] < paddle.getBrickX()+mainPaddle.getWidth()){
					mBallSpeedY[i] = -(mBallSpeedY[i]);
				}

				for(int j=0;j<brickGroup.size();j++){
					if((mBallX[i] >= brickGroup.get(j).getBrickX()+ brick[j].getWidth() && mBallX[i] <= brickGroup.get(j).getBrickX()+brick[j].getWidth()*2) 
							&& mBallY[i] >= brickGroup.get(j).getBrickY() && mBallY[i] <= brickGroup.get(j).getBrickY()+brick[j].getHeight()){
						mBallSpeedY[i] = -(mBallSpeedY[i]);
						brickGroup.get(j).setBrickX(-200);
						updateScore(5);
					}

				}
				/*
				for(int j=0;j<brickGroup.size();j++){
					if(collisionCheck(brickGroup.get(j),mBallX[i],mBallY[i]) == true){
						mBallSpeedY[i] = -(mBallSpeedY[i]);
						brickGroup.get(j).setBrickX(-200);
					}	
				}*/


			}

			if(mBallSpeedX[i] <= -100 && mBallSpeedY[i] <= -100){//bottom right to top left
				if(mBallX[i] <= 0){ //wall
					mBallSpeedX[i] = -(mBallSpeedX[i]);
				}
				if(mBallY[i] <=0){ //roof
					mBallSpeedY[i] = -(mBallSpeedY[i]);
				}

				for(int j=0;j<brickGroup.size();j++){
					if((mBallX[i] >= brickGroup.get(j).getBrickX() + brick[j].getWidth()&& mBallX[i] <= brickGroup.get(j).getBrickX()+brick[j].getWidth()*2) 
							&& mBallY[i] >= brickGroup.get(j).getBrickY() && mBallY[i] <= brickGroup.get(j).getBrickY()+brick[j].getHeight()){
						mBallSpeedY[i] = -(mBallSpeedY[i]);
						brickGroup.get(j).setBrickX(-200);
						updateScore(5);
					}

				}



				/*
				for(int j=0;j<brickGroup.size();j++){
					if(collisionCheck(brickGroup.get(j),mBallX[i],mBallY[i]) == true){
						mBallSpeedY[i] = -(mBallSpeedY[i]);
						brickGroup.get(j).setBrickX(-200);
					}	
				}
				 */


				// create two seperate arrays with 3 blocks in each
				// run through each array and check if any of the balls Y coordinates' 
				// will collide with the area the block is in. E.g. top left coordiante + block width 
				// to include the whole area under the array && balls X coordinate is between the
				// blocks top left coordinate and top lefts coordinate + the block length	
			}

			if(mBallSpeedX[i] >= 100 && mBallSpeedY[i] <= -100){//bottom left to top right
				if(mBallY[i] <= 0){
					mBallSpeedY[i] = -(mBallSpeedY[i]);
				}
				if(mBallX[i] > mCanvasWidth){
					mBallSpeedX[i] = -(mBallSpeedX[i]);
				}

				for(int j=0;j<brickGroup.size();j++){
					if((mBallX[i] >= brickGroup.get(j).getBrickX() + brick[j].getWidth()&& mBallX[i] <= brickGroup.get(j).getBrickX()+brick[j].getWidth()*2) 
							&& mBallY[i] >= brickGroup.get(j).getBrickY() && mBallY[i] <= brickGroup.get(j).getBrickY()+brick[j].getHeight()){
						mBallSpeedY[i] = -(mBallSpeedY[i]);
						brickGroup.get(j).setBrickX(-200);
						updateScore(5);
					}


					/*
					if(collisionCheck(brickGroup.get(j),mBallX[i],mBallY[i]) == true){
						mBallSpeedX[i] = -(mBallSpeedX[i]);
						brickGroup.get(j).setBrickX(-200);
					}	*/
				}

			}

		}
		
		
		if(mBallY[0] < 5 && mBallY[1] < 5 && mBallY[2] < 5){
			setState(STATE_LOSE);
		}
		
		
			if( brickGroup.get(0).getBrickX() == -200 && brickGroup.get(1).getBrickX() == -200 && brickGroup.get(2).getBrickX() == -200&& brickGroup.get(3).getBrickX() == -200
					&& brickGroup.get(4).getBrickX() == -200&& brickGroup.get(5).getBrickX() == -200){
				while(getScore()<50){
				updateScore(1);
				}
				setState(STATE_WIN);
			}
			
		
	}

	private void printf(String string) {
		// TODO Auto-generated method stub

	}

	boolean hitCheck(float x, float y, Brick brick) {
		return (x >= brick.getBrickX() && x <= brick.getBrickX()+40) &&
				(y >= brick.getBrickY() && y <= brick.getBrickY()+23);
	}

	boolean collisionCheck(Brick brick, float mBallX, float mBallY) {
		return hitCheck(mBallX+7, mBallY+7, brick) ||
				hitCheck(mBallX+7, mBallY, brick) ||
				hitCheck(mBallX, mBallY+7, brick) ||
				hitCheck(mBallX, mBallY, brick);
	}

}


// This file is part of the course "Begin Programming: Build your first mobile game" from futurelearn.com
// Copyright: University of Reading and Karsten Lundqvist
// It is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// It is is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// 
// You should have received a copy of the GNU General Public License
// along with it.  If not, see <http://www.gnu.org/licenses/>.