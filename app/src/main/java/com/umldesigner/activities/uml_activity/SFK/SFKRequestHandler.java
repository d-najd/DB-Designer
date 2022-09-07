package com.umldesigner.activities.uml_activity.SFK;

import android.util.Log;
import com.umldesigner.Message;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.foreign_key.dto.SFKPojo;

import java.util.List;

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
        Log.d("Execute", "receiveData with code: " + request.toString() + " and received data count " + requestedData.size());

        new CheckTablesExist(this, requestedData, controller, request);
    }

    /**
     * this method gets called once we are sure that all sfk's exist
     */
    public void continueSetup(List<SFKPojo> requestedData, ApiController<SFKPojo> controller, ApiRequest request){
        Message.message(controller.getContainer().getContext(), "hello");
    }

    private static class CheckTablesExist{
        private final SFKRequestHandler parent;
        private final List<SFKPojo> requestedData;
        private final ApiController<SFKPojo> controller;
        private final ApiRequest request;

        //how much time we have currently waited so far (in ms)
        long curWait = 0;

        //delay between checks to see if the table items have been received (in ms)
        long delayBetweenChecks = 200;

        //maximum waiting time for the items to be received, if it goes past this error should get thrown (in ms)
        long maxWaitTime = 10_000;

        /**
         * @implNote no need to call runCheck method, it gets called automatically
         */
        public CheckTablesExist(SFKRequestHandler parent, List<SFKPojo> requestedData,
                                ApiController<SFKPojo> controller, ApiRequest request){
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
        private void runCheck(){
            SUtils sUtils = SUtils.getInstance();

            final boolean[] allNotExist = {false};
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();

                    //boolean for telling us if all the pojos don't exist in sUtils

                    while(maxWaitTime >= curWait){
                        for(SFKPojo pojo : requestedData){
                            if(sUtils.getTableByUuid(pojo.getIdentity().getFirstUuid()) == null ||
                               sUtils.getTableByUuid(pojo.getIdentity().getSecondUuid()) == null){
                                allNotExist[0] = true;
                                break;
                            }
                        }

                        //if the tables don't exist we need to repeat
                        if(!allNotExist[0]) {
                            try {
                                sleep(delayBetweenChecks);
                                curWait += delayBetweenChecks;
                                runCheck();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            };
            thread.start();
        }
    }
}
