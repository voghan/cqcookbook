package com.cookbook.cq.domain.selector;

import com.cookbook.cq.domain.Base;

import java.util.List;

/**
 * User: bvaughn
 * Date: 9/8/14
 */
public class AemSelectorItems<T> extends Base {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
