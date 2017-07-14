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
package diamond.manager;

import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;

/**
 * Die abstrakte Klasse AbstractManager ist die Vorschrift bzw. Oberklasse
 * fuer alle Manager-Klassen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public abstract class AbstractManager 
{
	/*
	 * Referenz auf die Engine
	 */
	private DiamondEngine engine;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public AbstractManager( DiamondEngine engine )
	{
		this.engine = engine;
	}
	
	/**
	 * Methode liefert eine Referenz auf die Engine
	 * 
	 * @return Liefert eine Referenz auf die Engine
	 */
	public DiamondEngine getEngine()
	{
		return this.engine;
	}
	
	/**
	 * Methode initiert den Manager
	 * 
	 * @param drawable GLAutoDrawable
	 */
	public abstract void init( GLAutoDrawable drawable );
	
	/**
	 * Methode fordert den Manager auf, seinen Inhalt
	 * darzustellen
	 * 
	 * @param drawable GLAutoDrawable
	 */
	public abstract void display( GLAutoDrawable drawable );
	
	/**
	 * Methode zerstoert den Manager
	 * 
	 * @param drawable GLAutoDrawable
	 */
	public abstract void dispose( GLAutoDrawable drawable );
}
