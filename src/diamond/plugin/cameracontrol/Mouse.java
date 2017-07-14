/**
 * Copyright (c)2013 Diamond Engine
 * All rights reserved
 * 
 * Distributed under the BSD-Lizenz
 * Veroeffentlicht unter der BSD-Lizenz
 * 
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, 
 * mit oder ohne Veraenderung, sind unter den folgenden Bedingungen zulaessig:
 * 	1.Weiterverbreitete nichtkompilierte Exemplare muessen das obige Copyright, 
 * 	  diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 	2.Weiterverbreitete kompilierte Exemplare muessen das obige Copyright, 
 * 	  diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation 
 * 	  und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 	3.Alle Werbematerialien, die Eigenschaften oder die Benutzung erwaehnen, 
 * 	  muessen die folgende Bemerkung enthalten: "Dieses Produkt enthaelt Software, 
 * 	  die von der Diamond Engine und Beitragsleistenden entwickelt wurde."
 * 	4.Weder der Name der Engine noch die Namen der Beitragsleistenden duerfen zum Kennzeichnen 
 * 	  oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, 
 * 	  ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * 
 * DIESE SOFTWARE WIRD VON DEN DIAMOND ENGINE ENTWICKLERN UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE 
 * SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFUEGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: 
 * DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FUER EINEN BESTIMMTEN ZWECK. 
 * AUF KEINEN FALL SIND DIE ENTWICKLER ODER DIE BEITRAGSLEISTENDEN FUER IRGENDWELCHE DIREKTEN, 
 * INDIREKTEN, ZUFAELLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHAEDEN (UNTER ANDEREM VERSCHAFFEN 
 * VON ERSATZGUETERN ODER -DIENSTLEISTUNGEN; EINSCHRAENKUNG DER NUTZUNGSFAEHIGKEIT; 
 * VERLUST VON NUTZUNGSFAEHIGKEIT; DATEN; PROFIT ODER GESCHAEFTSUNTERBRECHUNG), 
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, 
 * STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLAESSIGKEIT) VERANTWORTLICH, 
 * AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, 
 * WENN SIE AUF DIE MOEGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 */
package diamond.plugin.cameracontrol;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import diamond.DiamondEngine;

/**
 * Die Klasse Mouse ist Bestandteil des CameraControlPlugins
 * Sie regelt die Steuerung der Kamera ueber die Maus
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Mouse implements MouseMotionListener, MouseListener, MouseWheelListener 
{
	/*
	 * Referenz auf die Engine
	 */
	private final DiamondEngine engine;
	
	/*
	 * Allgemeine Attribute
	 */
	private int mouseButtonClicked;
	private int lastMouseX, lastMouseY;
	private float turnHorizontal, turnVertical;
		
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Mouse( DiamondEngine engine ) 
	{ 
		this.engine = engine;
			
		mouseButtonClicked = MouseEvent.NOBUTTON;
			
		this.lastMouseX = -1;
		this.lastMouseY = -1;
		this.turnHorizontal = 0;
		this.turnVertical = 0;
			
	   	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
	   	// Setze die Maus auf die Mitte des Bildschirms
		try 
		{
			Robot robot;
			robot = new Robot();
			robot.mouseMove( screenSize.width / 2, screenSize.height / 2 );		
		} 
		catch( AWTException exc ) 
		{
			exc.printStackTrace();
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged( MouseEvent event ) 
	{ 
		if( this.lastMouseX == -1 || this.lastMouseY == -1 ) 
		{
			this.lastMouseX = event.getX();
			this.lastMouseY = event.getY();
			return;
		}
			
		// Horizontal
		if( this.mouseButtonClicked == MouseEvent.BUTTON1 )
		{
			if( this.lastMouseX != event.getX() )
				this.turnHorizontal += this.lastMouseX - event.getX();
			
			if( this.turnHorizontal > 3 || this.turnHorizontal < -3 )
			{
				this.engine.getDefaultView().setRotateYAnimation(( this.turnHorizontal / -100f ));
			}
			else
			{
				this.engine.getDefaultView().setRotateYAnimation( 0f );
				this.turnHorizontal = 0;
			}
		}
			
		// Vertical
		if( this.mouseButtonClicked == MouseEvent.BUTTON3 )
		{
			if( this.lastMouseY != event.getY() )
				this.turnVertical += this.lastMouseY - event.getY();
			
			if( this.turnVertical > 3 || this.turnVertical < -3 )
			{
				this.engine.getDefaultView().setRotateXAnimation(( this.turnVertical / 100f ));
			}
			else
			{
				this.engine.getDefaultView().setRotateXAnimation( 0f );
				this.turnVertical = 0;
			}
		}

		this.lastMouseX = event.getX();
		this.lastMouseY = event.getY();
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved( MouseEvent event ) 
	{ 
		int height = this.engine.getCanvas().getSize().height;
		int width = this.engine.getCanvas().getSize().width;
		float h = height / 4f;
		float w = width / 4f;
		int tmpHeight = Math.abs( height - event.getY());	
		int tmpWidth = Math.abs( width - event.getX());
			
		if( event.getX() < w )
			this.engine.getDefaultView().setTranslateXAnimation(( w - event.getX() ) / w * -2 );
		else if( tmpWidth < w )
			this.engine.getDefaultView().setTranslateXAnimation(( w - tmpWidth ) / w * 2 );
		else
			this.engine.getDefaultView().setTranslateXAnimation( 0f );
			
		if( event.getY() < h )			
			this.engine.getDefaultView().setTranslateZAnimation(( h - event.getY() ) / h * 2 );
		else if( tmpHeight < h )
			this.engine.getDefaultView().setTranslateZAnimation(( h - tmpHeight ) / h * -2 );
		else
			this.engine.getDefaultView().setTranslateZAnimation( 0f );
			
		this.lastMouseX = event.getX();
		this.lastMouseY = event.getY();
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked( MouseEvent event ) 
	{ 
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered( MouseEvent event ) 
	{
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited( MouseEvent event )
	{
		this.engine.getDefaultView().setTranslateXAnimation( 0f );
		this.engine.getDefaultView().setTranslateZAnimation( 0f );
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed( MouseEvent event ) 
	{
		this.mouseButtonClicked = event.getButton();
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased( MouseEvent event ) 
	{ 
		this.mouseButtonClicked = MouseEvent.NOBUTTON;
			
		this.engine.getDefaultView().setRotateXAnimation( 0f );
		this.engine.getDefaultView().setRotateYAnimation( 0f );
		this.engine.getDefaultView().setRotateZAnimation( 0f );
		this.turnHorizontal = 0;
		this.turnVertical = 0;
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved( MouseWheelEvent event ) 
	{ 
		int notches = event.getWheelRotation();
		this.engine.getDefaultView().setTranslateY( notches / 2 );
	}
}