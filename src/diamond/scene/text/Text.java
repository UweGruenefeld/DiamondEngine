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
package diamond.scene.text;

import java.awt.Font;

import diamond.util.graphics.complexion.Color;

//TODO Text braucht noch eine Rotation

/**
 * Die Klasse Text ermoeglicht Texte die zwei- oder dreidimensional 
 * platziert werden koennen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Text 
{
	/*
	 * Allgemeine Attribute
	 */
	private String value;
	private TextPosition position;
	private int x, y, z;
	private float scale;
	private Color color;
	private Font font;
	private boolean text3D;
	
	/**
	 * Konstruktor
	 * 
	 * @param value Textinhalt
	 * @param position Textposition
	 */
	public Text( String value, TextPosition position )
	{
		this.setUp( value, position, new Color( 1f, 1f, 1f ), new Font( "Arial", Font.BOLD, 18 ) );
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param value Textinhalt
	 * @param position Textposition
	 * @param color Textfarbe
	 */
	public Text( String value, TextPosition position, Color color )
	{
		this.setUp( value, position, color, new Font( "Arial", Font.BOLD, 18 ) );
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param value Textinhalt
	 * @param position Textposition
	 * @param color Textfarbe
	 * @param font Schriftart
	 */
	public Text( String value, TextPosition position, Color color, Font font )
	{
		this.setUp( value, position, color, font );
	}
	
	/**
	 * Methode setzt die Parameter eines Text-Objekts
	 * 
	 * @param value Textinhalt
	 * @param position Textposition
	 * @param color Textfarbe
	 * @param font Schriftart
	 */
	private void setUp( String value, TextPosition position, Color color, Font font )
	{
		this.value = value;
		this.position = position;
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.scale = .05f;
		this.color = color;
		this.font = font;
		this.text3D = false;
	}
	
	/**
	 * Methode setzt den Textinhalt
	 * 
	 * @param value Textinhalt
	 */
	public void setValue( String value )
	{
		this.value = value;
	}
	
	/**
	 * Methode liefert den Textinhalt
	 * 
	 * @return Liefert den Textinhalt
	 */
	public String getValue()
	{
		return this.value;
	}
	
	/**
	 * Methode setzt die Textposition
	 * Wird nur bei 2D verwendet
	 * 
	 * @param position Textposition
	 */
	public void setPosition( TextPosition position )
	{
		this.position = position;
	}
	
	/**
	 * Methode liefert die TextPosition
	 * Wird nur bei 2D verwendet
	 * 
	 * @return Liefert die TextPosition
	 */
	public TextPosition getPosition()
	{
		return this.position;
	}
	
	/**
	 * Methode setzt die Position des Textes
	 * 
	 * @param x x-Position
	 * @param y y-Position
	 */
	public void set( int x, int y )
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Methode setzt die Position des Textes
	 * 
	 * @param x x-Position
	 * @param y y-Position
	 * @param z z-Position (wird nur bei 3D beachtet)
	 */
	public void set( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Methode setzt die Position des Textes
	 * 
	 * @param x x-Position
	 * @param y y-Position
	 * @param z z-Position (wird nur bei 3D beachtet)
	 * @param scale Skalierung (wird nur bei 3D beachtet)
	 */
	public void set( int x, int y, int z, float scale )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.scale = scale;
	}
	
	/**
	 * Methode setzt die x-Position
	 * 
	 * @param x x-Position
	 */
	public void setX( int x )
	{
		this.x = x;
	}
	
	/**
	 * Methode liefert die x-Position
	 * 
	 * @return Liefert die x-Position
	 */
	public int getX()
	{
		return this.x;
	}
	
	/**
	 * Methode setzt die y-Position
	 * 
	 * @param y y-Position
	 */
	public void setY( int y )
	{
		this.y = y;
	}
	
	/**
	 * Methode liefert die y-Position
	 * 
	 * @return Liefert die y-Position
	 */
	public int getY()
	{
		return this.y;
	}
	
	/**
	 * Methode setzt die x-Position
	 * 
	 * @param z x-Position
	 */
	public void setZ( int z )
	{
		this.z = z;
	}
	
	/**
	 * Methode liefert die z-Position
	 * 
	 * @return Liefert die z-Position
	 */
	public int getZ()
	{
		return this.z;
	}
	
	/**
	 * Methode setzt die Skalierung
	 * 
	 * @param scale Skalierung
	 */
	public void setScale( int scale )
	{
		this.scale = scale;
	}
	
	/**
	 * Methode liefert die Skalierung
	 * 
	 * @return Liefert die Skalierung
	 */
	public float getScale()
	{
		return this.scale;
	}
	
	/**
	 * Methode setzt die Farbe
	 * 
	 * @param color Farbe
	 */
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	/**
	 * Methode liefert die Farbe
	 * 
	 * @return Liefert die Farbe
	 */
	public Color getColor()
	{
		return this.color;
	}
	
	/**
	 * Methode setzt die Schriftart
	 * 
	 * @param font Schriftart
	 */
	public void setFont( Font font )
	{
		this.font = font;
	}
	
	/**
	 * Methode liefert die Schriftart
	 * 
	 * @return Liefert die Schriftart
	 */
	public Font getFont()
	{
		return this.font;
	}
	
	/**
	 * Methode aktiviert 3D-Text
	 */
	public void enableText3D()
	{
		this.text3D = true;
	}
	
	/**
	 * Methode deaktivert 3D-Text
	 */
	public void disableText3D()
	{
		this.text3D = false;
	}
	
	/**
	 * Methode ueberprueft ob es sich um 3D Text handelt
	 * 
	 * @return Liefert true, wenn 3D-Text, sonst false
	 */
	public boolean isText3D()
	{
		return this.text3D;
	}
}
