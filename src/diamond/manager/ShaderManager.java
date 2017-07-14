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
package diamond.manager;

import java.io.InputStream;
import java.util.Stack;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.logger.LogLevel;
import diamond.manager.shader.FragmentShader;
import diamond.manager.shader.Shader;
import diamond.manager.shader.ShaderProgram;
import diamond.manager.shader.VertexShader;

//TODO Fehlerbehandlung einbauen

/**
 * Die Klasse ShaderManager verwaltet die Shader Programme
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class ShaderManager extends AbstractManager
{
	/*
	 * Stack fuer die Shader Programme
	 */
	private final Stack<ShaderProgram> shader;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public ShaderManager( DiamondEngine engine )
	{
		super( engine );
		
		this.shader = new Stack<ShaderProgram>();
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#init(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void init( GLAutoDrawable drawable )
	{
		GL2 gl = drawable.getGL().getGL2();
		this.compile( gl );
		ShaderManager.noProgram( gl );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#display(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void display( GLAutoDrawable drawable )
	{
		GL2 gl = drawable.getGL().getGL2();
		this.compile( gl );
		ShaderManager.noProgram( gl );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#dispose(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void dispose( GLAutoDrawable drawable )
	{
	}
	
	/**
	 * Methode fuegt ein Shader Programm zum ShaderManager hinzu
	 * 
	 * @param shader Shader
	 */
	public void add( ShaderProgram shader )
	{
		this.shader.push( shader );
		this.getEngine().getLogger().log( LogLevel.INFORMATION, "Neues Shaderprogramm" );
	}
	
	//TODO Es sollte jedes Programm nur einmal erzeugt werden
	/**
	 * Methode erzeugt ein Shader Programm anhand eines Shader-Enums
	 * 
	 * @param shader Ausgewaehlter Shader
	 * @return Liefert das Shader Programm
	 */
	public ShaderProgram get( Shader shader )
	{
		ShaderProgram shaderProgram = new ShaderProgram();
		
		VertexShader vertex = null;
		FragmentShader fragment = null;
		
		switch( shader )
		{
			case SKYBOX:
				vertex = new VertexShader( this.getShader( "skybox/vSkybox.glsl" ));
				fragment = new FragmentShader( this.getShader( "skybox/fSkybox.glsl" ));
				break;
			case TERRAIN_FLAT:
				vertex = new VertexShader( this.getShader( "terrain_flat/vFlat.glsl" ));
				fragment = new FragmentShader( this.getShader( "terrain_flat/fFlat.glsl" ));
				break;
			case TERRAIN_MULTICOLOR:
				vertex = new VertexShader( this.getShader( "terrain_multicolor/vMultiColor.glsl" ));
				fragment = new FragmentShader( this.getShader( "terrain_multicolor/fMultiColor.glsl" ));
				break;
			case TERRAIN_MULTITEXTURE:
				vertex = new VertexShader( this.getShader( "terrain_multitexture/vMultiTexture.glsl" ));
				fragment = new FragmentShader( this.getShader( "terrain_multitexture/fMultiTexture.glsl" ));
				break;
			case TERRAIN_ALPHAMAP:
				vertex = new VertexShader( this.getShader( "terrain_alphamap/vAlphaMap.glsl" ));
				fragment = new FragmentShader( this.getShader( "terrain_alphamap/fAlphaMap.glsl" ));
				break;
			case NODE_REFLECTION:
				vertex = new VertexShader( this.getShader( "node_reflection/vReflection.glsl" ));
				fragment = new FragmentShader( this.getShader( "node_reflection/fReflection.glsl" ));
				break;	
			case NODE_BUMPMAPPING:
				vertex = new VertexShader( this.getShader( "node_bumpmapping/vBumpMapping.glsl" ));
				fragment = new FragmentShader( this.getShader( "node_bumpmapping/fBumpMapping.glsl" ));
				break;	
			case NODE_WATER:
				vertex = new VertexShader( this.getShader( "node_water/vWater.glsl" ));
				fragment = new FragmentShader( this.getShader( "node_water/fWater.glsl" ));
				break;
			case NODE_BILLBOARD:
				vertex = new VertexShader( this.getShader( "node_billboard/vBillboard.glsl" ));
				fragment = new FragmentShader( this.getShader( "node_billboard/fBillboard.glsl" ));
				break;
			case NODE_TOONSHADING:
				vertex = new VertexShader( this.getShader( "node_toonshading/vToonShading.glsl" ));
				fragment = new FragmentShader( this.getShader( "node_toonshading/fToonShading.glsl" ));
				break;
		}
		
		shaderProgram.add( vertex );
		shaderProgram.add( fragment );
		
		this.add( shaderProgram );
		
		return shaderProgram;
	}
	
	/**
	 * Liefert einen Shader
	 * Ein Shader liegt im Paket diamond/manager/shader/code
	 * 
	 * @param resource Dateiname
	 * @return Liefert die Resource
	 */
	private InputStream getShader( String resource )
	{
		String url = "diamond/manager/shader/code/";
		ClassLoader loader = getClass().getClassLoader();
		
		return loader.getResourceAsStream( url + resource );
	}
	
	/**
	 * Methode kompiliert die Shader Programme
	 * 
	 * @param gl GL2
	 */
	private void compile( GL2 gl )
	{
		while( !this.shader.isEmpty() )
		{
			if( !this.shader.pop().compile( gl ))
			{
				System.out.println( "Shader ERROR" );
			}
		}
	}
	
	/**
	 * statische Methode zum Verwenden von Shader Programmen 
	 * 
	 * @param gl GL2 
	 * @param shader Shader
	 */
	public static void use( GL2 gl, ShaderProgram shader )
	{
		if( shader.isCompiled() )
		{		
			gl.glUseProgram( shader.getProgrammID() );
		}
	}
	
	/**
	 * Methode sorgt dafuer, dass kein Programm gebunden ist
	 * 
	 * @param gl GL2
	 */
	public static void noProgram( GL2 gl )
	{
		gl.glUseProgram( 0 );
	}
}
