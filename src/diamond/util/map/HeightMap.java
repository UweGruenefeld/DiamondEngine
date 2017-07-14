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
package diamond.util.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * ADT-Klasse zum Verwalten einer Height Map
 * Dabei sollten die Rot-, Gruen- und Blau-Anteile gleich sein
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenfeld
 */
public class HeightMap 
{
	/*
	 * Variablen zum Speichern der Height Map
	 */
	private final float[][] heightMap;
	private final float maxValue;
	private final float minValue;
	
	/**
	 * Konstruktor
	 * Erzeugt die HeightMap aus dem InputStream
	 * 
	 * @param stream InputStream aus dem die Height Map erzeugt wird
	 * @param maxValue Gibt den maximalen Wert der Height Map an
	 */
	public HeightMap( InputStream stream, float maxValue )
	{
		this.maxValue = Math.abs( maxValue );
		this.minValue = 0;
		
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read( stream );
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
		
		if( image == null )
		{
			this.heightMap = new float[0][0];	
		}
		else
		{
			this.heightMap = new float[ image.getWidth() ][ image.getHeight() ];
			
			for( int x=0; x < this.heightMap.length; x++ )
			{
				for( int y=0; y < this.heightMap[x].length; y++ )
				{
					this.heightMap[x][y] = this.getHeight( image.getRGB( x, y ));
				}
			}
		}
	}
	
	/**
	 * Liefert die Hoehe anhand der Farbe
	 * 
	 * @param color Farbe die in Hoehenwert umgerechnet werden soll
	 * @return Liefert die Hoehe zu der Farbe
	 */
	private float getHeight( int color )
	{
		Color c = new Color( color );
		return ( c.getRed() + c.getGreen() + c.getBlue() ) / ( 3.0f * 255 );
	}
	
	/**
	 * Liefert die Hoehe an der angegebenen Stelle
	 * 
	 * @param x x-Wert
	 * @param y y-Wert
	 * @return Liefert die Hoehe
	 */
	public float get( int x, int y )
	{
		// Gibt es den gewuenschten Wert?
		if( this.heightMap == null || x < 0 || y < 0 || this.heightMap.length <= x || this.heightMap[x].length <= y )
			return 0;
		
		return this.heightMap[x][y] * this.maxValue;
	}
	
	/**
	 * Liefert die Breite der Height Map
	 * 
	 * @return Liefert die Breite der Height Map
	 */
	public int getWidth()
	{
		if( this.heightMap != null )
		{
			return this.heightMap.length;
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * Liefert die Hoehe der Height Map
	 * 
	 * @return Liefert die Hoehe der Height Map
	 */
	public int getHeight()
	{
		if( this.heightMap.length >= 1 )
			return this.heightMap[0].length;

		return 0;
	}
	
	/**
	 * Liefert den maximalen Wert der Height Map
	 * 
	 * @return Liefert den maximalen Wert der Height Map
	 */
	public float getMaxValue()
	{
		return this.maxValue;
	}
	
	/**
	 * Liefert den minimalen Wert der Height Map
	 * 
	 * @return Liefert den minimalen Wert der Height Map
	 */
	public float getMinVaue()
	{
		return this.minValue;
	}
}