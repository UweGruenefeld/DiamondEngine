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

import diamond.manager.shader.ShaderProgram;
import diamond.manager.texture.Texture;

/**
 * Das Interface Template definiert, was die Complexion-Klasse implementieren muss
 * 
 * @version 0.1 pre-alpha, 2013-02-21
 * @author Uwe Wilko Gruenefeld
 */
public interface Template 
{
	/**
	 * Methode setzt die Display-Variable und 
	 * bestimmt damit was angezeigt wird
	 * 
	 * @param display Display-Enum
	 */
	public void setDisplay( Display display );
	
	/**
	 * Methode liefert die Display-Variable
	 * 
	 * @return Liefert die Display-Variable
	 */
	public Display getDisplay();
	
	/**
	 * Methode setzt die angegebene Texture in die erste Textureeinheit
	 * 
	 * @param texture Texture die gesetzt werden soll
	 */
	public void setTexture( Texture texture );
	
	/**
	 * Methode setzt die angegebene Texture in die angebene Textureeinheit
	 * 
	 * @param textureUnit Texture Einheit
	 * @param texture Texture die gesetzt werden soll
	 */
	public void setTexture( int textureUnit, Texture texture );
	
	/**
	 * Methode liefert alle Texturen aus allen Textureeinheiten
	 * 
	 * @return Liefert alle Texturen aus allen Textureeinheiten
	 */
	public Texture[] getTextures();
	
	/**
	 * Methode liefert die Texture der ersten Textureeinheit
	 * 
	 * @return Liefert die Texture der ersten Textureinheit
	 */
	public Texture getTexture();
	
	/**
	 * Methode liefert die Texture aus der gewaehlten Textureeinheit
	 * 
	 * @param textureUnit Textureeinheit
	 * @return Liefert die Texture aus der gewaehlten Textureeinheit
	 */
	public Texture getTexture( int textureUnit );
	
	/**
	 * Methode setzt das Material
	 * 
	 * @param material Material
	 */
	public void setMaterial( Material material );
	
	/**
	 * Methode liefert das Material
	 * 
	 * @return Liefert das Material
	 */
	public Material getMaterial();
	
	/**
	 * Methode setzt die Farbe
	 * 
	 * @param color Farbe
	 */
	public void setColor( Color color );
	
	/**
	 * Methode liefert die Farbe
	 * 
	 * @return Liefert die Farbe
	 */
	public Color getColor();
	
	/**
	 * Methode aktiviert den Shader der Klasse
	 */
	public void enableShader();
	
	/**
	 * Methode deaktiviert den Shader der Klasse
	 */
	public void disableShader();
	
	/**
	 * Methode ueberprueft ob der Shader aktiviert ist oder nicht
	 * 
	 * @return Liefert true, wenn der Shader aktiviert ist, sonst false
	 */
	public boolean useShader();
	
	/**
	 * Methode setzt den Shader
	 * 
	 * @param shader Shader
	 */
	public void setShader( ShaderProgram shader );
	
	/**
	 * Methode liefert den Shader
	 * 
	 * @return Liefert den Shader
	 */
	public ShaderProgram getShader();
	
	/**
	 * Methode aktiviert oder deaktivert Culling
	 * 
	 * @param culling Wenn true, dann aktiviert, sonst deaktivert
	 */
	public void setCulling( boolean culling );
	
	/**
	 * Methode prueft ob Culling eingeschaltet ist
	 * 
	 * @return Liefert true, wenn Culling eingeschaltet ist, sonst false
	 */
	public boolean isCulling();
	
	/**
	 * Methode aktivert Back Face oder Front Face
	 * 
	 * @param backFace Wenn true, dann Back Face, sonst Front Face
	 */
	public void setBackFace( boolean backFace );
	
	/**
	 * Methode prueft ob Back Face oder Front Face eingestellt ist
	 * 
	 * @return Liefert true, wenn Back Face, sonst false
	 */
	public boolean isBackFace();
}
