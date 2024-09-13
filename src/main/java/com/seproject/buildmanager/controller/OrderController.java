package com.seproject.buildmanager.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.seproject.buildmanager.common.Constants;
import com.seproject.buildmanager.service.MstCaseService;
import com.seproject.buildmanager.service.MstCustomerService;
import com.seproject.buildmanager.service.MstFloorManagementService;
import com.seproject.buildmanager.service.MstSupplierManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("order")
public class OrderController {

  @Autowired
  private MstCaseService mstCaseService;

  @Autowired
  private MstCustomerService mstCustomerService;

  @Autowired
  private MstFloorManagementService mstFloorManagementService;

  @Autowired
  private MstSupplierManagementService mstSupplierService;

  @GetMapping("")
  public String getAllCase(Model model, HttpServletRequest request) {


    // logger.info("--- UserController.getAllUsers START ---");

    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    model.addAttribute("csrfToken", csrfToken.getToken());
    model.addAttribute("csrfHeaderName", csrfToken.getHeaderName());
    model.addAttribute("case", mstCaseService.viewCaseForm());
    model.addAttribute("customer", mstCustomerService.getAllCustomers());
    model.addAttribute("floor", mstFloorManagementService.viewFloorForm());
    model.addAttribute("statusTrue", Constants.STATUS_TRUE);
    // logger.info("--- UserController.getAllUsers END ---");
    return "order/list";
  }

  // 業者・仕入れ先選択
  @GetMapping("/select")
  public String getAllSupplier(@RequestParam(value = "caseId") String caseId, HttpSession session,
      Model model, HttpServletRequest request) {


    // logger.info("--- UserController.getAllUsers START ---");

    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    model.addAttribute("csrfToken", csrfToken.getToken());
    model.addAttribute("csrfHeaderName", csrfToken.getHeaderName());
    model.addAttribute("supplier", mstSupplierService.viewSupplierForm());
    model.addAttribute("statusTrue", Constants.STATUS_TRUE);

    String transactionToken = UUID.randomUUID().toString();
    session.setAttribute("transactionToken", transactionToken);
    model.addAttribute("transactionToken", transactionToken);

    model.addAttribute("caseId", caseId);

    // logger.info("--- UserController.getAllUsers END ---");
    return "order/supplier";
  }

  // 発注
  @GetMapping("/breakdown")
  public String getItems(@RequestParam("caseId") String caseId,
      @RequestParam("supplierId") String supplierId,
      @RequestParam("supplierName") String supplierName, HttpSession session, Model model,
      HttpServletRequest request) {


    // logger.info("--- UserController.getAllUsers START ---");

    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    model.addAttribute("csrfToken", csrfToken.getToken());
    model.addAttribute("csrfHeaderName", csrfToken.getHeaderName());
    model.addAttribute("case", mstCaseService.viewCaseForm());
    model.addAttribute("statusTrue", Constants.STATUS_TRUE);

    String transactionToken = UUID.randomUUID().toString();
    session.setAttribute("transactionToken", transactionToken);
    model.addAttribute("transactionToken", transactionToken);

    model.addAttribute("caseId", caseId);
    model.addAttribute("supplierId", supplierId);
    model.addAttribute("supplierName", supplierName);

    // logger.info("--- UserController.getAllUsers END ---");
    return "order/items";
  }
}
