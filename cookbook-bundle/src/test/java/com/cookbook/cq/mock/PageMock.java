package com.cookbook.cq.mock;

import com.day.cq.commons.Filter;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;
import com.day.cq.wcm.api.WCMException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/**
 * User: bvaughn
 * Date: 8/4/14
 */
public class PageMock implements Page {

    private String title;
    private String name;
    private String path;

    public PageMock(String title, String name, String path) {
        this.title = title;
        this.name = name;
        this.path = path;
    }

    @Override public String getPath() {
        return path;
    }

    @Override public PageManager getPageManager() {
        return null;
    }

    @Override public Resource getContentResource() {
        return null;
    }

    @Override public Resource getContentResource(String s) {
        return null;
    }

    @Override public Iterator<Page> listChildren() {
        return null;
    }

    @Override public Iterator<Page> listChildren(Filter<Page> pageFilter) {
        return null;
    }

    @Override public Iterator<Page> listChildren(Filter<Page> pageFilter, boolean b) {
        return null;
    }

    @Override public boolean hasChild(String s) {
        return false;
    }

    @Override public int getDepth() {
        return 0;
    }

    @Override public Page getParent() {
        return null;
    }

    @Override public Page getParent(int i) {
        return null;
    }

    @Override public Page getAbsoluteParent(int i) {
        return null;
    }

    @Override public ValueMap getProperties() {
        return null;
    }

    @Override public ValueMap getProperties(String s) {
        return null;
    }

    @Override public String getName() {
        return name;
    }

    @Override public String getTitle() {
        return title;
    }

    @Override public String getDescription() {
        return null;
    }

    @Override public String getPageTitle() {
        return null;
    }

    @Override public String getNavigationTitle() {
        return null;
    }

    @Override public boolean isHideInNav() {
        return false;
    }

    @Override public boolean hasContent() {
        return false;
    }

    @Override public boolean isValid() {
        return false;
    }

    @Override public long timeUntilValid() {
        return 0;
    }

    @Override public Calendar getOnTime() {
        return null;
    }

    @Override public Calendar getOffTime() {
        return null;
    }

    @Override public String getLastModifiedBy() {
        return null;
    }

    @Override public Calendar getLastModified() {
        return null;
    }

    @Override public String getVanityUrl() {
        return null;
    }

    @Override public Tag[] getTags() {
        return new Tag[0];
    }

    @Override public void lock() throws WCMException {

    }

    @Override public boolean isLocked() {
        return false;
    }

    @Override public String getLockOwner() {
        return null;
    }

    @Override public boolean canUnlock() {
        return false;
    }

    @Override public void unlock() throws WCMException {

    }

    @Override public Template getTemplate() {
        return null;
    }

    @Override public Locale getLanguage(boolean b) {
        return null;
    }

    @Override public <AdapterType> AdapterType adaptTo(Class<AdapterType> type) {
        return null;
    }
}
