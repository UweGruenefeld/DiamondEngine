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
import diamond.manager.texture.StandardTexture;
import diamond.manager.texture.NormalTexture;
import diamond.scene.Scene;
import diamond.scene.node.Node;
import diamond.util.graphics.complexion.Display;

/**
 * Die Klasse BumpMapping implementiert die gleichnamige Funktionalitaet
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class BumpMapping extends Node
{
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 * @param texture Oberflaechen-Texture
	 * @param normal Normalen-Texture
	 */
	public BumpMapping( DiamondEngine engine, StandardTexture texture, NormalTexture normal ) 
	{
		super( engine );
		
		this.setShader( engine.getShaderManager().get( Shader.NODE_BUMPMAPPING ));
		
		this.setTexture( 0, texture );
		this.setTexture( 1, normal );
		
		this.setDisplay( Display.TEXTURE );
		this.enableShader();
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.Node#render(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void render( GL2 gl, Scene view )
	{
		ShaderManager.noProgram( gl );
		
		int[] var = new int[2];
		var[0] = this.getShader().getLocation( gl, "diffuseTexture" );
		var[1] = this.getShader().getLocation( gl, "normalTexture" );

	    for( int i=0; i < super.getTextures().length; i++ )
	    {
	    	gl.glActiveTexture( GL2.GL_TEXTURE0 + i );
	    	if( super.getTexture( i ) != null )
	    		super.getTexture( i ).bind( gl );
	    }

		gl.glProgramUniform1i( this.getShader().getProgrammID(), var[0], 0 );
		gl.glProgramUniform1i( this.getShader().getProgrammID(), var[1], 1 );
		
		ShaderManager.use( gl, this.getShader() );
		
		super.render( gl, view );
	}
}