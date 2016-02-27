import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class UVA10389 {

	private static BufferedReader in;
	private static StringBuilder answer = new StringBuilder();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int numCase = -1;
		String line = null;
		int posx, posy;
		Scanner scan;
		Point initPoint, finPoint, preStat = null, stat = null;
		Edge edge;
		File file = new File("in_10389");
		ArrayList<Point> listPoint = new ArrayList<Point>();

		if (file.exists())
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
		else
			in = new BufferedReader(new InputStreamReader(System.in));

		numCase = Integer.parseInt(in.readLine());
		in.readLine();

		for (int i = 0; i < numCase; ++i) {
			line = in.readLine();

			scan = new Scanner(line);

			posx = Integer.parseInt(scan.next());
			posy = Integer.parseInt(scan.next());

			initPoint = new Point(false, posx, posy);

			posx = Integer.parseInt(scan.next());
			posy = Integer.parseInt(scan.next());

			finPoint = new Point(false, posx, posy);

			edge = new Edge(finPoint);
			edge.calculateMinutes(initPoint);
			initPoint.addEdge(edge);

			line = in.readLine();
			listPoint.clear();
			listPoint.add(initPoint);
			listPoint.add(finPoint);

			while (line != null && !line.equals("")) {
				scan = new Scanner(line);
				preStat = null;

				while (scan.hasNext()) {

					posx = Integer.parseInt(scan.next());
					posy = Integer.parseInt(scan.next());

					if (posx == -1 && posy == -1)
						break;

					stat = new Point(true, posx, posy);

					/*
					 * edge = new Edge(stat); edge.calculateMinutes(initPoint);
					 * initPoint.addEdge(edge);
					 * 
					 * edge = new Edge(finPoint); edge.calculateMinutes(stat);
					 * stat.addEdge(edge);
					 */

					if (preStat != null) {
						edge = new Edge(stat);
						edge.calculateMinutes(preStat);
						preStat.addEdge(edge);

						edge = new Edge(preStat);
						edge.calculateMinutes(stat);
						stat.addEdge(edge);
					}

					listPoint.add(stat);
					preStat = stat;
				}
				line = in.readLine();
			}

			for (Point point : listPoint) {
				for (Point point2 : listPoint) {
					Edge edw = new Edge(point2);
					edw.calculateMinutesWalk(point);
					point.addEdge(edw);
				}
			}

			computePaths(initPoint);
			if (answer.length() >0 )
				answer.append("\n");
			answer.append(Math.round(finPoint.minMinutes) + "\n");
		}

		System.out.print(answer);

	}

	static void computePaths(Point source) {
		source.minMinutes = 0.;
		PriorityQueue<Point> pointQueue = new PriorityQueue<Point>();
		pointQueue.add(source);

		while (!pointQueue.isEmpty()) {
			Point u = pointQueue.poll();

			// Visit each edge exiting u
			for (Edge e : u.adjacencies) {
				Point v = e.destination;
				double weight = e.minutes;
				double distanceThroughU = u.minMinutes + weight;
				if (distanceThroughU < v.minMinutes) {
					pointQueue.remove(v);
					v.minMinutes = distanceThroughU;
					v.previous = u;
					pointQueue.add(v);
				}
			}
		}
	}

}

class Point implements Comparable<Point> {

	public final boolean station;
	public int x, y;
	public ArrayList<Edge> adjacencies;
	public double minMinutes = Double.POSITIVE_INFINITY;
	public Point previous;

	public Point(boolean station, int px, int py) {
		super();
		this.station = station;
		adjacencies = new ArrayList<>();
		this.x = px;
		this.y = py;
	}

	@Override
	public int compareTo(Point o) {
		// TODO Auto-generated method stub
		return Double.compare(minMinutes, o.minMinutes);
	}

	void addEdge(Edge ed) {
		adjacencies.add(ed);
	}

}

class Edge {
	public final Point destination;
	double minutes;

	public Edge(Point target) {
		super();
		this.destination = target;
	}

	void calculateMinutes(Point origin) {
		double facx, facy;

		facx = destination.x - origin.x;
		facy = destination.y - origin.y;
		if (origin.station && destination.station) {
			minutes = (Math.sqrt((facx * facx) + (facy * facy)) / (1.0 * (40 * 1000) / 60));
		} else {
			minutes = (Math.sqrt((facx * facx) + (facy * facy)) / (1.0 * (10 * 1000) / 60));
		}
	}

	void calculateMinutesWalk(Point origin) {
		double facx, facy;

		facx = destination.x - origin.x;
		facy = destination.y - origin.y;

		minutes = (Math.sqrt((facx * facx) + (facy * facy)) / (1.0 * (10 * 1000) / 60));

	}

}
