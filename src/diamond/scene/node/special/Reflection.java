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

import diamond.DiamondEngine;
import diamond.manager.ShaderManager;
import diamond.manager.shader.Shader;
import diamond.scene.Scene;
import diamond.scene.node.Node;
import diamond.util.graphics.complexion.Display;
import diamond.util.math.Vector;

/**
 * Die Klasse Reflection ermoeglicht Objekte mit Reflektionen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Reflection extends Node
{
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Reflection( DiamondEngine engine ) 
	{
		super( engine );
		
		this.setShader( engine.getShaderManager().get( Shader.NODE_REFLECTION ));
		this.enableShader();
		this.setDisplay( Display.NONE );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.Node#renderMesh(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void renderMesh( GL2 gl, Scene scene ) 
	{
		gl.glEnable( GL2.GL_CULL_FACE );
		gl.glCullFace( GL2.GL_BACK );
		
		gl.glEnable( GL2.GL_TEXTURE_CUBE_MAP );
		gl.glEnable( GL2.GL_TEXTURE_GEN_S );
		gl.glEnable( GL2.GL_TEXTURE_GEN_T );
		gl.glEnable( GL2.GL_TEXTURE_GEN_R );
		
		if( !scene.getSkyboxControl().isEnabled() )
			return;
		
		gl.glActiveTexture( GL2.GL_TEXTURE0 );
		gl.glBindTexture( GL2.GL_TEXTURE_CUBE_MAP, scene.getSkyboxControl().getTexture() );		
		{
	    	ShaderManager.use( gl, this.getShader() );
	    	
			int cube = this.getShader().getLocation( gl, "cube" );
			gl.glUniform1i( cube, 0 );	
			
			int world = this.getShader().getLocation( gl, "world" );
			float[] matrix = { 1, 0, 0, 0, 
							   0, 1, 0, 0, 
							   0, 0, 1, 0, 
							   this.getTranslate().get( 0 ), 
							   this.getTranslate().get( 1 ), 
							   this.getTranslate().get( 2 ), 0 };
			gl.glUniformMatrix4fv( world, 1, false, matrix, 0 );
			
			int camera = this.getShader().getLocation( gl, "camera" );
			Vector pos = scene.getPosition();
			gl.glUniform3fv( camera, 2, pos.toArray(), 0 );
			
			super.renderMesh( gl, scene );
			
			ShaderManager.noProgram( gl );
		}
		
		gl.glDisable( GL2.GL_TEXTURE_GEN_R );
		gl.glDisable( GL2.GL_TEXTURE_GEN_T );
		gl.glDisable( GL2.GL_TEXTURE_GEN_S );
	    gl.glDisable( GL2.GL_TEXTURE_CUBE_MAP ); 
	    
	    gl.glDisable( GL2.GL_CULL_FACE );
	}
}