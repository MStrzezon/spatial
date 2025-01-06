package org.neo4j.gis.spatial.pipes.processing;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;


public class Test {

	public static void main(String[] args) throws ParseException {
		Coordinate c1 = new Coordinate(567141.2325154628, 244213.50232875533);
		Coordinate c2 = new Coordinate(637384.2583022658, 486754.59622221906);

		Coordinate c3 = new Coordinate(7423986.565104583, 5547788.146700716);
		Coordinate c4 = new Coordinate(7500835.494143137, 5788453.816557724);

		// Initialize the GeometryFactory
		GeometryFactory geometryFactory = new GeometryFactory();

		// 1. Create the Point from coordinates (7423986.565104583, 5547788.146700716)
		Coordinate pointCoordinate = new Coordinate(7423986.565104583, 5547788.146700716);
		Point point = geometryFactory.createPoint(pointCoordinate);

		// Print the Point
		System.out.println("Point: " + point);

		// 2. Create the Polygon from WKT (Well-Known Text)
		String wkt = "POLYGON ((499420.8127744635567069 258092.1025804663076997, 499423.7957242266274989 258091.9799863677471876, 499423.6167456065304577 258086.7001497829332948, 499420.6123816219624132 258086.8005153397098184, 499420.8127744635567069 258092.1025804663076997))";
		WKTReader reader = new WKTReader(geometryFactory);
		Geometry geometry = reader.read(wkt);

		// Assuming the WKT is a polygon
		Polygon polygon = (Polygon) geometry;

		// Print the Polygon
		System.out.println("Polygon: " + polygon);

		System.out.println(OrthodromicDistance.suggestSearchWindow(point, 1));
		System.out.println(OrthodromicDistance.suggestSearchWindow(polygon, 0.001));

//		Coordinate c1 = new Coordinate(19.938333, 50.061389);
//		Coordinate c2 = new Coordinate(21.012229, 52.229676);
		System.out.println(OrthodromicDistance.calculateDistance(c1, c2));
		System.out.println(OrthodromicDistance.calculateDistance(c3, c4));
//		System.out.println(OrthodromicDistance.suggestSearchWindow(c1, 1));

	}

}
