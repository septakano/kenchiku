package com.seproject.buildmanager.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_case")
@Data
@NoArgsConstructor


public class MstCase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id; // ID

  @Column(name = "case_name") // 案件名
  private String caseName;

  @Column(name = "cust_id") // 顧客ID
  private Integer custId;

  @Column(name = "own_id") // オーナーID
  private Integer ownId;

  @Column(name = "customer_name") // 顧客名
  private String customerName;

  @Column(name = "floor_id") // 物件ID
  private Integer floorId;

  @Column(name = "floor_name") // 物件名
  private String floorName;

  @Column(name = "case_kind") // 種別
  private Integer caseKind;

  @Column(name = "case_visit_plan_date") // 訪問予定日時
  private LocalDateTime caseVisitPlanDate;

  @Column(name = "visit_manager") // 訪問担当者
  private String visitManager;

  @Column(name = "case_deposit") // 敷金
  private Integer caseDeposit;

  @Column(name = "case_contract_date") // 賃貸契約日
  private LocalDateTime caseContractDate;

  @Column(name = "case_contract_end_date") // 賃貸契約終了日
  private LocalDateTime caseContractEndDate;

  @Column(name = "case_tenant_l_name") // 入居者性
  private String caseTenantLName;

  @Column(name = "case_tenant_f_name") // 入居者名
  private String caseTenantFName;

  @Column(name = "case_tenant_name") // 入居者名前
  private String caseTenantName;

  @Column(name = "case_tenant_l_name_kana") // 入居者性カナ
  private String caseTenantLNameKana;

  @Column(name = "case_tenant_f_name_kana") // 入居者名カナ
  private String caseTenantFNameKana;

  @Column(name = "case_tenant_name_kana") // 入居者名前カナ
  private String caseTenantNameKana;

  @Column(name = "case_tenant_tel") // 入居者電話番号
  private String caseTenantTel;

  @Column(name = "case_tenant_zip") // 転居先郵便番号
  private String caseTenantZip;

  @Column(name = "case_tenant_prefecture") // 転居先都道府県
  private Integer caseTenantPrefecture;

  @Column(name = "case_tenant_address") // 転居先住所
  private String caseTenantAddress;

  @Column(name = "case_tenant_building") // 転居先建物名
  private String caseTenantBuilding;

  @Column(name = "case_tenant_bank_name") // 入居者銀行名
  private String caseTenantBankName;

  @Column(name = "case_tenant_branch_name") // 入居者銀行支店名
  private String caseTenantBranchName;

  @Column(name = "case_tenant_bank_no") // 入居者口座番号
  private String caseTenantBankNo;

  @Column(name = "case_tenant_meigi") // 口座名義
  private String caseTenantMeigi;

  @Column(name = "case_tenant_meigi_kana") // 口座名義カナ
  private String caseTenantMeigiKana;

  @Column(name = "case_cylinder_no") // シリンダー番号
  private String caseCylinderNo;

  @Column(name = "case_bikou") // 備考
  private String caseBiko;

  @Column(name = "status") // ステータス
  private Integer status;

  @Column(name = "case_situation_status") // 状況ステータス
  private Integer caseSituationStatus;

  @Column(name = "case_visit_date") // 訪問日時
  private LocalDateTime caseVisitDate;

  @Column(name = "created_at") // 登録日時
  private LocalDateTime createdAt;

  @Column(name = "updated_at") // 更新日
  private LocalDateTime updatedAt;

  @JoinColumn(name = "address", referencedColumnName = "address", insertable = false,
      updatable = false)
  private String address;

  @JoinColumn(name = "building_name", referencedColumnName = "building_name", insertable = false,
      updatable = false)
  private String buildingName;


  // 日付フォーマット変換
  @Transient
  private String strCaseVisitPlanDate;

  @Transient
  private String strCaseContractDate;

  @Transient
  private String strCaseContractEndDate;



}
