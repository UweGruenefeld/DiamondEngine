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
package diamond.scene.control;

import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.scene.Mode;
import diamond.scene.Scene;
import diamond.scene.light.Light;
import diamond.scene.node.Node;

/**
 * Die Klasse ShadowControl ermoeglicht es
 * ShadowMapping in einer Szene zu verwenden
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class ShadowControl extends Control
{
	/*
	 * Allgemeine Variablen
	 */
	private Light transmitter;
	private Scene shadowScene;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public ShadowControl( DiamondEngine engine )
	{
		super( engine );

		this.transmitter = null;
		this.shadowScene = new Scene( engine, false );
		this.shadowScene.enable();
		this.shadowScene.setSize( 512, 512 );
		this.shadowScene.setMode( Mode.DEPTH );
		this.shadowScene.setCamera( false );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#enable()
	 */
	@Override
	public void enable()
	{
		super.enable();
		
		if( this.shadowScene != null )
			this.shadowScene.enable();
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#disable()
	 */
	@Override
	public void disable()
	{
		super.disable();
		
		if( this.shadowScene != null )
			this.shadowScene.disable();
	}
	
	/**
	 * Methode liefert die Shadow-Szene
	 * 
	 * @return Liefert die Shadow-Szene
	 */
	public Scene getShadowScene()
	{
		return this.shadowScene;
	}
	
	/**
	 * Methode setzt den Transmitter des Schattens
	 * 
	 * @param transmitter Schatten-Transmitter
	 */
	public void setTransmitter( Light transmitter )
	{
		this.transmitter = transmitter;
	}
	
	/**
	 * Methode liefert den Transmitter des Schattens
	 * 
	 * @return Liefert den Transmitter des Schattens
	 */
	public Light getTransmitter()
	{
		return this.transmitter;
	}
	
	/**
	 * Methode fuegt einen Occuper hinzu
	 * 
	 * @param node Occuper
	 */
	public void addOccuper( Node node )
	{
		if( this.shadowScene == null )
			return;
		
		this.shadowScene.add( node );
	}
	
	/**
	 * Methode entfernt einen Occuper
	 * 
	 * @param node Occuper
	 */
	public void removeOccuper( Node node )
	{
		if( this.shadowScene == null )
			return;
		
		this.shadowScene.remove( node );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#init(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void init( GLAutoDrawable drawable, Scene scene ) 
	{
		if( this.shadowScene != null )
			this.getEngine().getRenderManager().add( this.shadowScene );
		
		this.display( drawable, scene );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#display(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void display( GLAutoDrawable drawable, Scene scene ) 
	{
		if( this.shadowScene == null || this.transmitter == null )
			return;

		this.shadowScene.setCameraMatrix( this.transmitter.getLightModelviewMatrix() );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#dispose(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void dispose( GLAutoDrawable drawable, Scene scene ) 
	{
	}
}