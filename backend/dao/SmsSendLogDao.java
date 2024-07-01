package com.hitrsutpay.sa.backend.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hitrsutpay.sa.backend.domain.SmsSendLog;
import com.hitrsutpay.sa.backend.req.SMSLogDataRequest;
import com.hitrsutpay.sa.backend.utils.StringTool;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;



public interface SmsSendLogDao extends JpaRepository<SmsSendLog,String>{
	
	@Query("select t  from  SmsSendLog  t  where t.acqid =:acqid and t.dttm >=:startDateTime  and t.dttm <=:endDateTime order by t.dttm  ")
	public List<SmsSendLog> getSmsSendLogs(@Param("acqid")String acqid,@Param("startDateTime") Timestamp startDateTime,@Param("endDateTime") Timestamp endDateTime);
	
	default List<SmsSendLog> findByCriteria(SMSLogDataRequest request, EntityManager entityManager) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SmsSendLog> cq = cb.createQuery(SmsSendLog.class);
        Root<SmsSendLog> root = cq.from(SmsSendLog.class);

        List<Predicate> predicates = new ArrayList<>();
        if (request.getAcqId() != null) {
            predicates.add(cb.equal(root.get("acqid"), request.getAcqId().name()));
        }
        if (request.getOrderNum() != null) {
            predicates.add(cb.equal(root.get("ordernum"), request.getOrderNum()));
        }
        if (request.getMerId() != null) {
        	predicates.add(cb.equal(root.get("merid"), request.getMerId()));
        }
        if (request.getTelHash() != null) {
        	String telHash = StringTool.hashTrxTokenMD5(request.getTelHash());
        	predicates.add(cb.equal(root.get("telHash"), telHash));
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
        	Timestamp startTime = Timestamp.valueOf(request.getStartTime());
            Timestamp endTime = Timestamp.valueOf(request.getEndTime());
        	predicates.add(cb.between(root.get("dttm"), startTime, endTime));
        }
        if (request.getSmsType() != null) {
            predicates.add(cb.equal(root.get("type"), request.getSmsType().getValue()));
        }
        if (request.getResult() != null) {
            predicates.add(cb.equal(root.get("rslt"), request.getResult().getValue()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}