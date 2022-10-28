package com.kroger.ngpp.testutils;

import com.kroger.desp.commons.effo.data.ready.EventHeader;
import com.kroger.desp.commons.effo.data.ready.Payload;
import com.kroger.desp.events.effo.data.ready.EffoDataReady;

public class Utils {
    public static EffoDataReady mockBatchOutput() {
        EffoDataReady dataReady = new EffoDataReady();
        EventHeader header = new EventHeader();
        Payload payload = new Payload();
        header.setType("8451-edna-data-ready");
        payload.setBatchId("80414c93-6d59-4dc5-a277-fed0fb30d09d");
        payload.setScience("rpo");
        payload.setRunType("in-market");
        dataReady.setEventHeader(header);
        dataReady.setPayload(payload);
        return dataReady;
    }
}
