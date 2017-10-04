package manualweb.dao;

import manualweb.model.Manual;
import manualweb.model.ManualFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManualDaoImpl implements ManualDao {
    private static final Logger logger = LoggerFactory.getLogger(ManualDaoImpl.class);

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
        manualFilter.populateFilters();

        return manualFilter;
    }

    public ManualFilter filterManuals(ManualFilter filter) {
        ManualFilter manualFilter = new ManualFilter();
        manualFilter.setManuals(getRawManuals(filter));
        manualFilter.setFilters(filter);
       // manualFilter.filterManuals();
        manualFilter.populateFilters();

        return manualFilter;
    }

    @SuppressWarnings("unchecked")
    private List<Manual> getRawManuals(ManualFilter filter){
        Session session = this.sessionFactory.getCurrentSession();

        return session.createQuery(this.buildQuery(filter)).list();
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

        return query;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}

