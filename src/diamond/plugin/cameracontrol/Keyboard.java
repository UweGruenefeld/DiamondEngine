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
package diamond.plugin.cameracontrol;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import diamond.DiamondEngine;

/**
 * Die Klasse Keyboard ist Teil des CameraControlPlugins
 * Sie regelt die Kamerasteuerung mit Hilfe der Tastatur
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Keyboard implements KeyListener 
{
	/*
	 * Referenz auf die Engine
	 */
	private final DiamondEngine engine;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Keyboard( DiamondEngine engine ) 
	{ 
		this.engine = engine;
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed( KeyEvent event ) 
	{
		int keyCode = event.getKeyCode();
		
		switch ( keyCode ) 
		{
			case KeyEvent.VK_ESCAPE: 
				this.engine.dispose();
				System.exit( 0 );
				break;
				
			// Translate Z-Axis
			case KeyEvent.VK_UP:
				this.engine.getDefaultView().setTranslateZAnimation( 1f );
				break;
				
			case KeyEvent.VK_DOWN:
				this.engine.getDefaultView().setTranslateZAnimation( -1f );
				break;
				
			// Translate X-Axis
			case KeyEvent.VK_RIGHT:
				this.engine.getDefaultView().setTranslateXAnimation( 1f );
				break;
				
			case KeyEvent.VK_LEFT:
				this.engine.getDefaultView().setTranslateXAnimation( -1f );
				break;
				
			// Translate Y-Axis
			case KeyEvent.VK_R:
				this.engine.getDefaultView().setTranslateYAnimation( 1f );
				break;
				
			case KeyEvent.VK_F:
				this.engine.getDefaultView().setTranslateYAnimation( -1f );
				break;
				
				
			// Rotate X-Axis
			case KeyEvent.VK_W:
				this.engine.getDefaultView().setRotateXAnimation( 5f );
				break;
				
			case KeyEvent.VK_S:
				this.engine.getDefaultView().setRotateXAnimation( -5f );
				break;
				
			// Rotate Y-Axis
			case KeyEvent.VK_D:
				this.engine.getDefaultView().setRotateYAnimation( 5f );
				break;
				
			case KeyEvent.VK_A:
				this.engine.getDefaultView().setRotateYAnimation( -5f );
				break;
				
			// Rotate Z-Axis
			case KeyEvent.VK_E:
				this.engine.getDefaultView().setRotateZAnimation( 5f );
				break;
				
			case KeyEvent.VK_Q:
				this.engine.getDefaultView().setRotateZAnimation( -5f );
				break;
				
			// Reset
			case KeyEvent.VK_G:	
				this.engine.getDefaultView().reset();
				break;
		}
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased( KeyEvent event ) 
	{ 
		int keyCode = event.getKeyCode();
		
		switch ( keyCode ) 
		{
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				this.engine.getDefaultView().setTranslateZAnimation( 0f );
				break;
				
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
				this.engine.getDefaultView().setTranslateXAnimation( 0f );
				break;
				
			case KeyEvent.VK_R:
			case KeyEvent.VK_F:
				this.engine.getDefaultView().setTranslateYAnimation( 0f );
				break;
				
			case KeyEvent.VK_W:
			case KeyEvent.VK_S:
				this.engine.getDefaultView().setRotateXAnimation( 0f );
				break;
				
			case KeyEvent.VK_A:
			case KeyEvent.VK_D:
				this.engine.getDefaultView().setRotateYAnimation( 0f );
				break;				
				
			case KeyEvent.VK_Q:
			case KeyEvent.VK_E:
				this.engine.getDefaultView().setRotateZAnimation( 0f );
				break;
		}
	}

	/**
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped( KeyEvent event )
	{ 
	}
}