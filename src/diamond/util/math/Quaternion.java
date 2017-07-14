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
package diamond.util.math;

/**
 * ADT-Klasse zum Verwalten von mathematischen Quaternionen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Quaternion 
{
	/*
	 * Variablen zum Speichern des Quaternion
	 */
	private float x, y, z, w;
	
	/**
	 * Default-Konstruktor
	 * Initialisiert die Werte mit 0
	 */
	public Quaternion()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 1;
	}
	
	/**
	 * Konstruktor
	 * Initialisiert die Werte mit den Vorgaben
	 * 
	 * @param x	x-Wert
	 * @param y y-Wert
	 * @param z z-Wert
	 * @param w w-Wert
	 */
	public Quaternion( float x, float y, float z, float w )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * Copy-Konstruktor
	 * 
	 * @param quaternion Das zu kopierende Quaternion
	 */
	public Quaternion( Quaternion quaternion )
	{
		this.set( quaternion );
	}
	
	/**
	 * Liefert den x-Wert
	 * 
	 * @return Liefert den x-Wert
	 */
	public float getX()
	{
		return this.x;
	}
	
	/**
	 * Liefert den y-Wert
	 * 
	 * @return Liefert den y-Wert
	 */
	public float getY()
	{
		return this.y;
	}
	
	/**
	 * Liefert den z-Wert
	 * 
	 * @return Liefert den z-Wert
	 */
	public float getZ()
	{
		return this.z;
	}
	
	/**
	 * Liefert den w-Wert
	 * 
	 * @return Liefert den w-Wert
	 */
	public float getW()
	{
		return this.w;
	}
	
	/**
	 * Setzt den x-Wert
	 * 
	 * @param x x-Wert
	 * @return Liefert eine Referenz auf dieses Objekt
	 */
	public Quaternion setX( float x )
	{
		this.x = x;
		return this;
	}
	
	/**
	 * Setzt den y-Wert
	 * 
	 * @param y y-Wert
	 * @return Liefert eine Referenz auf dieses Objekt
	 */
	public Quaternion setY( float y )
	{
		this.y = y;
		return this;
	}
	
	/**
	 * Setzt den z-Wert
	 * 
	 * @param z z-Wert
	 * @return Liefert eine Referenz auf dieses Objekt
	 */
	public Quaternion setZ( float z )
	{
		this.z = z;
		return this;
	}
	
	/**
	 * Setzt den w-Wert
	 * 
	 * @param w w-Wert
	 * @return Liefert eine Referenz auf dieses Objekt
	 */
	public Quaternion setW( float w )
	{
		this.w = w;
		return this;
	}	
	
	/**
	 * Setzt alle Werte des Quaternions gleichzeitig
	 * 
	 * @param x	x-Wert
	 * @param y y-Wert
	 * @param z z-Wert
	 * @param w w-Wert
	 * @return Liefert eine Referenz auf dieses Objekt
	 */
	public Quaternion set( float x, float y, float z, float w )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	/**
	 * Setzt alle Werte anhand eines vorgebenen Quaternions
	 * 
	 * @param q Das zu kopierende Quaternions
	 * @return Liefert eine Referenz auf dieses Objekt
	 */
	public Quaternion set( Quaternion q )
	{
		this.x = q.getX();
		this.y = q.getY();
		this.z = q.getZ();
		this.w = q.getW();
		return this;
	}
	
	/**
	 * Setzt alle Werte anhand vorgegebener Winkel
	 * ACHTUNG: Bitte genau drei Winkel angeben!
	 * 
	 * @param angles Erwartet drei Winkel
	 * @return Liefert eine Referenz auf dieses Objekt
	 */
	public Quaternion setFromAngles( float[] angles )
	{
		// Sind genau drei Winkel vorhanden?
		if( angles.length != 3 )
			return null;
		
		return this.setFromAngles( angles[0], angles[1], angles[2] );
	}
	
	/**
	 * Setzt alle Werte anhand vorgegebener Winkel
	 * 
	 * @param yaw yaw-Wert
	 * @param roll roll-Wert
	 * @param pitch pitch-Wert
	 * @return Liefert eine Referenz auf das Objekt
	 */
	public Quaternion setFromAngles( float yaw, float roll, float pitch )
	{
		float angle;
        float sinRoll, sinPitch, sinYaw, cosRoll, cosPitch, cosYaw;
        
        // Pitch
        angle = pitch * 0.5f;
        sinPitch = (float)Math.sin( angle );
        cosPitch = (float)Math.cos( angle );
        
        // Roll
        angle = roll * 0.5f;
        sinRoll = (float)Math.sin( angle );
        cosRoll = (float)Math.cos( angle );
        
        // Yaw
        angle = yaw * 0.5f;
        sinYaw = (float)Math.sin( angle );
        cosYaw = (float)Math.cos( angle );

        // Werte setzen
        this.w = ( cosRoll * cosPitch * cosYaw - sinRoll * sinPitch * sinYaw );
        this.x = ( cosRoll * cosPitch * sinYaw + sinRoll * sinPitch * cosYaw );
        this.y = ( sinRoll * cosPitch * cosYaw + cosRoll * sinPitch * sinYaw );
        this.z = ( cosRoll * sinPitch * cosYaw - sinRoll * cosPitch * sinYaw );
        
        // Quaternion normalisieren
        Quaternion q = this.normalize();
        q.setX( q.getX() );
        q.setY( q.getY() );
        q.setZ( q.getZ() );
        q.setW( q.getW() );
        
        return this;
	}
	
	/**
	 * Setzt dieses Quaternion auf die Identitaet
	 * 
	 * @return Liefert eine Referenz auf das Objekt
	 */
	public Quaternion setIdentity()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 1;
		return this;
	}
	
	/**
	 * Prueft ob das Quaternion die Identitaet ist
	 * 
	 * @return Liefert true, wenn es die Identitaet ist, sonst false
	 */
	public boolean isIdentity()
	{
		if( this.x == 0 && this.y == 0 && this.z == 0 && this.w == 1 )
			return true;
		return false;
	}
	
	/**
	 * Addiert ein Quaternion zu diesem und liefert ein neues Quaternion zurueck
	 * 
	 * @param q Das zu addierende Quaternion
	 * @return Liefert das neue Quaternion
	 */
	public Quaternion add( Quaternion q ) 
	{
        return new Quaternion( this.x + q.x, this.y + q.y, this.z + q.z, this.w + q.w );
    }
	
	/**
	 * Subtrahiert ein Quaternion von diesem und liefert ein neues Quaternion zurueck
	 * 
	 * @param q Das zu subtrahierende Quaternion
	 * @return Liefert das neue Quaternion
	 */
	public Quaternion subtract( Quaternion q ) 
	{
		return new Quaternion( this.x - q.x, this.y - q.y, this.z - q.z, this.w - q.w);
	}
	
	/**
	 * Multipliziert ein Quaternion mit diesem und liefert ein neues Quaternion zurueck 
	 * 
	 * @param q Das zu multiplizierende Quaternion
	 * @return Liefert das neue Quaternion
	 */
	public Quaternion mult( Quaternion q )
	{
		Quaternion result = new Quaternion();
		result.setX( this.x * q.getW() + this.y * q.getZ() - this.z * q.getY() + this.w * q.getX() );
		result.setY( -this.x * q.getZ() + this.y * q.getW() + this.z * q.getX() + this.w * q.getY() );
		result.setZ( this.x * q.getY() - this.y * q.getX() + this.z * q.getW() + this.w * q.getZ() );
		result.setW( -this.x * q.getX() - this.y * q.getY() - this.z * q.getZ() + this.w * q.getW() );
		
		return result;
	}
	
	/**
	 * Multipliziert ein Vektor und Fakor mit diesem Quaternion und liefert einen neuen Vector
	 * 
	 * @param vector Der zu multiplizierende Vektor
	 * @param factor Der zu multiplizierende Faktor
	 * @return Liefert den neuen Vektor
	 */
	public Vector mult( Vector vector, float factor )
	{
		Quaternion temp = new Quaternion();
		Quaternion result = new Quaternion();
		vector = vector.normalize();
		
		temp.setX( vector.get( 0 ) );
		temp.setY( vector.get( 1 ) );
		temp.setZ( vector.get( 2 ) );
		temp.setW( 0 );
		
		result = temp.mult( new Quaternion( -this.getX(), -this.getY(), -this.getZ(), this.getW() ));
		result = this.mult( result );
		
		return new Vector( result.getX() * factor, result.getY() * factor, result.getZ() * factor );
	}
	
	/**
	 * Multipliziert einen Skalar mit diesem Quaternion und liefert ein neuen Quaternion
	 * 
	 * @param scalar Der zu multiplizierende Skalar
	 * @return Liefert den neuen Quaternion
	 */
	public Quaternion mult( float scalar )
	{
		Quaternion result = new Quaternion();
		result.setX( this.x * scalar );
		result.setY( this.y * scalar );
		result.setZ( this.z * scalar );
		result.setW( this.w * scalar );
		
		return result;
	}
	
	/**
	 * Liefert die Norm dieses Quaternions
	 * 
	 * @return Liefert die Norm dieses Quaternions
	 */
	public float norm()
	{
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	/**
	 * Normalisiert das Quaternion und liefert ein neues Quaternion zurueck
	 * 
	 * @return Liefert das neue QUaternion
	 */
	public Quaternion normalize()
	{
		Quaternion result = new Quaternion();
		float norm = (float)Math.pow( this.norm(), -0.5f );
		
		result.setX( this.x * norm );
		result.setY( this.y * norm );
		result.setZ( this.z * norm );
		result.setW( this.w * norm );
		
		return result;
	}
	
	/**
	 * Methode bildet das Inverse des Quaterions und liefert es als neues Quaternion zurueck
	 * 
	 * @return Liefert das neue Quaternion
	 */
	public Quaternion inverse()
	{
		Quaternion result = new Quaternion();
		float norm = norm();
        if ( norm > 0.0 ) 
        {
            float inverseNorm = 1f / norm;
            result.setX( this.x *= -inverseNorm );
            result.setY( this.y *= -inverseNorm );
            result.setZ( this.z *= -inverseNorm );
            result.setW( this.w *=  inverseNorm );
        }
        
        return result;
	}
	
	/**
	 * Methode negiert dass Quaternion
	 * 
	 * @return Liefert eine Referenz auf dieses Quaternion
	 */
	public Quaternion negate()
	{
		this.x *= -1;
		this.y *= -1;
		this.z *= -1;
		this.w *= -1;
		
		return this;
	}
	
	/**
	 * Methode wandelt den Quaternion in eine Matrix
	 * 
	 * @return Liefert die Matrix als 1-dimensionalen Vektor zurueck
	 */
	public float[] toMatrix()
	{
		float[] result = { 1 - 2 * this.y * this.y - 2 * this.z * this.z, 2 * this.x * this.y - 2 * this.w * this.z, 2 * this.x * this.z + 2 * this.w * this.y, 0,
							2 * this.x * this.y + 2 * this.w * this.z, 1 - 2 * this.x * this.x - 2 * this.z * this.z, 2 * this.y * this.z - 2 * this.w * this.x, 0,
							2 * this.x * this.z - 2 * this.w * this.y, 2 * this.y * this.z + 2 * this.w * this.x, 1 - 2 * this.x * this.x - 2 * this.y * this.y, 0,
							0, 0, 0, 1 };
		return result;
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[ " + this.x + ", " + this.y + ", " + this.z + ", " + this.w + " ]";
	}
}