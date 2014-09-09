/*
| Copyright 2013 Adobe
|
| Licensed under the Apache License, Version 2.0 (the "License");
| you may not use this file except in compliance with the License.
| You may obtain a copy of the License at
|
| http://www.apache.org/licenses/LICENSE-2.0
|
| Unless required by applicable law or agreed to in writing, software
| distributed under the License is distributed on an "AS IS" BASIS,
| WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
| See the License for the specific language governing permissions and
| limitations under the License.
*/
// Create the session store called "cookbookstore"
if (!CQ_Analytics.CookbookStoreMgr ) {

    // Create the session store as a JSONStore
    CQ_Analytics.CookbookStoreMgr = CQ_Analytics.JSONStore.registerNewInstance("cookbookstore");

	CQ_Analytics.CookbookStoreMgr.currentId = "";

    // Function to load the data for the current user
    CQ_Analytics.CookbookStoreMgr.loadData = function() {

        console.info("Loading CookbookStoreMgr data");

        var authorizableId = CQ_Analytics.ProfileDataMgr.getProperty("authorizableId");
        var url = "/apps/cookbook/components/content/loader.json";
        var mydata = null;

        if ( (authorizableId !== CQ_Analytics.CookbookStoreMgr.currentId) & CQ_Analytics.CookbookStoreMgr.initialized ) {

            url = CQ_Analytics.Utils.addParameter(url, "authorizableId", authorizableId);

            try {

                var object = CQ.shared.HTTP.eval(url);
                if (object) { this.data = object; }

            } catch(error) {
                console.log("Error", error);
            }

			CQ_Analytics.CookbookStoreMgr.currentId = authorizableId;

        }

        return this.data;

    };

    CQ_Analytics.CCM.addListener("configloaded", function() {

        CQ_Analytics.ProfileDataMgr.addListener("update", function() {
			this.loadData();
            this.fireEvent("update");
        }, CQ_Analytics.CookbookStoreMgr);

	}, CQ_Analytics.CookbookStoreMgr);

    CQ_Analytics.CookbookStoreMgr.addListener("initialize", function() {
		this.loadData();
    });

    CQ_Analytics.CookbookStoreMgr.initialized = false;

    CQ_Analytics.CookbookStoreMgr.getValue = function(service) {
        console.log(CQ_Analytics.CookbookStoreMgr.data);
        if (CQ_Analytics.CookbookStoreMgr.data) {
            if (CQ_Analytics.CookbookStoreMgr.data[service]) return  CQ_Analytics.CookbookStoreMgr.data[service].value;
        } else {
            console.log("..lost..");
        }
        return "";
    }


}