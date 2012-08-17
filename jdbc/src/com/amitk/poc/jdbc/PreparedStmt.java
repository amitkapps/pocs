package com.amitk.poc.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

public class PreparedStmt {

	private static String[] projectStatus = {"Dev%","Tes%","Des%","Des%","Tes%","Des%","Des%","Des%","Dev%","Dev%","Tes%","Tes%","Des%","Dev%","Des%","Req%","Tes%","Tes%","Req%","Tes%","Tes%","Tes%","Des%","Des%","Dev%","Tes%","Req%","Dev%","Dev%","Req%","Dev%","Tes%","Dev%","Des%","Des%","Des%","Dev%","Des%","Req%","Tes%","Dev%","Des%","Req%","Dev%","Dev%","Tes%","Dev%","Dev%","Des%","Tes%","Dev%","Des%","Dev%","Des%","Des%","Des%","Dev%","Dev%","Des%","Des%","Tes%","Dev%","Dev%","Tes%","Des%","Dev%","Des%","Des%","Dev%","Des%","Dev%","Req%","Dev%","Req%","Req%","Req%","Des%","Req%","Dev%","Des%","Tes%","Des%","Des%","Tes%","Req%","Dev%","Dev%","Des%","Tes%","Tes%","Dev%","Dev%","Dev%","Req%","Des%","Dev%","Des%","Des%","Dev%","Req%","Des%","Req%","Des%","Tes%","Des%","Dev%","Req%","Des%","Des%","Tes%","Des%","Dev%","Des%","Des%","Req%","Dev%","Dev%","Dev%","Des%","Tes%","Req%","Tes%","Req%","Des%","Des%","Tes%","Dev%","Dev%","Dev%","Des%","Des%","Dev%","Dev%","Req%","Dev%","Dev%","Tes%","Tes%","Tes%","Dev%","Dev%","Des%","Des%","Dev%","Des%","Des%","Des%","Dev%","Des%","Req%","Des%","Req%","Dev%","Dev%","Dev%","Dev%","Des%","Des%","Dev%","Dev%","Dev%","Req%","Des%","Des%","Des%","Tes%","Des%","Tes%","Dev%","Dev%","Dev%","Dev%","Req%","Dev%","Dev%","Req%","Des%","Tes%","Dev%","Tes%","Des%","Dev%","Des%","Req%","Des%","Des%","Req%","Dev%","Dev%","Tes%","Des%","Dev%","Dev%","Des%","Req%","Req%","Dev%","Des%","Dev%","Tes%","Dev%","Dev%","Tes%","Des%","Req%","Req%","Req%","Req%","Req%","Req%","Dev%","Des%","Tes%","Req%","Dev%","Des%","Tes%","Req%","Dev%","Req%","Des%","Req%","Des%","Dev%","Des%","Dev%","Des%","Tes%","Dev%","Des%","Req%","Des%","Dev%","Req%","Dev%","Dev%","Dev%","Tes%","Req%","Dev%","Des%","Dev%","Des%","Tes%","Dev%","Tes%","Des%","Dev%","Dev%","Req%","Des%","Req%","Des%","Tes%","Dev%","Dev%","Dev%","Req%","Des%","Des%","Req%","Des%","Des%","Tes%","Tes%","Des%","Tes%","Dev%","Des%","Tes%","Tes%","Req%","Dev%","Des%","Des%","Des%","Des%","Des%","Des%","Des%","Dev%","Req%","Dev%","Des%","Req%","Des%","Des%","Des%","Des%","Dev%","Req%","Des%","Des%","Des%","Des%","Req%","Dev%","Des%","Req%","Des%","Tes%","Des%","Dev%","Des%","Dev%","Tes%","Req%","Des%","Tes%","Dev%","Des%","Tes%","Des%","Dev%","Des%","Dev%","Des%","Req%","Tes%","Dev%","Tes%","Des%","Req%","Tes%","Des%","Dev%","Dev%","Des%","Req%","Tes%","Des%","Des%","Des%","Dev%","Dev%","Des%","Dev%","Des%","Req%","Req%","Des%","Tes%","Tes%","Dev%","Tes%","Des%","Des%","Tes%","Tes%","Des%","Req%","Des%","Dev%","Des%","Dev%","Req%","Req%","Dev%","Tes%","Dev%","Dev%","Dev%","Des%","Req%","Req%","Des%","Dev%","Req%","Req%","Dev%","Dev%","Dev%","Dev%","Dev%","Tes%","Tes%","Des%","Req%","Des%","Des%","Dev%","Req%","Des%","Dev%","Req%","Des%","Tes%","Des%","Dev%","Dev%","Req%","Req%","Dev%","Dev%","Des%","Tes%","Des%","Tes%","Tes%","Req%","Req%","Dev%","Des%","Des%","Req%","Dev%","Tes%","Req%","Tes%","Req%","Tes%","Req%","Des%","Dev%","Req%","Req%","Req%","Dev%","Des%","Dev%","Dev%","Dev%","Req%","Dev%","Tes%","Tes%","Tes%","Des%","Dev%","Dev%","Tes%","Des%","Dev%","Dev%","Des%","Dev%","Dev%","Dev%","Tes%","Des%","Req%","Des%","Req%","Dev%","Des%","Dev%","Dev%","Des%","Dev%","Des%","Des%","Req%","Dev%","Tes%","Req%","Tes%","Des%","Dev%","Tes%","Tes%","Des%","Tes%","Des%","Des%","Des%","Tes%","Tes%","Des%","Tes%","Tes%","Des%","Dev%","Dev%","Req%","Dev%","Dev%","Des%","Tes%","Dev%","Des%","Des%","Tes%","Des%","Dev%","Des%","Dev%","Des%","Des%","Req%","Des%","Des%","Req%","Req%","Dev%","Des%","Dev%","Dev%","Dev%","Des%","Req%","Tes%","Dev%","Tes%","Tes%","Des%","Tes%","Dev%","Des%","Tes%","Tes%","Tes%","Des%","Tes%","Des%","Req%","Req%","Des%","Des%","Tes%","Dev%","Dev%","Tes%","Req%","Des%","Des%","Tes%","Des%","Tes%","Tes%","Tes%","Tes%","Dev%","Dev%","Des%","Req%","Des%","Dev%","Des%","Req%","Des%","Dev%","Req%","Des%","Tes%","Dev%","Des%","Des%","Des%","Req%","Tes%","Req%","Dev%","Des%","Des%","Req%","Tes%","Des%","Tes%","Req%","Req%","Tes%","Dev%","Des%","Des%","Dev%","Dev%","Req%","Req%","Dev%","Dev%","Dev%","Dev%","Tes%","Dev%","Req%","Dev%","Dev%","Des%","Des%","Dev%","Des%","Req%","Tes%","Dev%","Des%","Des%","Dev%","Dev%","Req%","Tes%","Des%","Dev%","Des%","Des%","Dev%","Des%","Req%","Dev%","Tes%","Tes%","Tes%","Dev%","Tes%","Req%","Tes%","Dev%","Req%","Dev%","Des%","Dev%","Req%","Dev%","Des%","Des%","Dev%","Tes%","Des%","Req%","Des%","Req%","Req%","Tes%","Dev%","Dev%","Des%","Dev%","Dev%","Des%","Dev%","Dev%","Req%","Des%","Req%","Req%","Req%","Des%","Req%","Des%","Tes%","Des%","Des%","Des%","Des%","Req%","Tes%","Dev%","Dev%","Des%","Req%","Dev%","Des%","Des%","Des%","Des%","Dev%","Req%","Tes%","Req%","Tes%","Des%","Des%","Tes%","Des%","Dev%","Req%","Tes%","Dev%","Des%","Dev%","Req%","Des%","Dev%","Dev%","Des%","Des%","Dev%","Des%","Req%","Des%","Req%","Dev%","Des%","Des%","Req%","Dev%","Tes%","Des%","Tes%","Tes%","Tes%","Tes%","Dev%","Req%","Req%","Tes%","Des%","Tes%","Dev%","Des%","Dev%","Dev%","Des%","Des%","Req%","Tes%","Dev%","Req%","Des%","Tes%","Des%","Des%","Dev%","Dev%","Dev%","Dev%","Dev%","Des%","Des%","Des%","Dev%","Dev%","Dev%","Des%","Dev%","Dev%","Des%","Dev%","Dev%","Req%","Des%","Dev%","Dev%","Des%","Tes%","Req%","Dev%","Des%","Des%","Req%","Tes%","Dev%","Dev%","Dev%","Req%","Dev%","Req%","Dev%","Tes%","Dev%","Des%","Dev%","Dev%","Dev%","Dev%","Des%","Dev%","Dev%","Dev%","Dev%","Dev%","Des%","Dev%","Des%","Dev%","Des%","Des%","Des%","Dev%","Dev%","Des%","Dev%","Dev%","Des%","Dev%","Des%","Dev%","Req%","Dev%","Dev%","Dev%","Des%","Des%","Dev%","Des%","Des%","Req%","Dev%","Req%","Tes%","Des%","Des%","Des%","Des%","Dev%","Dev%","Tes%","Des%","Des%","Tes%","Dev%","Dev%","Dev%","Req%","Tes%","Tes%","Req%","Des%","Des%","Tes%","Des%","Req%","Tes%","Req%","Des%","Dev%","Dev%","Dev%","Des%","Dev%","Dev%","Tes%","Dev%","Tes%","Dev%","Des%","Req%","Des%","Dev%","Req%","Des%","Des%","Des%","Req%","Des%","Des%","Dev%","Dev%","Dev%","Des%","Dev%","Tes%","Tes%","Des%","Req%","Dev%","Des%","Tes%","Dev%","Dev%","Dev%","Dev%","Req%","Dev%","Dev%","Tes%","Des%","Des%","Dev%","Req%","Req%","Dev%","Dev%","Req%","Dev%","Req%","Tes%","Tes%","Des%","Des%","Req%","Des%","Req%","Tes%","Dev%","Des%","Req%","Dev%","Tes%","Des%","Tes%","Tes%","Dev%","Tes%","Tes%","Des%","Des%","Dev%","Dev%","Tes%","Dev%","Des%","Des%","Req%","Req%","Tes%","Des%","Des%","Dev%","Des%","Tes%","Dev%","Des%","Dev%","Des%","Tes%","Des%","Des%","Des%","Des%","Req%","Des%","Des%","Tes%","Dev%","Dev%","Des%","Dev%","Tes%","Req%","Req%","Dev%","Tes%","Dev%","Dev%","Des%","Dev%","Tes%","Dev%","Dev%","Dev%","Req%","Dev%","Dev%","Dev%","Des%","Dev%","Dev%","Dev%","Dev%","Des%","Req%","Des%","Req%","Dev%","Dev%","Req%","Des%","Dev%","Dev%","Dev%","Dev%","Req%","Req%","Req%","Des%","Dev%","Req%","Dev%","Des%","Tes%","Tes%","Des%","Req%","Tes%","Dev%","Dev%","Req%","Des%","Dev%","Des%","Dev%","Des%","Dev%","Dev%","Des%","Des%","Dev%","Req%","Req%","Dev%","Dev%","Des%","Dev%","Des%","Des%","Des%","Des%","Des%","Req%","Req%","Tes%","Des%","Des%","Des%","Dev%","Req%","Des%","Des%","Dev%","Dev%","Dev%","Dev%","Des%","Des%","Tes%","Des%","Dev%","Dev%","Dev%","Des%","Dev%","Des%","Des%","Dev%","Req%","Req%","Des%","Tes%","Des%","Dev%","Dev%","Req%","Dev%","Dev%","Req%","Dev%","Des%","Dev%","Tes%","Dev%","Dev%","Dev%","Dev%","Tes%","Dev%","Tes%","Dev%","Des%","Des%","Dev%","Dev%","Tes%","Des%","Req%","Tes%","Dev%","Dev%","Tes%","Tes%","Des%","Des%","Dev%","Dev%","Des%","Req%","Dev%","Dev%","Des%","Tes%","Dev%","Des%","Req%","Des%","Dev%","Req%","Dev%","Des%","Dev%","Dev%","Dev%","Dev%","Req%","Tes%","Tes%","Dev%","Des%","Dev%","Req%","Des%","Des%","Des%","Req%","Dev%","Dev%","Req%","Des%","Des%","Dev%","Dev%","Req%","Tes%","Req%","Des%","Des%","Req%","Req%","Des%","Dev%","Dev%","Dev%","Tes%","Des%","Dev%","Des%","Tes%","Des%","Des%","Tes%","Dev%","Des%","Dev%","Des%","Des%","Tes%","Dev%","Dev%","Tes%","Req%","Des%","Dev%","Tes%","Dev%","Req%","Des%","Req%","Dev%","Tes%","Des%","Dev%","Dev%","Dev%","Des%","Dev%","Tes%","Tes%","Dev%","Dev%","Dev%","Dev%","Des%","Des%","Dev%","Des%","Tes%","Des%","Dev%","Des%","Dev%","Dev%","Req%","Req%","Dev%","Tes%","Tes%","Req%","Tes%","Tes%","Dev%","Des%","Des%","Des%","Tes%","Dev%","Tes%","Des%","Dev%","Des%","Req%","Des%","Req%","Tes%","Dev%","Des%","Des%","Tes%","Des%","Dev%","Tes%","Req%","Dev%","Dev%","Dev%","Des%","Des%","Dev%","Dev%","Req%","Req%","Des%","Des%","Dev%","Dev%","Tes%","Tes%","Dev%","Dev%","Tes%","Dev%","Req%","Req%","Dev%","Dev%","Dev%","Dev%","Des%","Dev%","Des%","Des%","Dev%","Dev%","Tes%","Tes%","Req%","Dev%","Req%","Tes%","Dev%","Tes%","Req%","Req%","Dev%","Req%","Des%","Dev%","Des%","Req%","Tes%","Des%","Dev%","Req%","Des%","Req%","Tes%","Des%","Dev%","Tes%","Dev%","Dev%","Dev%","Des%","Req%","Dev%","Des%","Req%","Dev%","Dev%","Req%","Dev%","Dev%","Des%","Dev%","Des%","Dev%","Tes%","Tes%","Req%","Dev%","Des%","Des%","Dev%","Dev%","Des%","Des%","Req%","Des%","Req%","Req%","Dev%","Dev%","Des%","Tes%","Dev%","Req%","Tes%","Dev%","Req%","Dev%","Req%","Dev%","Dev%","Des%","Req%","Req%","Dev%","Dev%","Des%","Tes%","Des%","Req%","Req%","Dev%","Tes%","Des%","Des%","Req%","Dev%","Des%","Des%","Des%","Des%","Dev%","Des%","Des%","Des%","Des%","Req%","Des%","Dev%","Des%","Tes%","Tes%","Req%","Des%","Tes%","Tes%","Des%","Des%","Des%","Dev%","Dev%","Tes%","Dev%","Dev%","Dev%","Tes%","Req%","Des%","Req%","Dev%","Dev%","Req%","Dev%","Req%","Tes%","Tes%","Dev%","Dev%","Des%","Des%","Des%","Des%","Req%","Dev%","Des%","Dev%","Req%","Req%","Des%","Dev%","Tes%","Dev%","Dev%","Des%","Tes%","Des%","Des%","Des%","Req%","Tes%","Tes%","Req%","Dev%","Tes%","Req%","Dev%","Req%","Tes%","Dev%","Dev%","Des%","Req%","Dev%","Dev%","Des%","Des%","Req%","Des%","Dev%","Des%","Dev%","Req%","Dev%","Dev%","Dev%","Dev%","Des%","Tes%","Dev%","Tes%","Tes%","Des%","Dev%","Tes%","Dev%","Dev%","Dev%","Des%","Tes%","Tes%","Tes%","Des%","Req%","Des%","Des%","Des%","Tes%","Dev%","Tes%","Dev%","Des%","Des%","Req%","Req%","Dev%","Des%","Req%","Dev%","Dev%","Dev%","Tes%","Des%","Des%","Des%","Req%","Des%","Req%","Dev%","Req%","Dev%","Tes%","Tes%","Des%","Dev%","Tes%","Des%","Dev%","Req%","Dev%","Dev%","Des%","Dev%","Des%","Tes%","Req%","Des%","Req%","Req%","Dev%","Tes%","Req%","Dev%","Des%","Des%","Des%","Des%","Tes%","Tes%","Des%","Des%","Tes%","Dev%","Tes%","Dev%","Des%","Des%","Dev%","Tes%","Tes%","Tes%","Dev%","Dev%","Dev%","Dev%","Dev%","Dev%","Des%","Req%","Tes%","Dev%","Des%","Tes%","Dev%","Des%","Req%","Des%","Req%","Req%","Tes%","Dev%","Dev%","Dev%","Tes%","Tes%","Des%","Dev%","Des%","Des%","Des%","Dev%","Dev%","Dev%","Tes%","Des%","Des%","Dev%","Des%","Req%","Dev%","Tes%","Dev%","Req%","Tes%","Des%","Tes%","Req%","Des%","Dev%","Req%","Des%","Req%","Dev%","Req%","Tes%","Dev%","Des%","Des%","Des%","Dev%","Dev%","Req%","Des%","Dev%","Des%","Dev%","Req%","Dev%","Des%","Des%","Tes%","Des%","Tes%","Des%","Des%","Des%","Tes%","Des%","Dev%","Dev%","Dev%","Dev%","Req%","Des%","Req%","Tes%","Dev%","Dev%","Des%","Dev%","Dev%","Tes%","Dev%","Req%","Dev%","Dev%","Des%","Req%","Des%","Tes%","Tes%","Tes%","Des%","Dev%","Dev%","Dev%","Dev%","Des%","Dev%","Dev%","Tes%","Dev%","Des%","Des%","Dev%","Des%","Dev%","Des%","Des%","Des%","Des%","Dev%","Dev%","Tes%","Des%","Dev%","Dev%","Tes%","Des%","Tes%","Dev%","Req%","Dev%","Dev%","Des%","Tes%","Des%","Des%","Des%","Des%","Dev%","Req%","Dev%","Dev%","Des%","Tes%","Tes%","Des%","Dev%","Dev%","Dev%","Req%","Dev%","Req%","Des%","Dev%","Req%","Des%","Dev%","Req%","Tes%","Des%","Req%","Des%","Dev%","Des%","Req%","Des%","Dev%","Tes%","Req%","Dev%","Des%","Req%","Des%","Dev%","Des%","Des%","Des%","Tes%","Dev%","Dev%","Tes%","Req%","Des%","Dev%","Des%","Dev%","Req%","Tes%","Tes%","Tes%","Dev%","Des%","Dev%","Tes%","Req%","Req%","Dev%","Dev%","Tes%","Des%","Des%","Req%","Req%","Tes%","Req%","Tes%","Dev%","Dev%","Des%","Req%","Dev%","Des%","Tes%","Dev%","Dev%","Des%","Dev%","Req%","Dev%","Dev%","Req%","Dev%","Tes%","Dev%","Dev%","Req%","Dev%","Des%","Tes%","Tes%","Req%","Req%","Dev%","Dev%","Dev%","Tes%","Des%","Des%","Tes%","Dev%","Dev%","Des%","Dev%","Des%","Tes%","Des%","Tes%","Tes%","Des%","Req%","Tes%","Tes%","Dev%","Dev%","Des%","Tes%","Req%","Tes%","Dev%","Dev%","Des%","Req%","Req%","Des%","Dev%","Dev%","Tes%","Des%","Des%","Dev%","Des%","Req%","Dev%","Tes%","Des%","Des%","Tes%","Des%","Dev%","Dev%","Dev%","Dev%","Tes%","Dev%","Tes%","Dev%","Dev%","Dev%","Req%","Req%","Tes%","Req%","Tes%","Req%","Dev%","Dev%","Des%","Req%","Des%","Des%","Des%","Req%","Des%","Req%","Des%","Des%","Tes%","Des%","Dev%","Dev%","Des%","Des%","Dev%","Tes%","Dev%","Des%","Dev%","Des%","Des%","Dev%","Dev%","Dev%","Dev%","Tes%","Tes%","Des%","Req%","Des%","Dev%","Dev%","Req%","Des%","Tes%","Tes%","Dev%","Des%","Dev%","Req%","Req%","Tes%","Des%","Des%","Req%","Tes%","Dev%","Dev%","Dev%","Dev%","Dev%","Dev%","Des%","Dev%","Dev%","Req%","Des%","Dev%","Dev%","Req%","Dev%","Dev%","Tes%","Des%","Dev%","Dev%","Des%","Tes%","Req%","Tes%","Dev%","Des%","Dev%","Dev%","Dev%","Req%","Dev%","Des%","Des%","Tes%","Dev%","Tes%","Des%","Dev%","Des%","Dev%","Des%","Dev%","Dev%","Dev%","Des%","Req%","Des%","Req%","Des%","Req%","Tes%","Dev%","Dev%","Des%","Req%","Tes%","Des%","Dev%","Des%","Req%","Req%","Des%","Des%","Req%","Dev%","Des%","Dev%","Tes%","Tes%","Des%","Dev%","Dev%","Tes%","Dev%","Dev%","Req%","Des%","Des%","Dev%","Dev%","Dev%","Des%","Tes%","Dev%","Des%","Req%","Tes%","Des%","Dev%","Tes%","Tes%","Des%","Tes%","Dev%","Tes%","Req%","Tes%","Des%","Req%","Req%","Des%","Dev%","Dev%","Req%","Dev%","Dev%","Req%","Dev%","Req%","Dev%","Des%","Req%","Des%","Tes%","Dev%","Des%","Dev%","Tes%","Des%","Des%","Des%","Des%","Dev%","Des%","Tes%","Tes%","Des%","Des%","Des%","Dev%","Req%","Des%","Dev%","Des%","Req%","Des%","Des%","Tes%","Dev%","Tes%","Tes%","Des%","Des%","Des%","Des%","Req%","Req%","Tes%","Des%","Tes%","Des%","Des%","Dev%","Des%","Des%","Tes%","Tes%","Des%","Req%","Des%","Des%","Des%","Req%","Des%","Dev%","Req%","Req%","Dev%","Des%","Dev%","Tes%","Tes%","Dev%","Dev%","Req%","Des%","Des%","Des%","Req%","Dev%","Dev%","Tes%","Tes%","Tes%","Dev%","Dev%"};
	private static String[] projectCode = {"QAC","TSM","AJW","SYK","IXC","BQE","ELT","PXR","ALZ","WAE","BSF","WJN","DEF","FER","CXQ","KTZ","WVP","FST","PIV","WSG","TOC","WHY","WJW","PRE","CNK","KYP","NYD","GAZ","KBP","FYU","WQU","DVA","VAC","EPV","SBK","XNS","OAD","DIP","PHV","EDC","TOP","GIP","IFO","HKR","XVO","DDD","GWF","LWY","JYE","HLY","XYA","BDT","EJQ","KNW","SRY","MJI","DDC","WNW","TCJ","PBW","WWM","ZAI","NJZ","FKH","PNU","ZIK","ROO","MDZ","EYD","ROE","UPR","OZU","DQV","VIB","WZR","NPS","ZTV","AZI","VGO","KSW","BYL","WRA","EVK","NGV","BJR","KEF","MJC","HSQ","RYV","FLI","SLB","UPU","LEC","SGE","BEV","OLU","HBU","TVP","VRB","BCS","FKF","AMY","WYA","OIB","GLJ","SRH","JRL","OPF","MFT","JJR","XSQ","FVQ","SWC","NKU","ZPP","MBH","FTM","WJR","NAW","XHQ","MXM","HJF","OOQ","NWX","ONN","OXU","HYC","VTK","OLN","WOD","KHL","IID","LUO","AVK","KNE","HZX","WPI","ITA","PMW","WGT","MTP","OYU","MZV","GHS","QPY","BTV","MRT","NBY","IMT","TMB","MHX","WQZ","QAF","KBK","UGW","NCA","NKH","PIY","SLK","AMF","KJI","YBJ","PED","OGF","RLV","GLZ","COT","XYR","YMO","UKS","IVX","ZKT","SFA","GAP","NQH","LZU","HJW","SSX","BEJ","TQN","IGK","IOK","RXQ","SYV","HTD","BGO","SGI","LCP","PAK","ULE","IJJ","IEM","FXV","HEU","XEN","YXU","FWP","TWW","OWG","MAQ","OCI","ZIU","GKY","KEK","BDA","WXZ","CPU","IIK","UCX","GQE","BON","FME","OGD","SPP","LQB","HBM","CRG","VRZ","QWD","QQQ","ASB","RKE","UYF","GPS","WYK","CGC","KMX","EOU","RIQ","GUB","GAL","SLC","AJT","IYG","WAK","JQQ","JOC","LFG","OGC","FXJ","DYC","CYB","QZL","IPV","DIQ","CCX","FQB","UXB","GJX","ESY","YJQ","ZIR","ETF","QWF","WLR","RPC","JTR","TCP","BYG","UZB","HZI","DQE","OOE","OML","WWH","RXX","PMK","ZUC","XIA","EMO","NBC","NZX","AGV","YXH","BAF","BJJ","SHR","IFH","OAJ","QTA","USF","BWQ","QNX","AOL","MLP","PTM","UHS","NEL","GCR","HQH","IGC","PTZ","PIL","LSB","NEN","RLL","HBL","EDU","AMD","ONT","PFH","DCH","ZWM","QKI","ZLY","DOT","RHW","JWA","LZS","XLS","JOV","TWQ","GQC","WZK","ULA","KKO","FNB","KKS","GMU","LXW","OKV","FIB","DVO","EAV","CDN","DSR","OGO","JWV","XAC","GZH","LVC","NOJ","YXR","OSE","CBR","TTM","PHQ","NXZ","PUK","ZXV","GWS","UYV","WFZ","BWS","TWF","OQW","SJU","IRD","HIU","YZQ","UIR","JDE","AFP","ZTD","OEC","PAQ","IXY","VVL","WGA","NCD","EDH","WBX","MEC","MEB","AGK","VBT","AQQ","SOO","TCQ","EFM","ZYF","ZAS","UQT","SRI","TQX","QLZ","LFN","YUW","ZLL","KXC","PGC","XYQ","TTM","ULB","JKM","YTE","RZF","RZI","GKV","GNB","BCJ","APT","MLM","WMN","JUC","XXY","SPY","KIS","XSG","DJY","AEF","IOT","UTA","AOW","AKW","OGH","DHL","IGO","QLW","GFR","QYE","BLQ","RFD","YNA","YPC","KEQ","YWM","BAO","RQN","IZB","YKS","TJT","OSZ","FXR","FKH","TLW","UVP","ARG","EXJ","GHI","QSL","AQH","HFR","STW","IYH","SHH","TQJ","TEE","NZB","URH","VNK","UBL","SIU","SWR","MZP","OIC","QZM","LXG","ZDO","SUX","MLM","ADW","HIT","ZML","DJH","OYP","HYJ","YFJ","XHM","TGH","ODU","LYJ","TDW","YCN","WVR","FRL","QHC","DRX","WHL","BPT","APB","PZO","ZXV","DWX","EKU","WUG","KWH","WLZ","LGG","AAA","YJH","PWI","NBQ","EOI","GZG","GMB","WLS","CBI","XVK","VXF","APB","XXW","FKR","QHQ","HWE","YMF","UCR","ATH","AKS","YZF","KTO","TOT","PAC","COE","PJJ","EMZ","LBU","OXY","XKQ","HVR","AUW","BYB","DLN","VEW","RIX","HGV","WTV","GAS","JKM","OQH","QYS","XGV","PGG","GHC","CBL","VSF","TCY","RLM","ZMQ","JPF","DGE","DOX","RIQ","KZQ","EDM","JJE","ILK","MKG","TEP","YTU","HNB","WFX","ENY","XJT","TFC","BMM","FJQ","LBY","VKF","JGC","FWL","IOD","NHC","WSN","IWR","TZD","HPX","WSU","SAZ","XHF","ZWJ","QGV","SOK","AZH","TRH","JGT","XWV","AFE","XJY","SXO","IOE","NGP","VNF","OUX","ZHV","WUW","SJE","XNZ","BII","TEA","JRO","OYM","QWB","HGJ","ZIU","FEE","MRO","PVT","WHH","PPP","RKL","CRG","TYL","CMM","YWL","PYG","IDP","MMM","MOR","WJC","VXH","EJM","FGK","ZVP","UCM","TED","VTZ","HSY","UNQ","FTZ","SQU","DFI","DNB","BQT","DCA","MGS","HUX","NAE","IZS","PAB","JMR","MXQ","RTJ","XXW","HGG","XWI","DOX","DNY","YEC","JSI","HNJ","AZF","YBA","GTD","WEA","JIU","HVR","BDM","LQT","FLD","FJK","ESZ","MFO","QSF","AAL","PRQ","ZHH","NVI","XJQ","XAH","ZGY","CUS","VRA","YBG","AZC","XJL","OBE","ELU","NOT","DSQ","WCP","VEC","IPG","GAL","FWU","LXD","QWK","YNP","MHX","WVR","BBA","XSA","NHA","VWA","OGD","VCS","EEC","PUI","SEB","GTY","WUJ","ZAE","LOP","ATE","TGP","BGR","OVA","AJQ","NTV","LYP","HAL","ESX","ORK","DJW","UDB","AAO","VJZ","OTS","QMM","MKP","RAK","JHR","FLT","IUF","HPX","FRD","PGO","JGA","LSY","NWW","ELD","FZF","AFV","VVE","ROG","AYS","RWS","CTE","ATS","FRZ","SYQ","WKT","DHO","VZU","DFC","RJW","CNS","JDE","IXP","OUN","ZER","UUK","CHR","WBF","ARA","QDW","IME","UHV","JNX","FIH","ZKA","KBH","UWQ","RLC","TXI","WIS","ZIU","GJG","QKN","UOE","WHM","MKJ","KPO","CKF","IAH","WEE","XNC","SQS","UBU","UGH","CLG","VRT","OVO","KOO","NNU","GOM","FBS","BKF","KYV","LDV","EKX","IFM","URO","BEC","LZA","BRX","EDT","QEF","CSC","MKT","RUG","GDQ","GGZ","KCM","CBO","HDC","GOM","AYL","JLY","QXY","MPR","QGT","ENG","NQN","ADR","KGF","DFF","HYD","YSB","BZP","UOY","AQP","FIE","IJN","WOZ","NOS","YNU","UJE","IVX","XEI","LMF","DXV","FKT","UWS","LUI","TBM","ORO","NGL","GUL","RZV","IUH","HJB","VLX","DGP","TRQ","XOF","WDR","WOR","XLN","OEK","VEH","FIL","VCI","QVZ","YTB","QBS","ATL","YGM","BWG","RTP","YOT","SAY","LUQ","MZV","ZOD","VJT","HZN","YXZ","EUX","EFY","AKO","SGE","TSK","KFZ","NIL","HBR","FPC","WXZ","LIS","WAX","ZAX","CYG","NAM","KUJ","DOU","GAK","ILZ","ABP","ZHI","IOG","GBQ","IAV","RHD","TIV","UJU","LHX","GLY","JOW","KWS","LUT","WKU","PGR","SWW","UZY","UEA","GOL","GVI","QJC","RAT","UTM","FUQ","AWN","HDW","UXT","PBU","HOT","IPA","FUY","EIR","GPQ","XIV","MXY","XXS","BJM","LPS","WTL","MNI","GFK","TZP","EHF","BGZ","GNW","TEF","YDK","HOE","GHP","NVN","QCS","JVP","MZN","AUP","YUT","AZE","EQI","SZP","NYQ","NTL","CIB","PVL","DKA","UPU","SOQ","BVE","FUD","YVM","FNU","DFZ","RAK","QIU","RAN","YWN","FGR","TKR","TIR","FFN","QFX","QAQ","DDF","LLY","KOF","QVX","GJD","COY","VDQ","ERW","EVS","AOC","LMF","RMI","QWE","ETP","XOL","HHF","KUB","FBI","SIE","VWF","DCS","REK","LNT","BMF","RGX","KRA","PIC","VVH","SFI","ZSO","QFQ","PEN","ARS","UNJ","JLJ","EAN","FGA","ESY","KGN","NWA","SIH","BPZ","XGU","RNX","FMI","PFL","LZC","NTR","PYX","YSS","RRM","UZO","NCA","BES","QQX","LCC","XLQ","SXC","CBW","XFL","VRY","GJJ","JDH","GQN","MWS","YVT","CBP","BEK","JOF","BAB","WLI","JXT","FNL","YKU","PCY","LOV","AQF","VLI","ALU","MMU","URH","STZ","REM","CDJ","SQT","YKU","PXM","FGN","YDA","OQF","WQW","MWX","IPF","GNB","ERB","OKO","GNU","NMX","JNE","VXL","RJV","ITZ","AAA","PIE","BLK","PCG","QQJ","CGJ","CUP","VPO","QVO","ICP","LDF","TOC","XZN","MDS","GMS","JUB","AVQ","QKW","EZE","NRW","QTJ","MPP","YZY","JOA","MFJ","ZKW","EFY","AHT","ITH","BNL","OGS","AKV","CFB","FGX","RCE","QCI","DAX","ZKB","VQW","SUX","UKP","TLU","OER","OYE","IZC","RCS","XFG","ZHL","WVM","YMS","VPG","HGQ","KQF","RZI","CEM","NIA","YHM","QAQ","ZAN","OCM","ZST","GHC","ICS","HFY","CQV","WDQ","SYW","PYR","TRG","TPG","AML","XCK","PBD","PAC","VWK","REM","MQB","OLH","MFE","WYM","ILP","SLB","VHN","VJN","SQZ","NTF","MUD","YTH","WNB","PEQ","OHF","HTM","VDK","OLU","DUS","AEZ","ZDC","WQF","ADW","LJU","EHS","UAH","XHL","XOH","DCC","HKZ","KYT","VBY","TYX","WZF","RGU","IIR","QKI","BWX","WQU","KDY","RBD","SGO","JRU","XNK","LSA","WMF","EXS","XVK","UNS","NPF","BHR","SIZ","CAH","PSO","SUK","KJA","QSK","PPL","OOV","NKL","SBF","YJN","ZGN","OMI","OJL","TRQ","NKF","UMG","DYL","DNC","JAI","IRI","GGP","JZE","FFQ","UYB","SMC","OFT","ZFV","WWF","CKI","AVY","JMI","FBC","MDU","KDH","OQL","BSP","SCD","UYK","HJX","BAY","VID","LLL","OWG","ERW","EOC","LPZ","QPT","ANN","QSF","PXX","LQP","HWS","LAF","HJP","XWI","ODY","DOC","EPV","MCO","IFO","KFZ","QAM","XNT","YKW","RLV","XDR","WUU","GLA","XAC","RMD","CKK","MFM","SLC","WBR","RCJ","WQI","HRM","TAW","TCF","CSC","RAY","OOH","GVA","PHR","ASM","PLA","NWW","ILS","BUZ","VAT","TYB","UKG","IBQ","QXP","PFC","KLR","KYF","MBA","AVF","XTX","XEX","LEB","YGX","PUO","BQM","FSR","EHG","YGC","HKB","ULJ","DBD","TTO","BVS","AHL","XDD","GRZ","MTN","OUK","YOC","FUL","NAA","KCP","GIM","IVW","ZZG","JPV","DNC","JSA","EXE","QYI","EKS","LCG","RVT","OJH","CMG","IOX","DRT","OFD","ZTG","WBP","VYC","SPS","BRK","EOQ","EQU","RZI","PZL","YQB","XKW","KPA","EDA","BZZ","JQR","EGA","MKC","KRF","JJF","TSR","XXN","FAF","MUD","FYI","GYS","CQO","RNU","ZSM","WQK","PXO","NRW","CSR","GLQ","PDB","SGY","CYA","EQP","XNG","LHD","KYU","MXI","NTN","SYZ","VVI","BDL","GDU","ROA","WCJ","QZK","OUJ","RWS","TVO","NFJ","CXM","MIY","UIU","XZX","AJF","NCB","CSQ","AHB","XKH","PEI","URE","IPS","AHR","FXB","KKT","UGT","YBH","XOT","CFM","PXY","VYY","MNV","JJH","ECD","MGD","ZZL","MDG","LKL","LPV","OPV","PPY","WPG","SCB","HHM","KUB","QUK","VZL","HMD","ZEP","NTC","PDO","JPZ","FPQ","DDW","JQI","MTN","VAT","UEY","DBK","LUB","COZ","ZJQ","CUA","QUA","ZAF","THG","ARW","VIW","IBD","YWR","HPD","NXP","GYI","MVU","QDC","IUW","SDA","TZD","KZO","UDU","TMO","ZAF","YXK","EVM","OXP","LHG","DMM","HPZ","IJD","ODM","PEU","XJB","DSU","LUN","SWL","YPQ","GJV","IHI","WGU","VNQ","FHO","VJN","SLF","VCH","PND","GBJ","XGO","DPN","JWI","UCQ","AIK","BSR","UST","NQU","RAD","MQF","PHV","SOR","NZU","FGM","PFT","GUS","QBI","WYV","LDR","SIL","YBC","SBA","SBL","UEB","PRC","SPU","EES","XHJ","CVO","HQX","DXD","XBK","IEK","TOQ","GWS","XGJ","JXT","ARO","PYZ","AJS","UUJ","JHD","PWM","EAE","UPM","NEI","HBB","RMR","MHP","PDE","UCB","YYK","QTR","GKJ","PTQ","WKA","RPD","VNV","MML","QZR","BVC","USI","WKT","ZHM","CPA","ZCD","IPI","WUH","UPE","LOW","OOK","GIL","WIW","XWH","EUM","JKA","WXX","GJV","KGJ","WCV","CAX","BNP","ZTJ","NVB","INW","ANZ","ICF","DTC","VTC","TBU","BZL","AOG","VMA","OIS","AIK","SBR","LHA","BND","UHK","AAW","PMN","VLI","ZYA","TCK","QGF","MWL","PLB","QOT","IHX","HTD","QNM","UGD","ZVR","MCN","VKP","WLZ","FYM","RHL","VOU","LDS","PIW","EMW","MFC","IRC","EML","BHP","AUK","DUE","FUT","DBZ","NZB","SHI","UTJ","WGT","PLD","WOA","XBO","VJA","CSR","ZML","IKD","PMZ","BIM","VMV","OKE","RIJ","CTV","RFT","LIK","ZQT","ZDL","ROW","SXX","TPH","GMI","KEP","KNV","OAU","TGI","FNZ","WTM","QKF","TJY","LMT","QOJ","GVT","LJA","PVC","VWP","QXL","OTW","NZX","CDY","XHS","IUR","UFB","GDG","MBN","XDW","ABI","ILZ","QCP","DIY","TYP","AZP","TTK","PTS","EJP","IUW","CZL","RVZ","CGR","DSS","UMK","NZB","JAU","DBN","EYO","LQJ","UUH","YTD","NQF","CKK","OHP","AIZ","XSM","KIJ","KKY","EVV","ILT","ZCB","JAL","ASB","IVE","JCE","DSQ","UKQ","JDJ","TEU","BPO","ATP","EUZ","GJV","LIW","IME","TZP","IEE","JHA","EAG","PCF","BYZ","ZKU","BTJ","QSA","LCU","UFI","EWT","BBB","OSX","OQF","KCQ","WWK","AKS","UHO","MOO","FZC","EIU","QTP","IGW","ECN","HJG","AHD","WZF","CTI","CRQ","XXH","RXM","PVD","ACQ","VXN","AII","KNA","IZL","XKQ","YVW","LJQ","LRI","NBS","PRR","XBF","EBA","ZNR","MPN","LOW","KRU","KDQ","XQR","YZG","HGW","KUQ","URM","XMU","SPM","NRF","XPW","OQG","DCE","CMX","KOR","VGD","FRC","QUG","QFN","WPP","HWL","ODG","JGH","LWD","TLT","LGO","FGW","TSV","QFL","JBT","FGI","KMH","PZR","VWI","TGV","HHA","SKJ","YJK","WKJ","OYW","VVG","DRF","MPA","ZXM","EHZ","YBQ","GNW","DPB","NCM","XGS","AJC","BBH","CVC","CWC","GRZ","KXA","PJU","NQY","XSX","HHX","UEN","EWE","MCW","SAU","SQA","BPU","OQV","VJX","SXP","OBS","VKX","TFY","AJQ","IXF","JPA","HZZ","LLO","HEB","WHZ","BNH","JHW","VLY","QQM","KDY","BHQ","YRW","VEF","HSA","PBR","FDO","MVP","LEF","UWI","DKI","SJM","DHQ","BRF","QRD","TBG","EHP","YRQ","NMT","DSM","NBT","SXS","QAR","JCF","RGW","RTL","GYH","LYT","HDA","ACW","WYW","TVP","GMZ","JSI","ZVX","RDD","JJS","APQ","SGC","JDN","PXB","RQI","KGV","SBL","IRX","CLY","ETK","ITS","RPP","CZL","HYJ","DQM","CDQ","NSX","FOL","WHS","GIL","DBH","IIG","AKV","IWU","UFJ","GNP","GAJ","JAZ","INJ","SGA","VHZ","DYR","KQK","VDE","HMK","SWH","CLI","EEB","TIL","PHW","CLB","PUA","GFB","JLD","PJV","TEV","KNY","GXD","GYG","KSY","EIV","SDS","VYQ","DHN","RFV","DUO","NON","EZG","ZDX","IWV","TLA","UVT","URK","VIH","PSB","JKI","RTJ","ODM","MTJ","OAN","WFU","GAJ","PTO","EVB","FUM","DZX","LKA","WYL","LEB","RNA","SHC","YNE","JLZ","ANQ","UKS","EYR","WXE","OFL","OOP","ELP","DJM","BJL","IGF","KFQ","SCG","HIY","QWZ","ZZT","URT","IFL","GUN","FIP","BDA","SQT","VZG","AVW","SQD","CFL","EKG","TKC","ETE","XCT","GLP","FGR","PYZ","LGB","KRE","QPS","KHI","OFF","EDK","HSI","SEN","EPY","ECT","KXH","CLS","RYY","YUN","XDZ","CHP","YRP","TCC","JEL","SNA","SQA","XSP","XEN","NMY","CGP","HIQ","ZNE"};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		runStmts(5000, true);
	
	}

	private static int getRandomNumber(){
		return (int)(Math.random() * 2000);
	}

	private static void runStmts(int iterations, boolean isRunPrep){

		if(isRunPrep){
			////////// PREP STMT
			long total = 0;
	
			runPrepStmtLike("De%","MIL");
			runPrepStmtLike("De%","MIL");
	
			for (int i = 1; i <= iterations; i++) {
				long current = System.currentTimeMillis();
				int index = getRandomNumber();
				String projectCode = RandomLetters.getRandomUpperCaseString(3);
				runPrepStmtLike(projectStatus[index],projectCode);
				long end = System.currentTimeMillis();
				System.out.println("Time taken " + i + " prep (millis): " + (end - current));
				total = total + end - current;
			}
			System.out.println("Average prep = " + total/iterations);
		}
		else{
			////////// STMT
			long total2 = 0;
			
			runStmtLike("De%","MIL");
			runStmtLike("De%","MIL");
	
			for (int i = 1; i <= iterations; i++) {
				long current = System.currentTimeMillis();
				int index = getRandomNumber();
				String projectCode = RandomLetters.getRandomUpperCaseString(3);
				runStmtLike(projectStatus[index],projectCode);
				long end = System.currentTimeMillis();
				System.out.println("Time taken " + i + " (millis): " + (end - current));
				total2 = total2 + end - current;
			}
			System.out.println("Average = " + total2/iterations);
		}		
		
	}
	
	public static void runPrepStmtLike(String projectStatus, String projectCode){
		
	    Context ctx = null;
	      Hashtable ht = new Hashtable();
	      ht.put(Context.INITIAL_CONTEXT_FACTORY,
	             "weblogic.jndi.WLInitialContextFactory");
	      ht.put(Context.PROVIDER_URL,
	             "t3://localhost:7777");

	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;

	      try {
	        ctx = new InitialContext(ht);
	        javax.sql.DataSource ds 
	          = (javax.sql.DataSource) ctx.lookup ("jdbc.ds.akapoor");
	        conn = ds.getConnection();

	       // You can now use the conn object to create 
	       //  Statements and retrieve result sets:

	        stmt = conn.prepareStatement("SELECT cas___auto___ref___b.*"
					  + "FROM (SELECT (COUNT (1) OVER ()) AS cas__auto__col__row__count,"
					  + " ROWNUM AS cas__auto__col__row__number,"
					  + " cas__auto__inclause__ref__a.*"
					  + " FROM (SELECT *"
					  + " FROM project_mt"
					  + " WHERE project_status LIKE ? AND project_cd = ?) cas__auto__inclause__ref__a) cas___auto___ref___b"
					  + " WHERE cas___auto___ref___b.cas__auto__col__row__number >= 1"
					  + " AND cas___auto___ref___b.cas__auto__col__row__number <= 100");
	        stmt.setString(1, projectStatus);
	        stmt.setString(2, projectCode);
	        stmt.execute();
	        rs = stmt.getResultSet();
	        
	        if(!rs.next())
	        	System.out.println(projectCode + " " + projectStatus);
	        else{
		        do{
		        	//System.out.println( "Project Cd:\t" + rs.getString(2));
		        	//System.out.println( "Project Status:\t" + rs.getString(6));
		        	boolean temp;
		        	temp = rs.getString(2) == null ? true : false;
		        	temp = rs.getString(6) == null ? true : false;
		        	if(temp) System.out.println("Null values");

		        }while(rs.next());
		      }
	      }
	      catch (Exception e) {
			// TODO: handle exception
	    	  e.printStackTrace();
		}finally{
	        try{rs.close();
	        stmt.close();
	        conn.close();}catch(Exception e){e.printStackTrace();}
		}
	}		


	public static void runStmtLike(String projectStatus, String projectCode){
		
	    Context ctx = null;
	      Hashtable ht = new Hashtable();
	      ht.put(Context.INITIAL_CONTEXT_FACTORY,
	             "weblogic.jndi.WLInitialContextFactory");
	      ht.put(Context.PROVIDER_URL,
	             "t3://localhost:7777");

	      Connection conn = null;
	      Statement stmt = null;
	      ResultSet rs = null;

	      try {
	        ctx = new InitialContext(ht);
	        javax.sql.DataSource ds 
	          = (javax.sql.DataSource) ctx.lookup ("jdbc.ds.akapoor");
	        conn = ds.getConnection();

	       // You can now use the conn object to create 
	       //  Statements and retrieve result sets:

	        stmt = conn.createStatement();
	        stmt.execute("SELECT cas___auto___ref___b.*"
						  + "FROM (SELECT (COUNT (1) OVER ()) AS cas__auto__col__row__count,"
						  + " ROWNUM AS cas__auto__col__row__number,"
						  + " cas__auto__inclause__ref__a.*"
						  + " FROM (SELECT *"
						  + " FROM project_mt"
						  + " WHERE project_status LIKE '"+ projectStatus +"' AND project_cd = '"+ projectCode +"') cas__auto__inclause__ref__a) cas___auto___ref___b"
						  + " WHERE cas___auto___ref___b.cas__auto__col__row__number >= 1"
						  + " AND cas___auto___ref___b.cas__auto__col__row__number <= 100");
	        rs = stmt.getResultSet();
	        
	        if(!rs.next())
	        	System.out.println(projectCode + " " + projectStatus);
	        else{
		        do{
		        	//System.out.println( "Project Cd:\t" + rs.getString(2));
		        	//System.out.println( "Project Status:\t" + rs.getString(6));
		        	boolean temp;
		        	temp = rs.getString(2) == null ? true : false;
		        	temp = rs.getString(6) == null ? true : false;
		        	if(temp) System.out.println("Null values");

		        }while(rs.next());
		      }
	      }
	      catch (Exception e) {
			// TODO: handle exception
	    	  e.printStackTrace();
		}finally{
	        try{rs.close();
	        stmt.close();
	        conn.close();}catch(Exception e){e.printStackTrace();}
		}
	}		
	
}
