package com.mmtap.cma.demo.saas.tenant;

public class TenantContextHolder {
    private static final InheritableThreadLocal<String> currentTenant = new InheritableThreadLocal<String>();


    public static void setTenantId(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
