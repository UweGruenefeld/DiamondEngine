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
package diamond.scene.terrain.flat;

import javax.media.opengl.GL2;

import diamond.Constant;
import diamond.DiamondEngine;
import diamond.manager.ShaderManager;
import diamond.manager.shader.Shader;
import diamond.scene.Scene;
import diamond.scene.light.Light;
import diamond.scene.terrain.Terrain;
import diamond.util.math.Matrix;
import diamond.util.math.Vector;

/**
 * Die Klasse FlatTerrain stellt ein ebenerdiges Terrain dar
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class FlatTerrain extends Terrain
{
	/*
	 * Allgemeine Attribute
	 */
	private int size;
	private int slices;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public FlatTerrain( DiamondEngine engine )
	{
		super( engine );
		
		this.size = Constant.defaultFlatTerrainSize;
		this.slices = Constant.defaultFlatTerrainSlices;
		
		// Erzeuge die Shader
		this.setShader( engine.getShaderManager().get( Shader.TERRAIN_FLAT ));
		this.enableShader();
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.terrain.Terrain#getSize()
	 */
	@Override
	public Vector getSize()
	{
		return new Vector( this.size, this.size );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.terrain.Terrain#renderMesh(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void renderMesh( GL2 gl, Scene scene ) 
	{
		// Soll ein Schatten gezeigt werden?
		if( scene.getShadowControl().isEnabled() )
		{	
			gl.glMatrixMode( GL2.GL_TEXTURE );
			gl.glActiveTexture( GL2.GL_TEXTURE2 );
			
			float bias[] = { .5f,  0f,  0f, 0f,
					  		  0f, .5f,  0f, 0f,
					  		  0f,  0f, .5f, 0f,
					  		 .5f, .5f, .5f, 1f };
			gl.glLoadMatrixf( bias, 0 );
			
			Light light = scene.getShadowControl().getTransmitter();
			gl.glMultMatrixf( light.getLightProjectionsMatrix(), 0 );
			gl.glMultMatrixf( light.getLightModelviewMatrix(), 0 );			
			gl.glMultMatrixf( Matrix.inverse4( scene.getCameraModelViewMatrix() ), 0 );
			
			gl.glMatrixMode( GL2.GL_MODELVIEW );
			
			int[] var = new int[2];
			var[0] = this.getShader().getLocation( gl, "terrain" );
		    var[1] = this.getShader().getLocation( gl, "shadow" );
			 
		    gl.glActiveTexture( GL2.GL_TEXTURE0 );
			this.getTexture().bind( gl );
		    
			gl.glActiveTexture( GL2.GL_TEXTURE1 );
			scene.getShadowControl().getShadowScene().bind( gl );
			
			gl.glProgramUniform1i( this.getShader().getProgrammID(), var[0], 0 );
			gl.glProgramUniform1i( this.getShader().getProgrammID(), var[1], 1 );
			
			ShaderManager.use( gl, this.getShader() );	
		}		
		
		gl.glTranslatef( -this.size / 2f, 0, -this.size / 2f );
		
		gl.glBegin( GL2.GL_TRIANGLES );
		{
			for( float i = 0; i < this.size; i += this.slices ) 
			{
				for( float j = 0; j < this.size; j += this.slices ) 
				{
					gl.glNormal3f( 0f, 1f,  0f );
					
					gl.glTexCoord2f( 0, 0 );
					gl.glVertex3f( i, 0f, j );
					
					gl.glTexCoord2f( 1, 1 );
					gl.glVertex3f( i + this.slices, 0f, j + this.slices );	
					
					gl.glTexCoord2f( 0, 1 );
					gl.glVertex3f( i + this.slices, 0f, j );
						
					
					gl.glNormal3f( 0f, 1f,  0f );
					
					gl.glTexCoord2f( 0, 0 );
					gl.glVertex3f( i, 0f, j );
					
					gl.glTexCoord2f( 1, 0 );
					gl.glVertex3f( i, 0f, j + this.slices );
					
					gl.glTexCoord2f( 1, 1 );
					gl.glVertex3f( i + this.slices, 0f, j + this.slices );
				}
			}
		}
		gl.glEnd();
		
		// Soll ein Schatten gezeigt werden
		if( scene.getShadowControl().isEnabled() )
		{
			gl.glMatrixMode( GL2.GL_TEXTURE );
			gl.glActiveTexture( GL2.GL_TEXTURE1 );
			
			gl.glLoadIdentity();
			
			gl.glMatrixMode( GL2.GL_MODELVIEW );
		}
	}
}