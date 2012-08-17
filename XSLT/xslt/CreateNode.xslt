<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:miltns="http://www.matson.com/milportal/schema"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:mileem="http://www.matson.com/mil/eem"
	exclude-result-prefixes="#all"
	>
	<xsl:output method="xml"/>

	<!-- Start with the root node -->
	<xsl:template match="/">
		<xsl:copy-of select="mileem:createParamTag('type1',CreateNode/tag1)"/>
		<xsl:copy-of select="mileem:createParamTag('type2',CreateNode/tag2)"/>
		<xsl:copy-of select="mileem:createParamTag('type3',CreateNode/tag3)"/>
		<xsl:copy-of select="mileem:createParamTag('type4',CreateNode/tag4)"/>
	</xsl:template>

	<xsl:function name="mileem:createParamTag">
		<xsl:param name="type"/>
		<xsl:param name="sourceTextNode"/>
		<xsl:if test="$sourceTextNode">
			<xsl:message><xsl:value-of select="$sourceTextNode"/></xsl:message>
			<Param type="{$type}">
				<xsl:choose>
					<xsl:when test="not($sourceTextNode = '')">
						<xsl:value-of select="$sourceTextNode"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>NULL</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</Param>
		</xsl:if>
	</xsl:function>
	
	<!-- Formats the passed xs:datetime to MDI date format i.e. "yyyyMMdd HHmmss" -->
	<xsl:function name="mileem:fnFormatAsMDIDate">
		<xsl:param name="xsDateTime" as="xs:dateTime"/>
		<xsl:value-of select="fn:format-dateTime($xsDateTime, '[Y0001][M01][D01] [H01][m01][s01]')"/>
	</xsl:function>

</xsl:stylesheet>