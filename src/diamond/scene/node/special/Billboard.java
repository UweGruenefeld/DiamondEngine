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
package diamond.scene.node.special;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.TextureCoords;

import diamond.DiamondEngine;
import diamond.manager.shader.Shader;
import diamond.manager.texture.Texture;
import diamond.scene.Scene;
import diamond.scene.node.Node;
import diamond.util.graphics.complexion.Display;

//TODO Es muss moeglich sein die Richtung anzugeben
// in der ein Billboard gleich aussieht

/**
 * Die Klasse Billboard ermoeglicht Objekte,
 * die aus jeder Sicht gleich aussehen
 * 
 * @version 0.1, 2013-02-05
 * @author Uwe Wilko Gruenefeld
 */
public class Billboard extends Node
{
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 * @param texture Texture
	 */
	public Billboard( DiamondEngine engine, Texture texture )
	{
		super( engine );
		
		this.setTexture( texture );
		this.setDisplay( Display.TEXTURE );
		this.setShader( engine.getShaderManager().get( Shader.NODE_BILLBOARD ));
		this.enableShader();
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.Node#renderMesh(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void renderMesh( GL2 gl, Scene scene )
	{
		gl.glEnable( GL2.GL_BLEND );
		gl.glBlendFunc( GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA );
		gl.glEnable( GL2.GL_ALPHA_TEST );
		gl.glAlphaFunc( GL2.GL_GREATER, 0.1f );  
		
		float mat[] = new float[16];
		gl.glGetFloatv( GL2.GL_MODELVIEW_MATRIX, mat, 0 );

		TextureCoords coord = null;
		
		if( this.getTexture() != null )
			coord = this.getTexture().getTextureCoords();
		else
			coord = new TextureCoords( 0f, 0f, 1f, 1f );
		
		gl.glBegin( GL2.GL_TRIANGLE_STRIP );
		{
			gl.glNormal3f( 0, 0, 1 );
			gl.glTexCoord2f( coord.left(), coord.top() );
			gl.glVertex3f( -mat[0], 2, -mat[8] );
			gl.glTexCoord2f( coord.left(), coord.bottom() );
			gl.glVertex3f( -mat[0], 0, -mat[8] );
			gl.glTexCoord2f( coord.right(), coord.top() );
			gl.glVertex3f(  mat[0], 2,  mat[8] );
			gl.glTexCoord2f( coord.right(), coord.bottom() );
			gl.glVertex3f(  mat[0], 0,  mat[8] );	
		}
		gl.glEnd();	
		
		gl.glDisable( GL2.GL_ALPHA_TEST );
		gl.glDisable( GL2.GL_BLEND );
	}
}