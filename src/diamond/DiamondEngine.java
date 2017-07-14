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
package diamond;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import diamond.graphics.Animator;
import diamond.graphics.Renderer;
import diamond.graphics.Settings;
import diamond.init.CombinedInit;
import diamond.init.InitCanvas;
import diamond.init.InitGlobal;
import diamond.init.InitPlugin;
import diamond.logger.LogLevel;
import diamond.logger.Logger;
import diamond.manager.ModelManager;
import diamond.manager.PluginManager;
import diamond.manager.RenderManager;
import diamond.manager.AssetManager;
import diamond.manager.ShaderManager;
import diamond.manager.TextureManager;
import diamond.manager.TimeManager;
import diamond.scene.Scene;

/**
 * Hauptklasse der Diamond Engine
 * Eine Instanz dieser Klasse erzeugt eine Instanz der Diamond Engine
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class DiamondEngine 
{
	/*
	 * Status der Engine
	 */
	GlobalState state;
	
	/*
	 * OpenGL(JOGL) abhaengige Variablen
	 */
	private final GLProfile profile;
	private final GLCanvas canvas;
	
	/*
	 * Logger und Settings
	 */
	private final Logger logger;
	
	/*
	 * Initiator
	 * Zum Initialisieren der Diamond Engine
	 */
	private InitGlobal initGeneral;
	private InitCanvas initCanvas;
	private InitPlugin initPlugin;
	
	/*
	 * Elementare Grafik-Objekte
	 */
	private final Settings settings;
	private final Animator animator;
	private final RenderManager renderManager;
	private final Renderer render;
	
	/*
	 * Verschiedene Manager für unterschiedliche Aufgaben
	 */
	private final AssetManager asset;
	private final ModelManager model;
	private final ShaderManager shader;
	private final TextureManager texture;
	private final TimeManager time;

	/*
	 * Plugin Manager
	 * Zum Verwalten von Plugins
	 */
	private final PluginManager plugin;
	
	/**
	 * Default-Konstruktor
	 * Wird benoetigt um eine Instanz der Diamond Engine zu erzeugen
	 */
	public DiamondEngine()
	{
		this.state = GlobalState.UNDEFINED;
		
		this.profile = this.initGLProfile();
		this.canvas = this.initGLCanvas();
		
		this.logger = new Logger( this );
		this.logger.init();
		
		this.settings = new Settings( this );
		this.animator = new Animator( this );
		this.renderManager = new RenderManager( this );
		this.render = new Renderer( this );

		this.asset = new AssetManager( this );
		this.model = new ModelManager( this );
		this.shader = new ShaderManager( this );
		this.texture = new TextureManager( this );
		this.time = new TimeManager( this );
		
		this.plugin = new PluginManager( this );
	}
	
	/**
	 * Methode liefert den Status der Engine
	 * 
	 *  @return Liefert den Status der Engine
	 */
	public GlobalState getState()
	{
		return this.state;
	}
	
	/**
	 * ACHTUNG! Methode dient nur zur internen Verwendung
	 */
	public void rendererInitiated()
	{
		if( this.state != GlobalState.STARTUP )
		{
			this.logger.log( LogLevel.ERROR, "Die Methode rendererInitiated() ist nur zur internen Verwendung gedacht" );
			return;
		}
		this.state = GlobalState.RUNNING;
	}
	
	/**
	 * Liefert die GLProfile Instanz und nimmt die noetigen Einstellungen vor
	 * 
	 * @return Liefert eine Referenz auf das GLProfile
	 */
	private GLProfile initGLProfile()
	{
		GLProfile.initSingleton();
    	GLProfile profile = GLProfile.get( GLProfile.GL2 );
    	
    	return profile;
	}
	
	/**
	 * Erzeugt ein GLCanvas und nimmt die nötigen Einstellungen vor
	 * 
	 * @return Liefert das erzeugte GLCanvas
	 */
	private GLCanvas initGLCanvas()
	{
    	GLCapabilities caps = new GLCapabilities( this.profile );
    	
    	caps.setRedBits( 8 );
        caps.setBlueBits( 8 );
        caps.setGreenBits( 8 );
        caps.setAlphaBits( 8 );
   
        GLCanvas canvas = new GLCanvas( caps );
        
        canvas.setFocusable( true );
        canvas.requestFocus();
    	
    	return canvas;
	}
	
	/**
	 * Initialisiert alle moeglichen Einstellungen
	 * 
	 * @param c Erfordert ein Objekt das CombinedInit implementiert hat
	 */
	public void registerCombinedInit( Class<? extends CombinedInit> c )
	{	
		if( this.state != GlobalState.UNDEFINED )
		{
			System.err.println("ERROR: Initialisierung der Engine bereits ausgefuehrt" );
			return;
		}

		this.registerInitGeneral( c );
		this.registerInitCanvas( c );
		this.registerInitPlugin( c );
		
		this.state = GlobalState.INITIATED;
	}
	
	/**
	 * Initialisiert alle allgemeinen Einstellungen
	 * 
	 * @param c Erfordert ein Objekt das InitGeneral implementiert hat
	 */
	public void registerInitGeneral( Class<? extends InitGlobal> c )
	{
		if( this.state != GlobalState.UNDEFINED )
		{
			System.err.println("ERROR: Initialisierung der Engine bereits ausgefuehrt" );
			return;
		}
		
		try
		{
			this.initGeneral = c.newInstance();
		}
		catch( Exception exc )
		{
			System.err.println("ERROR: InitGeneral wurde nicht korrekt zugewiesen" );
			return;
		}
		
		this.initGeneral.initLogger( this, this.logger );
		this.logger.log( LogLevel.INFORMATION, "Logger wurd gestartet" );
		this.initGeneral.initSettings( this, this.settings );
		this.initGeneral.initShader( this, this.shader );
		this.initGeneral.initTexture( this, this.texture );
	}
	
	/**
	 * Initialisiert das GLCanvas
	 * ACHTUNG: Muss nach registerInitGeneral( InitGeneral initGeneral ) aufgerufen werden!
	 * 
	 * @param c Erfordert ein Objekt das InitCanvas implementiert hat
	 */
	public void registerInitCanvas( Class<? extends InitCanvas> c )
	{
		if( this.initGeneral == null )
		{
			this.logger.log( LogLevel.ERROR, "Reihenfolge der Init-Aufrufe beachten" );
			return;
		}
		
		try
		{
			this.initCanvas = c.newInstance();
		}
		catch( Exception exc )
		{
			this.logger.log( LogLevel.ERROR, "InitCanvas wurde nicht korrekt zugewiesen" );
			return;
		}

		this.initCanvas.initCanvas( this, this.canvas );
	}
	
	/**
	 * Initialisiert die gewuenschten Plugins
	 * ACHTUNG: Muss nach registerInitCanvas( InitGeneral initGeneral ) und
	 * registerInitCanvas( InitCanvas initCanvas ) aufgerufen werden!
	 * 
	 * @param c Erfordert ein Objekt das InitPlugin implementiert hat
	 */
	public void registerInitPlugin( Class<? extends InitPlugin> c )
	{
		if( this.initGeneral == null || this.initCanvas == null )
		{
			System.err.println("ERROR: Reihenfolge der Init-Aufrufe beachten" );
			return;
		}
		
		try
		{
			this.initPlugin = c.newInstance();
		}
		catch( Exception exc )
		{
			System.err.println("ERROR: InitPlugin wurde nicht korrekt zugewiesen" );
			return;
		}

		this.initPlugin.initPlugin( this, this.plugin );
		
		this.state = GlobalState.INITIATED;
	}
	
	/**
	 * Methode startet den Animator und damit das Zeichnen in das GLCanvas durch die Engine
	 */
	public void run()
	{
		if( this.state != GlobalState.INITIATED )
		{
			this.logger.log( LogLevel.ERROR, "Engine kann nicht gestartet werden, weil sie nicht korrekt initialisiert wurde" );
			return;
		}
		
		this.canvas.addGLEventListener( this.render );
		this.animator.init();
	
		this.state = GlobalState.STARTUP;
	}
	
	/**
	 * Methode vernichtet den Animator und stoppt damit das Zeichnen in das GLCanvas
	 */
	public void dispose()
	{
		this.state = GlobalState.DISPOSE;
		
		this.animator.dispose();
		this.logger.dispose();
	}

	/**
	 * Liefert das GLCanvas
	 * 
	 * @return Liefert das GLCanvas
	 */
	public GLCanvas getCanvas() 
	{
		return this.canvas;
	}
	
	/**
	 * Liefert das GLProfile
	 * 
	 * @return Liefert das GLProfile
	 */
	public GLProfile getProfile()
	{
		return this.profile;
	}
	
	/**
	 * Liefert den Logger
	 * 
	 * @return Liefert den Logger
	 */
	public Logger getLogger()
	{
		return this.logger;
	}
	
	/**
	 * Liefert den Settings Manager
	 * 
	 * @return Liefert den Settings Manager
	 */
	public Settings getSettingsManager()
	{
		return this.settings;
	}
	
	/**
	 * Liefert den Render Manager
	 * 
	 * @return Liefert den Render Manager
	 */
	public RenderManager getRenderManager()
	{
		return this.renderManager;
	}

	/**
	 * Liefert den Asset Manager
	 * 
	 * @return Liefert den Asset Manager
	 */
	public AssetManager getAssetManager()
	{
		return this.asset;
	}
	
	/**
	 * Liefert den Model Manager
	 * 
	 * @return Liefert den Model Manager
	 */
	public ModelManager getModelManager()
	{
		return this.model;
	}
	
	/**
	 * Liefert den Shader Manager
	 * 
	 * @return Liefert den Shader Manager
	 */
	public ShaderManager getShaderManager()
	{
		return this.shader;
	}

	/**
	 * Liefert den Texture Manager
	 * 
	 * @return Liefert den Texture Manager
	 */
	public TextureManager getTextureManager()
	{
		return this.texture;
	}
	
	/**
	 * Liefert den Time Manager
	 * 
	 * @return Liefert den Time Manager
	 */
	public TimeManager getTimeManager()
	{
		return this.time;
	}
	
	/**
	 * Liefert den Plugin Manager
	 * 
	 * @return Liefert den Plugin Manager
	 */
	public PluginManager getPluginManager()
	{
		return this.plugin;
	}
	
	/**
	 * Diese Methode stellt eine Vereinfachung dar und erlaubt den
	 * schnellen Zugriff auf die Default View
	 * 
	 * @return Liefert die Default View
	 */	
	public Scene getDefaultView()
	{
		return this.renderManager.getDefaultScene();
	}
}