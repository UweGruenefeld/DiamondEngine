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
package diamond.scene.light;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import diamond.util.math.Vector;

/**
 * Die abstrakte Klasse Light bietet eine Vorschrift bzw. Oberklasse fuer Licht
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public abstract class Light 
{
	/*
	 * Allgemeine Attribute
	 */
	private boolean enable;
	Vector position;
	private Vector ambient, diffuse, specular;
	
	/*
	 * Matrizen
	 */
	private float[] lightProjectionMatrix;
	private float[] lightModelViewMatrix;
	protected boolean refresh;
	
	/**
	 * Default-Konstruktor
	 */
	public Light()
	{
		this.enable = false;
		
		this.position = new Vector( 0, 0, 0, 1 );
		
		this.ambient = new Vector( 0.3f, 0.3f, 0.3f, 1 );
		this.diffuse = new Vector( 0.3f, 0.3f, 0.3f, 1 );
		this.specular = new Vector( 0.4f, 0.4f, 0.4f, 1 );
		
		this.lightProjectionMatrix = new float[16];
		this.lightModelViewMatrix = new float[16];
		this.refresh = true;
	}
	
	/**
	 * Methode aktiviert das Licht
	 */
	public void enable()
	{
		this.enable = true;
	}
	
	/**
	 * Methode deaktiviert das Licht
	 */
	public void disable()
	{
		this.enable = false;
	}
	
	/**
	 * Methode ueberprueft ob ein Licht aktiviert ist
	 * 
	 * @return Liefert true, wenn aktiviert, sonst false
	 */
	public boolean isEnabled()
	{
		return this.enable;
	}
	
	/**
	 * Methode liefert die Position des Lichts
	 * 
	 * @return Liefert die Position des Lichts
	 */
	public Vector getPosition()
	{
		return this.position;
	}
	
	/**
	 * Abstrakte Methode setzt die Position eines Lichts
	 * 
	 * @param x x-Position
	 * @param y y-Position
	 * @param z z-Position
	 */
	public abstract void setPosition( float x, float y, float z );

	/**
	 * Methode liefert den ambienten Teil des Lichts
	 * 
	 * @return Liefert den ambienten Teil des Lichts
	 */
	public Vector getAmbient()
	{
		return this.ambient;
	}
	
	/**
	 * Methode setzt den ambienten Teil des Lichts
	 * 
	 * @param r rot-Anteil
	 * @param g gruen-Anteil
	 * @param b blau-Anteil
	 */
	public void setAmbient( float r, float g, float b )
	{
		this.ambient = new Vector( r, g, b, 1 );
	}
	
	/**
	 * Methode setzt den ambienten Teil des Lichts
	 * 
	 * @param r rot-Anteil
	 * @param g gruen-Anteil
	 * @param b blau-Anteil
	 * @param w Faktor
	 */
	public void setAmbient( float r, float g, float b, float w )
	{
		this.ambient = new Vector( r, g, b, w );
	}
	
	/**
	 * Methode liefert den diffusen Teil des Lichts
	 * 
	 * @return Liefert den diffusen Teil des Lichts
	 */
	public Vector getDiffuse()
	{
		return this.diffuse;
	}
	
	/**
	 * Methode setzt den diffusen Teil des Lichts
	 * 
	 * @param r rot-Anteil
	 * @param g gruen-Anteil
	 * @param b blau-Anteil
	 */
	public void setDiffuse( float r, float g, float b )
	{
		this.diffuse = new Vector( r, g, b, 1 );
	}
	
	/**
	 * Methode setzt den diffusen Teil des Lichts
	 * 
	 * @param r rot-Anteil
	 * @param g gruen-Anteil
	 * @param b blau-Anteil
	 * @param w Faktor
	 */
	public void setDiffuse( float r, float g, float b, float w )
	{
		this.diffuse = new Vector( r, g, b, w );
	}
	
	/**
	 * Methode liefert den spekularen Teil des Lichts
	 * 
	 * @return Liefert den spekularen Teil des Lichts
	 */
	public Vector getSpecular()
	{
		return this.specular;
	}
	
	/**
	 * Methode setzt den spekularen Teil des Lichts
	 * 
	 * @param r rot-Anteil
	 * @param g gruen-Anteil
	 * @param b blau-Anteil
	 */
	public void setSpecular( float r, float g, float b )
	{
		this.specular = new Vector( r, g, b, 1 );
	}
	
	/**
	 * Methode setzt den spekularen Teil des Lichts
	 * 
	 * @param r rot-Anteil
	 * @param g gruen-Anteil
	 * @param b blau-Anteil
	 * @param w Faktor
	 */
	public void setSpecular( float r, float g, float b, float w )
	{
		this.specular = new Vector( r, g, b, w );
	}
	
	/**
	 * Methode liefert die Projektions-Matrix
	 * 
	 * @return Liefert die Projektions-Matrix
	 */
	public float[] getLightProjectionsMatrix()
	{
		return this.lightProjectionMatrix;
	}
	
	/**
	 * Methode liefert die Modelview-Matrix
	 * 
	 * @return Liefert die Modelview-Matrix
	 */
	public float[] getLightModelviewMatrix()
	{
		return this.lightModelViewMatrix;
	}
	
	/**
	 * Methode wendet das Licht an
	 * 
	 * @param gl GL2
	 * @param i Lichtnummer
	 */
	public void display( GL2 gl, int i )
	{
		if( this.enable )
		{
			gl.glEnable( GL2.GL_LIGHT0 + i );
			gl.glLightfv( GL2.GL_LIGHT0 + i, GL2.GL_POSITION, this.position.toArray(), 0 );
			gl.glLightfv( GL2.GL_LIGHT0 + i, GL2.GL_AMBIENT, this.ambient.toArray(), 0 );
			gl.glLightfv( GL2.GL_LIGHT0 + i, GL2.GL_DIFFUSE, this.diffuse.toArray(), 0 );
			gl.glLightfv( GL2.GL_LIGHT0 + i, GL2.GL_SPECULAR, this.specular.toArray(), 0 );
		}
		else
			gl.glDisable( GL2.GL_LIGHT0 + i );

		
		if( this.refresh )
		{	
			GLU glu = new GLU();
			
			// Aktualisiere Projektions-Matrix
			gl.glMatrixMode( GL2.GL_PROJECTION );
			gl.glPushMatrix();
			{
				gl.glLoadIdentity();
				glu.gluPerspective( 42.0f, 1.0f, 0.01f, 50.0f );
				gl.glGetFloatv( GL2.GL_PROJECTION_MATRIX, this.lightProjectionMatrix, 0 );
				gl.glLoadIdentity();
			}
			gl.glPopMatrix();
			
			// Aktualisiere Modelview-Matrix
			gl.glMatrixMode( GL2.GL_MODELVIEW );
			gl.glPushMatrix();
			{
				gl.glLoadIdentity();
				glu.gluLookAt( this.position.x(), this.position.y(), this.position.z(), 
							   0f, 0f, 0f, 
							   1f, 0f, 0f );
				gl.glGetFloatv( GL2.GL_MODELVIEW_MATRIX, this.lightModelViewMatrix, 0 );
				gl.glLoadIdentity();
			}
			gl.glPopMatrix();
			
			this.refresh = false;
		}
	}
}