package cgeo.geocaching.maps.mapsforge.v6.layers;

import cgeo.geocaching.location.Geopoint;
import cgeo.geocaching.maps.routing.Route;
import cgeo.geocaching.utils.DisplayUtils;

import java.util.ArrayList;

import org.mapsforge.core.graphics.Canvas;
import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.Point;

public class RouteLayer extends AbstractLineLayer implements Route.RouteUpdater {

    private float distance = 0.0f;
    private PostRealDistance postRealRouteDistance = null;

    public interface PostRealDistance {
        void postRealDistance (float realDistance);
    }

    public RouteLayer(final PostRealDistance postRealRouteDistance) {
        super();
        lineColor = 0xD00000A0;
        this.postRealRouteDistance = postRealRouteDistance;
        width = DisplayUtils.getRouteLineWidth();
    }

    public void updateRoute(final ArrayList<Geopoint> route, final float distance) {
        super.updateTrack(route);
        this.distance = distance;

        if (postRealRouteDistance != null) {
            postRealRouteDistance.postRealDistance(distance);
        }
    }

    @Override
    public void draw(final BoundingBox boundingBox, final byte zoomLevel, final Canvas canvas, final Point topLeftPoint) {
        super.draw(boundingBox, zoomLevel, canvas, topLeftPoint);

        if (postRealRouteDistance != null) {
            postRealRouteDistance.postRealDistance(distance);
        }
    }

}
