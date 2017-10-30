package manualweb.dao;

import manualweb.model.Manual;
import manualweb.model.ManualFilter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

@Repository
public class ManualDaoImpl implements ManualDao {
    private static final Logger logger = LoggerFactory.getLogger(ManualDaoImpl.class);
    private static final Short MAX_RESULTS = 3;

    private SessionFactory sessionFactory;

    public void addManual(Manual manual) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(manual);
        logger.info("manual added successfully. Manual details: "+manual);
    }

    public void updateManual(Manual manual) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(manual);
        logger.info("manual updated successfully. Manual details: "+manual);
    }

    public void removeManual(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Manual manual = (Manual)session.load(Manual.class, id);
        if (manual!=null){
            session.delete(manual);
            logger.info("manual deleted successfully. Manual details: "+manual);
        }
        else logger.info("manual wasn't deleted");
    }

    public Manual getManualById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Manual manual = (Manual)session.load(Manual.class,  id);
        logger.info("manual updated successfully. Manual details: "+manual);

        return manual;
    }

    public ManualFilter listManuals() {
        ManualFilter manualFilter = new ManualFilter();
        manualFilter.setManuals(getRawManuals(manualFilter));
        populateFilters(manualFilter);

        return manualFilter;
    }

    public ManualFilter filterManuals(ManualFilter filter) {
        ManualFilter manualFilter = new ManualFilter();
        filter.setHasFilters(true);
        manualFilter.setManuals(getRawManuals(filter));
        manualFilter.setFilters(filter);
        populateFilters(manualFilter);
        manualFilter.setHasFilters(true);

        return manualFilter;
    }

    public ManualFilter loadManualsFromPage(ManualFilter filter, int pageNo) {
        if (pageNo<1){
            pageNo = 1;
        }
        filter.setCurrentPage(pageNo);
        filter.setManuals(getRawManuals(filter));
        populateFilters(filter);

        return filter;
    }

    @SuppressWarnings("unchecked")
    private List<Manual> getRawManuals(ManualFilter filter){
        Session session = this.sessionFactory.getCurrentSession();
        String queryString = this.buildQuery(filter);
        Query query = session.createQuery(queryString);
        if (!filter.isHasFilters()){
            filter.setTotalQueryResults((Long)(session.createQuery("select count(*) " + queryString).uniqueResult()));
            int maxResults = filter.getMaxResults();
            if (filter.getCurrentPage()*maxResults>filter.getTotalQueryResults()){
                filter.setCurrentPage(0);
            }
            query.setMaxResults(maxResults);
            query.setFirstResult(filter.getCurrentPage()*maxResults);
        }

        return query.list();
    }

    @SuppressWarnings("unchecked")
    private void populateFilters(ManualFilter filter){
        ManualFilter filterStorage = new ManualFilter();
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT DISTINCT brand from Manual order by brand");
        List brandObjects = query.list();
        Iterator it = brandObjects.iterator();
        List<String>brands = new ArrayList<String>();
        while (it.hasNext()){
            brands.add((String)it.next());
        }
        filter.setBrands(brands);

        query = session.createQuery("SELECT DISTINCT partNo from Manual order by partNo");
        List partsObjects = query.list();
        it = partsObjects.iterator();
        List<String>parts = new ArrayList<String>();
        while (it.hasNext()){
            parts.add((String)it.next());
        }
        filter.setParts(parts);

        query = session.createQuery("SELECT DISTINCT docType from Manual order by docType");
        List docTypeObjects = query.list();
        it = docTypeObjects.iterator();
        List<String>docTypes = new ArrayList<String>();
        while (it.hasNext()){
            docTypes.add((String)it.next());
        }
        filter.setDocTypes(docTypes);

        query = session.createQuery("SELECT DISTINCT category from Manual order by category");
        List categoryObjects = query.list();
        it = categoryObjects.iterator();
        List<String>categories = new ArrayList<String>();
        while (it.hasNext()){
            categories.add((String)it.next());
        }
        filter.setCategories(categories);
    }

    private String buildQuery(ManualFilter filter){
        StringBuilder sb = new StringBuilder();
        boolean hasFilters = false;
        sb.append("from Manual");
        String brand = filter.getManualBrand();
        if (brand!=null && brand.length()!=0){
            sb.append("brand = '");
            sb.append(brand);
            sb.append("'");
            hasFilters = true;
        }
        String partNo = filter.getManualPart();
        if (partNo!=null && partNo.length()!=0){
            if (hasFilters){
                sb.append(" AND ");
            }
            sb.append("partNo = '");
            sb.append(partNo);
            sb.append("'");
            hasFilters = true;
        }
        String docType = filter.getManualDoctype();
        if (docType!=null && docType.length()!=0){
            if (hasFilters){
                sb.append(" AND ");
            }
            sb.append("docType = '");
            sb.append(docType);
            sb.append("'");
            hasFilters = true;
        }
        String category = filter.getManualCategory();
        if (category!=null && category.length()!=0){
            if (hasFilters){
                sb.append(" AND ");
            }
            sb.append("category = '");
            sb.append(category);
            sb.append("'");
            hasFilters = true;
        }
        String query = sb.toString();
        if (hasFilters){
            query = query.replace("from Manual", "from Manual where ");
        }
      //System.out.println(sessionFactory.getCurrentSession().createQuery("select count(*) from Manual where brand = 'Sony'").uniqueResult());
        return query;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}

