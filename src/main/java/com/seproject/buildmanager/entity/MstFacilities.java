package com.seproject.buildmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_facilities")
@Data
@NoArgsConstructor
public class MstFacilities {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "equipment_bit")
  private Integer equipmentBit;

  @Column(name = "name")
  private String name;

  @Column(name = "equipment_detail_bit")
  private Integer equipmentDetailBit;
}
