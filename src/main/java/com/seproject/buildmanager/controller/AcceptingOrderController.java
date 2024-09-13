package com.seproject.buildmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.seproject.buildmanager.common.Constants;
import com.seproject.buildmanager.common.MstCodeEnums;
import com.seproject.buildmanager.entity.MstCase;
import com.seproject.buildmanager.service.MstAcceptingOrderService;
import com.seproject.buildmanager.service.MstCaseService;
import com.seproject.buildmanager.service.MstCodeService;
import com.seproject.buildmanager.service.MstCustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("accepting-order")
public class AcceptingOrderController { // OrderManagement

  private static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);

  private MstCodeService mstCodeService;

  private MstAcceptingOrderService mstOrderService;

  private MstCaseService mstCaseService;

  private MstCustomerService mstCustomerService;

  // Enumから都道府県のcode_kindの値を取得
  private static final int PREFECTURES = MstCodeEnums.PREFECTURES.getValue();

  // Enumから種別を取得するためののcode_kindの値を取得
  private static final int TASK_SUBSTANCE = MstCodeEnums.TASK_SUBSTANCE.getValue();

  // Enumから状況ステータスを取得するため
  private static final int SITUATION_STATUS = MstCodeEnums.SITUATION_STATUS.getValue();

  public AcceptingOrderController(MstCodeService mstCodeService,
      MstAcceptingOrderService mstOrderService, MstCaseService mstCaseService,
      MstCustomerService mstCustomerService) {
    this.mstCodeService = mstCodeService;
    this.mstOrderService = mstOrderService;
    this.mstCaseService = mstCaseService;
    this.mstCustomerService = mstCustomerService;
  }

  @GetMapping("")
  public String getAcceptingOrder(Model model, HttpServletRequest request) {
    logger.info("--- AcceptingOrderController.getAcceptingOrder START ---");
    // CSRFトークンをモデルに追加
    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    model.addAttribute("csrfToken", csrfToken.getToken());
    model.addAttribute("csrfHeaderName", csrfToken.getHeaderName());
    model.addAttribute("orderFormList", this.mstOrderService.viewOrderForm()); // またはordersData
    model.addAttribute("caseFormList", this.mstCaseService.viewCaseForm());
    model.addAttribute("statusTrue", Constants.STATUS_TRUE);

    // 状況ステータスを取得するためのリスト
    model.addAttribute("situations", mstCodeService.getCodeByKind(SITUATION_STATUS));

    logger.info("--- AcceptingOrderController.getAcceptingOrder END ---");

    return "acceptingOrder/accepting_order";
  }

  @GetMapping("break-down")
  public String viewUchiwake(@RequestParam(value = "id") int id, Model model, HttpSession session) {
    logger.info("--- AcceptingOrderController.viewUchiwake START ---");

    /*
     * 使わない場合は、消す 
     * List<MstCustomer> customers = mstCustomerService.getAllCustomers();
     * model.addAttribute("customerList", customers);
     * 
     * // 種別を取得するためのリスト model.addAttribute("substances",
     * mstCodeService.getCodeByKind(TASK_SUBSTANCE));
     * 
     * // 状況ステータスを取得するためのリスト model.addAttribute("situations",
     * mstCodeService.getCodeByKind(SITUATION_STATUS));
     * 
     */

    MstCase mstCase = mstCaseService.findDisplayById(id);
    model.addAttribute("mstCaseForm", mstCaseService.updateCaseForm(mstCase));


    logger.info("--- AcceptingOrderController.viewUchiwake END ---");

    return "acceptingOrder/accepting_order_uchiwake";
  }


}
