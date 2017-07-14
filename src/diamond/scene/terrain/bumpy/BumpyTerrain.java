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
package diamond.scene.terrain.bumpy;

import java.io.InputStream;

import javax.media.opengl.GL2;

import diamond.DiamondEngine;
import diamond.logger.LogLevel;
import diamond.scene.Scene;
import diamond.scene.terrain.Terrain;
import diamond.util.map.HeightMap;
import diamond.util.map.NormalMap;
import diamond.util.math.Vector;

/**
 * Die Klasse BumpyTerrain ist ein huegeliges Terrain
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class BumpyTerrain extends Terrain
{
	/*
	 * Allgemeine Attribute
	 */
	private HeightMap heightMap;
	private NormalMap normalMap;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public BumpyTerrain( DiamondEngine engine )
	{
		super( engine );
		
		this.heightMap = null;
		this.normalMap = null;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.terrain.Terrain#getHeight(float, float)
	 */
	@Override
	public float getHeight( float x, float z )
	{
		if( this.heightMap == null )
			return super.getHeight( x, z );
		
		return this.heightMap.get( (int)x, (int)z);
	}
	
	/**
	 * Methode setzt die HeightMap
	 * 
	 * @param heightMap HeightMap
	 * @param faktor Faktor
	 */
	public void set( InputStream heightMap, float faktor )
	{		
		this.heightMap = new HeightMap( heightMap, faktor );
		this.normalMap = new NormalMap( this.heightMap );
	}
	
	/**
	 * Methode liefert die HeightMap
	 * 
	 * @return Liefert die HeightMap
	 */
	public HeightMap getHeightMap()
	{
		return this.heightMap;
	}
	
	/**
	 * Methode liefert die NormalMap
	 * 
	 * @return Liefert die NormalMap
	 */
	public NormalMap getNormalMap()
	{
		return this.normalMap;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.terrain.Terrain#getSize()
	 */
	@Override
	public Vector getSize()
	{
		if( this.heightMap == null )
			return new Vector( 0, 0 );
		
		return new Vector( this.heightMap.getWidth() - 1, this.heightMap.getHeight() - 1 );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.terrain.Terrain#renderMesh(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void renderMesh( GL2 gl, Scene view )
	{
		if( this.heightMap == null || this.normalMap == null )
		{
			this.getEngine().getLogger().log( LogLevel.WARNING, "Keine Normal- oder HeightMap für das BumpyTerrain angegeben" );
			return;
		}
		
		gl.glTranslatef( -this.heightMap.getWidth() / 2f, 0, -this.heightMap.getWidth() / 2f );
		
		float w = 1f / ( this.heightMap.getWidth() - 1 );
		float h = 1f / ( this.heightMap.getHeight() - 1 );
		
		for( int i=0; i < this.heightMap.getWidth() - 1; i++ )
		{
			for( int j=0, k=this.heightMap.getHeight() - 2; j < this.heightMap.getHeight() - 1; j++, k-- )
			{
				gl.glBegin( GL2.GL_TRIANGLES );
				{
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE0, 0, 0 );
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE1, i * w, ( k + 1 ) * h );
					gl.glNormal3fv( this.normalMap.get( i, j ).toArray(), 0 );
					gl.glVertex3d( i, this.heightMap.get( i, j ), j );
					
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE0, 1, 1 );
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE1, ( i + 1 ) * w, k * h );
					gl.glNormal3fv( this.normalMap.get( i + 1, j + 1 ).toArray(), 0 );
					gl.glVertex3d( i + 1, this.heightMap.get( i + 1, j + 1 ), j + 1 );	
					
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE0, 0, 1 );
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE1, ( i + 1 ) * w, ( k + 1 ) * h );
					gl.glNormal3fv( this.normalMap.get( i + 1, j ).toArray(), 0 );
					gl.glVertex3d( i + 1, this.heightMap.get( i + 1, j ), j );
						
	
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE0, 0, 0 );
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE1, i * w, ( k + 1 ) * h );
					gl.glNormal3fv( this.normalMap.get( i, j ).toArray(), 0 );
					gl.glVertex3d( i, this.heightMap.get( i, j ), j );
					
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE0, 1, 0 );
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE1, i * w, k * h );
					gl.glNormal3fv( this.normalMap.get( i, j + 1 ).toArray(), 0 );
					gl.glVertex3d( i, this.heightMap.get( i, j + 1 ), j + 1 );
					
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE0, 1, 1 );
					gl.glMultiTexCoord2f( GL2.GL_TEXTURE1, ( i + 1 ) * w, k * h );
					gl.glNormal3fv( this.normalMap.get( i + 1, j + 1 ).toArray(), 0 );
					gl.glVertex3d( i + 1, this.heightMap.get( i + 1, j + 1 ), j + 1 );
				}
				gl.glEnd();
			}
		}
	}
}