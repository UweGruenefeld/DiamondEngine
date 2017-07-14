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
package diamond.util.graphics.polygon;

import diamond.DiamondEngine;
import diamond.util.Parser;

/**
 * ADT-Klasse zum Verwalten von Faces
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Face 
{
	/*
	 * Variablen zum Speichern des Faces
	 */
	private final int vertices[], textureVertices[], vertexNormals[];
	
	/**
	 * Konstruktor
	 * Erzeugt ein neuen Face anhand der Angaben in dem String
	 * Beispiel-String-Arrray: "v/vt/vn", "v/vt/vn", ...
	 * 
	 * @param engine Referenz auf die Engine
	 * @param face Face als String
	 */
	public Face( DiamondEngine engine, String... face )
	{
		this.vertices = new int[ face.length ];
		this.textureVertices = new int[ face.length ];
		this.vertexNormals = new int[ face.length ];
		
		for( int i=0; i < face.length; i++ )
		{
			String vertex[] = face[i].split( "/" );
			
			this.vertices[i] = vertex.length > 0 ? Parser.parseInt( engine, vertex[0] ) : 0;
			this.textureVertices[i] = vertex.length > 1 ? Parser.parseInt( engine, vertex[1] ) : 0;
			this.vertexNormals[i] = vertex.length > 2 ? Parser.parseInt( engine, vertex[2] ) : 0;
		}
	}
	
	/**
	 * Liefert die Vertices des Faces
	 * 
	 * @return Liefert die Vertices des Faces
	 */
	public int[] getVertices()
	{
		return this.vertices;
	}
	
	/**
	 * Liefert die Texturekoordinaten des Faces
	 * 
	 * @return Liefert due Texturekoorsdinaten des Faces
	 */
	public int[] getTextureVertices()
	{
		return this.textureVertices;
	}
	
	/**
	 * Liefert die Normalen des Faces
	 * 
	 * @return Liefert die Normalen des Faces
	 */
	public int[] getVertexNormals()
	{
		return this.vertexNormals;
	}
}