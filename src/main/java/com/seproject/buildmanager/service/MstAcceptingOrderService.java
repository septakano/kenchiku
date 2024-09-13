package com.seproject.buildmanager.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.seproject.buildmanager.common.MstCodeEnums;
import com.seproject.buildmanager.entity.MstAcceptingOrder;
import com.seproject.buildmanager.entity.MstCase;
import com.seproject.buildmanager.entity.MstCode;
import com.seproject.buildmanager.form.MstAcceptingOrderForm;
import com.seproject.buildmanager.form.MstCaseForm;
import com.seproject.buildmanager.repository.MstAcceptingOrderRepository;
import com.seproject.buildmanager.repository.MstCaseRepository;

@Service
public class MstAcceptingOrderService {

  private final MstAcceptingOrderRepository mstAcceptingOrderRepository;

  private final MstCaseRepository mstCaseRepository;

  private final MstCodeService mstCodeService;

  // Enumから種別を取得するためののcode_kindの値を取得
  private static final int TASK_SUBSTANCE = MstCodeEnums.TASK_SUBSTANCE.getValue();

  private MstAcceptingOrderService(MstAcceptingOrderRepository mstAcceptingOrderRepository,
      MstCaseRepository mstCaseRepository, MstCodeService mstCodeService) {
    this.mstAcceptingOrderRepository = mstAcceptingOrderRepository;
    this.mstCaseRepository = mstCaseRepository;
    this.mstCodeService = mstCodeService;
  }

  public List<MstAcceptingOrder> findDisplay() {
    List<MstAcceptingOrder> OrderInfoList = mstAcceptingOrderRepository.findAll();
    return OrderInfoList;
  }

  public List<MstCase> findCaseDisplay() {
    List<MstCase> CaseInfoList = mstCaseRepository.findAll();
    return CaseInfoList;
  }


  public List<MstAcceptingOrderForm> viewOrderForm() {
    List<MstAcceptingOrder> mstOrder = findDisplay();
    List<MstAcceptingOrderForm> mstOrderForm = new ArrayList<MstAcceptingOrderForm>();
    for (MstAcceptingOrder orderNum : mstOrder) {
      mstOrderForm.add(updateOrderForm(orderNum));
    }
    return mstOrderForm;
  }

  public List<MstCaseForm> viewCaseForm() {
    List<MstCase> mstCase = findCaseDisplay();
    List<MstCaseForm> mstCaseForm = new ArrayList<MstCaseForm>();
    for (MstCase caseNum : mstCase) {
      mstCaseForm.add(updateCaseForm(caseNum));
    }

    return mstCaseForm;

  }

  public MstAcceptingOrderForm updateOrderForm(MstAcceptingOrder mstOrder) {
    MstAcceptingOrderForm tmp = new MstAcceptingOrderForm();

    // MstCode mstCode = mstCodeService.getCodeByKindAndBranch(TASK_SUBSTANCE,
    // mstCase.getCaseKind());

    tmp.setId(String.valueOf(mstOrder.getId()));
    tmp.setCaseId(String.valueOf(mstOrder.getCaseId()));
    tmp.setConStartDate(mstOrder.getConStartDate());
    tmp.setConEndDate(mstOrder.getConEndDate());

    return tmp;
  }

  public MstCaseForm updateCaseForm(MstCase mstCase) {

    MstCaseForm tmp = new MstCaseForm();

    MstCode mstCode = mstCodeService.getCodeByKindAndBranch(TASK_SUBSTANCE, mstCase.getCaseKind());

    tmp.setCaseKind(mstCode.getCodeName());
    tmp.setCaseName(mstCase.getCaseName());
    tmp.setCustomerName(mstCase.getCustomerName());
    tmp.setFloorName(mstCase.getFloorName());
    tmp.setCaseTenantAddress(mstCase.getCaseTenantAddress());
    tmp.setCaseTenantBuilding(mstCase.getCaseTenantBuilding());
    tmp.setCaseTenantName(mstCase.getCaseTenantName());
    tmp.setCaseVisitDate(mstCase.getCaseVisitDate());
    tmp.setCreatedAt(mstCase.getCreatedAt());
    tmp.setUpdatedAt(mstCase.getUpdatedAt());
    tmp.setCaseTenantBranchName(mstCase.getCaseTenantBranchName());

    return tmp;
  }
}
