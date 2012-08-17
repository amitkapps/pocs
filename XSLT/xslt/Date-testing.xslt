<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="xml" indent="yes" omit-xml-declaration="no" />

	<xsl:template match="/aaa">
		<xsl:variable name="dt" select="xsl:current-dateTime()" />
		<time>
			<xsl:value-of select="$dt" />
		</time>
		<day>
			<xsl:value-of select="xsl:get-day-from-dateTime($dt)" />
		</day>
		<month>
			<xsl:value-of select="xsl:get-month-from-dateTime($dt)" />
		</month>
		<year>
			<xsl:value-of select="xsl:get-year-from-dateTime($dt)" />
		</year>
		<hours>
			<xsl:value-of select="xsl:get-hours-from-dateTime($dt)" />
		</hours>
		<minutes>
			<xsl:value-of select="xsl:get-minutes-from-dateTime($dt)" />
		</minutes>
		<seconds>
			<xsl:value-of select="xsl:get-seconds-from-dateTime($dt)" />
		</seconds>
		<timezone>
			<xsl:value-of select="xsl:get-timezone-from-dateTime($dt)" />
		</timezone>
	</xsl:template>

</xsl:stylesheet>