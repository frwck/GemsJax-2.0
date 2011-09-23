package org.gemsjax.client.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gemsjax.client.admin.presenter.Presenter;
import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.IconLoadEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.IconLoadHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.client.metamodel.MetaConnectionImpl;
import org.gemsjax.client.metamodel.MetaClassImpl;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.Point;
import org.gemsjax.shared.metamodel.MetaConnection;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;

/**
 * This class is a {@link Drawable} that displays a {@link MetaConnection} on the {@link MetaClassCanvas}.
 * A {@link MetaConnection} is displayed between two {@link MetaClassDrawable}s.<br /> <br />
 * This class implements the {@link ResizeHandler} and the {@link MoveHandler} interface and will be registered to the 
 * connected {@link MetaClassDrawable}s. So when a {@link MetaClassDrawable} was moved / resized, this {@link MetaConnectionDrawable}
 * will automatically adjust itself to the {@link MetaClassDrawable}s.
 * 
 * 
 * This class paints the lines and icons for a connection, while the {@link MetaConnectionBox} draws a box with the name 
 * and Attributes. But the {@link MetaConnectionBox} itself is not a real Drawable, its only part of this MetaConnectionDrawable.
 * So this {@link MetaConnectionDrawable} implements the {@link Moveable} and {@link Resizeable} interface. This corresponding functionality is wrapped
 * by this MetaConnectionDrawable, but will be delegated to the {@link MetaConnectionBox}.
 * So the functionality is splitted in two classes ( {@link MetaConnectionDrawable} and {@link MetaConnectionBox}) but on the {@link MetaModelCanvas}
 * appears only this {@link MetaConnectionDrawable}
 * 
 * @author Hannes Dorfmann
 *
 */
public class MetaConnectionDrawable implements Drawable, Moveable, Clickable, Resizeable, Focusable, HasPlaceable,  PlaceableDestination, IconLoadable{
	
	/**
	 * The {@link MetaConnectionImpl} that is displayed with this Drawable
	 */
	private MetaConnection connection;
	
	/**
	 * The Connection connects {@link #source} with {@link #target}.
	 */
	private MetaClassDrawable source;

	/**
	 * The Connection connects {@link #source} with {@link #target}.
	 */
	private MetaClassDrawable target;
	
	
	private MetaConnectionBox connectionBox;

	// Handlers
	private List<FocusHandler> focusHandlers;
	private List<ClickHandler> clickHandlers;
	private List<MoveHandler> moveHandlers;
	private List<ResizeHandler> resizeHandlers;
	private List<IconLoadHandler> iconHandlers;
	
	public List<Anchor> dockedAnchors;
	
	private Anchor sourceAnchor;
	private Anchor sourceConnectionBoxAnchor;
	private Anchor targetAnchor;
	private Anchor targetConnectionBoxAnchor;
	
	private String destinationAreaHighlightColor = "rgba(168,245,140,0.5)";
	private String onMouseOverDestinationColor = "rgba(85,187,250,0.5)";
	
	
	private Image sourceIconImage;
	private Image targetIconImage;
	
	private boolean sourceIconLoaded = false;
	private boolean targetIconLoaded = false;
	
	
	/**
	 * The mapping between the AnchorPoint (model) and the Anchor (View)
	 */
	private HashMap<AnchorPoint, Anchor> anchorMap;
	
	
	/**
	 * The offset when, the user try to click on this Drawable by clicking on the connection line 
	 */
	private double mouseOffSet = 7;
	
	/**
	 * The offset when, the user try to click on this Drawable by clicking on the connection line, but the line is not a linear function, 
	 * but a vertical line instead
	 */
	private double verticalLineXOffset=7;
	
	
	
	/**
	 * Creates {@link Drawable} that displays a {@link MetaConnectionImpl}.
	 * A {@link MetaConnectionImpl} is displayed between two {@link MetaClassImpl}es, 
	 * @param connection The {@link MetaConnectionImpl} that is displayed with this Drawable
	 * @param metaClassA The {@link MetaClassDrawable} where the connection starts.
	 * @param metaClassB The {@link MetaClassDrawable} where the connection ends.
	 */
	public MetaConnectionDrawable(MetaConnection connection, MetaClassDrawable metaClassA, MetaClassDrawable metaClassB)
	{
		this.connection = connection;
		this.source = metaClassA;
		this.target = metaClassB;
		
		this.connectionBox = new MetaConnectionBox(connection, this);
		
		// Handlers
		focusHandlers = new ArrayList<FocusHandler>();
		clickHandlers = new ArrayList<ClickHandler>();
		moveHandlers = new ArrayList<MoveHandler>();
		resizeHandlers = new ArrayList<ResizeHandler>();
		iconHandlers = new ArrayList<IconLoadHandler>();
		dockedAnchors = new ArrayList<Anchor>();
		
		generateAnchors();

		setSource(metaClassA);
		setTarget(metaClassB);
		
		
		// Icons
		initIcons();
	}
	
	
	public ImageElement getSourceIcon()
	{
		return (ImageElement) sourceIconImage.getElement().cast();
	}
	
	
	public ImageElement getTargetIcon()
	{
		return (ImageElement) targetIconImage.getElement().cast();
	}
	
	
	private void initIcons()
	{
		sourceIconImage = new Image();
		
		sourceIconImage.addLoadHandler(new LoadHandler() {
				
				@Override
				public void onLoad(LoadEvent event) {
					sourceIconLoaded = true;
					fireIconLoadEvent(new IconLoadEvent(MetaConnectionDrawable.this, sourceIconImage.getUrl()));
				}
			});
				
	
		sourceIconImage.setVisible(false);
		RootPanel.get().add(sourceIconImage); // image must be on page to fire load events
		
		sourceIconLoaded = false;
		sourceIconImage.setUrl(connection.getSourceIconURL());
		
		
		targetIconImage = new Image();
		
		targetIconImage.addLoadHandler(new LoadHandler() {
				
				@Override
				public void onLoad(LoadEvent event) {
					targetIconLoaded = true;
					fireIconLoadEvent(new IconLoadEvent(MetaConnectionDrawable.this, targetIconImage.getUrl()));
				}
			});
				
	
		targetIconImage.setVisible(false);
		RootPanel.get().add(targetIconImage); // image must be on page to fire load events
		
		targetIconLoaded = false;
		targetIconImage.setUrl(connection.getTargetIconURL());
		
		
		
	}
	
	
	
	/**
	 * Generates the Anchors for the line between source and connection box and the connection box and the target
	 * and insert the new created Anchors into the {@link #anchorMap}
	 */
	private void generateAnchors()
	{
	
		anchorMap = new HashMap <AnchorPoint, Anchor>();
		
		
		AnchorPoint sp = connection.getSourceRelativePoint();
		AnchorPoint scp = connection.getSourceConnectionBoxRelativePoint();
		AnchorPoint tcp = connection.getTargetConnectionBoxRelativePoint();
		AnchorPoint tp = connection.getTargetRelativePoint();
		
		
		sourceAnchor = new Anchor(sp, source);
		sourceConnectionBoxAnchor = new Anchor(scp, this);
		targetConnectionBoxAnchor = new Anchor(tcp, this);
		targetAnchor = new Anchor(tp, target);
		
		
		// docking 
		this.dockAnchor(sourceConnectionBoxAnchor);
		this.dockAnchor(targetConnectionBoxAnchor);
		
		
		anchorMap.put(connection.getSourceRelativePoint(), sourceAnchor);
		anchorMap.put(connection.getSourceConnectionBoxRelativePoint(), sourceConnectionBoxAnchor);
		anchorMap.put(connection.getTargetConnectionBoxRelativePoint(), targetConnectionBoxAnchor);
		anchorMap.put(connection.getTargetRelativePoint(), targetAnchor);
		
		AnchorPoint current = sourceAnchor.getNextAnchorPoint();
		
		while (current!=sourceConnectionBoxAnchor.getAnchorPoint())
		{
			anchorMap.put(current, new Anchor(current, null));
			current = current.getNextAnchorPoint();
		}
		
		
		current = targetConnectionBoxAnchor.getNextAnchorPoint();
		
		while (current!=targetAnchor.getAnchorPoint())
		{
			anchorMap.put(current, new Anchor(current, null));
			current = current.getNextAnchorPoint();
		}
		
	}
	
	
	
	
	
	@Override
	public void draw(Context2d context) {
		
		context.save();
		
		drawConnectionLines(context);
		
		connectionBox.draw(context);
		
		// TODO what to do with the icons
		//drawIcons(context);
		
		
		if (connection.isSelected())
			drawOnSelect(context);
		
		
		context.restore();
	}
	
	
	private void drawIcons(Context2d context)
	{
		double height = connection.getSourceIconHeight();
		double width = connection.getSourceIconWidth();
		
		double x = source.getX() + sourceAnchor.getX() ;
		double y = source.getY() + sourceAnchor.getY() ;
		
		
		if (connection.getSourceIconURL()!=null && !connection.getSourceIconURL().equals(""))
		{
			if (sourceIconLoaded)
			{
				context.save();
				
				SC.logWarn("Destination: "+sourceAnchor.getDestination().getCoordinatesBorderDirection(x, y));
				
				context.translate(x-connection.getSourceIconWidth(), y);
				
				switch(sourceAnchor.getDestination().getCoordinatesBorderDirection(x, y))
				{
					
					case LEFT: 	context.rotate(2*Math.PI - Math.PI/2); break;
								
					case BOTTOM:context.rotate(Math.PI); break;
								
					case RIGHT: context.rotate(Math.PI/2); break;
					
					case NOWHERE:
					case TOP:	
					default: 	break;
				}
				
				
				context.drawImage(getSourceIcon(), 0,0, width, height);
				context.restore();
				
				
			}
			else
			{	
	
				String loadingTxt = "Loading";
				
				context.setFont(""+connection.getAttributeFontSize()+"px "+connection.getFontFamily());
				double textWidth = loadingTxt.length() * connection.getAttributeFontCharWidth();
				
				context.setFillStyle("black");
				context.setTextAlign("left");
				
				if (textWidth<width)
					context.fillText(loadingTxt, x+((width-textWidth)/2), y+connection.getAttributeFontSize(), width);
				else
				{
					int chars = (int)(textWidth / connection.getAttributeFontCharWidth()) - 3;
					
					if (chars<=0)
						context.fillText("...", x, y+connection.getAttributeFontSize(), width);
					else
						context.fillText(loadingTxt.substring(0, chars)+"...", x, y+connection.getAttributeFontSize(), width);
				}
			}
		}
		
		
		
		
		if (connection.getTargetIconURL()!=null && !connection.getTargetIconURL().equals(""))
		{
		
			x = target.getX() + targetAnchor.getX();
			y = target.getY() + targetAnchor.getY();
			
			width = connection.getTargetIconWidth();
			height = connection.getTargetIconHeight();
	
			if (targetIconLoaded)
			{
				context.save();
				
				context.translate(x, y);
				
				switch(targetAnchor.getDestination().getCoordinatesBorderDirection(x, y))
				{
					
					case LEFT: 	context.rotate(Math.PI/2); break;
								
					case BOTTOM:context.rotate(Math.PI); break;
								
					case RIGHT: context.rotate(2*Math.PI - Math.PI/2); break;
					
					case NOWHERE: 
					case TOP:	
					default: 	context.rotate(0); break;
				}
				
				context.drawImage(getTargetIcon(), 0,0, width, height);
				
				context.restore();
				
			}
			else
			{	
	
				String loadingTxt = "Loading";
				
				context.setFont(""+connection.getAttributeFontSize()+"px "+connection.getFontFamily());
				double textWidth = loadingTxt.length() * connection.getAttributeFontCharWidth();
				
				context.setFillStyle("black");
				context.setTextAlign("left");
				
				if (textWidth<width)
					context.fillText(loadingTxt, x+((width-textWidth)/2), y+connection.getAttributeFontSize(), width);
				else
				{
					int chars = (int)(textWidth / connection.getAttributeFontCharWidth()) - 3;
					
					if (chars<=0)
						context.fillText("...", x, y+connection.getAttributeFontSize(), width);
					else
						context.fillText(loadingTxt.substring(0, chars)+"...", x, y+connection.getAttributeFontSize(), width);
				}
			}
		
		}
		
	}
	
	
	private void drawConnectionLines(Context2d context)
	{
		context.save();
		
		context.setFillStyle(connection.getLineColor());
		context.setLineWidth(connection.getLineSize());
		
		
		
		double prevX , prevY;
		Anchor a;
		
		prevX = source.getX() + sourceAnchor.getX();
		prevY = source.getY() + sourceAnchor.getY();
		

		AnchorPoint current = sourceAnchor.getAnchorPoint().getNextAnchorPoint();
		
		context.beginPath();
		context.moveTo(prevX, prevY);
	
		// Draw line between the source and the connection box	
		while (current!=sourceConnectionBoxAnchor.getAnchorPoint())
		{
			a = anchorMap.get(current);
			
			context.lineTo( a.getX(), a.getY());
			
			prevX = a.getX();
			prevY = a.getY();
			current = current.getNextAnchorPoint();
		}
		
		
		// At last, draw line from previous to the connectionBoxAnchor (the finish)
		//context.moveTo(prevX, prevY);
		context.lineTo( connectionBox.getX() + sourceConnectionBoxAnchor.getX(), connectionBox.getY()+ sourceConnectionBoxAnchor.getY());
		
		context.stroke();
		
		prevX = targetConnectionBoxAnchor.getX() + connectionBox.getX();
		prevY = targetConnectionBoxAnchor.getY() + connectionBox.getY();
		
		current=targetConnectionBoxAnchor.getAnchorPoint().getNextAnchorPoint();
		
		context.beginPath();
		context.moveTo(prevX, prevY);
		
		// Draw Anchor points between the connection box and the target
		while (current!=targetAnchor.getAnchorPoint())
		{
			a = anchorMap.get(current);
			
			context.lineTo( a.getX(), a.getY());
			
			prevX = a.getX();
			prevY = a.getY();
			current = current.getNextAnchorPoint();
		}
		
		// At last, draw line from previous to the targetAnchor (the finish)
		// context.moveTo(prevX, prevY);
		context.lineTo( target.getX() + targetAnchor.getX(), target.getY()+ targetAnchor.getY());
		
		
		context.stroke();
	
		context.restore();
	}
	
	
	
	
	private void drawOnSelect(Context2d context)
	{
		sourceAnchor.draw(context);
		sourceConnectionBoxAnchor.draw(context);
		targetAnchor.draw(context);
		targetConnectionBoxAnchor.draw(context);
		
		AnchorPoint current = sourceAnchor.getNextAnchorPoint();
		
		Anchor a;
		// Draw anchor points between the source and the connection box	
		while (current!=sourceConnectionBoxAnchor.getAnchorPoint())
		{
			a = anchorMap.get(current);
			a.draw(context);
			current = current.getNextAnchorPoint();
		}
		

		current=targetConnectionBoxAnchor.getAnchorPoint();
		
		// Draw Anchot points between the connection box and the target
		while (current!=targetAnchor.getAnchorPoint())
		{
			a = anchorMap.get(current);
			a.draw(context);
			current = current.getNextAnchorPoint();
		}
		
	}
	
	
	@Override
	public boolean isSelected()
	{
		return connection.isSelected();
	}

	@Override
	public double getZIndex() {
		return connection.getZIndex();
	}


	
	
	@Override
	public boolean hasCoordinate(double x, double y) {
		
		boolean box = connectionBox.hasCoordinate(x, y);
		
		if (box ) return true;
		
		
		double m =0, b = 0 , t = 0, currentX=0, currentY=0, nextX=0, nextY = 0;
		
		Anchor currentAnchor = sourceAnchor;
		Anchor nextAnchor = null;
		
		AnchorPoint currentPoint = sourceAnchor.getAnchorPoint();
		
		
		while (currentPoint != sourceConnectionBoxAnchor.getAnchorPoint())
		{
			currentAnchor = anchorMap.get(currentPoint);
			nextAnchor = anchorMap.get(currentPoint.getNextAnchorPoint());
			
			// if the current is the sourceAnchorPoint, which is the start point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (currentAnchor == sourceAnchor)
			{
				currentX = source.getX() + sourceAnchor.getX();
				currentY = source.getY() + sourceAnchor.getY();
			}
			else
			{
				currentX = currentAnchor.getX();
				currentY = currentAnchor.getY();
			}
			
			
			
			// if the next is the sourceConnectionBoxAnchorPoint, which is the end point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (nextAnchor == sourceConnectionBoxAnchor)
			{
				nextX = connectionBox.getX() + sourceConnectionBoxAnchor.getX();
				nextY = connectionBox.getY() + sourceConnectionBoxAnchor.getY();
			}
			else
			{
				nextX = nextAnchor.getX();
				nextY = nextAnchor.getY();
			}
			
			
			
			// special case that current and next have the same x coordinate, so there is a vertical line instead of a linear function
			if (Math.abs(currentX-x)<=verticalLineXOffset && Math.abs(nextX-x)<=verticalLineXOffset)
			{
				if (currentY<nextY && isBetween(currentY, nextY, y))
					return true;
				else
				if (currentY>=nextY && isBetween(nextY, currentY, y))
					return true;
			}
			
			
			// calculate the slopetriangle 
			m = (currentY - nextY) / (currentX - nextX);
			
			// calculate x axis deferral	b = y - m*x
			b = currentY - m * currentX;
			
			// temp variable to calculate  m * x + b = t
			t = m * x + b;
			
			if (Math.abs(t - y)<=mouseOffSet)
				return true;
			
			currentPoint = currentPoint.getNextAnchorPoint();
		}
		
		
		
		
		currentAnchor = targetConnectionBoxAnchor;
		nextAnchor = null;
		
		currentPoint = targetConnectionBoxAnchor.getAnchorPoint();
		
		
		while (currentPoint != targetAnchor.getAnchorPoint())
		{
			currentAnchor = anchorMap.get(currentPoint);
			nextAnchor = anchorMap.get(currentPoint.getNextAnchorPoint());
			
			// if the current is the sourceAnchorPoint, which is the start point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (currentAnchor == targetConnectionBoxAnchor)
			{
				currentX = connectionBox.getX() + targetConnectionBoxAnchor.getX();
				currentY = connectionBox.getY() + targetConnectionBoxAnchor.getY();
			}
			else
			{
				currentX = currentAnchor.getX();
				currentY = currentAnchor.getY();
			}
			
			
			
			// if the next is the sourceConnectionBoxAnchorPoint, which is the end point, than you first have to calculate the absolute x/y since in the AnchorPoint itself has relative coordinates
			if (nextAnchor == targetAnchor)
			{
				nextX = target.getX() + targetAnchor.getX();
				nextY = target.getY() + targetAnchor.getY();
			}
			else
			{
				nextX = nextAnchor.getX();
				nextY = nextAnchor.getY();
			}
			
			
			
			// special case that current and next have the same x coordinate, so there is a vertical line instead of a linear function
			if (Math.abs(currentX-x)<=verticalLineXOffset && Math.abs(nextX-x)<=verticalLineXOffset)
			{
				if (currentY<nextY && isBetween(currentY, nextY, y))
					return true;
				else
				if (currentY>=nextY && isBetween(nextY, currentY, y))
					return true;
			}
			
			
			// calculate the slopetriangle 
			m = (currentY - nextY) / (currentX - nextX);
			
			// calculate x axis deferral	b = y - m*x
			b = currentY - m * currentX;
			
			// temp variable to calculate  m * x + b = t
			t = m * x + b;
			
			if (Math.abs(t - y)<=mouseOffSet)
				return true;
			
			currentPoint = currentPoint.getNextAnchorPoint();
		}

		
		return false;
	}

	

	@Override
	public void addMoveHandler(MoveHandler handler) {
		if (!moveHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				moveHandlers.add(moveHandlers.size(), handler);
			else
				moveHandlers.add(0,handler);
	}

	@Override
	public boolean fireMoveEvent(MoveEvent event) {
		boolean delivered = false;
		
		for (MoveHandler h : moveHandlers)
		{
			h.onMove(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public double getX() {
		// Used by the Moveable
		return connectionBox.getX();
	}

	@Override
	public double getY() {
		return connectionBox.getY();
	}

	

	@Override
	public void removeMoveHandler(MoveHandler handler) {
		moveHandlers.remove(handler);
	}

	@Override
	public void addClickHandler(ClickHandler handler) {
		
		if (!clickHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should allways be the last in the list
				clickHandlers.add(clickHandlers.size(), handler);
			else
				clickHandlers.add(0,handler);
		
	}

	@Override
	public boolean fireClickEvent(ClickEvent event) {
		
		boolean delivered = false;
		
		for (ClickHandler h : clickHandlers)
		{
			h.onClick(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void removeClickHandler(ClickHandler handler) {
		clickHandlers.remove(handler);
	}

	@Override
	public void addFocusHandler(FocusHandler handler) {
		if (!focusHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				focusHandlers.add(focusHandlers.size(), handler);
			else
				focusHandlers.add(0,handler);
	}

	@Override
	public boolean fireFocusEvent(FocusEvent event) {
		
		boolean delivered = false;
		
		for (FocusHandler h : focusHandlers)
		{
			h.onFocusEvent(event);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void removeFocusHandler(FocusHandler handler) {
		focusHandlers.remove(handler);
	}


	@Override
	public Object getDataObject() {
		return connection;
	}



	public MetaClassDrawable getSource() {
		return source;
	}




	/**
	 * Set the source (Drawable) of this {@link MetaConnectionDrawable} (named {@link #source}).
	 * The call of this method will also dock the {@link #sourceAnchor} on the {@link #cource} by calling {@link MetaClassDrawable#dockAnchor(Anchor)}
	 * and call {@link MetaClassDrawable#undockAnchor(Anchor)} of the previous source.
	 * @param source
	 */
	public void setSource(MetaClassDrawable source) {
		
		if (this.source!=null)
		{
			this.source.undockAnchor(sourceAnchor);
		}
		
		this.source = source;
		this.source.dockAnchor(sourceAnchor);
	}





	public MetaClassDrawable getTarget() {
		return target;
	}





	/**
	 * Set the target (Drawable) of this {@link MetaConnectionDrawable} (named {@link #source}).
	 * The call of this method will also dock the {@link #targetAnchor} on the {@link #target} by calling {@link MetaClassDrawable#dockAnchor(Anchor)}
	 * and call {@link MetaClassDrawable#undockAnchor(Anchor)} of the previous target.
	 * @param target
	 */
	public void setTarget(MetaClassDrawable target) {
		
		if (this.target!=null)
		{
			this.target.undockAnchor(targetAnchor);
		}
		
		this.target = target;
		this.target.dockAnchor(targetAnchor);
		
	}


	@Override
	public void setX(double x) {
		connectionBox.setX(x);
	}


	@Override
	public void setY(double y) {
		connectionBox.setY(y);
	}




	@Override
	public void addResizeHandler(ResizeHandler handler) {
		if (!resizeHandlers.contains(handler))
			if (handler instanceof Presenter)		// The presenter should always be the last in the list
				resizeHandlers.add(resizeHandlers.size(), handler);
			else
				resizeHandlers.add(0,handler);
		
	}


	@Override
	public boolean fireResizeEvent(ResizeEvent event) {

		boolean delivered = false;
		
		for (ResizeHandler h : resizeHandlers)
		{
			h.onResize(event);
			delivered = true;
		}
		
		return delivered;
	}


	@Override
	public double getHeight() {
		return connectionBox.getHeight();
	}


	@Override
	public double getWidth() {
		return connectionBox.getWidth();
	}


	@Override
	public ResizeArea isResizerAreaAt(double x, double y) {
		return connectionBox.isResizerAreaAt(x, y);
	}


	@Override
	public void removeResizeHandler(ResizeHandler resizeHandler) {
		resizeHandlers.remove(resizeHandler);
	}


	@Override
	public void setHeight(double height) {
		connectionBox.setHeight(height);
	}


	@Override
	public void setWidth(double width) {
		connectionBox.setWidth(width);
	}





	@Override
	public double getMinHeight() {
		return connectionBox.getMinHeight();
	}





	@Override
	public double getMinWidth() {
		return connectionBox.getMinWidth();
	}
	
	
	/**
	 * Automatically adjust the coordinates of the {@link #sourceConnectionBoxAnchor} and {@link #targetConnectionBoxAnchor}.
	 * This must always be called, when the width or the height has been changed.
	 */
	public void adjustConnectionBoxAnchors()
	{
		
		if (sourceConnectionBoxAnchor.getY() > getHeight())
			sourceConnectionBoxAnchor.setY(getHeight());
		
		if (sourceConnectionBoxAnchor.getX()> getWidth())
			sourceConnectionBoxAnchor.setX(getWidth());
		
		
		if (sourceConnectionBoxAnchor.getY()<getHeight() && sourceConnectionBoxAnchor.getY() > 0)
			if (sourceConnectionBoxAnchor.getX()>0 && sourceConnectionBoxAnchor.getX()<getWidth())
				sourceConnectionBoxAnchor.setX(getWidth());


		if (sourceConnectionBoxAnchor.getX()<getWidth() && sourceConnectionBoxAnchor.getX()>0)
			if (sourceConnectionBoxAnchor.getY()>0 && sourceConnectionBoxAnchor.getY()<getHeight())
				sourceConnectionBoxAnchor.setY(getHeight());
		
		
		
		
		if (targetConnectionBoxAnchor.getY() > getHeight())
			targetConnectionBoxAnchor.setY(getHeight());
		
		if (targetConnectionBoxAnchor.getX()> getWidth())
			targetConnectionBoxAnchor.setX(getWidth());
		
		
		if (targetConnectionBoxAnchor.getY()<getHeight() && targetConnectionBoxAnchor.getY()>0)
			if (targetConnectionBoxAnchor.getX()>0 && targetConnectionBoxAnchor.getX()<getWidth())
				targetConnectionBoxAnchor.setX(getWidth());
		

		if (targetConnectionBoxAnchor.getX()<getWidth() && targetConnectionBoxAnchor.getX()>0)
			if (targetConnectionBoxAnchor.getY()>0 && targetConnectionBoxAnchor.getY()<getHeight())
				targetConnectionBoxAnchor.setY(getHeight());
	}









	public Anchor getSourceAnchor() {
		return sourceAnchor;
	}





	public Anchor getSourceConnectionBoxAnchor() {
		return sourceConnectionBoxAnchor;
	}





	public Anchor getTargetAnchor() {
		return targetAnchor;
	}





	public Anchor getTargetConnectionBoxAnchor() {
		return targetConnectionBoxAnchor;
	}


	
	
	private boolean isBetween(double minValue, double maxValue, double valueToCheck)
	{
		return valueToCheck>=minValue && valueToCheck<=maxValue;
	}

	



	@Override
	public Anchor hasPlaceableAt(double x, double y) {
		
		
		
		// Calculate the absolute Positions for the 4 default AnchorPoints
		if (isBetween(source.getX()+sourceAnchor.getX()-(sourceAnchor.getWidth()/2), source.getX()+sourceAnchor.getX()+(sourceAnchor.getWidth()/2), x)
			&& isBetween(source.getY()+sourceAnchor.getY()-(sourceAnchor.getHeight()/2), source.getY()+sourceAnchor.getY()+(sourceAnchor.getHeight()/2), y)
			)
			return sourceAnchor;
		
		
		if (isBetween(connectionBox.getX()+sourceConnectionBoxAnchor.getX()-(sourceConnectionBoxAnchor.getWidth()/2), connectionBox.getX()+sourceConnectionBoxAnchor.getX()+(sourceConnectionBoxAnchor.getWidth()/2), x)
			&& isBetween(connectionBox.getY()+sourceConnectionBoxAnchor.getY()-(sourceConnectionBoxAnchor.getHeight()/2), connectionBox.getY()+sourceConnectionBoxAnchor.getY()+(sourceConnectionBoxAnchor.getHeight()/2), y)
			)
			return sourceConnectionBoxAnchor;
		
		
		if (isBetween(connectionBox.getX()+targetConnectionBoxAnchor.getX()-(targetConnectionBoxAnchor.getWidth()/2), connectionBox.getX()+targetConnectionBoxAnchor.getX()+(targetConnectionBoxAnchor.getWidth()/2), x)
			&& isBetween(connectionBox.getY()+targetConnectionBoxAnchor.getY()-(targetConnectionBoxAnchor.getHeight()/2), connectionBox.getY()+targetConnectionBoxAnchor.getY()+(targetConnectionBoxAnchor.getHeight()/2), y)
			)
			return targetConnectionBoxAnchor;
		
		
		// Calculate the absolute Positions for the 4 default AnchorPoints
		if (isBetween(target.getX()+targetAnchor.getX()-(targetAnchor.getWidth()/2), target.getX()+targetAnchor.getX()+(targetAnchor.getWidth()/2), x)
			&& isBetween(target.getY()+targetAnchor.getY()-(targetAnchor.getHeight()/2), target.getY()+targetAnchor.getY()+(targetAnchor.getHeight()/2), y)
			)
			return targetAnchor;
		
		
		
		
		
		
		// check AnchorPoints between source and connection box
	
		
		AnchorPoint currentPoint = sourceAnchor.getAnchorPoint();
		Anchor a;
		// if we are at the end, than null should be there
		while (currentPoint!=sourceConnectionBoxAnchor.getAnchorPoint())
		{
			a = anchorMap.get(currentPoint);
			
			if (a.hasCoordinate(x, y))
				return a;
			
			currentPoint = currentPoint.getNextAnchorPoint();
		}
		
		
		// check AnchorPoints between connection Box and target
		currentPoint = targetConnectionBoxAnchor.getAnchorPoint();
		
		while (currentPoint!=targetAnchor.getAnchorPoint())
		{
			a = anchorMap.get(currentPoint);
			
			if (a.hasCoordinate(x, y))
				return a;
			
			currentPoint = currentPoint.getNextAnchorPoint();
		}
		
		
		return null;
	}
	
	
	
	public MetaClassDrawable getSourceDrawable()
	{
		return source;
	}
	
	
	public MetaClassDrawable getTargetDrawable()
	{
		return target;
	}
	
	public MetaConnectionBox getConnectionBox()
	{
		return connectionBox;
	}
	
	/**
	 * Get the {@link Anchor} for the searched {@link AnchorPoint}
	 * @param anchorPoint
	 * @return The {@link Anchor} or null (if no {@link Anchor} for the AnchorPoint exists)
	 */
	public Anchor getAnchor(AnchorPoint anchorPoint)
	{
		return anchorMap.get(anchorPoint);
	}

	@Override
	public Point canPlaceableBePlacedAt(double x, double y) {
		
		double offset = 3;
		
		// the left border
		if (isBetween(connectionBox.getX()-offset, connectionBox.getX()+offset,x) && isBetween(connectionBox.getY(), connectionBox.getY()+connectionBox.getHeight(),y))
			return new Point(connectionBox.getX(),y);
		else	// right border
		if (isBetween(connectionBox.getX()+connectionBox.getWidth()-offset, connectionBox.getX()+connectionBox.getWidth()+offset,x) && isBetween(connectionBox.getY(), connectionBox.getY()+connectionBox.getHeight(),y))
			return new Point(connectionBox.getX()+connectionBox.getWidth(),y);
		else // Top Border
		if (isBetween(connectionBox.getX(), connectionBox.getX() + connectionBox.getWidth(), x ) && isBetween(connectionBox.getY()-offset, connectionBox.getY()+offset,y))
			return new Point(x,connectionBox.getY());
		else // bottom border
		if (isBetween(connectionBox.getX(), connectionBox.getX() + connectionBox.getWidth(), x ) && isBetween(connectionBox.getY()+connectionBox.getHeight()-offset, connectionBox.getY()+connectionBox.getHeight()+offset,y))
				return new Point(x,connectionBox.getY()+connectionBox.getHeight());
		
		return null;
		
	}

	@Override
	public void highlightDestinationArea(Context2d context) {
		
		context.save();
		context.setStrokeStyle(destinationAreaHighlightColor);
		context.setLineWidth(3);
		
		context.beginPath();
		context.moveTo(connectionBox.getX(), connectionBox.getY());
		context.lineTo(connectionBox.getX(), connectionBox.getY()+connectionBox.getHeight());
		context.lineTo(connectionBox.getX()+connectionBox.getWidth(), connectionBox.getY()+connectionBox.getHeight());
		context.lineTo(connectionBox.getX()+connectionBox.getWidth(), connectionBox.getY());
		context.lineTo(connectionBox.getX(), connectionBox.getY());
		context.closePath();
		
		context.stroke();
		
		context.restore();
	}

	@Override
	public void highlightOnMouseOverDestinationArea(Context2d context) {
		context.save();
		context.setStrokeStyle(onMouseOverDestinationColor);
		context.setLineWidth(3);
		
		context.beginPath();
		context.moveTo(connectionBox.getX(), connectionBox.getY());
		context.lineTo(connectionBox.getX(), connectionBox.getY()+connectionBox.getHeight());
		context.lineTo(connectionBox.getX()+connectionBox.getWidth(), connectionBox.getY()+connectionBox.getHeight());
		context.lineTo(connectionBox.getX()+connectionBox.getWidth(), connectionBox.getY());
		context.lineTo(connectionBox.getX(), connectionBox.getY());
		context.closePath();
		
		context.stroke();
		
		context.restore();
	}
	
	
	
	/**
	 * Dock an {@link Anchor} to this {@link Drawable}.
	 * This docking is important, to react on resizements.
	 * @param a
	 */
	@Override
	public void dockAnchor(Anchor a)
	{
		if (!dockedAnchors.contains(a))
			dockedAnchors.add(a);
	}
	
	
	/**
	 * Undock an {@link Anchor}
	 * @see #dockAnchor(Anchor)
	 * @param a
	 */
	@Override
	public void undockAnchor(Anchor a)
	{
		dockedAnchors.remove(a);
	}
	
	/**
	 * Get a list with all {@link Anchor}s that are currently docked to this {@link MetaClassDrawable}
	 * @return
	 */
	@Override
	public List<Anchor> getDockedAnchors()
	{
		return dockedAnchors;
	}
	
	
	
	@Override
	public BorderDirection getCoordinatesBorderDirection(double x, double y) {
	
		double offset = 5;
		
		// TOP
		if (isBetween(connectionBox.getX(), connectionBox.getX()+connectionBox.getWidth(), x) && isBetween(connectionBox.getY()-offset, connectionBox.getY()+offset, y))
			return BorderDirection.TOP;
		else
		// BOTTOM
		if (isBetween(connectionBox.getX(), connectionBox.getX()+connectionBox.getWidth(), x) && isBetween(connectionBox.getY() + connectionBox.getHeight()-offset, connectionBox.getY()+connectionBox.getHeight()+offset, y))
			return BorderDirection.BOTTOM;
		else
		// LEFT
		if (isBetween(connectionBox.getY(), connectionBox.getY()+connectionBox.getHeight(), y) && isBetween(connectionBox.getX() -offset, connectionBox.getX() + offset, x))
			return BorderDirection.LEFT;
		else
		// RIGHT
		if (isBetween(connectionBox.getY(), connectionBox.getY()+connectionBox.getHeight(), y) && isBetween(connectionBox.getX() + connectionBox.getWidth() -offset, connectionBox.getX() + connectionBox.getWidth() + offset, x))
			return BorderDirection.RIGHT;
		
		
		return BorderDirection.NOWHERE;
		
	}

	@Override
	public void addIconLoadHandler(IconLoadHandler h) {
		if (!iconHandlers.contains(h))
			iconHandlers.add(h);
	}

	@Override
	public boolean fireIconLoadEvent(IconLoadEvent e) {
		boolean delivered = false;
		
		for (IconLoadHandler h: iconHandlers)
		{
			h.onIconLoaded(e);
			delivered = true;
		}
		
		return delivered;
	}

	@Override
	public void removeIconLoadHanlder(IconLoadHandler h) {
		iconHandlers.remove(h);
	}

}
