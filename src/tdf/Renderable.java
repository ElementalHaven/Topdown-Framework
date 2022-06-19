package tdf;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Main object to be added to the renderer for the purpose of drawing to it.
 */
public abstract class Renderable {
	/**
	 * Whether or not the object is visible, and as such, should be rendered.
	 */
	public boolean		visible = true;
	/**
	 * A bounding box representing the area taken up by this Renderable.<br>
	 * If not null, these bounds will be tested against the renderer's visible
	 * bounds to see if this object should be drawn.
	 * If null, this object will be drawn regardless
	 */
	public Rectangle	bounds;
	
	/**
	 * The main function for rendering an object within the renderer
	 * 
	 * @param g The graphics context to render to
	 * @param config The active rendering configuration
	 */
	public abstract void render(Graphics2D g, Config config);
	
	/**
	 * When overriden, allows objects to opt out of any antialiasing.
	 * This is useful for when antialiasing can cause rendering artifacts,
	 * such as bleeding between the edges of filled polygons
	 * 
	 * @return Whether or not antialiasing is allowed to be done for this object
	 */
	public boolean canBeAntialiased() {
		return true;
	}
}
