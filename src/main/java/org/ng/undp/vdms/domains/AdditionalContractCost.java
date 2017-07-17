package org.ng.undp.vdms.domains;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by emmanuel on 7/2/17.
 */
@Data
@Entity
@Table(name = "additional_contract_cost")
public class AdditionalContractCost extends  Model{

    @ManyToOne
    @NotNull
    private Contract contract;

    private BigDecimal amount;

    private String reason;

}
