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

import java.util.HashSet;
import java.util.Iterator;

import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.GlobalState;
import diamond.manager.plugin.Plugin;

/**
 * Die Klasse PluginManager verwaltet die Plugins der Engine
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class PluginManager extends AbstractManager
{
	/*
	 * Speichert die Plugins ab
	 */
	private HashSet<Plugin> plugin;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public PluginManager( DiamondEngine engine )
	{
		super( engine );
		
		this.plugin = new HashSet<Plugin>();
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#init(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void init( GLAutoDrawable drawable )
	{
		Iterator<Plugin> iterator = this.plugin.iterator();
		while( iterator.hasNext() )
		{
			iterator.next().init( drawable );
		}
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#display(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void display( GLAutoDrawable drawable ) 
	{
		Iterator<Plugin> iterator = this.plugin.iterator();
		while( iterator.hasNext() )
		{
			iterator.next().display( drawable );
		}
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#dispose(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void dispose( GLAutoDrawable drawable ) 
	{
		Iterator<Plugin> iterator = this.plugin.iterator();
		while( iterator.hasNext() )
		{
			iterator.next().dispose( drawable );
		}
	}
	
	// TODO Methode darf nur Klasse uebergeben bekommen, nicht Objekt
	/**
	 * Methode zum Hinzufuegen eines Plugins
	 * 
	 * @param plugin Plugin das hinzugefuegt werden soll
	 */
	public void add( Plugin plugin )
	{
		if( this.getEngine().getState() == GlobalState.INITIATED || 
				this.getEngine().getState() == GlobalState.UNDEFINED )
			this.plugin.add( plugin );
	}
}