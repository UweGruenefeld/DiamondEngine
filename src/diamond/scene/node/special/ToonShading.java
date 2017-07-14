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
import diamond.manager.shader.Shader;
import diamond.scene.Scene;
import diamond.scene.node.Node;
import diamond.util.graphics.complexion.Color;
import diamond.util.graphics.complexion.DefaultColor;
import diamond.util.graphics.complexion.Display;

/**
 * Die Klasse ToonShading ermoeglicht es Objekte im Comic-Aussehen zu zeichnen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class ToonShading extends Node
{
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public ToonShading( DiamondEngine engine ) 
	{
		super( engine );
		
		this.setShader( engine.getShaderManager().get( Shader.NODE_TOONSHADING ));
		this.enableShader();
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.util.graphics.complexion.Complexion#display(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void display( GL2 gl, Scene scene )
	{
		Display display = this.getDisplay();
		Color color = this.getColor();
		
		this.setDisplay( Display.COLOR );
		this.setColor( new Color( DefaultColor.BLACK ));
		this.disableShader();
		
		gl.glFrontFace( GL2.GL_CW );
		gl.glPolygonMode( GL2.GL_FRONT, GL2.GL_LINE );
		gl.glLineWidth( 5 );
		
		super.display( gl, scene );
		
		gl.glLineWidth( 1 );
		gl.glPolygonMode( GL2.GL_FRONT, GL2.GL_FILL );
		gl.glFrontFace( GL2.GL_CCW );
		
		this.enableShader();
		this.setColor( color );
		this.setDisplay( display );
		
		super.display( gl, scene );
	}
}