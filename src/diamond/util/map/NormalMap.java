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

import diamond.util.math.Vector;

/**
 * ADT-Klasse zum Verwalten einer Normal Map
 * Dabei sollte der Rot-Wert der x-Wert sein, der Gruen-Wert sollte der y-Wert sein,
 * der z-Wert sollte der Blau-Wert sein
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenfeld
 */
public class NormalMap 
{
	/*
	 * Variablen zum Speichern der Normal Map
	 */
	private float[][][] normalMap;
	
	/**
	 * Konstruktor
	 * Erzeugt eine Normal Map aus einer Height Map
	 * 
	 * @param heightMap Height Map die zur Erzeugung verwendet werden soll
	 */
	public NormalMap( HeightMap heightMap )
	{
		this.normalMap = new float[ heightMap.getWidth() ][ heightMap.getHeight() ][3];
		
		for( int x=1; x < heightMap.getWidth() - 1; x++ )
		{
			for( int y=1; y < heightMap.getHeight() - 1; y++ )
			{
				Vector center = new Vector( x, heightMap.get( x, y ), y );
				Vector top = null, bottom = null, left = null, right = null, topBottom = null, leftRight = null, result = null;
				
				top = center.crossProduct( new Vector( x, heightMap.get( x, y ), y + 1 )).normalize();
				bottom = center.crossProduct( new Vector( x, heightMap.get( x, y ), y - 1 )).normalize();
				left = center.crossProduct( new Vector( x - 1, heightMap.get( x, y ), y )).normalize();
				right = center.crossProduct( new Vector( x + 1, heightMap.get( x, y ), y )).normalize();
					
				topBottom = top.crossProduct( bottom ).normalize();
				leftRight = left.crossProduct( right ).normalize();

				result = topBottom.crossProduct( leftRight ).normalize();
				
				this.set( x, y, result );
			}
		}
		
		for( int x=0; x < heightMap.getWidth(); x += heightMap.getWidth() - 1 )
		{
			for( int y=0; y < heightMap.getHeight(); y += heightMap.getHeight() - 1 )
			{
				if( x == 0 )
					this.copy( x + 1, y, x, y );
				if( x == heightMap.getWidth() - 1 )
					this.copy( x - 1, y, x, y );
				if( y == 0 )
					this.copy( x, y + 1, x, y );
				if( y == heightMap.getHeight() - 1 )
					this.copy( x, y - 1, x, y );
			}
		}
	}
	
	/**
	 * Methode erlaubt das Setzen einzelner Vektoren der Normal Map
	 * 
	 * @param x x-Wert
	 * @param y y-Wert
	 * @param vector Vektor der gesetzt wird
	 */
	public void set( int x, int y, Vector vector )
	{
		if( vector.getSize() < 3 )
			return;
		
		for( int i=0; i < 3; i++ )
		{
			this.normalMap[ x ][ y ][ i ] = vector.get( i );
		}
	}
	
	/**
	 * Methode erlaubt es Werte in der Normal Map umzukopieren
	 * 
	 * @param srcX Quellen x-Wert
	 * @param srcY Quellen y-Wert
	 * @param destX Ziel x-Wert
	 * @param destY Ziel y-Wert
	 */
	public void copy( int srcX, int srcY, int destX, int destY )
	{
		for( int i=0; i < 3; i++ )
		{
			this.normalMap[ destX ][ destY ][ i ] = this.normalMap[ srcX ][ srcY ][ i ];
		}
	}
	
	/**
	 * Liefert einen Vekotr mit dem Wert der Normal Map an einer Stelle
	 * 
	 * @param x x-Wert
	 * @param y y-Wert
	 * @return Liefert den Vektor
	 */
	public Vector get( int x, int y )
	{
		return new Vector( this.normalMap[x][y][0], this.normalMap[x][y][1], 
				this.normalMap[x][y][2] );
	}
}