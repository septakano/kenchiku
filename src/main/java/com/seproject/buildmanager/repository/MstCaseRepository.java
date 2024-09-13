package com.seproject.buildmanager.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.seproject.buildmanager.entity.MstCase;

public interface MstCaseRepository extends JpaRepository<MstCase, Integer> {

  @EntityGraph(value = "MstUser.withAllAssociations", type = EntityGraph.EntityGraphType.FETCH)
  public List<MstCase> findAll();

  @Query(value = "SELECT " + "ca.case_name, " + " ca.id, " + "    ca.cust_id, " + " ca.own_id,"
      + "    CONCAT(cu.cust_l_name, ' ', cu.cust_f_name) AS customer_name, " + "    ca.floor_id, "
      + "    fm.floor_name, " + "fm.address, " + "fm.building_name, " + "    ca.case_kind, "
      + "    ca.case_visit_plan_date, " + "    ca.visit_manager, " + "    ca.case_deposit, "
      + "    ca.case_contract_date, " + "    ca.case_contract_end_date, "
      + "    ca.case_tenant_l_name, "
      + "CONCAT(ca.case_tenant_l_name, ' ', ca.case_tenant_f_name) AS case_tenant_name,"
      + "    ca.case_tenant_f_name, " + "    ca.case_tenant_l_name_kana, "
      + "CONCAT(ca.case_tenant_l_name_kana, ' ', ca.case_tenant_f_name_kana) AS case_tenant_name_kana,"
      + "    ca.case_tenant_f_name_kana, " + "    ca.case_tenant_tel, " + "    ca.case_tenant_zip, "
      + "    ca.case_tenant_prefecture, " + "    ca.case_tenant_address, "
      + "    ca.case_tenant_building, " + "    ca.case_tenant_bank_name, "
      + "case_tenant_branch_name," + "    ca.case_tenant_bank_no, " + "    ca.case_tenant_meigi, "
      + "    ca.case_tenant_meigi_kana, " + "    ca.case_cylinder_no, " + "    ca.case_bikou, "
      + "    ca.status, " + "ca.case_situation_status," + "    ca.case_visit_date, "
      + "    ca.created_at, " + "    ca.updated_at " + "FROM mst_case ca "
      + "LEFT JOIN mst_customer cu ON ca.cust_id = cu.id "
      + "LEFT JOIN mst_floor_management fm ON ca.floor_id = fm.id ", nativeQuery = true)
  List<MstCase> findDisplay();

  @Query(value = "SELECT " + "ca.case_name, " + " ca.id, " + "    ca.cust_id, " + " ca.own_id,"
      + "    CONCAT(cu.cust_l_name, ' ', cu.cust_f_name) AS customer_name, " + "    ca.floor_id, "
      + "    fm.floor_name, " + "    ca.case_kind, " + "    ca.case_visit_plan_date, "
      + "    ca.visit_manager, " + "    ca.case_deposit, " + "    ca.case_contract_date, "
      + "    ca.case_contract_end_date, " + "    ca.case_tenant_l_name, "
      + "CONCAT(ca.case_tenant_l_name, ' ', ca.case_tenant_f_name) AS case_tenant_name,"
      + "    ca.case_tenant_f_name, " + "    ca.case_tenant_l_name_kana, "
      + "CONCAT(ca.case_tenant_l_name_kana, ' ', ca.case_tenant_f_name_kana) AS case_tenant_name_kana,"
      + "    ca.case_tenant_f_name_kana, " + "    ca.case_tenant_tel, " + "    ca.case_tenant_zip, "
      + "    ca.case_tenant_prefecture, " + "    ca.case_tenant_address, "
      + "    ca.case_tenant_building, " + "    ca.case_tenant_bank_name, "
      + "case_tenant_branch_name," + "    ca.case_tenant_bank_no, " + "    ca.case_tenant_meigi, "
      + "    ca.case_tenant_meigi_kana, " + "    ca.case_cylinder_no, " + "    ca.case_bikou, "
      + "    ca.status, " + "ca.case_situation_status," + "    ca.case_visit_date, "
      + "    ca.created_at, " + "    ca.updated_at " + "FROM mst_case ca "
      + "LEFT JOIN mst_customer cu ON ca.cust_id = cu.id "
      + "LEFT JOIN mst_floor_management fm ON ca.floor_id = fm.id WHERE ca.id = :id",
      nativeQuery = true)
  Optional<MstCase> findDisplayById(@Param("id") Integer id);



  @Query(value = "SELECT " + "ca.case_name, " + " ca.id, " + "    ca.cust_id, " + " ca.own_id,"
      + "    CONCAT(cu.cust_l_name, ' ', cu.cust_f_name) AS customer_name, " + "    ca.floor_id, "
      + "    fm.floor_name, " + "    ca.case_kind, " + "    ca.case_visit_plan_date, "
      + "    ca.visit_manager, " + "    ca.case_deposit, " + "    ca.case_contract_date, "
      + "    ca.case_contract_end_date, " + "    ca.case_tenant_l_name, "
      + "CONCAT(ca.case_tenant_l_name, ' ', ca.case_tenant_f_name) AS case_tenant_name,"
      + "    ca.case_tenant_f_name, " + "    ca.case_tenant_l_name_kana, "
      + "CONCAT(ca.case_tenant_l_name_kana, ' ', ca.case_tenant_f_name_kana) AS case_tenant_name_kana,"
      + "    ca.case_tenant_f_name_kana, " + "    ca.case_tenant_tel, " + "    ca.case_tenant_zip, "
      + "    ca.case_tenant_prefecture, " + "    ca.case_tenant_address, "
      + "    ca.case_tenant_building, " + "    ca.case_tenant_bank_name, "
      + "case_tenant_branch_name," + "    ca.case_tenant_bank_no, " + "    ca.case_tenant_meigi, "
      + "    ca.case_tenant_meigi_kana, " + "    ca.case_cylinder_no, " + "    ca.case_bikou, "
      + "    ca.status, " + "ca.case_situation_status," + "    ca.case_visit_date, "
      + "    ca.created_at, " + "    ca.updated_at " + "FROM mst_case ca "
      + "LEFT JOIN mst_customer cu ON ca.cust_id = cu.id "
      + "LEFT JOIN mst_floor_management fm ON ca.floor_id = fm.id "
      + "WHERE CASE WHEN :caseName =  '' THEN TRUE ELSE ca.case_name LIKE :caseName END "
      + "AND CASE WHEN :caseVisitPlanDate = '' THEN TRUE ELSE ca.case_visit_plan_date LIKE :caseVisitPlanDate END "
      + "AND CASE WHEN :caseKind = '' THEN TRUE ELSE ca.case_kind = :caseKind END "
      + "AND CASE WHEN :customerName = '' THEN TRUE ELSE  CONCAT(cu.cust_l_name, ' ', cu.cust_f_name) LIKE :customerName END "
      + "AND CASE WHEN :floorName = '' THEN TRUE ELSE fm.floor_name LIKE :floorName END "
      + "AND CASE WHEN :caseSituationStatus = '' THEN TRUE ELSE ca.case_situation_status = :caseSituationStatus END "
      + "AND CASE WHEN :caseTenantName = '' THEN TRUE ELSE CONCAT(ca.case_tenant_l_name, ' ', ca.case_tenant_f_name) LIKE :caseTenantName END "
      + "AND CASE WHEN :caseTenantNameKana = '' THEN TRUE ELSE CONCAT(ca.case_tenant_l_name_kana, ' ', ca.case_tenant_f_name_kana) LIKE :caseTenantNameKana END "
      + "AND CASE WHEN :status = '' THEN TRUE ELSE ca.status = :status END "
      + "AND CASE WHEN :createdAt = '' THEN TRUE ELSE ca.created_at LIKE :createdAt END",
      nativeQuery = true)
  public List<MstCase> search(@Param("caseName") String caseName,
      @Param("caseVisitPlanDate") String caseVisitPlanDate, @Param("caseKind") String caseKind,
      @Param("customerName") String customerName, @Param("floorName") String floorName,
      @Param("caseSituationStatus") String caseSituationStatus,
      @Param("caseTenantName") String caseTenantName,
      @Param("caseTenantNameKana") String caseTenantNameKana, @Param("status") String status,
      @Param("createdAt") String createdAt);

  @Modifying
  @Transactional
  @Query("UPDATE MstCase SET status = CASE WHEN status = 1 THEN 0 ELSE 1 END WHERE id = :id")
  void toggleStatus(@Param("id") Integer id);


}
