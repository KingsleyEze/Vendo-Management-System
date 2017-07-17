package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Skill;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.repositories.SkillRepository;
import org.ng.undp.vdms.repositories.VpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macbook on 3/29/17.
 */

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Iterable<Skill> findAll() {

        return skillRepository.findAll();
    }


    public Skill getSkill(Long id) {
        return skillRepository.findOne(id);
    }

    public Skill editSkill(Skill vpa) {
        return skillRepository.save(vpa);
    }

    public void delete(Long id) {
        skillRepository.delete(id);
    }

    public Skill save(Skill d) {
        return skillRepository.save(d);
    }


    public  List<Skill> findAllById(List<Long> skillIds){return  skillRepository.findAllById(skillIds);}

    public Skill findBySkill(String skill){
        return skillRepository.findByName(skill);
    }
}
