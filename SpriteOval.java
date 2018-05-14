
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

public abstract class SpriteOval {
	int x;
	int y;
	int width;
	int height;
	
	public SpriteOval(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	abstract public void draw1(Graphics2D g);
	
	public Double getEllipse() {
		return new Ellipse2D.Double(x, y, width, height);
	}
}
