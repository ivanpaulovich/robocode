package robosinhos;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Robo03 - a robot by (your name here)
 */
public class Robo03 extends Robot
{
	double distancia = 0;

	public void run() 
	{
		while (true)
		{
			turnGunRight(20);
			AndarPraFrente();
		}
	}
	
	public void AndarPraFrente()
	{
		ahead(distancia);
		distancia = 0;
	}
	
	/**
	 * Baseado no código TrackFire.java 
	 * Esta função ajusta a mira do Tank em direção ao alvo
	 * e se necessário também rotaciona o Tank
	 */
	public void onScannedRobot(ScannedRobotEvent e) 
	{
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

		// If it's close enough, fire!
		if (Math.abs(bearingFromGun) <= 3) 
		{
			turnGunRight(bearingFromGun);
			// We check gun heat here, because calling fire()
			// uses a turn, which could cause us to lose track
			// of the other robot.
			if (getGunHeat() == 0) {				
				double forca = Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1);				
				atirar(forca);
			}
		} // otherwise just set the gun to turn.
		// Note:  This will have no effect until we call scan()
		else 
		{
			turnGunRight(bearingFromGun);
		}
		
		// Generates another scan event if we see a robot.
		// We only need to call this if the gun (and therefore radar)
		// are not turning.  Otherwise, scan is called automatically.
		if (bearingFromGun == 0) {
			scan();
		}
	}

	public void onHitByBullet(HitByBulletEvent e) 
	{
		//
		// Ao ser atingido o tank deve fugir
		fugirDeTiro();
	}
	
	public void onHitWall(HitWallEvent e)
	{
		//
		// Ao colidir com parede o tank deve retroceder
		desviarDeParede();
	}
	
	public void onWin(WinEvent e) 
	{
		//
		// Ao vencer o tank deve comemorar
		comemorar();
	}
	
	public void atirar(double forca)
	{
		fire(forca);
	}
	
	public void fugirDeTiro()
	{
		turnLeft(90 - e.getBearing());
		distancia = 100;
	}
	
	public void desviarDeParede()
	{
		turnRight(180);
		distancia = 100;
	}
	
	public void comemorar()
	{
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}
}
