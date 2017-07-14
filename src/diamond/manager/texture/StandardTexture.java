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

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import diamond.DiamondEngine;

/**
 * Die DefaultTexture implementiert das Interface Texture
 * Es handelt sich um eine Klasse zur Repraesentation von 2D-Texturen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class StandardTexture implements Texture
{
	/*
	 * Referenz auf die Engine
	 */
	private DiamondEngine engine;
	
	/*
	 * Attribute zum Speichern der Texture
	 */
	private com.jogamp.opengl.util.texture.Texture texture;
	private TextureData textureData;
	
	/**
	 * Konstruktor
	 * Erzeugt leere Texture
	 * 
	 * @param engine Referenz auf die Engine
	 * @param width Breite der Texture
	 * @param height Hoehe der Texture
	 */
	public StandardTexture( DiamondEngine engine, int width, int height )
	{
		this.engine = engine;
		
		this.texture = null;
		this.textureData = null;
		
		this.setEmpty( width, height );
	}
	
	/**
	 * Konstruktor
	 * Erzeugt Texure aus einem BufferedImage
	 * 
	 * @param engine Referenz auf die Engine
	 * @param image BufferedImage
	 */
	public StandardTexture( DiamondEngine engine, BufferedImage image )
	{
		this.engine = engine;
		
		this.texture = null;
		this.textureData = null;
		
		this.set( image );
	}
	
	/**
	 * Konstruktor
	 * Erzeugt eine Texture aus einer Datei
	 * 
	 * @param engine Referenz auf die Engine
	 * @param texture String auf eine Datei
	 * @param postfix Dateierweiterung
	 */
	public StandardTexture( DiamondEngine engine, String texture, String postfix )
	{
		this.engine = engine;
		
		this.texture = null;
		this.textureData = null;
		
		this.set( engine.getAssetManager().get( "texture/standard/" + texture ), postfix );
	}
	
	/**
	 * Konstruktor
	 * Erzeugt eine Texture aus einem InputStream 
	 * 
	 * @param engine Referenz auf eine Engine
	 * @param stream InputStream
	 * @param postfix Datei-Format
	 */
	public StandardTexture( DiamondEngine engine, InputStream stream, String postfix )
	{
		this.engine = engine;
		
		this.texture = null;
		this.textureData = null;
		
		this.set( stream, postfix );
	}
	
	/**
	 * Methode erzeugt eine leere Texture mit der angebenen Groesse
	 * 
	 * @param width	Breite
	 * @param height Hoehe
	 */
	public void setEmpty( int width, int height )
	{
		try
		{			
			BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
			this.textureData = AWTTextureIO.newTextureData( this.engine.getProfile(), image, false );
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
	}
	
	/**
	 * Methode erzeugt eine Texture aus einem BufferedImage
	 * 
	 * @param image BufferedImage
	 */
	public void set( BufferedImage image )
	{
		try
		{
			this.textureData = AWTTextureIO.newTextureData( this.engine.getProfile(), image, false );
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
	}
	
	/**
	 * Methode erzeugt eine Texture aus einem InputStream
	 * 
	 * @param stream InputStream
	 * @param postfix Dateityp
	 */
	public void set( InputStream stream, String postfix )
	{
		try
		{
			this.textureData = TextureIO.newTextureData( this.engine.getProfile(), stream, false, postfix );
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
	}	
	
	/**
	 * Methode prueft ob die Texture aktualisiert werden soll
	 * 
	 * @return Liefert true, wenn die Texture aktualisiert werden soll, sonst false
	 */
	public boolean inUpdate()
	{
		return this.textureData != null;
	}
	
	/**
	 * Methode prueft, ob die Texture verwendet werden kann
	 * 
	 * @return Liefert true, wenn die Texture verwendet werden kann, sonst false
	 */
	public boolean isUsable()
	{
		return this.texture != null;
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#update(javax.media.opengl.GL2)
	 */
	@Override
	public void update( GL2 gl ) 
	{
		if( this.textureData != null )
		{
			this.texture = TextureIO.newTexture( this.textureData );
			this.textureData = null;
		}
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#bind(javax.media.opengl.GL2)
	 */
	@Override
	public void bind( GL2 gl ) 
	{
		if( this.texture == null )
			return;
		
		gl.glEnable( GL2.GL_TEXTURE_2D );
		
		this.texture.bind( gl );
		
		gl.glDisable( GL2.GL_TEXTURE_2D );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#getTexture()
	 */
	public int getTexture()
	{
		//TODO Moeglichkeit die ID der Texture zu liefern
		return 0;//this.texture;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#getTextureCoords()
	 */
	public TextureCoords getTextureCoords()
	{
		if( this.texture != null )
			return texture.getImageTexCoords();
			
		return new TextureCoords( 0f, 0f, 0f, 0f );
	}
}