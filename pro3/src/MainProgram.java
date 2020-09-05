//INSTRUCTIONS
//USE THE "W" and "S" keys to navigate the spaceship through the field of obstacles
//Main class made by Caelan Barut
import java.awt.Color;
import java.util.Random;

public class MainProgram {
	static final int Max_Asteroids = 1;
	static final int MAX_ROCK = 1;
	final static int RUN_STATE = 0;
	static final int Max_ScreenX = 1000;// This is the screen size x
	static final int Max_ScreenY = 780;// This is the screen size y
	static final int Collides = 1;
	final static int LOSE_STATE = 2;
	static final int MAX_FUEL = 10;

	public static void main(String[] args) {

		// Setup EZ graphics system.
		EZ.initialize(Max_ScreenX, Max_ScreenY); // PIXEL picture element
		int asterod = RUN_STATE;
		Random rand = new Random();
		//create time variable with start time
		long startTime;
		startTime = System.currentTimeMillis();		
		// Create sky images
		EZImage sky1 = EZ.addImage("OuterSpace.png", 500, 255);
		EZImage sky2 = EZ.addImage("OuterSpace.png", 1500, 255);
		// scale the sky
		sky1.scaleBy(2.0);
		sky2.scaleBy(2.0);
		// add spaceship
		Spaceship ship = new Spaceship("ship.png", 100, 270); // Braden
		
		
		//CREATE ALL OBSTACLES BELOW
		// create new obstacles based on "MAX_ASTERIODS"
		obstacles[] asteriod = new obstacles[Max_Asteroids];
		for (int i = 0; i < Max_Asteroids; i++) {
			asteriod[i] = new obstacles("obstacle_asteroid.png", Max_ScreenX, Max_ScreenY);// position the asteroid and

		} // Create the second obstacles based on MAX_ROCK
		Obstacle2[] rocks = new Obstacle2[MAX_ROCK];
		for (int g = 0; g < MAX_ROCK; g++) {
			rocks[g] = new Obstacle2("rock.png", Max_ScreenX, Max_ScreenY);
		} // create the third obstacle
		obstical3[] moreRocks = new obstical3[MAX_ROCK];
		for (int y = 0; y < MAX_ROCK; y++) {
			moreRocks[y] = new obstical3("obstacle_asteroid.png", Max_ScreenX, Max_ScreenY);
		}
		// Create the amount of fuel
		Fuel[] gas = new Fuel[MAX_FUEL];
		for (int i = 0; i < MAX_FUEL; i++) {
			gas[i] = new Fuel("gold.png", Max_ScreenX, Max_ScreenY);
		}
		EZSound boom = EZ.addSound("explode.wav");
		EZSound music = EZ.addSound("music.wav");
		music.play();
		Random randomGenerator = new Random();
		float a = 1f;
		int b = 0;// counter for the time.
		int Health = 20;// Starting Health
		int points = 0;// Starting amount for the points... collect object to get points

		// While game is in Run_STATE, loop until game is in LOSE_STATE
		while (asterod == RUN_STATE) {
			ship.play();// Braden
			long currentTime = System.currentTimeMillis();
			if (currentTime - startTime >= 1000) {
				startTime = System.currentTimeMillis();
				a += 0.1f;// Every second increase size by this amount
				b++;// Increase by one very second, This will be the timer counter in Int
				Health -= 0.05 * b;// Lose this much health every second

				System.out.println(b + " " + "sec");// This will be the timer, replace this with the font TEXT then
													// delete this
				System.out.println(Health + " points");
			}
			int c = b;
			// Marcos
			if (c > 13) {// This code is for scaling the speed with time. Each second make object move
							// faster
				c = 12;// Set a limit so the object don't move too fast
			}

			if (a > 6) {// Limit the size the obstacle can grow
				a = 5;
			}

			// This is the codes to make asteroid move, scale asteroid with time and
			// collision
			for (int j = 0; j < Max_Asteroids; j++) {
				asteriod[j].scaleTo(0.3 * a);// Make the size of asteroid smaller

				asteriod[j].move(1 + c);// Get asteroids to move with a certain speed
				asteriod[j].rotateBy(-1);// Rotate the asteroid
				int s = 0;// counter for the future step, for s
				// Checks if the ship collides with asteroid
				if ((asteriod[j].isInside(ship.getX() - s, ship.getY() - s)))
						 {
					asterod = LOSE_STATE;// Change it to lose state, ends game and will do
					// something
					System.out.println("HIT");// REMOVE THIS LATER, THIS JUST CHECKS IF ITS HITTING ASTEROIDS

				}
			}

			// This is the code to make the second obstacle move, scaled the size, and
			// collision
			for (int j = 0; j < MAX_ROCK; j++) {
				rocks[j].scaleTo(0.003);// Make the size of asteroid smaller
				rocks[j].move(2);// Get asteroids to move
				moreRocks[j].move(2 + c);
				int s = 20;// counter for the future step, for s
				// Checks if the ship collides with asteroid
				if ((rocks[j].isInside(ship.getX() - s, ship.getY() - s)))
						 {
					asterod = LOSE_STATE;// Change it to lose state, ends game and will do
					// something
					System.out.println("HIT");// REMOVE THIS LATER, THIS JUST CHECKS IF ITS HITTING ASTEROIDS

				}
				int y = 0;
				if ((moreRocks[j].isInside(ship.getX() - y, ship.getY() - y)))
						 {
					asterod = LOSE_STATE;// Change it to lose state, ends game and will do
					// something
					System.out.println("HIT");// REMOVE THIS LATER, THIS JUST CHECKS IF ITS HITTING ASTEROIDS

				}
			}

			// The is the code to make the asteroid move , scale image to a smaller size and
			// collision code
			for (int g = 0; g < MAX_FUEL; g++) {
				gas[g].move(1 + c);// Make the fuel image move to the left
				gas[g].scaleTo(0.2);// Scale image to 0.2
				int s = 30;// Counter for the future steps
				if ((gas[g].isInside(ship.getX() - s, ship.getY() - s))
						|| (gas[g].isInside(ship.getX() + s, ship.getY() - s))
						|| (gas[g].isInside(ship.getX() - s, ship.getY() + s))
						|| (gas[g].isInside(ship.getX() + s, ship.getY() + s))) {
					Health += 1;// Add 1 Health every time collides with fuel
					points += 1000;// Increase the point by this amount
					gas[g].setPosition(0, 0);// Moves the fuel image of screen if collides with ship

					System.out.println(points);

				}
				if (Health > 21) {// This sets a limit so that the Health does not go beyond 20
					Health = 20;// Set health to 20 if Health is greaterthan 21
				}

			}
			
			// If the player loses do the follwoing......
			if (asterod == LOSE_STATE) {
				
				music.stop();
				boom.play();
				EZ.addImage("boom.png", ship.getX(), ship.getY());
				Color u = new Color(250, 10, 10);
				EZText text = EZ.addText(512, 300, "You Lose", u, 100);

				text.setFont("American Captain.TTF");
				// player loses
			}

			
			// animate the sky
			sky1.moveForward(-6.5);
			sky2.moveForward(-6.5);
		

			if (sky1.getXCenter() < -500) {
				sky1.moveForward(2000);
			}
			if (sky2.getXCenter() < -500) {
				sky2.moveForward(2000);
			}
			
			EZ.refreshScreen();
		}
	}
}
