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
 * ADT-Klasse zum Verwalten von mathematischen Vektoren
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Vector
{
	/*
	 * Variable zum Speichern des Vektor
	 */
	private float[] vector = null;

	/**
	 * Konstruktor 
	 * Erzeugt ein Vektor aus float-Werten
	 * 
	 * @param vector
	 */
	public Vector( float... vector ) 
	{
		this.vector = vector;
	}
	
	/**
	 * Methode liefert den Wert des Vektors
	 * 
	 * @param element gewuenschtes Element
	 * @return Liefert den Wert des Vektors
	 */
	public float get( int element )
	{
		// Ist das gewuenschte Element vorhanden?
		if( element < 0 || element >= this.vector.length )
			return 0;
		
		return this.vector[ element ];
	}
	
	/**
	 * Methode liefert den x-Wert
	 * 
	 * @return Liefert den x-Wert
	 */
	public float x()
	{
		// x-Wert vorhanden?
		if( this.vector.length > 0 )
			return this.vector[0];
		
		return 0f;
	}
	
	/**
	 * Methode liefert den y-Wert
	 * 
	 * @return Liefert den y-Wert
	 */
	public float y()
	{
		// y-Wert vorhanden?
		if( this.vector.length > 1 )
			return this.vector[1];
		
		return 0f;
	}
	
	/**
	 * Methode liefert den z-Wert
	 * 
	 * @return Liefert den z-Wert
	 */
	public float z()
	{
		// z-Wert vorhanden?
		if( this.vector.length > 2 )
			return this.vector[2];
		
		return 0f;
	}
	
	/**
	 * Methode setzt einen Wert des Vektors
	 * 
	 * @param element Gibt die Position des Werts an
	 * @param value Gibt den Wert an
	 */
	public void set( int element, float value )
	{
		// Ist der Wert vorhanden?
		if( element >= 0 && element < this.vector.length )
			this.vector[ element ] = value;
	}
	
	/**
	 * Methode setzt die Werte des Vektors
	 * 
	 * @param values Die zu setzenden Werte
	 */
	public void set( float... values )
	{
		this.vector = values;
	}
	
	/**
	 * Methode setzt die Werte des Vektors anhand eines anderen Vektors
	 * 
	 * @param vector Der Vektor von dem die Werte kommen
	 */
	public void set( Vector vector )
	{
		this.vector = vector.vector;
	}
	
	/**
	 * Liefert die Groesse des Vektors
	 * 
	 * @return Liefert die Groesse des Vektors
	 */
	public Integer getSize()
	{
		return this.vector.length;
	}
	
	/**
	 * Methode addiert einen Wert an einer Stelle und gibt ein neuen Vektor zurueck
	 * 
	 * @param element Gibt die Position im Vektor an
	 * @param value Gibt den Wert an
	 * @return Liefert einen neuen Vektor
	 */
	public Vector addComponent( int element, float value )
	{
		Vector temp = this.clone();
		
		if( element >= 0 && element < temp.vector.length )
			temp.vector[ element ] += value; 
		
		return temp;
	}
	
	/**
	 * Methode addiert einen Vektor zu diesem
	 * 
	 * @param vector Der zu addierende Vektor
	 * @return Liefert einen neuen Vekotr
	 */
	public Vector add( Vector vector )
	{
		float[] temp =  vector.getSize() > this.getSize() ?  
				new float[ vector.getSize() ] : 
				new float[ this.getSize() ];
				
		for( Integer i=0; i < temp.length; i++ )
			temp[ i ] = this.vector[ i ] + vector.get( i );
		
		return new Vector( temp );
	}
	
	/**
	 * Methode subbtrahiert einen Vektor von diesem
	 * 
	 * @param vector Der zu subtrahierende Vektor
	 * @return Liefert einen neuen Vektor
	 */
	public Vector sub( Vector vector )
	{
		float[] temp =  vector.getSize() > this.getSize() ?  
				new float[ vector.getSize() ] : 
				new float[ this.getSize() ];
				
		for( Integer i=0; i < temp.length; i++ )
			temp[ i ] = this.vector[ i ] - vector.get( i );
		
		return new Vector( temp );
	}
	
	/**
	 * Methode multipliziert einen Skalar zu diesem Vektor und 
	 * liefert das Ergebnis als neuen Vektor
	 * 
	 * @param skalar Der zu multiplizierende Skalar
	 * @return Liefert den neuen Vektor
	 */
	public Vector scalar( float skalar )
	{
		float[] temp = new float[ this.getSize() ];
		
		for( Integer i=0; i < temp.length; i++ )
			temp[ i ] = this.vector[ i ] * skalar;
		
		return new Vector( temp );
	}
	
	/**
	 * Methode bildet das Skalarprodukt und
	 * liefert das Ergebnis als neuen Vektor
	 * 
	 * @param vector Der zu multiplizierende Vektor
	 * @return Liefert den neuen Vektor
	 */
	public Vector scalarProduct( Vector vector )
	{
		float[] temp =  vector.getSize() > this.getSize() ?  
				new float[ vector.getSize() ] : 
				new float[ this.getSize() ];
				
		for( Integer i=0; i < temp.length; i++ )
			temp[ i ] = this.vector[ i ] * vector.get( i );
		
		return new Vector( temp );
	}
	
	/**
	 * Methode bildet das Krezuprodukt und
	 * liefert das Ergebnis als neuen Vektor
	 * 
	 * @param vector Der zu multiplizierende Vektor
	 * @return Liefert den neuen Vektor
	 */
	public Vector crossProduct( Vector vector )
	{
		float[] temp = new float[ 3 ];
		
		temp[ 0 ] = ( this.vector[ 1 ] * vector.get( 2 )) +
					( this.vector[ 2 ] * vector.get( 1 ));
		temp[ 1 ] = ( this.vector[ 2 ] * vector.get( 0 )) +
					( this.vector[ 0 ] * vector.get( 2 ));
		temp[ 2 ] = ( this.vector[ 0 ] * vector.get( 1 )) +
					( this.vector[ 1 ] * vector.get( 0 ));
		
		return new Vector( temp );
	}
	
	/**
	 * Methode liefert die Laenge des Vektors
	 * 
	 * @return Liefert die Laenge des Vektors
	 */
	public float getLength()
	{
		double temp = 0;
		
		for( Integer i=0; i < this.getSize(); i++ )
			temp += Math.pow( this.vector[ i ], 2 );

		return (float)Math.sqrt( temp );
	}
	
	/**
	 * Methode normalisiert den Vektor und
	 * liefert das Ergebnis als neuen Vektor
	 * 
	 * @return Liefert das Ergebnis als neuen Vektor
	 */
	public Vector normalize()
	{
		float length = this.getLength();
		float[] temp = new float[ this.getSize() ];
		
		if( length == 0 )
			length = (float)( 1 * Math.pow( 10, -300 ) );
		
		for( Integer i=0; i < temp.length; i++ )
			temp[ i ] = this.vector[ i ] / length;

		return new Vector( temp );
	}
	
	/**
	 * Methode wandelt den Vektor in einen String um
	 * 
	 * @return Liefert den Vektor als String
	 */
	@Override
	public String toString()
	{
		String temp = "vector |";
		
		for( Integer i=0; i < this.getSize(); i++ )
			temp += this.get( i ) + "|";
		
		return temp;
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Vector clone()
	{
		float[] temp = new float[ this.getSize() ];
		
		for( Integer i=0; i < temp.length; i++ )
			temp[ i ] = this.vector[ i ];
		
		return new Vector( temp );		
	}
	
	/**
	 * Liefert den Vektor als Array
	 * 
	 * @return Liefert das Array
	 */
	public float[] toArray()
	{
		return this.vector;
	}
}