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
package diamond.scene.mesh.geometrie;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.TextureCoords;

import diamond.scene.Scene;
import diamond.scene.mesh.AbstractMesh;
import diamond.scene.node.Node;

/**
 * Die Klasse Cube zeichnet einen Wuerfel
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Cube extends AbstractMesh
{
	/**
	 * Konstruktor
	 * 
	 * @param node Knoten
	 */
	public Cube( Node node )
	{
		super( node );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.mesh.Mesh#renderMesh(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void renderMesh( GL2 gl, Scene view )
	{
		TextureCoords coord = new TextureCoords( 0f, 0f, 1f, 1f );
		
		boolean useTangent = this.getNode().useShader() && this.getNode().getShader() != null;
		int tangent = 0;
		
		if( useTangent )
			tangent = this.getNode().getShader().getAttribute( gl, "tangent" );		
		
		gl.glBegin( GL2.GL_TRIANGLE_STRIP );
		{
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.top() );
			gl.glNormal3f( 0, 0, 1 );
			gl.glVertex3f( 0, 1, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.bottom() );
			gl.glNormal3f( 0, 0, 1 );
			gl.glVertex3f( 0, 0, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.top() );
			gl.glNormal3f( 0, 0, 1 );
			gl.glVertex3f( 1, 1, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.bottom() );
			gl.glNormal3f( 0, 0, 1 );
			gl.glVertex3f( 1, 0, 1 );	
		}
		gl.glEnd();	
		gl.glBegin( GL2.GL_TRIANGLE_STRIP );
		{
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 0f, 1f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.top() );
			gl.glNormal3f( 1, 0, 0 );
			gl.glVertex3f( 1, 1, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 0f, 1f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.bottom() );
			gl.glNormal3f( 1, 0, 0 );
			gl.glVertex3f( 1, 0, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 0f, 1f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.top() );
			gl.glNormal3f( 1, 0, 0 );
			gl.glVertex3f( 1, 1, 0 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 0f, 1f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.bottom() );
			gl.glNormal3f( 1, 0, 0 );
			gl.glVertex3f( 1, 0, 0 );
		}
		gl.glEnd();
		gl.glBegin( GL2.GL_TRIANGLE_STRIP );
		{
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.top() );
			gl.glNormal3f( 0, 0, -1 );
			gl.glVertex3f( 1, 1, 0 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.bottom() );
			gl.glNormal3f( 0, 0, -1 );
			gl.glVertex3f( 1, 0, 0 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.top() );
			gl.glNormal3f( 0, 0, -1 );
			gl.glVertex3f( 0, 1, 0 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.bottom() );
			gl.glNormal3f( 0, 0, -1 );
			gl.glVertex3f( 0, 0, 0 );
		}
		gl.glEnd();
		gl.glBegin( GL2.GL_TRIANGLE_STRIP );
		{
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.top() );
			gl.glNormal3f( 0, 1, 0 );
			gl.glVertex3f( 1, 1, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.bottom() );
			gl.glNormal3f( 0, 1, 0 );
			gl.glVertex3f( 1, 1, 0 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.top() );
			gl.glNormal3f( 0, 1, 0 );
			gl.glVertex3f( 0, 1, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.bottom() );
			gl.glNormal3f( 0, 1, 0 );
			gl.glVertex3f( 0, 1, 0 );
		}
		gl.glEnd();
		gl.glBegin( GL2.GL_TRIANGLE_STRIP );
		{
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 0f, 1f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.top() );
			gl.glNormal3f( -1, 0, 0 );
			gl.glVertex3f( 0, 1, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 0f, 1f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.bottom() );
			gl.glNormal3f( -1, 0, 0 );
			gl.glVertex3f( 0, 1, 0 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 0f, 1f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.top() );
			gl.glNormal3f( -1, 0, 0 );
			gl.glVertex3f( 0, 0, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 0f, 1f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.bottom() );
			gl.glNormal3f( -1, 0, 0 );
			gl.glVertex3f( 0, 0, 0 );
		}
		gl.glEnd();
		gl.glBegin( GL2.GL_TRIANGLE_STRIP );
		{
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.top() );
			gl.glNormal3f( 0, -1, 0 );
			gl.glVertex3f( 0, 0, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.left(), coord.bottom() );
			gl.glNormal3f( 0, -1, 0 );
			gl.glVertex3f( 0, 0, 0 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.top() );
			gl.glNormal3f( 0, -1, 0 );
			gl.glVertex3f( 1, 0, 1 );
			
			if( useTangent )
				gl.glVertexAttrib3fv( tangent, new float[]{ 1f, 0f, 0f }, 0 );
			gl.glTexCoord2f( coord.right(), coord.bottom() );
			gl.glNormal3f( 0, -1, 0 );
			gl.glVertex3f( 1, 0, 0 );
		}
		gl.glEnd();
	}
}