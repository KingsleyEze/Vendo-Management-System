package org.ng.undp.vdms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.NgoTechnical;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoGoodDto;
import org.ng.undp.vdms.dto.NgoOfficeDto;
import org.ng.undp.vdms.dto.NgoTechnicalDto;
import org.ng.undp.vdms.repositories.NgoTechnicalRepository;
import org.ng.undp.vdms.utils.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/18/2017.
 */

@Service
public class NgoTechnicalService {

    @Autowired
    private NgoTechnicalRepository repository;

    @Autowired
    private NgoGoodService goodService;
    @Autowired
    private NgoOfficeService officeService;

    public NgoTechnical save(NgoTechnical technical) {
        return repository.save(technical);
    }

    public NgoTechnical findyByUser(User user) {
        return repository.findByUser(user);
    }


    public NgoTechnical createTechnical(NgoTechnicalDto dto) {

        NgoTechnical technical = new NgoTechnical();

        Optional<User> optional = Auth.INSTANCE.getAuth();

        if (!optional.isPresent()) return null;

        User user = optional.get();

        if(dto.getId() != null)
            technical.setId(dto.getId());

        technical.setUser(user);

        repository.save(technical);

        Gson gson = new Gson();

        //Ngo Good and Services
        NgoGoodDto[] ngoGoodDto =
                gson.fromJson(dto.getNgoGoodArray(), NgoGoodDto[].class);

        if (ngoGoodDto != null) {

            for (NgoGoodDto goodDto : ngoGoodDto)
                goodService.createNgoGood(goodDto, technical, user);
        }

        //Ngo Office
        NgoOfficeDto[] ngoOfficeDto =
                gson.fromJson(dto.getNgoCompanyArray(), NgoOfficeDto[].class);

        if (ngoOfficeDto != null) {

            for (NgoOfficeDto officeDto : ngoOfficeDto)
                officeService.createNgoOffice(officeDto, technical, user);
        }


        return technical;
    }

    public NgoTechnicalDto populateTechnical(User user) {

        ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(
                    PropertyNamingStrategy.LOWER_CAMEL_CASE);

        ModelMapper modelMapper = new ModelMapper();

        NgoTechnicalDto dto = null;

        NgoTechnical technical = this.findyByUser(user);



        if (!Objects.isNull(technical)) {

            dto = modelMapper.map(technical, NgoTechnicalDto.class);

            String goodArray, companyArray = null;

            try {
                    goodArray = objectMapper.writeValueAsString(
                            technical.getNgoGood().toArray());
                    companyArray = objectMapper.writeValueAsString(
                            technical.getNgoOffices().toArray());

                    dto.setNgoGoodArray(goodArray);
                    dto.setNgoCompanyArray(companyArray);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

        return dto;
    }
}

