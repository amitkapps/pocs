<?xml version="1.0" encoding="UTF-8"?>
<!-- *******************************************************************************
	  This stylesheet converts an incoming MIL User Profile xml into an MDI understandable
	  format - Org Rel message.
	 *******************************************************************************
 -->
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.w3.org/1999/XSL/Transform http://www.w3.org/2007/schema-for-xslt20.xsd"
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
				Sub level XMLGroup which is just a holder for multiple transactions.
				In this case it'll always be a sinble transaction. 
			-->
			<XMLGroup CtrlNumber="1"
				IncludedMessages="IncludedMessage" GroupType="OR">
				
				<!-- *** XMLTransaction abstracting a unit of work ***-->
				<XMLTransaction TransactionType="ORGREL"
					CtrlNumber="1">
					
					<!-- *** ASN Transaction for the incoming User Profile *** -->
					<Organization>
						<OrganizationID>
							<xsl:value-of select="userProfile/customProfile/parentId"/>
						</OrganizationID>
						<OrganizationName>UNKNOWN</OrganizationName>
						<Responsible>
							<TradePartner>
								<TradePartnerName><xsl:value-of select="userProfile/coreProfile/companyName"/></TradePartnerName>
								<TradePartnerID Qualifier="ZZ"><xsl:value-of select="userProfile/customProfile/parentId" /></TradePartnerID>
								<!-- Assuming the first address as the Responsible Trade Partner Address - TBD-->
								<xsl:variable name="responsibleTradePartnerAddress" select="userProfile/coreProfile/addressList/address[position() = 1]"/>
								<xsl:if test="$responsibleTradePartnerAddress">
									<TradePartnerAddress>
										<Street><xsl:value-of select="$responsibleTradePartnerAddress/addressLine1"/></Street>
										<Street><xsl:value-of select="$responsibleTradePartnerAddress/addressLine2"/></Street>
										<City><xsl:value-of select="$responsibleTradePartnerAddress/cityName"/></City> 
										<StateProvinceCd><xsl:value-of select="$responsibleTradePartnerAddress/stateCode"/></StateProvinceCd> 
										<PostalCd><xsl:value-of select="$responsibleTradePartnerAddress/postalCode"/></PostalCd> 
										<CountryCd><xsl:value-of select="$responsibleTradePartnerAddress/countryCode"/></CountryCd> 
									</TradePartnerAddress>
								</xsl:if>
							</TradePartner>
						</Responsible>
						<xsl:apply-templates select="userProfile/customProfile/milCustomUserControllingParties/controllingParties" mode="createAccessTags"/>
						
						<AppUser DisabledFlag="UNKNOWN">
							<UserID><xsl:value-of select="userProfile/coreProfile/userProfileId"/></UserID>
							<FirstName><xsl:value-of select="userProfile/coreProfile/firstName"/></FirstName>
							<LastName><xsl:value-of select="userProfile/coreProfile/lastName"/></LastName>
							<RoleID>UNKNOWN</RoleID>
							<Password>UNKNOWN</Password>
							<Company><xsl:value-of select="userProfile/coreProfile/companyName"/></Company>
							<!-- Assuming Phone number to be the first non fax number from the phone list- TBD -->
							<PhoneNumber><xsl:value-of select="userProfile/coreProfile/phoneList/phone[not(phoneType/typeCode = 'WFX')][position() = 1]/phoneNumber"/></PhoneNumber>
							<EmailAddress><xsl:value-of select="userProfile/coreProfile/emailAddress"/></EmailAddress>
							<!-- Pick the second address as the user address- TBD -->
							<xsl:variable name="userAddressElement" select="userProfile/coreProfile/addressList/address[position() = 2]"></xsl:variable>
							<xsl:if test="$userAddressElement">
								<UserAddress>
									<Street><xsl:value-of select="$userAddressElement/addressLine1"/></Street>
									<Street><xsl:value-of select="$userAddressElement/addressLine2"/></Street>
									<City><xsl:value-of select="$userAddressElement/cityName"/></City> 
									<StateProvinceCd><xsl:value-of select="$userAddressElement/stateCode"/></StateProvinceCd> 
									<PostalCd><xsl:value-of select="$userAddressElement/postalCode"/></PostalCd> 
									<CountryCd><xsl:value-of select="$userAddressElement/countryCode"/></CountryCd> 
								</UserAddress>
							</xsl:if>
						</AppUser>											
					</Organization>
				</XMLTransaction>
			</XMLGroup>
		</XMLTransmission>
	</xsl:template>

	<xsl:template match="controllingParties" mode="createAccessTags">
		<Access ActionCd="ADD">
			<TradePartner>
				<TradePartnerName><xsl:value-of select="controllingPartyName"/></TradePartnerName>
				<TradePartnerID Qualifier="ZZ"><xsl:value-of select="controllingPartyId" /></TradePartnerID>
				<TradePartnerAddress>
					<Street><xsl:value-of select="addressLine1"/></Street>
					<Street><xsl:value-of select="addressLine2"/></Street>
					<City><xsl:value-of select="cityName"/></City> 
					<StateProvinceCd><xsl:value-of select="stateCode"/></StateProvinceCd> 
					<PostalCd><xsl:value-of select="zipPostalCode"/></PostalCd> 
					<CountryCd><xsl:value-of select="countryCode"/></CountryCd> 
				</TradePartnerAddress>
			</TradePartner>
		</Access>		
	</xsl:template>
	
</xsl:stylesheet>