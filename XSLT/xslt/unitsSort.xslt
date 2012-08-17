<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <xsl:for-each select="unitsShipped/unit">
            <xsl:sort select="location/type" order="ascending"/>
            <xsl:sort select="location/code" order="descending" />
            <xsl:apply-templates select="."></xsl:apply-templates>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="unit">
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td><xsl:value-of select="shipperClass"/></td>
            <td><xsl:value-of select="consigneeName"/></td>
        </tr>
    </xsl:template>
    
</xsl:stylesheet>
