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
package diamond.graphics;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import diamond.Constant;
import diamond.DiamondEngine;
import diamond.manager.RenderManager;

//TODO Die Einstellungen sollten in eine eigenständige Klasse ausgelagert werden
// z.B. Smooth, DepthTest u.s.w

/**
 * Die Klasse Renderer definiert die fuer OpenGL(JOGL) wichtigen Methoden
 * zum korrekten Zeichnen der einzelnen Frames
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Renderer implements GLEventListener
{
	/*
	 * Referenzen auf Engine-Funktionalitaeten
	 */
	private final DiamondEngine engine;
	private final RenderManager renderManager;

	/**
	 * Default Konstruktor
	 * 
	 * @param engine Referenz auf die Diamond Engine
	 */
	public Renderer( DiamondEngine engine ) 
	{		
		this.engine = engine;
		this.renderManager = engine.getRenderManager();
	}
	
	/**
	 * Methode zum Initialisieren der Grafik
	 * 
	 * @param drawable Bekommt ein GLAutoDrawable uebergeben
	 */
	@Override
	public void init( GLAutoDrawable drawable ) 
	{
		GL2 gl = drawable.getGL().getGL2();
		this.renderManager.setGL( gl );
		
		gl.setSwapInterval( 1 );
		gl.glClearColor( 0f, 0f, 0f, 1f );

		// Settings
		gl.glShadeModel( GL2.GL_SMOOTH );
		gl.glEnable( GL2.GL_DEPTH_TEST );
		gl.glDepthFunc( GL2.GL_LEQUAL );
		gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );
		
		// Inits
		this.engine.getAssetManager().init( drawable );
		this.engine.getModelManager().init( drawable );
		this.engine.getPluginManager().init( drawable );
		this.engine.getShaderManager().init( drawable );
		this.engine.getTextureManager().init( drawable );
		this.engine.getTimeManager().init( drawable );
		
		// Render Manager
		this.engine.getRenderManager().init( drawable );
		
		this.engine.rendererInitiated();
	}
	
	/**
	 * Methode zum Darstellen der Grafik
	 * 
	 * @param drawable Bekommt ein GLAutoDrawable uebergeben
	 */
	@Override
	public void display( GLAutoDrawable drawable ) 
	{
		GL2 gl = drawable.getGL().getGL2();
		this.renderManager.setGL( gl );

		// Display / Update
		this.engine.getAssetManager().display( drawable );
		this.engine.getModelManager().display( drawable );
		this.engine.getPluginManager().display( drawable );
		this.engine.getShaderManager().display( drawable );
		this.engine.getTextureManager().display( drawable );
		this.engine.getTimeManager().display( drawable );
		
		// Render Manager
		this.engine.getRenderManager().display( drawable );		
	}
	
	/**
	 * Methode zum Anpassen der Grafik
	 * 
	 * @param drawable Bekommt ein GLAutoDrawable uebergeben
	 */
	@Override
	public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height ) 
	{
		GL2 gl = drawable.getGL().getGL2();
		this.renderManager.setGL( gl );
		
		GLU glu = new GLU();
		
		if( height == 0 )
			height = 1;
		
		gl.glMatrixMode( GL2.GL_PROJECTION );
		gl.glLoadIdentity();
		
		float aspect = (float) width / (float) height;
		glu.gluPerspective( Constant.perspectiveAngle, aspect, 
				Constant.perspectiveNear, Constant.perspectiveFar );
		
		gl.glMatrixMode( GL2.GL_MODELVIEW );
		gl.glLoadIdentity();
	}

	/**
	 * Methode zum Beenden der Grafik
	 * 
	 * @param drawable Bekommt ein GLAutoDrawable uebergeben
	 */
	@Override
	public void dispose( GLAutoDrawable drawable ) 
	{ 
		// Dispose
		this.engine.getAssetManager().dispose( drawable );
		this.engine.getModelManager().dispose( drawable );
		this.engine.getPluginManager().dispose( drawable );
		this.engine.getShaderManager().dispose( drawable );
		this.engine.getTextureManager().dispose( drawable );
		this.engine.getTimeManager().dispose( drawable );
	}
}