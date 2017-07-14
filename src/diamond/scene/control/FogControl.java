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
package diamond.scene.control;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.scene.Scene;
import diamond.util.graphics.complexion.Color;
import diamond.util.graphics.complexion.DefaultColor;

/**
 * Die Klasse FogControl bietet eine Nebel-Funktionalitaet fuer eine Szene
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class FogControl extends Control
{
	/*
	 * Allgemeine Attribute
	 */
	private Color color;
	private float density, start, end;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public FogControl( DiamondEngine engine )
	{
		super( engine );
		
		this.color = new Color( DefaultColor.BLACK );
		
		this.density = 0.1f;
		this.start = 15f;
		this.end = 15f;
	}
	
	/**
	 * Methode setzt die Nebelfarbe
	 * 
	 * @param color Nebelfarbe
	 */
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	/**
	 * Methode liefert die Nebelfarbe
	 * 
	 * @return Liefert die Nebelfarbe
	 */
	public Color getColor()
	{
		return this.color;
	}
	
	/**
	 * Methode setzt die Density
	 * 
	 * @param density Density
	 */
	public void setDensity( float density )
	{
		this.density = density;
	}
	
	/**
	 * Methode liefert die Density
	 * 
	 * @return Density
	 */
	public float getDensity()
	{
		return this.density;
	}
	
	/**
	 * Methode setzt den Startwert des Nebels
	 * 
	 * @param start Startwert fuer den Nebel
	 */
	public void setStart( float start )
	{
		this.start = start;
	}
	
	/**
	 * Methode liefert den Startwert fuer den Nebel
	 * 
	 * @return Liefert den Startwert fuer den Nebel
	 */
	public float getStart()
	{
		return this.start;
	}
	
	/**
	 * Methode setzt den Endwert des Nebels
	 * 
	 * @param end Endwert des Nebels
	 */
	public void setEnd( float end )
	{
		this.end = end;
	}
	
	/**
	 * Methode liefert den Endwert des Nebels
	 * 
	 * @return Liefert den Endwert des Nebels
	 */
	public float getEnd()
	{
		return this.end;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#init(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void init( GLAutoDrawable drawable, Scene scene ) 
	{
		display( drawable, scene );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#display(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void display( GLAutoDrawable drawable, Scene scene ) 
	{
		GL2 gl = drawable.getGL().getGL2();
		
		if( !this.isEnabled() )
		{
			gl.glDisable( GL2.GL_FOG );
			gl.glClearColor( 0f, 0f, 0f, 1f );
			return;
		}
		
		gl.glEnable( GL2.GL_FOG );
		
		gl.glClearColor( this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha() );
		gl.glFogfv( GL2.GL_FOG_COLOR, new float[]{ this.color.getRed(), this.color.getGreen(), 
				this.color.getBlue(), this.color.getAlpha() }, 0 );
		
		gl.glFogf( GL2.GL_FOG_DENSITY, this.density );
		gl.glFogf( GL2.GL_FOG_START, this.start );
		gl.glFogf( GL2.GL_FOG_END, this.end );
		gl.glFogi( GL2.GL_FOG_MODE, GL2.GL_EXP );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#dispose(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void dispose( GLAutoDrawable drawable, Scene scene ) 
	{
	}
}