package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

/**
 * ContainerEventVO (resp package) — b.java W2() 中上报容器状态使用。
 * 对应 vendor server/b.java 中的 ContainerEvent 构建逻辑。
 */
public class ContainerEventVO extends MessageBodyVO {
    private String containerCode;
    private Integer isOpened;
    private String packageName;
    private Integer serviceState;

    public ContainerEventVO() {}

    public String getContainerCode() { return containerCode; }
    public void setContainerCode(String containerCode) { this.containerCode = containerCode; }

    public Integer getIsOpened() { return isOpened; }
    public void setIsOpened(Integer isOpened) { this.isOpened = isOpened; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public Integer getServiceState() { return serviceState; }
    public void setServiceState(Integer serviceState) { this.serviceState = serviceState; }

    @NonNull
    @Override
    public String toString() {
        return "ContainerEventVO{containerCode='" + containerCode
                + "', packageName='" + packageName
                + "', isOpened=" + isOpened
                + ", serviceState=" + serviceState + '}';
    }
}
