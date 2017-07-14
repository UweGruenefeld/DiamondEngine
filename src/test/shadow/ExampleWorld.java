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
package test.shadow;

import javax.media.opengl.GL2;


import diamond.DiamondEngine;
import diamond.scene.Scene;
import diamond.scene.mesh.geometrie.Cube;
import diamond.scene.node.Node;
import diamond.scene.node.special.ToonShading;
import diamond.scene.painter.Painter;
import diamond.scene.terrain.flat.FlatTerrain;
import diamond.util.graphics.complexion.Color;
import diamond.util.graphics.complexion.DefaultColor;
import diamond.util.graphics.complexion.DefaultMaterial;
import diamond.util.graphics.complexion.Display;
import diamond.util.graphics.complexion.Material;

/**
 * Die Klasse ExampleWorld ist ein Painter der die Szene mit Leben fuellt
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class ExampleWorld extends Painter
{	
	/*
	 * Allgemeine Attribute
	 */
	private SunCube sun;
	private float value;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public ExampleWorld( DiamondEngine engine )
	{
		super( engine );
		
		this.value = 0;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.painter.Painter#init(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void init( GL2 gl, Scene scene ) 
	{
		// Zeige FPS an
		scene.getTextControl().enable();
		scene.enableFPS();
		
		// Erzeuge Sonne
		scene.getLightControl().enable();
		this.sun = new SunCube( this.getEngine() );
		this.sun.setDisplay( Display.NONE );
		scene.getLightControl().set( 0, this.sun.getLight() );
		scene.add( this.sun );
		
		// Erzeuge den ersten Wuerfel
		Node cube1 = new Node( this.getEngine() );
		cube1.setMesh( new Cube( cube1 ));
		cube1.setMaterial( new Material( DefaultMaterial.GOLD ));
		cube1.setDisplay( Display.MATERIAL );
		cube1.setScale( 3, 3, 3 );
		cube1.setTranslate( 3, 5, 3 );
		scene.add( cube1 );
		
		// Erzeuge den zweiten Wuerfel
		Node cube2 = new Node( this.getEngine() );
		cube2.setMesh( new Cube( cube1 ) );
		cube2.setColor( new Color( DefaultColor.RED ));
		cube2.setDisplay( Display.COLOR );
		cube2.setScale( 3, 3, 3 );
		cube2.setTranslate( -4, 5, -5 );
		scene.add( cube2 );
		
		// Erzeuge Toon-Shading Hasen
		ToonShading toon = new ToonShading( this.getEngine() );
		toon.setMesh( this.getEngine().getModelManager().importWavefront( "bunny.obj" ));
		toon.setScale( 40f, 40f, 40f );
		toon.setTranslate( 4, 4.8f, -5 );
		scene.add( toon );
		
		// Richte den Schatten ein
		scene.getShadowControl().enable();
		scene.getShadowControl().setTransmitter( this.sun.getLight() );
		scene.getShadowControl().addOccuper( cube1 );
		scene.getShadowControl().addOccuper( cube2 );
		scene.getShadowControl().addOccuper( toon );

		// Erzeuge die Skybox
		scene.getSkyboxControl().enable();
		scene.getSkyboxControl().set( Initiator.SKYBOX );
		
		// Erzeuge das Terrain
		scene.getTerrainControl().enable();
		FlatTerrain terrain = new FlatTerrain( this.getEngine() );	
		terrain.setDisplay( Display.TEXTURE );
		terrain.setTexture( 0, Initiator.GRASS );
		scene.getTerrainControl().set( terrain );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.painter.Painter#update(javax.media.opengl.GL2, diamond.scene.Scene, long)
	 */
	@Override
	public void update( GL2 gl, Scene scene, long factor ) 
	{
		this.value += factor / 1000f;
		
		this.sun.setPosition( (float)Math.sin( this.value ) * 8.5f, 29, 
				(float)Math.cos( this.value ) * 8.5f );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.painter.Painter#dispose(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void dispose( GL2 gl, Scene scene ) 
	{
	}
}