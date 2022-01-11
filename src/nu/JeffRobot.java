package nu;

import java.awt.Color;

import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

public class JeffRobot extends TeamRobot {
    
	private boolean running = false;
	public void run() {
    	
    	setup();
    	
    	running = true;
        while (running) {
        	loop();
        }
    }
    
    private void setup() {
        this.setColors(Color.RED, Color.PINK, Color.BLACK, Color.GREEN, Color.BLUE);
    }
    
    private void loop() {
    	out.println("turning down");
    	turnToHeading(180);
    	out.println("moving down");
    	ahead(getY()-100);
    	out.println("turning left");
    	turnToHeading(270);
    	out.println("moving left");
    	ahead(getX()-100);
    	stop();
    	out.println("facing inward");
    	turnToHeading(45);
    	out.println("done");
    	stop();
    	running = false;
    }
    
    // https://math.stackexchange.com/questions/110080/shortest-way-to-achieve-target-angle/2898118
    private void setTurnToHeading(double target) {
    	double t = target % 360, c = getHeading();
    	
    	double a = t - c, b = t - c + 360, y = t - c - 360;
    	
    	double aa = Math.abs(a), ab = Math.abs(b), ay = Math.abs(y);
    	
    	if (aa <= ab && aa <= ay) { // A min
    		setTurnRight(a);
    	} else if (ab <= aa && ab <= ay) { // B min
    		setTurnRight(b);
    	} else { // Y min/equal
    		setTurnRight(y);
    	}
    }
    
    private void turnToHeading(double target) {
    	if (getHeading() != target) {
	    	setTurnToHeading(target);
	    	execute();
    	}
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
    	fire(Rules.MAX_BULLET_POWER);
    }
}
