import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Drone main file
 * 
 * @author rebaz
 *
 */
public class Drone extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DroneMoveModel rmm;
	public Obstacle obstacle;

	private ArrayList<Color> legColors = new ArrayList<Color>();
	int colorChangeInterval = 0;
	int blinkChangeInterval = 0;
	boolean warningBlinking = false;
	Color warningColor = Color.RED;

	public Drone(DroneMoveModel rmm) {

		this.rmm = rmm;
		obstacle = new Obstacle(rmm.zm);

		legColors.add(Color.BLUE);
		legColors.add(Color.GREEN);
		legColors.add(Color.YELLOW);
		legColors.add(Color.WHITE);

		Thread animationThread = new Thread(new Runnable() {

			public void run() {
				while (true) {
					repaint();
					try {
						Thread.sleep(20);
					} catch (Exception ex) {
					}
				}
			}
		});

		animationThread.start();

	}

	public void paintComponent(Graphics g) {

		g.setColor(Color.PINK);
		g.fillRect(rmm.zm.getZoneMinX(), rmm.zm.getZoneMinY(), rmm.zm.zoneWidth, rmm.zm.zoneHeight);

		g.setColor(Color.YELLOW);
		g.fillRect(230, 175, 120, 100);

		g.setColor(Color.RED);
		g.fillRect(obstacle.minX, obstacle.minY, obstacle.width, obstacle.height);

		Graphics2D gg = (Graphics2D) g;

		if (rmm.isRobotMoving() == true && rmm.isInterruptMove() == false) {
			if (rmm.getX() < rmm.zm.getMinX() || (rmm.getX() + rmm.DRONE_W) > rmm.zm.getMaxX()) {
				rmm.setVelX();
			}

			if (rmm.getY() < rmm.zm.getMinY() || (rmm.getY() + rmm.DRONE_H) > rmm.zm.getMaxY()) {
				rmm.setVelY();
			}

			rmm.setX(rmm.getX() + rmm.getVelX());
			rmm.setY(rmm.getY() + rmm.getVelY());

			rmm.setDistanceTraveled(rmm.getDistanceTraveled() + Math.abs(rmm.getX()));
		}

		Ellipse2D leftEar = new Ellipse2D.Double(rmm.getX() + 10, rmm.getY() + 30, 10, 10);
		Ellipse2D rightEar = new Ellipse2D.Double(rmm.getX() + 10, rmm.getY(), 10, 10);

		Ellipse2D leftNose = new Ellipse2D.Double(rmm.getX(), rmm.getY() + 15, 10, 10);
		Ellipse2D rightNose = new Ellipse2D.Double(rmm.getX() + rmm.DRONE_W - 10, rmm.getY() + 15, 10, 10);

		gg.setColor(Color.BLACK);
		g.fillRect(rmm.getX(), rmm.getY(), rmm.DRONE_W, rmm.DRONE_H);
		gg.setColor(legColors.get(0));
		gg.fill(leftEar);
		gg.setColor(legColors.get(1));
		gg.fill(rightEar);
		gg.setColor(legColors.get(2));
		gg.fill(leftNose);
		gg.setColor(legColors.get(3));
		gg.fill(rightNose);

		blinkChangeInterval += 10;
		if (rmm.isInterruptMove()) {
			turnOnAlarm(gg, blinkChangeInterval);
		}

		// Check if zone is out of area
		blinkChangeInterval += 20;
		if (!rmm.zm.isDroneInZone) {
			turnOnAlarm(gg, blinkChangeInterval);
		}

		rmm.setLastLocation(rmm.getX(), rmm.getY());

		colorChangeInterval += 10;

		if (colorChangeInterval % 200 == 0) {

			Color firstColor = legColors.get(0);
			legColors.remove(0);
			legColors.add(firstColor);

			colorChangeInterval = 0;
		}

//		processesTimerCounter += 20;
//		if (processesTimerCounter >= 1000) {
//			processesTimerCounter = 0;
//			
//			if (firstProcessDeath > 0)
//				firstProcessDeath -= 1;
//			if (secondProcessDeath > 0)
//				secondProcessDeath -= 1;
//
//		}
	}

	public void turnOnAlarm(Graphics2D gg, int interval) {

		if (blinkChangeInterval % 750 == 0) {
			warningBlinking = !warningBlinking;
			warningColor = (warningBlinking) ? Color.RED : Color.BLACK;
			blinkChangeInterval = 0;
		}
		gg.setColor(warningColor);
		Ellipse2D warning = new Ellipse2D.Double(rmm.getX() + 8, rmm.getY() + 12, 15, 15);
		gg.fill(warning);
	}

	class RobotKeyHandler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT:
				rmm.setVelX(-1);
				rmm.setVelY(0);
				rmm.setRobotMoving(true);
				break;

			case KeyEvent.VK_RIGHT:
				rmm.setVelX(1);
				rmm.setVelY(0);
				rmm.setRobotMoving(true);
				break;

			case KeyEvent.VK_DOWN:
				rmm.setVelX(0);
				rmm.setVelY(1);
				rmm.setRobotMoving(true);
				break;

			case KeyEvent.VK_UP:
				rmm.setVelX(0);
				rmm.setVelY(-1);
				rmm.setRobotMoving(true);
				break;

			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// rmm.setRobotMoving(false);
		}

	}

}
