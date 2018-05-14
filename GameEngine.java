

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<ItemspeedDown>  ItemDown = new ArrayList<ItemspeedDown>();	
	private SpaceShip1 v1;
	private SpaceShip2 v2;	
	
	private Timer timer;
	private int count = 1;
	private int Level = 1;
	
	private long score = 0;
	private double difficulty = 0.09;
	
	public GameEngine(GamePanel gp, SpaceShip1 v1, SpaceShip2 v2) {
		this.gp = gp;
		this.v1 = v1;
		this.v2 = v2;		
		
		gp.sprites.add(v1);
		gp.sprites.add(v2);
		
		timer = new Timer(80, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
				count++;
				if(count == 600){
					difficulty += 0.09;
					count = 0;
				}
					
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		int x = (int)(Math.random()*390);
		Enemy e = new Enemy(x, 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateItem(){
			int x = (int)(Math.random()*390);
			ItemspeedDown i = new ItemspeedDown(x,30);
			gp.spriteOvals.add(i);
			ItemDown.add(i);
				
	}
	
	private void process(){
		
		if(Math.random() < difficulty){
			generateEnemy();
			count++;
		}
		if(Math.random() < difficulty && count %5 == 0){
			generateItem();
		}
		
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
				if(score %500 == 0){
					difficulty += 0.09;
					Level +=1;
				}
			}
		}
		Iterator<ItemspeedDown> w_iter = ItemDown.iterator();
		while(w_iter.hasNext()){
			ItemspeedDown e = w_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				w_iter.remove();
				gp.spriteOvals.remove(e);
				
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr1 = v1.getRectangle();
		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er;
		Ellipse2D.Double ei;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr1)){
				die();
				return;
			}
			if(er.intersects(vr2)){
					 die();
					 return;
			}
		}
		for(ItemspeedDown i : ItemDown){
			ei = i.getEllipse();
			if(ei.intersects(vr1)){
				 difficulty -= 0.01;
			}
			else if(ei.intersects(vr2)){
				 difficulty -= 0.0;
			}
		}
	
	}
	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			v1.move(-1);
			break;
		case KeyEvent.VK_D:
			v1.move(1);
			break;
		case KeyEvent.VK_LEFT:
			v2.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v2.move(1);
			break;
		}
	}
	

	public long getScore(){
		return score;
	}
	public	int getLevel(){
		return Level;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
