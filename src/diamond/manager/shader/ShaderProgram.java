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

import java.util.Stack;

import javax.media.opengl.GL2;

/**
 * Die Klasse ShaderProgram werwaltet Vertex- und Fragment-Shader
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class ShaderProgram 
{
	/*
	 * Programm ID
	 */
	private int programmID;
	
	/*
	 * Zustaende des Programms
	 */
	private boolean create;
	private boolean delete;
	
	/*
	 * Verwaltet neue Shader und Shader die Geloescht werden sollen
	 */
	private final Stack<AbstractShader> addShader;
	private final Stack<AbstractShader> removeShader;
	
	/**
	 * Konstruktor
	 */
	public ShaderProgram()
	{
		this.create = true;
		this.delete = false;
	
		this.addShader = new Stack<AbstractShader>();
		this.removeShader = new Stack<AbstractShader>();
	}
	
	/**
	 * Prueft ob das Shader Programm kompiliert wurde
	 * 
	 * @return	Liefert true, wenn kompiliert, sonst false
	 */
	public boolean isCompiled()
	{
		return !this.create;
	}
	
	/**
	 * Methode liefert die Shader Programm ID
	 * 
	 * @return Liefert die Shader Programm ID
	 */
	public int getProgrammID()
	{
		return this.programmID;
	}
	
	/**
	 * Methode fuegt einen neuen Shader hinzu
	 * 
	 * @param shader Shader
	 */
	public void add( AbstractShader shader )
	{
		this.addShader.add( shader );
	}
	
	/**
	 * Methode entfernt einen Shader
	 * 
	 * @param shader Shader
	 */
	public void delete( AbstractShader shader )
	{
		this.removeShader.add( shader );
	}
	
	/**
	 * Methode setzt float-Wert im Shader
	 * 
	 * @param gl GL2
	 * @param name Name der Variablen
	 * @param value Inhalt der Variablen
	 */
	public void setFloatValue( GL2 gl, String name, float value ) 
	{
		gl.glUniform1f( gl.glGetUniformLocation( this.programmID, name ), value );
	}

	/**
	 * Methode setzt int-Wert im Shader
	 * 
	 * @param gl GL2
	 * @param name Name der Variablen
	 * @param value Inhalt der Variablen
	 */
	public void setIntegerValue( GL2 gl, String name, int value ) 
	{
		gl.glUniform1i( gl.glGetUniformLocation( this.programmID, name ), value );
	}
	
	/**
	 * Methode liefert die Location fuer eine Uniform-Variable
	 * 
	 * @param gl GL2
	 * @param name Uniformname
	 * @return Liefert die Location
	 */
	public int getLocation( GL2 gl, String name ) 
	{
		return gl.glGetUniformLocation( this.programmID, name );
	}
	
	/**
	 * Methode liefert die Location fuer ein Attribut
	 * 
	 * @param gl GL2 
	 * @param name Attributname
	 * @return Liefert die Location
	 */
	public int getAttribute( GL2 gl, String name )
	{
		return gl.glGetAttribLocation( this.programmID, name );
	}
	
	/**
	 * Methode kompiliert das Shader Programm
	 * 
	 * @param gl GL2
	 * @return Liefert true, wenn das Shader Programm kompiliert wurde, sonst false
	 */
	public boolean compile( GL2 gl )
	{		
		// loescht das Program
		if( this.delete == true )
		{
			if( this.create != true )
			{
				gl.glDeleteProgram( this.programmID );
			}			
			this.create = true;
			
			return false;
		}
		
		// erzeugt das Program 
		if( this.create == true )
		{
			this.programmID = gl.glCreateProgram();
			this.create = false;
		}
		
		// fuegt einen Shader hinzu
		while( !this.addShader.empty() )
		{
			AbstractShader shader = this.addShader.pop();
			if( shader.compile( gl ))
			{
				gl.glAttachShader( this.programmID, shader.getShaderID() );
			} 
			else
			{
				System.out.println( "Shader ERROR" );
			}
		}
		
		// entfernt einen Shader
		while( !this.removeShader.empty() )
		{
			gl.glDetachShader( this.programmID, this.removeShader.pop().getShaderID() );
		}
		
		int[] success = new int[1];
		
		gl.glLinkProgram( this.programmID );
		gl.glGetProgramiv( this.programmID, GL2.GL_LINK_STATUS, success, 0 );
		
		return success[0] == GL2.GL_TRUE; 
	}
	
	/**
	 * Methode entfernt ein Shader Programm
	 */
	public void destroy()
	{
		this.delete = true;
	}
}
