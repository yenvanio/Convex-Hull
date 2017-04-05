package ConvexHull;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConvexHull extends JPanel {

	private static Point2D[] points;
	private static Point2D[] hullPoints;
	String csvFile;
	int i = 5;
	static int sizeX = 2000, sizeY=2000;
	int scalar = 100; int adder = 50;
	QStack<Polygon> qstack = new QStack<Polygon>();

	public ConvexHull() {

		setUp(i);

		final long startTime = System.currentTimeMillis();
		long totalHullPoints = 0;

		hullPoints = new Point2D[] {};
		
		JarvisMarch jarvisMarch = new JarvisMarch(points);
		hullPoints = jarvisMarch.getHull();

		totalHullPoints += hullPoints.length;

		getPolygons();
		repaint();

		final long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("Total time elapsed: " + duration + "ms");
	}

	public void setUp(int exercise) {
		// Set up Points
		csvFile = "exercise-" + exercise + ".csv";
		String delimeter = ",";
		List<String[]> lines = new ArrayList<String[]>();

		try {
			Scanner sc = new Scanner(new File(csvFile));
			sc.nextLine();
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine().split(","));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int size = lines.size();
		points = new Point2D[size];

		for (int i = 0; i < lines.size(); i++) {
			String[] temp = lines.get(i);
			Point2D.Double p = new Point2D.Double();
			p.x = Double.parseDouble(temp[0]);
			p.y = Double.parseDouble(temp[1]);
			points[i] = p;
		}

	}

	public static void setUpFrame() {

		ConvexHull panel = new ConvexHull();

		JFrame frame = new JFrame();
		frame.setTitle("Convex Hull Plot");
		frame.setSize(sizeX, sizeY);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());

		panel.setLayout(null);
		panel.setLocation(0, 0);
		panel.setPreferredSize(new Dimension(sizeX, sizeY));

		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	public void getPolygons() {
		if(i==4){scalar=20;}
		int xPoints[] = new int[hullPoints.length];
		int yPoints[] = new int[hullPoints.length];

		for (int i = 0; i < hullPoints.length; ++i) {
			double xD = Math.abs((hullPoints[i].getX())) *scalar;
			double xY = Math.abs((hullPoints[i].getY())) *scalar;
			xPoints[i] = (int)xD;
			yPoints[i] = (int) xY;
		}
		Polygon p = new Polygon(xPoints, yPoints, hullPoints.length);
		qstack.push(p);
	}

	@Override
	public void paintComponent(Graphics g) {
		if(i==4){scalar=20;}
		// super.paintComponents(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, sizeX, sizeY);

		// Draw the points
		g.setColor(Color.WHITE);
		Graphics2D g2 = (Graphics2D) g;

		for (Point2D point : points) {
			double xD = Math.abs((point.getX())) *scalar;
			int x = (int)xD;
			System.out.print(x + " ");
			double yD = Math.abs((point.getY()))*scalar;
			int y = (int)yD;
			System.out.print(y);
			Ellipse2D e = new Ellipse2D.Double(x - 2, y - 2, 6, 6);
			g2.fill(e);
			System.out.println(" ");
		}
		for (int i = 0; i < qstack.size(); i++) {

			g2.draw(qstack.pop());
		}

	}

	public static void main(String[] args) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		setUpFrame();
	}
}