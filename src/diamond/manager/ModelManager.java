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
package diamond.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.media.opengl.GLAutoDrawable;

import diamond.DiamondEngine;
import diamond.scene.mesh.model.Wavefront;
import diamond.util.Array;
import diamond.util.Parser;
import diamond.util.graphics.polygon.Face;
import diamond.util.graphics.polygon.Line;
import diamond.util.graphics.polygon.Point;
import diamond.util.graphics.vertex.Vertex;
import diamond.util.graphics.vertex.VertexNormal;
import diamond.util.graphics.vertex.ParameterSpaceVertex;
import diamond.util.graphics.vertex.TextureVertex;

//TODO Wavefront muss vervollstaendigt werden
//TODO OgreXML sollte als Standart eingeführt werden

/**
 * Die Klasse ModelManager implementiert
 * verschiedene Modelle
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class ModelManager extends AbstractManager
{
	/**
	 * Konstruktor
	 * 
	 * @param engine Referenz auf die Engine
	 */
	public ModelManager( DiamondEngine engine )
	{
		super( engine );
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#init(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void init( GLAutoDrawable drawable ) 
	{
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#display(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void display( GLAutoDrawable drawable ) 
	{
	}

	/**
	 * (non-Javadoc)
	 * @see diamond.manager.AbstractManager#dispose(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void dispose( GLAutoDrawable drawable ) 
	{
	}
	
	/**
	 * Methode liefert ein Wavefront-Objekt
	 * 
	 * @param filename Dateipfad der geladen wird
	 * @return liefert ein Wavefront-Objekt
	 */
	public Wavefront importWavefront( String filename )
	{
		Wavefront wavefront = new Wavefront();

		try ( BufferedReader count = new BufferedReader( new InputStreamReader( 
				this.getEngine().getAssetManager().get( "model/wavefront/" + filename )));
			  BufferedReader parse = new BufferedReader( new InputStreamReader( 
				this.getEngine().getAssetManager().get( "model/wavefront/" + filename ))))
		{			
			int v = 0, vp = 0, vt = 0, vn = 0;
			int p = 0, l = 0, f = 0;
			
			String line;
			while (( line = count.readLine() ) != null )   
			{
				String word[] = line.split( " " );
				
				if( word[0].equals( "v" ))
					++v;
				else if( word[0].equals( "vp" ))
					++vp;
				else if( word[0].equals( "vt" ))
					++vt;
				else if( word[0].equals( "vn" ))
					++vn;
				
				else if( word[0].equals( "p" ))
					++p;
				else if( word[0].equals( "l" ))
					++l;
				else if( word[0].equals( "f" ))
					++f;
			}
			
			wavefront.setSize( v, vt, vp, vn, p, l, f );
			
			while (( line = parse.readLine() ) != null )   
			{
				String word[] = line.split( " " );
				if( word.length < 2 )
					continue;
				
				if( word[0].equals( "v" ))
				{
					if( word.length == 4 )
						wavefront.addV( new Vertex( Parser.parseFloat( this.getEngine(), word[1] ), 
							Parser.parseFloat( this.getEngine(), word[2] ), Parser.parseFloat( this.getEngine(), word[3] )));
					else if( word.length == 5 )
						wavefront.addV( new Vertex( Parser.parseFloat( this.getEngine(), word[1] ), 
							Parser.parseFloat( this.getEngine(), word[2] ), Parser.parseFloat( this.getEngine(), word[3] ), Parser.parseFloat( this.getEngine(), word[4] )));
				}
				else if( word[0].equals( "vp" ))
				{
					if( word.length == 3 )
						wavefront.addVp( new ParameterSpaceVertex( Parser.parseFloat( this.getEngine(), word[1] ), Parser.parseFloat( this.getEngine(), word[2] )));
					else if( word.length == 4 )
						wavefront.addVp( new ParameterSpaceVertex( Parser.parseFloat( this.getEngine(), word[1] ), 
							Parser.parseFloat( this.getEngine(), word[2] ), Parser.parseFloat( this.getEngine(), word[3] )));
				}
				else if( word[0].equals( "vt" ))
				{
					if( word.length == 2 )
						wavefront.addVt( new TextureVertex( Parser.parseFloat( this.getEngine(), word[1] )));
					else if( word.length == 3 )
						wavefront.addVt( new TextureVertex( Parser.parseFloat( this.getEngine(), word[1] ), Parser.parseFloat( this.getEngine(), word[2] )));
					else if( word.length == 4 )
						wavefront.addVt( new TextureVertex( Parser.parseFloat( this.getEngine(), word[1] ), 
							Parser.parseFloat( this.getEngine(), word[2] ), Parser.parseFloat( this.getEngine(), word[3] )));
				}
				else if( word[0].equals( "vn" ))
				{
					if( word.length == 4 )
						wavefront.addVn( new VertexNormal( Parser.parseFloat( this.getEngine(), word[1] ), 
							Parser.parseFloat( this.getEngine(), word[2] ), Parser.parseFloat( this.getEngine(), word[3] )));
				}
				
				else if( word[0].equals( "p" ))
					wavefront.addPoint( new Point( this.getEngine(), (String[])Array.cutFirst( word )));
				
				else if( word[0].equals( "l" ))
					wavefront.addLine( new Line( this.getEngine(), (String[])Array.cutFirst( word )));
					
				else if( word[0].equals( "f" ))
					wavefront.addFace( new Face( this.getEngine(), (String[])Array.cutFirst( word )));
			}
		} 
		catch ( Exception exc ) 
		{
			exc.printStackTrace();
		}
		
		return wavefront;
	}
}
