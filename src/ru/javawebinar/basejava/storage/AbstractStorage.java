package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid).thenComparing(Resume::getFullName);

    protected static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public final void update(Resume r) {
        LOG.info("update " + r);
        SK searchKey = getExistingSearchKey(r.getUuid());
        replaceResume(r, searchKey);
    }

    @Override
    public final void save(Resume r) {
        LOG.info("save " + r);
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        saveResume(r, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("get " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("ERROR: a resume with a similar uuid is not present in the storage; uuid: " + uuid);
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("ERROR: a resume with a similar uuid is present in the storage; uuid: " + uuid);
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public final List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopyAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    protected abstract List<Resume> doCopyAll();

    protected abstract void replaceResume(Resume r, SK searchKey);

    protected abstract void saveResume(Resume r, SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void deleteResume(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

}
