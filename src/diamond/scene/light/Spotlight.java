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
package diamond.scene.light;

import javax.media.opengl.GL2;

import diamond.util.math.Vector;

/**
 * Die Klasse Spotlight ermoeglicht ein Spotlight einzusetzen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Spotlight extends Light
{
	/*
	 * Allgemeine Attribute
	 */
	private Vector direction;
	private float cutOff, exponent;
	
	/**
	 * Default-Konstruktor
	 */
	public Spotlight()
	{
		this.direction = new Vector( 0, 0, 0, 1 );
		this.cutOff = 25;
		this.exponent = 50;
	}
	
	/**
	 * Methode setzt die Richtung des Spotlights
	 * 
	 * @param x x-Richtung
	 * @param y y-Richtung
	 * @param z z-Richtung
	 */
	public void setDirection( float x, float y, float z )
	{
		this.direction = new Vector( x, y, z, 1 );
	}
	
	/**
	 * Methode liefert die Richtung des Spotlights
	 * 
	 * @return Liefert die Richtung des Spotlights
	 */
	public Vector getDirection()
	{
		return this.direction;
	}
	
	/**
	 * Methode setzt den CutOff des Spotlight
	 * 
	 * @param cutOff CutOff des Spotlight
	 */
	public void setCutOff( float cutOff )
	{
		this.cutOff = cutOff;
	}
	
	/**
	 * Methode liefert den CutOff des Spotlight
	 * 
	 * @return Liefert den CutOff des Spotlight
	 */
	public float getCutOff()
	{
		return this.cutOff;
	}
	
	/**
	 * Methode setzt den Exponenten des Spotlight
	 * 
	 * @param exponent Exponent
	 */
	public void setExponent( float exponent )
	{
		this.exponent = exponent;
	}
	
	/**
	 * Methode liefert den Exponent des Spotlight
	 * 
	 * @return Liefert den Exponent des Spotlight
	 */
	public float getExponent()
	{
		return this.exponent;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.light.Light#setPosition(float, float, float)
	 */
	@Override
	public void setPosition( float x, float y, float z ) 
	{
		this.position = new Vector( x, y, z, 1 );
		this.refresh = true;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.light.Light#display(javax.media.opengl.GL2, int)
	 */
	@Override
	public void display( GL2 gl, int i )
	{
		super.display( gl, i );
		
		gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_SPOT_DIRECTION, this.direction.toArray(), 0 );	
		gl.glLightf( GL2.GL_LIGHT2, GL2.GL_SPOT_CUTOFF, this.cutOff );
		gl.glLightf( GL2.GL_LIGHT2, GL2.GL_SPOT_EXPONENT, this.exponent );
	}
}