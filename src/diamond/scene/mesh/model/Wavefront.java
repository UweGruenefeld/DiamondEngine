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
package diamond.scene.mesh.model;

import javax.media.opengl.GL2;

import diamond.DiamondEngine;
import diamond.scene.Scene;
import diamond.scene.mesh.AbstractMesh;
import diamond.scene.mesh.Mesh;
import diamond.scene.node.Node;
import diamond.util.graphics.polygon.Face;
import diamond.util.graphics.polygon.Line;
import diamond.util.graphics.polygon.Point;
import diamond.util.graphics.vertex.Vertex;
import diamond.util.graphics.vertex.VertexNormal;
import diamond.util.graphics.vertex.ParameterSpaceVertex;
import diamond.util.graphics.vertex.TextureVertex;

//TODO Es fehlt eine vollstaendige Implementierung von Wavefront-Objekten

/**
 * Die Klasse Wavefront ist eine Implementierung
 * von Wavefront Objects
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
@SuppressWarnings( "all" )
public class Wavefront implements Mesh
{
	/*
	 * Vertices
	 */
	private Vertex v[];
	private ParameterSpaceVertex vp[];
	private TextureVertex vt[];
	private VertexNormal vn[];
	
	/*
	 * Polygons
	 */
	private Point p[];
	private Line l[];
	private Face f[];
	
	/*
	 * Groesse der einzelnen Speicher
	 */
	private int countV, countVp, countVt, countVn;
	private int countP, countL, countF;
	
	public Wavefront()
	{		
		this.countV = 0;
		this.countVp = 0;
		this.countVt = 0;
		this.countVn = 0;
		
		this.countP = 0;
		this.countL = 0;
		this.countF = 0;
	}

	/**
	 * Methode setzt die Groesse der einzelnen Speicher
	 * 
	 * @param v Vertex-Speicher
	 * @param vt Texture-Vertex-Speicher
	 * @param vp Parameter-Space-Vertex-Speicher
	 * @param vn Vertex-Normals-Speicher
	 * @param p Speicher fuer Punkte
	 * @param l Speicher fuer Linien
	 * @param f Speicher fuer Faces
	 */
	public void setSize( int v, int vt, int vp, int vn, 
			int p, int l, int f  )
	{
		// Warum inkrementieren?
		this.v = new Vertex[v];
		this.vp = new ParameterSpaceVertex[vp];
		this.vt = new TextureVertex[vt];
		this.vn = new VertexNormal[vn];
		
		this.p = new Point[p];
		this.l = new Line[l];
		this.f = new Face[f];
	}
	
	/**
	 * Methode fuegt einen Vertex hinzu
	 * 
	 * @param v Vertex
	 */
	public void addV( Vertex v )
	{
		this.v[this.countV++] = v;
	}
	
	/**
	 * Methode fuegt einen Parameter-Space-Vertex hinzu
	 * 
	 * @param vp Parameter-Space-Vertex
	 */
	public void addVp( ParameterSpaceVertex vp )
	{
		this.vp[this.countVp++] = vp;
	}
	
	/**
	 * Methode fuegt einen Texture-Vertex hinzu
	 * 
	 * @param vt Texture-Vertex
	 */
	public void addVt( TextureVertex vt )
	{
		this.vt[this.countVt++] = vt;
	}
	
	/**
	 * Methode fuegt eine Vertex-Normal hinzu
	 * 
	 * @param vn Vertex-Normal
	 */
	public void addVn( VertexNormal vn )
	{
		this.vn[this.countVn++] = vn;
	}
	
	/**
	 * Methode fuegt einen Punkt hinzu
	 * 
	 * @param p Punkt
	 */
	public void addPoint( Point p )
	{
		this.p[this.countP++] = p;
	}
	
	/**
	 * Methode fuegt eine Linie hinzu
	 * 
	 * @param l Linie
	 */
	public void addLine( Line l )
	{
		this.l[this.countL++] = l;
	}
	
	/**
	 * Methode fuegt ein Face hinzu
	 * 
	 * @param f Face
	 */
	public void addFace( Face f )
	{
		this.f[this.countF++] = f;
	}
	
	/**
	 * (non-Javadoc)
	 * @see diamond.scene.mesh.Mesh#renderMesh(javax.media.opengl.GL2, diamond.scene.Scene)
	 */
	@Override
	public void renderMesh( GL2 gl, Scene view )
	{		
		gl.glEnable( GL2.GL_NORMALIZE );
		gl.glPushMatrix();
		{						
			if( this.p.length > 0 )
			{
				gl.glBegin( GL2.GL_POINTS );
				{
					for( int i=0; i < this.p.length; i++ )
					{
						if( this.p[i] == null )
							continue;						
						
						for( int j=0; j < this.p[i].getVertices().length; j++ )
							this.drawV( gl, this.p[i].getVertices()[j] );
					}
				}
				gl.glEnd();
			}
			
			if( this.l.length > 0 )
			{
				for( int i=0; i < this.l.length; i++ )
				{
					if( this.l[i] == null )
						continue;
				
					gl.glBegin( GL2.GL_LINES );
					{
						for( int j=0; j < this.l[i].getVertices().length; j++ )
						{
							this.drawVt( gl, this.l[i].getTextureVertices()[j] );
							this.drawV( gl, this.l[i].getVertices()[j] );
						}
					}
					gl.glEnd();
				}							
			}
			
			if( this.f.length > 0 )
			{
				for( int i=0; i < this.f.length; i++ )
				{	
					if( this.f[i] == null )
						continue;
					
					gl.glBegin( GL2.GL_POLYGON );
					{
						for( int j=0; j < this.f[i].getVertices().length; j++ )
						{
							this.drawVn( gl, this.f[i].getVertexNormals()[j] );
							this.drawVt( gl, this.f[i].getTextureVertices()[j] );
							this.drawV( gl, this.f[i].getVertices()[j] );
						}
					}
					gl.glEnd();
				}
			}
		}
		gl.glPopMatrix();
		gl.glDisable( GL2.GL_NORMALIZE );
	}
	
	/**
	 * Methode zeichnet alle Vertices
	 * 
	 * @param gl GL2
	 * @param id Gibt die ID an
	 */
	private void drawV( GL2 gl, int id )
	{
		id--;
		if( id == 0 || id >= this.v.length )
			return;
		
		Vertex v = this.v[id];
		
		if( v == null )
			return;
		
		gl.glVertex4f( v.getX(), v.getY(), v.getZ(), v.getW() );
	}
	
	/**
	 * Methode zeichnet alle Parameter-Space-Vertices
	 * 
	 * @param gl GL2
	 * @param id Gibt die ID an
	 */
	private void drawVp( GL2 gl, int id )
	{
		id--;
		if( id < 0 || id >= this.vp.length )
			return;
		
		ParameterSpaceVertex vp = this.vp[id];
		
		if( vp == null )
			return;
		
		//TODO Es fehlt eine Implementierung von Vp
	}
	
	/**
	 * Methode zeichnet alle Texture-Vertices
	 * 
	 * @param gl GL2
	 * @param id Gibt die ID an
	 */
	private void drawVt( GL2 gl, int id )
	{
		id--;
		if( id < 0 || id >= this.vt.length )
			return;
		
		TextureVertex vt = this.vt[id];
		
		if( vt == null )
			return;
		
		gl.glTexCoord3f( vt.getU(), vt.getV(), vt.getW() );
	}
	
	/**
	 * Methode zeichnet alle Vertex-Normalen
	 * 
	 * @param gl GL2
	 * @param id Gibt die ID an
	 */
	private void drawVn( GL2 gl, int id )
	{
		id--;
		if( id < 0 || id >= this.vn.length )
			return;
		
		VertexNormal vn = this.vn[id];
		
		if( vn == null )
			return;
		
		gl.glNormal3f( vn.getI(), vn.getJ(), vn.getK() );
	}
}