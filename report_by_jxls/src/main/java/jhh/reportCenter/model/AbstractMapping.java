package jhh.reportCenter.model;


import java.io.Serializable;

/**
 * 映射抽象类
 *
 * @param <T>
 */
public class AbstractMapping<T> implements Serializable {
    private T id;
    private transient Long version;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
