<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <xsl:element name="unit-list" >
            <xsl:attribute name="count">
                <xsl:value-of select="count(unitsShipped/unit)"/>
            </xsl:attribute> 
            <xsl:for-each select="unitsShipped/unit">
                <xsl:sort select="location/type" order="ascending"/>
                <xsl:sort select="location/code" order="descending" />
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="unit">
        <xsl:element name="shipped-unit">
            <xsl:attribute name="vinsight-id" >
            <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:element name="shipper">
                <xsl:value-of select="shipperName"/>
            </xsl:element>             
            <xsl:element name="consignee">
                <xsl:value-of select="consigneeName"/>
            </xsl:element>             
        </xsl:element>
    </xsl:template>
    
</xsl:stylesheet>
