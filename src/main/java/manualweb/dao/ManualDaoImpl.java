package manualweb.dao;

import manualweb.model.Constants;
import manualweb.model.Manual;
import manualweb.model.ManualFilter;
import manualweb.model.UserChoiceKeeper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ManualDaoImpl implements ManualDao {
    private static final Logger logger = LogManager.getLogger(ManualDaoImpl.class.getName());
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
        manualFilter.setManuals(getRawManuals(new UserChoiceKeeper(), manualFilter));
        populateFilters(new UserChoiceKeeper(), manualFilter);

        return manualFilter;
    }

    public ManualFilter filterManuals(UserChoiceKeeper choice) {
        ManualFilter manualFilter = new ManualFilter();
        choice.setHasFilters(true);
        manualFilter.setManuals(getRawManuals(choice, manualFilter));
        populateFilters(choice, manualFilter);
        manualFilter.setHasFilters(true);

        return manualFilter;
    }

    public ManualFilter loadManualsFromPage(ManualFilter filter, int pageNo) {
        if (pageNo<0){
            pageNo = 0;
        }
        filter.setCurrentPage(pageNo);
        filter.setManuals(getRawManuals(new UserChoiceKeeper(), filter));
        populateFilters(new UserChoiceKeeper(), filter);

        return filter;
    }


    @SuppressWarnings("unchecked")
    private List<Manual> getRawManuals(UserChoiceKeeper choice, ManualFilter filter){
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = this.getQuery(session,choice);
        if (choice.isHasFilters())
        {
            return criteria.list();
        }
        int maxResults = Constants.MAX_RESULTS;
        filter.setTotalQueryResults((Long)(session.createQuery("select count(*) FROM Manual").uniqueResult()));
        if (filter.getCurrentPage()*maxResults>filter.getTotalQueryResults()){
            filter.setCurrentPage(0);
        }
        criteria.setMaxResults(maxResults);
        criteria.setFirstResult(filter.getCurrentPage()*maxResults);

        return criteria.list();
    }

    private void populateFilters(UserChoiceKeeper choice, ManualFilter filter){
        Session session = this.sessionFactory.getCurrentSession();
        String brand = choice.getManualBrand();

        Criteria criteria = this.getQuery(session,choice);

        if (brand!=null&&brand.length()>0) {
            List<String>brands = new ArrayList<String>();
            brands.add(brand);
            filter.setBrands(brands);

            criteria = this.getQuery(session, choice);
            criteria.addOrder(Order.asc("partNo"));
            criteria.setProjection(Projections.distinct(Projections.property("partNo")));
            filter.setParts(criteria.list());

            criteria = this.getQuery(session, choice);
            criteria.addOrder(Order.asc("docType"));
            criteria.setProjection(Projections.distinct(Projections.property("docType")));
            filter.setDocTypes(criteria.list());

            criteria = this.getQuery(session, choice);
            criteria.addOrder(Order.asc("category"));
            criteria.setProjection(Projections.distinct(Projections.property("category")));
            filter.setCategories(criteria.list());
        }
        else {
            criteria.addOrder(Order.asc("brand"));
            criteria.setProjection(Projections.distinct(Projections.property("brand")));
            filter.setBrands(criteria.list());
        }
    }



    @SuppressWarnings("unchecked")
    private Criteria getQuery(Session session, UserChoiceKeeper choice){
        Criteria criteria = session.createCriteria(Manual.class);
        String brand = choice.getManualBrand();
        if (brand!=null&&brand.length()>0) {
            criteria.add(Restrictions.like("brand",brand));
            String partNo = choice.getManualPart();
            if (partNo!=null&&partNo.length()>0) {
                criteria.add(Restrictions.like("partNo",partNo));
            }
            String docType = choice.getManualDoctype();
            if (docType!=null&&docType.length()>0) {
                criteria.add(Restrictions.like("docType",docType));
            }
            String category = choice.getManualCategory();
            if (category!=null&&category.length()>0) {
                criteria.add(Restrictions.like("category",category));
            }
        }

        return criteria;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

     /*@SuppressWarnings("unchecked")
    private List<Manual> getRawManuals2(ManualFilter filter){
        Session session = this.sessionFactory.getCurrentSession();
        if (filter.isHasFilters())
        {
            Criteria criteria = this.getQuery(session,filter);
            return criteria.list();
        }
        String queryString = this.buildQuery(filter);
        Query query = session.createQuery(queryString);
        filter.setTotalQueryResults((Long)(session.createQuery("select count(*) " + queryString).uniqueResult()));
        int maxResults = filter.getMaxResults();
        if (filter.getCurrentPage()*maxResults>filter.getTotalQueryResults()){
            filter.setCurrentPage(0);
        }
        query.setMaxResults(maxResults);
        query.setFirstResult(filter.getCurrentPage()*maxResults);
        logger.info("current page is " + filter.getCurrentPage());
        return query.list();
    }*/

}

