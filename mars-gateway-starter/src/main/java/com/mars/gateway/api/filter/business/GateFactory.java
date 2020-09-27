package com.mars.gateway.api.filter.business;

import com.mars.gateway.config.MarsGateWayConfig;
import com.mars.gateway.core.util.GateWayConfigUtil;

import java.util.List;

public class GateFactory {

    public static List<GateFilter> getGateFilter(){
        MarsGateWayConfig marsGateWayConfig = GateWayConfigUtil.getMarsGateWayConfig();
        if(marsGateWayConfig != null){
            return marsGateWayConfig.getGateFilter();
        }
        return null;
    }
}
