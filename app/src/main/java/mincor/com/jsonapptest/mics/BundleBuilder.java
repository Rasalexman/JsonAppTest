package mincor.com.jsonapptest.mics;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by alexander on 31.08.17.
 */

public class BundleBuilder {
    private final Bundle bundle;

    public BundleBuilder(Bundle bundle) {
        this.bundle = bundle;
    }

    public BundleBuilder putParcelable(String key, Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    public Bundle build() {
        return bundle;
    }
}
