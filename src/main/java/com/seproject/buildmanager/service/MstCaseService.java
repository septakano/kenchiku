package com.seproject.buildmanager.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import com.seproject.buildmanager.common.MstCodeEnums;
import com.seproject.buildmanager.entity.MstCase;
import com.seproject.buildmanager.entity.MstCode;
import com.seproject.buildmanager.entity.MstFloorManagement;
import com.seproject.buildmanager.form.MstCaseForm;
import com.seproject.buildmanager.repository.MstCaseRepository;
import com.seproject.buildmanager.repository.MstCodeRepository;
import com.seproject.buildmanager.repository.MstFloorManagementRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service

public class MstCaseService
    implements RegistUploadFile, MstSearchService<MstCaseForm, MstCaseForm> {
  private static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);

  private final MstCaseRepository mstcaseRepository;

  private final MstCodeRepository mstCodeRepesitory;

  private final MstFloorManagementRepository mstFloorManagementRepository;



  public MstCaseService(MstCaseRepository mstCaseRepository, MstCodeRepository mstCodeRepesitory,
      MstFloorManagementRepository mstFloorManagementRepository) {
    this.mstcaseRepository = mstCaseRepository;
    this.mstCodeRepesitory = mstCodeRepesitory;
    this.mstFloorManagementRepository = mstFloorManagementRepository;
  }

  // Enumから種別を取得するためののcode_kindの値を取得
  private static final int TASK_SUBSTANCE = MstCodeEnums.TASK_SUBSTANCE.getValue();
  // 都道府県取得
  private static final int PREFECTURES = MstCodeEnums.PREFECTURES.getValue();
  // 状況ステータス取得
  private static final int SITUATION_STATUS = MstCodeEnums.SITUATION_STATUS.getValue();

  public List<MstCase> findDisplay() {
    List<MstCase> CaseInfoList = mstcaseRepository.findDisplay();
    return CaseInfoList;
  }

  public List<MstCaseForm> viewCaseForm() {

    List<MstCase> mstCase = findDisplay();
    List<MstCaseForm> mstCaseForm = new ArrayList<MstCaseForm>();
    for (MstCase caseNum : mstCase) {
      mstCaseForm.add(updateCaseForm(caseNum));
    }
    return mstCaseForm;
  }

  // 表示用にエンティティから取ってきたデータをフォームに格納する
  public MstCaseForm updateCaseForm(MstCase mstCase) {
    MstCaseForm tmp = new MstCaseForm();

    tmp.setId(mstCase.getId());
    tmp.setCaseName(mstCase.getCaseName());
    tmp.setCustId(String.valueOf(mstCase.getCustId()));
    tmp.setOwnId(String.valueOf(mstCase.getOwnId()));
    tmp.setCustomerName(mstCase.getCustomerName());
    tmp.setFloorId(String.valueOf(mstCase.getFloorId()));
    tmp.setFloorName(mstCase.getFloorName());
    tmp.setAddress(mstCase.getAddress());
    tmp.setBuildingName(mstCase.getBuildingName());
    MstCode mstCode = mstCodeRepesitory
        .findByCodeKindAndBranchNum(TASK_SUBSTANCE, mstCase.getCaseKind()).orElse(new MstCode());

    tmp.setCaseKind(String.valueOf(mstCode.getCodeName()));
    tmp.setCaseVisitPlanDate(mstCase.getCaseVisitPlanDate());
    tmp.setVisitManager(mstCase.getVisitManager());
    tmp.setCaseDeposit(String.valueOf(mstCase.getCaseDeposit()));
    tmp.setCaseContractDate(mstCase.getCaseContractDate());
    tmp.setCaseContractEndDate(mstCase.getCaseContractEndDate());
    tmp.setCaseTenantLName(mstCase.getCaseTenantLName());
    tmp.setCaseTenantFName(mstCase.getCaseTenantFName());
    tmp.setCaseTenantName(mstCase.getCaseTenantName());
    tmp.setCaseTenantLNameKana(mstCase.getCaseTenantLNameKana());
    tmp.setCaseTenantFNameKana(mstCase.getCaseTenantFNameKana());
    tmp.setCaseTenantNameKana(mstCase.getCaseTenantNameKana());
    tmp.setCaseTenantTel(mstCase.getCaseTenantTel());
    tmp.setCaseTenantZip(mstCase.getCaseTenantZip());
    tmp.setCaseTenantPrefecture(String.valueOf(mstCase.getCaseTenantPrefecture()));
    tmp.setCaseTenantAddress(mstCase.getCaseTenantAddress());
    tmp.setCaseTenantBuilding(mstCase.getCaseTenantBuilding());
    tmp.setCaseTenantBankName(mstCase.getCaseTenantBankName());
    tmp.setCaseTenantBranchName(mstCase.getCaseTenantBranchName());
    tmp.setCaseTenantBankNo(mstCase.getCaseTenantBankNo());
    tmp.setCaseTenantMeigi(mstCase.getCaseTenantMeigi());
    tmp.setCaseTenantMeigiKana(mstCase.getCaseTenantMeigiKana());
    tmp.setCaseCylinderNo(mstCase.getCaseCylinderNo());
    tmp.setCaseBikou(mstCase.getCaseBiko());
    tmp.setStatus(String.valueOf(mstCase.getStatus()));

    MstCode mstCode2 = mstCodeRepesitory
        .findByCodeKindAndBranchNum(SITUATION_STATUS, mstCase.getCaseSituationStatus())
        .orElse(new MstCode());

    tmp.setCaseSituationStatus(String.valueOf(mstCode2.getCodeName()));
    tmp.setCaseVisitDate(mstCase.getCaseVisitDate());
    tmp.setCreatedAt(mstCase.getCreatedAt());
    tmp.setUpdatedAt(mstCase.getUpdatedAt());

    tmp.setStrCaseVisitPlanDate(mstCase.getStrCaseVisitPlanDate());
    tmp.setStrCaseContractDate(mstCase.getStrCaseContractDate());
    tmp.setStrCaseContractEndDate(mstCase.getStrCaseContractEndDate());

    return tmp;
  }

  /**
   * 新規登録用のフォームを作成し返却します。
   * 
   * @return MstCaseForm ユーザフォーム
   */
  public MstCaseForm registerCaseForm() {

    logger.info("--- MstCaseService.registerCaseForm START ---");

    MstCaseForm tmp = new MstCaseForm();

    logger.info("--- MstCaseService.registerCaseForm END ---");

    return tmp;

  }

  public MstCase findDisplayById(Integer id) {
    return mstcaseRepository.findDisplayById(id).orElse(new MstCase());
  }

  public MstCase saveCase(MstCaseForm mstCaseForm) {

    logger.info("--- MstCaseService.saveCase START ---");

    MstCase tmp = new MstCase();

    if (mstCaseForm.getId() != null) {
      tmp.setId(mstCaseForm.getId());
      tmp.setCreatedAt(mstCaseForm.getCreatedAt());
      tmp.setUpdatedAt(LocalDateTime.now());
    } else {
      tmp.setCreatedAt(LocalDateTime.now());
      tmp.setUpdatedAt(null);
    }
    tmp.setCaseName(mstCaseForm.getCaseName());
    tmp.setCustId(Integer.valueOf(mstCaseForm.getCustId()));
    tmp.setOwnId(Integer.valueOf(mstCaseForm.getOwnId()));
    tmp.setCustomerName(mstCaseForm.getCustomerName());
    tmp.setFloorId(Integer.valueOf(mstCaseForm.getFloorId()));
    tmp.setFloorName(mstCaseForm.getFloorName());

    MstCode mstCode = mstCodeRepesitory
        .findByCodeKindAndName(TASK_SUBSTANCE, mstCaseForm.getCaseKind()).orElse(new MstCode());
    tmp.setCaseKind(mstCode.getCodeBranchNum());



    tmp.setCaseVisitPlanDate(mstCaseForm.getCaseVisitPlanDate());
    tmp.setVisitManager(mstCaseForm.getVisitManager());

    try {
      tmp.setCaseDeposit(Integer.valueOf(mstCaseForm.getCaseDeposit()));
    } catch (NumberFormatException e) {
      tmp.setCaseDeposit(null);
    }

    tmp.setCaseContractDate(mstCaseForm.getCaseContractDate());
    tmp.setCaseContractEndDate(mstCaseForm.getCaseContractEndDate());
    tmp.setCaseTenantLName(mstCaseForm.getCaseTenantLName());
    tmp.setCaseTenantFName(mstCaseForm.getCaseTenantFName());
    tmp.setCaseTenantName(mstCaseForm.getCaseTenantName());
    tmp.setCaseTenantLNameKana(mstCaseForm.getCaseTenantLNameKana());
    tmp.setCaseTenantFNameKana(mstCaseForm.getCaseTenantFNameKana());
    tmp.setCaseTenantNameKana(mstCaseForm.getCaseTenantNameKana());
    tmp.setCaseTenantTel(mstCaseForm.getCaseTenantTel());
    tmp.setCaseTenantZip(mstCaseForm.getCaseTenantZip());

    MstCode mstCode2 =
        mstCodeRepesitory.findByCodeKindAndName(PREFECTURES, mstCaseForm.getCaseTenantPrefecture())
            .orElse(new MstCode());
    tmp.setCaseTenantPrefecture(mstCode2.getCodeBranchNum());



    tmp.setCaseTenantAddress(mstCaseForm.getCaseTenantAddress());
    tmp.setCaseTenantBuilding(mstCaseForm.getCaseTenantBuilding());
    tmp.setCaseTenantBankName(mstCaseForm.getCaseTenantBankName());
    tmp.setCaseTenantBranchName(mstCaseForm.getCaseTenantBranchName());
    tmp.setCaseTenantBankNo(mstCaseForm.getCaseTenantBankNo());
    tmp.setCaseTenantMeigi(mstCaseForm.getCaseTenantMeigi());
    tmp.setCaseTenantMeigiKana(mstCaseForm.getCaseTenantMeigiKana());
    tmp.setCaseCylinderNo(mstCaseForm.getCaseCylinderNo());
    tmp.setCaseBiko(mstCaseForm.getCaseBikou());

    try {
      tmp.setStatus(Integer.valueOf(mstCaseForm.getStatus()));
    } catch (NumberFormatException e) {
      tmp.setStatus(1);
    }

    tmp.setCaseVisitDate(mstCaseForm.getCaseVisitDate());



    MstCase result = mstcaseRepository.save(tmp);

    logger.info("--- MstCaseService.saveCase END ---");

    return result;


  }

  public List<MstCaseForm> selectForm() {
    List<MstFloorManagement> floor = mstFloorManagementRepository.findDisplay();
    List<MstCaseForm> mstCaseForm = new ArrayList<MstCaseForm>();
    for (MstFloorManagement floorNum : floor) {
      mstCaseForm.add(floorForm(floorNum));

    }
    return mstCaseForm;
  }

  public MstCaseForm floorForm(MstFloorManagement mstFloorManagement) {
    MstCaseForm tmp = new MstCaseForm();

    tmp.setId(mstFloorManagement.getId());
    tmp.setFloorName(mstFloorManagement.getFloorName());
    tmp.setPostCode(mstFloorManagement.getPostCode());
    tmp.setAddress(mstFloorManagement.getAddress());
    tmp.setBuildingName(mstFloorManagement.getBuildingName());

    return tmp;
  }

  public MstCase getCaseById(Integer id) {
    return mstcaseRepository.findById(id).orElse(new MstCase());
  }

  public MstCase saveStatus(Integer id) {
    MstCase mstCase = getCaseById(id);
    mstCase.setUpdatedAt(LocalDateTime.now());

    MstCase result = mstcaseRepository.save(mstCase);
    mstcaseRepository.toggleStatus(id);
    return result;
  }

  public MstCase save(Integer id, String view) {
    MstCase mstCase = getCaseById(id);
    MstCode mstCode =
        mstCodeRepesitory.findByCodeKindAndName(SITUATION_STATUS, view).orElse(new MstCode());
    mstCase.setCaseSituationStatus(mstCode.getCodeBranchNum());
    mstCase.setUpdatedAt(LocalDateTime.now());

    MstCase result = mstcaseRepository.save(mstCase);
    return result;
  }


  public void poi(HttpServletResponse response) throws IOException {
    // エクセルファイル生成
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Case Data");
    ServletOutputStream outputStream = response.getOutputStream();
    try {


      // ヘッダー行を作成
      createHeaderRow(sheet);

      // データベースからオーナーのリストを取得
      List<MstCase> cases = findDisplay();

      // エクセルファイルにデータを書き込む
      int rowNum = 1;
      for (MstCase case1 : cases) {
        Row row = sheet.createRow(rowNum++);
        writeCaseDataToRow(case1, row);
      }

      // ファイル名に2バイト文字を使えるように一工夫
      response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''"
          + URLEncoder.encode("案件データ.xlsx", StandardCharsets.UTF_8.name()));


      workbook.write(outputStream);
      outputStream.close();
      workbook.close();
    } catch (IOException e) {
      logger.error("Error generating Excel file", e);
      throw new RuntimeException("エクセルファイルの生成に失敗しました。", e);
    } finally {
      if (outputStream != null) {
        outputStream.close();
      }
      if (workbook != null) {
        workbook.close();
      }
    }
  }



  private void writeCaseDataToRow(MstCase case1, Row row) {
    row.createCell(0).setCellValue(case1.getId());
    row.createCell(1).setCellValue(case1.getCaseName());
    row.createCell(2).setCellValue(case1.getCustId());
    row.createCell(3).setCellValue(case1.getOwnId());
    row.createCell(4).setCellValue(case1.getFloorId());
    row.createCell(5).setCellValue(case1.getCaseKind());
    row.createCell(6).setCellValue(case1.getCaseVisitPlanDate().toString());
    row.createCell(7).setCellValue(case1.getVisitManager());
    row.createCell(8).setCellValue(case1.getCaseDeposit());
    row.createCell(9).setCellValue(case1.getCaseContractDate().toString());
    row.createCell(10).setCellValue(case1.getCaseContractEndDate().toString());
    row.createCell(11).setCellValue(case1.getCaseTenantLName());
    row.createCell(12).setCellValue(case1.getCaseTenantFName());
    row.createCell(13).setCellValue(case1.getCaseTenantLNameKana());
    row.createCell(14).setCellValue(case1.getCaseTenantFNameKana());
    row.createCell(15).setCellValue(case1.getCaseTenantTel());
    row.createCell(16).setCellValue(case1.getCaseTenantZip());
    row.createCell(17).setCellValue(case1.getCaseTenantPrefecture());
    row.createCell(18).setCellValue(case1.getCaseTenantAddress());
    row.createCell(19).setCellValue(case1.getCaseTenantBuilding());
    row.createCell(20).setCellValue(case1.getCaseTenantBankName());
    row.createCell(21).setCellValue(case1.getCaseTenantBankNo());
    row.createCell(22).setCellValue(case1.getCaseTenantBranchName());
    row.createCell(23).setCellValue(case1.getCaseTenantMeigi());
    row.createCell(24).setCellValue(case1.getCaseTenantMeigiKana());
    row.createCell(25).setCellValue(case1.getCaseCylinderNo());
    row.createCell(26).setCellValue(case1.getCaseBiko());
    row.createCell(27).setCellValue(case1.getStatus());
    row.createCell(28).setCellValue(case1.getCaseSituationStatus());
    row.createCell(29).setCellValue(case1.getCaseVisitDate().toString());
    row.createCell(32).setCellValue(case1.getCreatedAt().toString());
    if (case1.getUpdatedAt() != null) {
      row.createCell(33).setCellValue(case1.getUpdatedAt().toString());
    } else {
      row.createCell(33).setCellValue("NULL");
    }

  }

  private void createHeaderRow(Sheet sheet) {
    Row headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("id");
    headerRow.createCell(1).setCellValue("case_name");
    headerRow.createCell(2).setCellValue("cust_id");
    headerRow.createCell(3).setCellValue("own_id");
    headerRow.createCell(4).setCellValue("floor_id");
    headerRow.createCell(5).setCellValue("case_kind");
    headerRow.createCell(6).setCellValue("case_visit_plan_date");
    headerRow.createCell(7).setCellValue("visit_manager");
    headerRow.createCell(8).setCellValue("case_deposit");
    headerRow.createCell(9).setCellValue("case_contract_date");
    headerRow.createCell(10).setCellValue("case_contract_end_date");
    headerRow.createCell(11).setCellValue("case_tenant_l_name");
    headerRow.createCell(12).setCellValue("case_tenant_f_name");
    headerRow.createCell(13).setCellValue("case_tenant_l_name_kana");
    headerRow.createCell(14).setCellValue("case_tenant_f_name_kana");
    headerRow.createCell(15).setCellValue("case_tenant_tel");
    headerRow.createCell(16).setCellValue("case_tenant_zip");
    headerRow.createCell(17).setCellValue("case_tenant_prefecture");
    headerRow.createCell(18).setCellValue("case_tenant_address");
    headerRow.createCell(19).setCellValue("case_tenant_building");
    headerRow.createCell(20).setCellValue("case_tenant_bank_name");
    headerRow.createCell(21).setCellValue("case_tenant_branch_name");
    headerRow.createCell(22).setCellValue("case_tenant_bank_no");
    headerRow.createCell(23).setCellValue("case_tenant_meigi");
    headerRow.createCell(24).setCellValue("case_tenant_meigi_kana");
    headerRow.createCell(25).setCellValue("case_cylinder_no");
    headerRow.createCell(26).setCellValue("case_bikou");
    headerRow.createCell(27).setCellValue("status");
    headerRow.createCell(28).setCellValue("case_situation_status");
    headerRow.createCell(29).setCellValue("case_visit_date");
    headerRow.createCell(32).setCellValue("created_at");
    headerRow.createCell(33).setCellValue("updated_at");
  }

  @Override
  public void registUploadFile(List<Object> object) {
    // TODO 自動生成されたメソッド・スタブ
    if (object != null && object.size() != 0) {
      MstCase mstCase = new MstCase();
      mstCase.setId((int) Double.parseDouble(String.valueOf(object.get(1))));
      mstCase.setCaseName(String.valueOf(object.get(2)));
      mstCase.setCustId((int) Double.parseDouble(String.valueOf(object.get(3))));
      mstCase.setOwnId((int) Double.parseDouble(String.valueOf(object.get(4))));
      mstCase.setFloorId((int) Double.parseDouble(String.valueOf(object.get(5))));
      mstCase.setCaseKind((int) Double.parseDouble(String.valueOf(object.get(6))));
      String localtime = String.valueOf(object.get(7));
      localtime = localtime.replace("T", " ");
      LocalDateTime localDateTime = null;
      try {
        localDateTime =
            LocalDateTime.parse(localtime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      } catch (DateTimeParseException e) {
        localDateTime =
            LocalDateTime.parse(localtime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      }
      mstCase.setCaseVisitPlanDate(localDateTime);
      mstCase.setVisitManager(String.valueOf(object.get(8)));
      mstCase.setCaseDeposit((int) Double.parseDouble(String.valueOf(object.get(9))));
      String localtime1 = String.valueOf(object.get(10));
      localtime1 = localtime1.replace("T", " ");
      localDateTime = null;
      try {
        localDateTime =
            LocalDateTime.parse(localtime1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      } catch (DateTimeParseException e) {
        localDateTime =
            LocalDateTime.parse(localtime1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      }
      mstCase.setCaseContractDate(localDateTime);
      String localtime2 = String.valueOf(object.get(11));
      localtime2 = localtime2.replace("T", " ");
      localDateTime = null;
      try {
        localDateTime =
            LocalDateTime.parse(localtime2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      } catch (DateTimeParseException e) {
        localDateTime =
            LocalDateTime.parse(localtime2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      }
      mstCase.setCaseContractEndDate(localDateTime);
      mstCase.setCaseTenantLName(String.valueOf(object.get(12)));
      mstCase.setCaseTenantFName(String.valueOf(object.get(13)));
      mstCase.setCaseTenantLNameKana(String.valueOf(object.get(14)));
      mstCase.setCaseTenantFNameKana(String.valueOf(object.get(15)));
      mstCase.setCaseTenantTel(String.valueOf(object.get(16)));
      mstCase.setCaseTenantZip(String.valueOf(object.get(17)));
      mstCase.setCaseTenantPrefecture((int) Double.parseDouble(String.valueOf(object.get(18))));
      mstCase.setCaseTenantAddress(String.valueOf(object.get(19)));
      mstCase.setCaseTenantBuilding(String.valueOf(object.get(20)));
      mstCase.setCaseTenantBankName(String.valueOf(object.get(21)));
      mstCase.setCaseTenantBranchName(String.valueOf(object.get(22)));
      mstCase.setCaseTenantBankNo(String.valueOf(object.get(23)));
      mstCase.setCaseTenantMeigi(String.valueOf(object.get(24)));
      mstCase.setCaseTenantMeigiKana(String.valueOf(object.get(25)));
      mstCase.setCaseCylinderNo(String.valueOf(object.get(26)));
      mstCase.setCaseBiko(String.valueOf(object.get(27)));
      mstCase.setStatus((int) Double.parseDouble(String.valueOf(object.get(28))));
      mstCase.setCaseSituationStatus((int) Double.parseDouble(String.valueOf(object.get(29))));
      String localtime3 = String.valueOf(object.get(30));
      localtime3 = localtime3.replace("T", " ");
      localDateTime = null;
      try {
        localDateTime =
            LocalDateTime.parse(localtime3, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      } catch (DateTimeParseException e) {
        localDateTime =
            LocalDateTime.parse(localtime3, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      }
      mstCase.setCaseVisitDate(localDateTime);
      String localtime4 = String.valueOf(object.get(33));
      localtime4 = localtime4.replace("T", " ");
      localDateTime = null;
      try {
        localDateTime =
            LocalDateTime.parse(localtime4, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      } catch (DateTimeParseException e) {
        localDateTime =
            LocalDateTime.parse(localtime4, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      }
      mstCase.setCreatedAt(localDateTime);
      String localtime5 = String.valueOf(object.get(34));
      localtime5 = localtime5.replace("T", " ");
      localDateTime = null;
      try {
        localDateTime =
            LocalDateTime.parse(localtime5, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      } catch (DateTimeParseException e) {
        localDateTime =
            LocalDateTime.parse(localtime5, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      }
      mstCase.setCreatedAt(localDateTime);
      mstcaseRepository.save(mstCase);
    }
  }


  public List<MstCaseForm> search(MstCaseForm mstCaseForm) {
    String caseName = "";
    String caseVisitPlanDate = "";
    String caseKind = "";
    String customerName = "";
    String floorName = "";
    String caseSituationStatus = "";
    String caseTenantName = "";
    String caseTenantNameKana = "";
    String status = "";
    String createdAt = "";

    MstCaseForm case2 = mstCaseForm;

    caseName += this.nullCheck(case2.getCaseName());
    caseVisitPlanDate += this.nullCheck(case2.getStrCaseVisitPlanDate());
    if (case2.getCaseKind() == null || case2.getCaseKind().equals("")) {
      caseKind += "";
    } else {
      MstCode mstCode = mstCodeRepesitory.findByCodeKindAndName(TASK_SUBSTANCE, case2.getCaseKind())
          .orElse(new MstCode());
      case2.setCaseKind(mstCode.getCodeBranchNum().toString());
      caseKind += case2.getCaseKind();
    }

    customerName += this.nullCheck(case2.getCustomerName());
    floorName += this.nullCheck(case2.getFloorName());
    if (case2.getCaseSituationStatus() == null || case2.getCaseSituationStatus().equals("")) {
      caseSituationStatus += "";
    } else {
      MstCode mstCode =
          mstCodeRepesitory.findByCodeKindAndName(SITUATION_STATUS, case2.getCaseSituationStatus())
              .orElse(new MstCode());
      case2.setCaseSituationStatus(mstCode.getCodeBranchNum().toString());
      caseSituationStatus += case2.getCaseSituationStatus();
    }
    caseTenantName += this.nullCheck(case2.getCaseTenantName());
    caseTenantNameKana += this.nullCheck(case2.getCaseTenantNameKana());
    if (case2.getStatus() == null || case2.getStatus().equals("")) {
      status += "";
    } else {
      if (case2.getStatus().equals("active")) {
        status += "1";
      } else {
        status += "0";
      }
    }
    createdAt += this.nullCheck(case2.getCreatedAt1());

    List<MstCase> a = mstcaseRepository.search(caseName, caseVisitPlanDate, caseKind, customerName,
        floorName, caseSituationStatus, caseTenantName, caseTenantNameKana, status, createdAt);

    List<MstCaseForm> mstCaseFormList = new ArrayList<>();
    for (MstCase case3 : a) {
      mstCaseFormList.add(updateCaseForm(case3));
    }
    return mstCaseFormList;

  }


}
