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

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.scene.Scene;
import diamond.scene.terrain.Terrain;

/**
 * Die Klasse TerrainControl erlaubt es Terrains in die Szene einzufuegen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class TerrainControl extends Control
{
	/*
	 * Speichert das aktuelle Terrain
	 */
	private Terrain terrain;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public TerrainControl( DiamondEngine engine )
	{
		super( engine );
	}
	
	/**
	 * Methode liefert das aktuelle Terrain
	 * 
	 * @return Liefert das aktuelle Terrain
	 */
	public Terrain get()
	{
		return this.terrain;
	}
	
	/**
	 * Methode setzt das aktuelle Terrain
	 * 
	 * @param terrain aktuelle Terrain
	 */
	public void set( Terrain terrain )
	{
		this.terrain = terrain;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#init(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void init( GLAutoDrawable drawable, Scene scene ) 
	{
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#display(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	public void display( GLAutoDrawable drawable, Scene scene )
	{
		GL2 gl = drawable.getGL().getGL2();
		
		if( !this.isEnabled() || this.terrain == null )
			return;

		gl.glEnable( GL2.GL_TEXTURE_2D );
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE , GL2.GL_MODULATE );
		
		this.terrain.display( gl, scene );
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