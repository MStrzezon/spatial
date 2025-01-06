package org.neo4j.gis.spatial.pipes.processing;


import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.operation.overlay.OverlayOp;
import org.locationtech.jts.operation.overlay.snap.SnapIfNeededOverlayOp;
import org.neo4j.gis.spatial.pipes.AbstractGeoPipe;
import org.neo4j.gis.spatial.pipes.GeoPipeFlow;

public class CalculateIntersectionAngle extends AbstractGeoPipe {

	private final Geometry referenceGeometry;

	public static final String ANGLE = "IntersectionAngle";

	public CalculateIntersectionAngle(Geometry referenceGeometry) {
		super(ANGLE);
		this.referenceGeometry = referenceGeometry;
	}

	@Override
	protected GeoPipeFlow process(GeoPipeFlow flow) {
		if (referenceGeometry instanceof MultiLineString referenceMultiLineString
				&& flow.getGeometry() instanceof MultiLineString flowMultiLineString) {
			for (int i = 0; i < flowMultiLineString.getNumGeometries(); i++) {
				LineString ls1 = (LineString) flowMultiLineString.getGeometryN(i);
				for (int j = 0; j < referenceMultiLineString.getNumGeometries(); j++) {
					LineString ls2 = (LineString) referenceMultiLineString.getGeometryN(j);
					Geometry intersection = SnapIfNeededOverlayOp.overlayOp(ls1, ls2, OverlayOp.INTERSECTION);

					if (!intersection.isEmpty() && intersection instanceof Point intersectionPoint) {

						double angle = Angle.angleBetweenOriented(ls1.getCoordinate(), intersection.getCoordinate(),
								ls2.getCoordinate());
						setProperty(flow, angle);
						return flow;
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Both geometries must be MultiLineString");
		}
		return flow;
	}

}
