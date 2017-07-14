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
package diamond.manager.texture;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureData;
//import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import diamond.DiamondEngine;

/**
 * Die CubeTexture implementiert das Interface Texture
 * Es handelt sich um eine Klasse zur Repraesentation von Cube-Texturen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class CubeTexture implements Texture
{
	/*
	 * Referenz auf die Engine
	 */
	private DiamondEngine engine;
	
	/*
	 * Speichert die Cube Texture
	 */
	private int cubeTexture;
	
	/*
	 * Speichert eine Anfrage eine neue Texture zu generieren
	 */
	private InputStream[] stream;
	private String postfix;
	
	/**
	 * Konstruktor
	 * Erzeugt Cube Texture aus Strings
	 * 
	 * @param engine Referenz auf die Engine
	 * @param east Ost - Positiv X
	 * @param west West - Negativ X
	 * @param top Oben - Positiv Y
	 * @param bottom Unten - Negativ Y
	 * @param north Nord - Positiv Z
	 * @param south Sued - Negativ Z
	 * @param postfix Dateierweiterung
	 */
	public CubeTexture( DiamondEngine engine, String east, String west, String top, String bottom, String north, String south, String postfix )
	{
		this.engine = engine;
		
		this.cubeTexture = -1;
		
		this.stream = new InputStream[]{ this.engine.getAssetManager().get( "texture/cube/" + east ), this.engine.getAssetManager().get( "texture/cube/" + west ),
				this.engine.getAssetManager().get( "texture/cube/" + top ),	this.engine.getAssetManager().get( "texture/cube/" + bottom ),
				this.engine.getAssetManager().get( "texture/cube/" + north ), this.engine.getAssetManager().get( "texture/cube/" + south ) };
		this.postfix = postfix;
	}
	
	/**
	 * Konstruktor
	 * Erzeugt Cube Texture aus InputStreams
	 * 
	 * @param engine Referenz auf die Engine
	 * @param east Ost - Positiv X
	 * @param west West - Negativ X
	 * @param top Oben - Positiv Y
	 * @param bottom Unten - Negativ Y
	 * @param north Nord - Positiv Z
	 * @param south Sued - Negativ Z
	 * @param postfix Dateierweiterung
	 */
	public CubeTexture( DiamondEngine engine, InputStream east, InputStream west, InputStream top, InputStream bottom, InputStream north, InputStream south, String postfix )
	{
		this.engine = engine;
		
		this.cubeTexture = -1;
		
		this.stream = new InputStream[]{ east, west, top, bottom, north, south };
		this.postfix = postfix;
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#update(javax.media.opengl.GL2)
	 */
	@Override
	public void update( GL2 gl ) 
	{
		if( this.stream == null || this.postfix == null )
			return;
		
		// Generiere Texture ID
		IntBuffer intBuffer = IntBuffer.allocate( 1 );
		gl.glGenTextures( 1, intBuffer );
		this.cubeTexture = intBuffer.get();
		
		// Binde die Texture
		gl.glBindTexture( GL2.GL_TEXTURE_CUBE_MAP, this.cubeTexture );
		gl.glTexParameteri( GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_GENERATE_MIPMAP, GL2.GL_TRUE );

		try 
		{
			for ( int i = 0; i < stream.length; i++ ) 
			{
				BufferedImage image = ImageIO.read( stream[i] );
				TextureData textureData = AWTTextureIO.newTextureData( this.engine.getProfile(), image, false );
				//TextureData textureData = TextureIO.newTextureData( this.engine.getProfile(), stream[i], false, postfix );
				
				gl.glTexImage2D( GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0,
						GL2.GL_RGB8, textureData.getWidth(), textureData.getHeight(), 0,
						GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, textureData.getBuffer() );
			}
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
				
		this.stream = null;
		this.postfix = null;
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#bind(javax.media.opengl.GL2)
	 */
	@Override
	public void bind( GL2 gl ) 
	{		
		gl.glEnable( GL2.GL_TEXTURE_CUBE_MAP );
		
		gl.glBindTexture( GL2.GL_TEXTURE_CUBE_MAP, this.cubeTexture );
		
		gl.glDisable( GL2.GL_TEXTURE_CUBE_MAP );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#getTexture()
	 */
	@Override
	public int getTexture()
	{
		return this.cubeTexture;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#getTextureCoords()
	 */
	@Override
	public TextureCoords getTextureCoords() 
	{
		return new TextureCoords( 0, 0, 1, 1 );
	}
}