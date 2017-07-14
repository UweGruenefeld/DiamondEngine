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

import java.util.HashSet;
import java.util.Iterator;

import javax.media.opengl.GL2;

import diamond.DiamondEngine;
import diamond.manager.texture.StandardTexture;
import diamond.scene.Scene;
import diamond.scene.node.Node;
import diamond.scene.node.special.Billboard;
import diamond.util.math.Vector;

/**
 * Die Klasse Forest ermoeglicht es einen Wald darzustellen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Forest extends Node
{
	/*
	 * Allgemeine Attribute
	 */
	private StandardTexture[] texture;
	private int size, number;
	private HashSet<Billboard> forest;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 * @param size Groesse des Waldbereichs x = y = size
	 * @param number Anzahl der Baeume in dem Bereich
	 * @param texture Array von Baum-Texturen
	 */
	public Forest( DiamondEngine engine, int size, int number, StandardTexture... texture ) 
	{
		super( engine );
		
		this.texture = texture;
		
		this.size = size;
		this.number = number;
		
		this.forest = new HashSet<Billboard>();
				
		this.generate();
	}
	
	/**
	 * Methode generiert den Wald
	 */
	private void generate()
	{
		for( int i=0; i<this.number; i++ )
		{
			int tex = (int)( Math.random() * ( this.texture.length ));			
			Billboard tree = new Billboard( this.getEngine(), this.texture[tex] );
			
			// Generiere x-, z-Position zufaellig
			int x = (int)( Math.random() * ( this.size + 1 ) );
			int z = (int)( Math.random() * ( this.size + 1 ) );
			
			tree.setTranslate( x, 0, z );

			this.forest.add( tree );
			this.add( tree );
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.Node#renderMesh(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void renderMesh( GL2 gl, Scene scene )
	{
		if( scene.getTerrainControl().get() == null )
			return;
		
		Vector size = scene.getTerrainControl().get().getSize();
		size.set( 0, ( size.x() + 1 ) / 2f );
		size.set( 1, ( size.y() + 1 ) / 2f );
		
		Iterator<Billboard> treeIterator = this.forest.iterator();
		while( treeIterator.hasNext() )
		{
			Billboard tree = treeIterator.next();
			tree.setTranslateY( scene.getTerrainControl().get()
					.getHeight( tree.getTranslate().x() + size.x() + this.getTranslate().x(),
						tree.getTranslate().z() + size.y() + this.getTranslate().z() ));
		}
		
		super.renderMesh( gl, scene );
	}
}