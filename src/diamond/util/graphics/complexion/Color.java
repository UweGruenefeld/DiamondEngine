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
package diamond.util.graphics.complexion;

import javax.media.opengl.GL2;

/**
 * ADT-Klasse zum Verwalten von Farben
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Color 
{
	/*
	 * Variablen zum Speichern der Farben
	 */
	private float red, green, blue;
	private float alpha;

	/**
	 * Default-Konstruktor
	 */
	public Color()
	{
		this.red = 0f;
		this.green = 0f;
		this.blue = 0f;
		this.alpha = 1f;
	}
	
	/**
	 * Konstruktor
	 * Erzeugt die Farbe aus float-Werten, dabei ist 1 Maximum und 0 Minimum
	 * 
	 * @param red Rot-Anteil
	 * @param green Gruen-Anteil
	 * @param blue Blau-Anteil
	 */
	public Color( float red, float green, float blue ) 
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = 1f;
	}
	
	/**
	 * Konstruktor
	 * Erzeugt die Farbe aus float-Werten, dabei ist 1 Maximum und 0 Minimum
	 * 
	 * @param red Rot-Anteil
	 * @param green Gruen-Anteil
	 * @param blue Blau-Anteil
	 * @param alpha Alpha-Anteil
	 */
	public Color( float red, float green, float blue, float alpha ) 
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	/**
	 * Konstruktor
	 * Erzeugt die Farbe aus int-Werten, dabei ist 255 Maximum und 0 Minimum
	 * 
	 * @param red Rot-Anteil
	 * @param green Gruen-Anteil
	 * @param blue Blau-Anteil
	 */
	public Color( int red, int green, int blue ) 
	{
		this.red = red / 255f;
		this.green = green / 255f;
		this.blue = blue / 255f;
		this.alpha = 1f;
	}
	
	/**
	 * Konstruktor
	 * Erzeugt die Farbe aus int-Werten, dabei ist 255 Maximum und 0 Minimum
	 * der alpha-Wert ist ein float-Wert mit 1 als Maxmimum
	 * 
	 * @param red Rot-Anteil
	 * @param green Gruen-Anteil
	 * @param blue Blau-Anteil
	 * @param alpha Alpha-Anteil
	 */
	public Color( int red, int green, int blue, float alpha ) 
	{
		this.red = red / 255f;
		this.green = green / 255f;
		this.blue = blue / 255f;
		this.alpha = alpha;
	}
	
	/**
	 * Konstruktor
	 * Erzeugt eine Farbe aus einem statischen Wert
	 * 
	 * @param color Gibt den statischen Wert an
	 */
	public Color( DefaultColor color )
	{
		this.alpha = 1f;
		
		switch( color )
		{
			case WHITE:
				this.red = 1f;
				this.green = 1f;
				this.blue = 1f;
				break;
			case BLACK:
				this.red = 0f;
				this.green = 0f;
				this.blue = 0f;
				break;
			case RED:
				this.red = 1f;
				this.green = 0f;
				this.blue = 0f;
				break;
			case GREEN:
				this.red = 0f;
				this.green = 1f;
				this.blue = 0f;
				break;
			case BLUE:
				this.red = 0f;
				this.green = 0f;
				this.blue = 1f;
				break;
			case MAGENTA:
				this.red = 1f;
				this.green = 0f;
				this.blue = 1f;
				break;
			case CYAN:
				this.red = 0f;
				this.green = 1f;
				this.blue = 1f;
				break;
			case YELLOW:
				this.red = 1f;
				this.green = 1f;
				this.blue = 0f;
				break;
		}
	}
	
	/**
	 * Methode zum Verwenden der Farbe
	 * 
	 * @param gl Benoetigt GL2
	 */
	public void use( GL2 gl )
	{
		gl.glColor4f( this.red, this.green, this.blue, this.alpha );
	}
	
	/**
	 * Liefert den Rot-Anteil
	 * 
	 * @return Liefert den Rot-Anteil
	 */
	public float getRed()
	{
		return this.red;
	}

	/**
	 * Setzt den Rot-Anteil
	 * Maximum 1, Minimum 0
	 * 
	 * @param red Rot-Anteil
	 */
	public void setRed( float red )
	{
		this.red = red;
	}
	
	/**
	 * Setzt den Rot-Anteil
	 * Maximum 255, Minimum 0
	 * 
	 * @param red Rot-Anteil
	 */
	public void setRed( int red )
	{
		this.red = red / 255f;
	}

	/**
	 * Liefert den Gruen-Anteil
	 * 
	 * @return Liefert den Gruen-Anteil
	 */
	public float getGreen() 
	{
		return this.green;
	}

	/**
	 * Liefert den Gruen-Anteil
	 * Maximum 1, Minimum 0
	 * 
	 * @param green Gruen-Anteil
	 */
	public void setGreen( float green ) 
	{
		this.green = green;
	}
	
	/**
	 * Setzt den Gruen-Anteil
	 * Maximum 255, Minimum 0
	 * 
	 * @param green Gruen-Anteil
	 */
	public void setGreen( int green ) 
	{
		this.green = green / 255f;
	}

	/**
	 * Liefert den Blau-Anteil
	 * 
	 * @return Liefert den Blau-Anteil
	 */
	public float getBlue() 
	{
		return this.blue;
	}

	/**
	 * Setzt den Blau-Anteil
	 * Maximum 1, Minimum 0
	 * 
	 * @param blue Blau-Anteil
	 */
	public void setBlue( float blue )
	{
		this.blue = blue;
	}
	
	/**
	 * Setzt den Blau-Anteil
	 * Maximum 255, Minimum 0
	 * 
	 * @param blue Blau-Anteil
	 */
	public void setBlue( int blue )
	{
		this.blue = blue / 255f;
	}
	
	/**
	 * Liefert den Alpha-Anteil
	 * 
	 * @return Liefert den Alpha-Anteil
	 */
	public float getAlpha() 
	{
		return this.alpha;
	}

	/**
	 * Setzt den Alpha-Anteil
	 * Maximum 1, Minimum 0
	 * 
	 * @param alpha Alpha-Anteil
	 */
	public void setAlpha( float alpha )
	{
		this.alpha = alpha;
	}
}
