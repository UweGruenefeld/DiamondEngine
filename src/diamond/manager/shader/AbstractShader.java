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
package diamond.manager.shader;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.media.opengl.GL2;

/**
 * Die Klasse AbstractShader ist die Oberklasse fuer Shader
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public abstract class AbstractShader 
{	
	/*
	 * Shader ID
	 */
	private int shaderID;
	
	/*
	 * Zustaende des Shader
	 */
	protected boolean create;	
	protected boolean delete;
	
	/*
	 * Allgemeine Attribute
	 */
	private String program;
	
	/**
	 * Konstruktor
	 * 
	 * @param file Dateiname
	 */
	public AbstractShader( String file )
	{
		try( FileInputStream fileInput = new FileInputStream( file ))
		{
			this.init( fileInput );
		}
		catch( Exception exc )
		{
			this.create = false;
			exc.printStackTrace();
		}
	}
	
	/**
	 * Konstruktor
	 * 
	 * @param stream InputStream
	 */
	public AbstractShader( InputStream stream )
	{
		this.init( stream );
	}
	
	/**
	 * Methode initialisiert den AbstractShader
	 * 
	 * @param stream InpuStream
	 */
	private void init( InputStream stream )
	{
		this.create = true;
		this.delete = false;
		java.util.Scanner scanner = new java.util.Scanner( stream );
		scanner.useDelimiter( "\\A" );
		this.program = scanner.hasNext() ? scanner.next() : "";
		scanner.close();
	}
	
	/**
	 * Methode liefert die Shader ID
	 * 
	 * @return Liefert die Shader ID
	 */
	public final int getShaderID()
	{
		return this.shaderID;
	}
	
	/**
	 * Methode setzt die Shader ID
	 * 
	 * @param shaderID Shader ID
	 */
	protected final void setShaderID( int shaderID )
	{
		this.shaderID = shaderID;
	}
	
	/**
	 * Methode liefert das Shader Program
	 * 
	 * @return Liefert das Shader Program
	 */
	protected final String getProgram()
	{
		return this.program;
	}
	
	/**
	 * Methode prueft ob der AbstractShader kompiliert ist
	 * 
	 * @return true, wenn kompiliert, sonst false
	 */
	boolean isCompiled()
	{
		return !this.create;
	}
	
	/**
	 * Methode kompiliert den Shader
	 * 
	 * @param gl GL2
	 * @return Liefert true, wenn der Shader kompiliert wurde, sonst false
	 */
	boolean compile( GL2 gl )
	{		
		int[] success = new int[1];
		
		gl.glShaderSource( this.getShaderID(), 1, new String[] { this.getProgram() }, (int[]) null, 0 );
		gl.glCompileShader( this.getShaderID() );
		
		gl.glGetShaderiv( this.getShaderID(), GL2.GL_COMPILE_STATUS, success, 0 );
		
		return success[0] == GL2.GL_TRUE;
	}
	
	/**
	 * Methode zerstoert den Shader
	 */
	void destroy()
	{
		this.delete = true;
	}
}