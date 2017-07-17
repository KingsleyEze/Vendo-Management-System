package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "supplier_financials")
@Data
public class SupplierFinancial extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String bankName;

    @Column
    private String bankAccountName;

    @Column
    private String bankAccountNumber;

    @Column
    private String bankAddress;

    @Column
    private String swiftOrBicAddress;

    //ANNUAL VALUE OF TOTAL INCOME FOR THE LAST 3 YEARS
    @Column
    private String totalIncomeYearOne;

    @Column
    private String totalIncomeYearTwo;

    @Column
    private String totalIncomeYearThree;

    //ANNUAL VALUE OF EXPORT SALES FOR THE LAST 3 YEARS
    @Column
    private String exportSalesYearOne;

    @Column
    private String exportSalesYearTwo;

    @Column
    private String exportSalesYearThree;
}
