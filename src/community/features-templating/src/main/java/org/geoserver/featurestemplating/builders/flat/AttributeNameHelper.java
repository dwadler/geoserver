/* (c) 2020 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.featurestemplating.builders.flat;

/** An helper class to help creating attribute names when producing a flat geoJson output */
class AttributeNameHelper {

    private String key;
    private String parentKey;

    private String separator;

    static final String PROPERTIES_KEY = "properties";

    AttributeNameHelper(String key, String separator) {
        this.key = key;
        this.separator = separator;
    }

    String getFinalAttributeName() {
        String parentKey = this.parentKey;
        String key = this.key;
        if (parentKey != null && !parentKey.equals(PROPERTIES_KEY) && key != null)
            key = parentKey + separator + key;
        else if (key == null) key = parentKey;
        if (separator != "_" && key != null)
            key = replaceDefaultSeparatorWithCustom(key, separator);
        return key;
    }

    String getCompleteCompositeAttributeName() {
        String parentKey = this.parentKey;
        String key = this.key;
        if (parentKey != null && !parentKey.equals(PROPERTIES_KEY)) {
            if (key != null && !key.equals(PROPERTIES_KEY)) key = parentKey + separator + key;
            else if (key == null || key.equals(PROPERTIES_KEY)) key = parentKey;
        }
        if (separator != "_" && key != null)
            key = replaceDefaultSeparatorWithCustom(key, separator);
        return key;
    }

    String getCompleteIteratingAttributeName(int elementsSize, int index) {
        String key = this.key;
        String parentKey = this.parentKey;
        if (parentKey != null && !parentKey.equals(PROPERTIES_KEY))
            key = parentKey + separator + key;
        if (separator != "_") key = replaceDefaultSeparatorWithCustom(key, separator);
        String itKey;
        if (elementsSize > 0) itKey = key + "_" + (index + 1);
        else itKey = key;
        return itKey;
    }

    private String replaceDefaultSeparatorWithCustom(String key, String separator) {
        char[] charArr = key.toCharArray();
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < charArr.length; i++) {
            char next = ' ';
            char curr = charArr[i];
            if (curr == '_') {
                if (i < charArr.length - 1) {
                    next = charArr[i + 1];
                }
                if (!Character.isDigit(next)) sb.append(separator);
                else sb.append(curr);
            } else {
                sb.append(curr);
            }
        }
        return sb.toString();
    }

    void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getSeparator() {
        return separator;
    }
}