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

if (CQ_Analytics.CookbookStoreMgr ) {


    CQ_Analytics.CookbookStoreMgr.renderer = function(store, divId) {

        // first load data
		// CQ_Analytics.CookbookStoreMgr.loadData();

		$CQ("#" + divId).children().remove();


        // Set title
		$CQ("#" + divId).addClass("cq-cc-cookbookstore");
		var div = $CQ("<div>").html("Cookbook Context");
		$CQ("#" + divId).append(div);           


		var data = this.getJSON();

        if (data) {
            for (var i in data) {
                if (typeof data[i] === 'object') {
                    console.log(data[i]);
                    var div = $CQ("<div>").html(data[i].label + " - " + data[i].value);
					$CQ("#" + divId).append(div);
                }
            }
        }       

    }

    
	CQ_Analytics.ClickstreamcloudMgr.register(CQ_Analytics.CookbookStoreMgr);

}