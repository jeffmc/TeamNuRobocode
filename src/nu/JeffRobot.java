package nu;

import java.awt.Color;

import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class JeffRobot extends TeamRobot {
    
	private double[] absoluteDesiredVelocity = {0, 0};
	
	private boolean running = false;
	public void run() {
    	
    	setup();
    	
    	running = true;
        while (running) {
        	determineVelocity();
        	movementLoop();
        }
    }
    
    private void setup() {
        this.setColors(Color.RED, Color.PINK, Color.BLACK, Color.GREEN, Color.BLUE);
    }
    
    private void setADV(double x, double y) {
    	absoluteDesiredVelocity[0] = x;
    	absoluteDesiredVelocity[1] = y;
    }
    private double getADVx() { return absoluteDesiredVelocity[0]; }
    private double getADVy() { return absoluteDesiredVelocity[1]; }
    
    private void determineVelocity() {
    	double cx = getBattleFieldWidth()/2, cy = getBattleFieldHeight()/2;
    	setADV(cx-getX(), cy-getY());
    }
    
    private void movementLoop() {
    	double dx = getADVx(), dy = getADVy();
    	double desiredTheta = toIntervalDeg(Math.toDegrees(Math.atan2(dx, dy)));
    	double desiredSpeed = dx*dx+dy*dy;
    	turnToHeading(desiredTheta);
    	double hdg = getHeading();
    	System.out.println("Heading: " + hdg);
    	System.out.println("dtht: " + desiredTheta);
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
    
    private void turnToHeading(double target) {
    	if (getHeading() != target) {
	    	setTurnRight(getTurnToHeading(target));
    	} else {
    		setTurnRight(0);
    	}
    }
    
    private double toIntervalDeg(double bef) { // returns 0-360 from a degree value
    	return bef < 0 ? 360 + bef : bef;
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
    	fire(Rules.MAX_BULLET_POWER);
    }
    
    private boolean dbleql(double a, double b) {
    	return Math.abs(a-b) < 0.1;
    }
}
