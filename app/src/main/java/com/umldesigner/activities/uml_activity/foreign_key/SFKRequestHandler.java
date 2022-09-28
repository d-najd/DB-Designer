package com.umldesigner.activities.uml_activity.foreign_key;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.umldesigner.Message;
import com.umldesigner.activities.uml_activity.table_item.SItemData;
import com.umldesigner.activities.uml_activity.table.STableData;
import com.umldesigner.infrastructure.uml.error.ErrorTags;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.foreign_key.dto.SFKPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class SFKRequestHandler implements RequestHandler<SFKPojo> {
    private static SFKRequestHandler instance;

    public static SFKRequestHandler getInstance(){
        if (instance == null){
            instance = new SFKRequestHandler();
        }
        return instance;
    }

    @Override
    public void receiveData(List<SFKPojo> requestedData, ApiController<SFKPojo> controller, ApiRequest request) {
        Log.d("Execute", "receiveData with request: " + request.toString() + " and received data count "
                + Objects.requireNonNullElse(requestedData, new ArrayList<>()).size());

        new CheckTablesExist(this, requestedData, controller, request);
    }

    /**
     * this method gets called once we are sure that all sfk's exist
     */
    synchronized public void continueSetup(List<SFKPojo> requestedData, ApiController<SFKPojo> controller, ApiRequest request){
        Log.d("Execute", "continueSetup");

        for(SFKPojo pojo : requestedData) {
            STableData fTable = SUtils.getInstance().getTableByUuid(pojo.getFirstTableUuid());
            STableData sTable = SUtils.getInstance().getTableByUuid(pojo.getSecondTableUuid());

            SItemData fItem = fTable.getItemByUuid(pojo.getIdentity().getFirstUuid());
            SItemData sItem = sTable.getItemByUuid(pojo.getIdentity().getSecondUuid());

            new SFKBuilder(
                    controller.getContainer(),
                    fTable,
                    fTable.getItemPosition(fItem),
                    sTable,
                    sTable.getItemPosition(sItem)
            ).build();
        }

    }

    private static class CheckTablesExist {
        private final SFKRequestHandler parent;
        private final List<SFKPojo> requestedData;
        private final ApiController<SFKPojo> controller;
        private final ApiRequest request;

        //how much time we have currently waited so far (in ms)
        long curWait = 0;

        //delay between checks to see if the table items have been received (in ms)
        long delayBetweenChecks = 200;

        //maximum waiting time for the items to be received, if it goes past this error should get thrown (in ms)
        long maxWaitTime = 10_00;

        /**
         * @implNote no need to call runCheck method, it gets called automatically
         */
        public CheckTablesExist(SFKRequestHandler parent, List<SFKPojo> requestedData,
                                ApiController<SFKPojo> controller, ApiRequest request) {
            this.parent = parent;
            this.requestedData = requestedData;
            this.controller = controller;
            this.request = request;
            runCheck();
        }

        /**
         * @implSpec method for checking if the tables and items exist in the table (for example if the request for getting them is
         * still not done). if they don't exist wait 10 seconds, and check every 200ms for changes.
         * after 10 seconds if they still aren't present error message gets displayed to the user
         */
        synchronized private void runCheck() {
            SUtils sUtils = SUtils.getInstance();

            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    Looper.prepare();

                    while (maxWaitTime >= curWait) {
                        //boolean for telling us if all the pojos don't exist in sUtils
                        boolean allNotExist = false;

                        for (SFKPojo pojo : requestedData) {
                            if (sUtils.getTableByUuid(pojo.getFirstTableUuid()) == null ||
                                    sUtils.getTableByUuid(pojo.getSecondTableUuid()) == null) {
                                allNotExist = true;
                                break;
                            }
                        }

                        //if the tables don't exist we need to repeat
                        if (allNotExist) {
                            try {
                                sleep(delayBetweenChecks);
                                curWait += delayBetweenChecks;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            //getting the main thread and forwarding the request to it
                            new Handler(controller.getContainer().getContext().getMainLooper()).post(()
                                    -> parent.continueSetup(requestedData, controller, request));
                            return;
                        }
                    }
                    if(requestedData != null){
                        ArrayList<String> tableUuids = new ArrayList<>(sUtils.getTablesByUuid().keySet());
                        Log.e(ErrorTags.APP_ERROR, "unable to find table assigned to one of the sfk's "
                                + requestedData + " available tables are by uuid's are " + tableUuids);
                        Message.defErrMessage(controller.getContainer().getContext());
                    } else {
                        Log.w(ErrorTags.APP_WARN, "there doesn't seem to be any SFK's for the current table");
                    }
                }
            };
            thread.start();
        }
    }
}
