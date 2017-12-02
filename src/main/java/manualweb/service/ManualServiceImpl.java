package manualweb.service;

import manualweb.dao.ManualDao;
import manualweb.model.Manual;
import manualweb.model.PageInfoKeeper;
import manualweb.model.UserChoiceKeeper;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class ManualServiceImpl implements ManualService {
    private ManualDao manualDao;

    @Transactional
    public void addManual(Manual manual) {
        this.manualDao.addManual(manual);
    }

    @Transactional
    public void updateManual(Manual manual) {
        this.manualDao.updateManual(manual);
    }

    @Transactional
    public void removeManual(int id) {
    this.manualDao.removeManual(id);
    }

    @Transactional
    public Manual getManualById(int id) {
        return this.manualDao.getManualById(id);
    }

    @Transactional
    public PageInfoKeeper listManuals() {
      return this.manualDao.listManuals();
    }

    @Transactional
    public PageInfoKeeper filterManuals(UserChoiceKeeper choice) {
        return this.manualDao.filterManuals(choice);
    }

    @Transactional
    public PageInfoKeeper loadManualsFromPage(int pageNo) {
        return this.manualDao.loadManualsFromPage(pageNo);
    }

    public void setManualDao(ManualDao manualDao) {
        this.manualDao = manualDao;
    }
}
