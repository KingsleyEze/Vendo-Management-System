package org.ng.undp.vdms.repositories.security;

import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.domains.consultants.LanguageSkill;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 6/27/17.
 */
public interface LanguageSkillRepository extends CrudRepository<LanguageSkill, Long>, JpaSpecificationExecutor<LanguageSkill> {
}
