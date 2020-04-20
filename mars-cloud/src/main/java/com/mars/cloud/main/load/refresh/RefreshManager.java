package com.mars.cloud.main.load.refresh;

import com.mars.cloud.main.core.constant.MarsCloudConstant;
import com.mars.cloud.main.core.zookeeper.ZkHelper;
import com.mars.cloud.main.load.register.Registered;
import com.mars.core.annotation.MarsBean;
import com.mars.core.annotation.MarsWrite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听器,由Watcher触发
 */
@MarsBean("refreshManager")
public class RefreshManager {

    @MarsWrite("registered")
    private Registered registered;

    /**
     * 从zookeeper里把所有的接口都拉取下来
     * @return 所有的接口
     * @throws Exception 异常
     */
    public synchronized Map<String,List<String>> refreshCacheApi() throws Exception {
        /* 如果zk没有打开连接的话 就打开一下 */
        ZkHelper.openConnection();

        Map<String,List<String>> urlMap = new ConcurrentHashMap<>();

        /* 获取根节点下的所有子节点 */
        List<String> nodeList = ZkHelper.getChildren(MarsCloudConstant.BASE_SERVER_NODE);
        if(nodeList == null || nodeList.size() < 1){
            return null;
        }

        for(String node : nodeList){
            String childrenNodePath = MarsCloudConstant.BASE_SERVER_NODE+"/"+node;

            /* 获取每一个子节点下的所有子节点 */
            List<String> childrenNodeList = ZkHelper.getChildren(childrenNodePath);
            if(childrenNodeList == null || childrenNodeList.size() < 1){
                continue;
            }

            /* 将获取到的节点的数据取出来 */
            List<String> urls = new ArrayList<>();
            for(String childrenNode : childrenNodeList){
                String cacheNodePath = childrenNodePath + "/" + childrenNode;
                String url = ZkHelper.getData(cacheNodePath);
                if(url == null || url.equals("")){
                    continue;
                }
                urls.add(url);
            }
            urlMap.put(childrenNodePath,urls);
        }

        return urlMap;
    }
}
