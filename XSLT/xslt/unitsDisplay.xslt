<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Units Shipped</title>
            </head>
            <body>
                <h1>Units Shipped</h1>
                <table>
                    <xsl:apply-templates select="unitsShipped/unit"/>
                </table>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="unit">
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td><xsl:value-of select="shipperClass"/></td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
