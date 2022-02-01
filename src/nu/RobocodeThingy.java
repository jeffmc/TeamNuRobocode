package nu;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.Color;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * RobocodeThingy - an extremely scuffed robot by Uno 
 * Beats everything I tested it against in terms of leaderboard performance except the walls bot (in which case it goes back and forth) 
 * Comes out on top of the leaderboard most of the time when put in a free for all against all sample robots (even with walls) 
 * This doesn't count testing against a team of robots 
 * Absolutely destroys ramfire every time 
 * Might fix before the close date if it's still before the tournament and I have time 
 * Last edited: 1/30/2022 
 */
public class RobocodeThingy extends TeamRobot
{
	//Map<String, Double> targetMap = new HashMap<String, Double>(); // Map of targets and their distances
	// Make an int called dist and set it to 50 
	int dist = 50;
	public int ticksSinceScannedBot = 1000000;
	/**
	 * run: RobocodeThingy's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// Set colors of robot to match with team 
        setColors(Color.RED, Color.PINK, Color.BLACK, Color.GREEN, Color.BLUE);
		// Set scan color to orange
		setScanColor(Color.orange);

		// Make sure that the gun doesn't turn with the robot 
		setAdjustGunForRobotTurn(true);

		// Robot main loop
		while(true) {
			// Sweep 
			if (ticksSinceScannedBot > 2) {
				setTurnGunRight(15);
			}
			else {
				// Make gun not turn
				setTurnGunRight(0);
			}
			// Spam right really hard 
			setTurnRight(69420);
			// Set max velocity to 6 to we don't break the sound barrier 
			setMaxVelocity(6);
			// Wheeeee 
			setAhead(69420);
			execute();
			// Increment ticks since scanned bot
			ticksSinceScannedBot++;
		}
	} 

	/**
	 * onScannedRobot: Pew pew pew 
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Spam right really hard 
		setTurnRight(69420);
		// Set ahead 69420
		setAhead(69420);
		// Don't sort by distance, just yeet projectiles at whatever we see 
		if (!isTeammate(e.getName())) { // Check if the target is actually an enemy 
			// Refresh ticks since scanned bot
			ticksSinceScannedBot = 0;
			// Calculate the angle to the scanned robot 
			double angle = e.getBearing() + getHeading();
			// No movement prediction because we don't know the actual gun position so it would be inaccurate 
			// Set the gun to turn to the angle to the scanned robot 
			setTurnGunRight(normalRelativeAngleDegrees(angle - getGunHeading()));
			if (getGunHeat() == 0) {
				// If the other robot is close by enough and our health is decently high, hit them really hard 
				if (e.getDistance() < 120 && getEnergy() > 10) { // Big boom 
					fire(3);
				}
				else if (e.getDistance() < 250 && getEnergy() > 30) { // Slightly less big boom 
					fire(2);
				} 
				else { // Fire at lower power because it might not actually hit 
					// Turn our entire robot to the target
					setTurnRight(normalRelativeAngleDegrees(angle - getHeading()));
					fire(1);
				}
				// Compensate for weird gun thingy 
				// This looks weird but it makes sure that we don't lose track of our target 
				turnGunLeft(15); 
			}
			// Call scan again 
			scan();
		}
	}
	/**
	 * onHitWall: We got unlucky and spawned next to a wall so now we have to turn away from it 
	 */
	public void onHitWall(HitWallEvent e) {
		// Get the coordinates of our robot
		double x = getX();
		double y = getY();
		// Get the coordinates of the middle of the field
		double midX = getBattleFieldWidth() / 2;
		double midY = getBattleFieldHeight() / 2;
		// Calculate the heading from our robot to the middle of the field
		double heading = Math.atan2(midX - x, midY - y);
		// Set the robot to turn to the heading
		setTurnRight(normalRelativeAngleDegrees(Math.toDegrees(heading) - getHeading()));
		setAhead(150);
	}	

	/**
	 * onHitRobot: Aim and make the cannon go boom really hard since the robot is like right next to us 
	 */
	public void onHitRobot(HitRobotEvent e) {
		if (!isTeammate(e.getName())) {
			if (e.getBearing() > -45 && e.getBearing() < 45) {
				double angle = e.getBearing() + getHeading();
				setTurnGunRight(normalRelativeAngleDegrees(angle - getGunHeading()));
				if (getGunHeat() == 0) fire(3); 
				scan();
			}
		}
	}
}
