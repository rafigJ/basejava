import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; storage[i] != null; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; storage[i] != null; i++) {
            if (storage[i].toString().equals(uuid))
                return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        int remove_index = size();
        for (int i = 0; storage[i] != null; i++) {
            if (storage[i].toString().equals(uuid)){
                remove_index = i;
                break;
            }
        }
        for (int i = remove_index; i < size(); i++) {
            storage[i] = storage[i + 1];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size()];
        if (size() >= 0) System.arraycopy(storage, 0, resumes, 0, size());
        return resumes;
    }

    int size() {
        int i = 0;
        while (storage[i] != null){
            i++;
        }
        return i;
    }
}
