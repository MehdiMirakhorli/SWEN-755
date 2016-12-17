
public class Obstacle {
	int minX, minY, maxX, maxY;
	int height = 20, width = 20;
	
	SafeArea sa;
	public Obstacle(ZoneModel zm) {
		this.minX = zm.getZoneMinX() + 100;
		this.minY = zm.getZoneMinY() + 100;
		this.maxX = this.minX + 20;
		this.maxY = this.minY + 20;
		this.sa = new SafeArea(minX, minY);
	}
	
}

class SafeArea {
	
	public int minX;
	public int minY;
	public int maxX;
	public int maxY;
	
	public SafeArea(int minX, int minY) {
		this.minX = minX - 50;
		this.maxX = this.minX + 120;
		this.minY = minY - 41;
		this.maxY = this.minY + 100;
	}
	
}
