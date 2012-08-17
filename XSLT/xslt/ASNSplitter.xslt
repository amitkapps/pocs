<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:output method="xml" />

	<xsl:template match="/" >
		<xsl:comment>Copy of ASN Core</xsl:comment>
		<xsl:copy >
			<xsl:apply-templates select="/XMLTransmission" mode="copy"/>
		</xsl:copy>
		<!-- 
		<xsl:for-each select="/XMLTransmission/XMLGroup/XMLTransaction/ASN/TradePartner">
			<xsl:copy-of select="."/>
		</xsl:for-each>
		 -->
	</xsl:template>
	
	<!-- Template to copy all attributes of the passed element node -->
	<xsl:template name="copyAttributes">
		<xsl:for-each select="@*">
			<xsl:copy/>
		</xsl:for-each>
	</xsl:template>
	
	<!-- retain the XMLTransmission tag -->
	<xsl:template match="/XMLTransmission" mode="copy">
		<xsl:copy >
			<xsl:call-template name="copyAttributes"/>
			<xsl:apply-templates select="XMLGroup" mode="copy"/>
		</xsl:copy>
	</xsl:template>

	<!-- retain the XML Group tag -->
	<xsl:template match="XMLGroup" mode="copy">
		<xsl:copy >
			<xsl:call-template name="copyAttributes"/>
			<xsl:apply-templates select="XMLTransaction" mode="copy"/>
		</xsl:copy>
	</xsl:template>

	<!-- create multiple XMLTransaction tags based on the number of TradePartners -->
	<xsl:template match="XMLTransaction" mode="copy">
		<xsl:copy>
			<xsl:call-template name="copyAttributes"/>
			<xsl:apply-templates select="ASN" mode="copy"/>
		</xsl:copy>

		<xsl:for-each select="ASN/*[name() = 'TradePartner']">		
			<XMLTransaction>
				<xsl:attribute name="CtrlNumber">
					<xsl:value-of select="position() + 1"/>
				</xsl:attribute>
				<xsl:attribute name="TransactionType">ASN</xsl:attribute>
				<ASN>
					<!-- Copy the core elements of the ASN for this subsequent transaction -->
					<xsl:apply-templates select=".." mode="createCoreElements" />
					<!-- TradePartner for this XML Transaction -->
					<xsl:apply-templates select="." mode="copy"/>
				</ASN>
			</XMLTransaction>
		</xsl:for-each>
	</xsl:template>
	
	<!-- Copy all from ASN except the trade partners -->
	<xsl:template match="ASN" mode="copy">
		<xsl:copy >
			<xsl:call-template name="copyAttributes"/>
			<xsl:apply-templates select="*[not(name() = 'TradePartner')]" mode="copyForInitialXMLTransaction"/>
		</xsl:copy>
	</xsl:template>
	
	<!-- Deep Copy all non TradePartner nodes under XMLTransaction/ASN-->
	<xsl:template match="XMLTransaction/ASN/*[not(name() = 'TradePartner')]" mode="copyForInitialXMLTransaction">
		<xsl:copy-of select="."/>
	</xsl:template>

	<!-- Copy the trade partner -->
	<xsl:template match="XMLTransaction/ASN/*[name() = 'TradePartner']" mode="copy">
		<xsl:copy-of select="Date"/>
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="ASN" mode="createCoreElements">
		<PurposeCd>04</PurposeCd>
		<xsl:copy-of select="ShipmentID"/>
		<xsl:copy-of select="CarrierSCAC"/>
		<xsl:copy-of select="Mode"/>
	</xsl:template>
	
</xsl:stylesheet>