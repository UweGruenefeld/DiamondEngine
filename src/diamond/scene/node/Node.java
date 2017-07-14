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
package diamond.scene.node;

import java.util.HashSet;
import java.util.Iterator;

import javax.media.opengl.GL2;

import diamond.DiamondEngine;
import diamond.scene.Scene;
import diamond.scene.mesh.Mesh;
import diamond.util.graphics.complexion.Complexion;
import diamond.util.math.Vector;

/**
 * Die Klasse Node dient als abstrakter Knoten
 * fuer weitere Knoten zum Speichern von beliebigen
 * grafischen Objekten 
 * 
 * @version 0.1, 2013-02-05
 * @author Uwe Wilko Gruenefeld
 */
public class Node extends Complexion implements RootNode
{
	/*
	 * Referenz auf die Engine
	 */
	private final DiamondEngine engine;
	
	/*
	 * Variable zum Speichern weiterer Nodes
	 */
	private HashSet<Node> node;
	private Mesh mesh;
	
	/*
	 * Attribute fuer Verschiebung, Rotation und Skalierung
	 */
	private final Vector translate;
	private final Vector rotate;
	private final Vector scale;
	
	/**
	 * Konstruktor
	 * Erzeugt einen neuen Node mit Default-Werten
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Node( DiamondEngine engine )
	{
		this.engine = engine;
		
		this.node = new HashSet<Node>();
		
		this.translate = new Vector( 0f, 0f, 0f );
		this.rotate = new Vector( 0f, 0f, 0f );
		this.scale = new Vector( 1f, 1f, 1f );
	}
	
	/**
	 * Methode liefert eine Referenz auf eine Engine
	 * 
	 * @return Liefert eine Referenz auf eine Engine
	 */
	public final DiamondEngine getEngine()
	{
		return this.engine;
	}
	
	/**
	 * Methode setzt den Mesh eines Knotens
	 * 
	 * @param mesh Mesh
	 */
	public void setMesh( Mesh mesh )
	{
		this.mesh = mesh;
	}
	
	/**
	 * Methode liefert den Mesh eines Knotens
	 * 
	 * @return Liefert den Mesh eines Knotens
	 */
	public Mesh getMesh()
	{
		return this.mesh;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.RootNode#add(diamond.scene.node.Node)
	 */
	@Override
	public final void add( Node container )
	{
		this.node.add( container );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.RootNode#remove(diamond.scene.node.Node)
	 */
	@Override
	public final void remove( Node container )
	{
		this.node.remove( container );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.RootNode#get()
	 */
	@Override
	public final Node[] get()
	{
		return this.node.toArray( new Node[0] );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Complexion#render(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	public void render( GL2 gl, Scene view )
	{
		gl.glPushMatrix();
		{			
			// Translate
			gl.glTranslated( this.translate.get( 0 ), 
				this.translate.get( 1 ), this.translate.get( 2 ));
			
			// Rotate
			gl.glRotated( this.rotate.get( 0 ), 1., 0., 0. );
			gl.glRotated( this.rotate.get( 1 ), 0., 1., 0. );
			gl.glRotated( this.rotate.get( 2 ), 0., 0., 1. );
			
			// Scale
			gl.glScaled( this.scale.get( 0 ), 
					this.scale.get( 1 ), this.scale.get( 2 ));
			
			gl.glPushMatrix();
			{
				this.renderMesh( gl, view );
			}
			gl.glPopMatrix();
			
			Iterator<Node> iterator = this.node.iterator();
			while( iterator.hasNext() )
			{	
				iterator.next().display( gl, view );
			}
		}
		gl.glPopMatrix();
	}
	
	/**
	 * Methode um den Inhalt des Knoten zu zeichen
	 * 
	 * @param gl GL2
	 * @param scene aktuelle View
	 */
	public void renderMesh( GL2 gl, Scene scene )
	{
		if( this.mesh == null )
			return;
		
		this.mesh.renderMesh( gl, scene );
	}
	
	/**
	 * Methode liefert die Verschiebung des Knoten
	 * 
	 * @return Liefert die Verschiebung des Knotens
	 */
	public final Vector getTranslate()
	{
		return this.translate;
	}
	
	/**
	 * Methode setzt die Verschiebung des Knotens
	 * 
	 * @param translate Gibt die Verschiebung an
	 */
	public final void setTranslate( float... translate )
	{
		for( int i=0; i < 3 && i < translate.length; i++ )
			this.translate.set( i, translate[i] );
	}
	
	/**
	 * Methode setzt die Verschiebung des Knotens 
	 * relativ zu den aktuellen Werten
	 * 
	 * @param translate Gibt die Verschiebung an
	 */
	public final void setTranslateRelative( float... translate )
	{
		for( int i=0; i < 3 && i < translate.length; i++ )
			this.translate.set( this.translate.addComponent( i, translate[i] ));
	}
	
	/**
	 * Methode setzt die x-Verschiebung des Knotens
	 * 
	 * @param translateX Gibt die Verschiebung an
	 */
	public final void setTranslateX( float translateX )
	{
		this.translate.set( 0, translateX );
	}
	
	/**
	 * Methode setzt die x-Verschiebung des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param translateX Gibt die Verschiebung an
	 */
	public final void setTranslateXRelative( float translateX )
	{
		this.translate.set( this.translate.addComponent( 0, translateX ));
	}
	
	/**
	 * Methode setzt die y-Verschiebung des Knotens
	 * 
	 * @param translateY Gibt die Verschiebung an
	 */
	public final void setTranslateY( float translateY )
	{
		this.translate.set( 1, translateY );
	}
	
	/**
	 * Methode setzt die y-Verschiebung des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param translateY Gibt die Verschiebung an
	 */
	public final void setTranslateYRelative( float translateY )
	{
		this.translate.set( this.translate.addComponent( 1, translateY ));
	}
	
	/**
	 * Methode setzt die z-Verschiebung des Knotens
	 * 
	 * @param translateZ Gibt die Verschiebung an
	 */
	public final void setTranslateZ( float translateZ )
	{
		this.translate.set( 2, translateZ );
	}
	
	/**
	 * Methode setzt die z-Verschiebung des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param translateZ Gibt die Verschiebung an
	 */
	public final void setTranslateZRelative( float translateZ )
	{
		this.translate.set( this.translate.addComponent( 2, translateZ ));
	}
	
	/**
	 * Methode liefert die Rotation als Vektor
	 * 
	 * @return Liefert die Rotation
	 */
	public final Vector getRotate()
	{
		return this.rotate;
	}
	
	/**
	 * Methode setzt die Rotation des Knotens
	 * 
	 * @param rotate Gibt die Rotation an
	 */
	public final void setRotate( float... rotate )
	{
		for( int i=0; i < 3 && i < rotate.length; i++ )
			this.rotate.set( i, rotate[i] );
	}
	
	/**
	 * Methode setzt die Rotation des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param rotate Gibt die Rotation an
	 */
	public final void setRotateRelative( float... rotate )
	{
		for( int i=0; i < 3 && i < rotate.length; i++ )
			this.rotate.set( this.rotate.addComponent( i, rotate[i] ));
	}
	
	/**
	 * Methode setzt die x-Rotation des Knotens
	 * 
	 * @param rotateX Gibt die x-Rotation an
	 */
	public final void setRotateX( float rotateX )
	{
		this.rotate.set( 0, rotateX );
	}
	
	/**
	 * Methode setzt die x-Rotation des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param rotateX Gibt die x-Rotation an
	 */
	public final void setRotateXRelative( float rotateX )
	{
		this.rotate.set( this.rotate.addComponent( 0, rotateX ));
	}
	
	/**
	 * Methode setzt die y-Rotation des Knotens
	 * 
	 * @param rotateY Gibt die y-Rotation an
	 */
	public final void setRotateY( float rotateY )
	{
		this.rotate.set( 1, rotateY );
	}
	
	/**
	 * Methode setzt die y-Rotation des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param rotateY Gibt die y-Rotation an
	 */
	public final void setRotateYRelative( float rotateY )
	{
		this.rotate.set( this.rotate.addComponent( 1, rotateY ));
	}
	
	/**
	 * Methode setzt die z-Rotation des Knotens
	 * 
	 * @param rotateZ Gibt die z-Rotation an
	 */
	public final void setRotateZ( float rotateZ )
	{
		this.rotate.set( 2, rotateZ );
	}
	
	/**
	 * Methode setzt die z-Rotation des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param rotateZ Gibt die z-Rotation an
	 */
	public final void setRotateZRelative( float rotateZ )
	{
		this.rotate.set( this.rotate.addComponent( 2, rotateZ ));
	}
	
	/**
	 * Methode liefert die Skalierung des Knotens
	 * 
	 * @return Liefert die Skalierung
	 */
	public final Vector getScale()
	{
		return this.scale;
	}
	
	/**
	 * Methode setzt die Skalierung des KNotens
	 * 
	 * @param scale Skalierung
	 */
	public final void setScale( float... scale )
	{
		for( int i=0; i < 3 && i < scale.length; i++ )
			this.scale.set( i, scale[i] );
	}
	
	/**
	 * Methode setzt die Skalierung des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param scale Skalierung
	 */
	public final void setScaleRelative( float... scale )
	{
		for( int i=0; i < 3 && i < scale.length; i++ )
			this.scale.set( this.scale.addComponent( i, scale[i] ));
	}
	
	/**
	 * Methode setzt die x-Skalierung des Knotens
	 * 
	 * @param scaleX x-Skalierung
	 */
	public final void setScaleX( float scaleX )
	{
		this.scale.set( 0, scaleX );
	}
	
	/**
	 * Methode setzt die x-Skalierung des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param scaleX x-Skalierung
	 */
	public final void setScaleXRelative( float scaleX )
	{
		this.scale.set( this.scale.addComponent( 0, scaleX ));
	}
	
	/**
	 * Methode setzt die y-Skalierung des Knotens
	 * 
	 * @param scaleY y-Skalierung
	 */
	public final void setScaleY( float scaleY )
	{
		this.scale.set( 1, scaleY );
	}
	
	/**
	 * Methode setzt die y-Skalierung des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param scaleY y-Skalierung
	 */
	public final void setScaleYRelative( float scaleY )
	{
		this.scale.set( this.scale.addComponent( 1, scaleY ));
	}
	
	/**
	 * Methode setzt die z-Skalierung des Knotens
	 * 
	 * @param scaleZ z-Skalierung
	 */
	public final void setScaleZ( float scaleZ )
	{
		this.scale.set( 2, scaleZ );
	}
	
	/**
	 * Methode setzt die z-Skalierung des Knotens
	 * relativ zu den aktuellen Werten
	 * 
	 * @param scaleZ z-Skalierung
	 */
	public final void setScaleZRelative( float scaleZ )
	{
		this.scale.set( this.scale.addComponent( 2, scaleZ ));
	}
}