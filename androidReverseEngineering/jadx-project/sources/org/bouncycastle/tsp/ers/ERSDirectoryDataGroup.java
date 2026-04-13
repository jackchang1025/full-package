package org.bouncycastle.tsp.ers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ERSDirectoryDataGroup extends ERSDataGroup {
    public ERSDirectoryDataGroup(File file) {
        super(buildGroup(file));
    }

    private static List<ERSData> buildGroup(File file) {
        ERSCachingData eRSFileData;
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("file reference does not refer to directory");
        }
        File[] listFiles = file.listFiles();
        ArrayList arrayList = new ArrayList(listFiles.length);
        for (int i2 = 0; i2 != listFiles.length; i2++) {
            if (!listFiles[i2].isDirectory()) {
                eRSFileData = new ERSFileData(listFiles[i2]);
            } else if (listFiles[i2].listFiles().length != 0) {
                eRSFileData = new ERSDirectoryDataGroup(listFiles[i2]);
            }
            arrayList.add(eRSFileData);
        }
        return arrayList;
    }

    public List<ERSFileData> getFiles() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 != this.dataObjects.size(); i2++) {
            if (this.dataObjects.get(i2) instanceof ERSFileData) {
                arrayList.add((ERSFileData) this.dataObjects.get(i2));
            }
        }
        return arrayList;
    }

    public List<ERSDirectoryDataGroup> getSubdirectories() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 != this.dataObjects.size(); i2++) {
            if (this.dataObjects.get(i2) instanceof ERSDirectoryDataGroup) {
                arrayList.add((ERSDirectoryDataGroup) this.dataObjects.get(i2));
            }
        }
        return arrayList;
    }
}
