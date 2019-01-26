package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

public class ResponseEmergencyStop extends ResponseEntity {
    /**true 表示急停按钮处于激活状态(按下), false 表示急停按钮处于非激活状态(拔起); 可缺省：是*/
    private boolean emergency;

    /**true 表示驱动器发生急停, false 驱动器发生未急停; 可缺省：是*/
    private boolean driver_emc;

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public boolean isDriver_emc() {
        return driver_emc;
    }

    public void setDriver_emc(boolean driver_emc) {
        this.driver_emc = driver_emc;
    }
}
