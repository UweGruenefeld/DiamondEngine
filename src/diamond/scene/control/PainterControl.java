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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.logger.LogLevel;
import diamond.scene.Scene;
import diamond.scene.painter.Painter;

/**
 * Die Klasse PainterManager verwaltet den Zugriff
 * auf die Painter
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class PainterControl extends Control
{
	/*
	 * Variablen zum Speichern der Painter
	 */
	private HashSet<Painter> painter;
	private Stack<Painter> initPainter;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public PainterControl( DiamondEngine engine )
	{
		super( engine );
		
		this.painter = new HashSet<Painter>();
		this.initPainter = new Stack<Painter>();
		
		this.enable();
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#init(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void init( GLAutoDrawable drawable, Scene view )
	{
		GL2 gl = drawable.getGL().getGL2();
		
		while( !this.initPainter.isEmpty() ) 
		{
			this.initPainter.pop().init( gl, view );
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#display(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void display( GLAutoDrawable drawable, Scene scene )
	{
		if( !this.isEnabled() )
			return;
		
		this.init( drawable, scene );
		GL2 gl = drawable.getGL().getGL2();
		
		for( Painter painter : this.painter )
			painter.display( gl, scene, this.getEngine().getTimeManager().getTime() );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#dispose(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	public void dispose( GLAutoDrawable drawable, Scene scene )
	{
		GL2 gl = drawable.getGL().getGL2();
		
		for( Painter painter : this.painter )
			painter.dispose( gl, scene );
	}
	
	/**
	 * Methode fuegt einen neuen Painter hinzu
	 * 
	 * @param c Painter
	 */
	public void register( Class<? extends Painter> c ) 
	{
		try 
		{
			Painter painter = c.getConstructor( DiamondEngine.class ).newInstance( this.getEngine() );
		
			this.painter.add( painter );
			this.initPainter.push( painter );
		}
		catch( Exception exc )
		{
			this.getEngine().getLogger().log( LogLevel.ERROR, "Der uebergebene Painter ist nicht korrekt und kann nicht registriert werden" );
		}
	}
	
	/**
	 * Methode liefert einen Painter mit Hilfe seines Klassennamens
	 * 
	 * @param c Klassenname des Painter
	 * @return Liefert einen Painter
	 */
	public Painter get( Class<? extends Painter> c )
	{
		Iterator<Painter> iterator = this.painter.iterator();
		while( iterator.hasNext() )
		{
			Painter painter = iterator.next();
			
			if( c.isInstance( painter ))
				return painter;
		}
		
		return null;
	}
	
	/**
	 * Methode entfernt einen alten Painter
	 * 
	 * @param c Painter
	 */
	public void destroy( Class<? extends Painter> c ) 
	{
		Painter painter = this.get( c );
		
		if( painter == null )
			return;
		
		//TODO Remove-Stack, damit die Scene mit uebergeben werden kann
		//painter.dispose( gl, scene );
		this.painter.remove( painter );
	}
	
	/**
	 * Methode laesst den Painter ablaufen
	 * 
	 * @param c Klassenname des Painters
	 */
	public void run( Class<? extends Painter> c )
	{
		Painter painter = this.get( c );
		
		if( painter == null )
			return;
		
		painter.run();
	}
	
	/**
	 * Methode laesst den Painter stoppen
	 * 
	 * @param c Klassenname des Painter
	 */
	public void stop( Class<? extends Painter> c )
	{
		Painter painter = this.get( c );
		
		if( painter == null )
			return;
		
		painter.stop();
	}
}