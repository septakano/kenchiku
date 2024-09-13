package com.seproject.buildmanager.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import com.seproject.buildmanager.common.MstCodeEnums;
import com.seproject.buildmanager.entity.MstCode;
import com.seproject.buildmanager.entity.MstEstimateManagement;
import com.seproject.buildmanager.form.MstEstimateManagementForm;
import com.seproject.buildmanager.repository.MstCodeRepository;
import com.seproject.buildmanager.repository.MstEstimateManagementRepository;

@Service

public class MstEstimateManagementService {

  private static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);

  private final MstEstimateManagementRepository mstEstimateManagementRepository;

  private final MstCodeRepository mstCodeRepesitory;

  public MstEstimateManagementService(
      MstEstimateManagementRepository mstEstimateManagementRepository,
      MstCodeRepository mstCodeRepesitory) {
    this.mstEstimateManagementRepository = mstEstimateManagementRepository;
    this.mstCodeRepesitory = mstCodeRepesitory;

  }

  // 状況ステータス取得
  private static final int SITUATION_STATUS = MstCodeEnums.SITUATION_STATUS.getValue();

  // Enumから種別を取得するためののcode_kindの値を取得
  private static final int TASK_SUBSTANCE = MstCodeEnums.TASK_SUBSTANCE.getValue();


  public List<MstEstimateManagement> findDisplay() {
    List<MstEstimateManagement> MstEstimateManagementList =
        mstEstimateManagementRepository.findDisplay();
    return MstEstimateManagementList;
  }

  public List<MstEstimateManagementForm> viewMstEstimateManagementForm() {
    List<MstEstimateManagement> mstEstimateManagement = findDisplay();
    List<MstEstimateManagementForm> mstEstimateManagementForm =
        new ArrayList<MstEstimateManagementForm>();
    for (MstEstimateManagement estimateNum : mstEstimateManagement) {
      mstEstimateManagementForm.add(this.updateMstEstimateManagementForm(estimateNum));
    }
    return mstEstimateManagementForm;
  }

  // 表示用にエンティティから取ってきたデータをフォームに格納する
  public MstEstimateManagementForm updateMstEstimateManagementForm(
      MstEstimateManagement mstEstimateManagement) {

    MstEstimateManagementForm tmp = new MstEstimateManagementForm();

    tmp.setId(mstEstimateManagement.getId());
    tmp.setCaseId(String.valueOf(mstEstimateManagement.getCaseId()));
    tmp.setConstoructionId(String.valueOf(mstEstimateManagement.getConstoructionId()));
    tmp.setCostId(String.valueOf(mstEstimateManagement.getCostId()));
    tmp.setNum(String.valueOf(mstEstimateManagement.getNum()));
    tmp.setBid(String.valueOf(mstEstimateManagement.getBid()));
    tmp.setTenantBurden(String.valueOf(mstEstimateManagement.getTenantBurden()));
    tmp.setTenantRatioBurden(String.valueOf(mstEstimateManagement.getTenantRatioBurden()));
    tmp.setOwner_burden(String.valueOf(mstEstimateManagement.getOwner_burden()));
    tmp.setSum(String.valueOf(mstEstimateManagement.getSum()));
    tmp.setCreatedAt(mstEstimateManagement.getCreatedAt());
    tmp.setUpdatedAt(mstEstimateManagement.getUpdatedAt());

    MstCode mstCode2 = mstCodeRepesitory.findByCodeKindAndBranchNum(SITUATION_STATUS,
        mstEstimateManagement.getCaseSituationStatus()).orElse(new MstCode());

    tmp.setCaseSituationStatus(String.valueOf(mstCode2.getCodeName()));

    MstCode mstCode = mstCodeRepesitory
        .findByCodeKindAndBranchNum(TASK_SUBSTANCE, mstEstimateManagement.getCaseKind())
        .orElse(new MstCode());

    tmp.setCaseKind(String.valueOf(mstCode.getCodeName()));
    tmp.setCaseName(mstEstimateManagement.getCaseName());
    tmp.setCustomerName(mstEstimateManagement.getCustomerName());
    tmp.setFloorName(mstEstimateManagement.getFloorName());
    tmp.setBuildingName(mstEstimateManagement.getBuildingName());
    tmp.setAddress(mstEstimateManagement.getAddress());

    return tmp;
  }


}
