package com.seproject.buildmanager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.seproject.buildmanager.entity.MstEstimateManagement;

public interface MstEstimateManagementRepository
    extends JpaRepository<MstEstimateManagement, Integer> {
  @EntityGraph(value = "MstUser.withAllAssociations", type = EntityGraph.EntityGraphType.FETCH)
  public List<MstEstimateManagement> findAll();

  @Query(value = "SELECT e.id, " + "e.case_id, " + "e.constoruction_id, " + "e.cost_id, "
      + "e.num, " + "e.credits, " + "e.bid, " + "e.tenant_burden, " + "e.tenant_ratio_burden, "
      + "e.owner_burden, " + "e.sum, " + "e.created_at, " + "e.updated_at, "
      + "c.case_situation_status, " + "c.case_kind, " + "c.case_name, "
      + "CONCAT(cust.cust_l_name, ' ', cust.cust_f_name) AS customer_name, " + "f.floor_name, "
      + "f.building_name, " + "f.address " + "FROM mst_estimate e "
      + "JOIN mst_case c ON e.case_id = c.id "
      + "LEFT JOIN mst_floor_management f ON c.floor_id = f.id "
      + "LEFT JOIN mst_customer cust ON c.cust_id = cust.id", nativeQuery = true)
  List<MstEstimateManagement> findDisplay();


}
