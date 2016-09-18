package org.jokar.gankio.model.network.result;

/**
 * Created by JokAr on 16/9/17.
 */
public class HttpResult<T> {
    boolean error;
    T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
