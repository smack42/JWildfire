package org.jwildfire.create.tina.variation;

import java.util.HashMap;
import java.util.Map;

public enum RessourceName {
    IMAGE_FILENAME  ("image_filename"),
    INLINED_IMAGE   ("inlined_image"),
    IMAGE_SRC       ("image_src"),
    IMAGE_DESC_SRC  ("image_desc_src"),
    FLAME           ("flame"),
    FORMULA         ("formula"),
    XFORMULA        ("xformula"),
    YFORMULA        ("yformula"),
    ZFORMULA        ("zformula"),
    OBJ_FILENAME    ("obj_filename"),
    UVMAP_FILENAME  ("uvmap_filename")
    ;
    
    /** unique String representation of each RessourceName value **/
    public final String text;
    
    /** the constructor */
    private RessourceName(String text) {
        this.text = text;
    }
    
    private static final Map<String, RessourceName> TEXT_MAP;
    static {
        Map<String, RessourceName> map = new HashMap<>();
        for (final RessourceName n : values()) {
            if (map.put(n.text, n) != null) {
                throw new IllegalArgumentException("duplicate RessourceName " + n.toString());
            }
        }
        TEXT_MAP = map;
    }
    
    /**
     * find a RessourceName value by its "text" String property
     * @param text String representation of the requested RessourceName value
     * @return the RessourceName that contains the specified text
     * @throws IllegalArgumentException if no RessourceName is found for the specified text
     */
    public static RessourceName get(String text) {
        final RessourceName result = TEXT_MAP.get(text);
        if (result == null) {
            throw new IllegalArgumentException("no RessourceName found for " + text);
        } else {
            return result;
        }
    }
    
    /**
     * returns the "text" String representation of this RessourceName
     */
    @Override
    public String toString() {
        return this.text;
    }
}
