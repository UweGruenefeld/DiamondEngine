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
package test.shadow;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import diamond.DiamondEngine;
import diamond.graphics.Settings;
import diamond.init.CombinedInit;
import diamond.logger.LogOutput;
import diamond.logger.Logger;
import diamond.manager.PluginManager;
import diamond.manager.ShaderManager;
import diamond.manager.TextureManager;
import diamond.manager.texture.CubeTexture;
import diamond.manager.texture.StandardTexture;
import diamond.plugin.cameracontrol.CameraControlPlugin;

/**
 * Die Klasse Initiator initilisiert die Engine
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Initiator implements CombinedInit
{
	/*
	 * Cube-Texture
	 */
	public static CubeTexture SKYBOX; 
	
	/*
	 * Standart-Texturen
	 */
	public static StandardTexture WATER, SAND, GRASS;
	
	/**
	 * (non-Javadoc)
	 * @see diamond.init.InitGlobal#initSettings(diamond.DiamondEngine, diamond.graphics.Settings)
	 */
	@Override
	public void initSettings( DiamondEngine engine, Settings settings ) 
	{
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.init.InitGlobal#initLogger(diamond.DiamondEngine, diamond.logger.Logger)
	 */
	@Override
	public void initLogger( DiamondEngine engine, Logger logger )
	{
		logger.setLogLevelInformation();
		logger.setOutput( LogOutput.CONSOLE );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.init.InitGlobal#initShader(diamond.DiamondEngine, diamond.manager.ShaderManager)
	 */
	@Override
	public void initShader( DiamondEngine engine, ShaderManager shader ) 
	{
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.init.InitGlobal#initTexture(diamond.DiamondEngine, diamond.manager.TextureManager)
	 */
	@Override
	public void initTexture( DiamondEngine engine, TextureManager texture ) 
	{
		Initiator.SKYBOX = new CubeTexture( engine, "blue/east.png", "blue/west.png", "blue/top.png", "blue/bottom.png",
				"blue/north.png", "blue/south.png", "png" );
		texture.add( Initiator.SKYBOX );
		
		Initiator.WATER = new StandardTexture( engine, "water.png", "png" );
		texture.add( Initiator.WATER );
		Initiator.SAND = new StandardTexture( engine, "sand.png", "png" );
		texture.add( Initiator.SAND );
		Initiator.GRASS = new StandardTexture( engine, "grass.png", "png" );
		texture.add( Initiator.GRASS );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.init.InitPlugin#initPlugin(diamond.DiamondEngine, diamond.manager.PluginManager)
	 */
	@Override
	public void initPlugin( DiamondEngine engine, PluginManager plugin ) 
	{
		plugin.add( new CameraControlPlugin( engine ));
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.init.InitCanvas#initCanvas(diamond.DiamondEngine, javax.media.opengl.awt.GLCanvas)
	 */
	@Override
	public void initCanvas( DiamondEngine engine, GLCanvas canvas ) 
	{
		final JFrame frame = new JFrame( "Diamond Engine" );
		frame.add( canvas );
		
		// Vollbild
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds( 0, 0, screenSize.width, screenSize.height );
        frame.setUndecorated( true );
		
		// Fenster
		/*
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds( 50, 50, screenSize.width - 100, screenSize.height - 100 );
        */
		
		final DiamondEngine e = engine;
		frame.addWindowListener( new WindowAdapter() 
		{
			@Override 
			public void windowClosing ( WindowEvent event ) 
			{
				e.dispose();
				System.exit( 0 );
			}
		});
		frame.setVisible( true );
	}
}