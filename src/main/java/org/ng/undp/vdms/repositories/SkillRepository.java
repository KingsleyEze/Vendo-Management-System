package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Skill;
import org.ng.undp.vdms.domains.Vpa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by macbook on 3/29/17.
 */
public interface SkillRepository extends CrudRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {


    @Query("SELECT q FROM Skill q WHERE  q.id IN (:skillIds)")
    public List<Skill> findAllById(@Param("skillIds") List<Long> skillIds);

    public Skill findByName(String skill);


}
