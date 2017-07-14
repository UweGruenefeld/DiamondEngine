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
package diamond.util.graphics.complexion;

import javax.media.opengl.GL2;

import diamond.Constant;
import diamond.manager.ShaderManager;
import diamond.manager.shader.ShaderProgram;
import diamond.manager.texture.Texture;
import diamond.scene.Scene;

/**
 * Klasse zur Darstellung von bestimmten Objekten
 * Ermoeglicht Texturen, Materialen, Farben und Shader
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public abstract class Complexion implements Template
{
	/*
	 * Variablen fuer die Darstellung
	 */
	private Display display;
	private Texture[] texture;
	private Material material;
	private Color color;
	
	/*
	 * Shadervariablen
	 */
	private boolean useShader;
	private ShaderProgram shader;
	
	/*
	 * Culling
	 */
	private boolean culling;
	private boolean backFace;
	
	/**
	 * Konstruktor
	 * Erzeugt ein Complexion Objekt ohne Parameter
	 */
	public Complexion()
	{
		this.display = Display.NONE;
		this.texture = new Texture[Constant.defaultMaxTexture];
		this.material = new Material();
		this.color = new Color();
		
		this.useShader = false;
		this.shader = null;
		
		this.culling = false;
		this.backFace = true;
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#setDisplay(diamond.util.graphics.complexion.Display)
	 */
	@Override
	public void setDisplay( Display display )
	{
		this.display = display;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#getDisplay()
	 */
	@Override
	public Display getDisplay()
	{
		return this.display;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#setTexture(diamond.manager.texture.Texture)
	 */
	@Override
	public void setTexture( Texture texture )
	{
		this.texture[0] = texture;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#setTexture(int, diamond.manager.texture.Texture)
	 */
	@Override
	public void setTexture( int textureUnit, Texture texture )
	{
		// Ist die gewuenschte Textureeinheit vorhanden?
		if( this.texture.length <= textureUnit )
			return;
		
		this.texture[textureUnit] = texture;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#getTextures()
	 */
	@Override
	public Texture[] getTextures()
	{
		return this.texture;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#getTexture()
	 */
	@Override
	public Texture getTexture()
	{
		if( this.texture.length > 0 )
			return this.texture[0];
		
		return null;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#getTexture(int)
	 */
	@Override
	public Texture getTexture( int textureUnit )
	{
		if( this.texture.length > textureUnit )
			return this.texture[textureUnit];
		return null;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#setMaterial(diamond.util.graphics.complexion.Material)
	 */
	@Override
	public void setMaterial( Material material )
	{
		this.material = material;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#getMaterial()
	 */
	@Override
	public Material getMaterial()
	{
		return this.material;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#setColor(diamond.util.graphics.complexion.Color)
	 */
	@Override
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#getColor()
	 */
	@Override
	public Color getColor()
	{
		return this.color;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#enableShader()
	 */
	@Override
	public void enableShader()
	{
		this.useShader = true;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#disableShader()
	 */
	@Override
	public void disableShader()
	{
		this.useShader = false;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#useShader()
	 */
	@Override
	public boolean useShader()
	{
		return this.useShader;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#setShader(diamond.manager.shader.ShaderProgram)
	 */
	@Override
	public void setShader( ShaderProgram shader )
	{
		this.shader = shader;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#getShader()
	 */
	@Override
	public ShaderProgram getShader()
	{
		return this.shader;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#setCulling(boolean)
	 */
	@Override
	public void setCulling( boolean culling )
	{
		this.culling = culling;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#isCulling()
	 */
	@Override
	public boolean isCulling()
	{
		return this.culling;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#setBackFace(boolean)
	 */
	@Override
	public void setBackFace( boolean backFace )
	{
		this.backFace = backFace;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Template#isBackFace()
	 */
	@Override
	public boolean isBackFace()
	{
		return this.backFace;
	}
	
	/**
	 * Methode wendet die Darstellungseinstellungen an
	 * 
	 * @param gl GL2
	 * @param scene aktuelle Scene
	 */
	public void display( GL2 gl, Scene scene )
	{
		// Aktuelles Shaderprogramm speichern und neues Shaderprogramm laden
		int[] program = new int[1];
		gl.glGetIntegerv( GL2.GL_CURRENT_PROGRAM, program, 0 );
		
		if( this.shader != null && this.useShader == true )
			ShaderManager.use( gl, this.shader );
		else
			ShaderManager.noProgram( gl );
		
		// Farbe setzen
		if( this.display == Display.COLOR || this.display == Display.COLOR_AND_MATERIAL ||
				this.display == Display.COLOR_AND_TEXTURE || this.display == Display.ALL )
		{
			gl.glEnable( GL2.GL_COLOR_MATERIAL );
			this.color.use( gl );
		}
		else
		{
			gl.glDisable( GL2.GL_COLOR_MATERIAL );
			gl.glColor4f( 1, 1, 1, 1 );
		}
		
		// Material setzen
		if( this.display == Display.MATERIAL || this.display == Display.COLOR_AND_MATERIAL || 
				this.display == Display.COLOR_AND_TEXTURE || this.display == Display.ALL )
			this.material.use( gl );
		else
			Material.useDefault( gl );
		
		// Texture setzen
		if( this.display == Display.TEXTURE || this.display == Display.COLOR_AND_TEXTURE ||
				this.display == Display.MATERIAL_AND_TEXTURE || this.display == Display.ALL )
		{		
			gl.glEnable( GL2.GL_TEXTURE );
			gl.glEnable( GL2.GL_MULTISAMPLE );
			
			for( int i = 0; i < this.texture.length; i++ )
			{
				if( this.texture[i] != null )
				{
					gl.glActiveTexture( GL2.GL_TEXTURE0 + i );				
					this.texture[i].bind( gl );
				}
			}
		}
		else
		{
			gl.glDisable( GL2.GL_TEXTURE );
		}	
		
		if( this.culling )
		{
			gl.glEnable( GL2.GL_CULL_FACE );
			
			if( this.backFace )
				gl.glCullFace( GL2.GL_BACK );
			else
				gl.glCullFace( GL2.GL_FRONT );
		}
		else
			gl.glDisable( GL2.GL_CULL_FACE );

		// wirklichen Inhalt rendern
		this.render( gl, scene );
		
		if( this.culling )
			gl.glDisable( GL2.GL_CULL_FACE );

		// Textureeinheit zuruecksetzen
		gl.glActiveTexture( GL2.GL_TEXTURE0 );
		
		if( this.display == Display.TEXTURE || this.display == Display.COLOR_AND_TEXTURE ||
				this.display == Display.MATERIAL_AND_TEXTURE || this.display == Display.ALL )
		{	
			gl.glDisable( GL2.GL_MULTISAMPLE );	
			gl.glDisable( GL2.GL_TEXTURE );
		}
		
		// Altes Shaderprogramm wiederladen
		gl.glUseProgram( program[0] );
	}
	
	/**
	 * Methode ist ein Platzhalter
	 * Wird abgeleitet um beliebige Inhalte mit den Einstellungen zu rendern
	 * 
	 * @param gl GL2
	 * @param scene aktuelle View
	 */
	public abstract void render( GL2 gl, Scene scene );
}