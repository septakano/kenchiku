package com.seproject.buildmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.seproject.buildmanager.common.MstCodeEnums;
import com.seproject.buildmanager.service.MstCodeService;
import com.seproject.buildmanager.service.MstEstimateManagementService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("estimate")
public class EstimateManagementController {

  private static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);

  @Autowired
  private MstEstimateManagementService mstEstimateManagementService;

  @Autowired
  private MstCodeService mstCodeService;


  // Enumから状況ステータスを取得するため
  private static final int SITUATION_STATUS = MstCodeEnums.SITUATION_STATUS.getValue();

  // Enumから種別を取得するためののcode_kindの値を取得
  private static final int TASK_SUBSTANCE = MstCodeEnums.TASK_SUBSTANCE.getValue();



  public EstimateManagementController(MstEstimateManagementService mstEstimateManagementService,
      MstCodeService mstCodeService) {
    super();
    this.mstEstimateManagementService = mstEstimateManagementService;
    this.mstCodeService = mstCodeService;
  }

  @GetMapping("")
  public String getEstmateManagement(Model model, HttpServletRequest request) {
    logger.info("--- CaseController.getCase START ---");
    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    model.addAttribute("csrfToken", csrfToken.getToken());
    model.addAttribute("csrfHeaderName", csrfToken.getHeaderName());
    model.addAttribute("estimateManagementInfo",
        mstEstimateManagementService.viewMstEstimateManagementForm());
    // 状況ステータスを取得するためのリスト
    model.addAttribute("situation", mstCodeService.getCodeByKind(SITUATION_STATUS));
    // 種別を取得するためのリスト
    model.addAttribute("caseKind", mstCodeService.getCodeByKind(TASK_SUBSTANCE));



    return "estimateManagement/estimateManagement";
  }

}
