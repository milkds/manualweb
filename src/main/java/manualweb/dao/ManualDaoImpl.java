package manualweb.dao;

import manualweb.model.Constants;
import manualweb.model.Manual;
import manualweb.model.PageInfoKeeper;
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

    /*This field final class is defined in Spring app context (mvc-dispatcher-servlet.xml)*/
    private SessionFactory sessionFactory;

    /**
     * Adds manual to Database
     * @param manual - Manual to be added to database.
     */
    public void addManual(Manual manual) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(manual);
        logger.info("manual added successfully. Manual details: "+manual);
    }

    /**
     * Updates manual in database
     * @param manual - Manual to be updated in database.
     */
    public void updateManual(Manual manual) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(manual);
        logger.info("manual updated successfully. Manual details: "+manual);
    }

    /**
     * Removes manual from database
     * @param id - ID of manual to be removed from database
     */
    public void removeManual(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Manual manual = (Manual)session.load(Manual.class, id);
        if (manual!=null){
            session.delete(manual);
            logger.info("manual deleted successfully. Manual details: "+manual);
        }
        else logger.info("manual wasn't deleted");
    }

    /**
     * Gets manual from database by selected ID
     * @param id - ID of manual to be retrieved from database.
     * @return - manual from database by selected ID
     */
    public Manual getManualById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Manual manual = (Manual)session.load(Manual.class,  id);
        logger.info("manual updated successfully. Manual details: "+manual);

        return manual;
    }

    /**
     * Gets information for building initial page (manuals to show, total quantity of
     * manuals in db (for pagination) and list of values for for filter)
     * @return object with all info necessary to build initial page
     */
    public PageInfoKeeper listManuals() {
        PageInfoKeeper pageInfoKeeper = new PageInfoKeeper();
        /*getting manuals to show and total quantity of manuals in db*/
        pageInfoKeeper.setManuals(getManualsFromDatabase(new UserChoiceKeeper(), pageInfoKeeper));

        /*getting list of values for filter by brand (as per our app logic - initially user
        filters by brand, and than other filters become available).*/
        populateInitialFilter(pageInfoKeeper);

        return pageInfoKeeper;
    }

    /**
     * Gets information for building page to show manuals according to user
     * choices.
     * @param choice - values selected by user from filter fields on web page.
     * @return object with all info necessary to build page with manuals filtered according
     *          to user choices.
     */
    public PageInfoKeeper filterManuals(UserChoiceKeeper choice) {
        /* Need this in case user removes brand selection from filter and launches filtration - we load
        initial page, as no filtration without brand is allowed as per app logic */
        if (choice.getManualBrand().length()==0){
            return listManuals();
        }

        PageInfoKeeper pageInfoKeeper = new PageInfoKeeper();

        /*this field is mainly for check on jsp page. Also checked in getManualsFromDatabase method*/
        pageInfoKeeper.setHasFilters(true);

        /*getting manuals to show*/
        pageInfoKeeper.setManuals(getManualsFromDatabase(choice, pageInfoKeeper));

        /*getting values for filtration fields at jsp*/
        populateFilters(choice, pageInfoKeeper);

        return pageInfoKeeper;
    }

    /**
     * Gets manuals for showing on page with number selected by User.
     * This is realisation of pagination in our app.
     * @param pageNo - number of page to show
     * @return - object with all info necessary to build page with manuals, where
     *          number of page is selected by user.
     */
    public PageInfoKeeper loadManualsFromPage(int pageNo) {
        /* This checks if user has chosen to load page with negative/zero number. For that case we load
          initial page */
        if (pageNo<=0){
            pageNo = 1;
        }
        /*We start showing pages from 1, but really results start from 0, so we need to just it here,
        because if we do this on jsp layer - it will confuse final user.*/
        pageNo = pageNo-1;

        PageInfoKeeper pageInfoKeeper = new PageInfoKeeper();
        pageInfoKeeper.setCurrentPage(pageNo);

        /*Getting manuals to show on chosen page*/
        pageInfoKeeper.setManuals(getManualsFromDatabase(new UserChoiceKeeper(), pageInfoKeeper));

         /*getting values for filtration fields at jsp*/
        populateFilters(new UserChoiceKeeper(), pageInfoKeeper);

        return pageInfoKeeper;
    }

    /**
     * Gets manuals from database according to requested scenario.
     * @param choice - user choice in case this method is called by filtration request
     * @param pageInfoKeeper - object, which we fill with requested info
     * @return object with all info necessary to build page with manuals under selected scenario.
     */
    @SuppressWarnings("unchecked")
    private List<Manual>getManualsFromDatabase(UserChoiceKeeper choice, PageInfoKeeper pageInfoKeeper){
        Session session = this.sessionFactory.getCurrentSession();

       /* preparing query to send to db. In this method we check if we actually have filters and than add
       them to criteria, if true.*/
        Criteria criteria = this.getQuery(session,choice);

       /* if we have filters - we don't use pagination - we show all results which we get after filtration. */
        if (pageInfoKeeper.isHasFilters())
        {
            return criteria.list();
        }

        /* This is the maximum quantity of manuals to show on web page. */
        int maxResults = Constants.MAX_RESULTS;

        /* Here we get total manual quantity in db and store it in according field of pageInfoKeeper.*/
        pageInfoKeeper.setTotalQueryResults((Long)(session.createQuery("select count(*) FROM Manual").uniqueResult()));

        /*if (pageInfoKeeper.getCurrentPage()*maxResults>pageInfoKeeper.getTotalQueryResults()){
            pageInfoKeeper.setCurrentPage(0);
        }*/
        //TODO: decide do we have any situation when this check can be actual.

        /* Setting max results criteria to query. We don't need to get more manuals, than we show. */
        criteria.setMaxResults(maxResults);

        /* Setting number of first result criteria to query. Its actual if we get information for pages different from 0. */
        criteria.setFirstResult(pageInfoKeeper.getCurrentPage()*maxResults);

        return criteria.list();
    }

    /**
     * Sets fields (which are List<String> objects) to PageInfoKeeper object to be shown
     * as values for drop-down filter fields in web page.
     * @param choice - user choices from filter fields, in case we call this from filter method.
     * @param pageInfoKeeper - object, which we fill with requested info
     */
    private void populateFilters(UserChoiceKeeper choice, PageInfoKeeper pageInfoKeeper){
        /*Preparing query to db.*/
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = this.getQuery(session,choice);

        /* We check if filter by brand is present and if no - we don't need to get other filters as per app logic.
          If filter by brand is present - we go 3 times to db and fill all fields, else we go only once and fill only
          brands field.
         */
        String brand = choice.getManualBrand();
        if (brand!=null&&brand.length()>0) {
            List<String>brands = new ArrayList<String>();
            brands.add(brand);
            pageInfoKeeper.setBrands(brands);

            criteria = this.getQuery(session, choice);
            criteria.addOrder(Order.asc("partNo"));
            criteria.setProjection(Projections.distinct(Projections.property("partNo")));
            pageInfoKeeper.setParts(criteria.list());

            criteria = this.getQuery(session, choice);
            criteria.addOrder(Order.asc("docType"));
            criteria.setProjection(Projections.distinct(Projections.property("docType")));
            pageInfoKeeper.setDocTypes(criteria.list());

            criteria = this.getQuery(session, choice);
            criteria.addOrder(Order.asc("category"));
            criteria.setProjection(Projections.distinct(Projections.property("category")));
            pageInfoKeeper.setCategories(criteria.list());
        }
        else {
            criteria.addOrder(Order.asc("brand"));
            criteria.setProjection(Projections.distinct(Projections.property("brand")));
            pageInfoKeeper.setBrands(criteria.list());
        }
    }

    /**
     * Populates brands field with values to be shown on web page at drop down filter field.
     * @param pageInfoKeeper - object, which we fill with requested info
     */
    private void populateInitialFilter(PageInfoKeeper pageInfoKeeper){
        /* Preparing query for db. */
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Manual.class);

        /*Setting order*/
        criteria.addOrder(Order.asc("brand"));

        /*Setting column from which we need values and marking that we need distinct values.*/
        criteria.setProjection(Projections.distinct(Projections.property("brand")));

        /*executing query and setting results*/
        pageInfoKeeper.setBrands(criteria.list());
    }

    /**
     * Prepares query to db, adding SQL part "where field xxx like 'yyy' " on HQL language.
     * @param session - session in which query should be executed.
     * @param choice - user choices from web page filtration fields (if present), otherwise - blank object.
     * @return executable query to db.
     */
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
    /* Setter for sessionFactory */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}

