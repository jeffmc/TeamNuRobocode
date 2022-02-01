package nu;
import static robocode.util.Utils.normalRelativeAngleDegrees;
// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

/**
 * AaronBot - a robot by Aaron
 */
public class AaronBot extends TeamRobot
{
	boolean movingForward = false;
	int turnDirection = 1;
	int dist = 50;
	/*
	* run: AaronBot's default behavior
	*/
	public void run() {
		// Initialization of the robot should be put here
		
		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

        setColors(Color.RED, Color.PINK, Color.BLACK, Color.GREEN, Color.BLUE);
		
		// Robot main loop
		while (true) {
			turnRight(5 * turnDirection);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		// If the other robot is close by, and we have plenty of life,
		// fire hard!
		if (isTeammate(e.getName())){
			return;
		} else {
			if (e.getDistance() < 50 && getEnergy() > 50) {
				fire(9);
			} // otherwise, fire 1.
			else {
				fire(7);
			}
			// Call scan again, before we turn the gun
			scan();
		}
	}
	
	
	
	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() >= 0) {
		turnDirection = 1;
		} else {
		turnDirection = -1;
		}
		turnRight(e.getBearing());
		
		// Determine a shot that won't kill the robot...
		// We want to ram him instead for bonus points
		if (e.getEnergy() > 16) {
			fire(9);
		} else if (e.getEnergy() > 10) {
			fire(6);
		} else if (e.getEnergy() > 4) {
			fire(5);
		} else if (e.getEnergy() > 2) {
			fire(4);
		} else if (e.getEnergy() > .4) {
			fire(3);
		}
		ahead(40); // Ram him again!
	}
	
	
	
	
	/**
	* reverseDirection:  Switch from ahead to back &amp; vice versa
	*/
	public void reverseDirection() {
		if (movingForward) {
		setBack(40000);
		movingForward = false;
		} else {
		setAhead(40000);
		movingForward = true;
		}
	}
	
	/**
	* onScannedRobot: What to do when you see another robot
	*/
	
	/**
	* onHitByBullet: What to do when you're hit by a bullet
	*/
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
		
		ahead(dist);
		dist *= -1;
		scan();
	}

	/**
	* onHitWall: What to do when you hit a wall
	*/
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		reverseDirection();
	}
}