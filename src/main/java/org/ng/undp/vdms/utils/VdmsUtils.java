package org.ng.undp.vdms.utils;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.security.Role;

import java.util.*;

/**
 * Created by macbook on 6/20/17.
 */
public class VdmsUtils
{


    public static Map<String, String> getVendorRolesAsMap(User user) {
        Set<Role> roles = Accessor.findOne(User.class, Filter.get().field("uuid", user.getUuid())).getRoles();
        Map<String, String> vendors = new HashMap<String, String>(1);

        roles.forEach((role) -> {
            if (!role.getName().equals("VENDOR") && (!role.getName().equals("ADMIN_ACCOUNT")) && (!role.getName().equals("STAFF"))) {
                vendors.put(role.getName().toUpperCase(), role.getName().toUpperCase());
            }


        });
        return vendors;

    }

    public static Map<String, String> getVendorRolesAsMap() {
        List<Role> roles = Accessor.findList(Role.class, Filter.get().field("active", true));
        Map<String, String> vendors = new HashMap<String, String>(1);

        roles.forEach((role) -> {
            if (!role.getName().equals("VENDOR") && (!role.getName().equals("ADMIN_ACCOUNT")) && (!role.getName().equals("STAFF"))) {
                vendors.put(role.getName().toUpperCase(), role.getName().toUpperCase());
            }


        });
        return vendors;

    }

    public static Set<Role> getVendorRoles() {
        List<Role> roles = Accessor.findList(Role.class, Filter.get().field("active", true));
        Set<Role> vendors = new HashSet<Role>(1);

        roles.forEach((role) -> {
            if (!role.getName().equals("VENDOR") && (!role.getName().equals("ADMIN_ACCOUNT")) && (!role.getName().equals("STAFF"))) {
                vendors.add(role  );
            }


        });
        return vendors;

    }
    public static  List<Role> getNonVendorRoles() {
        List<Role> roles = Accessor.findList(Role.class, Filter.get().field("active", true));
        List<Role> vendors = new ArrayList<Role>();

        roles.forEach((role) -> {
            if (!role.getName().equals("NGO") && (!role.getName().equals("CONSULTANT")) && (!role.getName().equals("SUPPLIER")) && (!role.getName().equals("VENDOR"))) {
                vendors.add(role);
            }


        });
        return vendors;
    }

}
