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
package diamond.scene.terrain;

import javax.media.opengl.GL2;

import diamond.DiamondEngine;
import diamond.scene.Scene;
import diamond.scene.mesh.Mesh;
import diamond.util.graphics.complexion.Complexion;
import diamond.util.math.Vector;

/**
 * Die abstrakte Klasse Terrain ist die Vorschrift bzw. Oberklasse fuer alle Terains
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public abstract class Terrain extends Complexion implements Mesh
{
	/*
	 * Referenz auf die Engine
	 */
	private DiamondEngine engine;
	
	/*
	 * Allgemeine Attribute
	 */
	private int list;
	private boolean compile;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Terrain( DiamondEngine engine )
	{
		this.engine = engine;
		
		this.list = 0;
		this.compile = true;
		
		this.setCulling( true );
	}
	
	/**
	 * Methode liefert die y-Position eines Terrain
	 * 
	 * @param x x-Position
	 * @param z y-Position
	 * @return Liefert die y-Position
	 */
	public float getHeight( float x, float z )
	{
		return 0;
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
	 * Methode liefert die Groesse des Terrain
	 * 
	 * @return Liefert die Groesse des Terrain
	 */
	public abstract Vector getSize();

	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Complexion#render(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void render( GL2 gl, Scene view )
	{
		if( this.compile || view.getShadowControl().isEnabled() )
		{
			this.list = gl.glGenLists( 1 );
			
		    gl.glNewList( this.list, GL2.GL_COMPILE );
			gl.glPushMatrix();
			{
				renderMesh( gl, view );
			}
			gl.glPopMatrix();
			gl.glEndList();
			
			this.compile = false;
		}
		
		gl.glCallList( this.list );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.mesh.Mesh#renderMesh(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	public abstract void renderMesh( GL2 gl, Scene view );
}