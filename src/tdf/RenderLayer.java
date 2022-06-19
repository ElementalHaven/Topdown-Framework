package tdf;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

class RenderLayer {
	final int				layerId;
	final List<Renderable>	objects	= new ArrayList<>();

	RenderLayer(int layer) {
		layerId = layer;
	}
	
	boolean render(Graphics2D g, Config config, Rectangle2D viewportBounds, boolean antialiasingEnabled) {
		for(int i = 0; i < objects.size(); i++) {
			Renderable object = objects.get(i);
			// render only visible objects that lie within the viewport area
			// if the object's bounds are unknown,
			// it's considered always within the viewport area
			if(object.visible && (object.bounds == null || viewportBounds.intersects(object.bounds))) {
				if(config.antialias && (object.canBeAntialiased() != antialiasingEnabled)) {
					antialiasingEnabled = !antialiasingEnabled;
					
					Object hint = antialiasingEnabled ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF;
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, hint);
				}
				object.render(g, config);
			}
		}
		return antialiasingEnabled;
	}
	
	void clear() {
		objects.clear();
	}
}
