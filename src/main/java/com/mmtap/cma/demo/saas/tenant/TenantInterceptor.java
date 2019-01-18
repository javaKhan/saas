package com.mmtap.cma.demo.saas.tenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class TenantInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("执行拦截器");
        log.info("设置线程ID：" + Thread.currentThread().getId());
        String x = DataSourceBasedMultiTenantConnectionProviderImpl.dataSourcesMtApp.toString();
        log.info("数据源池:" + x);
        if (null != DataSourceBasedMultiTenantConnectionProviderImpl.dataSourcesMtApp
                && DataSourceBasedMultiTenantConnectionProviderImpl.dataSourcesMtApp.size() > 1) {
            String domain = request.getRequestURL().toString();
            int domainIndex = domain.toLowerCase().indexOf(".zdha.cn");
            log.info(domain+"    "+Thread.currentThread());
            if (domainIndex > 0) { //有域名才做解析
                String domainKey = domain.substring(0, domainIndex).replace("http://", "");
                log.info("domian:" + domain + "    dominKey:" + domainKey);
                if (DataSourceBasedMultiTenantConnectionProviderImpl.dataSourcesMtApp.size() > 0
                        && null != DataSourceBasedMultiTenantConnectionProviderImpl.dataSourcesMtApp.get(domainKey)) {
                    log.info("tentid:设置" + domainKey);
                    TenantContextHolder.setTenantId(domainKey);
                    Thread thread = Thread.currentThread();
                    log.info("TenantContextHolder:"+TenantContextHolder.getTenant());
                } else {
                    TenantContextHolder.setTenantId("Default");
                    log.info("TenantContextHolder default:"+TenantContextHolder.getTenant());
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        log.info("释放:"+TenantContextHolder.getTenant());
        TenantContextHolder.clear();
    }

}
