package com.seproject.buildmanager.form;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MstAcceptingOrderForm {
  private String id;
  private String caseId;
  private LocalDateTime conStartDate;
  private LocalDateTime conEndDate;
  private String caseKind;
  private String caseName;
  private String customerName;
  private String floorName;
  private String address;
  private String buildingName;
  private String caseTenantName;
  private LocalDateTime caseVisitDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String caseTenantBranchName;
  private String transactionToken;

}
