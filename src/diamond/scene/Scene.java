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
package diamond.scene;

import java.util.HashSet;
import java.util.Iterator;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.TextureCoords;

import diamond.Constant;
import diamond.DiamondEngine;
import diamond.manager.ShaderManager;
import diamond.manager.texture.Texture;
import diamond.scene.control.FogControl;
import diamond.scene.control.LightControl;
import diamond.scene.control.PainterControl;
import diamond.scene.control.ShadowControl;
import diamond.scene.control.SkyboxControl;
import diamond.scene.control.TerrainControl;
import diamond.scene.control.TextControl;
import diamond.scene.node.Node;
import diamond.scene.node.RootNode;
import diamond.scene.text.Text;
import diamond.scene.text.TextPosition;

//TODO Stencil Mode fertigstellen

/**
 * Die Klasse Scene ist eine komplexe Oberklasse
 * Jede Scene hat seine eigene Kamera
 * Jede Scene hat seine eigene RootNode um beliebige Objekte aufzunehmen
 * Jede Scene ist auch eine Texture und ein Frame Buffer Object
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public final class Scene extends Camera implements RootNode, Texture
{
	/*
	 * Zustand der Szene
	 */
	private SceneState state;
	
	/*
	 * Controls
	 */
	private final boolean controls;
	private FogControl fog;
	private LightControl light;
	private PainterControl painter;
	private ShadowControl shadow;
	private SkyboxControl skybox;
	private TerrainControl terrain;
	private TextControl text;
	
	/*
	 * Wurzelknoten fuer die RootNode
	 */
	private HashSet<Node> rootNode;
	
	/*
	 * Allgemeine Attribute
	 */
	private boolean enable;
	private Mode mode;
	private int frameBuffer, renderBuffer, texture;
	private int width, height;
	
	/*
	 * FPS-Anzeige
	 */
	private boolean fps;
	private Text fpsText;
	
	/*
	 * Matrizen
	 */
	private float[] cameraProjectionMatrix;
	private float[] cameraModelviewMatrix;
	
	/*
	 * Kamera oder ModelView
	 */
	private boolean camera;
	private float[] cameraMatrix;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Scene( DiamondEngine engine )
	{
		super( engine );
		
		this.controls = true;
		this.setUp();
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Scene( DiamondEngine engine, boolean controls )
	{
		super( engine );
		
		this.controls = controls;
		this.setUp();
	}
	
	/**
	 * Methode erzeugt die Default-Werte einer View
	 */
	private void setUp()
	{
		this.state = SceneState.UNDEFINED;
		
		// Soll die Szene ueber Controls verfuegen?
		if( this.controls )
		{
			this.fog = new FogControl( this.getEngine() );
			this.light = new LightControl( this.getEngine() );
			this.painter = new PainterControl( this.getEngine() );
			this.shadow = new ShadowControl( this.getEngine() );
			this.skybox = new SkyboxControl( this.getEngine() );
			this.terrain = new TerrainControl( this.getEngine() );
			this.text = new TextControl( this.getEngine() );
		}
		
		// Root-Knoten
		this.rootNode = new HashSet<Node>();
		
		// Aktiviere die Szene
		this.enable = true;
		
		this.mode = Mode.DEFAULT;
		this.frameBuffer = -1;
		this.renderBuffer = -1;
		this.texture = -1;
		this.setSize( 0, 0 );
		
		// FPS
		this.fps = false;
		this.fpsText = new Text( "FPS 0", TextPosition.BOTTOM_LEFT );
		this.fpsText.set( 5, 5 );
		
		// Matrizen
		this.cameraModelviewMatrix = new float[16];
		this.cameraProjectionMatrix = new float[16];
		
		// Kamera oder Matrix
		this.camera = true;
		this.cameraMatrix = new float[16];
	}
	
	/**
	 * Methode liefert den aktuellen View-Status
	 * 
	 * @return Liefert den aktuellen View-Status
	 */
	public SceneState getState()
	{
		return this.state;
	}
	
	/**
	 * Methode liefert die FogControl
	 * 
	 * @return Liefert die FogControl
	 */
	public FogControl getFogControl()
	{
		return this.fog;
	}
	
	/**
	 * Methode liefert die LightControl
	 * 
	 * @return Liefert die LightControl
	 */
	public LightControl getLightControl()
	{
		return this.light;
	}
	
	/**
	 * Methode liefert die PainterControl
	 * 
	 * @return Liefert die PainterControl
	 */
	public PainterControl getPainterControl()
	{
		return this.painter;
	}
	
	/**
	 * Methode liefert die ShadowControl
	 * 
	 * @return Liefert die ShadowControl
	 */
	public ShadowControl getShadowControl()
	{
		return this.shadow;
	}
	
	/**
	 * Methode liefert die SkyboxControl
	 * 
	 * @return Liefert die SkyboxControl
	 */
	public SkyboxControl getSkyboxControl()
	{
		return this.skybox;
	}
	
	/**
	 * Methode liefert die TerrainControl
	 * 
	 * @return Liefert die TerrainControl
	 */
	public TerrainControl getTerrainControl()
	{
		return this.terrain;
	}
	
	/**
	 * Methode liefert die TextControl
	 * 
	 * @return Liefert die TextControl
	 */
	public TextControl getTextControl()
	{
		return this.text;
	}
	
	/**
	 * Aktiviere die Szene
	 */
	public void enable()
	{
		this.enable = true;
	}
	
	/**
	 * Deaktiviere die Szene
	 */
	public void disable()
	{
		this.enable = false;
	}
	
	/**
	 * Prueft, ob die Scene aktiv ist
	 * 
	 * @return Liefert true, wenn die View aktiv ist, sonst false
	 */
	public boolean isActive()
	{
		return this.enable;
	}
	
	/**
	 * Setzt den Modus der Szene
	 * 
	 * @param mode Modus: DEFAULT, COLOR, DEPTH, STENCIL
	 */
	public void setMode( Mode mode )
	{
		this.mode = mode;
	}

	/**
	 * Setzt die Groesse der Szene
	 * 
	 * @param width Breite der Szene
	 * @param height Hoehe der Szene
	 */
	public void setSize( int width, int height )
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Methode liefert die Breite der Szene
	 * 
	 * @return Liefert die Breite
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * Methode liefert die Hoehe der Szene
	 * 
	 * @return Liefert die Hoehe
	 */
	public int getHeight()
	{
		return this.height;
	}
	
	/**
	 * Methode ueberprueft ob FPS aktiviert sind
	 * 
	 * @return Liefert true, wenn aktiviert, sonst false
	 */
	public boolean isFPSEnabled()
	{
		return this.fps;
	}
	
	/**
	 * Aktiviert die FPS-Anzeige
	 * ACHTUNG: TextControl muss ebenfalls aktiviert sein
	 */
	public void enableFPS()
	{
		if( this.text == null )
			return;
		
		this.fps = true;
		this.text.add( this.fpsText );
	}
	
	/**
	 * Deaktiviert die FPS-Anzeige
	 */
	public void disableFPS()
	{
		this.fps = false;
		
		if( this.text != null )
			this.text.delete( this.fpsText );
	}
	
	/**
	 * Methode liefert die ModelView-Matrix der Kamera
	 * 
	 * @return Liefert die ModelView-Matrix der Kamera
	 */
	public float[] getCameraModelViewMatrix()
	{
		return this.cameraModelviewMatrix;
	}
	
	/**
	 * Methode liefert die Projektions-Matrix der Kamera
	 * 
	 * @return Liefert die Projektions-Matrix der Kamera
	 */
	public float[] getCameraProjectionMatrix()
	{
		return this.cameraProjectionMatrix;
	}
	
	/**
	 * Methode setzt ob die Kamera oder die Matrix verwendet werden soll
	 * 
	 * @param camera Kamera, wenn true, sonst Matrix
	 */
	public void setCamera( boolean camera )
	{
		this.camera = camera;
	}
	
	/**
	 * Methode ueberprueft ob die Kamera oder Matrix verwendet wird
	 * 
	 * @return Wenn true, dann Kamera, sonst Matrix
	 */
	public boolean isCamera()
	{
		return this.camera;
	}
	
	/**
	 * Methode setzt die Kamera-Matrix
	 * 
	 * @param cameraMatrix Kamera-Matrix
	 */
	public void setCameraMatrix( float[] cameraMatrix )
	{
		this.cameraMatrix = cameraMatrix;
	}
	
	/**
	 * Methode liefert die Kamera-Matrix
	 * 
	 * @return Liefert die Kamera-Matrix
	 */
	public float[] getCameraMatrix()
	{
		return this.cameraMatrix;
	}
	
//	private void checkFrameBufferObject( GL2 gl ) 
//	{
//		int fboStatus = gl.glCheckFramebufferStatus( GL2.GL_FRAMEBUFFER );
//		
//		switch ( fboStatus ) 
//		{
//			case GL2.GL_FRAMEBUFFER_UNSUPPORTED:
//				this.getEngine().getLogger().log( LogLevel.ERROR, "Unsupported framebuffer format" );
//				break;
//			case GL2.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
//				this.getEngine().getLogger().log( LogLevel.ERROR, "Framebuffer incomplete, missing attachment" );
//				break;
//			case GL2.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS:
//				this.getEngine().getLogger().log( LogLevel.ERROR, "Framebuffer incomplete, attached images must have same dimensions" );
//				break;
//			case GL2.GL_FRAMEBUFFER_INCOMPLETE_FORMATS:
//				this.getEngine().getLogger().log( LogLevel.ERROR, "Framebuffer incomplete, attached images must have same format" );
//				break;
//			case GL2.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
//				this.getEngine().getLogger().log( LogLevel.ERROR, "Framebuffer incomplete, missing draw buffer" );
//				break;
//			case GL2.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
//				this.getEngine().getLogger().log( LogLevel.ERROR, "Framebuffer incomplete, missing read buffer" );
//				break;
//			default:
//				return;
//		}
//	}

	/*
	 * ###
	 * Texture-Eigenschaften der Szene
	 * ###
	 */
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#update(javax.media.opengl.GL2)
	 */
	@Override
	public void update( GL2 gl ) 
	{
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#bind(javax.media.opengl.GL2)
	 */
	@Override
	public void bind( GL2 gl ) 
	{	
		gl.glEnable( GL2.GL_TEXTURE_2D );

		gl.glBindTexture( GL2.GL_TEXTURE_2D, this.texture );
		
		gl.glDisable( GL2.GL_TEXTURE_2D );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.texture.Texture#getTexture()
	 */
	public int getTexture()
	{
		return this.texture;
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
	
	/*
	 * ###
	 * RootNode-Eigenschaften der Szene
	 * ### 
	 */
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.RootNode#add(diamond.scene.node.Node)
	 */
	@Override
	public void add( Node node )
	{
		this.rootNode.add( node );
	}
	

	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.RootNode#remove(diamond.scene.node.Node)
	 */
	@Override
	public void remove( Node node )
	{
		this.rootNode.remove( node );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.node.RootNode#get()
	 */
	@Override
	public Node[] get()
	{
		return this.rootNode.toArray( new Node[0] );
	}
	
	/*
	 * ###
	 * Szenen-Routinen
	 * ###
	 */
	
	/**
	 * Methode initialisiert die Buffer und Texturen
	 * 
	 * @param drawable GLAutoDrawable
	 */
	public void init( GLAutoDrawable drawable )
	{
		if( this.state != SceneState.UNDEFINED )
			return;
		
		if( this.controls )
		{
			this.fog.init( drawable, this );
			this.light.init( drawable, this );
			this.painter.init( drawable, this );
			this.shadow.init( drawable, this );
			this.skybox.init( drawable, this );
			this.terrain.init( drawable, this );
			this.text.init( drawable, this );
		}
		
		GL2 gl = drawable.getGL().getGL2();
		
		// Erzeuge Buffer und Texture
		int[] frame = { 0 };
		gl.glGenFramebuffers( 1, frame, 0 );
		this.frameBuffer = frame[0];
		
		int[] render = { 0 };
		gl.glGenRenderbuffers( 1, render, 0 );
		this.renderBuffer = render[0];
		
		int[] tex = { 0 };
		gl.glGenTextures( 1, tex, 0 );		
		this.texture = tex[0];
		
		// Initialisierung abgeschlossen
		this.state = SceneState.INITIATED;
	}
	
	/**
	 * Methode rendert die Scene
	 * 
	 * @param drawable GLAutoDrawable
	 * @param defaultScene Wenn true, dann die Default Scene, sonst false
	 */
	public void display( GLAutoDrawable drawable, boolean defaultScene )
	{
		this.init( drawable );
		
		if( this.state != SceneState.INITIATED )
			return;
		
		if( this.enable == false && !defaultScene )
			return;
		
		this.render( drawable, defaultScene );
		
		// Aktualisiere FPS
		if( this.fps )
			this.fpsText.setValue( "FPS " + (int)this.getEngine().getRenderManager().getFPS() );
	}
	
	/**
	 * Methode zerstoert die Szene
	 * 
	 * @param drawable GLAutoDrawable
	 */
	public void dispose( GLAutoDrawable drawable )
	{
		if( this.controls )
		{
			this.fog.dispose( drawable, this );
			this.light.dispose( drawable, this );
			this.painter.dispose( drawable, this );
			this.shadow.dispose( drawable, this );
			this.skybox.dispose( drawable, this );
			this.terrain.dispose( drawable, this );
			this.text.dispose( drawable, this );
		}
		
		this.state = SceneState.DISPOSED;
	}
		
	/**
	 * Methode rendert die Scene
	 * 
	 * @param drawable GLAutoDrawable
	 * @param defaultScene Default-Scene
	 */
	private void render( GLAutoDrawable drawable, boolean defaultScene )
	{
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();
		
		if( defaultScene ) 
		{
			this.setMode( Mode.DEFAULT );
			this.setSize( drawable.getWidth(), drawable.getHeight() );
			
			// Aktualisiere Projektionsmatrix
			gl.glMatrixMode( GL2.GL_PROJECTION );
			gl.glLoadIdentity();
			glu.gluPerspective( Constant.perspectiveAngle, (float)drawable.getWidth() / (float)drawable.getHeight(), 
					Constant.perspectiveNear, Constant.perspectiveFar );
			gl.glGetFloatv( GL2.GL_PROJECTION_MATRIX, this.cameraProjectionMatrix, 0 );
		}
		else
		{		
			// Aktualisiere Projektionsmatrix
			gl.glMatrixMode( GL2.GL_PROJECTION );
			gl.glLoadIdentity();
			if( this.width == 0 || this.height == 0 )
				glu.gluPerspective( Constant.perspectiveAngle, 1, 
					Constant.perspectiveNear, Constant.perspectiveFar );
			else
				glu.gluPerspective( Constant.perspectiveAngle, this.width / this.height, 
					Constant.perspectiveNear, Constant.perspectiveFar );
			gl.glGetFloatv( GL2.GL_PROJECTION_MATRIX, this.cameraProjectionMatrix, 0 );
			
			// Viewport einstellen
			gl.glPushAttrib( GL2.GL_VIEWPORT_BIT );
			gl.glViewport( 0, 0, this.width, this.height );
			
			// Buffer binden
			gl.glBindFramebuffer( GL2.GL_FRAMEBUFFER, this.frameBuffer );
			gl.glBindRenderbuffer( GL2.GL_RENDERBUFFER, this.renderBuffer );
			gl.glRenderbufferStorage( GL2.GL_RENDERBUFFER, GL2.GL_DEPTH_COMPONENT, this.width, this.height );
			gl.glFramebufferRenderbuffer( GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_RENDERBUFFER, this.renderBuffer );
			
			// Texture binden
			gl.glBindTexture( GL2.GL_TEXTURE_2D, this.texture );
			
			// Color-Modus
			if( this.mode == Mode.COLOR )
			{
				gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST );
				gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST );	
				gl.glTexImage2D( GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA8, this.width, this.height, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, null );
				gl.glFramebufferTexture2D( GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, this.texture, 0 );
			}
			// Depth-Modus
			else if( this.mode == Mode.DEPTH )
			{				
				gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST ); 
				gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST ); 
				gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE );
				gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE );
			    gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_COMPARE_MODE, GL2.GL_NONE );
			    gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_COMPARE_FUNC, GL2.GL_LESS );
			    gl.glTexParameteri( GL2.GL_TEXTURE_2D, GL2.GL_DEPTH_TEXTURE_MODE, GL2.GL_INTENSITY );
			    gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE );

				gl.glTexImage2D( GL2.GL_TEXTURE_2D, 0, GL2.GL_DEPTH_COMPONENT, this.width, this.height, 0, GL2.GL_DEPTH_COMPONENT, GL2.GL_UNSIGNED_INT, null );

				gl.glDrawBuffer( GL2.GL_NONE );
				gl.glReadBuffer( GL2.GL_NONE );
				
				gl.glFramebufferTexture2D( GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_TEXTURE_2D, this.texture, 0 );
				
				gl.glShadeModel( GL2.GL_FLAT );
				gl.glDisable( GL2.GL_LIGHTING );
				gl.glDisable( GL2.GL_NORMALIZE );
			}
			// Stencil-Modus
			else if( this.mode == Mode.STENCIL )
			{
				gl.glTexImage2D( GL2.GL_TEXTURE_2D, 0, GL2.GL_STENCIL_BUFFER_BIT, this.width, this.height, 0, GL2.GL_STENCIL_BUFFER_BIT, GL2.GL_UNSIGNED_INT, null );
				gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_STENCIL_ATTACHMENT, GL2.GL_TEXTURE_2D, this.texture, 0);
			}
		}
		
		// FogControl
		if( this.controls )
			this.fog.display( drawable, this );
		gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
		
		// Matrix Mode
		gl.glMatrixMode( GL2.GL_MODELVIEW );
		gl.glLoadIdentity();
	
		// Kamera rendern
		if( this.camera )
			super.render( gl );
		else
			gl.glLoadMatrixf( this.cameraMatrix, 0 );
		
		// Aktualisiere ModelView-Matrix
		gl.glGetFloatv( GL2.GL_MODELVIEW_MATRIX, this.cameraModelviewMatrix, 0 );

		ShaderManager.noProgram( gl );
		
		// Controls rendern
		if( this.controls )
		{
			this.light.display( drawable, this );
			this.painter.display( drawable, this );
			this.shadow.display( drawable, this );
			this.skybox.display( drawable, this );
			this.terrain.display( drawable, this );
		}
				
		// Zeichne die Nodes dieses RootNodes
		Iterator<Node> iterator = this.rootNode.iterator();
		while( iterator.hasNext() )
		{	
			iterator.next().display( gl, this );
		}
		
		ShaderManager.noProgram( gl );
		
		// TextControl
		if( this.controls )
		{
			this.text.display( drawable, this );
		}
				
		if( !defaultScene ) 
		{
			gl.glBindFramebuffer( GL2.GL_FRAMEBUFFER, 0 );
			gl.glPopAttrib();
			
			if( this.mode == Mode.DEPTH )
			{
				gl.glEnable( GL2.GL_NORMALIZE );
				gl.glEnable( GL2.GL_LIGHTING );
				gl.glShadeModel( GL2.GL_SMOOTH );
			}
		}
	}
}