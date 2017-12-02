package manualweb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * This class keeps values for building web page.
 */
public class PageInfoKeeper {

    private List<Manual> manuals;

    private List<String> brands;
    private List<String> parts;
    private List<String> docTypes;
    private List<String> categories;

    private int currentPage = 0;
    private Long totalQueryResults = 0L;

    private boolean hasFilters = false;

    //Getters and Setters

    public List<Manual> getManuals() {
        return manuals;
    }
    public void setManuals(List<Manual> manuals) {
        this.manuals = manuals;
    }

    public List<String> getBrands() {
        return brands;
    }
    public List<String> getParts() {
        return parts;
    }
    public List<String> getDocTypes() {
        return docTypes;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setBrands(List<String> brands) {
        this.brands = brands;
    }
    public void setParts(List<String> parts) {
        this.parts = parts;
    }
    public void setDocTypes(List<String> docTypes) {
        this.docTypes = docTypes;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public Long getTotalQueryResults() {
        return totalQueryResults;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public void setTotalQueryResults(Long totalQueryResults) {
        this.totalQueryResults = totalQueryResults;
    }


    public boolean isHasFilters() {
        return hasFilters;
    }
    public void setHasFilters(boolean hasFilters) {
        this.hasFilters = hasFilters;
    }
}
