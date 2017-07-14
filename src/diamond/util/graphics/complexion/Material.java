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
package diamond.util.graphics.complexion;

import javax.media.opengl.GL2;

/**
 * ADT-Klasse zum Verwalten von Materialen
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public class Material 
{	
	/*
	 * Materialtabelle
	 * 
 	 * Ambient
	 * Diffuse
	 * Specular
	 * Shininess
	 */
	private static final float[][][] MATERIAL = {
	
		{ // Default
		{ 0.2f, 0.2f, 0.2f, 1f }, 	
		{ 0.8f, 0.8f, 0.8f, 1f }, 		
		{ 0.0f, 0.0f, 0.0f, 1f }, 		
		{ 0f }},						
										   
		{ // Chrome 
		{ 0.25f, 0.25f, 0.25f, 1f }, 
		{ 0.4f, 0.4f, 0.4f, 1f }, 				
		{ 0.774597f, 0.774597f, 0.774597f, 1f }, 	
		{ 76.4f }},	 								
	
		{ // Gold
		{ 0.25f, 0.2f, 0.07f, 1f },	
		{ 0.75f, 0.61f, 0.23f, 1f },
		{ 0.63f, 0.65f, 0.37f, 1f },
		{ 0.4f }},
		
		{ // Silver
		{ 0.2f, 0.2f, 0.2f },			
		{ 0.5f, 0.5f, 0.5f },
		{ 0.5f, 0.5f, 0.5f },
		{ 0.8f }},
		
		{ // Black-Plastic
		{ 0.0f, 0.0f, 0.0f, 1.0f },	
		{ 0.01f, 0.01f, 0.01f, 1.0f },
		{ 0.5f, 0.5f, 0.5f, 1.0f },
		{ 0.25f }},
		
		{ // Emerald
		{ 0.0215f, 0.1745f, 0.0215f, 1.0f },	
		{ 0.07568f, 0.61424f, 0.07568f, 1.0f },
		{ 0.633f, 0.727811f, 0.633f, 1.0f },
		{ 0.6f }},
		
		{ // Jade
		{ 0.135f, 0.2225f, 0.1575f, 1.0f },	
		{ 0.54f, 0.89f, 0.63f, 1.0f },
		{ 0.316228f, 0.316228f, 0.216228f, 1.0f },
		{ 0.1f }},
		
		{ // Obsidian
		{ 0.05375f, 0.05f, 0.06625f, 1.0f },	
		{ 0.18275f, 0.17f, 0.22525f, 1.0f },
		{ 0.332741f, 0.328634f, 0.346435f, 1.0f },
		{ 0.3f }},
		
		{ // Pearl
		{ 0.25f, 0.20725f, 0.20725f, 1.0f },	
		{ 1f, 0.829f, 0.829f, 1.0f },
		{ 0.296648f, 0.296648f, 0.296648f, 1.0f },
		{ 0.088f }},
		
		{ // Ruby
		{ 0.175f, 0.01175f, 0.01175f, 1.0f },	
		{ 0.61424f, 0.04136f, 0.04136f, 1.0f },
		{ 0.727811f, 0.626959f, 0.626959f, 1.0f },
		{ 0.6f }},
		
		{ // Turquoise
		{ 0.1f, 0.18725f, 0.1745f, 1.0f },	
		{ 0.396f, 0.74151f, 0.69102f, 1.0f },
		{ 0.297254f, 0.30829f, 0.306678f, 1.0f },
		{ 0.1f }},
		
		{ // Brass
		{ 0.329412f, 0.223529f, 0.027451f, 1.0f },	
		{ 0.780392f, 0.568627f, 0.113725f, 1.0f },
		{ 0.992157f, 0.941176f, 0.807843f, 1.0f },
		{ 0.21794872f }},
		
		{ // Bronze
		{ 0.2125f, 0.1275f, 0.054f, 1.0f },	
		{ 0.714f, 0.4284f, 0.18144f, 1.0f },
		{ 0.393548f, 0.271906f, 0.166721f, 1.0f },
		{ 0.2f }},
		
		{ // Copper
		{ 0.19125f, 0.0735f, 0.0225f, 1.0f },	
		{ 0.7038f, 0.27048f, 0.0828f, 1.0f },
		{ 0.256777f, 0.137622f, 0.086014f, 1.0f },
		{ 0.1f }},
		
		{ // Black-Rubber
		{ 0.02f, 0.02f, 0.02f, 1.0f },	
		{ 0.01f, 0.01f, 0.01f, 1.0f },
		{ 0.4f, 0.4f, 0.4f, 1.0f },
		{ 0.078125f }}
	};
	
	/*
	 * Variablen zum Speichern des Materials
	 */
	private float[] ambient, diffuse, specular;
	private float shininess;
	
	/**
	 * Default-Konstruktor
	 * Erzeugt das Default-Material
	 */
	public Material()
	{
		this.set( DefaultMaterial.DEFAULT );
	}
	
	/**
	 * Konstruktor
	 * Erzeugt ein Material aus der Vorgabe
	 * 
	 * @param material DefaultMaterial
	 */
	public Material( DefaultMaterial material )
	{
		this.set( material );
	}
	
	/**
	 * Methode setzt das gewuenschte Material
	 * 
	 * @param material DefaultMaterial
	 */
	public void set( DefaultMaterial material )
	{
		this.set( Material.MATERIAL[material.getID()][0], Material.MATERIAL[material.getID()][1], 
				Material.MATERIAL[material.getID()][2], Material.MATERIAL[material.getID()][3][0] );
	}
	
	/**
	 * Methode setzt das Material mit Hilfe der Angaben
	 * 
	 * @param ambient Ambiente-Komponente
	 * @param diffuse Diffuse-Komponente
	 * @param specular Spektulare-Komponente
	 * @param shininess Leuchtkraft-Komponente
	 */
	public void set( float[] ambient, float[] diffuse, float[] specular, float shininess )
	{
		this.set( ambient, diffuse, specular );
		this.setShininess( shininess );
	}
	
	/**
	 * Methode setzt das Material mit Hilfe der Angaben
	 * 
	 * @param ambient Ambiente-Komponente
	 * @param diffuse Diffuse-Komponente
	 * @param specular Spektulare-Komponente
	 */
	public void set( float[] ambient, float[] diffuse, float[] specular )
	{
		this.setAmbient( ambient );
		this.setDiffuse( diffuse );
		this.setSpecular( specular );
	}
	
	/**
	 * Methode setzt die Ambiente-Komponente
	 * 
	 * @param ambient Ambiente-Komponente
	 */
	public void setAmbient( float[] ambient )
	{
		this.ambient = ambient;
	}
	
	/**
	 * Methode setzt die Diffuse-Komponente
	 * 
	 * @param diffuse Diffuse-Komponente
	 */
	public void setDiffuse( float[] diffuse )
	{
		this.diffuse = diffuse;
	}
	
	/**
	 * Methode setzt die Spektulare-Komponente
	 * 
	 * @param specular Spektulare-Komponente
	 */
	public void setSpecular( float[] specular )
	{
		this.specular = specular;
	}
	
	/**
	 * Methode setzt die Leuchtkraft-Komponente
	 * 
	 * @param shininess	Leuchtkraft-Komponente
	 */
	public void setShininess( float shininess )
	{
		this.shininess = shininess;
	}
	
	/**
	 * Methode liefert die Shininess eines Materials
	 * 
	 * @return Liefert die Shininess
	 */
	public float getShininess()
	{
		return this.shininess;
	}
	
	/**
	 * Methode benutzt die Materialeigenschaften dieses Objekts
	 * 
	 * @param gl Benoetigt GL2
	 */
	public void use( GL2 gl )
	{
		gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, this.ambient, 0 );
		gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, this.diffuse, 0 );
		gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, this.specular, 0 );
		gl.glMaterialf( GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, this.shininess );
	}
	
	/**
	 * Statische Methode setzt die Materialeigenschaften anhand einer Material-Konstanten
	 * 
	 * @param gl Benoetigt GL2
	 * @param material DefaultMaterial
	 */
	public static void use( GL2 gl, DefaultMaterial material )
	{
		gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, Material.MATERIAL[ material.getID() ][0], 0 );
		gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, Material.MATERIAL[ material.getID() ][1], 0 );
		gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, Material.MATERIAL[ material.getID() ][2], 0 );
		gl.glMaterialf( GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, Material.MATERIAL[ material.getID() ][3][0] );
	}
	
	/**
	 * Statische Methode setzt die Materialeigenschaften auf die Default-Eigenschaften
	 * 
	 * @param gl Benoetigt GL2
	 */
	public static void useDefault( GL2 gl )
	{
		Material.use( gl, DefaultMaterial.DEFAULT );
	}
}