package com.seproject.buildmanager.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_estimate")
@Data
@NoArgsConstructor

public class MstEstimateManagement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id; // ID

  @Column(name = "case_id")
  private Integer caseId;

  @Column(name = "constoruction_id")
  private Integer constoructionId;

  @Column(name = "cost_id")
  private Integer costId;

  @Column(name = "num")
  private Integer num;

  @Column(name = "bid")
  private Integer bid;

  @Column(name = "tenant_burden")
  private Integer tenantBurden;

  @Column(name = "tenant_ratio_burden")
  private Integer tenantRatioBurden;

  @Column(name = "owner_burden")
  private Integer owner_burden;

  @Column(name = "sum")
  private Integer sum;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @JoinColumn(name = "case_situation_status", referencedColumnName = "case_situation_status",
      insertable = false, updatable = false)
  private Integer caseSituationStatus;

  @JoinColumn(name = "case_kind", referencedColumnName = "case_kind", insertable = false,
      updatable = false)
  private Integer caseKind;

  @JoinColumn(name = "case_name", referencedColumnName = "case_name", insertable = false,
      updatable = false)
  private String caseName;

  @JoinColumn(name = "customer_name", referencedColumnName = "customer_name", insertable = false,
      updatable = false)
  private String customerName;

  @JoinColumn(name = "floor_name", referencedColumnName = "floor_name", insertable = false,
      updatable = false)
  private String floorName;

  @JoinColumn(name = "building_name", referencedColumnName = "building_name", insertable = false,
      updatable = false)
  private String buildingName;

  @JoinColumn(name = "address", referencedColumnName = "address", insertable = false,
      updatable = false)
  private String address;



}
