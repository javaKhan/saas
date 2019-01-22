package com.mmtap.cma.demo.saas.tenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class TenantInterceptor extends HandlerInterceptorAdapter {

//    private static final String CURRENT_TENANT_IDENTIFIER = "TENANT_ID";

    @Autowired
    private DataSourceBasedMultiTenantConnectionProviderImpl dsProvider;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //如果没有初始化先初始化操作
        if (!DataSourceBasedMultiTenantConnectionProviderImpl.init) {
            dsProvider.initTenantDataSource();
        }
        if (null != dsProvider.dataSourcesMtApp) {
            String domain = request.getRequestURL().toString();
            int domainIndex = domain.toLowerCase().indexOf(".zdha.cn");
            if (domainIndex > 0) { //有域名才做解析
                String domainKey = domain.substring(0, domainIndex).
                        replace("http://", "").
                        replace("https://","");
                log.info("domian:" + domain + "    dominKey:" + domainKey);
                if (dsProvider.dataSourcesMtApp.size() > 0 && null != dsProvider.dataSourcesMtApp.get(domainKey)) {
                    request.setAttribute(MultiTenantConstants.CURRENT_TENANT_IDENTIFIER, domainKey);
                } else {
                    request.setAttribute(MultiTenantConstants.CURRENT_TENANT_IDENTIFIER, MultiTenantConstants.DEFAULT_TENANT_ID);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        request.removeAttribute(MultiTenantConstants.CURRENT_TENANT_IDENTIFIER);
    }

}
