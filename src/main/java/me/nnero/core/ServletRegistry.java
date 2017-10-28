package me.nnero.core;

import lombok.extern.slf4j.Slf4j;
import me.nnero.util.ResourceLoader;
import org.dom4j.DocumentException;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: NNERO
 * Time : 16:46 2017/10/23
 */
@Slf4j
public class ServletRegistry {

    private Map<String,String> servletMappingRegistry = new HashMap<>();

    public ServletRegistry(){}

    public void initRegistry(){
        try {
            ResourceLoader.getLoader().loadWebXMLConfigFile((className,path) -> {
                servletMappingRegistry.put(path,className);
            });
            log.info("web.xml init success!");
        } catch (DocumentException e) {
            log.warn("Web.xml parse error: ",e);
        }
    }

    public String getClassName(String path){
        return servletMappingRegistry.get(path);
    }
}
