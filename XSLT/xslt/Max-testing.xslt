<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:myfn="http://www.amitk.com/test/xml/max"
	version="2.0">
	<xsl:output method="text" indent="yes" omit-xml-declaration="no" />

	<xsl:function name="myfn:sort-employees" as="xs:string*">
	  <xsl:param name="in" as="xs:string*"/>
	  <xsl:perform-sort select="$in">
	    <xsl:sort select="."/>
	  </xsl:perform-sort>
	</xsl:function>


	<xsl:template match="/">
		<xsl:perform-sort select="//employee">
			<xsl:sort select="xs:dateTime(joined)" />
		</xsl:perform-sort>
		
		Emp with max join date:
		
		<xsl:copy-of select="//employee[joined = max(for $x in //employee/joined return xs:dateTime($x))]"></xsl:copy-of>
		
	</xsl:template>
	

</xsl:stylesheet>