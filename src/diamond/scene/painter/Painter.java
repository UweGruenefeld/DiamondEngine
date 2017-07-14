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
package diamond.scene.painter;

import javax.media.opengl.GL2;

import diamond.DiamondEngine;
import diamond.scene.Scene;

/**
 * Die Klasse Painter dient dem Engine-Verwender als 
 * Ausgangspunkt zum Darstellen von Grafik
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public abstract class Painter
{
	/*
	 * Referenz auf die Engine
	 */
	DiamondEngine engine;
	
	/*
	 * Allgemeine Attribute
	 */
	private boolean running;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Painter( DiamondEngine engine )
	{
		this.engine = engine;
		
		this.running = false;
	}
	
	/**
	 * Methode erlaubt den Aufruf der display-Methode in jedem Frame
	 */
	public void run()
	{
		this.running = true;
	}
	
	/**
	 * Methode unterbindet den Aufruf der display-Methode in jedem Frame
	 */
	public void stop()
	{
		this.running = false;
	}
	
	/**
	 * Methode dient zum Initialisieren des Painters
	 * 
	 * @param gl GL2
	 */
	public abstract void init( GL2 gl, Scene scene );
	
	/**
	 * Methode dient dem Aktualisieren des Painters
	 * 
	 * @param gl GL2
	 * @param factor Zeit in Millisekunden seit dem letzten Aufruf
	 */
	public void display( GL2 gl, Scene scene, long factor )
	{
		if( this.running )
			this.update( gl, scene, factor );		
	}
	
	public abstract void update( GL2 gl, Scene scene, long factor );
	
	/**
	 * Methode dient dem Zerstoeren des Painters
	 * 
	 * @param gl GL2
	 */
	public abstract void dispose( GL2 gl, Scene scene );
	
	/**
	 * Methode liefert eine Referenz auf die Engine
	 * 
	 * @return Liefert eine Referenz auf die Engine
	 */
	public DiamondEngine getEngine()
	{
		return this.engine;
	}
}