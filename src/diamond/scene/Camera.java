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

import javax.media.opengl.GL2;

import diamond.Constant;
import diamond.DiamondEngine;
import diamond.util.math.Quaternion;
import diamond.util.math.Vector;

/**
 * Die Klasse Kamera steuert die View-Objekte
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Camera 
{
	/*
	 * Referenz auf die Engine
	 */
	private final DiamondEngine engine;
	
	/*
	 * Speichert die aktuelle Position und die Winkel
	 */
	private Vector position;
	private volatile Quaternion angle;
	
	/*
	 * Speichert angeforderte animierte Veraenderung
	 * der Position und Rotation
	 */
	private Vector translate, rotate;

	/**
	 * Konstruktor
	 * Erzeugt eine neue Kamera an der Default-Position
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public Camera( DiamondEngine engine ) 
	{
		this.engine = engine;
		
		this.position = new Vector( Constant.defaultPositionX, 
				Constant.defaultPositionY, Constant.defaultPositionZ );
		this.angle = new Quaternion();
		
		this.translate = new Vector( 0, 0, 0 );
		this.rotate = new Vector( 0, 0, 0 );
	}
	
	/**
	 * Methode liefert eine Referenz auf die Engine
	 * 
	 * @return Liefert eine Referenz auf die Engine
	 */
	public DiamondEngine getEngine()
	{
		return this.engine;
	}
	
	/**
	 * Konstruktor
	 * Erzeugt eine neue Kamera an der angegebenen Position
	 * 
	 * @param engine Referenz auf die Engine
	 * @param x x-Wert
	 * @param y y-Wert
	 * @param z z-Wert
	 */
	public Camera( DiamondEngine engine, int x, int y, int z )
	{
		this.engine = engine;

		this.position = new Vector( x, y, z );
		this.angle = new Quaternion();
		
		this.translate = new Vector( 0, 0, 0 );
		this.rotate = new Vector( 0, 0, 0 );
	}
	
	/**
	 * Methode rendert die Kamera
	 * 
	 * @param gl GL2
	 */
	void render( GL2 gl ) 
	{
		this.update();
		this.setCameraMatrix( gl );
	}
	
	/**
	 * Aktualisiert die Kamera und erzeugt Kamera-Animationen
	 */
	private void update() 
	{
		// Sind Animationen angefordert?
		if( this.translate.get( 0 ) == 0f && this.translate.get( 1 ) == 0f && 
				this.translate.get( 2 ) == 0f && this.rotate.get( 0 ) == 0f && 
				this.rotate.get( 1 ) == 0f && this.rotate.get( 2 ) == 0f )
			return;	
	
		// Verschiebe-Animationen
		if( this.translate.get( 0 ) != 0.0f )
			this.setTranslateX( this.translate.get( 0 ));
		
		if( this.translate.get( 1 ) != 0.0f )
			this.setTranslateY( this.translate.get( 1 ));
		
		if( this.translate.get( 2 ) != 0.0f )
			this.setTranslateZ( this.translate.get( 2 ));
		
		// Rotations-Animationen
		if( this.rotate.get( 0 ) != 0.0f )
			this.setRotateX( this.rotate.get( 0 ));
		
		if( this.rotate.get( 1 ) != 0.0f )
			this.setRotateY( this.rotate.get( 1 ));
		
		if( this.rotate.get( 2 ) != 0.0f )
			this.setRotateZ( this.rotate.get( 2 ));
	}
	
	/**
	 * Multipliziert die Model-View Matrix mit der Camera-Matrix
	 * 
	 * @param gl GL2
	 */
	private void setCameraMatrix( GL2 gl )
	{
		gl.glMultMatrixf( this.getRotationMatrix(), 0 );
		gl.glMultMatrixf( this.getTranslationMatrix(), 0 );
	}
	
	/**
	 * Methode liefert die Rotationsmatrix der Kamera
	 * 
	 * @return Liefert die Rotationsmatrix
	 */
	public float[] getRotationMatrix()
	{
		return this.angle.toMatrix();
	}
	
	/**
	 * Methode liefert die Translationsmatrix der Kamera
	 * 
	 * @return Liefert die Translationsmatrix
	 */
	public float[] getTranslationMatrix()
	{
		return new float[]{ 1, 0, 0, 0,
							0, 1, 0, 0,
							0, 0, 1, 0,
							-this.position.get( 0 ), 
							-this.position.get( 1 ), 
							-this.position.get( 2 ), 1 };
	}
	
	/**
	 * Setzt die Position der Kamera
	 * 
	 * @param x x-Wert
	 * @param y y-Wert
	 * @param z z-Wert
	 */
	public void setPosition( float x, float y, float z )
	{
		this.position.set( 0, x );
		this.position.set( 1, y );
		this.position.set( 2, z );
	}
	
	/**
	 * Methode liefert die Position der Kamera
	 * 
	 * @return Liefert die Position
	 */
	public Vector getPosition()
	{
		return new Vector( this.position.get( 0 ), this.position.get( 1 ), this.position.get( 2 ) );
	}
	
	/**
	 * Methode liefert die x-Position der Kamera
	 * 
	 * @return Liefert die x-Position
	 */
	public float getPositionX()
	{
		return this.position.get( 0 );
	}
	
	/**
	 * Methode liefert die y-Position der Kamera
	 * 
	 * @return Liefert die y-Position
	 */
	public float getPositionY()
	{
		return this.position.get( 1 );
	}
	
	/**
	 * Methode liefert die z-Position der Kamera
	 * 
	 * @return Liefert die z-Position
	 */
	public float getPositionZ()
	{
		return this.position.get( 2 );
	}
	
	/**
	 * Methode setzt die Verschiebungs-Animation
	 * 
	 * @param translateX x-Verschiebung
	 * @param translateY y-Verschiebung
	 * @param translateZ z-Verschiebung
	 */
	public void setTranslateAnimation( float translateX, float translateY, float translateZ )
	{
		this.setTranslateXAnimation( translateX );
		this.setTranslateYAnimation( translateY );
		this.setTranslateZAnimation( translateZ );
	}
	
	/**
	 * Methode setzt die Verschiebungs-Animation
	 * 
	 * @param translateX x-Verschiebung
	 */
	public void setTranslateXAnimation( float translateX ) 
	{
		this.translate.set( 0, translateX * ( this.engine.getTimeManager().getTime() / 100f ));
	}
	
	/**
	 * Methode setzt die Verschiebungs-Animation
	 * 
	 * @param translateY y-Verschiebung
	 */
	public void setTranslateYAnimation( float translateY ) 
	{
		this.translate.set( 1, translateY * ( this.engine.getTimeManager().getTime() / 100f ));
	}
	
	/**
	 * Methode setzt die Verschiebungs-Animation
	 * 
	 * @param translateZ z-Verschiebung
	 */
	public void setTranslateZAnimation( float translateZ ) 
	{
		this.translate.set( 2, translateZ * ( this.engine.getTimeManager().getTime() / 100f ));
	}
	
	/**
	 * Methode setzt die Rotations-Animation
	 * 
	 * @param rotateX x-Rotation
	 * @param rotateY y-Rotation
	 * @param rotateZ z-Rotation
	 */
	public void setRotateAnimation( float rotateX, float rotateY, float rotateZ )
	{
		this.setRotateXAnimation( rotateX );
		this.setRotateYAnimation( rotateY );
		this.setRotateZAnimation( rotateZ );
	}
	
	/**
	 * Methode setzt die Rotations-Animation
	 * 
	 * @param rotateX x-Rotation
	 */
	public void setRotateXAnimation( float rotateX ) 
	{
		this.rotate.set( 0, rotateX * ( this.engine.getTimeManager().getTime() / 100f ));
	}
	
	/**
	 * Methode setzt die Rotations-Animation
	 * 
	 * @param rotateY y-Rotation
	 */
	public void setRotateYAnimation( float rotateY ) 
	{
		this.rotate.set( 1, rotateY * ( this.engine.getTimeManager().getTime() / 100f ));
	}
	
	/**
	 * Methode setzt die Rotations-Animation
	 * 
	 * @param rotateZ z-Rotation
	 */
	public void setRotateZAnimation( float rotateZ ) 
	{
		this.rotate.set( 2, rotateZ * ( this.engine.getTimeManager().getTime() / 100f ));
	}
	
	/**
	 * Methode setzt die Verschiebung
	 * 
	 * @param unitsX x-Einheiten
	 * @param unitsY y-Einheiten
	 * @param unitsZ z-Einheiten
	 */
	public void setTranslate( float unitsX, float unitsY, float unitsZ )
	{
		this.setTranslateX( unitsX );
		this.setTranslateY( unitsY );
		this.setTranslateZ( unitsZ );
	}
	
	/**
	 * Methode setzt die Verschiebung
	 * 
	 * @param units x-Einheiten
	 */
	public void setTranslateX( float units ) 
	{
		this.position = this.position.add( this.angle.mult( new Vector( 1f, 0f, 0f ), units ));
	}
	
	/**
	 * Methode setzt die Verschiebung
	 * 
	 * @param units y-Einheiten
	 */
	public void setTranslateY( float units ) 
	{
		this.position = this.position.add( this.angle.mult( new Vector( 0f, 1f, 0f ), units ));
	}
	
	/**
	 * Methode setzt die Verschiebung
	 * 
	 * @param units z-Einheiten
	 */
	public void setTranslateZ( float units ) 
	{
		this.position = this.position.sub( this.angle.mult( new Vector( 0f, 0f, 1f ), units ));
	}
	
	/**
	 * Methode setzt die Rotation
	 * 
	 * @param unitsX x-Rotation
	 * @param unitsY y-Rotation
	 * @param unitsZ z-Rotation
	 */
	public void setRotate( float unitsX, float unitsY, float unitsZ )
	{
		this.setRotateX( unitsX );
		this.setRotateY( unitsY );
		this.setRotateZ( unitsZ );
	}

	/**
	 * Methode setzt die Rotation
	 * 
	 * @param units x-Rotation
	 */
	public void setRotateX( float units )
	{
		Quaternion q = new Quaternion();
		double a = Math.toRadians( units ) * 0.5f;
		
		q.setX( (float)Math.sin( a ));
		q.setY( 0 );
		q.setZ( 0 );
		q.setW( (float)Math.cos( a ));

		this.angle = this.angle.mult( q );
	}
	
	/**
	 * Methode setzt die Rotation
	 * 
	 * @param units y-Rotation
	 */
	public void setRotateY( float units ) 
	{
		Quaternion q = new Quaternion();
		double a = Math.toRadians( units ) * -0.5f;
		
		q.setX( 0 );
		q.setY( (float)Math.sin( a ) );
		q.setZ( 0 );
		q.setW( (float)Math.cos( a ));

		this.angle = this.angle.mult( q );
	}
	
	/**
	 * Methode setzt die Rotation
	 * 
	 * @param units z-Rotation
	 */
	public void setRotateZ( float units )
	{
		Quaternion q = new Quaternion();
		double a = Math.toRadians( units ) * -0.5f;
		
		q.setX( 0 );
		q.setY( 0 );
		q.setZ( (float)Math.sin( a ) );
		q.setW( (float)Math.cos( a ));

		this.angle = this.angle.mult( q );
	}
	
	/**
	 * Methode setzt die Verschiebung
	 * 
	 * @param unitsX x-Einheiten
	 * @param unitsY y-Einheiten
	 * @param unitsZ z-Einheiten
	 */
	public void setTranslateAbsolute( float unitsX, float unitsY, float unitsZ )
	{
		this.setTranslateXAbsolute( unitsX );
		this.setTranslateYAbsolute( unitsY );
		this.setTranslateZAbsolute( unitsZ );
	}
	
	/**
	 * Methode setzt die Verschiebung
	 * 
	 * @param units x-Einheiten
	 */
	public void setTranslateXAbsolute( float units ) 
	{
		this.position = this.angle.mult( new Vector( 1f, 0f, 0f ), units );
	}
	
	/**
	 * Methode setzt die Verschiebung
	 * 
	 * @param units y-Einheiten
	 */
	public void setTranslateYAbsolute( float units ) 
	{
		this.position = this.angle.mult( new Vector( 0f, 1f, 0f ), units );
	}
	
	/**
	 * Methode setzt die Verschiebung
	 * 
	 * @param units z-Einheiten
	 */
	public void setTranslateZAbsolute( float units ) 
	{
		this.position = this.angle.mult( new Vector( 0f, 0f, 1f ), units );
	}
	
	/**
	 * Methode setzt die Rotation
	 * 
	 * @param unitsX x-Rotation
	 * @param unitsY y-Rotation
	 * @param unitsZ z-Rotation
	 */
	public void setRotateAbsolute( float unitsX, float unitsY, float unitsZ )
	{
		this.setRotateXAbsolute( unitsX );
		this.setRotateYAbsolute( unitsY );
		this.setRotateZAbsolute( unitsZ );
	}

	/**
	 * Methode setzt die Rotation
	 * 
	 * @param units x-Rotation
	 */
	public void setRotateXAbsolute( float units )
	{
		Quaternion q = new Quaternion();
		double a = Math.toRadians( units ) * 0.5f;
		
		q.setX( (float)Math.sin( a ));
		q.setY( 0 );
		q.setZ( 0 );
		q.setW( (float)Math.cos( a ));

		this.angle = q;
	}
	
	/**
	 * Methode setzt die Rotation
	 * 
	 * @param units y-Rotation
	 */
	public void setRotateYAbsolute( float units ) 
	{
		Quaternion q = new Quaternion();
		double a = Math.toRadians( units ) * -0.5f;
		
		q.setX( 0 );
		q.setY( (float)Math.sin( a ) );
		q.setZ( 0 );
		q.setW( (float)Math.cos( a ));

		this.angle = q;
	}
	
	/**
	 * Methode setzt die Rotation
	 * 
	 * @param units z-Rotation
	 */
	public void setRotateZAbsolute( float units )
	{
		Quaternion q = new Quaternion();
		double a = Math.toRadians( units ) * -0.5f;
		
		q.setX( 0 );
		q.setY( 0 );
		q.setZ( (float)Math.sin( a ) );
		q.setW( (float)Math.cos( a ));

		this.angle = q;
	}
	
	/**
	 * Methode setzt die Position und Rotation auf die Default-Werte
	 */
	public void reset()
	{
		this.resetPosition();
		this.resetRotation();
	}
	
	/**
	 * Methode setzt die Position auf die Default-Werte
	 */
	public void resetPosition()
	{
		this.resetPositionX();
		this.resetPositionY();
		this.resetPositionZ();
	}
	
	/**
	 * Methode setzt die Rotation auf die Default-Werte
	 */
	public void resetRotation()
	{
		this.resetRotationX();
		this.resetRotationY();
		this.resetRotationZ();
	}
	
	/**
	 * Methode setzt die x-Position auf die Default-Werte
	 */
	public void resetPositionX()
	{
		this.position.set( 0, Constant.defaultPositionX );
	}
	
	/**
	 * Methode setzt die y-Position auf die Default-Werte
	 */
	public void resetPositionY()
	{
		this.position.set( 1, Constant.defaultPositionY );
	}
	
	/**
	 * Methode setzt die z-Position auf die Default-Werte
	 */
	public void resetPositionZ()
	{
		this.position.set( 2, Constant.defaultPositionZ );
	}
	
	/**
	 * Methode setzt die x-Rotation auf die Default-Werte
	 */
	public void resetRotationX()
	{
		this.angle.setX( 0f );
	}
	
	/**
	 * Methode setzt die y-Rotation auf die Default-Werte
	 */
	public void resetRotationY()
	{
		this.angle.setY( 0f );
	}
	
	/**
	 * Methode setzt die z-Rotation auf die Default-Werte
	 */
	public void resetRotationZ()
	{
		this.angle.setZ( 0f );
	}
}