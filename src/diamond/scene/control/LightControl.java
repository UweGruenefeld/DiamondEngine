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

import diamond.Constant;
import diamond.DiamondEngine;
import diamond.scene.Scene;
import diamond.scene.light.Light;

/**
 * Die Klasse LightManager verwaltet die eingesetzten Lichter
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class LightControl extends Control
{
	/*
	 * Allgemeine Attribute
	 */
	private Light[] light;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public LightControl( DiamondEngine engine )
	{
		super( engine );
		
		this.light = new Light[Constant.defaultMaxLight];
	}
	
	/**
	 * Methode erlaubt das Einstellen eines Lichtes
	 * 
	 * @param element Lichtnummer
	 * @param light Licht abgeleitet von der abstrakten Klasse Light
	 */
	public void set( int element, Light light )
	{
		if( element >= 0 && element < this.light.length )
			this.light[element] = light;
	}
	
	/**
	 * Methode liefert ein Licht
	 * 
	 * @param element Lichtnummer
	 * @return Liefert das gewuenschte Licht
	 */
	public Light get( int element )
	{
		if( element >= 0 && element < this.light.length )
			return this.light[element];
		
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#init(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void init( GLAutoDrawable drawable, Scene scene ) 
	{
		this.display( drawable, scene );
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.control.Control#display(javax.media.opengl.GLAutoDrawable, diamond.scene.Scene)
	 */
	@Override
	public void display( GLAutoDrawable drawable, Scene scene ) 
	{
		GL2 gl = drawable.getGL().getGL2();
		
		if( ! this.isEnabled() )
		{
			gl.glDisable( GL2.GL_LIGHTING );
			return;
		}
		
		gl.glEnable( GL2.GL_LIGHTING );
		
		for( int i=0; i<this.light.length; i++ )
		{
			if( this.light[i] == null )
				gl.glDisable( GL2.GL_LIGHT0 + i );
			else
				this.light[i].display( gl, i );
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
}