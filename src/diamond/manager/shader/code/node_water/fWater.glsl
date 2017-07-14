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

// Fragment Shader - Water
// version 0.1 alpha, 2013-02-17

uniform sampler2D waterTexture;
uniform sampler2D normalTexture;
	
uniform float time;
	
varying vec3 lightVec;
varying vec3 halfVec;
varying vec3 eyeVec;

void main( void )
{
	vec3 normal = normalize( texture2D( normalTexture, 
		gl_TexCoord[0].st / 2 + vec3( time, 0, 0 )).rgb * 2.0 - 1.0 );

	float lamberFactor = max( dot( lightVec, normal ), 0.0 );
	
	vec4 diffuseMaterial;
	vec4 specularMaterial;
	
	float shininess;
  
	vec4 ambientLight = gl_LightSource[0].ambient;	
	vec4 diffuseLight = gl_LightSource[0].diffuse;
	vec4 specularLight = gl_LightSource[0].specular;
	
	vec4 color = vec4( 0, 0, 0, 1 );
	
	if( lamberFactor > 0.0 )
	{
		diffuseMaterial = texture2D( waterTexture, gl_TexCoord[0].st );
		
		specularMaterial = vec4( 1.0 );
		specularLight = gl_LightSource[0].specular;
		shininess = pow( max( dot( halfVec, normal ), 0.0 ), 2.0 );
		 
		color = diffuseMaterial * diffuseLight * lamberFactor;
		color += specularMaterial * specularLight * shininess;				
	}
	
	color += ambientLight;
	
	gl_FragColor += vec4( color.rgb, 0.4 );
	//gl_FragColor += vec4( color.rgb, time );
}