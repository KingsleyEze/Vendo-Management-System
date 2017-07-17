package org.ng.undp.vdms.configs.security;

import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.CustomUserDetails;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * Created by abdulhakim on 7/15/17.
 */
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    //
    public boolean isMember(Long OrganizationId) {
        final User user = ((CustomUserDetails) this.getPrincipal()).getUser();
        return true;// user.getOrganization().getId().longValue() == OrganizationId.longValue();
    }

    public boolean isVendor(String role) {
        final User user = ((CustomUserDetails) this.getPrincipal()).getUser();
        if(user.getRoleNames().size() == 1 ) {
            return true;

        }
        else if(user.getRoleNames().size()== 2 && user.getRoleNames().contains(role)){
            return true;
        }
        return false;
    }

    //

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }
}
