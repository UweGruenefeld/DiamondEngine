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
package test.showcase;

import javax.media.opengl.GL2;

import test.showcase.Initiator;

import diamond.DiamondEngine;
import diamond.manager.texture.StandardTexture;
import diamond.scene.Scene;
import diamond.scene.light.DirectionalLight;
import diamond.scene.mesh.geometrie.Cube;
import diamond.scene.mesh.geometrie.Quad;
import diamond.scene.mesh.geometrie.Sphere;
import diamond.scene.mesh.special.Teapot;
import diamond.scene.node.Node;
import diamond.scene.node.special.BumpMapping;
import diamond.scene.node.special.Reflection;
import diamond.scene.node.special.ToonShading;
import diamond.scene.node.special.Water;
import diamond.scene.painter.Painter;
import diamond.scene.terrain.bumpy.AlphaMapTerrain;
import diamond.scene.text.Text;
import diamond.scene.text.TextPosition;
import diamond.util.graphics.complexion.Color;
import diamond.util.graphics.complexion.DefaultColor;
import diamond.util.graphics.complexion.DefaultMaterial;
import diamond.util.graphics.complexion.Display;
import diamond.util.graphics.complexion.Material;
import diamond.util.math.Vector;

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
	private BumpMapping wuerfel1, wuerfel2;
	private Node cessna;
	private float time;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public ExampleWorld( DiamondEngine engine ) 
	{
		super( engine );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.painter.Painter#init(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void init( GL2 gl, Scene scene ) 
	{		
		// Kamera-Startposition
		scene.setPosition( -20, 7, 50 );
		
		// Einstellungen fuer die Szene
		scene.enableFPS();
		
		// Erzeuge das Licht
		scene.getLightControl().enable();
		DirectionalLight light = new DirectionalLight();
		light.enable();
		light.setPosition( 1, 1, 1 );
		scene.getLightControl().set( 0, light );
		
		// Erzeuge die Skybox
		scene.getSkyboxControl().enable();
		scene.getSkyboxControl().set( Initiator.SKYBOX );
		
		// Erzeuge das Terrain
		scene.getTerrainControl().enable();
		AlphaMapTerrain terrain = new AlphaMapTerrain( this.getEngine() );
		terrain.set( this.getEngine().getAssetManager().get( "texture/height/default.png" ), 10 );
		terrain.setAlphaTexture( Initiator.ALPHA );
		terrain.setTexture( 0, Initiator.ROAD );
		terrain.setTexture( 1, Initiator.DIRT );
		terrain.setTexture( 2, Initiator.GRASS );
		scene.getTerrainControl().set( terrain );
		
		// Erzeuge das Wasser
		Water water = new Water( this.getEngine() );
		Vector size = terrain.getSize();
		water.setMesh( new Quad( water ) );
		water.setTexture( 0, Initiator.WATER );
		water.setTexture( 1, Initiator.WATERNORMAL );
		water.setTranslate( size.x() / -2f, 3, size.y() / -2f );
		water.setScale( size.x(), 1, size.y() );
		scene.add( water );
		
		Text waterText = new Text( "Water-Effect", TextPosition.NONE, 
				new Color( DefaultColor.BLACK ));
		waterText.set( 0, 6, -45 );
		waterText.enableText3D();
		scene.getTextControl().add( waterText );

		// Erzeuge Wavefront-Objekte
		cessna = new Node( this.getEngine() );
		cessna.setMesh( this.getEngine().getModelManager().importWavefront( "cessna.obj" ));
		cessna.setDisplay( Display.COLOR );
		cessna.setColor( new Color( DefaultColor.BLUE ));
		cessna.setScale( .5f, .5f, .5f );
		cessna.setTranslate( 150, 20f, -35 );
		scene.add( cessna );	
		
		Node al = new Node( this.getEngine() );
		al.setMesh( this.getEngine().getModelManager().importWavefront( "al.obj" ));
		al.setDisplay( Display.MATERIAL );
		al.setMaterial( new Material( DefaultMaterial.RUBY ));
		al.setScale( .5f, .5f, .5f );
		al.setTranslate( 15, 5.6f, 35 );
		scene.add( al );
		
		Text wavefrontText = new Text( "Wavefront Object Loader", TextPosition.NONE, 
				new Color( DefaultColor.BLACK ));
		wavefrontText.set( 9, 7, 35 );
		wavefrontText.enableText3D();
		scene.getTextControl().add( wavefrontText );
		
		// Erzeuge Toon-Shading Hasen
		ToonShading toon = new ToonShading( this.getEngine() );
		toon.setMesh( this.getEngine().getModelManager().importWavefront( "bunny.obj" ));
		toon.setScale( 20f, 20f, 20f );
		toon.setTranslate( -20, 4.8f, 35 );
		scene.add( toon );
		
		Text toonText = new Text( "Toon-Shading", TextPosition.NONE, new Color( DefaultColor.BLACK ));
		toonText.set( -23, 7, 35 );
		toonText.enableText3D();
		scene.getTextControl().add( toonText );
		
		// Erzeuge Bumpmapping Wuerfel
		wuerfel1 = new BumpMapping( this.getEngine(), Initiator.STONE,
				Initiator.STONENORMAL );
		wuerfel1.setMesh( new Cube( wuerfel1 ));
		wuerfel1.setScale( 3, 3, 3 );
		wuerfel1.setTranslate( -5, 10, -30 );
		scene.add( wuerfel1 );
		
		wuerfel2 = new BumpMapping( this.getEngine(), Initiator.SAND,
				Initiator.STONENORMAL );
		wuerfel2.setMesh( new Cube( wuerfel2 ));
		wuerfel2.setScale( 3, 3, 3 );
		wuerfel2.setTranslate( 5, 10, -30 );
		scene.add( wuerfel2 );
		
		Text bumpText = new Text( "Bump-Mapping", TextPosition.NONE, new Color( DefaultColor.BLACK ));
		bumpText.set( -4, 8, -25 );
		bumpText.enableText3D();
		scene.getTextControl().add( bumpText );
		
		// Erzeuge Reflection Objekte
		Reflection sphere = new Reflection( this.getEngine() );
		sphere.setMesh( new Sphere() );
		sphere.setTranslate( -20, 10, 20 );
		scene.add( sphere );
		
		Reflection teapot = new Reflection( this.getEngine() );
		teapot.setMesh( new Teapot() );
		teapot.setTranslate( -10, 10, 20 );
		scene.add( teapot );
		
		Text reflectionText = new Text( "Reflection-Mapping", TextPosition.NONE, new Color( DefaultColor.BLACK ));
		reflectionText.set( -17, 8, 20 );
		reflectionText.enableText3D();
		scene.getTextControl().add( reflectionText );
		
		// Erzeuge Waelder
		Forest forest1 = new Forest( this.getEngine(), 15, 20,
				new StandardTexture[]{ Initiator.TREE3, Initiator.TREE4, 
			Initiator.TREE5, Initiator.TREE6 } );
		forest1.setTranslate( 15, 0, 10 );
		scene.add( forest1 );
		
		Forest forest2 = new Forest( this.getEngine(), 12, 20, 
				new StandardTexture[]{ Initiator.TREE1, Initiator.TREE2, 
			Initiator.TREE3, Initiator.TREE4, Initiator.TREE5, Initiator.TREE6 } );
		forest2.setTranslate( -15, 0, 35 );
		scene.add( forest2 );
		
		Forest forest3 = new Forest( this.getEngine(), 20, 25, 
				new StandardTexture[]{ Initiator.TREE1, Initiator.TREE2, 
			Initiator.TREE3, Initiator.TREE4 } );
		forest3.setTranslate( -55, 0, -35 );
		scene.add( forest3 );
		
		Forest forest4 = new Forest( this.getEngine(), 10, 10, 
				new StandardTexture[]{ Initiator.TREE1, Initiator.TREE2, 
			Initiator.TREE3, Initiator.TREE4 } );
		forest4.setTranslate( -15, 0, -15 );
		scene.add( forest4 );
		
		Text billboardText = new Text( "Billboarding", TextPosition.NONE, 
				new Color( DefaultColor.BLACK ));
		billboardText.set( -10, 8, -10 );
		billboardText.enableText3D();
		scene.getTextControl().add( billboardText );
		
		this.time = 15;
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.painter.Painter#update(javax.media.opengl.GL2, diamond.scene.Scene, long)
	 */
	@Override
	public void update( GL2 gl, Scene scene, long factor ) 
	{
		this.time += factor / 1000f;
		
		this.wuerfel1.setRotateYRelative( factor / 25f );
		
		this.wuerfel2.setRotateXRelative( factor / 30f );
		this.wuerfel2.setRotateZRelative( factor / 30f );
		
		this.cessna.setTranslateX((( this.time * 20 % 300 ) - 150 ) * -1 );
		this.cessna.setTranslateY((float)( Math.sin( this.time / 2 ) * 4 + 20 ));
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