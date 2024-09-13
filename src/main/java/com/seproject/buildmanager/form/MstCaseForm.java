package com.seproject.buildmanager.form;

import java.time.LocalDateTime;
import com.seproject.buildmanager.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MstCaseForm {

  private Integer id; // ID

  @NotBlank(message = "案件名は必須項目です",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})
  @Size(max = 25, message = "案件名は25文字以内で入力してください",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})
  private String caseName; // 案件名



  private String custId; // 顧客ID

  private String ownId; // オーナーID

  private String customerName; // 顧客名

  private String floorId; // 物件ID

  @NotBlank(message = "物件名は必須項目です",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})
  private String floorName; // 物件名

  private String caseKind; // 種別

  private LocalDateTime caseVisitPlanDate; // 訪問予定日

  private String visitManager; // 訪問担当者

  private String caseDeposit; // 敷金

  private LocalDateTime caseContractDate; // 賃貸契約日

  private LocalDateTime caseContractEndDate; // 賃貸契約終了日

  @NotBlank(message = "入居者姓は必須項目です",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})
  @Size(max = 10, message = "入居者姓は10文字以内で入力してください",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})

  private String caseTenantLName; // 入居者性

  @NotBlank(message = "入居者名は必須項目です",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})
  @Size(max = 10, message = "入居者名は10文字以内で入力してください",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})

  private String caseTenantFName; // 入居者名

  private String caseTenantName; // 入居者名前

  @NotBlank(message = "入居者性カナは必須項目です",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})
  @Size(max = 10, message = "入居者性カナは10文字以内で入力してください",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})

  private String caseTenantLNameKana; // 入居者性カナ

  @NotBlank(message = "入居者名カナは必須項目です",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})
  @Size(max = 10, message = "入居者名カナは10文字以内で入力してください",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class})

  private String caseTenantFNameKana; // 入居者名カナ

  private String caseTenantNameKana; // 入居者名前カナ

  @Pattern(regexp = "[0-9-]+",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class},
      message = "正しい携帯番号を入力してください")

  private String caseTenantTel; // 入居者電話番号

  @Pattern(regexp = "^\\d{3}-\\d{4}$|",
      groups = {ValidationGroups.Registration.class, ValidationGroups.Update.class},
      message = "正しい郵便番号を表示してください")

  private String caseTenantZip; // 転居先郵便番号

  private String caseTenantPrefecture; // 転居先都道府県

  private String caseTenantAddress; // 転居先住所

  private String caseTenantBuilding; // 転居先建物名

  private String caseTenantBankName; // 入居者銀行名

  private String caseTenantBankNo; // 入居者口座番号

  private String caseTenantMeigi; // 口座名義

  private String caseTenantMeigiKana; // 口座名義カナ

  private String caseCylinderNo; // シリンダー番号

  private String caseBikou; // 備考

  private String status; // ステータス

  private String caseSituationStatus; // 状況ステータス

  private LocalDateTime caseVisitDate; // 訪問日時

  private LocalDateTime createdAt; // 登録日時

  private LocalDateTime updatedAt; // 更新日

  private String caseTenantBranchName; // 顧客支店名

  private String transactionToken;

  // 物件管理テーブルから別ウィンドウ表示のための値取得
  private String postCode; // 郵便番号
  private String address; // 住所
  private String buildingName; // 建物名

  // 日付フォーマット変更用
  private String strCaseVisitPlanDate;

  private String strCaseContractDate;

  private String strCaseContractEndDate;

  // 検索フォーム

  private String createdAt1;



}
