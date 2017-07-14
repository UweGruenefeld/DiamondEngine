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
package diamond.init;

import diamond.DiamondEngine;
import diamond.graphics.Settings;
import diamond.logger.Logger;
import diamond.manager.ShaderManager;
import diamond.manager.TextureManager;

/**
 * Interface zur Initialisierung aller grundlegender Funktionalitaeten der DiamondEngine
 * Wird vom Engine-Verwender implementiert um die Engine zu initialisieren
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public interface InitGlobal 
{
	/**
	 * Methode dient zur Initialiserung des Loggers
	 * z.B Welches LogLevel verwendet wird
	 * 
	 * @param engine Referenz auf die DiamondEngine
	 * @param logger Referenz auf die Logger-Instanz
	 */
	public abstract void initLogger( DiamondEngine engine, Logger logger );
	
	/**
	 * Methode dient zur Initialisierung der Einstellungen der Engine
	 * z.B Um die Anzeige der FPS einzuschalten
	 * 
	 * @param engine Referenz auf die DiamondEngine
	 * @param settings Referenz auf den Settings Manager
	 */
	public abstract void initSettings( DiamondEngine engine, Settings settings );
	
	/**
	 * Methode dient zum initialisieren von Shadern
	 * 
	 * @param engine Referenz auf die DiamondEngine
	 * @param shader Referenz auf den Shader-Manager
	 */
	public abstract void initShader( DiamondEngine engine, ShaderManager shader );
	
	/**
	 * Methode dient zum initialisieren von Texturen
	 * 
	 * @param engine Referenz auf die DiamondEngine
	 * @param texture Referenz auf den Texture Manager
	 */
	public abstract void initTexture( DiamondEngine engine, TextureManager texture );
}