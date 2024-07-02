import KeepRouter from "@/components/KeepRouter.vue";
import naPageView from "@/views/404View.vue";

/*
  若CRM改用SA取對應表
  CCMaint : CRM1101
  CIAdd : CRM1201
  CIMaint : CRM1202
  StMaint : CRM1203
  SReport : CRM1204
  MIReport: CRM1205
  STFRSetting : CRM1301
  STFGSetting : CRM1302
  STFGReport : CRM1303
  STFAMReport : CRM1304
  BTFRSetting : CRM1401
  BTFReport : CRM1402
  BTFAPReport : CRM1403
  SMaint : CRM1501 
  AMaint : CRM1502 
  CMaint : CRM1503
  EMaint : CRM1504
  TMaint : CRM1505
  CNTMaint : CRM1506
  BTFTMaint : CRM1507
  STFBTMaint : CRM1508
  STFPMaint : CRM1509
  STFGMBTMaint : CRM1510
*/

export default {
  CRM1101 : {
    path: "/CCMaint",
    component: KeepRouter,
    meta: { active: 'CRM1101' , systemType : "CRM" },
    name: "CRM1101T",
    children:[
      {
        path: "",
        name : "CRM1101",
        component: () => import("@/views/CRM/CC/CC_Maint.vue"),
        meta: { keepAlive: true },//設定暫存
      },
      {
        path: "detail",
        name : "CRM1101_1",
        component: () => import("@/views/CRM/CC/CC_MaintDetail.vue"),
        checkName : ["CRM1101","CRM1101_1"] ,
        meta: { keepAlive: true },//設定暫存
        props: true,//參數會自動轉成props
      },
      {
        path: "editCC",
        name : "CRM1101_2",
        component: () => import("@/views/CRM/CC/CC_Edit.vue"),
        checkName : ["CRM1101_1"] ,
        props: true,//參數會自動轉成props
      },
    ],
    props: true,//參數會自動轉成props
  },
  CRM1201 : {
    path: "/CIAdd",
    name: "CRM1201",
    meta: { active: 'CRM1201' , systemType : "CRM" },
    component: () => import("@/views/CRM/CI/CI_Edit.vue"),
  },
  CRM1202 : {
    path: "/CIMaint",
    name: "CRM1202T",
    component: KeepRouter,
    meta: { active: 'CRM1202' , systemType : "CRM" },
    children:[
      {
        path: "",
        name : "CRM1202",
        component: () => import("@/views/CRM/CI/CI_Maint.vue"),
        meta: { keepAlive: true },//設定暫存
      },
      {
        path: "detail",
        name : "CRM1202_1",
        component: () => import("@/views/CRM/CI/CI_MaintDetail.vue"),
        checkName : ["CRM1202","CRM1202_2","CRM1202_4","CRM1202_3"] ,
        meta: { keepAlive: true },//設定暫存
        props: true,//參數會自動轉成props
      },
      {
        path: "editCI",
        name : "CRM1202_2",
        component: () => import("@/views/CRM/CI/CI_Edit.vue"),
        checkName : ["CRM1202_1"] ,
        props: true,//參數會自動轉成props
      },
      {
        path: "editStore",
        name : "CRM1202_3",
        component: () => import("@/views/CRM/CI/CI_EditStore.vue"),
        checkName : ["CRM1202_1"] ,
        props: true,//參數會自動轉成props
      },
      {
        path: "editSC",
        name : "CRM1202_4",
        component: () => import("@/views/CRM/CI/CI_EditSC.vue"),
        checkName : ["CRM1202_1"] ,
        props: true,//參數會自動轉成props
      },
    ],
  },
  CRM1203 : {
    path: "/StMaint",
    name: "CRM1203T",
    component: KeepRouter,
    meta: { active: 'CRM1203' , systemType : "CRM" },
    children:[
      {
        path: "",
        name : "CRM1203",
        component: () => import("@/views/CRM/CI/St_Maint.vue"),
        meta: { keepAlive: true },//設定暫存
      },
      {
        path: "CRM1203_1",
        name : "CRM1203_1",
        component: () => import("@/views/CRM/CI/CI_EditStore.vue"),
        checkName : ["CRM1203"] ,
        props: true,//參數會自動轉成props
      }
    ]
  },
  CRM1501 : {
    path: "/SMaint",
    name: "CRM1501",
    component: () => import("@/views/CRM/SS/S_Maint.vue"),
    meta: { active: 'CRM1501' , systemType : "CRM" },
  },
  CRM1502 : {
    path: "/AMaint",
    name: "CRM1502",
    component: () => import("@/views/CRM/SS/A_Maint.vue"),
    meta: { active: 'CRM1502' , systemType : "CRM"},
  },
  CRM1503 : {
    path: "/CMaint",
    name: "CRM1503",
    component: () => import("@/views/CRM/SS/C_Maint.vue"),
    meta: { active: 'CRM1503' , systemType : "CRM" },
  },
  CRM1504 : {
    path: "/EMaint",
    name: "CRM1504",
    component: () => import("@/views/CRM/SS/E_Maint.vue"),
    meta: { active: 'CRM1504' , systemType : "CRM" },
  },
  CRM1505 : {
    path: "/TMaint",
    name: "CRM1505",
    component: () => import("@/views/CRM/SS/T_Maint.vue"),
    meta: { active: 'CRM1505' , systemType : "CRM" },
  },
  CRM1506 : {
    path: "/CNTMaint",
    name: "CRM1506",
    component: () => import("@/views/CRM/SS/CNT_Maint.vue"),
    meta: { active: 'CRM1506' , systemType : "CRM" },
  },
  CRM1507 : {
    path: "/BTFTMaint",
    name: "CRM1507",
    component: () => import("@/views/CRM/SS/BTFT_Maint.vue"),
    meta: { active: 'CRM1507' , systemType : "CRM" },
  },
  CRM1508 : {
    path: "/STFBTMaint",
    name: "CRM1508",
    component: () => import("@/views/CRM/SS/STFBT_Maint.vue"),
    meta: { active: 'CRM1508' , systemType : "CRM" },
  },
  CRM1509 : {
    path: "/STFPMaint",
    name: "CRM1509",
    component: () => import("@/views/CRM/SS/STFP_Maint.vue"),
    meta: { active: 'CRM1509' , systemType : "CRM" },
  },
  CRM1510 : {
    path: "/STFGMBTMaint",
    name: "CRM1510",
    component: () => import("@/views/CRM/SS/STFGMBT_Maint.vue"),
    meta: { active: 'CRM1510' , systemType : "CRM" },
  },
  CRM1301 : {
    path: "/STFRSetting",
    name: "CRM1301",
    component: () => import("@/views/CRM/STF/R_Maint.vue"),
    meta: { active: 'CRM1301' , systemType : "CRM" },
  },
  CRM1302 : {
    path: "/STFGSetting",
    name: "CRM1302T",
    component: KeepRouter,
    meta: { active: 'CRM1302' , systemType : "CRM" },
    children:[
      {
        path: "",
        name : "CRM1302",
        component: () => import ("@/views/CRM/STF/G_Maint.vue"),
        //meta: { keepAlive: true },//設定暫存
        props: (route) => ({ 
          seloptionS: route.params.seloptionS ? route.params.seloptionS : ""  
          }),
      },
      {
        path: "editGroup",
        name : "CRM1302_1",
        component: () => import ("@/views/CRM/STF/G_Edit.vue"),
        checkName : ["CRM1302"] ,
        props: true,//參數會自動轉成props
        //meta: { keepAlive: true },//設定暫存
      },
    ]
  },
  CRM1303 : {
    path: "/STFGReport",
    name: "CRM1303",
    component: () => import ("@/views/CRM/STF/G_Report.vue"),
    meta: { active: 'CRM1303' , systemType : "CRM" },
  },
  CRM1304 : {
    path: "/STFAMReport",
    name: "CRM1304",
    component: () => import ("@/views/CRM/STF/A_Report.vue"),
    meta: { active: 'CRM1304' , systemType : "CRM" },
  },
  CRM1205 : {
    path: "/MIReport",
    name: "CRM1205",
    component: () => import ("@/views/CRM/R/MI_Report.vue"),
    meta: { active: 'CRM1205' , systemType : "CRM" },
  },
  CRM1401 : {
    path: "/BTFRSetting",
    name: "CRM1401",
    component: () => import ("@/views/CRM/BTF/R_Maint.vue"),
    meta: { active: 'CRM1401' , systemType : "CRM" },
  },
  CRM1402 : {
    path: "/BTFReport",
    name: "CRM1402",
    component: () => import ("@/views/CRM/BTF/R_Report.vue"),
    meta: { active: 'CRM1402' , systemType : "CRM" },
  },
  CRM1403 : {
    path: "/BTFAPReport",
    name: "CRM1403",
    component: () => import ("@/views/CRM/BTF/AP_Report.vue"),
    meta: { active: 'CRM1403' , systemType : "CRM" },
  },
  CRM1204 : {
    path: "/SReport",
    name: "CRM1204",
    component: () => import ("@/views/CRM/CI/S_Report.vue"),
    meta: { active: 'CRM1204' , systemType : "CRM" },
  },
  SA1101 : {
    path: "/SysEdit",
    name: "SA1101",
    component: () => import ("@/views/SA/Sys_Edit.vue"),
    meta: { active: 'SA1101' , systemType : "SA" },
  },
  SA1102 : {
    path: "/SriEdit",
    name: "SA1102",
    component: () => import ("@/views/SA/Sri_Edit.vue"),
    meta: { active: 'SA1102' , systemType : "SA" },
  },
  SA1401 : {
    path: "/UMaint",
    name: "SA1401_T",
    component: KeepRouter,
    meta: { active: 'SA1401' , systemType : "SA" },
    children:[
      {
        path: "",
        name : "SA1401",
        component: () => import("@/views/SA/User/U_Maint.vue"),
        meta: { keepAlive: true },//設定暫存
      },
      {
        path: "AddGroup",
        name : "SA1401_1",
        component: () => import("@/views/SA/User/U_AddGroup.vue"),
        checkName : ["SA1401"] ,
      },
    ]
  },
  SA1403 : {
    path: "/UChangePWD",
    name: "SA1403",
    component: () => import ("@/views/SA/User/U_ChangePWD.vue"),
    meta: { active: 'SA1403' , systemType : "SA"  },
  },
  SA1404 : {
    path: "/USetGroup",
    name: "SA1404_T",
    component: KeepRouter,
    meta: { active: 'SA1404' , systemType : "SA" },
    children:[
      {
        path: "",
        name : "SA1404",
        component: () => import ("@/views/SA/User/U_SetGroup.vue"),
        meta: {  },//設定暫存
      },
      {
        path: "AddGroup2",
        name : "SA1404_1",
        component: () => import("@/views/SA/User/U_AddGroup.vue"),
        checkName : ["SA1404"] ,
      },
    ]
  },
  SA1405 : {
    path: "/UAddBatch",
    name: "SA1405",
    meta: { active: 'SA1405' , systemType : "SA"  },
    component: () => import ("@/views/SA/User/U_AddBatch.vue"),
  },
  SA1201 : {
    path: "/RMaint",
    name: "SA1201T",
    component: KeepRouter,
    meta: { active: 'SA1201' , systemType : "SA"  },
    children:[
      {
        path: "",
        name : "SA1201",
        component: () => import("@/views/SA/Role/R_Maint.vue"),
      },
      {
        path: "RRight",
        name : "SA1201_1",
        component: () => import("@/views/SA/Role/R_Right.vue"),
        checkName : ["SA1201"] ,
      },
    ]
  },
  SA1301 : {
    path: "/SAdd",
    name: "SA1301",
    component: () => import("@/views/SA/Store/S_Add.vue"),
    meta: { active: 'SA1301' , systemType : "SA" },
  },
  SA1302 : {
    path: "/SEdit",
    name: "SA1302T",
    component: KeepRouter,
    meta: { active: 'SA1302' , systemType : "SA" },
    children:[
      {
        path: "",
        name: "SA1302",
        component: () => import("@/views/SA/Store/S_Edit.vue"),
      },
      {
        path: "S_Detail",
        name : "SA1302_1",
        component: () => import("@/views/SA/Store/S_Detail.vue"),
        checkName : ["SA1302"] ,
      },
    ]
  },
  SA1303 : {
    path: "/SBatch",
    name: "SA1303",
    component: () => import("@/views/SA/Store/S_Batch.vue"),
    meta: { active: 'SA1303' , systemType : "SA" },
  },
  SA1304 : {
    path: "/SSLTest",
    name: "SA1304",
    component: () => import("@/views/SA/Store/S_SSLTest.vue"),
    meta: { active: 'SA1304' , systemType : "SA" },
  },
  SA1305 : {
    path: "/SBatInsS",
    name: "SA1305",
    component: () => import("@/views/SA/Store/S_BatInsStore.vue"),
    meta: { active: 'SA1305' , systemType : "SA" },
  },
  SA1306 : {
    path: "/SApplePay",
    name: "SA1306",
    component: () => import("@/views/SA/Store/S_ApplePay.vue"),
    meta: { active: 'SA1306' , systemType : "SA" },
  },
  SA1309 : {
    path: "/SSPBVa",
    name: "SA1309",
    component: () => import("@/views/SA/Store/S_SPB_Va.vue"),
    meta: { active: 'SA1309' , systemType : "SA" },
  },
  SA1310 : {
    path: "/SStoreKey",
    name: "SA1310",
    component: () => import("@/views/SA/Store/S_StoreKey.vue"),
    meta: { active: 'SA1310' , systemType : "SA" },
  },
  SA1311 : {
    path: "/TRXTokenAuth",
    name: "SA1311",
    component: () => import("@/views/SA/Store/S_TRXTokenAuth.vue"),
    meta: { active: 'SA1311' , systemType : "SA" },
  },
  SA1312 : {
    path: "/SBathSPBUPOP",
    name: "SA1312",
    component: () => import("@/views/SA/Store/S_BathSPBUPOP.vue"),
    meta: { active: 'SA1312' , systemType : "SA" },
  },
  SA1313 : {
    path: "/SPayPageEdit",
    name: "SA1313",
    component: () => import("@/views/SA/Store/S_PayPageEdit.vue"),
    meta: { active: 'SA1313' , systemType : "SA" },
  },
  SA4001 : {
    path: "/OIosConfig",
    name: "SA4001",
    component: () => import("@/views/SA/Other/O_IosConfig.vue"),
    meta: { active: 'SA4001' , systemType : "SA" },
  },
  SA4002 : {
    path: "/OTestCard",
    name: "SA4002",
    component: () => import("@/views/SA/Other/O_TestCard.vue"),
    meta: { active: 'SA4002' , systemType : "SA" },
  },
  SA2201 : {
    path: "/OAuditLog",
    name: "SA2201",
    component: () => import("@/views/SA/Other/O_AuditLog.vue"),
    meta: { active: 'SA2201' , systemType : "SA" },
  },
  SA1801 : {
    path: "/ReturnCode",
    name: "SA1801",
    component: () => import("@/views/SA/ReturnCode_Edit.vue"),
    meta: { active: 'SA1801' , systemType : "SA" },
  },
  SA2401 : {
    path: "/RiskCouent",
    name: "SA2401",
    component: () => import("@/views/SA/Risk/R_Counect.vue"),
    meta: { active: 'SA2401' , systemType : "SA" },
  },
  SA2402 : {
    path: "/RiskDecCode",
    name: "SA2402",
    component: () => import("@/views/SA/Risk/R_DecisionCode.vue"),
    meta: { active: 'SA2402' , systemType : "SA" },
  },
  SA2403 : {
    path: "/RiskInsStrategy",
    name: "SA2403",
    component: () => import("@/views/SA/Risk/R_InsStrategy.vue"),
    meta: { active: 'SA2403' , systemType : "SA" },
  },
  SA2404 : {
    path: "/RiskStoreStrategy",
    name: "SA2404",
    component: () => import("@/views/SA/Risk/R_StoreStrategy.vue"),
    meta: { active: 'SA2404' , systemType : "SA" },
  },
  SA6002 : {
    path: "/TextQuery",
    name: "SA6002",
    component: () => import("@/views/SA/TextMsg/Te_Query.vue"),
    meta: { active: 'SA6002' , systemType : "SA" },
  },
  SA6001 : {
    path: "/TextReport",
    name: "SA6001",
    component: () => import("@/views/SA/TextMsg/Te_Report.vue"),
    meta: { active: 'SA6001' , systemType : "SA" },
  },
  SA2101 : {
    path: "/OpenStoreTranReport",
    name: "SA2101",
    component: () => import("@/views/SA/Other/O_OpenStoreTranReport.vue"),
    meta: { active: 'SA2101' , systemType : "SA" },
  },
  SA2701 : {
    path: "/OVQuery",
    name: "SA2701T",
    component: KeepRouter,
    meta: { active: 'SA2701' , systemType : "CMS"  },
    children:[
      {
        path: "",
        name : "SA2701",
        component: () => import("@/views/CMS/OV_Query.vue"),
      },
      {
        path: "OVQuery_Detail",
        name : "SA2701_1",
        checkName : ["SA2701", "SA2701_2"] ,
        component: () => import("@/views/CMS/OM_Ord_Detail.vue"),
        meta: { keepAlive: true },//設定暫存
      },
      {
        path: "OMCert_Detail",
        name : "SA2701_2",
        checkName : ["SA2701_1"] ,
        component: () => import("@/views/CMS/OM_CertDetail.vue"),
      }
    ]
  },
  SA2801 : {
    path: "/OMOQuery",
    name: "SA2801T",
    component: KeepRouter,
    meta: { active: 'SA2801' , systemType : "CMS" },
    children:[
      {
        path: "",
        name : "SA2801",
        component: () => import("@/views/CMS/OM_Ord_Query.vue"),
      },
      {
        path: "OMOQuery_Detail",
        name : "SA2801_1",
        checkName : ["SA2801", "SA2801_2"] ,
        component: () => import("@/views/CMS/OM_Ord_Detail.vue"),
        meta: { keepAlive: true },//設定暫存
      },
      {
        path: "OMCert_Detail",
        name : "SA2801_2",
        checkName : ["SA2801_1"] ,
        component: () => import("@/views/CMS/OM_CertDetail.vue"),
        
      }
    ]
  },
  SA2802 : {
    path: "/OMCertQuery",
    name: "SA2802T",
    component: KeepRouter,
    meta: { active: 'SA2802' , systemType : "CMS" },
    children:[
      {
        path: "",
        name : "SA2802",
        component: () => import("@/views/CMS/OM_CertQuery.vue"),
        meta: { keepAlive: true },//設定暫存
      },
      {
        path: "OMCert_Detail",
        name : "SA2802_1",
        checkName : ["SA2802"] ,
        component: () => import("@/views/CMS/OM_CertDetail.vue"),
      }
    ]
  },
  SA2803 : {
    path: "/OMExpCertQuery",
    name: "SA2803",
    component: () => import("@/views/CMS/OM_ExpCertQuery.vue"),
    meta: { active: 'SA2803' , systemType : "CMS" },
  },
  SA2901 : {
    path: "/PMProdQuery",
    name: "SA2901",
    component: () => import("@/views/CMS/PM_ProductQuery.vue"),
    meta: { active: 'SA2901' , systemType : "CMS" },
  },
  SA2902 : {
    path: "/PMProdCostQuery",
    name: "SA2902",
    component: () => import("@/views/CMS/PM_ProductCostQuery.vue"),
    meta: { active: 'SA2902' , systemType : "CMS" },
  },
  SA3001 : {
    path: "/MMemberQuery",
    name: "SA3001T",
    component: KeepRouter,
    meta: { active: 'SA3001' , systemType : "CMS" },
    children:[
      {
        path: "",
        name : "SA3001",
        component: () => import("@/views/CMS/MM_MemberQuery.vue"),
        meta: { keepAlive: true },//設定暫存
      },
      {
        path: "MMemberQuery_Detail",
        name : "SA3001_1",
        checkName : ["SA3001"] ,
        component: () => import("@/views/CMS/MM_MemberDetail.vue"),
      }
    ]
  }
} 

export function otherList () {
  return [{ 
    path:'/batchDesc',
    name: 'batchDesc',
    component: () => import("@/views/SA/Store/Batch_Dir.vue"),
  },
  { 
    path:'/batchSPBUPOPDesc',
    name: 'batchSPBUPOPDesc',
    component: () => import("@/views/SA/Store/BatchSPBUPOP_Dir.vue"),
  }]
}