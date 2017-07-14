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
 * ADT-Klasse zum Verwalten von mathematischen Matrizen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Matrix
{
	/*
	 * Variable zum Speichern der Matrix
	 */
	private float matrix[][];
	
	/**
	 * Konstruktor
	 * Erzeugt eine quadratische Matrix aus den vorgegebenen Werten
	 * 
	 * @param values Werte die in die Matrix geschrieben werden und die Groesse bestimmen
	 */
	public Matrix( float... values )
	{
		int size = (int)Math.sqrt( values.length );	// Erechne die Groesse
		this.matrix = new float[ size ][ size ];	// Erzeuge die Matrix
	
		// Schreibe die Werte in die Matrix
		for( int x=0; x < size; x++ )
		{
			for( int y=0; y < size; y++ )
			{
				if(( x * size ) + y < values.length )
					this.matrix[ x ][ y ] = values[ ( x * size ) + y ];
				else
					this.matrix[ x ][ y ] = 0f;
			}
		}
	}
	
	/**
	 * Konstruktor
	 * Erzeugt die Matrix mit angebener Groesse ohne Werte
	 * 
	 * @param x Groesse der Matrix in der x-Dimension
	 * @param y Groesse der Matrix in der y-Dimension
	 */
	public Matrix( int x, int y )
	{
		this.matrix = new float[x][y];	// Erzeugt die Matrix
		
		// Schreibt Nullen in die Matrix
		for( int i=0; i < x; i++ )
		{
			for( int j=0; j < y; j++ )
			{
				this.matrix[ i ][ j ] = 0;
			}
		}
	}
	
	/**
	 * Methode liefert den Wert aus einer Zelle
	 * 
	 * @param x	Position der Zelle in der x-Dimension
	 * @param y Position der Zelle in der y-Dimension
	 * @return Liefert den Wert aus der angegebenen Zelle
	 */
	public float get( int x, int y )
	{
		// Ist der Zugriff zulaessig?
		if( this.matrix != null || this.matrix[0] != null ||
				x > this.matrix.length || y >= this.matrix[0].length || x < 0 || y < 0 )
			return 0;
		
		return this.matrix[ x ][ y ];
	}
	
	/**
	 * Methode liefert die Breite der Matrix
	 * 
	 * @return Liefert die Breite der Matrix
	 */
	public int getWidth()
	{
		return this.matrix.length;
	}
	
	/**
	 * Methode liefert die Hoehe der Matrix
	 * 
	 * @return Liefert die Hoehe der Matrix
	 */
	public int getHeight()
	{
		if( matrix.length >= 1 )
			return matrix[0].length;
		
		return 0;
	}
	
	/**
	 * Methode schreibt einen Wert in eine Zelle
	 * 
	 * @param x	Position der Zelle in der x-Dimension
	 * @param y Position der Zelle in der y-Dimension
	 * @param value Der Wert, der in die Zelle geschrieben werden soll
	 */
	public void set( int x, int y, float value )
	{
		// Ist der Zugriff zulaessig?
		if( this.matrix != null || this.matrix[0] != null ||
				x > this.matrix.length || y >= this.matrix[0].length || x < 0 || y < 0 )
			return;
		
		this.matrix[ x ][ y ] = value;
	}
	
	/**
	 * Methode addiert eine Matrix mit dieser Matrix und liefert das Ergebnis als neue Matrix
	 * Die Werte dieser Klasse werden nicht beeinflusst, es wird eine neue Matrix erzeugt
	 * 
	 * @param matrix Benoetigt die Matrix die zu dieser Matrix addiert werden soll
	 * @return Liefert die neue Matrix als Ergebnis
	 */
	public Matrix add( Matrix matrix )
	{
		// Berechne die gueltigen Wertebereiche
		int minX = this.getWidth() < matrix.getWidth() ? this.getWidth() : matrix.getWidth();
		int minY = this.getHeight() < matrix.getHeight() ? this.getHeight() : matrix.getHeight();
		
		Matrix result = new Matrix( minX, minY );	// Erzeuge neue Matrix
		
		// Addiere die Werte
		for( int i=0; i < minX; i++ )
			for( int j=0; j < minY; j++ )
				result.set( i, j, this.get( i, j ) + matrix.get( i, j ));
				
		return result;
	}
	
	/**
	 * Methode addiert eine Matrix mit dieser Matrix und liefert diese Matrix als Ergebnis
	 * Die Werte dieser Klasse werden beeinflusst 
	 * 
	 * @param matrix Benoetigt die Matrix die zu dieser Matrix addiert werden soll
	 * @return Liefert eine Referenz auf diese Matrix zurueck
	 */
	public Matrix addLocal( Matrix matrix )
	{
		// Berechne die gueltigen Wertebereiche
		int minX = this.getWidth() < matrix.getWidth() ? this.getWidth() : matrix.getWidth();
		int minY = this.getHeight() < matrix.getHeight() ? this.getHeight() : matrix.getHeight();
		
		// Addiere die Werte
		for( int i=0; i < minX; i++ )
			for( int j=0; j < minY; j++ )
				this.set( i, j, this.get( i, j ) + matrix.get( i, j ));
				
		return this;
	}
	
	/**
	 * Methode multipliziert einen Skalar mit dieser Matrix und liefert das Ergebnis als neue Matrix
	 * Die Werte dieser Klasse werden nicht beeinflusst, es wird eine neue Matrix erzeugt
	 * 
	 * @param scalar Benoetigt den Skalar der mit dieser Matrix multipliziert werden soll
	 * @return Liefert die neue Matrix als Ergebnis
	 */
	public Matrix scalar( float scalar )
	{
		Matrix result = new Matrix( this.getWidth(), this.getHeight() );	// Erzeuge neue Matrix
		
		// Multipliziere mit dem Skalar
		for( int i=0; i < this.getWidth(); i++ )
			for( int j=0; j < this.getHeight(); j++ )
				result.set( i, j, scalar * this.get( i, j ) );
				
		return result;
	}
	
	/**
	 * Methode multipliziert einen Skalar mit dieser Matrix und liefert diese Matrix als Ergebnis
	 * Die Werte dieser Klasse werden beeinflusst.
	 * 
	 * @param scalar Benoetigt den Skalar der mit dieser MAtrix multipliziert werden soll
	 * @return Liefert eine Referenz auf diese Matrix zurueck
	 */
	public Matrix scalarLocal( float scalar )
	{
		// Multipliziere mit dem Skalar
		for( int i=0; i < this.getWidth(); i++ )
			for( int j=0; j < this.getHeight(); j++ )
				this.set( i, j, scalar * this.get( i, j ) );
				
		return this;
	}
	
	/**
	 * Methode multipliziert eine Matrix mit dieser Matrix und liefert das Ergebnis als neue Matrix
	 * Die Werte dieser Klasse werden nicht beeinflussst
	 * ACHTUNG: Wird nur quadratisch berechnet!
	 * 
	 * @param matrix Benoetigt die Matrix die mit dieser multipliziert werden soll
	 * @return Liefert die neue Matrix als Ergebnis
	 */
	public Matrix mult( Matrix matrix )
	{
		// Berechne die gueltigen Wertebereiche
		int minX = this.getWidth() < matrix.getWidth() ? this.getWidth() : matrix.getWidth();
		int minY = this.getHeight() < matrix.getHeight() ? this.getHeight() : matrix.getHeight();
		int min = minY < minX ? minY : minX;	// Vereinfachung: wird nur quadratisch berechnet
		
		Matrix temp = new Matrix( min, min );
		
		for( int i=0; i < min; i++ )
			for( int j=0; j < min; j++ )
			{
				float f = 0f;
				for( int k=0; k < min; k++ )
					f += this.get( i, k ) * matrix.get( k, j );
				temp.set( i, j, f );
			}				
		
		return temp;
	}
	
	/**
	 * Methode multipliziert eine Matrix mit dieser Matrix und liefert diese Matrix als Ergebnis
	 * Die Werte dieser Klasse werden beeinflusst
	 * ACHTUNG: Wird nur quadratisch berechnet!
	 * 
	 * @param matrix Benoetigt die Matrix die mit dieser multipliziert werden soll
	 * @return Liefert eine Referenz auf diese Matrix zurueck
	 */
	public Matrix multLocal( Matrix matrix )
	{
		// Berechne die gueltigen Wertebereiche
		int minX = this.getWidth() < matrix.getWidth() ? this.getWidth() : matrix.getWidth();
		int minY = this.getHeight() < matrix.getHeight() ? this.getHeight() : matrix.getHeight();
		int min = minY < minX ? minY : minX;	// Vereinfachung: wird nur quadratisch berechnet
		
		Matrix temp = new Matrix( min, min );
		
		// Erstmal in temporaerer Matrix multiplizieren,
		// damit keine Werte verfaelscht werden, die fuer die
		// Berechnung noch gebraucht werden
		for( int i=0; i < min; i++ )
			for( int j=0; j < min; j++ )
			{
				float f = 0f;
				for( int k=0; k < min; k++ )
					f += this.get( i, k ) * matrix.get( k, j );
				temp.set( i, j, f );
			}
		
		// Nach der Multiplikation in die lokale Matrix schreiben
		for( int i=0; i < min; i++ )
			for( int j=0; j < min; j++ )
			{
				this.set( i, j, temp.get( i, j ));
			}
		
		return this;
	}
	
	/**
	 * Methode transponiert diese Matrix und liefert das Ergebnis als neue Matrix zurueck
	 * Die Werte dieser Klasse werden nicht beeinflusst
	 * 
	 * @return Liefert die neue Matrix als Ergebnis
	 */
	public Matrix trans()
	{
		// Erzeuge neue Matrix
		Matrix result = new Matrix( this.getHeight(), this.getWidth() );
		
		// Transponiere die Matrix
		for( int i=0; i < this.getWidth(); i++ )
			for( int j=0; j < this.getHeight(); j++ )
				result.set( i, j, this.get( j, i ));
				
		return result;
	}
	
	/**
	 * Methode wandelt diese Matrix in ein 1-dimensionales float-Array
	 * 
	 * @return Liefert das 1-dimesnionale float-Array
	 */
	public float[] toArray()
	{
		// Pruefe ob die Matrix Werte enthaelt
		if( this.matrix == null || this.matrix[0] == null )
			return null;
		
		// Erzeuge Ausgabe-Array
		float[] result = new float[ this.matrix.length * this.matrix[0].length ];
		
		// Schreibe die Werte in das Array
		for( int i=0; i < this.matrix.length; i++ )
			for( int j=0; j < this.matrix[i].length; j++ )
				result[ (i * this.matrix.length) + j ] = this.matrix[ i ][ j ];
		
		return result;
	}
	
	/**
	 * Methode wandelt die Matrix in einen String um
	 * 
	 * @return Liefert die Matrix als String
	 */
	@Override
	public String toString()
	{
		String result = "matrix | |";
		
		for( Integer i=0; i < 4; i++ )
		{
			for( Integer j=0; j < 4; j++ )
				result += this.get( i, j ) + "|";
			result += " |";
		}
		
		return result;
	}
	
	/**
	 * Methode invertiert eine 4x4 Matrix
	 * 
	 * @param matrix Matrix als float-Array der Laenge 16
	 * @return Liefert die invertierte Matrix
	 */
	public static float[] inverse4( float[] matrix )
	{
		if( matrix.length < 16 )
			return null;
		
		float[] result = new float[16];
		
		result[0]  = matrix[0];
		result[1]  = matrix[4];
		result[2]  = matrix[8];
		result[3]  = 0f;
		result[4]  = matrix[1];
		result[5]  = matrix[5];
		result[6]  = matrix[9];
		result[7]  = 0f;
		result[8]  = matrix[2];
		result[9]  = matrix[6];
		result[10] = matrix[10];
		result[11] = 0f;
		result[12] = -( matrix[12] * result[0] + matrix[13] * result[4] + matrix[14] * result[8] );
		result[13] = -( matrix[12] * result[1] + matrix[13] * result[5] + matrix[14] * result[9] );
		result[14] = -( matrix[12] * result[2] + matrix[13] * result[6] + matrix[14] * result[10] );		
		result[15] = 1f;
		
		return result;
	}
}