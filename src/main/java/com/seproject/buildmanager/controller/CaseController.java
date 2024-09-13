package com.seproject.buildmanager.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.seproject.buildmanager.common.Constants;
import com.seproject.buildmanager.common.MstCodeEnums;
import com.seproject.buildmanager.config.TransactionTokenCheck;
import com.seproject.buildmanager.entity.MstCase;
import com.seproject.buildmanager.entity.MstCustomer;
import com.seproject.buildmanager.entity.MstOwnerManagement;
import com.seproject.buildmanager.form.MstCaseForm;
import com.seproject.buildmanager.service.CommonService;
import com.seproject.buildmanager.service.MstCaseService;
import com.seproject.buildmanager.service.MstCodeService;
import com.seproject.buildmanager.service.MstCustomerService;
import com.seproject.buildmanager.service.MstOwnerManagementService;
import com.seproject.buildmanager.validation.ValidationGroups;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("case")
public class CaseController {

  private static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);

  @Autowired
  private MstCaseService mstCaseService;// サービスのインスタンス

  @Autowired
  private CommonService commonService;


  @Autowired
  private MstCustomerService mstCustomerService;

  @Autowired
  private MstOwnerManagementService mstOwnerService;

  @Autowired
  private MstCodeService mstCodeService;


  // Enumから都道府県のcode_kindの値を取得
  private static final int PREFECTURES = MstCodeEnums.PREFECTURES.getValue();

  // Enumから種別を取得するためののcode_kindの値を取得
  private static final int TASK_SUBSTANCE = MstCodeEnums.TASK_SUBSTANCE.getValue();

  // Enumから状況ステータスを取得するため
  private static final int SITUATION_STATUS = MstCodeEnums.SITUATION_STATUS.getValue();



  public CaseController(MstCaseService mstCaseService, CommonService commonService) {
    super();
    this.mstCaseService = mstCaseService;
    this.commonService = commonService;
  }

  @GetMapping("")
  public String getCase(Model model, HttpServletRequest request) {
    logger.info("--- CaseController.getCase START ---");
    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    model.addAttribute("csrfToken", csrfToken.getToken());
    model.addAttribute("csrfHeaderName", csrfToken.getHeaderName());
    model.addAttribute("caseInfo", mstCaseService.viewCaseForm());
    model.addAttribute("statusTrue", Constants.STATUS_TRUE);
    // 状況ステータスを取得するためのリスト
    model.addAttribute("situation", mstCodeService.getCodeByKind(SITUATION_STATUS));
    model.addAttribute("mstCase", mstCaseService.registerCaseForm());
    // 種別を取得するためのリスト
    model.addAttribute("caseKind", mstCodeService.getCodeByKind(TASK_SUBSTANCE));



    return "case/case";

  }

  @GetMapping("register")
  public String registerCaseForm(HttpSession session, Model model) { // 新規登録画面

    logger.info("--- CaseController.registerCaseForm START ---");

    List<MstCustomer> customer = mstCustomerService.getAllCustomers();
    model.addAttribute("customer", customer);

    List<MstOwnerManagement> owner = mstOwnerService.getAllOwner();
    model.addAttribute("owner", owner);

    String transactionToken = UUID.randomUUID().toString();
    session.setAttribute("transactionToken", transactionToken);
    model.addAttribute("transactionToken", transactionToken);

    // 都道府県のリスト
    model.addAttribute("prefectures", mstCodeService.getCodeByKind(PREFECTURES));

    // 種別を取得するためのリスト
    model.addAttribute("caseKind", mstCodeService.getCodeByKind(TASK_SUBSTANCE));

    model.addAttribute("mstCase", mstCaseService.registerCaseForm());

    logger.info("--- CaseController.registerCaseForm End ---");

    return "case/case_register";

  }

  @PostMapping("register")
  public String processRegistration(
      @ModelAttribute("mstCase") @Validated(ValidationGroups.Registration.class) MstCaseForm mstCaseForm,
      BindingResult bingingResult, HttpSession session, Model model) {// 新規登録確認画面

    logger.info("--- FloorManagementController.processRegistration START ---");

    if (bingingResult.hasErrors()) {

      List<MstCustomer> customer = mstCustomerService.getAllCustomers();
      model.addAttribute("customer", customer);
      model.addAttribute("inputCustomer", new MstCustomer());

      String transactionToken = UUID.randomUUID().toString();
      session.setAttribute("transactionToken", transactionToken);
      model.addAttribute("transactionToken", transactionToken);
      // 都道府県のリスト
      model.addAttribute("prefectures", mstCodeService.getCodeByKind(PREFECTURES));

      // 種別を取得するためのリスト
      model.addAttribute("caseKind", mstCodeService.getCodeByKind(TASK_SUBSTANCE));

      // 訪問担当者
      List<MstOwnerManagement> owner = mstOwnerService.getAllOwner();
      model.addAttribute("owner", owner);

      return "case/case_register";
    }

    if (mstCaseForm.getCustId() != null) {
      mstCaseForm.setCustomerName(mstCustomerService
          .getCustomerById(Integer.parseInt(mstCaseForm.getCustId())).getCorpName());
      model.addAttribute("mstCaseForm", mstCaseForm);
    } else {
      mstCaseForm.setCustomerName(mstCustomerService.getCustomerById(1).getCorpName());
      model.addAttribute("mstCaseForm", mstCaseForm);
    }
    String owner = mstCaseForm.getOwnId();
    int ownId = Integer.parseInt(owner);
    MstOwnerManagement own = mstOwnerService.findOwnerById(ownId);
    mstCaseForm.setVisitManager(own.getLName() + own.getFName());



    logger.info("--- FloorManagementController.processRegistration END ---");

    return "case/case_register_confirm.html";


  }

  // 保存
  @PostMapping("save")
  @TransactionTokenCheck("save")
  public String saveCase(@ModelAttribute("mstCaseForm") MstCaseForm mstcCaseForm) {

    logger.info("--- CaseController.saveCase START ---");

    mstCaseService.saveCase(mstcCaseForm);

    logger.info("--- CaseController.saveCase END ---");
    return "redirect:/case/save";
  }

  @GetMapping("save")
  public String createCompleteRegister() {
    return "case/case_register_end";
  }

  @GetMapping("select")
  public String bukkenSelect(HttpSession session, Model model) {
    model.addAttribute("bukken", mstCaseService.selectForm());

    return "case/case_bukken_select";
  }

  // 変更
  @GetMapping("update")
  public String updateCaseForm(@RequestParam(value = "id") int id, HttpSession session,
      Model model) {

    logger.info("--- CustomerController.updateCaseForm START ---");

    String transactionToken = UUID.randomUUID().toString();
    session.setAttribute("transactionToken", transactionToken);
    model.addAttribute("transactionToken", transactionToken);

    List<MstCustomer> customer = mstCustomerService.getAllCustomers();
    model.addAttribute("customer", customer);

    List<MstOwnerManagement> owner = mstOwnerService.getAllOwner();
    model.addAttribute("owner", owner);

    // 都道府県のリスト
    model.addAttribute("prefectures", mstCodeService.getCodeByKind(PREFECTURES));

    // 種別を取得するためのリスト
    model.addAttribute("caseKind", mstCodeService.getCodeByKind(TASK_SUBSTANCE));


    MstCase mstCase = mstCaseService.getCaseById(id);

    // 日付のフォーマットを変更する処理
    String formattedCaseVisitPlanDate =
        this.commonService.getDatetimeLocalFormatString(mstCase.getCaseVisitPlanDate());
    mstCase.setStrCaseVisitPlanDate(formattedCaseVisitPlanDate);

    String formattedStrCaseContractDate =
        this.commonService.getDatetimeLocalFormatString(mstCase.getCaseContractDate());
    mstCase.setStrCaseContractDate(formattedStrCaseContractDate);

    String formattedStrCaseContractEndDate =
        this.commonService.getDatetimeLocalFormatString(mstCase.getCaseContractEndDate());
    mstCase.setStrCaseContractEndDate(formattedStrCaseContractEndDate);

    model.addAttribute("mstCase", mstCaseService.updateCaseForm(mstCase));

    logger.info("--- CustomerController.updateCaseForm END ---");

    return "case/case_update";
  }

  @PostMapping("update")
  public String processUpdate(
      @ModelAttribute("mstCase") @Validated(ValidationGroups.Registration.class) MstCaseForm mstCaseForm,
      BindingResult result, HttpSession session, Model model) {

    logger.info("--- CaseController.processUpdate START ---");

    if (result.hasErrors()) {

      List<MstCustomer> customer = mstCustomerService.getAllCustomers();
      model.addAttribute("customer", customer);
      model.addAttribute("inputCustomer", new MstCustomer());

      String transactionToken = UUID.randomUUID().toString();
      session.setAttribute("transactionToken", transactionToken);
      model.addAttribute("transactionToken", transactionToken);
      // 都道府県のリスト
      model.addAttribute("prefectures", mstCodeService.getCodeByKind(PREFECTURES));

      // 種別を取得するためのリスト
      model.addAttribute("caseKind", mstCodeService.getCodeByKind(TASK_SUBSTANCE));

      // 訪問担当者
      List<MstOwnerManagement> owner = mstOwnerService.getAllOwner();
      model.addAttribute("owner", owner);

      return "case/case_register";
    }
    if (mstCaseForm.getCustId() != null) {
      mstCaseForm.setCustomerName(mstCustomerService
          .getCustomerById(Integer.parseInt(mstCaseForm.getCustId())).getCorpName());
      model.addAttribute("mstCaseForm", mstCaseForm);
    } else {
      mstCaseForm.setCustomerName(mstCustomerService.getCustomerById(1).getCorpName());
      model.addAttribute("mstCaseForm", mstCaseForm);
    }
    String owner = mstCaseForm.getOwnId();
    int ownId = Integer.parseInt(owner);
    MstOwnerManagement own = mstOwnerService.findOwnerById(ownId);
    mstCaseForm.setVisitManager(own.getLName() + own.getFName());



    return "case/case_update_confirm";
  }

  // 更新保存
  @PostMapping("save-update")
  @TransactionTokenCheck("saveUpdate")
  public String saveCaseUpdate(@ModelAttribute("mstCaseForm") MstCaseForm mstCaseForm) { // DBに登録

    logger.info("--- CaseController.saveCustomer START ---");

    mstCaseService.saveCase(mstCaseForm);

    return "redirect:/case/save-update";
  }


  @GetMapping("save-update")
  public String createCompleteUpdate() { // 変更完了画面
    return "case/case_update_end";
  }


  @GetMapping("poi")
  public void generateExcel(HttpServletResponse response) throws IOException {
    try {
      mstCaseService.poi(response);
    } catch (IOException e) {
      e.printStackTrace();
    }



  }

  @GetMapping("search")
  public String search(HttpServletRequest request, Model model,
      @ModelAttribute("mstCaseForm") MstCaseForm mstCaseForm) {
    model.addAttribute("mstCase", mstCaseService.registerCaseForm());
    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    model.addAttribute("csrfToken", csrfToken.getToken());
    model.addAttribute("csrfHeaderName", csrfToken.getHeaderName());
    model.addAttribute("statusTrue", Constants.STATUS_TRUE);
    model.addAttribute("caseInfo", mstCaseService.search(mstCaseForm));
    model.addAttribute("situation", mstCodeService.getCodeByKind(SITUATION_STATUS));
    model.addAttribute("caseKind", mstCodeService.getCodeByKind(TASK_SUBSTANCE));
    return "case/case";


  }

}
