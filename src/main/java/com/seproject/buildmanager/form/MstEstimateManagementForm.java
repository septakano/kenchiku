package com.seproject.buildmanager.form;

import java.time.LocalDateTime;
import lombok.Data;

@Data

public class MstEstimateManagementForm {

  private Integer id;

  private String caseId;

  private String constoructionId;

  private String costId;

  private String num;

  private String bid;

  private String tenantBurden;

  private String tenantRatioBurden;

  private String owner_burden;

  private String sum;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private String caseSituationStatus;

  private String caseKind;

  private String caseName;

  private String customerName;

  private String floorName;

  private String buildingName;

  private String address;


}
