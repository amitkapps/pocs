<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:miltns="http://www.matson.com/milportal/schema"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:mileem="http://www.matson.com/mil/eem"
	exclude-result-prefixes="#all"

	xsi:schemaLocation="http://www.w3.org/1999/XSL/Transform http://www.w3.org/2007/schema-for-xslt20.xsd"
	>
	<xsl:output method="xml"/>

	<xsl:variable name="mandatoryXPaths" as="xs:string*">
	    <xsl:text>Employee/id</xsl:text>
	    <xsl:text>Employee/firstName</xsl:text>
	    <xsl:text>Employee/firstName1</xsl:text>
	</xsl:variable>
		
	<!-- Start with the root node -->
	<xsl:template match="/">
		<errors>
			
			<xsl:value-of select="for $xpath in $mandatoryXPaths return count($xpath)" />
		</errors>		
	
	</xsl:template>

</xsl:stylesheet>