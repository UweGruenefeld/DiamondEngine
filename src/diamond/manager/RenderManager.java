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

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.scene.Scene;

/**
 * Die Klasse RenderManager verwaltet die Views und
 * steht in enger Verbindung zum Renderer
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class RenderManager extends AbstractManager
{
	/*
	 * Variablen zum Speichern der Scenes
	 */
	private HashSet<Scene> scene;
	private Scene defaultScene;
	
	/*
	 * Grafikkontext
	 */
	private GL2 gl;
	
	/*
	 * Allgemeine Variablen
	 */
	private float fps;
	private long time;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public RenderManager( DiamondEngine engine )
	{
		super( engine );
		
		this.scene = new HashSet<Scene>();
		this.defaultScene = new Scene( engine, true );
		
		this.fps = 0;
		this.time = 0;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#init(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void init( GLAutoDrawable drawable ) 
	{
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#display(javax.media.opengl.GLAutoDrawable)
	 */
	public void display( GLAutoDrawable drawable )
	{
		Iterator<Scene> iterator = this.scene.iterator();
		while( iterator.hasNext() )
		{
			iterator.next().display( drawable, false );
		}
		this.defaultScene.display( drawable, true );
		this.updateFPS();
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#dispose(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void dispose( GLAutoDrawable drawable ) 
	{
		Iterator<Scene> iterator = this.scene.iterator();
		while( iterator.hasNext() )
		{
			iterator.next().dispose( drawable );
		}
		this.defaultScene.dispose( drawable );
	}
		
	/**
	 * Methode fuegt eine neue Scene hinzu
	 * 
	 * @param scene Scene
	 */
	public void add( Scene scene )
	{
		this.scene.add( scene );
	}
	
	/**
	 * Methode entfernt eine Scene
	 * 
	 * @param scene Scene
	 */
	public void remove( Scene scene )
	{
		this.scene.remove( scene );
	}
	
	/**
	 * Methode liefert alle gespeicherten Scenes
	 * 
	 * @return Liefert alle gespeicherten Scenes
	 */
	public Scene[] get()
	{
		return this.scene.toArray( new Scene[0] );
	}
	
	/**
	 * Methode zum Setzen des Grafikontextes
	 * 
	 * @param gl GL2
	 */
	public void setGL( GL2 gl )
	{
		this.gl = gl;
	}
	
	/**
	 * Methode liefert den Grafikkontext
	 * 
	 * @return Liefert den Grafikkontext GL2
	 */
	public GL2 getGL()
	{
		return this.gl;
	}
	
	/**
	 * Methode setzt die Default-Scene
	 * 
	 * @param scene neue Default-Scene
	 * @return Liefert true, wenn es funktioniert hat, sonst false
	 */
	public boolean setDefaultScene( Scene scene )
	{
		if( this.scene.contains( scene ) )
		{
			this.add( this.defaultScene );
			this.remove( scene );
			this.defaultScene = scene;
			
			return true;
		}
		return false;
	}
	
	/**
	 * Methode liefert die Default-Scene
	 * 
	 * @return Liefert die Default-Scene
	 */
	public Scene getDefaultScene()
	{
		return this.defaultScene;
	}
	
	/**
	 * Methode liefert die letzte gemessene FPS
	 * 
	 * @return Liefert die letzte gemessene FPS
	 */
	public float getFPS()
	{
		return this.fps;
	}
	
	/**
	 * Methode aktualisiert die FPS
	 */
	private void updateFPS()
	{
		if( this.getEngine().getTimeManager().getAbsoluteTime() > this.time + 1000 )
		{
			this.time = this.getEngine().getTimeManager().getAbsoluteTime();
			this.fps = 1f / ( this.getEngine().getTimeManager().getTime() / 1000f );
		}
	}
}
