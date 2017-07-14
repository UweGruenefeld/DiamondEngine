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
package diamond.util.graphics.vertex;

/**
 * ADT-Klasse zum Verwalten von Vertices
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Vertex 
{
	/*
	 * Variablen zum Speichern der Vertexdaten
	 */
	private float x, y, z, w;

	/**
	 * Konstruktor
	 * Erzeugt den Vertex mit Hilfe der x-, y- und z-Werte 
	 * 
	 * @param x x-Wert
	 * @param y y-Wert
	 * @param z z-Wert
	 */
	public Vertex( float x, float y, float z )
	{
		this.setValue( x, y, z, 1f );
	}
	
	/**
	 * Konstruktor
	 * Erzeugt den Vertex mit Hilfe der x-, y-, z- und w-Werte
	 * 
	 * @param x x-Wert
	 * @param y y-Wert
	 * @param z z-Wert
	 * @param w w-Wert
	 */
	public Vertex( float x, float y, float z, float w )
	{
		this.setValue( x, y, z, w );
	}
		
	/**
	 * Methode setzt die Werte
	 * 
	 * @param x	x-Wert
	 * @param y y-Wert
	 * @param z z-Wert
	 * @param w w-Wert
	 */
	public void setValue( float x, float y, float z, float w )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * Methode liefert den x-Wert
	 * 
	 * @return Liefert den x-Wert
	 */
	public float getX()
	{
		return this.x;
	}
	
	/**
	 * Methode liefert den y-Wert
	 * 
	 * @return Liefert den y-Wert
	 */
	public float getY()
	{
		return this.y;
	}
	
	/**
	 * Methode liefert den z-Wert
	 * 
	 * @return Liefert den z-Wert
	 */
	public float getZ()
	{
		return this.z;
	}
	
	/**
	 * Methode liefert den w-Wert
	 * 
	 * @return Liefert den w-Wert
	 */
	public float getW()
	{
		return this.w;
	}
}