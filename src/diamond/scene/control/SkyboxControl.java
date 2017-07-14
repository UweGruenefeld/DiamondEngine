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
package diamond.scene.control;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.manager.ShaderManager;
import diamond.manager.shader.Shader;
import diamond.manager.shader.ShaderProgram;
import diamond.manager.texture.CubeTexture;
import diamond.scene.Scene;
import diamond.util.math.Vector;

/**
 * Der Skybox Manager ist Teil des World Manager und erlaubt
 * die Verwendung einer Skybox
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class SkyboxControl extends Control
{
	/*
	 * Klassenattribute
	 */
	private CubeTexture cubeTexture;
	private ShaderProgram shader;
	
	int vertexBufferObject[];
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public SkyboxControl( DiamondEngine engine )
	{
		super( engine );

		this.shader = null;
	}
	
	/**
	 * Methode zum Setzen der Cube Texture fuer die Skybox
	 * 
	 * @param cubeTexture Referenz auf eine Cube Texture
	 */
	public void set( CubeTexture cubeTexture )
	{
		this.cubeTexture = cubeTexture;
	}	
	
	/**
	 * Liefert die Texture ID der Cube Texture
	 * 
	 * @return Liefert die Texture ID der Cube Texture
	 */
	public int getTexture()
	{
		return this.cubeTexture.getTexture();
	}

	/**
	 * Aktualisiert die aktuelle Cube Texture
	 * 
	 * @param gl Referenz auf den OpenGL Zeichner
	 */
	private void update( GL2 gl )
	{
		if( this.shader == null )
			this.shader = this.getEngine().getShaderManager().get( Shader.SKYBOX );
		
		if( this.cubeTexture != null )
			this.cubeTexture.update( gl );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#init(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void init( GLAutoDrawable drawable, Scene scene ) 
	{		
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#display(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	public void display( GLAutoDrawable drawable, Scene scene )
	{					
		if( !this.isEnabled() || this.cubeTexture == null )
			return;
		
		GL2 gl = drawable.getGL().getGL2();
		
		this.update( gl );
		
		gl.glDisable( GL2.GL_DEPTH_TEST );
		
		gl.glEnable( GL2.GL_CULL_FACE );
		gl.glCullFace( GL2.GL_BACK );
		
		gl.glEnable( GL2.GL_TEXTURE );
		gl.glActiveTexture( GL2.GL_TEXTURE0 );
		
		this.cubeTexture.bind( gl );
		
		gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE , GL2.GL_DECAL );
        gl.glTexParameteri( GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE );
        gl.glTexParameteri( GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE );
        gl.glTexParameteri( GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE );

	    gl.glPushMatrix();
	    {          
	    	ShaderManager.use( gl, this.shader );
	    	
			int cube = this.shader.getLocation( gl, "cube" );
			gl.glUniform1i( cube, 0 );		
		
          	Vector position = scene.getPosition();
           	gl.glTranslated( position.get( 0 ), position.get( 1 ), position.get( 2 ));	
           	
           	final int EDGE = 10;
           	
           	final int NORMAL[][] = {{  0,  0,  1 },
           							{ -1,  0,  0 },
           							{  0,  0, -1 },
           							{  1,  0,  0 },
           							{  0, -1,  0 },
           							{  0,  1,  0 }};
           	
           	final int TEXCOORD[][][] = {{{  1, -1,  1 },
           							   	 {  1, -1, -1 },
           							   	 {  1,  1, -1 },
           							   	 {  1,  1,  1 }},
           							   	{{ -1, -1, -1 },
           							     { -1, -1,  1 },
           							     { -1,  1,  1 },
           							     { -1,  1, -1 }},
           							    {{ -1,  1,  1 },
           							     {  1,  1,  1 },
           							     {  1,  1, -1 },
           							     { -1,  1, -1 }},
           							    {{ -1, -1, -1 },
           							     {  1, -1, -1 },
           							     {  1, -1,  1 },
           							     { -1, -1,  1 }},
           							    {{  1, -1, -1 },
           							     { -1, -1, -1 },
           							     { -1,  1, -1 },
           							     {  1,  1, -1 }},
           							    {{ -1, -1,  1 },
               							 {  1, -1,  1 },
               							 {  1,  1,  1 },
               							 { -1,  1,  1 }}};
           	
           	final int VERTEX[][][] = {{{  EDGE, -EDGE, -EDGE },
						   			   {  EDGE, -EDGE,  EDGE },
						   			   {  EDGE,  EDGE,  EDGE },
						   			   {  EDGE,  EDGE, -EDGE }}, 
						   			  {{ -EDGE, -EDGE,  EDGE },
						   			   { -EDGE, -EDGE, -EDGE },
						   			   { -EDGE,  EDGE, -EDGE },
						   			   { -EDGE,  EDGE,  EDGE }}, 
						   			  {{ -EDGE,  EDGE, -EDGE },
						   			   {  EDGE,  EDGE, -EDGE },
						   			   {  EDGE,  EDGE,  EDGE },
						   			   { -EDGE,  EDGE,  EDGE }},
						   			  {{ -EDGE, -EDGE,  EDGE },
						   			   {  EDGE, -EDGE,  EDGE },
						   			   {  EDGE, -EDGE, -EDGE },
						   			   { -EDGE, -EDGE, -EDGE }},
	       							  {{  EDGE, -EDGE,  EDGE },
           							   { -EDGE, -EDGE,  EDGE },
           							   { -EDGE,  EDGE,  EDGE },
           							   {  EDGE,  EDGE,  EDGE }},
           							  {{ -EDGE, -EDGE, -EDGE },
           							   {  EDGE, -EDGE, -EDGE },
           							   {  EDGE,  EDGE, -EDGE },
           							   { -EDGE,  EDGE, -EDGE }}};

	        for( int i=0; i < VERTEX.length; i++ )
	        {	            	
				gl.glBegin( GL2.GL_POLYGON );
			    {
			    	gl.glNormal3iv( NORMAL[i], 0 );
			        gl.glTexCoord3f( TEXCOORD[i][0][0], TEXCOORD[i][0][1], TEXCOORD[i][0][2] );
			        gl.glVertex3i( VERTEX[i][0][0], VERTEX[i][0][1], VERTEX[i][0][2] );
			            	
			        gl.glTexCoord3f( TEXCOORD[i][1][0], TEXCOORD[i][1][1], TEXCOORD[i][1][2] );
			        gl.glVertex3i( VERTEX[i][1][0], VERTEX[i][1][1], VERTEX[i][1][2] );
			            	
			        gl.glTexCoord3f( TEXCOORD[i][2][0], TEXCOORD[i][2][1], TEXCOORD[i][2][2] );
			        gl.glVertex3i( VERTEX[i][2][0], VERTEX[i][2][1], VERTEX[i][2][2] );
			            	
			        gl.glTexCoord3f( TEXCOORD[i][3][0], TEXCOORD[i][3][1], TEXCOORD[i][3][2] );
			        gl.glVertex3i( VERTEX[i][3][0], VERTEX[i][3][1], VERTEX[i][3][2] );
			    }
			    gl.glEnd();
	        }
	   
	        ShaderManager.noProgram( gl );
        }
	    gl.glPopMatrix();
	        
	    gl.glDisable( GL2.GL_TEXTURE );
	    
	    
	    gl.glDisable( GL2.GL_CULL_FACE );
		gl.glEnable( GL2.GL_DEPTH_TEST );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#dispose(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void dispose( GLAutoDrawable drawable, Scene scene ) 
	{
	}
}