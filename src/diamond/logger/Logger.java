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
package diamond.logger;

import java.io.File;
import java.io.FileWriter;

import diamond.DiamondEngine;

/**
 * Die Klasse Logger dient zum Aufzeichnen
 * von Errors, Warnungen und Informationen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Logger 
{
	/*
	 * Speichert LogOutput und LogLevel
	 */
	private LogOutput output;
	private LogLevel level;
	
	/*
	 * Variablen zum Speichern in einer Datei
	 */
	private File file;
	private FileWriter writer;
	
	/*
	 * Allgemeine Variablen
	 */
	private boolean initConsole;
	private boolean initFile;
	private String name;
	
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Logger( DiamondEngine engine )
	{
		this.initConsole = false;
		this.initFile = false;
		this.name = null;
		
		this.output = LogOutput.NONE;
		this.level = LogLevel.NONE;
	}
	
	/**
	 * Methode setzt die gewuenschte Ausgabe
	 * 
	 * @param output gewuenschte Ausgabe
	 */
	public void setOutput( LogOutput output )
	{
		this.output = output;
	}
	
	/**
	 * Methode setzt das gewuenschte LogLevel
	 * 
	 * @param level gewuenschtes Level
	 */
	public void setLogLevel( LogLevel level )
	{
		this.level = level;
	}
	
	/**
	 * Methode setzt das LogLevel auf Information
	 */
	public void setLogLevelInformation()
	{
		this.level = LogLevel.INFORMATION;
	}
	
	/**
	 * Methode setzt das LogLevel auf Warning
	 */
	public void setLogLevelWarning()
	{
		this.level = LogLevel.WARNING;
	}
	
	/**
	 * Methode setzt das LogLevel auf Error
	 */
	public void setLogLevelError()
	{
		this.level = LogLevel.ERROR;
	}
	
	/**
	 * Methode deaktiviert das Loggen
	 */
	public void setLogLevelNone()
	{
		this.level = LogLevel.NONE;
	}
	
	/**
	 * Methode initiert den Logger
	 */
	public void init()
	{
		this.initFile = true;
		this.initConsole = true;
		this.name = "log/" + System.currentTimeMillis() + ".log";
	}
	
	/**
	 * Methode zerstoert den Logger
	 */
	public void dispose()
	{
		try
		{
			if( this.writer != null )
				this.writer.close();
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
	}
	
	/**
	 * Methode erstellt einen Log-Eintrag
	 * 
	 * @param level LogLevel
	 * @param message Logeintrag
	 */
	public void log( LogLevel level, String message )
	{
		if( this.initFile && ( this.output == LogOutput.BOTH || this.output == LogOutput.FILE ))
			this.initFileLogger();
		
		if( this.initConsole && ( this.output == LogOutput.BOTH || this.output == LogOutput.CONSOLE ))
			this.initConsoleLogger();
		
		if( level == LogLevel.NONE || this.output == LogOutput.NONE || this.level == LogLevel.NONE )
			return;
		
		if( level.ordinal() >= this.level.ordinal() )
		{
			try
			{
				String logEntry = "LogLevel " + level + ": " +  message;
				if( this.output == LogOutput.BOTH || this.output == LogOutput.CONSOLE )
				{
					System.out.println( logEntry );
				}
				if( this.output == LogOutput.BOTH || this.output == LogOutput.FILE )
				{
					this.writer.write( logEntry );
					this.writer.write( System.getProperty( "line.separator" ));
					this.writer.flush();
				}
			}
			catch( Exception exc )
			{
				exc.printStackTrace();
			}
		}
	}
	
	/**
	 * Initialisiert das Loggen in die Konsole
	 */
	private void initConsoleLogger()
	{
		if( !this.initConsole )
			return;
		
		System.out.println( "Diamond Engine | Logfile | LogLevel: " + this.level );
		
		this.initConsole = false;
	}
	
	/**
	 * Initialisiert das Loggen in die Datei
	 */
	private void initFileLogger()
	{
		if( !this.initFile )
			return;
		
		this.file = new File( this.name );
		try 
		{
			this.file.createNewFile();
			this.writer = new FileWriter( this.file, true );	
			this.writer.write( "Diamond Engine | Logfile | LogLevel: " + this.level );
			this.writer.write( System.getProperty( "line.separator" ));
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
		finally
		{
			this.initFile = false;
		}
	}
}