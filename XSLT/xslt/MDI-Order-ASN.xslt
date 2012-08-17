<?xml version="1.0" encoding="UTF-8"?>
<!-- *******************************************************************************
	  This stylesheet converts an incoming MIL Order xml into an MDI understandable
	  format, with a few exceptions.
	  1. All the Generated TradePartner Tags are retained under a single ASN tag. 
	  	MDI however can handle only 2 Tradepartner tags under the ASN tag - one for
	  	the shipper and the other for the consignee.
	  2. MDI stores a Date tag associated with a TradePartner outside of the 
	  	TradePartner tag. and associates it to the TradePartner based on the 
	  	dateTypeCd attribute's value. In this case since there can be multiple shipper
	  	and consignees (TradePartner(s)) for an order we retain the DateTag inside of
	  	the TradePartner tag so the association between them is maintained in a simple
	  	manner. All the TradePartners retained inside a single XMLTransaction tag.
	  	
	  Another splitter stylesheet processes the output of this stylesheet and breaks
	  up this single XMLTransaction tag into multiple XML Transactions, where
	  there is only one TradePartner per transaction and the Date tag is also moved
	  outside of the TradePartner tag.
	 *******************************************************************************
 -->
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:miltns="http://www.matson.com/milportal/schema"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	>
	<xsl:output method="xml" />


	<!-- Start with the root node -->
	<xsl:template match="/">
		<!-- ******
			 Generate the Top level XMLTransmission tag 
			 The attributes will be overridden with real values from the application
			 after this xslt generates the xml
		-->
		<XMLTransmission Sender="Sender" Receiver="Receiver"
			CtrlNumber="CtrlNumber" Timestamp="Timestamp">

			<!-- **** 
				Sub level XMLGroup which is just a holder for multiple transactions 
				the IncludedMessages attribute will be overridden by the splitter XSLT
				that breaks up the output from this xslt so as to have multiple XMLTransactions
				based on the number of stops
			-->
			<XMLGroup CtrlNumber="1"
				IncludedMessages="IncludedMessage" GroupType="SH">
				
				<!-- *** XMLTransaction abstracting a unit of work ***-->
				<XMLTransaction TransactionType="ASN"
					CtrlNumber="1">
					
					<!-- *** ASN Transaction for the incoming mil Order *** -->
					<ASN>
					
					    <xsl:if test="not(miltns:Order/miltns:commandType = 'UPDATE')">
					    	<xsl:value-of  select="error(xs:QName('err'),'Oh, not again',.)"/>
					    </xsl:if>
						<!-- **** CORE TAGS **** -->
						<!-- Generate MDI PurposeCd tag from commandType -->
						<xsl:apply-templates select="miltns:Order/miltns:commandType" mode="PurposeCdTag" />
						<!-- MDI ShipmentID from orderNumber-->
						<ShipmentID>
							<xsl:value-of select="miltns:Order/miltns:orderNumber" />
						</ShipmentID>
						<!-- MDI CarrierSCAC hardcoded, TODO: ideal way would be to override in the application 
						     and not hardcode here-->
						<CarrierSCAC>MIOS</CarrierSCAC>
						<!-- MDI Mode is always X- meaning intermodal-->
						<Mode>X</Mode>
						
						<!-- **** Other optional tags representing header information **** -->
						<!-- Optional MDI Measure tag for totalWeight-->
						<xsl:call-template name="generateASNMeasureTag" >
							<xsl:with-param name="uomCd">LB</xsl:with-param>
							<xsl:with-param name="qualifier">G</xsl:with-param>
							<xsl:with-param name="measureData" select="miltns:Order/miltns:totalWeight" />
						</xsl:call-template>

						<!-- Measure tag for total pieces -->
						<xsl:call-template name="generateASNMeasureTag" >
							<xsl:with-param name="uomCd">PC</xsl:with-param>
							<xsl:with-param name="qualifier">SHQ</xsl:with-param>
							<xsl:with-param name="measureData" select="miltns:Order/miltns:totalPieces" />
						</xsl:call-template>
						
						<!-- MDI Location tags -->
						<!-- Location type cd OR from originCity -->
						<xsl:apply-templates select="miltns:Order/miltns:originCity" />
						<!-- Location type cd DE from destinationCity-->
						<xsl:apply-templates select="miltns:Order/miltns:destinationCity" />

						<!-- **** MDI REFERENCE TAGS **** -->
						<!-- Reference Code GV from orderNumber -->
						<xsl:call-template name="generateASNReferenceTag" >
							<xsl:with-param name="refTypeCd">GV</xsl:with-param>
							<xsl:with-param name="refData" select="miltns:Order/miltns:orderNumber" />
						</xsl:call-template>
						
						<!-- Reference Code 76 for totalMiles-->
						<xsl:call-template name="generateASNReferenceTag" >
							<xsl:with-param name="refTypeCd">76</xsl:with-param>
							<xsl:with-param name="refData" select="miltns:Order/miltns:totalMiles" />
						</xsl:call-template>
						
						<!-- Reference Code IT/Primary Customer reference number-->
						<xsl:call-template name="generateASNReferenceTag" >
							<xsl:with-param name="refTypeCd">IT</xsl:with-param>
							<xsl:with-param name="refData" select="miltns:Order/miltns:primaryReferenceNumber" />
						</xsl:call-template>
						
						<!-- Reference Code CR/Customer Reference number & BM - Bill Of Lading etc.-->
						<xsl:apply-templates select="miltns:Order/miltns:ReferenceNumber" mode="ReferenceTag"/>
						<!-- Reference Code ACC/ Order Status -->
						<xsl:call-template name="generateASNReferenceTag" >
							<xsl:with-param name="refTypeCd">ACC</xsl:with-param>
							<xsl:with-param name="refData" select="miltns:Order/miltns:orderStatus" />
						</xsl:call-template>
						<!-- Reference Code QN / Stop count -->
						<xsl:apply-templates
							select="miltns:Order/miltns:commandType" mode="ReferenceTag" />
							
						<!-- **** MDI DATE TAGS **** -->
						<!-- Date tag for SHP (mandatory shipment date)- which will always exist-->
						<xsl:call-template name="generateASNDateTag" >
							<xsl:with-param name="dateTypeCd">SHP</xsl:with-param>
							<xsl:with-param name="xsDateTime" select="miltns:Order/miltns:createdDate" />
						</xsl:call-template>
						<!-- Date tag based on different order status' -->
						<xsl:apply-templates select="miltns:Order/miltns:orderStatus" mode="DateTag" />
							
						<!-- **** MDI DATE AND TRADE PARTNER TAGS **** -->
						<!-- Create trade partner for LW Role Cd - the customer -->
						<xsl:apply-templates select="miltns:Order/miltns:customerName" mode="TradePartnerTag" />
						<!-- trade partner  tags from Shipment/Stop tags -->
						<xsl:apply-templates select="miltns:Order/miltns:Shipment/miltns:Stop" />
					</ASN>
				</XMLTransaction>
			</XMLGroup>
		</XMLTransmission>
	</xsl:template>

	<!-- ***** TEMPLATES FOR CORE MDI TAGS ***** -->
	<!-- Resolve MDI PurposeCd from commandType -->
	<xsl:template match="miltns:Order/miltns:commandType"
		mode="PurposeCdTag">
		<!-- Ascertain the purpose code based on the MIL command type -->
		<xsl:variable name="purposeCd">
			<xsl:choose>
				<xsl:when test=" . = 'CREATE'">
					<xsl:text>05</xsl:text>
				</xsl:when>
				<xsl:when test=" . = 'REPLACE'">
					<xsl:text>05</xsl:text>
				</xsl:when>
				<xsl:when test=" . = 'DELETE'">
					<xsl:text>03</xsl:text>
				</xsl:when>
				<xsl:when test=" . = 'UPDATE'">
					<xsl:text>04</xsl:text>
				</xsl:when>

			</xsl:choose>
		</xsl:variable>

		<PurposeCd>
			<xsl:value-of select="$purposeCd" />
		</PurposeCd>
	</xsl:template>

	<!-- Origin Location from originCity -->
	<xsl:template match="miltns:Order/miltns:originCity">
		<Location LocTypeCd="OR">
			<Place>
				<City>
					<xsl:value-of select="." />
				</City>
				<StateProvinceCd>
					<xsl:value-of select="../miltns:originState" />
				</StateProvinceCd>
			</Place>
		</Location>
	</xsl:template>

	<!-- Destination location from destinationCity -->
	<xsl:template match="miltns:Order/miltns:destinationCity">
		<Location LocTypeCd="DE">
			<Place>
				<City>
					<xsl:value-of select="." />
				</City>
				<StateProvinceCd>
					<xsl:value-of select="../miltns:destinationState" />
				</StateProvinceCd>
			</Place>
		</Location>
	</xsl:template>
	<!-- *********************************************************** -->

	<!-- ***** TEMPLATES FOR MDI REFERENCE TAGS ***** -->

	<xsl:template match="miltns:Order/miltns:ReferenceNumber" mode="ReferenceTag">
		<xsl:variable name="refTypeCd">
			<xsl:choose>
				<xsl:when test=" child::miltns:type = 'BOL'">
					<xsl:text>BM</xsl:text>
				</xsl:when>
				<xsl:when test=" child::miltns:type = 'PO'">
					<xsl:text>OP</xsl:text>
				</xsl:when>
				<xsl:when test=" child::miltns:type = 'PU'">
					<xsl:text>P8</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>X</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:if test="not( $refTypeCd = 'X' )">
			<xsl:call-template name="generateASNReferenceTag">
				<xsl:with-param name="refTypeCd" select="$refTypeCd" />
				<xsl:with-param name="refData" select="miltns:number" />
			</xsl:call-template>
		</xsl:if>

		<!-- Customer Reference Number -->
		<xsl:call-template name="generateASNReferenceTag">
			<xsl:with-param name="refTypeCd">CR</xsl:with-param>
			<xsl:with-param name="refData" select="miltns:number" />
		</xsl:call-template>
	</xsl:template>

	<!-- reference tag for count of stops - refTypeCd=QN -->
	<xsl:template match="miltns:Order/miltns:commandType"
		mode="ReferenceTag">
		<!-- Ascertain if we need to send the Stop counts -->
		<xsl:variable name="createStopCountReference">
			<xsl:choose>
				<xsl:when test=" . = 'CREATE'">
					<xsl:text>Y</xsl:text>
				</xsl:when>
				<xsl:when test=" . = 'REPLACE'">
					<xsl:text>Y</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>N</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:if test="$createStopCountReference = 'Y'">
			<xsl:call-template name="generateASNReferenceTag" >
				<xsl:with-param name="refTypeCd">QN</xsl:with-param>
				<xsl:with-param name="refData" select="count(../miltns:Shipment/*[name() = 'miltns:Stop'])" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<!-- *********************END REFERENCE TAGS *************************** -->

	<!-- *** Generate Date Tag for Order Received & On Schedule- not tied up with trade partner *** -->
	<xsl:template match="miltns:Order/miltns:orderStatus"
		mode="DateTag">
		<xsl:variable name="dateTypeCd">
			<xsl:choose>
				<xsl:when test=". = 'Order Received'">
					<xsl:text>008</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>X</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:if test="not($dateTypeCd = 'X')">
			<xsl:call-template name="generateASNDateTag" >
				<xsl:with-param name="dateTypeCd" select="$dateTypeCd"/>
				<xsl:with-param name="xsDateTime" select="../miltns:createdDate" />
			</xsl:call-template>
		</xsl:if>

	</xsl:template>


	<!-- *****************MDI TRADE PARTNER TAGS ********************* -->
	<!-- The customer trade partner (LW)-->
	<xsl:template match="miltns:Order/miltns:customerName"
		mode="TradePartnerTag">
		<TradePartner RoleCd="LW">
			<TradePartnerName>
				<xsl:value-of select="." />
			</TradePartnerName>
			<TradePartnerID>
				<xsl:value-of select="../miltns:controllingPartyID" />
			</TradePartnerID>
		</TradePartner>
	</xsl:template>

	<!-- **** Trade partner from stops template **** -->
	<xsl:template match="miltns:Order/miltns:Shipment/miltns:Stop">
		<!-- Identify the trade partner's role code -->
		<xsl:variable name="tradePartnerRoleCd">
			<xsl:choose>
				<!-- Shipper role code -->
				<xsl:when test="miltns:stopType = 'S'">
					<xsl:text>SH</xsl:text>
				</xsl:when>
				<!-- Consignee role code -->
				<xsl:when test="miltns:stopType = 'C'">
					<xsl:choose>
						<!-- Final Consignee role code -->
						<xsl:when
							test="miltns:stopEvent/miltns:eventType = 'AD'">
							<xsl:text>CH</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>CN</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>

		<!-- Create the trade partner tag for this stop -->
		<TradePartner>
			<xsl:attribute name="RoleCd">
				<xsl:value-of select="$tradePartnerRoleCd" />
			</xsl:attribute>
			<!-- we'll keep the date in here for this transformation
				but will programatically move out to be the child of ASN tag
				just so we know what trade partner is associated to what date tag
			-->
			<!-- check if stop Event exists -->
			<xsl:variable name="stopEventCount" >
				<xsl:value-of select="count(./*[name() = 'miltns:StopEvent'])"/>
			</xsl:variable>
			<xsl:choose>
				<xsl:when test="not($stopEventCount = 0)">
					<xsl:apply-templates select="miltns:StopEvent" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select=".[count(StopEvent) = 0]" mode="dateTagForNoStopEvent"/>
				</xsl:otherwise>
			</xsl:choose>
			

			<TradePartnerName>
				<xsl:value-of select="miltns:locationName" />
			</TradePartnerName>
			<TradePartnerID Qualifier="ZZ">
				<xsl:value-of select="miltns:locationID" />
			</TradePartnerID>
			<!-- We'll generate a TradePartnerAddress if we have the city at least -->
			<xsl:apply-templates select="miltns:city" />
		</TradePartner>
	</xsl:template>

	<!-- *** Template to generate MDI TradePartnerAddress *** -->
	<xsl:template
		match="miltns:Order/miltns:Shipment/miltns:Stop/miltns:city">
		<TradePartnerAddress>
			<Street>
				<xsl:value-of select="../miltns:address" />
			</Street>
			<City>
				<xsl:value-of select="." />
			</City>
			<StateProvinceCd>
				<xsl:value-of select="../miltns:state" />
			</StateProvinceCd>
			<!-- The country code is optional so we check for it first -->
			<xsl:if test="../miltns:country">
				<CountryCd>
					<xsl:value-of select="../miltns:country"/>
				</CountryCd>
			</xsl:if>
		</TradePartnerAddress>
	</xsl:template>

	<!-- ************ MDI DATE TAGS ASSOCIATED TO A TRADE PARTNER *************** -->
	<!-- **** Template to generate MDI Date for the TradePartner's events **** -->
	<xsl:template
		match="miltns:Order/miltns:Shipment/miltns:Stop/miltns:StopEvent">
		<xsl:variable name="dateTypeCd">
			<xsl:choose>
				<!-- Pickup Appointment Date -->
				<xsl:when test="miltns:eventType = 'PAP'">
					<xsl:text>860</xsl:text>
				</xsl:when>
				<!-- Delivery Appointment -->
				<xsl:when test="miltns:eventType = 'DAP'">
					<xsl:text>704</xsl:text>
				</xsl:when>
				<!-- Picked up -->
				<xsl:when test="miltns:eventType = 'APU'">
					<xsl:text>370</xsl:text>
				</xsl:when>
				<!-- Delivered - Actual -->
				<xsl:when test="miltns:eventType = 'ADL'">
					<xsl:text>035</xsl:text>
				</xsl:when>
				<!-- Final Delivery -->
				<xsl:when test="miltns:eventType = 'FDL'">
					<xsl:text>376</xsl:text>
				</xsl:when>
				<!-- Cancelled Pick Up Appointment -->
				<xsl:when test="miltns:eventType = 'XPA'">
					<xsl:text>175</xsl:text>
				</xsl:when>
				<!-- Cancelled Delivery Appointment -->
				<xsl:when test="miltns:eventType = 'XDA'">
					<xsl:text>061</xsl:text>
				</xsl:when>
				<!-- Pick Up ETA -->
				<xsl:when test="miltns:eventType = 'PET'">
					<xsl:text>369</xsl:text>
				</xsl:when>
				<!-- Delivery ETA -->
				<xsl:when test="miltns:eventType = 'DET'">
					<xsl:text>AA1</xsl:text>
				</xsl:when>
				<!-- Cancelled Pick Up ETA -->
				<xsl:when test="miltns:eventType = 'XPE'">
					<xsl:text>001</xsl:text>
				</xsl:when>
				<!-- Cancelled Delivery ETA -->
				<xsl:when test="miltns:eventType = 'XDE'">
					<xsl:text>177</xsl:text>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>

		<xsl:call-template name="generateASNDateTag" >
			<xsl:with-param name="dateTypeCd" select="$dateTypeCd"/>
			<xsl:with-param name="xsDateTime" select="miltns:eventDateTime" />
		</xsl:call-template>

	</xsl:template>

	<!-- Template to generate MDI Date tags for a Stop with no StopEvent for a Trade Partner -->
	<xsl:template
		match="miltns:Order/miltns:Shipment/miltns:Stop[count(StopEvent) = 0]" mode="dateTagForNoStopEvent">
		<xsl:variable name="dateTypeCd">
			<xsl:choose>
				<!-- Shipper means requested pickup -->
				<xsl:when test="miltns:stopType = 'S'">
					<xsl:text>118</xsl:text>
				</xsl:when>
				<!-- Consignee means requested delivery-->
				<xsl:when test="miltns:stopType = 'C'">
					<xsl:text>002</xsl:text>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>


		<xsl:call-template name="generateASNDateTag" >
			<xsl:with-param name="dateTypeCd" select="$dateTypeCd" />
			<xsl:with-param name="xsDateTime" select="miltns:requestedDate" />
		</xsl:call-template>
		
		<!-- Generate additional date tag for the requested Final Destination if this is the 
			 last consignee on the list.
		-->
		<xsl:if test="miltns:stopType = 'C'">
			<!-- ugly parse. we're trying to get the last consignee based on the sequenceNumber e.g. C1, C2, C3 etc... -->
			<xsl:if test="xs:integer(fn:substring(miltns:sequenceNo, 2, 1)) = max(for $seqNo in ../miltns:Stop[miltns:stopType = 'C']/miltns:sequenceNo return xs:integer(fn:substring($seqNo, 2, 1)) )">
				<xsl:call-template name="generateASNDateTag" >
					<xsl:with-param name="dateTypeCd">028</xsl:with-param>
					<xsl:with-param name="xsDateTime" select="miltns:requestedDate" />
				</xsl:call-template>
			</xsl:if>
		</xsl:if>

	</xsl:template>

	<!-- *********************************************************************** -->

	<!-- Other common templates -->

	<!-- Generates an ASN Measure tag for the passed uomCd, qualifier and measureData -->
	<xsl:template name="generateASNMeasureTag">
		<xsl:param name="uomCd" />
		<xsl:param name="qualifier" />
		<xsl:param name="measureData"/>
		
		<!-- the Measure tag -->
		<Measure UOMCd="LB" Qualifier="G">
			<xsl:attribute name="UOMCd">
				<xsl:value-of select="$uomCd"/>
			</xsl:attribute>
			<xsl:attribute name="Qualifier">
				<xsl:value-of select="$qualifier"/>
			</xsl:attribute>
			<xsl:value-of select="$measureData" />
		</Measure>

	</xsl:template>


	<!-- Generates an ASN Reference tag for the passed refTypeCd and refData -->
	<xsl:template name="generateASNReferenceTag">
		<xsl:param name="refTypeCd" />
		<xsl:param name="refData" />
		
		<!-- the Reference tag -->
		<Reference>
			<xsl:attribute name="RefTypeCd">
				<xsl:value-of select="$refTypeCd"/>
			</xsl:attribute>
			<xsl:value-of select="$refData" />
		</Reference>
	</xsl:template>
	
	<!-- Generates an ASN Date tag for the passed dateTypeCd and xsDateTime -->
	<xsl:template name="generateASNDateTag">
		<xsl:param name="dateTypeCd" />
		<xsl:param name="xsDateTime" as="xs:dateTime"/>
		
		<!-- the Date tag -->
		<Date>
			<xsl:attribute name="DateTypeCd">
				<xsl:value-of select="$dateTypeCd"/>
			</xsl:attribute>
			<xsl:call-template name="formatAsMDIDate" >
				<xsl:with-param name="xsDateTime" select="$xsDateTime" />
			</xsl:call-template>
		</Date>
	</xsl:template>
	
	<!-- Formats the passed xs:datetime to MDI date format i.e. "yyyyMMdd HHmmss" -->
	<xsl:template name="formatAsMDIDate">
		<xsl:param name="xsDateTime" as="xs:dateTime"/>
		<xsl:value-of select="fn:format-dateTime($xsDateTime, '[Y0001][M01][D01] [H01][m01][s01]')"/>
	</xsl:template>

</xsl:stylesheet>