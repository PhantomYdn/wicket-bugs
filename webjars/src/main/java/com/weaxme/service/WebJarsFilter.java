package com.weaxme.service;

import com.weaxme.HomePage;
import com.weaxme.WebJarsTestCaseApplication;
import org.apache.wicket.protocol.http.ReloadingWicketFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Vitaliy Gonchar
 */
public class WebJarsFilter extends ReloadingWicketFilter {

    private FilterConfig filterConfig;
    private ClassLoader classLoader;
    private static final Logger LOG = LoggerFactory.getLogger(WebJarsFilter.class);

    private static WebJarsFilter instance;

    private boolean isReload = false;

    @Override
    public void init(boolean isServlet, FilterConfig filterConfig) throws ServletException {
        instance = this;
        this.filterConfig = filterConfig;
        if (HomePage.wasReload) {
            classLoader = new WebJarsClassLoader(getUrl(), WebJarsTestCaseApplication.class.getClassLoader());
        } else classLoader = new WebJarsClassLoader(null, WebJarsTestCaseApplication.class.getClassLoader());

        super.init(isServlet, filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (!isReload) super.doFilter(servletRequest, servletResponse, filterChain);
    }


    @Override
    protected ClassLoader getClassLoader() {
        return classLoader;
    }

    public static WebJarsFilter getInstance() {
        return instance;
    }

    public void reload() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);
        executor.schedule(new Runnable() {

            @Override
            public void run() {
                isReload = true;
                destroy();
                try {
                    init(true, filterConfig);
                } catch (ServletException e) {
                    LOG.error("Cannot reload application", e);
                }
                isReload = false;
            }
        }, 2000, TimeUnit.MILLISECONDS);
    }

    private URL getUrl() {
        try {
            Path path = Paths.get("src/test/font-awesome-4.7.0.jar");
            LOG.info("exists: " + Files.exists(path));
            return path.toUri().toURL();
        } catch (MalformedURLException e) {
            LOG.error("Cannot create url!", e);
        }
        return null;
    }
}
