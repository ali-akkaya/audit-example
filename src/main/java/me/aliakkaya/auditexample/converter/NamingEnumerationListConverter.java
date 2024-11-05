package me.aliakkaya.auditexample.converter;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

public class NamingEnumerationListConverter {

    public static <T> List<T> convertNamingEnumerationToList(NamingEnumeration<T> namingEnumeration) {
        List<T> list = new ArrayList<>();
        try {
            while (namingEnumeration.hasMore()) {
                list.add(namingEnumeration.next());
            }
        } catch (NamingException e) {
            // Handle exception
            e.printStackTrace();
        } finally {
            // Ensure the NamingEnumeration is closed if necessary
            try {
                namingEnumeration.close();
            } catch (Exception e) {
                // Handle exception during closure
                e.printStackTrace();
            }
        }
        return list;
    }
}
