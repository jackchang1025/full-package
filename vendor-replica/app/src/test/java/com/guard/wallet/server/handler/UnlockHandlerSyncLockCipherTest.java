package com.guard.wallet.server.handler;

import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.utils.SharedPrefsManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnlockHandlerSyncLockCipherTest {

    @Test
    public void deserializedVoMustBeReqUnlockDeviceVO() {
        String json = "{\"textCipher\":\"1234\",\"deviceId\":\"test-uuid\"}";

        Object vo = SharedPrefsManager.d(json, ReqUnlockDeviceVO.class);

        assertNotNull("deserialized result must not be null", vo);
        assertTrue(
            "deserialized result must be ReqUnlockDeviceVO to pass UnlockHandler instanceof check",
            vo instanceof ReqUnlockDeviceVO
        );
        assertEquals("1234", ((ReqUnlockDeviceVO) vo).getTextCipher());
        assertEquals("test-uuid", ((ReqUnlockDeviceVO) vo).getDeviceId());
    }
}
