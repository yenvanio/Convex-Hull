package ConvexHull;

import java.awt.geom.Point2D;

import java.util.Comparator;

public class PointComparator implements Comparator<Point2D> {

	private Point2D origin;

	public PointComparator() {
	}

	public PointComparator(Point2D origin) {
		this.origin = origin;
	}

	@Override
	public int compare(Point2D p1, Point2D p2) {
		double angle;
		if (origin != null)
			angle = (p1.getX() - origin.getX()) * (p2.getY() - origin.getY())
					- (p2.getX() - origin.getX()) * (p1.getY() - origin.getY());
		else {
			angle = 
					p1.getX() * p2.getY() - p1.getY() * p2.getX();
		}
		return (int) angle;
	}
}
