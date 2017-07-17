package org.ng.undp.vdms.configs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.map.MultiValueMap;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.*;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.PathSecurity;
import org.ng.undp.vdms.domains.security.Permission;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.repositories.DocumentTypeRepository;
import org.ng.undp.vdms.repositories.SkillRepository;
import org.ng.undp.vdms.repositories.UserRepository;
import org.ng.undp.vdms.repositories.VpaRepository;
import org.ng.undp.vdms.repositories.VssRepository;
import org.ng.undp.vdms.repositories.security.PathSecurityRepository;
import org.ng.undp.vdms.repositories.security.PermissionRepository;
import org.ng.undp.vdms.repositories.security.RoleRepository;
import org.ng.undp.vdms.services.CountryService;
import org.ng.undp.vdms.services.StateService;
import org.ng.undp.vdms.services.security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by abdulhakim on 10/14/16.
 */

@Component
public class VDMSInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VpaRepository vpaRepository;

    @Autowired
    private VssRepository vssRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private PermissionRepository permissionRepository;


    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PathSecurityRepository pathSecurityRepository;
    @Autowired
    private CountryService countryService;
    @Autowired
    private StateService stateService;
    @Value("classpath:countries.json")
    private Resource file;

    @Value("classpath:nigeria_states_lg.json")
    private Resource statesFile;


    private void parseCountries() {
        try (final InputStream is = file.getInputStream()) {
            final List<Country> list = new ObjectMapper().readValue(is, new TypeReference<List<Country>>() {
            });

            for (Country c : list) {
                Country co = countryService.findByName(c.name);
                if (co == null) {
                    co = new Country();

                    co.setName(c.name);
                    co.setArea(c.area);
                    co.setCca2(c.cca2);
                    co.setCca3(c.cca3);
                    co.setTranslations(c.translations);
                    co.setCapital(c.capital);
                    co.setDemonym(c.demonym);
                    co.setLanguage(c.language);
                    co.setLatlng(c.latlng);
                    co.setBorders(c.borders);
                    co.setCurrency(c.currency);
                    co.setCallingCode(c.callingCode);
                    co.setRegion(c.region);
                    co.setRelevance(c.relevance);
                    co.setSubregion(c.subregion);

                    countryService.save(co);
                }
            }
        } catch (final IOException e) {
            throw new RuntimeException("unable to parse countries", e);
        }
    }

    private void parseStates(String country) {

        try (final InputStream is = statesFile.getInputStream()) {
            Country country1 = countryService.findByName(country);
            if (country1 != null) {

                final List<State> list = new ObjectMapper().readValue(is, new TypeReference<List<State>>() {
                });

                for (State c : list) {
                    State co = stateService.findByName(c.name);
                    if (co == null) {
                        co = new State();

                        co.setName(c.name);
                        co.setCapital(c.capital);
                        co.setLatitude(c.latitude);
                        co.setLongitude(c.longitude);
                        co.setMaxLat(c.maxLat);
                        co.setMaxLong(c.maxLong);
                        co.setMinLat(c.minLat);
                        co.setMinLong(c.minLong);
                        co.setCountry(country1);

                        stateService.save(co);
                    }

                }
            }
        } catch (final IOException e) {
            throw new RuntimeException("unable to parse nigerian states ", e);
        }
    }


    //@Autowired
    //private BCryptPasswordEncoder bCryptEncoder;
/*
    @Autowired
    public VDMSInitializer(UserRepository userRepository, VpaRepository vpaRepository, VssRepository vssRepository) {

        this.userRepository = userRepository;
        this.vpaRepository = vpaRepository;
        this.vssRepository = vssRepository;

    }
*/
    public void createDefaultVPAs() {


    /*
    *
    *





    *
    * */


        MultiValueMap vpas = new MultiValueMap();


        vpas.put(UserType.CONSULTANT, "Democratic Governance");
        vpas.put(UserType.CONSULTANT, "Private Sector Development");

        /*





- ICT as programme area with specialisation:

     ....database management

     ....software development

         */
        vpas.put(UserType.CONSULTANT, "Planning and Budget Management");
        vpas.put(UserType.CONSULTANT, "Policy Development");
        vpas.put(UserType.CONSULTANT, "Programme Development");
        vpas.put(UserType.CONSULTANT, "Programme Analysis");
        vpas.put(UserType.CONSULTANT, "Small Enterprise Management");
        vpas.put(UserType.CONSULTANT, "Cooperatives Governance");
        vpas.put(UserType.CONSULTANT, "Economic Management & Planning");
        vpas.put(UserType.CONSULTANT, "Logistic Management");
        vpas.put(UserType.CONSULTANT, "ICT");
        vpas.put(UserType.CONSULTANT, " Supply Procurement");
        vpas.put(UserType.CONSULTANT, "Finance Administration");
        vpas.put(UserType.CONSULTANT, " Entrepreneurship development / SME");
        vpas.put(UserType.NGO, "Democratic Governance");
        vpas.put(UserType.NGO, "Private Sector Development");
        vpas.put(UserType.CONSULTANT, "Peace and  Governance");
        vpas.put(UserType.NGO, "Economic Governance");
        vpas.put(UserType.CONSULTANT, "Environmental Governance and Climate Change");
        vpas.put(UserType.NGO, "Environmental Governance and Climate Change");

        vpas.put(UserType.CONSULTANT, "Conflict Prevention and Recovery");

        vpas.put(UserType.CONSULTANT, "Project Management/Implementation");

        vpas.put(UserType.CONSULTANT, "Drugs, Crime and Trafficking");
        vpas.put(UserType.NGO, "Conflict Prevention and Recovery");
        vpas.put(UserType.NGO, "Governance of HIV/AIDS");
        vpas.put(UserType.CONSULTANT, "Governance of HIV/AIDS");
        vpas.put(UserType.CONSULTANT, "Population and Development");


        vpas.put(UserType.NGO, "Drugs, Crime and Trafficking");
        vpas.put(UserType.SUPPLIER, "Goods");
        vpas.put(UserType.SUPPLIER, "Services");
        vpas.put(UserType.SUPPLIER, "Goods and Services");


        vpas.put(UserType.CONSULTANT, "All of the Above");

        Set entrySet = vpas.entrySet();
        Iterator it = entrySet.iterator();

        List list;
        while (it.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) it.next();
            list = (List) vpas.get(mapEntry.getKey());
            for (int j = 0; j < list.size(); j++) {
                Vpa v = new Vpa();
                v.setName(list.get(j).toString().trim());
                v.setDescription(list.get(j).toString().trim());
                v.setUsertype((UserType) mapEntry.getKey());

                if (
                        vpaRepository.findAllByNameAndUsertype(v.getName(), v.getUsertype()).size() == 0) {
                    vpaRepository.save(v);

                }
                //  System.out.println("\t" + mapEntry.getKey() + "\t  " + list.get(j));
            }
        }
    }


    public void createDefaultVss() {


        Vpa vpaDG = vpaRepository.findByNameAndUsertype("Democratic Governance".trim(), UserType.CONSULTANT);

        vpaDG.setUsertype(UserType.CONSULTANT);
        if (vpaDG != null) {


            Vss vss1 = new Vss();
            vss1.setName("Election".trim());
            vss1.setUsertype(UserType.CONSULTANT);
            vss1.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss1.getUsertype(), vss1.getVpa()).size() == 0) {
                vssRepository.save(vss1);
            }
            Vss vss2 = new Vss();
            vss2.setName("Local Governance".trim());
            vss2.setUsertype(UserType.CONSULTANT);
            vss2.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss2.getUsertype(), vss2.getVpa()).size() == 0) {
                vssRepository.save(vss2);

            }
            Vss vss3 = new Vss();
            vss3.setName("Civil Society Development".trim());
            vss3.setUsertype(UserType.CONSULTANT);
            vss3.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss3.getUsertype(), vss3.getVpa()).size() == 0) {
                vssRepository.save(vss3);
            }
            Vss vss4 = new Vss();
            vss4.setName("Anti-Corruption".trim());
            vss4.setUsertype(UserType.CONSULTANT);
            vss4.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss4.getUsertype(), vss4.getVpa()).size() == 0) {
                vssRepository.save(vss4);
            }
            Vss vss5 = new Vss();
            vss5.setName("e-Governance".trim());
            vss5.setUsertype(UserType.CONSULTANT);
            vss5.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss5.getUsertype(), vss5.getVpa()).size() == 0) {
                vssRepository.save(vss5);
            }


        }

        vpaDG = vpaRepository.findByNameAndUsertype("Conflict Prevention and Recovery".trim(), UserType.NGO);

        vpaDG.setUsertype(UserType.NGO);
        if (vpaDG != null) {


            Vss vss1 = new Vss();
            vss1.setName("Conflict Prevention".trim());
            vss1.setUsertype(UserType.NGO);
            vss1.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss1.getUsertype(), vss1.getVpa()).size() == 0) {

                vssRepository.save(vss1);
            }

            Vss vss2 = new Vss();
            vss2.setName("Mediation".trim());
            vss2.setUsertype(UserType.NGO);
            vss2.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss2.getUsertype(), vss2.getVpa()).size() == 0) {

                vssRepository.save(vss2);

            }
            Vss vss3 = new Vss();
            vss3.setName("Post Conflict Rehabilitation".trim());
            vss3.setUsertype(UserType.NGO);
            vss3.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss3.getUsertype(), vss3.getVpa()).size() == 0) {

                vssRepository.save(vss3);
            }
            Vss vss4 = new Vss();
            vss4.setName("Justice & Law".trim());
            vss4.setUsertype(UserType.NGO);
            vss4.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss4.getUsertype(), vss4.getVpa()).size() == 0) {

                vssRepository.save(vss4);
            }
            Vss vss5 = new Vss();
            vss5.setName("Leadership Development".trim());
            vss5.setUsertype(UserType.NGO);
            vss5.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss5.getUsertype(), vss5.getVpa()).size() == 0) {

                vssRepository.save(vss5);
            }
        }
        vpaDG = vpaRepository.findByNameAndUsertype("Services".trim(), UserType.SUPPLIER);

        vpaDG.setUsertype(UserType.SUPPLIER);
        if (vpaDG != null) {


            Vss vss1 = new Vss();
            vss1.setName("Training and Facilitation".trim());
            vss1.setUsertype(UserType.SUPPLIER);
            vss1.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss1.getUsertype(), vss1.getVpa()).size() == 0) {

                vssRepository.save(vss1);
            }
            Vss vss2 = new Vss();
            vss2.setName("Research & Assessment".trim());
            vss2.setUsertype(UserType.SUPPLIER);
            vss2.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss2.getUsertype(), vss2.getVpa()).size() == 0) {

                vssRepository.save(vss2);
            }

            Vss vss3 = new Vss();
            vss3.setName("Hotel Facilities and Services".trim());
            vss3.setUsertype(UserType.SUPPLIER);
            vss3.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss3.getUsertype(), vss3.getVpa()).size() == 0) {

                vssRepository.save(vss3);
            }
            Vss vss4 = new Vss();
            vss4.setName("Catering".trim());
            vss4.setUsertype(UserType.SUPPLIER);
            vss4.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss4.getUsertype(), vss4.getVpa()).size() == 0) {

                vssRepository.save(vss4);
            }
            Vss vss5 = new Vss();
            vss5.setName("Engineering & Construction Services".trim());
            vss5.setUsertype(UserType.SUPPLIER);
            vss5.setVpa(vpaDG);
            if (vssRepository.findAllByUsertypeAndVpa(vss5.getUsertype(), vss5.getVpa()).size() == 0) {

                vssRepository.save(vss5);
            }


        }
    }

    public void createDefaultSkills() {
        Skill skill = new Skill();
        skill.setName("Public Health");

        Skill skill2 = new Skill();
        skill2.setName("Medical Expert");

        Skill skill3 = new Skill();
        skill3.setName("Legal Expert");
        if (skillRepository.findByName(skill.getName()) == null) {
            skillRepository.save(skill);
        }
        if (skillRepository.findByName(skill2.getName()) == null) {
            skillRepository.save(skill2);
        }
        if (skillRepository.findByName(skill3.getName()) == null) {
            skillRepository.save(skill3);
        }


    }

    public void createDefaultRoles() {
        Role ngo = new Role();
        ngo.setName("NGO");
        ngo.setActive(true);
        ngo.setDescription("NGO role description");
        if (roleRepository.findOneByName(ngo.getName()) == null) {
            roleRepository.save(ngo);
        }
        Role supplier = new Role();
        supplier.setName("SUPPLIER");
        supplier.setActive(true);
        supplier.setDescription("SUPPLIER role description");
        if (roleRepository.findOneByName(supplier.getName()) == null) {

            roleRepository.save(supplier);
        }

        Role staff = new Role();
        staff.setName("STAFF");
        staff.setActive(true);
        staff.setDescription("STAFF role description");
        if (roleRepository.findOneByName(staff.getName()) == null) {

            roleRepository.save(staff);
        }

        Role consultant = new Role();
        consultant.setName("CONSULTANT");
        consultant.setActive(true);
        consultant.setDescription("CONSULTANT role description");
        if (roleRepository.findOneByName(consultant.getName()) == null) {

            roleRepository.save(consultant);
        }

        Role vendor = new Role();
        vendor.setName("VENDOR");
        vendor.setActive(true);
        vendor.setDescription("VENDOR role description");
        if (roleRepository.findOneByName(vendor.getName()) == null) {

            roleRepository.save(vendor);
        }

        Role admin = new Role();
        admin.setName("ADMIN_ACCOUNT");
        admin.setActive(true);
        admin.setDescription("ADMIN role description");
        if (roleRepository.findOneByName(admin.getName()) == null) {

            roleRepository.save(admin);
        }
    }

    public void createDefaultUsers() {

         /*
          public User( String lastname, String firstname,String email,   String password ,String... usertypes){


        * */


        User adminUser = new User("Khalib", "Haliru", "hakimkal@yahoo.com", "admin", "admin", new String[]{UserType.ADMIN.toString(), UserType.NGO.toString(), UserType.SUPPLIER.toString(),
                UserType.CONSULTANT.toString()
        });


        // adminUser.setPassword(bCryptEncoder.encode("admin"));
        User consultantUser = new User("Khalib", "Haliru", "consultant@yahoo.com", "consultant", "consultant", new String[]{UserType.CONSULTANT.toString()});

        User unstaffUser = new User("Khalib", "Haliru", "unstaff@yahoo.com", "unstaff", "unstaff", new String[]{UserType.UNSTAFF.toString()});

        User supplierUser = new User("Khalib", "Haliru", "supplier@yahoo.com", "supplier", "supplier", new String[]{UserType.SUPPLIER.toString()});

        User ngoUser = new User("Khalib", "Haliru", "ngo@yahoo.com", "ngo", "ngo", new String[]{

                UserType.NGO.toString()});


//Create Default Admin

        if (this.userRepository.findOneByUsername(adminUser.getUsername()) == null) {
            this.userRepository.save(adminUser);
        }

// Create Default Consultant
        if (this.userRepository.findOneByUsername(consultantUser.getUsername()) == null) {
            this.userRepository.save(consultantUser);
        }
        //Create Default Supplier

        if (this.userRepository.findOneByUsername(supplierUser.getUsername()) == null) {
            this.userRepository.save(supplierUser);

        }
// Create Default UNSTAFF Admin
        if (this.userRepository.findOneByUsername(unstaffUser.getUsername()) == null) {
            this.userRepository.save(unstaffUser);
        }
//Create default NGO user
        if (this.userRepository.findOneByUsername(ngoUser.getUsername()) == null) {
            this.userRepository.save(ngoUser);
        }

    }

    /**
     * Role based security related
     */
    public void createDummyPermissions() {

        Permission p1 = permissionRepository.findOneByName("CAN_ADD_SUPPLIER");
        if (Objects.isNull(p1)) {
            p1 = new Permission();
            p1.setName("CAN_ADD_SUPPLIER");
            p1.setActive(true);
            p1.setDescription("First Test Description Description");
            permissionRepository.save(p1);
        }

        Permission p2 = permissionRepository.findOneByName("CAN_ADD_NGO");
        if (Objects.isNull(p2)) {
            p2 = Objects.isNull(p2) ? new Permission() : p2;
            p2.setName("CAN_ADD_NGO");
            p2.setActive(true);
            p2.setDescription("Description Description");
            permissionRepository.save(p2);
        }

        Permission p3 = permissionRepository.findOneByName("CAN_ADD_CONSULTANT");
        if (Objects.isNull(p3)) {
            p3 = Objects.isNull(p3) ? new Permission() : p3;
            p3.setName("CAN_ADD_CONSULTANT");
            p3.setActive(true);
            p3.setDescription("First Test Description Description");
            permissionRepository.save(p3);
        }
        Permission p22 = permissionRepository.findOneByName("CAN_ADD_VENDOR");
        if (Objects.isNull(p22)) {
            p22 = Objects.isNull(p22) ? new Permission() : p22;
            p22.setName("CAN_ADD_VENDOR");
            p22.setActive(true);
            p22.setDescription("Vendor Description Description");
            permissionRepository.save(p22);
        }

        Permission staffPerm = permissionRepository.findOneByName("CAN_ADD_STAFF");
        if (Objects.isNull(staffPerm)) {
            staffPerm = Objects.isNull(staffPerm) ? new Permission() : staffPerm;
            staffPerm.setName("CAN_ADD_STAFF");
            staffPerm.setActive(true);
            staffPerm.setDescription("Staff Permission Description");
            permissionRepository.save(staffPerm);
        }


        Role adminRole = roleRepository.findOneByName("ADMIN_ACCOUNT");
        if (Objects.isNull(adminRole)) {
            adminRole = new Role();
            adminRole.setName("ADMIN_ACCOUNT");
            adminRole.setActive(true);
            adminRole.setDescription("Role for all Admin Users");
            Set<Permission> adminPerms = new HashSet<>(2);
            adminPerms.add(p1);
            adminPerms.add(p2);
            adminPerms.add(p22);
            adminPerms.add(p3);
            adminPerms.add(staffPerm);
            adminRole.setPermissions(adminPerms);
            roleRepository.save(adminRole);
        }


        Role staff = roleRepository.findOneByName("STAFF");
        if (Objects.isNull(staff)) {
            staff = new Role();
            staff.setName("STAFF");
            staff.setActive(true);
            staff.setDescription("Staff role description");
            Set<Permission> staffPerms = new HashSet<>(1);
            staffPerms.add(p22);

            staff.setPermissions(staffPerms);
            roleRepository.save(staff);
        }
        Role vendor = roleRepository.findOneByName("VENDOR");
        if (Objects.isNull(vendor)) {
            vendor = new Role();
            vendor.setName("VENDOR");
            vendor.setActive(true);
            vendor.setDescription("VENDOR role description");
            Set<Permission> vendorPerms1 = new HashSet<>(1);
            vendorPerms1.add(p22);
            vendor.setPermissions(vendorPerms1);
            roleRepository.save(vendor);
        }
        Role consultant = roleRepository.findOneByName("CONSULTANT");
        if (Objects.isNull(consultant)) {
            consultant = new Role();
            consultant.setName("CONSULTANT");
            consultant.setActive(true);
            consultant.setDescription("CONSULTANT role description");
            Set<Permission> consultantPerms = new HashSet<>(1);
            consultantPerms.add(p3);
            consultant.setPermissions(consultantPerms);
            roleRepository.save(consultant);
        }

        Role ngo = roleRepository.findOneByName("NGO");
        if (Objects.isNull(ngo)) {
            ngo = new Role();
            ngo.setName("NGO");
            ngo.setActive(true);
            ngo.setDescription("NGO role description");
            Set<Permission> ngoPerms = new HashSet<>(1);
            ngoPerms.add(p2);
            ngo.setPermissions(ngoPerms);
            roleRepository.save(ngo);
        }

        Role supplier = roleRepository.findOneByName("SUPPLIER");
        if (Objects.isNull(supplier)) {
            supplier = new Role();
            supplier.setName("SUPPLIER");
            supplier.setActive(true);
            supplier.setDescription("SUPPLIER role description");
            Set<Permission> supplierPerms = new HashSet<>(1);
            supplierPerms.add(p1);
            supplier.setPermissions(supplierPerms);
            roleRepository.save(supplier);
        }


        User hakeem = userRepository.findByEmail("hakimkal@yahoo.com");
        Set<Role> hakeemRoles = new HashSet<>(1);

        hakeemRoles.add(adminRole);
        hakeemRoles.add(ngo);
        hakeemRoles.add(supplier);
        hakeemRoles.add(consultant);

        hakeemRoles.add(vendor);


        hakeem.setRoles(hakeemRoles);

        userRepository.save(hakeem);


        User supplierUser = userRepository.findByEmail("supplier@yahoo.com");
          Set<Role>vendorRoles = new HashSet<>(1);

        vendorRoles.add(vendor);
        vendorRoles.add(vendor);
        supplierUser.setRoles(vendorRoles);
        userRepository.save(supplierUser);

        User consultantUser = userRepository.findByEmail("consultant@yahoo.com");

        vendorRoles.add(vendor);
        consultantUser.setRoles(vendorRoles);
        userRepository.save(consultantUser);


        User ngoUser = userRepository.findByEmail("ngo@yahoo.com");

        vendorRoles.add(vendor);
        ngoUser.setRoles(vendorRoles);
        userRepository.save(ngoUser);

        User staffUser = userRepository.findByEmail("unstaff@yahoo.com");
        Set<Role> staffRoles = new HashSet<>(1);

        staffRoles.add(staff);

        userRepository.save(staffUser);


    }

    public void createDummyPathSecurity() {
        PathSecurity ps = new PathSecurity();
        ps.setName("/accounts/login:get");
        ps.setPermission(null);
        pathSecurityRepository.save(ps);

        PathSecurity ps1 = new PathSecurity();
        ps1.setName("/accounts/login:post");
        ps1.setPermission(null);
        pathSecurityRepository.save(ps1);

        PathSecurity ps2 = new PathSecurity();
        ps2.setName("/logout:get");
        ps2.setPermission(null);
        pathSecurityRepository.save(ps2);

        PathSecurity ps3 = new PathSecurity();
        ps3.setName("/accounts/register:get");
        ps3.setPermission(null);
        pathSecurityRepository.save(ps3);

    }

    public void createDefaultDocTypes(){
        DocumentType docType1 = new DocumentType();
        docType1.setName("BOQ");
        docType1.setNiceName("Bill of Quantity");
        docType1.setDescription("Description of bill of Quantity");
        documentTypeRepository.save(docType1);

        DocumentType docType2 = new DocumentType();
        docType2.setName("INVOICE");
        docType2.setNiceName("Purchase Invoice");
        docType2.setDescription("Description of  invoice document Type");
        documentTypeRepository.save(docType2);

        DocumentType docType3 = new DocumentType();
        docType3.setName("PROPOSAL");
        docType3.setNiceName("Project Proposal Document");
        docType3.setDescription("Description of Proposal Document");
        documentTypeRepository.save(docType3);
    }


    @Override
    // @Transactional
    public void run(String... strings) throws Exception {
        if (Accessor.count(PathSecurity.class, Filter.get()) == 0) {
            createDummyPathSecurity();
        }
        if (Accessor.count(User.class, Filter.get()) >= 5) {
            return;
        }

        createDefaultRoles();
        parseCountries();
        parseStates("nigeria");
        createDefaultVPAs();
        createDefaultVss();
        createDefaultSkills();
        createDefaultUsers();
        createDummyPermissions();
        createDefaultDocTypes();


    }

}
