package nu;

import java.awt.Color;

import robocode.BattleEndedEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

// Robot by Jeff, part of Team Nu
// Currently seeks out the center of the map and shoots enemies when scanned. TODO: Implement independent scanning code.
public class JeffRobot extends TeamRobot {
    
	private double[] absoluteDesiredVelocity = {0, 0}; // Desired velocity variable
	
	private boolean running = false; 
	public void run() { // Run loop
    	
    	setup(); // Only run once
    	
    	running = true;
        while (running) {
        	determineVelocity();
        	movementLoop();
        }
    }
    
	@Override
	public void onBattleEnded(BattleEndedEvent event) {
		running = false;
		stop();
		turnRight(60000);
	}
	
    private void setup() {
        this.setColors(Color.RED, Color.PINK, Color.BLACK, Color.GREEN, Color.BLUE); // Team colors
    }
    
    private void setADV(double x, double y) {
    	absoluteDesiredVelocity[0] = x;
    	absoluteDesiredVelocity[1] = y;
    }
    private double getADVx() { return absoluteDesiredVelocity[0]; }
    private double getADVy() { return absoluteDesiredVelocity[1]; }
    
    private void determineVelocity() { // Find the desired velocity TODO: Find more complex velocities to move toward, not just centermap
    	double cx = getBattleFieldWidth()/2, cy = getBattleFieldHeight()/2;
    	setADV(cx-getX(), cy-getY());
    }
    
    private void movementLoop() { // Move towards desired velocity
    	double dx = getADVx(), dy = getADVy();
    	double desiredTheta = toIntervalDeg(Math.toDegrees(Math.atan2(dx, dy)));
//    	double desiredSpeed = dx*dx+dy*dy;
    	turnToHeading(desiredTheta);
    	double hdg = getHeading();
//    	System.out.println("Heading: " + hdg);
//    	System.out.println("dtht: " + desiredTheta);
    	if (dbleql(hdg,desiredTheta)) {
    		this.setAhead(100);
    	} else {
    		this.setAhead(0);
    	}
    	execute();
    }
    
    // https://math.stackexchange.com/questions/110080/shortest-way-to-achieve-target-angle/2898118
    private double getTurnToHeading(double target) {
    	double t = target % 360, c = getHeading();
    	
    	double a = t - c, b = t - c + 360, y = t - c - 360;
    	
    	double aa = Math.abs(a), ab = Math.abs(b), ay = Math.abs(y);
    	
    	if (aa <= ab && aa <= ay) { // A min
    		return a;
    	} else if (ab <= aa && ab <= ay) { // B min
    		return b;
    	} else { // Y min/equal
    		return y;
    	}
    }
    
    private void turnToHeading(double target) { // Turn the robot to this heading
    	if (getHeading() != target) {
	    	setTurnRight(getTurnToHeading(target));
    	} else {
    		setTurnRight(0);
    	}
    }
    
    private double toIntervalDeg(double bef) { // returns 0-360 from a degree value
    	return bef < 0 ? 360 + bef : bef;
    }
    
    public void onScannedRobot(ScannedRobotEvent e) { // Fire when enemy seen
		if (!isTeammate(e.getName())) fire(Rules.MAX_BULLET_POWER);
    }
    
 // Are these near equal
//    , I think I remember seeing this code on the Robocode physics site, but I cannot find it in library so I implement myself
    private boolean dbleql(double a, double b) { 
    	return Math.abs(a-b) < 0.1;
    }
}
