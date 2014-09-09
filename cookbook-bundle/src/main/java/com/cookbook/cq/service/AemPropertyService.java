package com.cookbook.cq.service;

import java.util.Dictionary;

public interface AemPropertyService {

    Dictionary<String, String> getProperty(String configuration);

    String getProperty(String config, String property);
}
