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

import java.util.HashSet;
import java.util.Iterator;

import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.awt.TextRenderer;

import diamond.DiamondEngine;
import diamond.manager.ShaderManager;
import diamond.scene.Scene;
import diamond.scene.text.Text;
import diamond.scene.text.TextPosition;

/**
 * Die Klasse TextManager verwaltet Texte
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class TextControl extends Control
{
	/*
	 * Text-Variablen
	 */
	private TextRenderer textRenderer;
	private final HashSet<Text> text;

	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public TextControl( DiamondEngine engine )
	{
		super( engine );
		
		this.text = new HashSet<Text>();
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
	@Override
	public void display( GLAutoDrawable drawable, Scene scene )
	{
		if( !this.isEnabled() )
			return;
		
		ShaderManager.noProgram( drawable.getGL().getGL2() );
		
		Iterator<Text> iterator = this.text.iterator();
		while( iterator.hasNext() )
		{
			Text text = iterator.next();
			
			this.textRenderer = new TextRenderer( text.getFont() );	
			
			if( text.isText3D() )
				this.textRenderer.begin3DRendering();
			else
				this.textRenderer.beginRendering( drawable.getWidth(), drawable.getHeight() );
			
			this.textRenderer.setColor( text.getColor().getRed(), text.getColor().getGreen(), 
					text.getColor().getBlue(), text.getColor().getAlpha() );	
			
			int height = drawable.getHeight();
			int width = drawable.getWidth();
			int x = text.getX();
			int y = text.getY();
			
			if( text.getPosition() == TextPosition.TOP_LEFT )
			{
				y += height;
			}
			else if( text.getPosition() == TextPosition.TOP_RIGHT )
			{
				x += width;
				y += height;
			}
			else if( text.getPosition() == TextPosition.CENTER )
			{
				x += width / 2;
				y += height / 2;
			}
			else if( text.getPosition() == TextPosition.BOTTOM_RIGHT )
			{
				y += height;
			}
			
			if( text.isText3D() )
			{
				this.textRenderer.draw3D( text.getValue(), text.getX(), text.getY(), 
						text.getZ(), text.getScale() );
				this.textRenderer.end3DRendering();
			}
			else
			{
				this.textRenderer.draw( text.getValue(), x, y );
				this.textRenderer.endRendering();
			}
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#dispose(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void dispose( GLAutoDrawable drawable, Scene scene ) 
	{	
	}
	
	/**
	 * Methode fuegt einen Text hinzu
	 * 
	 * @param text Text
	 */
	public void add( Text text )
	{
		this.text.add( text );
	}
	
	/**
	 * Methode entfernt einen Text
	 * 
	 * @param text Text
	 */
	public void delete( Text text )
	{	
		this.text.remove( text );
	}
}