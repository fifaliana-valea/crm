package site.easy.to.build.crm.util.csv.parameter;

import site.easy.to.build.crm.util.csv.exception.CSVException;

public interface CellCSV<T> {
    public T getValue(String value,int line)throws CSVException;
}
