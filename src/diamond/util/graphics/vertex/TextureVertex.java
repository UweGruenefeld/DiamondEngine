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
 * ADT-Klasse zum Verwalten von Texture Vertices
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class TextureVertex
{
	/*
	 * Variablen zum Speichern der Texture Vertexdaten
	 */
	private float u, v, w;

	/**
	 * Konstruktor
	 * ein-dimensionaler Texture Vertex
	 * 
	 * @param u u-Wert
	 */
	public TextureVertex( float u )
	{
		this.setValue( u, 0f, 0f );
	}
	
	/**
	 * Konstruktor
	 * zwei-dimensionaler Texture Vertex
	 * 
	 * @param u u-Wert
	 * @param v v-Wert
	 */
	public TextureVertex( float u, float v )
	{
		this.setValue( u, v, 0f );
	}
	
	/**
	 * Konstruktor
	 * drei-dimensionaler Texture Vertex
	 * 
	 * @param u u-Wert
	 * @param v v-Wert
	 * @param w w-Wert
	 */
	public TextureVertex( float u, float v, float w )
	{
		this.setValue( u, v, w );
	}
		
	/**
	 * Methode setzt den Wert des Texture Vertex
	 * 
	 * @param u u-Wert
	 * @param v v-Wert
	 * @param w w-Wert
	 */
	public void setValue( float u, float v, float w )
	{
		this.u = u;
		this.v = v;
		this.w = w;
	}
	
	/**
	 * Methode liefert den u-Wert
	 * 
	 * @return Liefert den u-Wert
	 */
	public  float getU()
	{
		return this.u;
	}
	
	/**
	 * Methode liefert den v-Wert
	 * 
	 * @return Liefert den v-Wert
	 */
	public float getV()
	{
		return this.v;
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