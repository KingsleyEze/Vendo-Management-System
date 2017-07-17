package org.ng.undp.vdms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.SupplierTechnical;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierGoodDto;
import org.ng.undp.vdms.dto.SupplierOfficeDto;
import org.ng.undp.vdms.dto.SupplierTechnicalDto;
import org.ng.undp.vdms.repositories.SupplierTechnicalRepository;
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
public class SupplierTechnicalService {

    @Autowired
    private SupplierTechnicalRepository repository;

    @Autowired
    private SupplierGoodService goodService;
    @Autowired
    private SupplierOfficeService officeService;

    public SupplierTechnical save(SupplierTechnical technical) {
        return repository.save(technical);
    }

    public SupplierTechnical findyByUser(User user) {
        return repository.findByUser(user);
    }


    public SupplierTechnical createTechnical(SupplierTechnicalDto dto) {

        SupplierTechnical technical = new SupplierTechnical();

        Optional<User> optional = Auth.INSTANCE.getAuth();

        if (!optional.isPresent()) return null;

        User user = optional.get();

        if(dto.getId() != null)
            technical.setId(dto.getId());

        technical.setUser(user);

        repository.save(technical);

        Gson gson = new Gson();

        //Supplier Good and Services
        SupplierGoodDto[] supplierGoodDto =
                gson.fromJson(dto.getSupplierGoodArray(), SupplierGoodDto[].class);

        if (supplierGoodDto != null) {

            for (SupplierGoodDto goodDto : supplierGoodDto)
                goodService.createSupplierGood(goodDto, technical, user);
        }

        //Supplier Office
        SupplierOfficeDto[] supplierOfficeDto =
                gson.fromJson(dto.getSupplierCompanyArray(), SupplierOfficeDto[].class);

        if (supplierOfficeDto != null) {

            for (SupplierOfficeDto officeDto : supplierOfficeDto)
                officeService.createSupplierOffice(officeDto, technical, user);
        }


        return technical;
    }

    public SupplierTechnicalDto populateTechnical(User user) {

        ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(
                    PropertyNamingStrategy.LOWER_CAMEL_CASE);

        ModelMapper modelMapper = new ModelMapper();

        SupplierTechnicalDto dto = null;

        SupplierTechnical technical = this.findyByUser(user);



        if (!Objects.isNull(technical)) {

            dto = modelMapper.map(technical, SupplierTechnicalDto.class);

            String goodArray, companyArray = null;

            try {
                    goodArray = objectMapper.writeValueAsString(
                            technical.getSupplierGood().toArray());
                    companyArray = objectMapper.writeValueAsString(
                            technical.getSupplierOffices().toArray());

                    dto.setSupplierGoodArray(goodArray);
                    dto.setSupplierCompanyArray(companyArray);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

        return dto;
    }
}

